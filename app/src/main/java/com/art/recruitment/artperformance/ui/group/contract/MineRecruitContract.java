package com.art.recruitment.artperformance.ui.group.contract;

import com.art.recruitment.artperformance.bean.mine.MineRecruitBean;
import com.art.recruitment.common.base.BaseView;

public interface MineRecruitContract extends BaseView {
    /**
     * 群聊
     */
    void returnChatGroupsBean(MineRecruitBean.DataBean bean);
}
