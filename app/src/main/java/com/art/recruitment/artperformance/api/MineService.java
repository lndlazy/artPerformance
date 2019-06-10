package com.art.recruitment.artperformance.api;

import com.art.recruitment.artperformance.bean.mine.CancelRecruitmentBean;
import com.art.recruitment.artperformance.bean.mine.ConsummateInfoBean;
import com.art.recruitment.artperformance.bean.mine.FeedbackBean;
import com.art.recruitment.artperformance.bean.mine.MineBean;
import com.art.recruitment.artperformance.bean.mine.MineDynamicBean;
import com.art.recruitment.artperformance.bean.mine.MineFecruitmentBean;
import com.art.recruitment.artperformance.bean.mine.MineSignUpBean;
import com.art.recruitment.artperformance.bean.mine.OssBean;
import com.art.recruitment.artperformance.bean.mine.PathUrlBean;
import com.art.recruitment.artperformance.bean.mine.RecruitmentOptBean;
import com.art.recruitment.artperformance.bean.mine.SignaTureBean;
import com.art.recruitment.common.base.BaseBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MineService {

    /**
     * 获取个人资料
     */
    @GET(ApiUrls.ACCOUNTS_MY)
    Observable<MineBean> getPersonalData();

    /**
     * 完善个人资料
     */
    @PUT(ApiUrls.ACCOUNTS_CONSUMMATE)
    Observable<ConsummateInfoBean> consummateInfo(@Body RequestBody body);

    /**
     * 完善3项基本信息
     */
    @PUT(ApiUrls.ACCOUNTS_CONSUMMATE3)
    Observable<ConsummateInfoBean> consummateInfo3(@Body RequestBody body);

    /**
     * OSS转换文件地址
     */
    @FormUrlEncoded
    @POST(ApiUrls.PATH_URL)
    Observable<BaseBean<String>> pathUrl(@Field("path") String path);

    /**
     * OSS签名
     */
    @POST(ApiUrls.SIGNATURE)
    Observable<SignaTureBean> signaTure(@Query("content") String content);

    /**
     * 签名信息
     */
    @GET(ApiUrls.OSS)
    Observable<OssBean> oss();

    /**
     * 我的报名列表
     */
    @GET(ApiUrls.RECRUITMENT_APPLY_MY)
    Observable<MineSignUpBean> applyList(@Query("page") int page,
                                         @Query("size") int size,
                                         @Query("sort") String sort);

    /**
     * 意见反馈
     */
    @POST(ApiUrls.FEEDBACK)
    Observable<FeedbackBean> feedBack(@Body RequestBody body);

    /**
     * 取消报名
     */
    @DELETE(ApiUrls.CANCEL_RECRUITMENT)
    Observable<CancelRecruitmentBean> cancelRecruitment(@Path("applyId") int applyId,
                                                        @Path("recruitmentId") String recruitmentId);

    /**
     * 我的招募
     */
    @GET(ApiUrls.RECRUITMENT_MY)
    Observable<MineFecruitmentBean> mineRecruitmentList(@Query("page") int page,
                                                        @Query("size") int size,
                                                        @Query("sort") String sort);

    /**
     * 上下架
     */
    @PUT(ApiUrls.RECRUITMENT_OPT)
    Observable<RecruitmentOptBean> recruitmentOpt(@Path("recruitmentId") String recruitmentId,
                                                  @Path("opt") String opt);

    /**
     * 我的动态圈列表
     */
    @GET(ApiUrls.MINE_DYNAMIC_LIST)
    Observable<MineDynamicBean> mineDynamicList(@Query("page") int page,
                                                @Query("size") int size,
                                                @Query("sort") String sort);

}
