package com.art.recruitment.artperformance.ui;

import com.art.recruitment.artperformance.bean.group.CityBean;
import com.art.recruitment.artperformance.bean.group.StatusBean;
import com.art.recruitment.common.base.BaseView;

import java.util.List;

public interface MainContract extends BaseView {
    /**
     * 认证状态
     */
    void returbStatusBean(StatusBean.DataBean bean);
}
