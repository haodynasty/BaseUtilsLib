package com.plusub.lib.service;

import android.app.Service;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.BuildConfig;
import com.plusub.lib.task.DataRefreshTask;
import com.plusub.lib.util.logger.LogLevel;
import com.plusub.lib.util.logger.Logger;

/**
 * 所有Service需要继承该方法（注：在App退出时会关闭所有继承BaseService的Service），
 * 如果在App退出不需要关闭该服务，不需要继承该服务
 * running background using for keep watch on the user task and activity
 * @author blakequ Blakequ@gmail.com
 * 
 */
public abstract class BaseService extends Service implements DataRefreshTask{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		if (BuildConfig.DEBUG) {
			Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.FULL).hideThreadInfo();
		} else {
			Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.NONE).hideThreadInfo();
		}
		BaseApplication.refreshList.add(this);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
}
