package com.plusub.lib.task;

import java.util.Map;

/**
 * 程序处理逻辑任务
 * @author blakequ Blakequ@gmail.com
 *
 */
public class TaskEntity {
	
	private int taskID;// 任务编号
	private Map taskParam;// 任务参数
	private Object taskObj; //其他任务对象
	private DataRefreshTask refreshTask; //更新请求对象
	
	public TaskEntity(DataRefreshTask refreshTask) {
		super();
		this.refreshTask = refreshTask;
	}
	
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public Map getTaskParam() {
		return taskParam;
	}
	public void setTaskParam(Map taskParam) {
		this.taskParam = taskParam;
	}
	public Object getTaskObj() {
		return taskObj;
	}
	public void setTaskObj(Object taskObj) {
		this.taskObj = taskObj;
	}
	public DataRefreshTask getRefreshTask() {
		return refreshTask;
	}
	public void setRefreshTask(DataRefreshTask refreshTask) {
		this.refreshTask = refreshTask;
	}
}
