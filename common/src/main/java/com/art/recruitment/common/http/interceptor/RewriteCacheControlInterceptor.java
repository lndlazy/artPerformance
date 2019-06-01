package com.art.recruitment.common.http.interceptor;

import android.text.TextUtils;

import com.art.recruitment.common.base.BaseApplication;
import com.art.recruitment.common.http.Api;
import com.art.recruitment.common.utils.NetWorkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 云端响应头拦截器，用来配置缓存策略
 * Dangerous interceptor that rewrites the server's cache-control header.
 *
 */
public class RewriteCacheControlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String cacheControl = request.cacheControl().toString();
        if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
            request = request.newBuilder()
                    .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置

            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + Api.CACHE_STALE_SEC)
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
