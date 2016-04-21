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
package com.plusub.lib.example.activity.tab2;

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
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.constant.ErrorCode;
import com.plusub.lib.example.R;
import com.plusub.lib.example.activity.adapter.BookAdapter;
import com.plusub.lib.example.activity.tab1.BrowserActivity;
import com.plusub.lib.example.entity.BookEntity;
import com.plusub.lib.example.http.RequestTaskConstant;
import com.plusub.lib.example.service.RequestService;
import com.plusub.lib.example.view.HeaderLayout;
import com.plusub.lib.example.view.HeaderLayout.HeaderStyle;
import com.plusub.lib.net.util.RequestParams;
import com.plusub.lib.task.TaskEntity;
import com.plusub.lib.task.TaskMessage;
import com.plusub.lib.util.LogUtils;
import com.plusub.lib.view.ViewInjectUtils;

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
public class Tab2Fragment extends BaseFragment {

	private BookAdapter adapter;
	@BindView(id = R.id.common_listview)
	private ListView mList;
	@BindView(id = R.id.common_head_layout)
	private HeaderLayout mHeader;
	@BindView(id = R.id.fab)
	private FloatingActionButton mFloatingButton;
	
	@Override
	public void refresh(TaskMessage msg, Object... param) {
		// TODO Auto-generated method stub
		ViewInjectUtils.dismissLoadingDialog();
		if (msg.errorCode != ErrorCode.DEFAULT_VALUE) {
			ViewInjectUtils.showCustomToast(getActivity(), "请求错误");
			return;
		}
		switch (msg.what) {
			case RequestTaskConstant.TASK_DOUBAN:
				List<BookEntity> list = (List<BookEntity>) msg.obj;
				if (list != null) {
					adapter.refreshData(list);
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		getBookOfDouban("哈利波特", 10);
		ViewInjectUtils.showLoadingDialog(getActivity(), "正在请求数据...");
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				BookEntity book = (BookEntity) adapter.getItem(position);
				if (book != null) {
					BrowserActivity.launch(getActivity(), book.getUrl());
				}
			}
		});
	}

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
		mHeader.init(HeaderStyle.TITLE_MIDDLE_TEXT);
		mHeader.setMiddleTitle("网络数据请求");
		mList.setAdapter(adapter);
		
        mFloatingButton.attachToListView(mList, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                LogUtils.d("ListViewFragment", "onScrollDown()");
            }

            @Override
            public void onScrollUp() {
            	LogUtils.d("ListViewFragment", "onScrollUp()");
            }
        }, new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            	LogUtils.d("ListViewFragment", "onScrollStateChanged()");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            	LogUtils.d("ListViewFragment", "onScroll()");
            }
        });
        mFloatingButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("----");
				getBookOfDouban("哈利波特", 10);
			}
		});
	}

	/**
	 * 发送服务器请求
	 * <p>Title: getBookOfDouban
	 * <p>Description: 
	 * @param queryString
	 * @param count
	 */
	private void getBookOfDouban(String queryString, int count){
		//请求参数
		RequestParams params = new RequestParams();
		params.put("q", queryString);
		params.put("count", count+"");
		//请求实体
		TaskEntity task = new TaskEntity(this);
		task.setTaskID(RequestTaskConstant.TASK_DOUBAN);
		task.setTaskObj(params);
		//将请求添加到请求任务列表
		RequestService.addNewTask(task);
	}

}
