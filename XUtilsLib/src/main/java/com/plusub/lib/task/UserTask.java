package com.plusub.lib.task;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.service.BaseService;

import android.os.AsyncTask;

/**
 * This class is used to handle the concurrent thread, if you want do something time-consuming,
 * please extends this class, it can help you manager the threads. 
 * @author qh
 *
 * @param <Params> 泛型参数
 * @param <Progress> 进度值（执行过程中返回的值）
 * @param <Result> 返回值（执行完返回的值）
 */
public abstract class UserTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	
	@Override
	protected void onPostExecute(Result result) {
		// TODO Auto-generated method stub
		BaseApplication.taskList.remove(this);
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		BaseApplication.taskList.add(this);
	}

	
	

}
