/*
 * Copyright (C) 2009 Teleca Poland Sp. z o.o. <android@teleca.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.plusub.lib.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.constant.ErrorCode;
import com.plusub.lib.constant.NetConstant;
import com.plusub.lib.net.util.RequestParams;
import com.plusub.lib.util.CacheUtils;
import com.plusub.lib.util.CommException;
import com.plusub.lib.util.LogUtils;
import com.plusub.lib.util.TextUtils;

/**
 * 实现网络连接数据请求
 * @author blakequ Blakequ@gmail.com
 *
 */
public class Caller {
	
	public static final String TAG = "Plusub_Caller";
	/**
	 * Cache for most recent request
	 */
	private static CacheUtils cacheUtils = null;
	
	
	/**
	 * 
	 * <p>Title: doGet
	 * <p>Description: Performs HTTP GET using Apache HTTP Client v 4
	 * @param url base url address. for example: http://myendpoint.com
	 * @param params params list. 
	 * @return
	 * @throws CommException 
	 */
	public static String doGet(String url, RequestParams params, boolean isCache, int cacheTime) throws CommException{
		if (params == null) {
			return doGet(url, isCache, cacheTime);
		}
		return doGet(url+params.toString(), isCache, cacheTime);
	}
	
	/**
	 * 建议使用该函数，可以放入参数和文件
	 * 可以代替 {@link #doMultiPost(String, Map, NameValuePair...)} and {@link #doPost(String, NameValuePair...)}
	 * 方法
	 * <p>Title: doPost
	 * <p>Description: do post method
	 * @param url
	 * @param params
	 * @return
	 * @throws CommException
	 */
	public static String doPost(String url, RequestParams params) throws CommException{
		if (params == null) {
			throw new CommException("do post params is null", ErrorCode.PARA_EXCEPTION);
		}
		LogUtils.d(TAG, "Caller.doPost "+url);
		
		String data = "";
		// initialize HTTP Post request objects
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		com.plusub.lib.net.HttpClient httpClient=new com.plusub.lib.net.HttpClient(defaultHttpClient, null);
		try {
			data=httpClient.doHttpPost(url, params.getEntity(), params.isHaveFileParams());
		} catch (Exception e) {
			CommException wsError = new CommException(e.getLocalizedMessage(), ErrorCode.OTHER_DEFAULT_EXCEPTION);
			throw wsError;
		}
		return data;
	}
	
	@Deprecated
	public static String doPost(String url, NameValuePair... nameValuePairs) throws CommException{
		if (nameValuePairs == null) {
			throw new CommException("do post params is null", ErrorCode.PARA_EXCEPTION);
		}
		LogUtils.d(TAG, "Caller.doPost "+url);
		
		String data = "";
		// initialize HTTP Post request objects
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		com.plusub.lib.net.HttpClient httpClient=new com.plusub.lib.net.HttpClient(defaultHttpClient, null);
		try {
			data=httpClient.doHttpPost(url, nameValuePairs);
		} catch (Exception e) {
			CommException wsError = new CommException(e.getLocalizedMessage(), ErrorCode.OTHER_DEFAULT_EXCEPTION);
			throw wsError;
		}
		
		return data;
	}
	
	/**
	 * 包括文件的post
	 * @param url
	 * @param fileMap
	 * @param nameValuePairs
	 * @return
	 * @throws WSError
	 */
	@Deprecated
	public static String doMultiPost(String url,Map<String, File> fileMap, NameValuePair... nameValuePairs)
			throws CommException{
		LogUtils.d(TAG, "Caller.doMultiPost "+url);
		String data = null;
		// initialize HTTP Post request objects
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		com.plusub.lib.net.HttpClient httpClient=new com.plusub.lib.net.HttpClient(defaultHttpClient, null);
		try {
			data=httpClient.doMultiHttpPost(url, fileMap, nameValuePairs);
		} catch (Exception e) {
			CommException wsError = new CommException(e.getLocalizedMessage(), ErrorCode.OTHER_DEFAULT_EXCEPTION);
			throw wsError;
		}
		return data;
	}
	
