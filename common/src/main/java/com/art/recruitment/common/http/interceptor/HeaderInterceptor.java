package com.art.recruitment.common.http.interceptor;

import com.art.recruitment.common.base.BaseApplication;
import com.art.recruitment.common.base.config.BaseConfig;
import com.art.recruitment.common.utils.EncryptSPUtils;
import com.art.recruitment.common.view.Global;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;



public class HeaderInterceptor implements Interceptor {

    private int mHeaderType;

    public HeaderInterceptor(int headerType) {
        this.mHeaderType = mHeaderType;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(initBuilder(chain.request().newBuilder()).build());
    }

    private Request.Builder initBuilder(Request.Builder builder) {

        builder.addHeader("Content-Ty", "application/json");
        builder.addHeader("Authorization", SPUtils.getInstance().getString(BaseConfig.BaseSPKey.TOKEN));
//        builder.addHeader("platform", "1");
//        builder.addHeader("appVersion", );
//        builder.addHeader("sysfrom", BuildConfig.SYSFROM);
//        builder.addHeader("userNo", com.baseapp.common.utils.SPUtils.getSharedStringData(BaseApplication.getAppContext(), Global.APP_USER_CODE_KEY));
//        builder.addHeader("platform", BuildConfig.PLATFORM);
//        builder.addHeader("appVersion", "1.0");
//        }
        return builder;
    }

    /**
     * 获取Http请求header字符串
     *
     * @param base64 是否使用base64编码
     * @return
     */
    private String getAuthorizationString(boolean base64) {
        String mHeaderString = "";
        JSONObject mJsonObject = new JSONObject();
        try {
            mJsonObject.put("token", EncryptSPUtils.getSharedStringData(BaseApplication.getAppContext(),BaseConfig.BaseSPKey.TOKEN));
//            mJsonObject.put("sysfrom", BuildConfig.SYSFROM);
//            mJsonObject.put("userNo", SPUtils.getInstance().getString(BaseConfig.BaseSPKey.USER_NO));
//            mJsonObject.put("platform", BuildConfig.PLATFORM);
            mJsonObject.put("appVersion", "");
            if (base64) {
                String mRawString = mJsonObject.toString();
                mHeaderString = EncodeUtils.base64Encode2String(mRawString.getBytes());
            } else {
                mHeaderString = mJsonObject.toString();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mHeaderString;
    }
}
