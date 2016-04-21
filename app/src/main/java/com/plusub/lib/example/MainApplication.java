/*
 * FileName: MainApplication.java
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
 * date     : 2014-11-13 下午2:32:34
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example;

import android.os.Environment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.plusub.lib.BaseApplication;
import com.plusub.lib.example.service.RequestService;
import com.plusub.lib.util.CacheManager;
import com.plusub.lib.util.FileUtils;
import com.plusub.lib.util.JSONUtils;
import com.plusub.lib.util.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainApplication extends BaseApplication {

	private static MainApplication instance;
    public static String mAppId;
    public static String mDownloadPath;
    public static String mCachePath;
    
  //表情
    public static List<String> mEmoticons = new ArrayList<String>();
	public static Map<String, Integer> mEmoticonsId = new HashMap<String, Integer>();
	public static List<String> mEmoticons_Zem = new ArrayList<String>();
	public static List<String> mEmoticons_Zemji = new ArrayList<String>();


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		LogUtils.d("MainApplication", "[MainApplication] onCreate");

		instance = this;
		initEnv();
		getHttpCache();
		
		//关闭解析异常打印开关
		JSONUtils.setPrintSwitch(true);
		
		//保存日志到SDcard
//		LogUtils.setLogFilePath("bangbang/log");
//		LogUtils.setSyns(true);
		
		//启动网络请求服务
		RequestService.startService(this);
	}

	/**
	 * 图片缓存
	 * <p>Title: getImageCache
	 * <p>Description: 
	 * @return
	 */
	public ImageLoader getImageCache(){
		return CacheManager.getImageCache(getApplicationContext());
	}


	public static MainApplication getInstance(){
		return instance;
	}
	
	/**
	 * 初始化基本的变量（路径，appid）
	 * <p>Title: initEnv
	 * <p>Description:
	 */
    private void initEnv() {
        mAppId = getString(R.string.app_id);
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        mDownloadPath = sdcard+"/" + mAppId+ "/download";
        mCachePath = BaseApplication.mCachePath;
        FileUtils.createExternalStoragePublicPicture();
		if(FileUtils.isSDCardAvailable()){
			FileUtils.createDir(new File(mDownloadPath));
		}
    }
    
	
	@Override
	public void doUncatchException(String savePath, long threadId,
			String content) {
		// TODO Auto-generated method stub
		LogUtils.e("MainApplication", "[MainApplication] exception thread id:"+ threadId+" exception "+content);
		//主线程异常，退出应用并重启
		if (threadId == 1) { 
			// 此处示例发生异常后，重新启动应用
//			Intent intent = new Intent(this, LoadingActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			startActivity(intent);
			
			android.os.Process.killProcess(android.os.Process.myPid());
		}else{
			
		}
	}


	@Override
	public void clearApp() {
		// TODO Auto-generated method stub
		
	}
	
}
