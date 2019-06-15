package com.art.recruitment.artperformance.ui.login.contract;

import com.art.recruitment.artperformance.bean.im.ImUserBean;
import com.art.recruitment.artperformance.bean.login.VerificationCodeBean;
import com.art.recruitment.artperformance.ui.login.ThirdBindResultEntry;
import com.art.recruitment.common.base.BaseView;
import com.art.recruitment.common.http.error.ErrorType;

/**
 * Created by linaidao on 2019/6/15.
 */

public interface ThirdBindContract extends BaseView {


    void returnVerificationCodeBean(VerificationCodeBean.DataBean verificationCodeBean);

    void returnBindInfo(ThirdBindResultEntry.DataBean bindResult);

    void returnImUserBean(ImUserBean.DataBean tokenBean);
}