	/**
	 * convert stream to string
	 * <p>Title: convertStreamToString
	 * <p>Description: 
	 * @param is
	 * @return
	 * @throws CommException
	 */
	public static String convertStreamToString(InputStream is) throws CommException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				CommException wsError = new CommException(e.getLocalizedMessage(), ErrorCode.OTHER_DEFAULT_EXCEPTION);
				throw wsError;
			}
		}

		return sb.toString();
	}
	
	
	/**
	 * Performs HTTP GET using Apache HTTP Client v 4
	 * 
	 * @param url
	 * @param isCache 是否缓存
	 * @param cacheTime 缓存时间，单位秒
	 * @return
	 * @throws WSError 
	 */
	private static String doGet(String url, boolean isCache, int cacheTime) throws CommException{
		String data = null;
		HttpParams httpParameters = new BasicHttpParams();   
		HttpGet httpGet = null;
		HttpResponse httpResponse;
		InputStream inputStream = null;
		
		if(cacheUtils != null && isCache){
			data = cacheUtils.getAsString(url);
			if(data != null){
				LogUtils.d(TAG, "Caller.doGet [cached] "+url);
				return data;
			}
		}
		LogUtils.d(TAG, "Caller.doGet "+url);
		
		// initialize HTTP GET request objects
		HttpConnectionParams.setConnectionTimeout(httpParameters, NetConstant.TIME_OUT);//设置请求超时10秒  
		HttpConnectionParams.setSoTimeout(httpParameters, NetConstant.TIME_OUT); //设置等待数据超时10秒  
		HttpClient httpClient = new DefaultHttpClient(httpParameters);
		try {
			// execute request
			try {
				httpGet = new HttpGet(url);
				if (TextUtils.notEmpty(BaseApplication.getInstance().getSessionId())) {
					httpGet.setHeader("Cookie", "JSESSIONID=" +BaseApplication.getInstance().getSessionId());
				}

				httpResponse = httpClient.execute(httpGet);
			} catch (UnknownHostException e) {
				CommException wsError = new CommException(e.getLocalizedMessage(), ErrorCode.NET_LINK_EXCEPTION);
				throw wsError;
			} catch (SocketException e){
				CommException wsError = new CommException(e.getLocalizedMessage(), ErrorCode.NET_LINK_EXCEPTION);
				throw wsError;
			} catch (IllegalArgumentException e){
				CommException wsError = new CommException(e.getLocalizedMessage(), ErrorCode.OTHER_DEFAULT_EXCEPTION);
				throw wsError;
			}
			
			// request data
			HttpEntity httpEntity = httpResponse.getEntity();
			
			if(httpEntity != null){
				inputStream = httpEntity.getContent();
				data = convertStreamToString(inputStream);
				data.replaceAll("\r", "");
				// cache the result
				if(cacheUtils != null && isCache){
					cacheUtils.put(url, data, cacheTime);
				}
			}
			
		} catch (ClientProtocolException e) {
			CommException wsError = new CommException(e.getLocalizedMessage(), ErrorCode.NET_LINK_EXCEPTION);
			throw wsError;
		} catch (IOException e) {
			CommException wsError = new CommException(e.getLocalizedMessage(), ErrorCode.OTHER_DEFAULT_EXCEPTION);
			throw wsError;
		} finally{
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					CommException wsError = new CommException(e.getLocalizedMessage(), ErrorCode.OTHER_DEFAULT_EXCEPTION);
					throw wsError;
				}
			}
		}
		
		return data;
	}

	public static CacheUtils getRequestCache(Context context){
		cacheUtils = CacheUtils.get(context);
		return cacheUtils;
	}
	
	public static String createStringFromIds(int[] ids){
		if(ids == null)
			return "";
		
		String query ="";
		
		for(int id : ids){
			query = query + id + "+";
		}
		
		return query;
	}
}
