package com.art.recruitment.artperformance.ui.group.contract;

import com.art.recruitment.artperformance.bean.group.ApplyListBean;
import com.art.recruitment.artperformance.bean.group.CencelHiringBean;
import com.art.recruitment.artperformance.bean.group.HiringBean;
import com.art.recruitment.common.base.BaseView;


public interface EmploymentContract extends BaseView {

    /**
     * 报名列表
     */
    void returnApplyListBean(ApplyListBean.DataBean bean);

    /**
     * 录用
     */
    void returnHiringBean(HiringBean.DataBean bean);

    /**
     * 取消录用
     */
    void returnCencelHiringBean(CencelHiringBean.DataBean bean);
}
