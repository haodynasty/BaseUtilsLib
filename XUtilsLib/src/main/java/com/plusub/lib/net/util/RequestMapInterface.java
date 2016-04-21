/*
 * FileName: RequestConstant.java
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
 * date     : 2014-6-8 下午12:37:13
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.net.util;

import java.util.HashMap;
import java.util.Map;


/**
 * url请求链接头，使用时需要继承该类
 * 使用时需要做两件事：
 * <li>1.声明请求ID</li>
 * <ul>
 * public static final int TASK_LOGIN = 0;</ul>
 * 
 * <li>2.将请求填入MAP</li>
 * <ul>
 * <code>
 * 	<ul>//请求任务实体RequestEntity含义（请求的URL，请求结果解析类，返回结果的类型（单个实体或数组），Get或Post类型请求）</ul>
 *	<ul>RequestMap.put(TASK_LOGIN, new RequestEntity(PATH + "/Users/login?", LoginBuilder.class, false, RequestType.GET));//登陆</ul>
 * </code>
 * @author service@plusub.com
 *
 */
public interface RequestMapInterface {
	
	/**
	 * 实现接口时添加任务ID，及任务对应的实体RequestEntity
	 * <li><h1>实例</h1></li>
	 * BaseRequestService.RequestMap.put(TASK_LOGIN, new RequestEntity(PATH + "/Users/login?", LoginBuilder.class, false, RequestType.GET));//登陆
	 * <ul><strong>TASK_LOGIN是任务ID，建议单独在一个类中定义</strong></ul>
	 * <p>Title: addTaskToMap
	 * <p>Description: 添加任务到Map
	 */
	public void addTaskToMap();

}
