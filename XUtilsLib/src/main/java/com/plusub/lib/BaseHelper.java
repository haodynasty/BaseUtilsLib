/*
 * FileName: BaseHelper.java
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
 * date     : 2015-5-7 上午11:00:53
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib;

import java.io.File;

import android.content.Context;

import com.plusub.lib.annotate.JsonParserUtils;
import com.plusub.lib.util.FileUtils;
import com.plusub.lib.util.JSONUtils;
import com.plusub.lib.util.LogUtils;

/**
 * @ClassName: BaseHelper
 * @Description: TODO 应用帮助类，可以设置全部日志打印开关
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-5-7 上午11:00:53<br>
 *     <b>最后修改时间：</b>2015-5-7 上午11:00:53
 * @version v1.0
 */
public class BaseHelper {
	
	/**
	 * 全部打印开关，包括日志LogUtils，和JSON解析
	 * <p>Title: setAllPrintSwitch
	 * <p>Description: 
	 * @param isOpen
	 */
	public static void setAllPrintSwitch(boolean isOpen){
		setLogPrintSwitch(isOpen);
		setJsonPrintSwitch(isOpen);
	}

	/**
	 * 设置日志打印开关(LogUtils)
	 * <p>Title: setLogPrintSwitch
	 * <p>Description: 
	 * @param isOpen true则打开日志打印
	 */
	public static void setLogPrintSwitch(boolean isOpen){
	    LogUtils.setDebugLog(isOpen);
	}
	
	/**
	 * 设置打印日志保存开关（默认关闭，保存路径为默认包名如:包名为com.plusub,则会保存到sdcard/com/plusub之下）
	 * <p>Title: setLogSaveSwitch
	 * <p>Description: 
	 * @param isSave 是否保存日志到sdcard
	 * @param context
	 */
	public static void setLogSaveSwitch(boolean isSave, Context context){
		LogUtils.setSyns(isSave);
		LogUtils.setDefaultFilePath(context);
	}
	
	/**
	 * 设置打印日志保存开关（默认关闭，保存路径为指定路径, 如：plusub/log，则会保存到sdcard/plusub/log目录下）
	 * <br><b>注意：路径必须为相对路径，格式为xx/xx形式，然后系统会自动保存到sdcard/xx/xx路径之下</b></br>
	 * <p>Title: setLogSavePath
	 * <p>Description: 
	 * @param path
	 */
	public static boolean setLogSaveSwitch(boolean isSave, String path){
		if (path == null || path.length() != 0) {
			String url = FileUtils.getSDCardPath().getPath() + File.separator + path;
			if (FileUtils.isDirectoryAvailable(url)) {
				LogUtils.setSyns(isSave);
				LogUtils.setLogFilePath(path);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * JSON解析异常打印开关
	 * <p>Title: setJsonPrintSwitch
	 * <p>Description: 
	 * @param isOpen true则打开
	 */
	public static void setJsonPrintSwitch(boolean isOpen){
	    JSONUtils.setPrintSwitch(isOpen);
	    JsonParserUtils.setPrintSwitch(isOpen);
	}
}
