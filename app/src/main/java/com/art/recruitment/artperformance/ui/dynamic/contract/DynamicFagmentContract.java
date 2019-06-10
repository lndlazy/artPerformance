package com.art.recruitment.artperformance.ui.dynamic.contract;

import com.art.recruitment.artperformance.bean.dynamic.DynamicLikesBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicListBean;
import com.art.recruitment.common.base.BaseView;

public interface DynamicFagmentContract extends BaseView {

    /**
     * 动态圈列表
     */
    void returnDynamicListBean(DynamicListBean.DataBean bean, int page);

    /**
     * 动态圈点赞
     */
    void returnDynamicLikesBean(DynamicLikesBean.DataBean bean);
}
