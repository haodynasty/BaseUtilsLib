/*
 * FileName: RequestService.java
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
 * date     : 2014-12-11 下午6:41:48
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.service;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.plusub.lib.example.entity.BookEntity;
import com.plusub.lib.example.http.RequestTaskConstant;
import com.plusub.lib.net.util.RequestEntity;
import com.plusub.lib.net.util.RequestEntity.RequestType;
import com.plusub.lib.service.BaseRequestService;
import com.plusub.lib.task.TaskEntity;
import com.plusub.lib.util.CommException;

/**
 * @ClassName: RequestService
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-11 下午6:41:48<br>
 *     <b>最后修改时间：</b>2014-12-11 下午6:41:48
 * @version v1.0
 */
public class RequestService extends BaseRequestService {

	private static String PATH = "https://api.douban.com";
	
	public static void startService(Context context){
		Intent intent = new Intent(context, RequestService.class);
		context.startService(intent);
	}
	
	@Override
	public void addTaskToMap() {
		// TODO Auto-generated method stub
		RequestMap.put(RequestTaskConstant.TASK_DOUBAN, new RequestEntity(PATH+"/v2/book/search?", BookEntity.class, RequestType.GET, true, 10));
	}

	@Override
	public void exitApp() {
		// TODO Auto-generated method stub
		RequestMap.clear();
		stopSelf();
	}
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAutoFinish() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean checkJsonError(JSONObject jo) throws CommException {
		// TODO Auto-generated method stub
		//必须实现
//		int status = Integer.parseInt(JSONUtils.getString(arg0, "status",
//				"200"));
//		if (status == 200) {
//			return true;
//		} else if (status == 10) {
//			throw new CommException("token expire " + status,
//					ErrorCode.CODE_TOKEN_EXPIRE);
//		} else if(status - 500==0){
//			throw new CommException("time exception " + status,
//					ErrorCode.SESSION_OUT_OF_TIME);
//		}else if (status > 0) {
//			return false;
//		} else {
//			throw new CommException("unknow exception " + status,
//					ErrorCode.OTHER_DEFAULT_EXCEPTION);
//		}
		return true;
	}

	@Override
	public boolean doErrorOprator(int taskId, int errorCode, TaskEntity task) {
		// TODO Auto-generated method stub
		// 举例session失效，模拟登陆重新获取session
//		if (errorCode == ErrorCode.SESSION_OUT_OF_TIME) {
//			String phone = PreferencesUtils.getString(this,PreferenceConfig.PHONE_NUM);
//			String pwd = PreferencesUtils.getString(this,PreferenceConfig.PASSWORD);
//			if (!StringUtils.isEmpty(pwd) && !StringUtils.isEmpty(phone)) {
//				//发送登陆请求
//				login(phone, pwd);
//				errorTask = task;
//			}
//			return true;
//		}
		return false;
	}

}
