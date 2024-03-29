package com.art.recruitment.artperformance.ui.mine.contract;

import com.art.recruitment.artperformance.bean.mine.MineFecruitmentBean;
import com.art.recruitment.artperformance.bean.mine.RecruitmentOptBean;
import com.art.recruitment.common.base.BaseView;

public interface MineFecruitmentContract extends BaseView {
    /**
     * 我的招募
     * @param bean
     * @param page
     */
    void returnMineFecruitmentBean(MineFecruitmentBean.DataBean bean, int page);

    /**
     * 上下架
     */
    void returnRecruitmentOptBean(RecruitmentOptBean.DataBean bean);
}
