package com.art.recruitment.common.http.interceptor;


import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.art.recruitment.common.BuildConfig;
import com.art.recruitment.common.base.BaseApplication;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.utils.EncryptSPUtils;
import com.art.recruitment.common.utils.GetExtranetIpUtils;
import com.art.recruitment.common.utils.IpUtils;
import com.art.recruitment.common.utils.PackageUtils;
import com.art.recruitment.common.utils.SystemUtil;
import com.art.recruitment.common.utils.UnClearCacheUtils;
import com.art.recruitment.common.utils.UuidUtils;
import com.art.recruitment.common.view.Global;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装公共参数（Key和密码）
 * <p>
 *
 * @author Administrator
 *         封装公共参数  12/29 只添加了sysfrom 字段； 有其他字段需封装再修改
 *         <p>
 */
public class ParamsInterceptor implements Interceptor {

    private static final String TAG = "request params";
    private Context context;
    private String appcode;
    private String ipAddress;
    private String phoneModel;
    private String deviceId;

    @Inject
    public ParamsInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request orgRequest = chain.request();
        RequestBody body = orgRequest.body();
        //收集请求参数，方便调试
        StringBuilder paramsBuilder = new StringBuilder();

        if (body != null) {

            RequestBody newBody;

            if (body instanceof FormBody) {
                newBody = addParamsToFormBody((FormBody) body, paramsBuilder);
            } else if (body instanceof MultipartBody) {
                newBody = addParamsToMultipartBody((MultipartBody) body, paramsBuilder);
            } else {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                getParmsValue();
                newBody = new FormBody.Builder()
                        .add("token", EncryptSPUtils.getSharedStringData(BaseApplication.getAppContext(), Global.APP_TOKEN_KEY))
                        .build();
                Request.Builder requestBuilder = original.newBuilder()
                        .method(original.method(), newBody)
                        .url(originalHttpUrl);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }


            //打印参数
            Request newRequest = orgRequest.newBuilder()
                    .url(orgRequest.url())
                    .method(orgRequest.method(), newBody)
                    .build();

            return chain.proceed(newRequest);

        }

        return chain.proceed(orgRequest);
    }

    /**
     * 为MultipartBody类型请求体添加参数
     * <p>
     *
     * @param body          请求主体
     * @param paramsBuilder 参数builder
     * @return builder.build();
     */
    private MultipartBody addParamsToMultipartBody(MultipartBody body, StringBuilder paramsBuilder) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        getParmsValue();
        builder.addFormDataPart("token", EncryptSPUtils.getSharedStringData(BaseApplication.getAppContext(), BaseConfig.BaseSPKey.TOKEN));
        paramsBuilder.append("token=").append(EncryptSPUtils.getSharedStringData(BaseApplication.getAppContext(), Global.APP_TOKEN_KEY));

//        builder.addFormDataPart("token", EncryptSPUtils.getInstance().getString(BaseConfig.BaseSPKey.TOKEN));


        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addPart(body.part(i));
        }

        return builder.build();
    }


    /**
     * 为FormBody类型请求体添加参数
     * <p>
     *
     * @param body          请求主体
     * @param paramsBuilder 参数builder
     * @return builder.build();
     */

    private FormBody addParamsToFormBody(FormBody body, StringBuilder paramsBuilder) {
        boolean isExistDeviceType = false;

        FormBody.Builder builder = new FormBody.Builder();
        getParmsValue();
        builder.add("token", EncryptSPUtils.getSharedStringData(BaseApplication.getAppContext(), Global.APP_TOKEN_KEY));
        paramsBuilder.append("token=").append(EncryptSPUtils.getSharedStringData(BaseApplication.getAppContext(), Global.APP_TOKEN_KEY));

        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            if (!"userNo".equals(body.encodedName(i))) {
                builder.addEncoded(body.encodedName(i), body.encodedValue(i));
                paramsBuilder.append("&");
                paramsBuilder.append(body.name(i));
                paramsBuilder.append("=");
                paramsBuilder.append(body.value(i));
            }
//            if ("deviceType".equals(body.encodedName(i))) {
//                isExistDeviceType = true;
//            } else {
//                if (!isExistDeviceType) {
//                    isExistDeviceType = false;
//                }
//            }
        }

//        if (!isExistDeviceType) {
//            builder.add("deviceType", phoneModel);
//            paramsBuilder.append("deviceType=").append(phoneModel);
//        }
        return builder.build();
    }


    private void getParmsValue() {
        appcode = String.valueOf(PackageUtils.getVersionCode(context));
        String extranetIp = com.blankj.utilcode.util.SPUtils.getInstance().getString(BaseConfig.BaseSPKey.EXTRANET_IP);
        if (TextUtils.isEmpty(extranetIp)) {
            GetExtranetIpUtils.getMobileIP();
            extranetIp =com.blankj.utilcode.util.SPUtils.getInstance().getString(BaseConfig.BaseSPKey.EXTRANET_IP);
        }
        ipAddress = extranetIp + "," + IpUtils.GetHostIp();
        phoneModel = SystemUtil.getPhoneModel();
        deviceId = UnClearCacheUtils.getString(BaseApplication.getAppContext(), Global.DEVICE_ID);
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = UuidUtils.id(BaseApplication.getAppContext());
            UnClearCacheUtils.putString(BaseApplication.getAppContext(), Global.DEVICE_ID, deviceId);
        }

    }
}

