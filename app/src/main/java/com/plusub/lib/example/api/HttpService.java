package com.plusub.lib.example.api;

import com.plusub.lib.example.model.BookEntity;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

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
 * date     : 2016/8/12 11:08 <br>
 * last modify author : <br>
 * version : 1.0 <br>
 * description:
 */
public interface HttpService {

    //获取图书列表
    @GET("v2/book/search")
    Observable<HttpResult<List<BookEntity>>> getBookList(
            @Query("q") String queryString,
            @Query("count") int count
    );

    //HttpResult or JsonObject //https://segmentfault.com/n/1330000004880380
    @GET("{url}")
    Observable<HttpResult> getData(
            @Path("url") String url,
            @QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST( "{url}")
    Observable<HttpResult> postData(
            @Path("url") String url,
            @FieldMap Map<String, String> map);

    @Multipart
    @POST( "{url}")
    Observable<HttpResult> uploadFile(
            @Path("url") String url,
            @PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("{url}")
    Observable<HttpResult> uploadFile(
            @Path("url") String url,
            @Part MultipartBody.Part file);
}
