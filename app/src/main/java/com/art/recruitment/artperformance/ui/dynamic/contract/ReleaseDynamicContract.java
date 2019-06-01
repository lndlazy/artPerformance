package com.art.recruitment.artperformance.ui.dynamic.contract;

import com.art.recruitment.artperformance.bean.dynamic.ReleaseDynamicBean;
import com.art.recruitment.artperformance.bean.mine.OssBean;
import com.art.recruitment.common.base.BaseView;

public interface ReleaseDynamicContract extends BaseView {
    /**
     * 发布动态圈
     */
    void returnReleaseDynamicBean(ReleaseDynamicBean.DataBean bean);

    /**
     * OSS签名
     */
    void returnSignaTureBean(String bean);

    /**
     * 签名信息
     */
    void returnOssBean(OssBean.DataBean bean);
}
