package com.art.recruitment.artperformance.ui.login.contract;

import com.art.recruitment.artperformance.bean.login.StartUpBean;
import com.art.recruitment.common.base.BaseView;

public interface LaunchContract extends BaseView {

    /**
     * 开机启动页 bean
     *
     * @param bean TokenRequest bean
     */
    void returnStartUpBean(StartUpBean.DataBean bean);
}
