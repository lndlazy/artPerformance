package com.art.recruitment.artperformance.ui.group.presenter;

import com.art.recruitment.artperformance.api.GroupService;
import com.art.recruitment.artperformance.bean.group.ApplyListBean;
import com.art.recruitment.artperformance.bean.group.CencelHiringBean;
import com.art.recruitment.artperformance.bean.group.GroupDetailBean;
import com.art.recruitment.artperformance.bean.group.HiringBean;
import com.art.recruitment.artperformance.ui.group.contract.EmploymentContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;
import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

public class EmploymentPresenter extends BasePresenter<EmploymentContract> {
    /**
     * 报名列表
     *
     */
    public void applyList(int recruitmentId, int hireState) {

        Api.
                observable(Api.getService(GroupService.class).applyList(recruitmentId, hireState)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<ApplyListBean.DataBean, ApplyListBean>() {
                    @Override
                    protected void _onSuccess(ApplyListBean.DataBean bean, String successMessage) {
                        if (bean.getContent() != null){
                            mView.returnApplyListBean(bean);
                        } else {
                            ToastUtils.showShort("暂时没有数据");
                        }
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, ApplyListBean.DataBean bean) {
                        if (bean != null){
                            mView.showErrorTip(errorType, errorCode, message);
                        } else if (!message.equals("未知错误")){
                            ToastUtils.showShort(message);
                        }
                    }
                });
    }

    /**
     * 录用
     *
     */
    public void hiring(int recruitmentId, int applyId, boolean ignoreRecruitNumber) {

        Api.
                observable(Api.getService(GroupService.class).hiring(recruitmentId, applyId, ignoreRecruitNumber)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<HiringBean.DataBean, HiringBean>() {
                    @Override
                    protected void _onSuccess(HiringBean.DataBean bean, String successMessage) {

                        mView.returnHiringBean(bean);

                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, HiringBean.DataBean bean) {

                        mView.showErrorTip(errorType, errorCode, message);

                    }
                });
    }

    /**
     * 取消录用
     *
     */
    public void cencelHiring(int recruitmentId, int applyId) {

        Api.
                observable(Api.getService(GroupService.class).cencelHiring(recruitmentId, applyId)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<CencelHiringBean.DataBean, CencelHiringBean>() {
                    @Override
                    protected void _onSuccess(CencelHiringBean.DataBean bean, String successMessage) {

                        mView.returnCencelHiringBean(bean);

                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, CencelHiringBean.DataBean bean) {

                        mView.showErrorTip(errorType, errorCode, message);

                    }
                });
    }
}
