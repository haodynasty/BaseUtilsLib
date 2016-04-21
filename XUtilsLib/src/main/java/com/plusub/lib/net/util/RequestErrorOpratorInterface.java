/*
 * FileName: RequestErrorOprator.java
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
 * date     : 2014-11-17 下午5:59:11
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.net.util;

import com.plusub.lib.task.TaskEntity;

/**
 * 请求任务错误处理(通用处理)
 * @author service@plusub.com
 *
 */
public interface RequestErrorOpratorInterface {

	/**
	 * 根据错误码进行错误处理
	 * <p>Title: doErrorOprator
	 * <p>Description: 
	 * @param taskId 任务ID
	 * @param errorCode 错误码com.plusub.lib.constant.ErrorCode
	 * @param task 发送错误的任务实体
	 * @return
	 */
	public boolean doErrorOprator(int taskId, int errorCode, TaskEntity task);
}
