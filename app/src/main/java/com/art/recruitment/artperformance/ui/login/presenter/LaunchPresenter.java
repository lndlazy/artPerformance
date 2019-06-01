package com.art.recruitment.artperformance.ui.login.presenter;

import com.art.recruitment.artperformance.api.LoginService;
import com.art.recruitment.artperformance.bean.login.StartUpBean;
import com.art.recruitment.artperformance.bean.login.TokenBean;
import com.art.recruitment.artperformance.ui.login.contract.LaunchContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;


public class LaunchPresenter extends BasePresenter<LaunchContract> {

    /**
     *
     */
    public void startUP() {

        Api.
                observable(Api.getService(LoginService.class).startUP()).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<StartUpBean.DataBean, StartUpBean>() {
                    @Override
                    protected void _onSuccess(StartUpBean.DataBean tokenBean, String successMessage) {
                        if (tokenBean != null){
                            mView.returnStartUpBean(tokenBean);
                        }
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, StartUpBean.DataBean tokenBean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }
}
