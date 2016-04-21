/*
 * FileName: ListAdapter.java
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
 * date     : 2014-12-12 下午8:07:38
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plusub.lib.adapter.BaseArrayListAdapter;
import com.plusub.lib.annotate.AnnotateUtil;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.MainApplication;
import com.plusub.lib.example.R;
import com.plusub.lib.example.common.AppConfig;
import com.plusub.lib.example.entity.SimpleEntity;
import com.plusub.lib.view.CircleImageView;

/**
 * 演示BaseArrayListAdapter用法和在Adapter中使用BindView的用法，只需要实现getView方法
 * @ClassName: ListAdapter
 * @Description: TODO
 * @author blakequ@gmail.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-12 下午8:07:38<br>
 *     <b>最后修改时间：</b>2014-12-12 下午8:07:38
 * @version v1.0
 */
public class ListAdapter extends BaseArrayListAdapter<SimpleEntity> {

	public ListAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 这里演示了在Adapter中使用BindView的用法
	 * @ClassName: Holder
	 * @Description: TODO
	 * @author qh@plusub.com
	 * @date 2014-12-12 下午8:26:50
	 * @version v1.0
	 */
	private class Holder{
		@BindView(id = R.id.list_iv_head)
		CircleImageView mIvHeader;
		@BindView(id = R.id.list_tv_name)
		TextView mTvContent;
		
		public Holder(View view){
			AnnotateUtil.initBindView(this, view);
		}
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if (convertView != null) {
			holder = (Holder) convertView.getTag();
		}else{
			convertView = mInflater.inflate(R.layout.listitem_simple_item, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		}
		setData(holder, position);
		return convertView;
	}


	private void setData(Holder holder, int position) {
		// TODO Auto-generated method stub
		SimpleEntity entity = (SimpleEntity) getItem(position);
		if (entity != null) {
			MainApplication.getInstance().getImageCache().displayImage(entity.getHeader(), holder.mIvHeader, 
					AppConfig.getCommonImageViewOptionsNotScale());
			holder.mTvContent.setText(entity.getContent());
		}
	}
}
