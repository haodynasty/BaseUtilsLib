package com.plusub.lib.example.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.plusub.lib.example.BuildConfig;
import com.plusub.lib.example.MainApplication;
import com.plusub.lib.example.api.http.UrlInterceptor;
import com.plusub.lib.example.api.utils.TypedFile;
import com.plusub.lib.util.FileUtils;
import com.plusub.lib.util.logger.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
 * date     : 2016/8/12 10:55 <br>
 * last modify author : <br>
 * version : 1.0 <br>
 * description:Retrofit封装
 */
public class RetrofitUtil {
    private static final int DEFAULT_TIMEOUT = 5;
    private static final int CACHE_SIZE = 10 * 1024 * 1024; //10MB
    private static HttpService httpService;
    private static Retrofit retrofit;

    /**
     * 获取接口
     * @return
     */
    public static HttpService getService() {
        if (httpService == null) {
            httpService = getRetrofit().create(HttpService.class);
        }
        return httpService;
    }

    private static Retrofit getRetrofit() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        //开启缓存实现（具体的实现需要使用拦截器：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0115/3873.html）
        File httpCacheDirectory = new File(FileUtils.getInnerCacheDir(MainApplication.getInstance()), "responses");
        builder.cache(new Cache(httpCacheDirectory, CACHE_SIZE));
        //builder.networkInterceptors().add(new CacheInterceptor());//缓存

        //设置网络请求日志
        if (BuildConfig.DEBUG){
            builder.networkInterceptors().add(new StethoInterceptor());
            builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Logger.i("OkHttp", message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY));//网络和日志拦截
        }

        //url拦截器,可以在发送前添加自定义头和设置缓存
        builder.addInterceptor(new UrlInterceptor());

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HttpUrl.BASE_URL)
                .build();
        return  retrofit;
    }

    /**
     * 观察者订阅
     * @param o
     * @param s
     * @param <T>
     */
    protected static<T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     * 要考虑HttpResult的可扩展性（返回结果统一处理）
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    protected class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            Logger.i("request result:" + httpResult);
            if (!httpResult.isResultOk()) {
                throw new ApiException(httpResult.getStatus(), httpResult.getMsg());
            }
            return httpResult.getEntity();
        }
    }

    /**
     * 如果文件不存在，则返回空
     * @param filePath 文件路径
     * @return if not exist of file, will return null
     */
    protected static RequestBody createRequestBodyForFile(String filePath){
        File file = new File(filePath);
        if (file.exists()) {
            TypedFile typedFile = new TypedFile("name", file);
            RequestBody requestFile = RequestBody.create(typedFile.getMediaType(), typedFile.getFile());
            return  requestFile;
        }
        return null;
    }


    /**
     * 直接创建可以上传的Body
     * @param name 服务器需要上传文件时规定的name
     * @param filePath 文件路径
     * @return if not exist of file, will return null
     */
    protected static MultipartBody.Part createMultipartBodyForFile(String name, String filePath){
        File file = new File(filePath);
        if (file.exists()) {
            TypedFile typedFile = new TypedFile(name, file);
            RequestBody requestFile = RequestBody.create(typedFile.getMediaType(), typedFile.getFile());
            MultipartBody.Part body = MultipartBody.Part.createFormData(typedFile.getName(), typedFile.getFile().getName(), requestFile);
            return  body;
        }
        return null;
    }

    protected static RequestBody createRequestBody(int param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected static RequestBody createRequestBody(long param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected static RequestBody createRequestBody(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

}
