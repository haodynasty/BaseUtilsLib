/*
 * FileName: BaseUITask.java
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
 * date     : 2014-6-3 下午1:16:39
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.activity;

/**
 * 
 * @ClassName: BaseUITask
 * @Description: TODO
 * @author qh@plusub.com
 * @date 2014-12-1 上午11:18:51
 * @version v1.0
 */
public interface BaseUITask {
	
	/**
	 * 初始化视图
	 * <p>Title: initView
	 * <p>Description:
	 */
	void initView();
	
	/**
	 * 初始化数据
	 * <p>Title: initData
	 * <p>Description:
	 */
	void initData();
	
	/**
	 * 初始化事件，如单击事件等
	 * <p>Title: initEvent
	 * <p>Description:
	 */
	void initEvent();
}
