/*
 * FileName: JSONParser.java
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
 * date     : 2014-7-14 上午10:00:21
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.parser;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.plusub.lib.util.CommException;

/**
 * json解析
 * @author blakequ Blakequ@gmail.com
 *
 * @param <T>
 */
public interface JSONParser<T> {

	public T build(JSONObject jsonObject) throws JSONException, CommException;
	
	public List<T> buildList(JSONObject jsonObject) throws JSONException, CommException;
	
	public T build(JSONArray jsonArray) throws JSONException, CommException;
	
	public List<T> buildList(JSONArray jsonArray) throws JSONException, CommException;
	
	/**
	 * 检查错误（在接收到服务器返回数据之前，先判断返回数据是否正确，正确在使用）
	 * <p>Title: checkError
	 * <p>Description: 
	 * @return
	 */
	public boolean checkError(JSONObject jo);
}
