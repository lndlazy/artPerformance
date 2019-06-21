package com.art.recruitment.artperformance.ui.home.contract;

import com.art.recruitment.artperformance.bean.home.BannerBean;
import com.art.recruitment.artperformance.bean.home.CitiSearch;
import com.art.recruitment.artperformance.bean.home.LogoBean;
import com.art.recruitment.artperformance.bean.home.RecruitListBean;
import com.art.recruitment.common.base.BaseView;

import java.util.List;

public interface HomeContract extends BaseView {

    /**
     * 返回Banner bean
     *
     * @param bean BannerBean bean
     */
    void returnBannerBean(List<BannerBean.DataBean> bean);

    /**
     * 返回招募列表 bean
     *
     * @param bean BannerBean bean
     * @param page
     */
    void returnRecruitListBean(RecruitListBean.DataBean bean, int page);
    void returnRecruitListBean(RecruitListBean.DataBean bean);

    /**
     * 城市搜索 bean
     *
     * @param bean BannerBean bean
     * @param isAutoLocation
     */
    void returnCitiSearchBean(CitiSearch.DataBean bean, boolean isAutoLocation);

    void returnLogoUrl(LogoBean.DataBean bean);

}
