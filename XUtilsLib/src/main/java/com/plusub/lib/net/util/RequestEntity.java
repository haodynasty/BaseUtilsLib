/*
 * FileName: RequestEntity.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : service@plusub.com
 * date     : 2014-11-13 下午5:22:03
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.net.util;

import com.plusub.lib.constant.PlusubConfig;

/**
 * 请求实体
 * @author service@plusub.com
 *
 */
public class RequestEntity {

	String requestUrl; //请求url
	Class classEntity; //解析后需要映射的实体对象
	boolean isCache = false; //是否缓存请求,后期添加缓存时间，默认缓存时间，一直缓存等扩展
	int cacheTime = PlusubConfig.cacheTime; //默认缓存时间1小时
	RequestType type; //请求类型POST和GET方式
	
	public RequestEntity(){}
	
	/**
	 * 
	 * <p>Title: 
	 * <p>Description: 
	 * @param requestUrl 请求的URL
	 * @param builder 解析函数
	 * @param isList 返回的结果类型是列表还是单个实体
	 * @param type 发送的请求类型：GET或POST
	 */
	public RequestEntity(String requestUrl, Class classEntity,
			RequestType type) {
		super();
		this.requestUrl = requestUrl;
		this.classEntity = classEntity;
		this.type = type;
		this.isCache = false;
	}
	
	/**
	 * 
	 * <p>Title: 
	 * <p>Description: 
	 * @param requestUrl 请求的URL
	 * @param builder 解析函数
	 * @param isList 返回的结果类型是列表还是单个实体
	 * @param type 发送的请求类型：GET或POST
	 * @param isCache 是否缓存请求，true缓存,缓存时间默认1小时(<b>post请求无法使用缓存方式</b>)
	 */
	public RequestEntity(String requestUrl, Class classEntity,
			RequestType type, boolean isCache) {
		super();
		this.requestUrl = requestUrl;
		this.classEntity = classEntity;
		this.type = type;
		this.isCache = isCache;
	}
	
	/**
	 * 
	 * <p>Title: 
	 * <p>Description: 
	 * @param requestUrl 请求的URL
	 * @param builder 解析函数
	 * @param isList 返回的结果类型是列表还是单个实体
	 * @param type 发送的请求类型：GET或POST
	 * @param isCache 是否缓存请求，true缓存,缓存时间默认1小时(<b>post请求无法使用缓存方式</b>)
	 * @param cacheTime 缓存时间（按秒计算）
	 */
	public RequestEntity(String requestUrl, Class classEntity,
			RequestType type, boolean isCache, int cacheTime) {
		super();
		this.requestUrl = requestUrl;
		this.classEntity = classEntity;
		this.type = type;
		this.isCache = isCache;
		this.cacheTime = cacheTime;
	}
	
	public boolean isCache() {
		return isCache;
	}

	public void setCache(boolean isCache) {
		this.isCache = isCache;
	}
	
	public int getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(int cacheTime) {
		this.cacheTime = cacheTime;
	}

	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public Class getClassEntity() {
		return classEntity;
	}
	public void setClassEntity(Class classEntity) {
		this.classEntity = classEntity;
	}
	
	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	
	@Override
	public String toString() {
		return "RequestEntity [requestUrl=" + requestUrl + ", classEntity="
				+ classEntity + ", isCache=" + isCache
				+ ", type=" + type + "]";
	}


	/**
	 * 请求类型get或post
	 * @author service@plusub.com
	 *
	 */
	public enum RequestType{
		GET,
		POST
	}
}
