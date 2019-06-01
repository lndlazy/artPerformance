package com.art.recruitment.artperformance.ui.group.contract;

import com.art.recruitment.artperformance.bean.group.ActorLikesBean;
import com.art.recruitment.artperformance.bean.group.CityBean;
import com.art.recruitment.artperformance.bean.group.GroupListBean;
import com.art.recruitment.common.base.BaseView;

import java.util.List;

public interface GroupFragmentContract extends BaseView {

    /**
     * 获取城市列表
     */
    void returbCityListBean(List<CityBean.DataBean> bean);

    /**
     * 获取群演列表
     */
    void returbGroupListBean(GroupListBean.DataBean bean);

    /**
     * 点赞
     */
    void returbActorLikesBean(ActorLikesBean.DataBean bean);
}
