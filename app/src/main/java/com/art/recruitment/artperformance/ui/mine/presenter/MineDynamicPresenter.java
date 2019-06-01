package com.art.recruitment.artperformance.ui.mine.presenter;

import com.art.recruitment.artperformance.api.MineService;
import com.art.recruitment.artperformance.bean.mine.MineDynamicBean;
import com.art.recruitment.artperformance.bean.mine.RecruitmentOptBean;
import com.art.recruitment.artperformance.ui.mine.contract.MineDynamicContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

public class MineDynamicPresenter extends BasePresenter<MineDynamicContract> {
    /**
     * 我的动态圈列表
     */
    public void mineDynamicList(int page, int size, String sort) {

        Api.
                observable(Api.getService(MineService.class).mineDynamicList(page, size, sort)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<MineDynamicBean.DataBean, MineDynamicBean>() {
                    @Override
                    protected void _onSuccess(MineDynamicBean.DataBean bean, String successMessage) {
                        mView.returnMineDynamicBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, MineDynamicBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }
}
