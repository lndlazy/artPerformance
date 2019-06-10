package com.art.recruitment.artperformance.ui.group.presenter;

import com.art.recruitment.artperformance.api.GroupService;
import com.art.recruitment.artperformance.api.HomeService;
import com.art.recruitment.artperformance.bean.group.RecruitmentEditBean;
import com.art.recruitment.artperformance.bean.group.ReleaseRecruitmentbBean;
import com.art.recruitment.artperformance.bean.home.BannerBean;
import com.art.recruitment.artperformance.bean.home.RecruitmentInforBean;
import com.art.recruitment.artperformance.ui.group.contract.ReleaseRecruitmentContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ReleaseRecruitmentPresenter extends BasePresenter<ReleaseRecruitmentContract> {

    /**
     * 发布招募
     *
     */
    public void releaseRecruitmen(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        Api.
                observable(Api.getService(GroupService.class).releaseRecruitmen(body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<ReleaseRecruitmentbBean.DataBean, ReleaseRecruitmentbBean>() {
                    @Override
                    protected void _onSuccess(ReleaseRecruitmentbBean.DataBean bean, String successMessage) {
                        mView.returnReleaseRecruitmentBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, ReleaseRecruitmentbBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 编辑招募信息
     *
     */
    public void recruitmentEdit(String recruitmentId, String str) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        Api.
                observable(Api.getService(GroupService.class).recruitmentEdit(recruitmentId, body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<RecruitmentEditBean.DataBean, RecruitmentEditBean>() {
                    @Override
                    protected void _onSuccess(RecruitmentEditBean.DataBean bean, String successMessage) {
                        mView.returnRecruitmentEdieBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, RecruitmentEditBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 获取招募详情
     *
     */
    public void recuitDetail(String recruitmentId) {

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

}
