/*
 * FileName: StatusEntity.java
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
 * date     : 2015-4-19 下午4:15:29
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.bean;

import com.plusub.lib.annotate.JsonParserField;

/**
 * 返回实体状态判断，如果json返回的key名字和字段status,message不一致需要重写@JsonParserField
 * @ClassName: StatusEntity
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-4-19 下午4:15:29<br>
 *     <b>最后修改时间：</b>2015-4-19 下午4:15:29
 * @version v1.0
 */
public class StatusEntity {
	@JsonParserField(defaultValue="0")
	protected int status;
	@JsonParserField(defaultValue="")
	protected String message;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "StatusEntity [status=" + status + ", message=" + message + "]";
	}
	
}
