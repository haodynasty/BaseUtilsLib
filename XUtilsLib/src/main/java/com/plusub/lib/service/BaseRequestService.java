/*
 * FileName: BaseRequestService.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : service@plusub.com
 * date     : 2014-11-17 下午5:05:08
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.plusub.lib.BuildConfig;
import com.plusub.lib.annotate.JsonParserUtils;
import com.plusub.lib.constant.ErrorCode;
import com.plusub.lib.net.util.RequestEntity;
import com.plusub.lib.net.util.RequestEntity.RequestType;
import com.plusub.lib.net.util.RequestErrorOpratorInterface;
import com.plusub.lib.net.util.RequestManager;
import com.plusub.lib.net.util.RequestMapInterface;
import com.plusub.lib.net.util.RequestParams;
import com.plusub.lib.parser.ParserCheckInterface;
import com.plusub.lib.task.TaskEntity;
import com.plusub.lib.task.TaskMessage;
import com.plusub.lib.task.UserTask;
import com.plusub.lib.util.CommException;
import com.plusub.lib.util.JSONUtils;
import com.plusub.lib.util.logger.LogLevel;
import com.plusub.lib.util.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求后台服务
 * 使用注意事项：
 * <li>必须实现抽象方法<h1> com.plusub.lib.net.util.RequestMap.addTaskToMap()</h1>，将所有任务添加到map</li>
 * <li>选择性实现接口方法doErrorOprator，集中处理请求错误</li>
 * <li>要请求必须首先添加网络请求任务addNewTask</li>
 * @author service@plusub.com
 *
 */
