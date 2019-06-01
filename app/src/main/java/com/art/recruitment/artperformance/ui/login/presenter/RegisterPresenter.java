package com.art.recruitment.artperformance.ui.login.presenter;

import com.art.recruitment.artperformance.api.LoginService;
import com.art.recruitment.artperformance.bean.im.ImUserBean;
import com.art.recruitment.artperformance.bean.login.RegisterBean;
import com.art.recruitment.artperformance.bean.login.ResetPasswordBean;
import com.art.recruitment.artperformance.bean.login.VerificationCodeBean;
import com.art.recruitment.artperformance.ui.login.contract.RegisterContract;
import com.art.recruitment.common.base.BaseBean;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;
import com.blankj.utilcode.util.LogUtils;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RegisterPresenter extends BasePresenter<RegisterContract> {

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
     * 注册
     */
    public void registerAccount(String registerStr) {

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), registerStr);

        Api.
                observable(Api.getService(LoginService.class).registerAccount(body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<RegisterBean.DataBean, RegisterBean>() {
                    @Override
                    protected void _onSuccess(RegisterBean.DataBean bean, String successMessage) {
                        mView.returnRegisterBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, RegisterBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 重置密码
     */
    public void resetPassword(String registerStr) {

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), registerStr);

        Api.
                observable(Api.getService(LoginService.class).resetPassword(body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<ResetPasswordBean.DataBean, ResetPasswordBean>() {
                    @Override
                    protected void _onSuccess(ResetPasswordBean.DataBean bean, String successMessage) {
                        mView.returnResetPasswordBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, ResetPasswordBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     *获取用户信息
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