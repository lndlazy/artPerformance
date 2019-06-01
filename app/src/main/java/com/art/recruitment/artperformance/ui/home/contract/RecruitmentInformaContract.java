package com.art.recruitment.artperformance.ui.home.contract;

import com.art.recruitment.artperformance.bean.home.ApplyBean;
import com.art.recruitment.artperformance.bean.home.RecruitmentInforBean;
import com.art.recruitment.common.base.BaseView;

public interface RecruitmentInformaContract extends BaseView {

    /**
     * 返回招募列表 bean
     *
     * @param bean BannerBean bean
     */
    void returnRecruitInforBean(RecruitmentInforBean.DataBean bean);

    /**
     * 报名
     */
    void returnApplyBean(ApplyBean.DataBean bean);
}
