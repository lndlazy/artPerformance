package com.art.recruitment.artperformance.ui.mine.presenter;

import com.art.recruitment.artperformance.api.MineService;
import com.art.recruitment.artperformance.bean.mine.FeedbackBean;
import com.art.recruitment.artperformance.ui.mine.contract.FeedbackContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FeedbackPresenter extends BasePresenter<FeedbackContract> {

    /**
     * 意见反馈
     */
    public void applyList(String string) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), string);

        Api.
                observable(Api.getService(MineService.class).feedBack(body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<FeedbackBean.DataBean, FeedbackBean>() {
                    @Override
                    protected void _onSuccess(FeedbackBean.DataBean bean, String successMessage) {
                        mView.returnFeedbackBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, FeedbackBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }
}
