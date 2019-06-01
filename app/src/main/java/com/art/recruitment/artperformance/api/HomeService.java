package com.art.recruitment.artperformance.api;

import com.art.recruitment.artperformance.bean.home.ApplyBean;
import com.art.recruitment.artperformance.bean.home.BannerBean;
import com.art.recruitment.artperformance.bean.home.CitiSearch;
import com.art.recruitment.artperformance.bean.home.CitisBean;
import com.art.recruitment.artperformance.bean.home.RecruitListBean;
import com.art.recruitment.artperformance.bean.home.RecruitmentInforBean;
import com.art.recruitment.artperformance.bean.login.TokenBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HomeService {

    /**
     * banner图接口
     */
    @GET(ApiUrls.HOME_BANNER)
    Observable<BannerBean> getBanner();

    /**
     * banner图详情接口
     */
    @GET(ApiUrls.BANNER_DETAIL)
    Observable<BannerBean> getBannerDetail();

    /**
     * 搜索城市
     */
    @FormUrlEncoded
    @POST(ApiUrls.CITI_SEARCH)
    Observable<CitiSearch> citiSearch(@Field("cityName") String cityName);

    /**
     * 招募列表接口
     */
    @GET(ApiUrls.RECRUIT_LIST)
    Observable<RecruitListBean> recuitList(@Query("cityId") int cityId,
                                           @Query("keyword") String keyword,
                                           @Query("page") int page,
                                           @Query("size") int size,
                                           @Query("sort") String sort);

    /**
     * 招募列表详情接口
     */
    @GET(ApiUrls.RECRUIT_DETAIL)
    Observable<RecruitmentInforBean> recuitDetail(@Path("recruitmentId") int recruitmentId);

    /**
     * 招募列表详情接口
     */
    @GET(ApiUrls.APPLY_LIST)
    Observable<ApplyBean> apply(@Path("recruitmentId") int recruitmentId);
}