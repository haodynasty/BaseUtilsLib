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
package com.plusub.lib.example.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.plusub.lib.adapter.BaseArrayListAdapter;
import com.plusub.lib.annotate.AnnotateUtil;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.R;
import com.plusub.lib.example.model.BookEntity;

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
			holder.mIvHeader.setHierarchy(getDraweeConfig());
			convertView.setTag(holder);
		}
		setData(holder, position);
		return convertView;
	}


	private void setData(Holder holder, int position) {
		// TODO Auto-generated method stub
		BookEntity book = (BookEntity) getItem(position);
		if (book != null) {
			holder.mIvHeader.setImageURI(book.getImage());
			holder.mTvContent.setText(book.getAuthorInfo());
			holder.mTvDate.setText(book.getPubdata());
			holder.mTvTitle.setText(book.getTitle());
		}
	}


	/**
	 * 1.一个DraweeHierarchy 是不可以被多个 View 共用的！
	 * 2.对于同一个View，请不要多次调用setHierarchy，即使这个View是可回收的。创建 DraweeHierarchy 的较为耗时的一个过程，应该多次利用。
	 * @return
	 */
	private GenericDraweeHierarchy getDraweeConfig(){
		GenericDraweeHierarchyBuilder builder =
				new GenericDraweeHierarchyBuilder(mContext.getResources());

		GenericDraweeHierarchy hierarchy = builder
				.setFadeDuration(300)
				.setProgressBarImage(new ProgressBarDrawable())
				.setPlaceholderImage(R.drawable.ic_img_load_default)
				.setFailureImage(R.drawable.ic_img_load_fail)
//				.setBackgrounds(backgroundList)
//				.setOverlays(overlaysList)
				.build();
		//圆角
		RoundingParams roundingParams = hierarchy.getRoundingParams();
		roundingParams.setCornersRadius(10);
		hierarchy.setRoundingParams(roundingParams);
		return hierarchy;
	}

	private class Holder{
		@BindView(id = R.id.list_iv_head)
		SimpleDraweeView mIvHeader;
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
