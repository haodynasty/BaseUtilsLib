/*
 * FileName: StringEntity.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : quhao <blakequ@gmail.com>
 * date     : 2014-6-9 下午2:13:23
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.imagebroswer;


public class StringEntity {
	private String content;
	private String subDetail;
	
	public StringEntity(){}

	public StringEntity(String content) {
		super();
		this.content = content;
	}
	
	public StringEntity(String content, String subDetail) {
		super();
		this.content = content;
		this.subDetail = subDetail;
	}

	public String getSubDetail() {
		return subDetail;
	}

	public void setSubDetail(String subDetail) {
		this.subDetail = subDetail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "StringEntity [content=" + content + ", subDetail=" + subDetail
				+ "]";
	}

}
