/*
 * FileName: ScreenObserver.java
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
 * date     : 2014-7-14 下午3:11:02
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * 包装屏幕开启关闭事件
 * 使用时调用 {@link #requestScreenStateUpdate(ScreenStateListener)}方法注册监听器, 
 * 在结束之后在onDestory方法中调用 {@link #stopScreenStateUpdate()}方法停止监听
 * <p>
 * 实现方法：<p>
 * 1）通过BroadcastReceiver接收广播Intent.ACTION_SCREEN_ON和Intent.ACTION_SCREEN_OFF可以判断屏幕状态是否锁屏，但是只有屏幕状态发生改变时才会发出广播；<p>
 * 2）如果要在屏幕状态发生改变之前就想获取屏幕状态，可以通过反射机制调用PowerManager的isScreenOn方法 。
 * @author blakequ Blakequ@gmail.com
 */
public class ScreenObserver{
	private static String TAG = ScreenObserver.class.getSimpleName();
	private Context mContext;
	private ScreenBroadcastReceiver mScreenReceiver;
    private ScreenStateListener mScreenStateListener;
    private static Method mReflectScreenState;
    
	public ScreenObserver(Context context){
		mContext = context;
		mScreenReceiver = new ScreenBroadcastReceiver();
		try {
			mReflectScreenState = PowerManager.class.getMethod("isScreenOn",
					new Class[] {});
		} catch (NoSuchMethodException nsme) {
			Log.d(TAG, "API < 7," + nsme);
		}
	}

	/**
	 * screen状态广播接收者
	 */
	private class ScreenBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
				mScreenStateListener.onScreenOn();
			} else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
				mScreenStateListener.onScreenOff();
			} else if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
				mScreenStateListener.onUserPresent();
			}
		}
	}

	/**
	 * 请求screen状态更新,需配合{@link #stopScreenStateUpdate()}使用
	 */
	public void requestScreenStateUpdate(ScreenStateListener listener) {
		mScreenStateListener = listener;
		startScreenBroadcastReceiver();
		firstGetScreenState();
	}

	/**
	 * 第一次请求screen状态
	 */
	private void firstGetScreenState() {
		PowerManager manager = (PowerManager) mContext
				.getSystemService(Activity.POWER_SERVICE);
		if (isScreenOn(manager)) {
			if (mScreenStateListener != null) {
				mScreenStateListener.onScreenOn();
			}
		} else {
			if (mScreenStateListener != null) {
				mScreenStateListener.onScreenOff();
			}
		}
	}

	/**
	 * 停止screen状态更新
	 */
	public void stopScreenStateUpdate() {
		mContext.unregisterReceiver(mScreenReceiver);
	}

	/**
	 * 启动screen状态广播接收器
	 */
	private void startScreenBroadcastReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		mContext.registerReceiver(mScreenReceiver, filter);
	}

	/**
	 * screen是否打开状态
	 */
	private static boolean isScreenOn(PowerManager pm) {
		boolean screenState = false;
		try {
			if (mReflectScreenState != null){
				screenState = (Boolean) mReflectScreenState.invoke(pm);
			}
		} catch (Exception e) {
			screenState = false;
		}
		return screenState;
	}

	// 外部调用接口
	public interface ScreenStateListener {
		public void onScreenOn();

		public void onScreenOff();

		public void onUserPresent();
	}

	/**
	 * 是否锁屏
	 * @param c
	 * @return
	 */
	public final static boolean isScreenLocked(Context c) {
		android.app.KeyguardManager mKeyguardManager = (KeyguardManager) c
				.getSystemService(c.KEYGUARD_SERVICE);
		return mKeyguardManager.inKeyguardRestrictedInputMode();
	}
}
