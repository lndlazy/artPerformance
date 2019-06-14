package com.art.recruitment.artperformance.ui.login.contract;

import com.art.recruitment.artperformance.bean.im.ImUserBean;
import com.art.recruitment.artperformance.bean.login.LoginBean;
import com.art.recruitment.artperformance.bean.login.TokenBean;
import com.art.recruitment.artperformance.ui.login.ThirdLoginEntry;
import com.art.recruitment.common.base.BaseView;

public interface LoginContract extends BaseView {

    /**
     * 返回Token bean
     *
     */
    void returnTokenBean(TokenBean.DataBean bean);

    /**
     *获取环信用户信息
     */
    void returnImUserBean(ImUserBean.DataBean bean);

    void returnThirdLogin(ThirdLoginEntry.DataBean thirdLoginBean);
}
