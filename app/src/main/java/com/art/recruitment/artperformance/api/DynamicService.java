package com.art.recruitment.artperformance.api;

import com.art.recruitment.artperformance.bean.dynamic.DeleteDynamicBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicCommentBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicCommentsBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicDetailBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicLikesBean;
import com.art.recruitment.artperformance.bean.dynamic.DynamicListBean;
import com.art.recruitment.artperformance.bean.dynamic.ReleaseDynamicBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DynamicService {
    /**
     * 动态圈列表
     */
    @GET(ApiUrls.DYNAMIC_LIST)
    Observable<DynamicListBean> dynamicList(@Query("page") int page,
                                            @Query("size") int size,
                                            @Query("sort") String sort);

    /**
     * 发布动态圈
     */
    @POST(ApiUrls.DYNAMIC_LIST)
    Observable<ReleaseDynamicBean> releaseDynamic(@Body RequestBody body);

    /**
     * 动态圈详情
     */
    @GET(ApiUrls.DYNAMIC_DETAIL)
    Observable<DynamicDetailBean> dynamicDetail(@Path("dynamicCircleId") int dynamicCircleId);

    /**
     * 动态圈评论列表
     */
    @GET(ApiUrls.DYNAMIC_DETAIL_COMMENTS)
    Observable<DynamicCommentsBean> dynamicComments(@Path("dynamicCircleId") int dynamicCircleId,
                                                    @Query("page") int page,
                                                    @Query("size") int size,
                                                    @Query("sort") String sort);

    /**
     * 动态圈评论
     */
    @POST(ApiUrls.DYNAMIC_DETAIL_COMMENTS)
    Observable<DynamicCommentBean> dynamicComment(@Path("dynamicCircleId") int dynamicCircleId,
                                                  @Body RequestBody body);

    /**
     * 删除动态
     */
    @DELETE(ApiUrls.DYNAMIC_DETAIL)
    Observable<DeleteDynamicBean> deleteDynamic(@Path("dynamicCircleId") int dynamicCircleId);

    /**
     * 点赞
     */
    @POST(ApiUrls.DYNAMIC_LIKES)
    Observable<DynamicLikesBean> dynamicLikes(@Path("dynamicCircleId") int dynamicCircleId);
}
