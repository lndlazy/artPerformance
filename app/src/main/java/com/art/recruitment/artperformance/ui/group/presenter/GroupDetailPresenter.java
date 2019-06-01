package com.art.recruitment.artperformance.ui.group.presenter;

import com.art.recruitment.artperformance.api.GroupService;
import com.art.recruitment.artperformance.api.HomeService;
import com.art.recruitment.artperformance.bean.group.ActorIdBean;
import com.art.recruitment.artperformance.bean.group.ActorLikesBean;
import com.art.recruitment.artperformance.bean.group.GroupDetailBean;
import com.art.recruitment.artperformance.bean.home.RecruitmentInforBean;
import com.art.recruitment.artperformance.ui.group.contract.GroupDetailContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

public class GroupDetailPresenter extends BasePresenter<GroupDetailContract> {
    /**
     * 获取招募详情
     *
     */
    public void recuitDetail(int recruitmentId) {

        Api.
                observable(Api.getService(GroupService.class).actorsDetail(recruitmentId)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<GroupDetailBean.DataBean, GroupDetailBean>() {
                    @Override
                    protected void _onSuccess(GroupDetailBean.DataBean bean, String successMessage) {
                        mView.returnGroupDetailBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, GroupDetailBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     *根据群演ID获取影虎信息
     */
    public void actorID(int actorId) {

        Api.
                observable(Api.getService(GroupService.class).actorID(actorId)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<ActorIdBean.DataBean, ActorIdBean>() {
                    @Override
                    protected void _onSuccess(ActorIdBean.DataBean bean, String successMessage) {
                        mView.returnActorIdBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, ActorIdBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 点赞
     */
    public void actorsLikes(int actorId) {
        Api.
                observable(Api.getService(GroupService.class).actorsLikes(actorId)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<ActorLikesBean.DataBean, ActorLikesBean>() {
                    @Override
                    protected void _onSuccess(ActorLikesBean.DataBean bean, String successMessage) {
                        mView.returbActorLikesBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, ActorLikesBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }
}
