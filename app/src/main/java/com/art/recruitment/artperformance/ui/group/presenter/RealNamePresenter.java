package com.art.recruitment.artperformance.ui.group.presenter;

import com.art.recruitment.artperformance.api.GroupService;
import com.art.recruitment.artperformance.bean.group.RealNameBean;
import com.art.recruitment.artperformance.bean.group.ReleaseRecruitmentbBean;
import com.art.recruitment.artperformance.ui.group.contract.RealNameContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RealNamePresenter extends BasePresenter<RealNameContract> {

    /**
     * 实名认证
     *
     */
    public void realName(String str) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        Api.
                observable(Api.getService(GroupService.class).realName(body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<RealNameBean.DataBean, RealNameBean>() {
                    @Override
                    protected void _onSuccess(RealNameBean.DataBean bean, String successMessage) {
                        mView.returnRealNameBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, RealNameBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

}