public abstract class BaseRequestService extends BaseService implements RequestErrorOpratorInterface, RequestMapInterface
	,ParserCheckInterface{
	
	public static boolean isrun = false; // 服务子进程
	/**保存任务对象*/
	public static ArrayList<TaskEntity> allRequestTask = new ArrayList<TaskEntity>(); 
	/**请求映射<TaskId, RequestLink>,任务id和请求链接的映射关系*/
	public static final Map<Integer, RequestEntity> RequestMap = new HashMap<Integer, RequestEntity>(); 
	private RequestManager requestManager;
	
	@Override
	public void refresh(TaskMessage msg, Object... param) {
		// TODO Auto-generated method stub
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		isrun = true;
		requestManager = new RequestManager();
		addTaskToMap();
		
		if(Build.VERSION.SDK_INT >= 11){
		     new ServiceTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}else{
		     new ServiceTask().execute();
		}

		if (BuildConfig.DEBUG) {
			Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.FULL).hideThreadInfo();
		} else {
			Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.NONE).hideThreadInfo();
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isrun = false;
		RequestMap.clear();
	}
	
	/**
	 * 添加任务，必须首先添加任务到任务列表中才能发送请求到服务器
	 * <p>Title: addNewTask
	 * <p>Description: add task to task list
	 * @param ts
	 */
	public static void addNewTask(TaskEntity ts) {
		allRequestTask.add(ts);
	}
	
	/**
	 * 移除任务
	 * <p>Title: removeTask
	 * <p>Description: 
	 * @param ts
	 */
	public static void removeTask(TaskEntity ts){
		if (ts != null && allRequestTask.contains(ts)) {
			allRequestTask.remove(ts);
		}
	}
	
	/**
	 * 移除任务
	 * <p>Title: removeTask
	 * <p>Description: 
	 * @param taskId
	 */
	public static void removeTask(int taskId){
		int size = allRequestTask.size();
		for (int i = size-1; i >= 0; i--) {
			if (allRequestTask.get(i).getTaskID() == taskId) {
				allRequestTask.remove(i);
			}
		}
	}
	
	/**
	 * 处理任务
	 * 
	 * @author blakequ Blakequ@gmail.com
	 * 
	 */
	private class ServiceTask extends UserTask<String, Object, Boolean> {
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			while (isrun) {
				TaskEntity lasttask = null;
				// 注意一定是要异步接任务
				synchronized (allRequestTask) {
					if (allRequestTask.size() > 0) {
						lasttask = allRequestTask.get(0);
						doTask(lasttask);
					}
				}
				if (isCancelled()){ 
					isrun = false;
					break;
				}
				// 睡眠
				sleep(100);
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Object... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			if (values[0] instanceof TaskMessage) {
				sendDataToActivity((TaskMessage)values[0]);
			}else if(values[0] instanceof TaskEntity){
				TaskEntity task = (TaskEntity) values[0];
				doErrorOprator(task.getTaskID(), (Integer)values[1], task);
			}
		}
		
		/**
		 * 将返回数据返回界面
		 * <p>Title: sendDataToActivity
		 * <p>Description: 
		 * @param msg
		 */
		private void sendDataToActivity(TaskMessage msg) {
			// TODO Auto-generated method stub
			if (msg.refreshTask != null) {
				msg.refreshTask.refresh(msg);
			}
		}

		private void sleep(long time) {
			try {
				Thread.sleep(time);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 启动任务
		 * <p>Title: doTask
		 * <p>Description: 
		 * @param task
		 */
		private void doTask(TaskEntity task) {
			// TODO Auto-generated method stub
			TaskMessage msg = new TaskMessage();
			msg.errorCode = ErrorCode.DEFAULT_VALUE;
			Map<String, Object> map = task.getTaskParam();
			if (map != null) {
				msg.data = transmitParams(map);
			}
			try {
				msg.obj = getServerResult(task.getTaskID(), (RequestParams)task.getTaskObj(), msg);
			}catch (CommException e) {
				e.printStackTrace();
				msg.errorCode = e.getStatusCode();
				Logger.e("[MainService] " + msg.errorCode + " CommException[MainService]");
				
				//bug:修复bug，doErrorOprator是在线程中运行，不能更新UI的问题
				publishProgress(task, e.getStatusCode());
//				doErrorOprator(task.getTaskID(), e.getStatusCode(), task);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msg.errorCode = ErrorCode.PARSER_JSON_EXCEPTION;
				Logger.e("[MainService] "+msg.errorCode + " JSONException");
				publishProgress(task, ErrorCode.PARSER_JSON_EXCEPTION);
//				doErrorOprator(task.getTaskID(), ErrorCode.PARSER_JSON_EXCEPTION, task);
			}catch(Exception e){
				e.printStackTrace();
				msg.errorCode = ErrorCode.OTHER_DEFAULT_EXCEPTION;
				Logger.e("[MainService] "+msg.errorCode+" "+e.getCause());
				publishProgress(task, ErrorCode.OTHER_DEFAULT_EXCEPTION);
//				doErrorOprator(task.getTaskID(), ErrorCode.OTHER_DEFAULT_EXCEPTION, task);
			}
			msg.what = task.getTaskID();
			msg.refreshTask = task.getRefreshTask();
			publishProgress(msg);
			allRequestTask.remove(task);// 完成任务
		}
	}
	
	/**
	 * 获取服务器返回的实体结果
	 * <p>Title: getServerResult
	 * <p>Description: 
	 * @param taskId
	 * @param params
	 * @return
	 * @throws CommException
	 * @throws JSONException
	 */
	private Object getServerResult(int taskId, RequestParams params, TaskMessage msg) throws CommException, JSONException{
		RequestEntity request = null;
		String result = "";
		Object objResult = null;
		if (RequestMap.containsKey(taskId)) {
			request = RequestMap.get(taskId);
			try {
				if (request.getType() == RequestType.GET) {//get请求
					if (!request.isCache()) {
						result = requestManager.doGet(request.getRequestUrl(), params);
					}else{
						result = requestManager.doGetByCache(request.getRequestUrl(), params, request.getCacheTime());
					}
				}else{//post请求
					result = requestManager.doPost(request.getRequestUrl(), params);
				}
				Logger.d("Request", "["+request.getRequestUrl()+"]"+ result);
				
				JSONObject jo = new JSONObject(result);
				msg.status = Integer.parseInt(JSONUtils.getString(jo, "status", "0"));
				msg.message = JSONUtils.getString(jo, "msg", "");
				//保存json原始数据, 前提是map传入是不为空
				if (msg.data != null) {
					msg.data.putString("json", result);
				}
				
				if (checkJsonError(jo)) {
					objResult = JsonParserUtils.initEntityParser(request.getClassEntity(), result);
				}
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CommException("获取解析类异常JSONException", ErrorCode.PARSER_JSON_EXCEPTION);
			}catch(CommException e){
				e.printStackTrace();
				throw new CommException(e.getMessage(), e.getStatusCode());
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CommException("其他异常Exception", ErrorCode.OTHER_DEFAULT_EXCEPTION);
			} 
			
		}else{
			throw new CommException("没有在RequestConstant中配置任务的映射", ErrorCode.OTHER_TASK_NOT_FOUND);
		}
		return objResult;
	}
	
	/**
	 * 参数转换
	 * <p>Title: transmitParams
	 * <p>Description: 
	 * @param map
	 * @return
	 */
	private Bundle transmitParams(Map map){
		String keys;
		if (map != null && map.size() > 0) {
			Bundle bd = new Bundle();
			for (Object key : map.keySet()) {
				//键必须是String类型
				if (key instanceof String) {
					keys = (String) key;
					//值类型判断
					Object obj = map.get(key);
					if (obj instanceof String) {
						bd.putString(keys, (String)obj);
					}else if(obj instanceof Integer){
						bd.putInt(keys, (Integer)obj);
					}else if(obj instanceof Boolean){
						bd.putBoolean(keys, (Boolean)obj);
					}else if(obj instanceof Serializable){
						bd.putSerializable(keys, (Serializable)obj);
					}else if(obj instanceof Character){
						bd.putChar(keys, (Character)obj);
					}else if(obj instanceof Double){
						bd.putDouble(keys, (Double)obj);
					}else if(obj instanceof Float){
						bd.putFloat(keys, (Float)obj);
					}else{
						Logger.e("[MainService] : unknow map values type ! keys:"+keys);
					}
				}
			}
			return bd;
		}
		return null;
	}
}
