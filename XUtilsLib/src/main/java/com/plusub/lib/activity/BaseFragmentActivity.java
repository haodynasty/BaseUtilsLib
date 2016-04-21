/*
 * FileName: BaseFragmentActivity.java
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
 * date     : 2014-6-11 下午12:06:15
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.annotate.AnnotateUtil;
import com.plusub.lib.constant.ErrorCode;
import com.plusub.lib.task.DataRefreshTask;
import com.plusub.lib.task.TaskMessage;
import com.plusub.lib.util.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public abstract class BaseFragmentActivity extends FragmentActivity implements 
	DataRefreshTask, BaseUITask, OnClickListener, IActivity{

	protected BaseApplication mApplication;
	/**日志打印TAG*/
	protected String TAG;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		if (BaseApplication.instance.isLockScreen()) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  
		}
		TAG = getClass().getName();
		setOverflowShowingAlways();
		mApplication = (BaseApplication) getApplication();
		
		BaseApplication.refreshList.add(this);

		setRootView(); // 必须放在annotate之前调用
		AnnotateUtil.initBindView(this); // 必须放在initialization之前调用
		initialize();
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		initView();
		initData();
		initEvent();
	}
	
	@Override
	public void initView() {}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		BaseApplication.refreshList.remove(this);
	}

	
	/**
	 * 通过Class跳转界面
	 * <p>Title: startActivity
	 * <p>Description: 
	 * @param cls
	 */
	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	/**
	 * 含有Bundle通过Class跳转界面
	 * <p>Title: startActivity
	 * <p>Description: 
	 * @param cls
	 * @param bundle
	 */
	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/**
	 * 通过Action跳转界面
	 * <p>Title: startActivity
	 * <p>Description: 
	 * @param action
	 */
	protected void startActivity(String action) {
		startActivity(action, null);
	}

	/**
	 * 含有Bundle通过Action跳转界面
	 * <p>Title: startActivity
	 * <p>Description: 
	 * @param action
	 * @param bundle
	 */
	protected void startActivity(String action, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}
	
	/**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    public void skipActivity(Activity aty, Class<?> cls) {
    	startActivity(cls);
        aty.finish();
    }


    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    public void skipActivity(Activity aty, Class<?> cls, Bundle extras) {
    	startActivity(cls, extras);
        aty.finish();
    }
    
    /**
	 * 解决Menu在低版本不显示的问题（物理按钮menu）
	 * <p>Title: setOverflowShowingAlways
	 * <p>Description:
	 */
	@SuppressLint("NewApi")
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解决overflow不显示图标的问题
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

    /**
	 * 在使用过程中只需要判断ErrorCode.DEFAULT_VALUE（正常），不正常则显示提示
	 */
	@Override
	public void refresh(TaskMessage msg, Object... param) {
		// TODO Auto-generated method stub
		int errorCode = msg.errorCode;
		switch(errorCode){
			case ErrorCode.DEFAULT_VALUE:
				break;
			case ErrorCode.PARA_EXCEPTION:
				LogUtils.e(this.getClass().getName(), "input params error");
				break;
			case ErrorCode.NET_LINK_EXCEPTION:
				LogUtils.e(this.getClass().getName(), "net connect error");
				break;
			case ErrorCode.PARSER_JSON_EXCEPTION:
				LogUtils.e(this.getClass().getName(), "json parser exception");
				break;
			case ErrorCode.PARSER_CLASS_NOT_FOUND:
				LogUtils.e(this.getClass().getName(), "class parser exception");
				break;
			case ErrorCode.OTHER_DEFAULT_EXCEPTION:
				LogUtils.e(this.getClass().getName(), "other exception");
				break;
			case ErrorCode.OTHER_TASK_NOT_FOUND:
				LogUtils.e(this.getClass().getName(), "main service task not found exception");
				break;
			case ErrorCode.SESSION_OUT_OF_TIME:
				LogUtils.e(this.getClass().getName(), "session out of time");
				break;
			default:
				break;
		}
	}
	
	
}
