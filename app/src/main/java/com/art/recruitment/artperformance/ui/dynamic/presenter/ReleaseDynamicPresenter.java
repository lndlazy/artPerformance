package com.art.recruitment.artperformance.ui.dynamic.presenter;

import com.art.recruitment.artperformance.api.DynamicService;
import com.art.recruitment.artperformance.api.MineService;
import com.art.recruitment.artperformance.bean.dynamic.DynamicListBean;
import com.art.recruitment.artperformance.bean.dynamic.ReleaseDynamicBean;
import com.art.recruitment.artperformance.bean.mine.OssBean;
import com.art.recruitment.artperformance.bean.mine.SignaTureBean;
import com.art.recruitment.artperformance.ui.dynamic.contract.ReleaseDynamicContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ReleaseDynamicPresenter extends BasePresenter<ReleaseDynamicContract> {
    /**
     * 发布动态圈
     */
    public void releaseDynamic(String codeStr) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), codeStr);

        Api.
                observable(Api.getService(DynamicService.class).releaseDynamic(body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<ReleaseDynamicBean.DataBean, ReleaseDynamicBean>() {
                    @Override
                    protected void _onSuccess(ReleaseDynamicBean.DataBean bean, String successMessage) {
                        mView.returnReleaseDynamicBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, ReleaseDynamicBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * OSS签名
     */
    public void signaTure(String codeStr) {

        Api.
                observable(Api.getService(MineService.class).signaTure(codeStr)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<String, SignaTureBean>() {
                    @Override
                    protected void _onSuccess(String bean, String successMessage) {
                        mView.returnSignaTureBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, String bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * OSS签名信息
     */
    public void oss() {

        Api.
                observable(Api.getService(MineService.class).oss()).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<OssBean.DataBean, OssBean>() {
                    @Override
                    protected void _onSuccess(OssBean.DataBean bean, String successMessage) {
                        mView.returnOssBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, OssBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

}
