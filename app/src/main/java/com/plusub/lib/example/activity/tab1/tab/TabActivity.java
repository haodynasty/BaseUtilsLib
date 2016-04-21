/*
 * FileName: TabActivity.java
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
 * date     : 2015-6-2 上午10:53:10
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.activity.tab1.tab;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

import com.plusub.lib.activity.BaseFragment;
import com.plusub.lib.activity.BaseFragmentActivity;
import com.plusub.lib.adapter.FragmentPageAdapter;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.R;
import com.plusub.lib.example.view.TabView;
import com.plusub.lib.example.view.TabView.OnTabChangeListener;
import com.plusub.lib.view.ScrollViewPager;

/**
 * @ClassName: TabActivity
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-6-2 上午10:53:10<br>
 *     <b>最后修改时间：</b>2015-6-2 上午10:53:10
 * @version v1.0
 */
public class TabActivity extends BaseFragmentActivity {

	@BindView(id = R.id.tab_view)
	private TabView mTabView;
	@BindView(id = R.id.viewpager)
	private ScrollViewPager mViewPager; 
	private FragmentPageAdapter mAdapter;
	private List<BaseFragment> fragments;
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		fragments = new ArrayList<BaseFragment>();
		fragments.add(new TabFragment1());
		fragments.add(new TabFragment2());
		fragments.add(new TabFragment3());
		mAdapter = new FragmentPageAdapter(getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(mAdapter);
		mTabView.setTabTitle(new String[]{"发现", "最新", "消息"});
		mTabView.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabSelected(int position) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		mTabView.attachToViewPager(mViewPager);
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_tabview);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


}
