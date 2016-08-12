package com.plusub.lib.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plusub.lib.activity.BaseFragment;
import com.plusub.lib.adapter.FragmentPageAdapter;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.MainApplication;
import com.plusub.lib.example.R;
import com.plusub.lib.example.ui.tab1.Tab1Fragment;
import com.plusub.lib.example.ui.tab2.Tab2Fragment;
import com.plusub.lib.example.ui.tab3.Tab3Fragment;
import com.plusub.lib.example.ui.tab4.Tab4Fragment;
import com.plusub.lib.example.utils.ChannelUtil;
import com.plusub.lib.view.ScrollViewPager;
import com.plusub.lib.view.ViewInjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主Activity
 * @ClassName: MainActivity
 * @Description: TODO
 * @author blakequ@gmail.com
 * @date 2014-12-12 下午5:41:51
 * @version v1.0
 */
public class MainActivity extends ToolbarActivity {

	@BindView(id = R.id.common_viewpager)
	private ScrollViewPager mPager;
	@BindView(id = R.id.main_tab_layout_1, click = true)
	private RelativeLayout mRlTab1;
	@BindView(id = R.id.main_tab_item_label1)
	private TextView mTab1Label;
	@BindView(id = R.id.main_tab_item_badge1)
	private ImageView mTab1Badge;
	
	@BindView(id = R.id.main_tab_layout_2, click = true)
	private RelativeLayout mRlTab2;
	@BindView(id = R.id.main_tab_item_label2)
	private TextView mTab2Label;
	
	@BindView(id = R.id.main_tab_layout_3, click = true)
	private RelativeLayout mRlTab3;
	@BindView(id = R.id.main_tab_item_label3)
	private TextView mTab3Lable;
	
	@BindView(id = R.id.main_tab_layout_4, click = true)
	private RelativeLayout mRlTab4;
	@BindView(id = R.id.main_tab_item_label4)
	private TextView mTab4Lable;
	@BindView(id = R.id.main_tab_item_badge4)
	private ImageView mTab4Badge;
	
	private FragmentPageAdapter mAdapter;
	private List<BaseFragment> fragments;
	private int keyTime = 0;
	private MyCount countDownTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("BaseUtilsLib");
		fragments = new ArrayList<BaseFragment>();
		fragments.add(new Tab1Fragment());
		fragments.add(new Tab2Fragment());
		fragments.add(new Tab3Fragment());
		fragments.add(new Tab4Fragment());
		mAdapter = new FragmentPageAdapter(getSupportFragmentManager(), fragments);
		mPager.setOffscreenPageLimit(fragments.size());
		mPager.setAdapter(mAdapter);
		mPager.addOnPageChangeListener(listener);
		setTabSelectedStates(0);
		//通过美团生成渠道号方案获取渠道号https://github.com/GavinCT/AndroidMultiChannelBuildTool
		System.out.println("---channel id: "+ ChannelUtil.getChannel(this));
		//测试不同flavor区别使用第三方sdk
		try {
			Class.forName("com.alibaba.fastjson.JSONObject");
			System.out.println("---alibaba pkg used");
		} catch (ClassNotFoundException ignored) {
			System.out.println("---alibaba pkg not used");
		}
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.main_tab_layout_1:
				setTabSelectedStates(0);
				break;
			case R.id.main_tab_layout_2:
				setTabSelectedStates(1);
				break;
			case R.id.main_tab_layout_3:
				setTabSelectedStates(2);
				break;
			case R.id.main_tab_layout_4:
				setTabSelectedStates(3);
				break;
			default:
				break;
		}
	}


	/**
	 * 改变选择状态
	 * <p>Title: setTabSelectedStates
	 * <p>Description: 
	 * @param index
	 */
	private void setTabSelectedStates(int index){
		mPager.setCurrentItem(index, false);
		setTitle("BaseUtilsLib" + index);
		switch (index) {
			case 0:
				mRlTab1.setSelected(true);
				mRlTab2.setSelected(false);
				mRlTab3.setSelected(false);
				mRlTab4.setSelected(false);
				break;
			case 1:
				mRlTab1.setSelected(false);
				mRlTab2.setSelected(true);
				mRlTab3.setSelected(false);
				mRlTab4.setSelected(false);
				setTitle("网络数据请求");
				break;
			case 2:
				mRlTab1.setSelected(false);
				mRlTab2.setSelected(false);
				mRlTab3.setSelected(true);
				mRlTab4.setSelected(false);
				setTitle("使用PullToRefresh下拉刷新");
				break;
			case 3:
				mRlTab1.setSelected(false);
				mRlTab2.setSelected(false);
				mRlTab3.setSelected(false);
				mRlTab4.setSelected(true);
				break;
			default:
				break;
		}
	}
	
	@Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	  // TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// moveTaskToBack(true);
			keyTime++;
			if (keyTime == 1) {
				ViewInjectUtils.showCustomToast(this, R.string.exit_app);
				if (countDownTimer != null) {
					countDownTimer.cancel();
				}
				countDownTimer = new MyCount(2 * 1000, 1000);
				countDownTimer.start();
			} else if (keyTime == 2) {
				finish();
				MainApplication.exitApp(this);
			}
			return true;
		}
		return false;
	 }

	@Override
	public void onTrimMemory() {

	}

	private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {

		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	@Override
	public int provideContentViewId() {
		return R.layout.activity_main;
	}


	/** 
	* 定义一个倒计时的内部类 
	* @author blakequ Blakequ@gmail.com 
	* 
	*/ 
	class MyCount extends CountDownTimer { 

	/** 
	* 
	* <p>Title: 
	* <p>Description: 
	* @param millisInFuture 倒计时毫秒数 
	* @param countDownInterval 间隔毫秒数 
	*/ 
		public MyCount(long millisInFuture, long countDownInterval) { 
			super(millisInFuture, countDownInterval); 
		} 

		@Override 
		public void onFinish() { 
			keyTime = 0; 
		} 
		@Override 
		public void onTick(long millisUntilFinished) { 
		} 
	}
}
