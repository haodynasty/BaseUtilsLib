package com.plusub.lib.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.BuildConfig;
import com.plusub.lib.annotate.AnnotateUtil;
import com.plusub.lib.constant.ErrorCode;
import com.plusub.lib.task.DataRefreshTask;
import com.plusub.lib.task.TaskMessage;
import com.plusub.lib.util.LogUtils;
import com.plusub.lib.util.logger.LogLevel;
import com.plusub.lib.util.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * all Activity must extends the class (not include listActivity)
 * 使用注意事项：<br>
 * <b>1.必须实现函数{@link #setRootView}}，设置root视图<br>
 * <b>2.系统会自动调用{@link #initView, initData, initEvent}, 无需手动调用}<br>
 * <b>3.在initView(), initData(), initEvent()中需要时实现不同的功能，分别是：初始化视图view，初始化视图数据，初始化事件如点击事件等<br>
 * <b>4.基类提供屏幕长宽，可直接调用<br>
 * <b>5.如果需要显示toast（调用ViewInjectUtils），loadingdialog可以直接调用<br>
 * <b>6.监听网络变化，使用方法onEventMainThread(NetStateChangeEvent event)即可,并注册EventBus.register(this);
 * <b>7.初始化视图采用AnnotateUtil注入框架，可替代findViewById， 使用方法简单直接,如下(click是该组件的单击属性)：<br>
 *		.@BindView(id = R.id.more1, click = true)<br>
 *  	private Button btn1;
 * <br>
 * @author blakequ Blakequ@gmail.com
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements OnClickListener,
	DataRefreshTask, BaseUITask, IActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (BaseApplication.instance.isLockScreen()) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  
		}
		if (BuildConfig.DEBUG) {
			Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.FULL).hideThreadInfo();
		} else {
			Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.NONE).hideThreadInfo();
		}
		setOverflowShowingAlways();
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
	public void initView() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		BaseApplication.refreshList.remove(this);
		BaseApplication.getRefWatcher(this).watch(this);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
	}

	public void replaceFragment(int id_content, Fragment fragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(id_content, fragment);
		transaction.commit();
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

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
