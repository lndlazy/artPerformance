package com.art.recruitment.artperformance.ui.mine.contract;

import com.art.recruitment.artperformance.bean.mine.MineDynamicBean;
import com.art.recruitment.common.base.BaseView;

public interface MineDynamicContract extends BaseView {
    /**
     * 我的动态圈列表
     */
    void returnMineDynamicBean(MineDynamicBean.DataBean bean);
}
