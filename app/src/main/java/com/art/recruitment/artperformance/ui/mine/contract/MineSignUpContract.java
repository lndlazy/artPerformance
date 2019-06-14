package com.art.recruitment.artperformance.ui.mine.contract;

import com.art.recruitment.artperformance.bean.mine.CancelRecruitmentBean;
import com.art.recruitment.artperformance.bean.mine.MineRecruitBean;
import com.art.recruitment.artperformance.bean.mine.MineSignUpBean;
import com.art.recruitment.common.base.BaseView;

public interface MineSignUpContract extends BaseView {
    /**
     * 我的报名列表
     */
    void returnApplyListBean(MineSignUpBean.DataBean bean, int page);

    /**
     * 取消报名
     */
    void returnCancelRecruitmentBean(CancelRecruitmentBean.DataBean bean);

    void returnChatGroupsBean(MineRecruitBean.DataBean bean);
}
