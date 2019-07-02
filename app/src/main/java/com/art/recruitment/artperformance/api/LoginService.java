package com.art.recruitment.artperformance.api;

import com.art.recruitment.artperformance.bean.home.ApplyBean;
import com.art.recruitment.artperformance.bean.im.ImUserBean;
import com.art.recruitment.artperformance.bean.login.RegisterBean;
import com.art.recruitment.artperformance.bean.login.ResetPasswordBean;
import com.art.recruitment.artperformance.bean.login.StartUpBean;
import com.art.recruitment.artperformance.bean.login.TokenBean;
import com.art.recruitment.artperformance.bean.login.VerificationCodeBean;
import com.art.recruitment.artperformance.ui.login.ThirdBindResultEntry;
import com.art.recruitment.artperformance.ui.login.ThirdLoginEntry;
import com.art.recruitment.common.base.BaseBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LoginService {

    /**
     * 开机启动页接口
     */
    @GET(ApiUrls.HOME_STARTUP)
    Observable<StartUpBean> startUP();

    /**
     * 获取Token接口
     */
    @POST(ApiUrls.TOKEN)
    Observable<TokenBean> getToken(@Body RequestBody body);

    /**
     * 发送手机验证码接口
     */
    @POST(ApiUrls.PHONE_VERIFICATION_CODE_SMS)
    Observable<VerificationCodeBean> getVerificationCode(@Body RequestBody body);

    /**
     * 注册账号接口
     */
    @POST(ApiUrls.REGISTER_ACCOUNT)
    Observable<RegisterBean> registerAccount(@Body RequestBody body);

    /**
     * 重置密码
     */
    @PUT(ApiUrls.RESET_PASSWORD)
    Observable<ResetPasswordBean> resetPassword(@Body RequestBody body);

    /**
     * 获取环信用户信息
     */
    @GET(ApiUrls.IM_USER)
    Observable<ImUserBean> imUser();


    /**
     * 第三方登录
     */
    @POST(ApiUrls.AUTHENTICATION_LOGIN)
    Observable<ThirdLoginEntry> authenticationLogin(@Path("socialType") String socialType, @Body RequestBody body);


    /**
     * 第三方账号绑定
     */
    @POST(ApiUrls.AUTHENTICATION_BIND)
    Observable<ThirdBindResultEntry> authenticationBind(@Path("socialType") String socialType, @Body RequestBody body);


//    /**
//     * 获取分享的url
//     */
//    @GET(ApiUrls.SHARE_APP)
//    Observable<ImUserBean> shareApp();


}
