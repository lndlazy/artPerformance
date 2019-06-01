package com.art.recruitment.artperformance.ui.mine.presenter;

import com.art.recruitment.artperformance.api.MineService;
import com.art.recruitment.artperformance.bean.group.RealNameBean;
import com.art.recruitment.artperformance.bean.mine.MineBean;
import com.art.recruitment.artperformance.ui.mine.contract.MineContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;
public class MinePresenter extends BasePresenter<MineContract> {

    /**
     * 获取个人资料
     */
    public void getPersonalData() {

        Api.
                observable(Api.getService(MineService.class).getPersonalData()).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<MineBean.DataBean, MineBean>() {
                    @Override
                    protected void _onSuccess(MineBean.DataBean bean, String successMessage) {
                        mView.returnMineDataBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, MineBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }
}
