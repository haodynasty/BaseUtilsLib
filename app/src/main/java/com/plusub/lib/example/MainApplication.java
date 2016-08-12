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

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.plusub.lib.BaseApplication;
import com.plusub.lib.activity.lifecycle.ActivityLifecycleCallbacksAdapter;
import com.plusub.lib.activity.lifecycle.FragmentLifecycleCallbacksAdapter;
import com.plusub.lib.util.FileUtils;
import com.plusub.lib.util.JSONUtils;
import com.plusub.lib.util.logger.Logger;

import java.io.File;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainApplication extends BaseApplication {

    public static String mAppId;
    public static String mDownloadPath;
    public static String mCachePath;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Logger.d("[MainApplication] onCreate");

		instance = this;
		initEnv();

		if (BuildConfig.DEBUG){
			//关闭解析异常打印开关
			JSONUtils.setPrintSwitch(true);
			Stetho.initializeWithDefaults(this);
		}else{
			JSONUtils.setPrintSwitch(false);
		}

		//生命周期监听
		setActivityLifecycleCallbacks(new ActivityLifecycleCallbacksAdapter() {
			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
				super.onActivityCreated(activity, savedInstanceState);
				Logger.i("activity lifecycle onActivityCreated");
			}
		});

		setFragmentLifecycleCallbacks(new FragmentLifecycleCallbacksAdapter(){
			@Override
			public void onFragmentCreateView(Fragment fragment, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				super.onFragmentCreateView(fragment, inflater, container, savedInstanceState);
				Logger.i("fragment lifecycle onFragmentCreateView");
			}

			@Override
			public void onFragmentCreated(Fragment fragment, Bundle savedInstanceState) {
				super.onFragmentCreated(fragment, savedInstanceState);
				Logger.i("fragment lifecycle onFragmentCreated");
			}
		});

		//网络图片加载
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		//设置网络请求日志
		if (BuildConfig.DEBUG){
			builder.networkInterceptors().add(new StethoInterceptor());
			builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
				@Override
				public void log(String message) {
					Logger.i("OkHttp", message);
				}
			}).setLevel(HttpLoggingInterceptor.Level.BODY));//网络和日志拦截
		}
		ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
				.newBuilder(this, builder.build())
				.build();
		Fresco.initialize(this, config);
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
		Logger.e("[MainApplication] exception thread id:" + threadId + " exception " + content);
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

	@Override
	public boolean isDebug() {
		return BuildConfig.DEBUG;
	}

}
