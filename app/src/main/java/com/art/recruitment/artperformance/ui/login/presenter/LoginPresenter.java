package com.art.recruitment.artperformance.ui.login.presenter;

import com.art.recruitment.artperformance.api.LoginService;
import com.art.recruitment.artperformance.bean.im.ImUserBean;
import com.art.recruitment.artperformance.bean.login.TokenBean;
import com.art.recruitment.artperformance.ui.login.contract.LoginContract;
import com.art.recruitment.common.base.BaseBean;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;
import com.blankj.utilcode.util.LogUtils;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginPresenter extends BasePresenter<LoginContract> {

    /**
     * 获取Token
     *
     */
    public void getToken(String token) {

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), token);
        Api.
                observable(Api.getService(LoginService.class).getToken(body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<TokenBean.DataBean, TokenBean>() {
                    @Override
                    protected void _onSuccess(TokenBean.DataBean tokenBean, String successMessage) {
                        if (tokenBean != null){
                            mView.returnTokenBean(tokenBean);
                        }
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, TokenBean.DataBean tokenBean) {
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