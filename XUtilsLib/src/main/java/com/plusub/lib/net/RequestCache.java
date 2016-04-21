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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * 
 * @author blakequ Blakequ@gmail.com
 *
 */
public class RequestCache {
	
	// TODO cache lifeTime
	
	private static int CACHE_LIMIT = 20;
	
	@SuppressWarnings("unchecked")
	private LinkedList history;
	private Hashtable<String, String> cache;
	private HashMap<String, Boolean> isCacheUrl;
	
	@SuppressWarnings("unchecked")
	public RequestCache(){
		history = new LinkedList();
		cache = new Hashtable<String, String>();
		isCacheUrl = new HashMap<String, Boolean>();
	}
	
	@SuppressWarnings("unchecked")
	public void put(String url, String data){
		history.add(url);
		// too much in the cache, we need to clear something
		if(history.size() > CACHE_LIMIT){
			String old_url = (String) history.poll();
			cache.remove(old_url);
		}
		if (!isCacheUrl.containsKey(url) || isCacheUrl.get(url)) {
			cache.put(url, data);
		}
	}
	
	public String get(String url){
		return cache.get(url);
	}
	
	public boolean remove(String url){
		if (cache.contains(url)) {
			cache.remove(url);
			if (isCacheUrl.containsKey(url)) {
				isCacheUrl.remove(url);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * set the url is cache or not
	 * <p>Title: setIsCache
	 * <p>Description: 
	 * @param url
	 * @param isCache
	 */
	public void setIsCache(String url, boolean isCache){
		isCacheUrl.put(url, isCache);
	}
	
	/**
	 * clear http cache
	 * <p>Title: clear
	 * <p>Description:
	 */
	public void clear(){
		cache.clear();
		isCacheUrl.clear();
	}
}
