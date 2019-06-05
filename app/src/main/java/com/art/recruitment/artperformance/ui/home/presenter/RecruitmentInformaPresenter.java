package com.art.recruitment.artperformance.ui.home.presenter;

import com.art.recruitment.artperformance.api.HomeService;
import com.art.recruitment.artperformance.bean.home.ApplyBean;
import com.art.recruitment.artperformance.bean.home.RecruitListBean;
import com.art.recruitment.artperformance.bean.home.RecruitmentInforBean;
import com.art.recruitment.artperformance.ui.home.contract.RecruitmentInformaContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

public class RecruitmentInformaPresenter extends BasePresenter<RecruitmentInformaContract> {

    /**
     * 获取招募详情
     *
     */
    public void recuitDetail(int recruitmentId) {

        Api.
                observable(Api.getService(HomeService.class).recuitDetail(recruitmentId)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<RecruitmentInforBean.DataBean, RecruitmentInforBean>() {
                    @Override
                    protected void _onSuccess(RecruitmentInforBean.DataBean bean, String successMessage) {
                        mView.returnRecruitInforBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, RecruitmentInforBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 报名
     *
     */
    public void apply(int recruitmentId) {

        Api.
                observable(Api.getService(HomeService.class).applyPost(recruitmentId)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<ApplyBean.DataBean, ApplyBean>() {
                    @Override
                    protected void _onSuccess(ApplyBean.DataBean bean, String successMessage) {
                        mView.returnApplyBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, ApplyBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }
}
