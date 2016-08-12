/*
 * FileName: Tab1Fragment.java
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
 * date     : 2014-12-12 下午4:56:53
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.ui.tab2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import com.plusub.lib.activity.BaseFragment;
import com.plusub.lib.activity.BrowserActivity;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.R;
import com.plusub.lib.example.api.ApiWrapper;
import com.plusub.lib.example.api.http.subscribers.SubscriberOnNextListener;
import com.plusub.lib.example.model.BookEntity;
import com.plusub.lib.example.ui.adapter.BookAdapter;
import com.plusub.lib.util.ToastUtils;
import com.plusub.lib.util.logger.Logger;

import java.util.List;

/**
 * 测试使用BaseArrayListAdapter,重写BaseAdapter但是更方便
 * 测试网络请求的使用方法
 * @ClassName: Tab1Fragment
 * @Description: TODO
 * @author blakequ@gmail.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-12 下午4:56:53<br>
 *     <b>最后修改时间：</b>2014-12-12 下午4:56:53
 * @version v1.0
 */
public class Tab2Fragment extends BaseFragment implements SubscriberOnNextListener<List<BookEntity>>{

	private BookAdapter adapter;
	@BindView(id = R.id.common_listview)
	private ListView mList;
	@BindView(id = R.id.fab)
	private FloatingActionButton mFloatingButton;

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_tab_2, null);
	}

	@Override
	protected void initView(View parentView) {
		// TODO Auto-generated method stub
		adapter = new BookAdapter(getApplicationContext());
		mList.setAdapter(adapter);
        mFloatingButton.attachToListView(mList, new ScrollDirectionListener() {
			@Override
			public void onScrollDown() {
				Logger.d("onScrollDown()");
			}

			@Override
			public void onScrollUp() {
				Logger.d("onScrollUp()");
			}
		}, new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				Logger.d("onScrollStateChanged()");
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				Logger.d("onScroll()");
			}
		});
        mFloatingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ApiWrapper.getBookList(getActivity(), "哈利波特", 10, Tab2Fragment.this);

			}
		});
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				BookEntity book = (BookEntity) adapter.getItem(position);
				if (book != null) {
					BrowserActivity.launch(getActivity(), book.getUrl(), book.getTitle());
				}
			}
		});
		ApiWrapper.getBookList(getActivity(), "哈利波特", 10, Tab2Fragment.this);
	}

	@Override
	public void onTrimMemory() {

	}

	@Override
	public void onNext(List<BookEntity> bookEntities) {
		if (bookEntities != null) {
			adapter.refreshData(bookEntities);
		}
	}

	@Override
	public void onError() {
		ToastUtils.show(getApplicationContext(), "获取图书失败");
	}
}
