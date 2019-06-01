package com.art.recruitment.artperformance.ui.login.contract;

import com.art.recruitment.artperformance.bean.im.ImUserBean;
import com.art.recruitment.artperformance.bean.login.RegisterBean;
import com.art.recruitment.artperformance.bean.login.ResetPasswordBean;
import com.art.recruitment.artperformance.bean.login.VerificationCodeBean;
import com.art.recruitment.common.base.BaseView;

public interface RegisterContract extends BaseView {

    /**
     * 获取验证码 bean
     * @param bean 验证码 bean
     */
    void returnVerificationCodeBean(VerificationCodeBean.DataBean bean);

    /**
     * 返回注册 bean
     * @param bean 注册 bean
     */
    void returnRegisterBean(RegisterBean.DataBean bean);

    /**
     * 重置密码 bean
     * @param bean 重置密码 bean
     */
    void returnResetPasswordBean(ResetPasswordBean.DataBean bean);

    /**
     *获取环信用户信息
     */
    void returnImUserBean(ImUserBean.DataBean bean);
}
