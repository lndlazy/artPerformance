package com.art.recruitment.artperformance.ui.group.presenter;

import com.art.recruitment.artperformance.api.GroupService;
import com.art.recruitment.artperformance.bean.group.ActorIdBean;
import com.art.recruitment.artperformance.bean.mine.MineRecruitBean;
import com.art.recruitment.artperformance.ui.group.contract.MineRecruitContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;

public class MineRecruitPresenter extends BasePresenter<MineRecruitContract> {
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
