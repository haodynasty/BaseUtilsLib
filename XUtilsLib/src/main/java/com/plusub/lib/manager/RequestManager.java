package com.plusub.lib.manager;

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
 * author  : quhao <blakequ@gmail.com>
 * date     : 2016/4/26 16:11
 * last modify author :
 * version : 1.0
 * description:封装http请求的管理类
 */
public class RequestManager {
    public static final String EXPIRES       = "expires";
    public static final String CACHE_CONTROL = "cache-control";
    public static final int TIME_OUT = 6*1000; //设置超时时间,6s
    //https://drakeet.me/retrofit-2-0-okhttp-3-0-config
    //http://www.jianshu.com/p/9c3b4ea108a7
    //http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0915/3460.html
    //okhttp配置：http://www.devtf.cn/?p=1264

    /**
     * //给每个请求重设超时、重试次数
     */
    public static void addRequest(){

    }

    /**
     * 取消连接
     */
    public static void cancel(){

    }
}
