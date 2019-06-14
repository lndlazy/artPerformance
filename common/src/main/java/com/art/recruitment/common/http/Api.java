package com.art.recruitment.common.http;

import android.support.annotation.NonNull;
import android.util.Log;

import com.art.recruitment.common.BuildConfig;
import com.art.recruitment.common.base.BaseApplication;
import com.art.recruitment.common.base.BaseBean;
import com.art.recruitment.common.http.config.ApiConfig;
import com.art.recruitment.common.http.config.RequestConfig;
import com.art.recruitment.common.http.interceptor.HeaderInterceptor;
import com.art.recruitment.common.http.interceptor.RewriteCacheControlInterceptor;
import com.art.recruitment.common.utils.NetWorkUtils;
import com.art.recruitment.common.view.ConverterFactory;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class Api {

    /**
     * 网络请求添加认证头
     */
    public static final int TYPE_HEADER = 1;


    public static final String HTTP_URL = "http://47.94.8.204:18080/";
//    public static final String HTTP_URL = "http://47.94.8.204:8082/";
    public static final String OSS_SIGNATURE = "oss/signature?content=";


    /**
     * retrofit--H5
     */
    public static final int TYPE_SHOP_H5_HEADER = 3;

    private static ApiConfig mApiConfig;

    private static List<Retrofit> mRetrofitList = new ArrayList<>();

    private static Retrofit mRetrofit = null;

    /*************************缓存设置*********************/
/*
   1. noCache 不使用缓存，全部走网络

    2. noStore 不使用缓存，也不存储缓存

    3. onlyIfCached 只使用缓存

    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言

    6. minFresh 设置有效时间，依旧如上

    7. FORCE_NETWORK 只走网络

    8. FORCE_CACHE 只走缓存*/

    /**
     * 设缓存有效期为两天
     */
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";


    /**
     * 设置Api的配置类，该方法请在Application中调用
     *
     * @param config
     */
    public static void setConfig(ApiConfig config) {
        mApiConfig = config;
        LogUtils.e("HostServer---set" + config.toString());
    }

    private static Retrofit getRetrofit(int headerType) {
        //Log拦截器
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        //网络缓存文件夹
        File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "cache");
        //100Mb;
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);

        RewriteCacheControlInterceptor cacheInterceptor = new RewriteCacheControlInterceptor();

        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(mApiConfig.getReadTimeOut(), TimeUnit.MILLISECONDS)
                .connectTimeout(mApiConfig.getConnectTimeOut(), TimeUnit.MILLISECONDS)
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .addInterceptor(new HeaderInterceptor(headerType))
                .addInterceptor(logInterceptor)
                .cache(cache)
                .build();
        LogUtils.e("HostServer---API" + mApiConfig.toString());

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        String url;
        switch (headerType) {
            case TYPE_HEADER: {
                url = mApiConfig.getHostServer();
            }
            break;
            default:
                url = mApiConfig.getHostServer();
        }


        return mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(ConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(HTTP_URL)
                .build();

    }


    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return NetWorkUtils.isNetConnected(BaseApplication.getAppContext()) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    public static <T> T getService(Class<T> clazz) {
        if (mRetrofitList.size() < 1 || mRetrofitList.get(0) == null) {
            mRetrofitList.add(0, getRetrofit(TYPE_HEADER));
            mRetrofitList.add(1, getRetrofit(TYPE_SHOP_H5_HEADER));
        }

        Log.e("getService: ", clazz.toString());

        int index;
        if ("com.coolbit.wallet.login.api.LoginService".equals(clazz.getName())) {
            index = 1;
        } else if ("com.coolbit.wallet.servicecenter.api.ServiceCenterService".equals(clazz.getName())) {
            index = 1;
        } else if ("com.coolbit.wallet.assets.api.AssetsService".equals(clazz.getName())) {
            index = 1;
        } else if ("com.coolbit.wallet.userinfo.api.UserInfoService".equals(clazz.getName())) {
            index = 1;
        } else if ("com.coolbit.wallet.assets.api.AssetsWallet2Service".equals(clazz.getName())) {
            index = 3;
        } else if ("com.baseapp.common.app.PublicService".equals(clazz.getName())) {
            index = 1;
        } else if ("com.coolbit.wallet.login.api.LoginWalletService".equals(clazz.getName())) {
            index = 0;
        } else {
            index = 0;
        }

        return mRetrofitList.get(index).create(clazz);
    }


    public static <T> T getImageService(Class<T> clazz) {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(mApiConfig.getReadTimeOut(), TimeUnit.MILLISECONDS)
                .connectTimeout(mApiConfig.getConnectTimeOut(), TimeUnit.MILLISECONDS)
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl("http://47.100.114.104:8667/")
                .build();
        return mRetrofit.create(clazz);
    }


    public static <T> T getH5Service(Class<T> clazz) {
        if (mRetrofitList.size() < 1 || mRetrofitList.get(0) == null) {
            mRetrofitList.add(0, getRetrofit(TYPE_HEADER));
            mRetrofitList.add(1, getRetrofit(TYPE_SHOP_H5_HEADER));
        }

        Log.e("getH5Service: ", clazz.toString());


        return mRetrofitList.get(2).create(clazz);
    }

    public static <T> T getHeaderService(Class<T> clazz) {

        if (mRetrofitList.size() < 2 || mRetrofitList.get(1) == null) {

            mRetrofitList.add(1, getRetrofit(TYPE_HEADER));
        }

        return mRetrofitList.get(1).create(clazz);
    }

    public static <R, T extends BaseBean<R>> RequestConfig observable(Observable<T> observable) {

        RequestConfig<R, T> mRequestConfig = new RequestConfig<R, T>();
        mRequestConfig.observable(observable);
        return mRequestConfig;
    }


}