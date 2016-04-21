package com.plusub.lib.task;



public interface DataRefreshTask {
	/**
	 * 数据更新操作
	 * <p>Title: refresh
	 * <p>Description: 
	 * @param msg 消息基本参数
	 * @param param
	 */
	void refresh(TaskMessage msg, Object ...param);
}
