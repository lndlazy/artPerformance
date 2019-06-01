package com.art.recruitment.artperformance.ui.dynamic.presenter;

import com.art.recruitment.artperformance.api.DynamicService;
import com.art.recruitment.artperformance.bean.dynamic.DeleteDynamicBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicCommentBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicCommentsBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicDetailBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicLikesBean;
import com.art.recruitment.artperformance.ui.dynamic.contract.DynamicDataContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DynamicDataPresenter extends BasePresenter<DynamicDataContract> {

    /**
     * 动态圈列表
     */
    public void dynamicDetail(int position) {

        Api.
                observable(Api.getService(DynamicService.class).dynamicDetail(position)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<DynamicDetailBean.DataBean, DynamicDetailBean>() {
                    @Override
                    protected void _onSuccess(DynamicDetailBean.DataBean bean, String successMessage) {
                        mView.returnDynamicDataBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, DynamicDetailBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 动态圈列表评论
     */
    public void dynamicComments(int position, int page, int size, String sort) {

        Api.
                observable(Api.getService(DynamicService.class).dynamicComments(position, page, size, sort)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<DynamicCommentsBean.DataBean, DynamicCommentsBean>() {
                    @Override
                    protected void _onSuccess(DynamicCommentsBean.DataBean bean, String successMessage) {
                        mView.returnDynamicCommentsBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, DynamicCommentsBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 动态圈发表评论
     */
    public void dynamicComment(int position, String codeStr) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), codeStr);

        Api.
                observable(Api.getService(DynamicService.class).dynamicComment(position, body)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<DynamicCommentBean.DataBean, DynamicCommentBean>() {
                    @Override
                    protected void _onSuccess(DynamicCommentBean.DataBean bean, String successMessage) {
                        mView.returnDynamicCommentBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, DynamicCommentBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 删除动态
     */
    public void deleteDynamic(int position) {

        Api.
                observable(Api.getService(DynamicService.class).deleteDynamic(position)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<DeleteDynamicBean.DataBean, DeleteDynamicBean>() {
                    @Override
                    protected void _onSuccess(DeleteDynamicBean.DataBean bean, String successMessage) {
                        mView.returnDeleteDynamicBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, DeleteDynamicBean.DataBean bean) {
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
