package com.plusub.lib.example.api.http;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.util.NetStateUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (C) BlakeQu All Rights Reserved <blakequ@gmail.com>
 * <p/>
 * Licensed under the blakequ.com License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * author  : quhao <blakequ@gmail.com> <br>
 * date     : 2016/5/17 11:18 <br>
 * last modify author : <br>
 * version : 1.0 <br>
 * description: http://www.jianshu.com/p/9c3b4ea108a7
 * max-stale在请求头设置有效，在响应头设置无效
 * max-stale和max-age同时设置的时候，缓存失效的时间按最长的算。
 */
public class CacheInterceptor implements Interceptor{
    public static final int NET_CACHE_TIME = 60; //read from cache for 1 minute
    public static final int NET_NO_CACHE_TIME = 60 * 60 * 24 * 28; //tolerate 4-weeks stale

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean isNet = NetStateUtils.hasNetWorkConnection(BaseApplication.getInstance());
        if (!isNet) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .url(request.url()).build();
        }

        Response response = chain.proceed(request);
        if (isNet) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//            String cacheControl = request.cacheControl().toString();
//            return originalResponse.newBuilder()
//                    .header("Cache-Control", cacheControl)
//                    .removeHeader("Pragma")
//                    .build();
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + NET_CACHE_TIME)
                    .build();
        } else {
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + NET_NO_CACHE_TIME)
                    .build();
        }
        return response;
    }
}
