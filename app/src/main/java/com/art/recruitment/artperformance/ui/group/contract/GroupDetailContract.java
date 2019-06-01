package com.art.recruitment.artperformance.ui.group.contract;

import com.art.recruitment.artperformance.bean.group.ActorIdBean;
import com.art.recruitment.artperformance.bean.group.ActorLikesBean;
import com.art.recruitment.artperformance.bean.group.GroupDetailBean;
import com.art.recruitment.common.base.BaseView;

public interface GroupDetailContract extends BaseView {

    /**
     * 群演详情
     */
    void returnGroupDetailBean(GroupDetailBean.DataBean bean);

    /**
     * 根据群演ID后去用户信息
     */
    void returnActorIdBean(ActorIdBean.DataBean bean);

    /**
     * 点赞
     */
    void returbActorLikesBean(ActorLikesBean.DataBean bean);
}
