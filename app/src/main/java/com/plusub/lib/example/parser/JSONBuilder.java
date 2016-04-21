/*
 * FileName: JSONBuilder.java
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
 * date     : 2014-12-29 下午4:43:02
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.parser;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.plusub.lib.parser.JSONParser;
import com.plusub.lib.util.CommException;

/**
 * @ClassName: JSONBuilder
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-29 下午4:43:02<br>
 *     <b>最后修改时间：</b>2014-12-29 下午4:43:02
 * @version v1.0
 */
public abstract class JSONBuilder<T> implements JSONParser {

	@Override
	public T build(JSONArray jsonArray) throws JSONException,
			CommException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> buildList(JSONArray jsonArray)
			throws JSONException, CommException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkError(JSONObject jo) {
		// TODO Auto-generated method stub
		//里面实现通用判断JSON返回的结果头，是否返回错误信息，状态是否正确
		return true;
	}
	
}
