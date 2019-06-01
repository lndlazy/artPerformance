package com.art.recruitment.artperformance.ui.group.contract;

import com.art.recruitment.artperformance.bean.group.RealNameBean;
import com.art.recruitment.common.base.BaseView;

public interface RealNameContract extends BaseView {

    /**
     * 实名认证
     */
    void returnRealNameBean(RealNameBean.DataBean bean);
}
