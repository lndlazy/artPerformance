package com.art.recruitment.artperformance.ui.mine.contract;

import com.art.recruitment.artperformance.bean.mine.FeedbackBean;
import com.art.recruitment.common.base.BaseView;

public interface FeedbackContract extends BaseView {

    /**
     * 意见反馈
     */
    void returnFeedbackBean(FeedbackBean.DataBean bean);
}
