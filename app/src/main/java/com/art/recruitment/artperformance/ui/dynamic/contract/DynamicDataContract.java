package com.art.recruitment.artperformance.ui.dynamic.contract;

import com.art.recruitment.artperformance.bean.dynamic.DeleteDynamicBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicCommentBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicCommentsBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicDetailBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicLikesBean;
import com.art.recruitment.common.base.BaseView;

public interface DynamicDataContract extends BaseView {
    /**
     * 动态圈详情
     */
    void returnDynamicDataBean(DynamicDetailBean.DataBean bean);

    /**
     * 动态圈详情评论
     */
    void returnDynamicCommentsBean(DynamicCommentsBean.DataBean bean);

    /**
     * 动态圈发表评论
     */
    void returnDynamicCommentBean(DynamicCommentBean.DataBean bean);

    /**
     * 删除动态
     */
    void returnDeleteDynamicBean(DeleteDynamicBean.DataBean bean);

    /**
     * 动态圈点赞
     */
    void returnDynamicLikesBean(DynamicLikesBean.DataBean bean);
}
