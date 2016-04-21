/*
 * FileName: DoGetRequestManager.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : quhao <blakequ@gmail.com>
 * date     : 2014-6-8 下午12:35:29
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.net.util;


import com.plusub.lib.constant.PlusubConfig;
import com.plusub.lib.net.Caller;
import com.plusub.lib.net.util.RequestParams;
import com.plusub.lib.util.CommException;


/**
 * 网络请求数据管理
 * @author blakequ Blakequ@gmail.com
 *
 */
public class RequestManager {
	
	public static final String TAG = RequestManager.class.getSimpleName();

	/**
	 * not cache
	 * <p>Title: doGet
	 * <p>Description: 
	 * @param url base url using like {@code http://.../cooperate/user/login.html?}
	 * @param params request params
	 * @return
	 * @throws CommException
	 */
	public String doGet(String url, RequestParams params) throws CommException{
		return Caller.doGet(url, params, false, 0);
	}
	
	/**
	 * request url by cache
	 * <p>Title: doGetByCache
	 * <p>Description: 默认缓存时间1小时
	 * @param url base url using like {@code http://.../cooperate/user/login.html?}
	 * @param params
	 * @return
	 * @throws CommException
	 */
	public String doGetByCache(String url, RequestParams params) throws CommException{
		return Caller.doGet(url, params, true, PlusubConfig.cacheTime);
	}
	
	/**
	 * request url by cache
	 * <p>Title: doGetByCache
	 * <p>Description: 
	 * @param url base url using like {@code http://.../cooperate/user/login.html?}
	 * @param cacheTime 缓存时间，单位秒
	 * @return
	 * @throws CommException
	 */
	public String doGetByCache(String url, RequestParams params, int cacheTime) throws CommException{
		return Caller.doGet(url, params, true, cacheTime);
	}
	
	/**
	 * simple post method
	 * <p>Title: doPost
	 * <p>Description: 
	 * @param url
	 * @param params
	 * @return
	 * @throws CommException
	 */
	public String doPost(String url, RequestParams params) throws CommException{
		return Caller.doPost(url, params);
	}
	
}
