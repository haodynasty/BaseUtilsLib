/*
 * FileName: BaseApplication.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : Administrator
 * date     : 2014-6-1 下午10:18:15
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plusub.lib.activity.BaseTask;
import com.plusub.lib.activity.lifecycle.ActivityLifecycleCallbacksCompat;
import com.plusub.lib.activity.lifecycle.FragmentLifecycleCallbacks;
import com.plusub.lib.activity.lifecycle.LifecycleDispatcher;
import com.plusub.lib.annotate.JsonParserUtils;
import com.plusub.lib.exp.UEHandler;
import com.plusub.lib.service.BaseService;
import com.plusub.lib.task.UserTask;
import com.plusub.lib.util.FileUtils;
import com.plusub.lib.util.JSONUtils;
import com.plusub.lib.util.StrictModeUtil;
import com.plusub.lib.util.logger.LogLevel;
import com.plusub.lib.util.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.LinkedList;
import java.util.List;

/**
 * 系统application
 * <li>注意：需要在manifest的string.xml中定义一个字段app_id,用于建立本地文件存储路径</li>
 * @author service@plusub.com
 *
 */
public abstract class BaseApplication extends Application {

	private String sessionId;
	public static boolean DEBUG_MODE = true;
	public static BaseApplication instance;
    /**缓存路径，内部路径*/
    public static String mCachePath;
    /**错误日志路径，内部路径*/
    public static String errorLogPath ;
    /**未捕获异常处理*/
    private UEHandler ueHandler;
    /**是否锁定屏幕为竖屏*/
    private boolean isLockScreen = true;
    /**用户异步任务列表*/
	public static List<UserTask> taskList = new LinkedList<UserTask>(); 
	/**基本任务列表*/
	public static List<BaseTask> totalList = new LinkedList<BaseTask>();
	private ActivityLifecycleCallbacksCompat activityLifecycleCallbacks;
	private FragmentLifecycleCallbacks fragmentLifecycleCallbacks;

    public static BaseApplication getInstance(){
    	return instance;
    }

	//启用内存监控
	private RefWatcher refWatcher;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		DEBUG_MODE = isDebug();
		StrictModeUtil.init();
		super.onCreate();
		instance = this;
		refWatcher = LeakCanary.install(this);
		initEnv();

		// 设置异常处理实例
		ueHandler = new UEHandler(errorLogPath, this);
        Thread.setDefaultUncaughtExceptionHandler(ueHandler);

        //打开解析异常打印开关
        JSONUtils.setPrintSwitch(BaseApplication.DEBUG_MODE);
        JsonParserUtils.setLogSwitch(BaseApplication.DEBUG_MODE);

		//日志开关
		if (BaseApplication.DEBUG_MODE) {
			Logger.init(getClass().getSimpleName()).hideThreadInfo().setMethodCount(1).setLogLevel(LogLevel.FULL);
		}else {
			Logger.init(getClass().getSimpleName()).hideThreadInfo().setMethodCount(1).setLogLevel(LogLevel.NONE);
		}

		//生命周期拦截器
		registerContextCallback();
	}

	/**
	 * 设置全局activity生命周期监听
	 * @param activityLifecycleCallbacks
	 */
	public void setActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat activityLifecycleCallbacks) {
		this.activityLifecycleCallbacks = activityLifecycleCallbacks;
	}

	/**
	 * 设置全局frgament生命周期监听
	 * @param fragmentLifecycleCallbacks
	 */
	public void setFragmentLifecycleCallbacks(FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
		this.fragmentLifecycleCallbacks = fragmentLifecycleCallbacks;
	}

	/**
	 * LeakCanary内存监控
	 * @param context
	 * @return 内存监控LeakCanary对象
	 */
	public static RefWatcher getRefWatcher(Context context) {
		BaseApplication application = (BaseApplication) context.getApplicationContext();
		return application.refWatcher;
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		exit();
		exitApp(this);
	}
	

	/**
	 * 处理系统未捕获异常
	 * <p>Title: doUncatchException
	 * <p>Description: 
	 * @param savePath 保存异常日志的路径
	 * @param threadId 抛出异常的线程id，主线程为id为1
	 * @param content 异常内容
	 */
	public abstract void doUncatchException(String savePath, long threadId, String content);
//	{
		//将异常上传服务器
//		uploadErrorToServer(savePath);
		//主线程异常，退出应用并重启
//		if (threadId == 1) { 
			// 此处示例发生异常后，重新启动应用
			//Intent intent = new Intent(this, LoadingActivity.class);
			//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//startActivity(intent);
					
