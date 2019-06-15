package com.art.recruitment.artperformance.ui.login.presenter;

import com.art.recruitment.artperformance.api.LoginService;
import com.art.recruitment.artperformance.bean.im.ImUserBean;
import com.art.recruitment.artperformance.bean.login.VerificationCodeBean;
import com.art.recruitment.artperformance.ui.login.ThirdBindResultEntry;
import com.art.recruitment.artperformance.ui.login.contract.RegisterContract;
import com.art.recruitment.artperformance.ui.login.contract.ThirdBindContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by linaidao on 2019/6/15.
 */

public class ThirdBindPresenter extends BasePresenter<ThirdBindContract> {


    /**
     * 获取验证码
     */
    public void getVerificationCode(String codeStr) {

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), codeStr);

        Api.
                observable(Api.getService(LoginService.class).getVerificationCode(body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<VerificationCodeBean.DataBean, VerificationCodeBean>() {
                    @Override
                    protected void _onSuccess(VerificationCodeBean.DataBean verificationCodeBean, String successMessage) {
                        mView.returnVerificationCodeBean(verificationCodeBean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, VerificationCodeBean.DataBean verificationCodeBean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }


    /**
     * 第三方绑定
     * @param socialType
     * @param bindStr
     */
    public void thirdBind(String socialType, String bindStr) {

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bindStr);

        Api.
                observable(Api.getService(LoginService.class).authenticationBind(socialType , body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<ThirdBindResultEntry.DataBean, ThirdBindResultEntry>() {
                    @Override
                    protected void _onSuccess(ThirdBindResultEntry.DataBean bindResult, String successMessage) {
                        mView.returnBindInfo(bindResult);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, ThirdBindResultEntry.DataBean verificationCodeBean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });

    }

    /**
     * 获取用户信息
     */
    public void imUser() {

        Api.
                observable(Api.getService(LoginService.class).imUser()).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<ImUserBean.DataBean, ImUserBean>() {
                    @Override
                    protected void _onSuccess(ImUserBean.DataBean tokenBean, String successMessage) {

                        mView.returnImUserBean(tokenBean);

                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, ImUserBean.DataBean tokenBean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }


}
