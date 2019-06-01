package com.art.recruitment.artperformance.api;

import com.art.recruitment.artperformance.bean.group.ActorIdBean;
import com.art.recruitment.artperformance.bean.group.ActorLikesBean;
import com.art.recruitment.artperformance.bean.group.ApplyListBean;
import com.art.recruitment.artperformance.bean.group.CencelHiringBean;
import com.art.recruitment.artperformance.bean.group.CityBean;
import com.art.recruitment.artperformance.bean.group.GroupDetailBean;
import com.art.recruitment.artperformance.bean.group.GroupListBean;
import com.art.recruitment.artperformance.bean.group.HiringBean;
import com.art.recruitment.artperformance.bean.group.RealNameBean;
import com.art.recruitment.artperformance.bean.group.RecruitmentEditBean;
import com.art.recruitment.artperformance.bean.group.ReleaseRecruitmentbBean;
import com.art.recruitment.artperformance.bean.group.StatusBean;
import com.art.recruitment.artperformance.bean.mine.MineRecruitBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupService {

    /**
     * 发布招募
     */
    @POST(ApiUrls.RECRUITMENT_PUBLISH)
    Observable<ReleaseRecruitmentbBean> releaseRecruitmen(@Body RequestBody body);

    /**
     * 招募编辑
     */
    @PUT(ApiUrls.RECRUIT_DETAIL)
    Observable<RecruitmentEditBean> recruitmentEdit(@Path("recruitmentId") int recruitmentId,
                                                    @Body RequestBody body);

    /**
     * 实名认证
     */
    @POST(ApiUrls.AUTHENTICATION_REALNAME)
    Observable<RealNameBean> realName(@Body RequestBody body);

    /**
     * 发布招募
     */
    @GET(ApiUrls.CITI_LIST)
    Observable<CityBean> cityList();

    /**
     * 获取群演列表
     */
    @GET(ApiUrls.ACTORS)
    Observable<GroupListBean> actorsList(@Query("maxAge") int maxAge,
                                         @Query("minAge") int minAge,
                                         @Query("cityId") int cityId,
                                         @Query("gender") int gender,
                                         @Query("page") int page,
                                         @Query("size") int size,
                                         @Query("sort") String sort);

    /**
     * 获取群演列表
     */
    @GET(ApiUrls.ACTORS)
    Observable<GroupListBean> actorsSearchList(@Query("actorName") String actorName,
                                                 @Query("page") int page,
                                                 @Query("size") int size,
                                                 @Query("sort") String sort);

    /**
     * 获取群演详情
     */
    @GET(ApiUrls.ACTORS_DETAIL)
    Observable<GroupDetailBean> actorsDetail(@Path("actorId") int actorId);

    /**
     * 点赞
     */
    @POST(ApiUrls.ACTOR_LIKES)
    Observable<ActorLikesBean> actorsLikes(@Path("actorId") int actorId);

    /**
     * 获取当前认证状态
     */
    @GET(ApiUrls.STATUS)
    Observable<StatusBean> status();

    /**
     * 报名列表
     */
    @GET(ApiUrls.APPLY_LIST)
    Observable<ApplyListBean> applyList(@Path("recruitmentId") int recruitmentId,
                                        @Query("hireState") int hireState);

    /**
     * 录用
     */
    @POST(ApiUrls.HIRING)
    Observable<HiringBean> hiring(@Path("recruitmentId") int recruitmentId,
                                  @Path("applyId") int applyId,
                                  @Query("ignoreRecruitNumber") boolean ignoreRecruitNumber);

    /**
     * 取消录用
     */
    @DELETE(ApiUrls.CANCEL_HIRED)
    Observable<CencelHiringBean> cencelHiring(@Path("recruitmentId") int recruitmentId,
                                              @Path("applyId") int applyId);

    /**
     * 根据群演ID获取用户信息
     */
    @GET(ApiUrls.ACTORID)
    Observable<ActorIdBean> actorID(@Path("actorId") int actorId);

    /**
     * 群聊
     */
    @POST(ApiUrls.CHAT_GROUPS)
    Observable<MineRecruitBean> chatGroups(@Path("recruitmentId") int recruitmentId);

}
