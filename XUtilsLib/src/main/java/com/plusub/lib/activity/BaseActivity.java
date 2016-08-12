package com.plusub.lib.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;

import com.plusub.lib.activity.lifecycle.LifecycleDispatcher;
import com.plusub.lib.annotate.AnnotateUtil;
import com.plusub.lib.util.GarbageUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * all Activity must extends the class (not include listActivity)
 * 使用注意事项：<br>
 * <b>1.必须实现函数{@link #provideContentViewId} or {@link #provideContentViewLayout} 其返回值不一样，设置root视图<br>
 * <b>2.基类提供屏幕长宽，可直接调用<br>
 * <b>3.如果需要显示toast（调用ViewInjectUtils），loadingdialog可以直接调用<br>
 * <b>4.监听网络变化，使用方法onEventMainThread(NetStateChangeEvent event)即可,并注册EventBus.register(this);
 * <b>5.初始化视图采用AnnotateUtil注入框架，可替代findViewById， 使用方法简单直接,如下(click是该组件的单击属性)：<br>
 *		.@BindView(id = R.id.more1, click = true)<br>
 *  	private Button btn1;
 * <br>
 * @author blakequ Blakequ@gmail.com
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements OnClickListener, IActivity, BaseTask{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LifecycleDispatcher.get().onActivityCreated(this, savedInstanceState);

		if (provideContentViewId() != 0){
			setContentView(provideContentViewId());
		}else if (provideContentViewLayout() != null){
			setContentView(provideContentViewLayout());
		}else {
			throw new IllegalStateException("No main layout, you should set provideContentViewId or provideContentViewLayout");
		}

		setOverflowShowingAlways();
		AnnotateUtil.initBindView(this); // 必须放在initialization之前调用
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		View rootView = GarbageUtils.getRootView(this);
		if (rootView != null){
			GarbageUtils.unBindDrawables(rootView);
			GarbageUtils.unBindListener(rootView);
		}
		LifecycleDispatcher.get().onActivityDestroyed(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		LifecycleDispatcher.get().onActivityStarted(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		LifecycleDispatcher.get().onActivityResumed(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		LifecycleDispatcher.get().onActivityPaused(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		LifecycleDispatcher.get().onActivityStopped(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		LifecycleDispatcher.get().onActivitySaveInstanceState(this, outState);
	}


	@Override
	public View provideContentViewLayout(){
		return null;
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
}
