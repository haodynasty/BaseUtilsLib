/*
 * FileName: RefreshActivity.java
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
 * date     : 2014-12-12 下午7:32:19
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.ui.tab1;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.R;
import com.plusub.lib.example.ui.ToolbarActivity;
import com.plusub.lib.example.ui.adapter.ListAdapter;
import com.plusub.lib.example.model.SimpleEntity;
import com.plusub.lib.task.UserTask;
import com.plusub.lib.view.ViewInjectUtils;
import com.plusub.lib.view.refresh.RefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示刷新与加载Activity
 * @ClassName: RefreshActivity
 * @Description: TODO
 * @author blakequ@gmail.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-12 下午7:32:19<br>
 *     <b>最后修改时间：</b>2014-12-12 下午7:32:19
 * @version v1.0
 */
public class RefreshActivity extends ToolbarActivity {

	@BindView(id = R.id.common_listview)
	private RefreshListView mRefreshList;
	private int num = 3;
	private int length = 8;
	private ListAdapter mAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("下拉刷新与加载");
		mAdapter = new ListAdapter(this);
		mAdapter.addAll(getTestData(0));
		mRefreshList.setDropDownStyle(true);
		mRefreshList.setOnBottomStyle(true);

		mRefreshList.setBottomShowOutOfScreen(true);//error-true
		mRefreshList.setAutoLoadOnBottom(true);
		mRefreshList.setShowFooterWhenNoMore(true);
//		mRefreshList.setShowFooterProgressBar(true);
//		mRefreshList.setFooterLoadingText("载入中...");
		mRefreshList.setAdapter(mAdapter);
		mRefreshList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				System.out.println("---"+position+mAdapter.getItem(position-1));
			}
		});
		mRefreshList.setOnRefreshListener(new RefreshListView.OnRefreshListener() {

			@SuppressLint("NewApi")
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				if(Build.VERSION.SDK_INT >= 11){
					new MyTask(true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}else{
					new MyTask(true).execute();
				}
				num = 3;
			}
		});
		mRefreshList.setOnBottomListener(new RefreshListView.OnBottomListener() {

			@Override
			public void onBottom() {
				// TODO Auto-generated method stub
				if (Build.VERSION.SDK_INT >= 11) {
					new MyTask(false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				} else {
					new MyTask(false).execute();
				}
			}
		});
		mRefreshList.onManualRefresh();
	}

	private List<SimpleEntity> getTestData(int startIndex){
		List<SimpleEntity> list = new ArrayList<SimpleEntity>();
		for (int i = startIndex; i < startIndex+length; i++) {
			SimpleEntity se = new SimpleEntity();
			se.setContent("测试数据"+i);
			se.setHeader("http://p3.sinaimg.cn/1043545162/180/53181330323932");
			list.add(se);
		}
		return list;
	}


	@Override
	public void onTrimMemory() {

	}

	@Override
	public int provideContentViewId() {
		return R.layout.activity_refresh;
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
//			showLoadingDialogNotCancel("正在加载数据...");
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
			mRefreshList.onRefreshComplete();
			if (isRefresh) {
				ViewInjectUtils.showCustomToast(RefreshActivity.this, "刷新完成");
				mAdapter.refreshData(getTestData(0));
			}else{
				mAdapter.addAll(getTestData((3-num)*length));
				if (num > 0) {
					ViewInjectUtils.showCustomToast(RefreshActivity.this, "加载列表");
				}else{
					mRefreshList.setNoMore(); //加载没有更多就不显示最后的了
					ViewInjectUtils.showCustomToast(RefreshActivity.this, "没有更多了");
				}
//				mRefreshList.onBottomComplete();
			}
		}
	}
}
