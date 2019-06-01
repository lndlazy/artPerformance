package com.art.recruitment.artperformance.ui.mine.presenter;

import com.art.recruitment.artperformance.api.MineService;
import com.art.recruitment.artperformance.bean.mine.MineFecruitmentBean;
import com.art.recruitment.artperformance.bean.mine.RecruitmentOptBean;
import com.art.recruitment.artperformance.ui.mine.contract.MineFecruitmentContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

public class MineFecruitmentPresenter extends BasePresenter<MineFecruitmentContract> {

    /**
     * 我的招募
     */
    public void mineRecruitmentList(int page, int size, String sort) {

        Api.
                observable(Api.getService(MineService.class).mineRecruitmentList(page, size, sort)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<MineFecruitmentBean.DataBean, MineFecruitmentBean>() {
                    @Override
                    protected void _onSuccess(MineFecruitmentBean.DataBean bean, String successMessage) {
                        mView.returnMineFecruitmentBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, MineFecruitmentBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 上下架
     */
    public void recruitmentOpt(int recruitmentId, String opt) {

        Api.
                observable(Api.getService(MineService.class).recruitmentOpt(recruitmentId, opt)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<RecruitmentOptBean.DataBean, RecruitmentOptBean>() {
                    @Override
                    protected void _onSuccess(RecruitmentOptBean.DataBean bean, String successMessage) {
                        mView.returnRecruitmentOptBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, RecruitmentOptBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }
}
