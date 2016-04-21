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
package com.plusub.lib.example.activity.tab3;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.plusub.lib.activity.BaseFragment;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.R;
import com.plusub.lib.example.activity.adapter.ListAdapter;
import com.plusub.lib.example.entity.SimpleEntity;
import com.plusub.lib.example.view.HeaderLayout;
import com.plusub.lib.example.view.HeaderLayout.HeaderStyle;
import com.plusub.lib.task.TaskMessage;
import com.plusub.lib.task.UserTask;
import com.plusub.lib.util.TimeUtils;
import com.plusub.lib.view.ViewInjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Tab1Fragment
 * @Description: TODO
 * @author blakequ@gmail.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-12 下午4:56:53<br>
 *     <b>最后修改时间：</b>2014-12-12 下午4:56:53
 * @version v1.0
 */
public class Tab3Fragment extends BaseFragment {

	@BindView(id = R.id.common_head_layout)
	private HeaderLayout mHeader;
	@BindView(id = R.id.listview)
	private PullToRefreshListView mListView;
	private TextView mEmptyView;
	
	private static int num = 3;
	private ListAdapter mAdapter;
	
	@Override
	public void refresh(TaskMessage msg, Object... param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		mHeader.init(HeaderStyle.TITLE_MIDDLE_TEXT);
		mHeader.setMiddleTitle("使用PullToRefresh下拉刷新");
		mAdapter = new ListAdapter(getActivity());
		mAdapter.addAll(getTestData(0));

		mListView.setEmptyView(mEmptyView);
		mListView.setAdapter(mAdapter);
		mListView.setMode(Mode.BOTH);//两端刷新
		mListView.getLoadingLayoutProxy(true, false).setPullLabel(getString(R.string.pull_to_refresh_pull_label));
		mListView.getLoadingLayoutProxy(true, false).setRefreshingLabel(getString(R.string.pull_to_refresh_refreshing_label));
		mListView.getLoadingLayoutProxy(true, false).setReleaseLabel(getString(R.string.pull_to_refresh_release_label));
		mListView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.pull_to_refresh_from_bottom_pull_label));
		mListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.pull_to_refresh_from_bottom_refreshing_label));
		mListView.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.pull_to_refresh_from_bottom_release_label));
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(TimeUtils.getDateENNotSecond(System.currentTimeMillis()));
				if(Build.VERSION.SDK_INT >= 11){
				     new MyTask(true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}else{
				     new MyTask(true).execute();
				}
				num = 3;
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if(Build.VERSION.SDK_INT >= 11){
				     new MyTask(false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}else{
				     new MyTask(false).execute();
				}
			}
		});
		
		mListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				// TODO Auto-generated method stub
				//实现底部自动加载
				mListView.setRefreshing();
			}
		});
		
		
		//这是自动刷新
		mListView.setRefreshing();
	}

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_tab_3, null);
	}

	@Override
	protected void initView(View parentView) {
		// TODO Auto-generated method stub
		mEmptyView = new TextView(getActivity());
		mEmptyView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mEmptyView.setText("还没有数据");
	}


	private List<SimpleEntity> getTestData(int startIndex){
		List<SimpleEntity> list = new ArrayList<SimpleEntity>();
		for (int i = startIndex; i < startIndex+4; i++) {
			SimpleEntity se = new SimpleEntity();
			se.setContent("测试数据"+i);
			se.setHeader("http://www.youqu55.com/d/file/20140921/61afedccccfabc215a62059ed49e9550.jpg");
			list.add(se);
		}
		return list;
	}
	
	/**
	 * 使用异步任务
	 * @ClassName: MyTask
	 * @Description: TODO
	 * @author qh@plusub.com
	 * @date 2014-12-12 下午8:59:20
	 * @version v1.0
	 */
	private class MyTask extends UserTask<Boolean, Boolean, Boolean>{
		boolean isRefresh = true;
		public MyTask(boolean isRefresh){
			this.isRefresh = isRefresh;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mListView.setEmptyView(null);
//			showLoadingDialog("正在加载数据...");
		}



		@Override
		protected Boolean doInBackground(Boolean... params) {
			// TODO Auto-generated method stub
			try {
				if (!isRefresh) {
					num--;
				}
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
//			dismissLoadingDialog();
			mListView.onRefreshComplete();
			if (isRefresh) {
				ViewInjectUtils.showCustomToast(getActivity(), "刷新完成");
				mAdapter.refreshData(getTestData(0));
			}else{
				mAdapter.addAll(getTestData((3-num)*4));
				if (num != 0) {
					ViewInjectUtils.showCustomToast(getActivity(), "加载列表");
				}else{
					ViewInjectUtils.showCustomToast(getActivity(), "没有更多了");
				}
			}
		}
	}
}
