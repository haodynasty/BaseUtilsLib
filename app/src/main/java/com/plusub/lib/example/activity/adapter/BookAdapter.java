/*
 * FileName: BookAdapter.java
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
 * date     : 2014-12-29 下午7:35:21
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.plusub.lib.adapter.BaseArrayListAdapter;
import com.plusub.lib.annotate.AnnotateUtil;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.MainApplication;
import com.plusub.lib.example.R;
import com.plusub.lib.example.common.AppConfig;
import com.plusub.lib.example.entity.BookEntity;

/**
 * @ClassName: BookAdapter
 * @Description: TODO
 * @author blakequ@gmail.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-29 下午7:35:21<br>
 *     <b>最后修改时间：</b>2014-12-29 下午7:35:21
 * @version v1.0
 */
public class BookAdapter extends BaseArrayListAdapter<BookEntity> {

	public BookAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if (convertView != null) {
			holder = (Holder) convertView.getTag();
		}else{
			convertView = mInflater.inflate(R.layout.listitem_book_item, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		}
		setData(holder, position);
		return convertView;
	}


	private void setData(Holder holder, int position) {
		// TODO Auto-generated method stub
		BookEntity book = (BookEntity) getItem(position);
		if (book != null) {
			MainApplication.getInstance().getImageCache().displayImage(book.getImage(), holder.mIvHeader, 
					AppConfig.getCommonImageViewOptionsNotScale());
			holder.mTvContent.setText(book.getAuthorInfo());
			holder.mTvDate.setText(book.getPubData());
			holder.mTvTitle.setText(book.getTitle());
		}
	}


	private class Holder{
		@BindView(id = R.id.list_iv_head)
		ImageView mIvHeader;
		@BindView(id = R.id.list_tv_title)
		TextView mTvTitle;
		@BindView(id = R.id.list_tv_date)
		TextView mTvDate;
		@BindView(id = R.id.list_tv_content)
		TextView mTvContent;
		
		public Holder(View view){
			AnnotateUtil.initBindView(this, view);
		}
	}
}
