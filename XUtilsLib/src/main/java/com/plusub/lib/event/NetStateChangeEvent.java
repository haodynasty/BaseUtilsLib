package com.plusub.lib.event;

import com.plusub.lib.util.NetStateUtils.NetWorkState;

/**
 * 网络状态变化事件监听
 * @author blakequ Blakequ@gmail.com
 *
 */
public class NetStateChangeEvent {
	
	private boolean netState;
	private NetWorkState netType;

	/**
	 * 返回网络类型，ConnectivityManager.TYPE_WIFI，ConnectivityManager.TYPE_MOBILE，不可用返回-1
	 * <p>Title: 
	 * <p>Description: 
	 * @param netState
	 * @param netType
	 */
	public NetStateChangeEvent(boolean netState, NetWorkState netType){
		this.netState = netState;
		this.netType = netType;
	}
	
	public boolean isNetState() {
		return netState;
	}

	/**
	 * 返回网络类型，NetWorkState.WIFI，NetWorkState.MOBILE，不可用返回NetWorkState.NONE
	 * <p>Title: getNetType
	 * <p>Description: 
	 * @return
	 */
	public NetWorkState getNetType() {
		return netType;
	}

	public void setNetType(NetWorkState netType) {
		this.netType = netType;
	}
}
