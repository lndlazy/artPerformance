package com.art.recruitment.artperformance.ui;

import com.art.recruitment.artperformance.api.GroupService;
import com.art.recruitment.artperformance.api.LoginService;
import com.art.recruitment.artperformance.bean.group.CityBean;
import com.art.recruitment.artperformance.bean.group.StatusBean;
import com.art.recruitment.artperformance.bean.im.ImUserBean;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;
import com.hyphenate.chat.EMClient;
import com.orhanobut.logger.Logger;

import java.util.List;

public class MainPresenter extends BasePresenter<MainContract> {
    /**
     * 获取城市列表
     */
    public void status() {
        Api.
                observable(Api.getService(GroupService.class).status()).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<StatusBean.DataBean, StatusBean>() {
                    @Override
                    protected void _onSuccess(StatusBean.DataBean bean, String successMessage) {
                        mView.returbStatusBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, StatusBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    public void loginToHx() {

//        if (EMClient.getInstance().isLoggedInBefore()) {
//            Logger.d("已经登录过环信");
//            return;
//        }

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
                        Logger.d("登录聊天服务器失败？:" + message);
                    }
                });

    }

//    /**
//     * 获取分享的url
//     */
//    public void getShareUrl() {
//
//        Api.
//                observable(Api.getService(LoginService.class).shareApp()).
//                presenter(this).
//                requestMode(RequestMode.SINGLE).
//                showLoading(true).
//                doRequest(new RxSubscriber<StatusBean.DataBean, StatusBean>() {
//                    @Override
//                    protected void _onSuccess(StatusBean.DataBean bean, String successMessage) {
//
//                    }
//
//                    @Override
//                    protected void _onError(ErrorType errorType, int errorCode, String message, StatusBean.DataBean bean) {
//
//                    }
//                });
//
//
//    }
}
