package com.art.recruitment.artperformance.ui.group.contract;

import com.art.recruitment.artperformance.bean.group.GroupListBean;
import com.art.recruitment.common.base.BaseView;

public interface SearchContract extends BaseView {
    /**
     * 获取群演列表
     */
    void returbGroupListBean(GroupListBean.DataBean bean);
}
