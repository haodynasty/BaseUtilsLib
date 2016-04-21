/*
 * FileName: PlusubConfig.java
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
 * date     : 2014-12-2 上午11:07:09
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.constant;

import com.plusub.lib.util.CacheUtils;

/**
 * @ClassName: PlusubConfig
 * @Description: TODO 全局默认配置文件
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-2 上午11:07:09<br>
 *     <b>最后修改时间：</b>2014-12-2 上午11:07:09
 * @version v1.0
 */
public class PlusubConfig {
	
	/**dubug模式，如果为ture则打印日志*/
	public static final boolean isDebugSwitch = true;
	/**是否保存日志到本地*/
	public static final boolean isLogSaveToFileSwitch = false;
	/**解析异常打印开关*/
	public static final boolean isPrintParserErrorSwitch = true;
	/**Json解析异常打印开关*/
	public static final boolean isPrintJsonErrorSwitch = false;
	/**默认缓存时间是1小时*/
	public static final int cacheTime = CacheUtils.TIME_HOUR;
}
