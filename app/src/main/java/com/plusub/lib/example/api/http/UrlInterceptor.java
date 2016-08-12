package com.plusub.lib.example.api.http;

import com.plusub.lib.util.logger.Logger;

import java.io.IOException;

import okhttp3.HttpUrl;
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
 * date     : 2016/5/16 18:16 <br>
 * last modify author : <br>
 * version : 1.0 <br>
 * description: 请求url处理拦截器
 */
public class UrlInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest  = chain.request();
        String newUrl = oldRequest.url().url().toString();
        int index = newUrl.indexOf("?");
        if (index <= 0) {//没有参数或者post请求
            index = newUrl.length();
        }

        try{
            String endUrl = newUrl.substring(index, newUrl.length());
            newUrl = newUrl.substring(0, index).replaceAll("%2F", "/")+endUrl;
            // 添加新的参数
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder(newUrl)
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host());
            //可增加新的参数
//                            .addQueryParameter(MarvelService.PARAM_API_KEY, mApiKey)
//                            .addQueryParameter(MarvelService.PARAM_TIMESTAMP, MarvelApiUtils.getUnixTimeStamp())
//                            .addQueryParameter(MarvelService.PARAM_HASH, marvelHash);

            // 新的请求
            Request newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authorizedUrlBuilder.build())
                    .build();
            Logger.d("OkHttp", "url translate:" + newRequest.url());
            return chain.proceed(newRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return chain.proceed(oldRequest);
    }
}