//			MainService.exitApp(this);
//			android.os.Process.killProcess(android.os.Process.myPid());
//		}else{
//			
//		}
//	}
	

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		clearService();
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		switch (level) {
			case TRIM_MEMORY_UI_HIDDEN:
				// 进行资源释放操作
				clearService();
				break;
		}
	}

	public boolean isLockScreen() {
		return isLockScreen;
	}

	/**
	 * 是否锁定屏幕为竖屏(默认为true)
	 * <p>Title: setLockScreen
	 * <p>Description: 
	 * @param isLockScreen
	 */
	public void setLockScreen(boolean isLockScreen) {
		this.isLockScreen = isLockScreen;
	}

	/**
	 * 
	 * Title: exit
	 * Description:should invoke this method before exit app
	 * <br>{@link #exitApp(Context)} 会自动调用该方法
	 */
	private void exit()
	{

	}
	
	/**
	 * 在应用退出时，清除一些缓存，关闭数据库，清除后台等基本操作
	 * <br><b>注意：在应用退出时应该主动调用{@link #exitApp(Context)}方法，它会自动回调该方法}</b>
	 * <p>Title: clearApp
	 * <p>Description:
	 */
	public abstract void clearApp();

	/**
	 * 获取当前debug模式
	 * @return 应该返回BuildConfig.DEBUG即可
	 */
	public abstract boolean isDebug();
	

	/**
	 * 初始化基本的变量（路径，appid）
	 * <p>Title: initEnv
	 * <p>Description:
	 */
    private void initEnv() {
		mCachePath = FileUtils.getInnerCacheDir(this).getAbsolutePath();
		if (Build.VERSION.SDK_INT >= 23){
			errorLogPath = FileUtils.getInnerFileDir(this).getAbsolutePath()+"/error.log";
		}else{
			errorLogPath = FileUtils.getOutCacheDir(this)+"/error.log";
		}
    }
    

	/**
	 * 退出应用程序，结束任务，清除堆栈
	 * <p>
	 * Title: exitApp
	 * <p>
	 * Description:
	 * 
	 * @param context
	 */
	@SuppressLint("NewApi")
	public static void exitApp(Context context) {
		Logger.d("BaseApplication exit");
		
		stopTask();
		// 退出所有Activity或Service
		clearContextStack();
		// 清除应用信息
		BaseApplication.getInstance().exit();
		BaseApplication.getInstance().clearApp();
		
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(ACTIVITY_SERVICE);
		activityManager.killBackgroundProcesses(context.getPackageName());
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

	/**
	 * when the application exit, we should stop all task which is running in
	 * the task list
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	private static void stopTask() {
		//停止所有任务
		if (taskList.size() == 0 || taskList == null) {
			return;
		}
		for (UserTask userTask : taskList) {
			if (userTask.getStatus() != AsyncTask.Status.FINISHED) {
				userTask.cancel(true);
			}
		}
		taskList.clear();
	}
	
	/**
	 * 清除Activity堆栈
	 * <p>Title: clearActivityStack
	 * <p>Description:
	 */
	public static void clearContextStack(){
		for (int i = 0; i < totalList.size(); i++) {
			if (totalList.get(i) instanceof Activity) {
				((Activity) totalList.get(i)).finish();
			}else if(totalList.get(i) instanceof BaseService){
				BaseService service = ((BaseService) totalList.get(i));
				service.exitApp();
				if (service.isAutoFinish()) {
					service.stopSelf();
				}
			}
		}
		totalList.clear();
	}

	/**
	 * 清除service
	 * <p>Title: clearActivityStack
	 * <p>Description:
	 */
	public static void clearService(){
		for (int i = 0; i < totalList.size(); i++) {
			if (totalList.get(i) instanceof BaseTask) {
				((BaseTask) totalList.get(i)).onTrimMemory();
			}else if(totalList.get(i) instanceof BaseService){
				BaseService service = ((BaseService) totalList.get(i));
				if (service.isAutoFinish()) {
					service.exitApp();
					service.stopSelf();
				}
				totalList.remove(i);
			}
		}
	}

	/**
	 * 当前应用Activity是否存在
	 * @param context
	 * @return true表示不存在
	 */
	public static boolean isActivityDead(Context context) {
		int num = 0;
		if (totalList != null){
			for (BaseTask task: totalList){
				if (task instanceof Activity){
					num++;
				}
			}
		}

		if (num == 0){
			return true;
		}
		return false;
	}


	/**
	 * 注册全局Activity和Fragment的生命周期监听器
	 */
	private void registerContextCallback(){
		LifecycleDispatcher.get().registerActivityLifecycleCallbacks(this, new ActivityLifecycleCallbacksCompat() {
			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
				if (BaseApplication.instance.isLockScreen()) {
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				}

				if (BaseApplication.DEBUG_MODE) {
					Logger.init(activity.getClass().getSimpleName()).setLogLevel(LogLevel.FULL).hideThreadInfo();
				} else {
					Logger.init(activity.getClass().getSimpleName()).setLogLevel(LogLevel.NONE).hideThreadInfo();
				}

				if (activity instanceof BaseTask){
					BaseTask task = (BaseTask) activity;
					BaseApplication.totalList.add(task);
				}

				if (activityLifecycleCallbacks != null){
					activityLifecycleCallbacks.onActivityCreated(activity, savedInstanceState);
				}
			}

			@Override
			public void onActivityStarted(Activity activity) {
				if (activityLifecycleCallbacks != null){
					activityLifecycleCallbacks.onActivityStarted(activity);
				}
			}

			@Override
			public void onActivityResumed(Activity activity) {
				if (activityLifecycleCallbacks != null){
					activityLifecycleCallbacks.onActivityResumed(activity);
				}
			}

			@Override
			public void onActivityPaused(Activity activity) {
				if (activityLifecycleCallbacks != null){
					activityLifecycleCallbacks.onActivityPaused(activity);
				}
			}

			@Override
			public void onActivityStopped(Activity activity) {
				if (activityLifecycleCallbacks != null){
					activityLifecycleCallbacks.onActivityStopped(activity);
				}
			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
				if (activityLifecycleCallbacks != null){
					activityLifecycleCallbacks.onActivitySaveInstanceState(activity, outState);
				}
			}

			@Override
			public void onActivityDestroyed(Activity activity) {
				if (activity instanceof BaseTask){
					BaseTask task = (BaseTask) activity;
					BaseApplication.totalList.remove(task);
				}

				BaseApplication.getRefWatcher(BaseApplication.this).watch(activity);
				if (activityLifecycleCallbacks != null){
					activityLifecycleCallbacks.onActivityDestroyed(activity);
				}
			}
		});

		LifecycleDispatcher.get().registerFragmentLifecycleCallbacks(this, new FragmentLifecycleCallbacks(){
			@Override
			public void onFragmentDestroyed(Fragment fragment) {
				if (fragment instanceof BaseTask){
					BaseTask task = (BaseTask) fragment;
					BaseApplication.totalList.remove(task);
				}
				BaseApplication.getRefWatcher(BaseApplication.this).watch(fragment);
				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentDestroyed(fragment);
				}
			}

			@Override
			public void onFragmentAttach(Fragment fragment, Context context) {
				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentAttach(fragment, context);
				}
			}

			@Override
			public void onFragmentDetach(Fragment fragment) {
				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentDetach(fragment);
				}
			}

			@Override
			public void onFragmentActivityCreated(Fragment fragment, Bundle savedInstanceState) {
				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentActivityCreated(fragment, savedInstanceState);
				}
			}

			@Override
			public void onFragmentCreateView(Fragment fragment, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentCreateView(fragment, inflater, container, savedInstanceState);
				}
			}

			@Override
			public void onFragmentDestroyView(Fragment fragment) {
				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentDestroyView(fragment);
				}
			}

			@Override
			public void onFragmentViewCreated(Fragment fragment, View view, Bundle savedInstanceState) {
				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentViewCreated(fragment, view, savedInstanceState);
				}
			}

			@Override
			public void onFragmentCreated(Fragment fragment, Bundle savedInstanceState) {
				if (fragment instanceof BaseTask){
					BaseTask task = (BaseTask) fragment;
					BaseApplication.totalList.remove(task);
				}
				if (BaseApplication.DEBUG_MODE) {
					Logger.init(fragment.getClass().getSimpleName()).setLogLevel(LogLevel.FULL).hideThreadInfo();
				} else {
					Logger.init(fragment.getClass().getSimpleName()).setLogLevel(LogLevel.NONE).hideThreadInfo();
				}

				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentCreated(fragment, savedInstanceState);
				}
			}

			@Override
			public void onFragmentStarted(Fragment fragment) {
				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentStarted(fragment);
				}
			}

			@Override
			public void onFragmentResumed(Fragment fragment) {
				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentResumed(fragment);
				}
			}

			@Override
			public void onFragmentPaused(Fragment fragment) {
				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentPaused(fragment);
				}
			}

			@Override
			public void onFragmentStopped(Fragment fragment) {
				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentStopped(fragment);
				}
			}

			@Override
			public void onFragmentSaveInstanceState(Fragment fragment, Bundle outState) {
				if (fragmentLifecycleCallbacks != null){
					fragmentLifecycleCallbacks.onFragmentSaveInstanceState(fragment, outState);
				}
			}
		});
	}
}
