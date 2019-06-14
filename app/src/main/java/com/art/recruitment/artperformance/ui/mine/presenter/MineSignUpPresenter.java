package com.art.recruitment.artperformance.ui.mine.presenter;

import com.art.recruitment.artperformance.api.GroupService;
import com.art.recruitment.artperformance.api.MineService;
import com.art.recruitment.artperformance.bean.mine.CancelRecruitmentBean;
import com.art.recruitment.artperformance.bean.mine.MineRecruitBean;
import com.art.recruitment.artperformance.bean.mine.MineSignUpBean;
import com.art.recruitment.artperformance.ui.mine.contract.MineSignUpContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MineSignUpPresenter extends BasePresenter<MineSignUpContract> {

    /**
     * 我的报名
     */
    public void applyList(final int page, int size, String sort) {

        Api.
                observable(Api.getService(MineService.class).applyList(page, size, sort)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<MineSignUpBean.DataBean, MineSignUpBean>() {
                    @Override
                    protected void _onSuccess(MineSignUpBean.DataBean bean, String successMessage) {
                        mView.returnApplyListBean(bean , page);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, MineSignUpBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 取消报名
     */
    public void cancelRecruitment(int applyId, String recruitmentId) {

        Api.
                observable(Api.getService(MineService.class).cancelRecruitment(applyId, recruitmentId)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<CancelRecruitmentBean.DataBean, CancelRecruitmentBean>() {
                    @Override
                    protected void _onSuccess(CancelRecruitmentBean.DataBean bean, String successMessage) {
                        mView.returnCancelRecruitmentBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, CancelRecruitmentBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }


    /**
     *根据群演ID获取影虎信息
     */
    public void chatGroups(String actorId) {

        Api.
                observable(Api.getService(GroupService.class).chatGroups(actorId)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<MineRecruitBean.DataBean, MineRecruitBean>() {
                    @Override
                    protected void _onSuccess(MineRecruitBean.DataBean bean, String successMessage) {
                        mView.returnChatGroupsBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, MineRecruitBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }


}
