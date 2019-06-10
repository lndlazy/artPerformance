package com.art.recruitment.artperformance.ui.dynamic.presenter;

import com.art.recruitment.artperformance.api.DynamicService;
import com.art.recruitment.artperformance.api.MineService;
import com.art.recruitment.artperformance.bean.dynamic.DynamicLikesBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicListBean;
import com.art.recruitment.artperformance.bean.mine.MineSignUpBean;
import com.art.recruitment.artperformance.ui.dynamic.contract.DynamicFagmentContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

public class DynamicFagmentPresenter extends BasePresenter<DynamicFagmentContract> {

    /**
     * 动态圈列表
     */
    public void dynamicList(final int page, int size, String sort) {

        Api.
                observable(Api.getService(DynamicService.class).dynamicList(page, size, sort)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<DynamicListBean.DataBean, DynamicListBean>() {
                    @Override
                    protected void _onSuccess(DynamicListBean.DataBean bean, String successMessage) {
                        mView.returnDynamicListBean(bean, page);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, DynamicListBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 动态圈点赞
     */
    public void dynamicLikes(int dynamicCircleId) {

        Api.
                observable(Api.getService(DynamicService.class).dynamicLikes(dynamicCircleId)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<DynamicLikesBean.DataBean, DynamicLikesBean>() {
                    @Override
                    protected void _onSuccess(DynamicLikesBean.DataBean bean, String successMessage) {
                        mView.returnDynamicLikesBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, DynamicLikesBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

}
