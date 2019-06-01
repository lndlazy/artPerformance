package com.art.recruitment.artperformance.ui.group.presenter;

import com.art.recruitment.artperformance.api.GroupService;
import com.art.recruitment.artperformance.bean.group.GroupListBean;
import com.art.recruitment.artperformance.ui.group.contract.SearchContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

public class SearchPresenter extends BasePresenter<SearchContract> {

    /**
     * 获取群演列表
     */
    public void actorsList(String actorName, int page, int size, String sort) {
        Api.
                observable(Api.getService(GroupService.class).actorsSearchList(actorName, page, size, sort)).
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
}
