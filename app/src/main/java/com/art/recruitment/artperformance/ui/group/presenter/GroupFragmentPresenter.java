package com.art.recruitment.artperformance.ui.group.presenter;

import com.art.recruitment.artperformance.api.GroupService;
import com.art.recruitment.artperformance.bean.group.ActorLikesBean;
import com.art.recruitment.artperformance.bean.group.CityBean;
import com.art.recruitment.artperformance.bean.group.GroupListBean;
import com.art.recruitment.artperformance.bean.group.RealNameBean;
import com.art.recruitment.artperformance.ui.group.contract.GroupFragmentContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;
import com.orhanobut.logger.Logger;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class GroupFragmentPresenter extends BasePresenter<GroupFragmentContract> {

    /**
     * 获取城市列表
     */
    public void cityList() {
        Api.
                observable(Api.getService(GroupService.class).cityList()).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<List<CityBean.DataBean>, CityBean>() {
                    @Override
                    protected void _onSuccess(List<CityBean.DataBean> bean, String successMessage) {
                        mView.returbCityListBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, List<CityBean.DataBean> bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 获取群演列表
     */
    public void actorsList(String maxAge, String minAge, int cityId, int gender, int page, int size, String sort) {
        Api.
                observable(Api.getService(GroupService.class).actorsList(maxAge, minAge, cityId, gender, page, size, sort)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<GroupListBean.DataBean, GroupListBean>() {
                    @Override
                    protected void _onSuccess(GroupListBean.DataBean bean, String successMessage) {
                        mView.returbGroupListBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, GroupListBean.DataBean bean) {
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

                        Logger.d("errorType::" + errorType + ",errorCode::" + errorCode
                                + ",message::" + message);

                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }
}
