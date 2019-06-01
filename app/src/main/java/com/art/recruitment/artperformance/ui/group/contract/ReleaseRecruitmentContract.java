package com.art.recruitment.artperformance.ui.group.contract;

import com.art.recruitment.artperformance.bean.group.RecruitmentEditBean;
import com.art.recruitment.artperformance.bean.group.ReleaseRecruitmentbBean;
import com.art.recruitment.artperformance.bean.home.RecruitmentInforBean;
import com.art.recruitment.common.base.BaseView;

public interface ReleaseRecruitmentContract extends BaseView {

    /**
     * 发布招募 bean
     *
     * @param bean ReleaseRecruitmentbBean bean
     */
    void returnReleaseRecruitmentBean(ReleaseRecruitmentbBean.DataBean bean);

    /**
     * 招募编辑
     */
    void returnRecruitmentEdieBean(RecruitmentEditBean.DataBean bean);

    /**
     * 招募详情 bean
     */
    void returnRecruitInforBean(RecruitmentInforBean.DataBean bean);
}
