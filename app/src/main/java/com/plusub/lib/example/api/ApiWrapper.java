package com.plusub.lib.example.api;

import android.content.Context;

import com.plusub.lib.example.api.http.subscribers.ProgressSubscriber;
import com.plusub.lib.example.api.http.subscribers.SubscriberOnNextListener;
import com.plusub.lib.example.model.BookEntity;

import java.util.List;

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
 * date     : 2016/8/12 11:37 <br>
 * last modify author : <br>
 * version : 1.0 <br>
 * description:
 */
public class ApiWrapper extends RetrofitUtil{

    /**
     * 获取图书列表
     * @param context
     * @param queryString
     * @param count
     * @param mSubscriberOnNextListener
     */
    public static void getBookList(Context context, String queryString, int count, SubscriberOnNextListener<List<BookEntity>> mSubscriberOnNextListener){
        Observable observable = getService().getBookList(queryString, count);
        toSubscribe(observable, new ProgressSubscriber(context, mSubscriberOnNextListener));
    }

}
