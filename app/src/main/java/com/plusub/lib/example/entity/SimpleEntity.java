/*
 * FileName: SimpleEntity.java
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
 * date     : 2014-12-12 下午8:08:13
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.entity;


/**
 * @ClassName: SimpleEntity
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-12 下午8:08:13<br>
 *     <b>最后修改时间：</b>2014-12-12 下午8:08:13
 * @version v1.0
 */
public class SimpleEntity {

	private String header;
	private String content;
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "SimpleEntity [header=" + header + ", content=" + content + "]";
	}
	
	

}
