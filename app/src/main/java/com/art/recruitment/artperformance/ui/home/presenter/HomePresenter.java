package com.art.recruitment.artperformance.ui.home.presenter;

import com.art.recruitment.artperformance.api.HomeService;
import com.art.recruitment.artperformance.api.LoginService;
import com.art.recruitment.artperformance.bean.home.BannerBean;
import com.art.recruitment.artperformance.bean.home.CitiSearch;
import com.art.recruitment.artperformance.bean.home.RecruitListBean;
import com.art.recruitment.artperformance.bean.login.TokenBean;
import com.art.recruitment.artperformance.ui.home.contract.HomeContract;
import com.art.recruitment.common.base.BasePresenter;
import com.art.recruitment.common.baserx.RxSubscriber;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.http.error.ErrorType;
import com.art.recruitment.common.http.mode.RequestMode;
import com.orhanobut.logger.Logger;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HomePresenter extends BasePresenter<HomeContract> {

    /**
     * 获取Banner
     *
     */
    public void getBanner() {

        Api.
                observable(Api.getService(HomeService.class).getBanner()).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<List<BannerBean.DataBean>, BannerBean>() {
                    @Override
                    protected void _onSuccess(List<BannerBean.DataBean> bean, String successMessage) {
                        mView.returnBannerBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, List<BannerBean.DataBean> bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 获取招募列表
     *
     */
    public void recuitList(int citycode, String keyword, final int page, int size, String sort) {

        Api.
                observable(Api.getService(HomeService.class).recuitList(citycode, keyword, page, size, sort)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<RecruitListBean.DataBean, RecruitListBean>() {
                    @Override
                    protected void _onSuccess(RecruitListBean.DataBean bean, String successMessage) {
                        mView.returnRecruitListBean(bean, page);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, RecruitListBean.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }

    /**
     * 搜索城市
     *
     */
    public void citiSearch(String cityName) {

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), cityName);
        Api.
                observable(Api.getService(HomeService.class).citiSearch(cityName)).
                presenter(this).
                requestMode(RequestMode.SINGLE).
                showLoading(true).
                doRequest(new RxSubscriber<CitiSearch.DataBean, CitiSearch>() {
                    @Override
                    protected void _onSuccess(CitiSearch.DataBean bean, String successMessage) {
                        mView.returnCitiSearchBean(bean);
                    }

                    @Override
                    protected void _onError(ErrorType errorType, int errorCode, String message, CitiSearch.DataBean bean) {
                        mView.showErrorTip(errorType, errorCode, message);
                    }
                });
    }
}