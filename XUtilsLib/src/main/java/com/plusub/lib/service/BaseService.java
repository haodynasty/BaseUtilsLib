package com.plusub.lib.service;

import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.task.DataRefreshTask;
import com.plusub.lib.util.LogUtils;

/**
 * 所有Service需要继承该方法（注：在App退出时会关闭所有继承BaseService的Service），
 * 如果在App退出不需要关闭该服务，不需要继承该服务
 * running background using for keep watch on the user task and activity
 * @author blakequ Blakequ@gmail.com
 * 
 */
public abstract class BaseService extends Service implements DataRefreshTask{

	/**日志打印TAG*/
	protected String TAG;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		TAG = getClass().getName();
		LogUtils.d(TAG, "start BaseService");
		BaseApplication.refreshList.add(this);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtils.d(TAG, "stop BaseService");
		BaseApplication.refreshList.remove(this);
	}
	
	/**
	 * 在退出App时会自动调用
	 * <ul>如：可以退出service，清除状态等工作</ul>
	 * <p>Title: exitApp
	 * <p>Description:
	 */
	public abstract void exitApp();
	
	/**
	 * 退出App时是否自动结束该Service
	 * <p>Title: isAutoFinish
	 * <p>Description: 
	 * @return true 如果退出app时结束该Service
	 */
	public abstract boolean isAutoFinish();
	
	/**
	 * <p>Title: isWorked
	 * <p>Description:  服务是否启动
	 * @param context
	 * @param className 要检查的服务名{@link #getClassName()}
	 * @return
	 */
	public static boolean isServiceWorked(Context context, String className) {
		ActivityManager myManager = (ActivityManager) context.getApplicationContext().getSystemService(
						Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString()
					.equals(className)) {
				return true;
			}
		}
		return false;
	}

}
