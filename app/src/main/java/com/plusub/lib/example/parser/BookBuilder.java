/*
 * FileName: BookBuilder.java
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
 * date     : 2014-12-29 下午6:09:21
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.plusub.lib.example.entity.BookEntity;
import com.plusub.lib.util.CommException;
import com.plusub.lib.util.JSONUtils;

/**
 * @ClassName: BookBuilder
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-29 下午6:09:21<br>
 *     <b>最后修改时间：</b>2014-12-29 下午6:09:21
 * @version v1.0
 */
public class BookBuilder extends JSONBuilder<BookEntity> {

	@Override
	public BookEntity build(JSONObject jsonObject) throws JSONException,
			CommException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookEntity> buildList(JSONObject jsonObject)
			throws JSONException, CommException {
		// TODO Auto-generated method stub
		if (checkError(jsonObject)) {
			List<BookEntity> books = new ArrayList<BookEntity>();
			JSONArray ja = jsonObject.getJSONArray("books");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				BookEntity book = new BookEntity();
				book.setAuthorInfo(JSONUtils.getString(jo, "summary", ""));
				book.setCatalog(JSONUtils.getString(jo, "catalog", ""));
				book.setImage(JSONUtils.getString(jo, "image", ""));
				book.setPages(Integer.parseInt(JSONUtils.getString(jo, "pages", "0")));
				book.setPrice(JSONUtils.getString(jo, "price", "0"));
				book.setPubData(JSONUtils.getString(jo, "pubdate", ""));
				book.setTitle(JSONUtils.getString(jo, "title", ""));
				book.setUrl(JSONUtils.getString(jo, "alt", ""));
				books.add(book);
			}
			return books;
		}
		return null;
	}

}
