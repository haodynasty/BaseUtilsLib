package com.plusub.lib.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.plusub.lib.event.NetStateChangeEvent;
import com.plusub.lib.util.NetStateUtils;
import com.plusub.lib.util.NetStateUtils.NetWorkState;

import org.greenrobot.eventbus.EventBus;

public class NetStateReceiver extends BroadcastReceiver{

	// 侦听网络状态的变化
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			// 判断当前网络是否已经连接
			if (info.getState() == NetworkInfo.State.CONNECTED) {
				EventBus.getDefault().post(new NetStateChangeEvent(true, NetStateUtils.getNetWorkConnectionType(context)));
			} else {
				EventBus.getDefault().post(new NetStateChangeEvent(false, NetWorkState.NONE));
			}
		} else {
			EventBus.getDefault().post(new NetStateChangeEvent(false, NetWorkState.NONE));
		}
	}
}
