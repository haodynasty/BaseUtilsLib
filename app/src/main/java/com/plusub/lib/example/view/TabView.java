/*
 * FileName: TabView.java
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
 * date     : 2015-6-2 上午9:28:43
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.view;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import java.util.ArrayList;
import java.util.List;

import com.plusub.lib.example.R;
import com.plusub.lib.util.ImageUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @ClassName: TabView
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-6-2 上午9:28:43<br>
 *     <b>最后修改时间：</b>2015-6-2 上午9:28:43
 * @version v1.0
 */
public class TabView extends LinearLayout {
	
	private LinearLayout mTabView;
	private LayoutInflater mInflater;
	private LinearLayout mTabContainer;
	private ImageView mIvCursor;
	private View mLine;

	private Context context;
	private List<TabHolder> tabItemList;
	private int mScreenWidth;
	private int mCursorCurPosition=0;
	private int tabCount = 0;
	private OnTabChangeListener onTabChangeListener;
	private int textColorSelector = 0;
	
	public TabView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	@SuppressLint("NewApi")
	public TabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	
	private void init(Context context){
		tabItemList = new ArrayList<TabHolder>();
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mInflater = LayoutInflater.from(context);
		mTabView = (LinearLayout) mInflater.inflate(R.layout.view_tab_layout, null);
		LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, ImageUtils.Dp2Px(context, 40));
		lp.gravity = Gravity.CENTER;
		mTabView.setLayoutParams(lp);
		addView(mTabView, new LinearLayout.LayoutParams(0, MATCH_PARENT, 1));
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		mTabContainer = (LinearLayout) mTabView.findViewById(R.id.tab_view_container);
		mIvCursor = (ImageView) mTabView.findViewById(R.id.tab_cursor);
		mLine = mTabView.findViewById(R.id.tab_line);
	}
	
	/**
	 * 设置tab下面背景线颜色，默认灰色
	 * <p>Title: setTabLineColor
	 * <p>Description: 
	 * @param colorRes 颜色的资源id，R.color..
	 */
	public void setTabLineColor(int colorRes){
		mLine.setBackgroundColor(context.getResources().getColor(colorRes));
	}
	
	/**
	 * 设置tab的文本颜色，必须是资源文件（颜色选择器selector，见库中的color/bg_tab_text.xml）
	 * <p>Title: setTabTextColorRes
	 * <p>Description: 
	 * @param resId 颜色资源id, R.color...
	 */
	public void setTabTextColorRes(int resId){
		this.textColorSelector = resId;
		refreshView();
	}
	
	/**
	 * 设置纯色背景
	 * <p>Title: setTabBackgroundColor
	 * <p>Description: 
	 * @param resId 颜色的资源id，R.color...
	 */
	public void setTabBackgroundColor(int resId){
		mTabView.setBackgroundColor(context.getResources().getColor(resId));
	}
	
	/**
	 * 设置图片资源作为背景
	 * <p>Title: setTabBackgroundImg
	 * <p>Description: 
	 * @param imgResId 图片资源id，R.drawable....
	 */
	public void setTabBackgroundImg(int imgResId){
		mTabView.setBackgroundResource(imgResId);
	}
	
	/**
	 * 设置tab显示的文字
	 * <p>Title: setTabTitle
	 * <p>Description: 
	 * @param strs
	 */
	public void setTabTitle(String[] strs){
		tabItemList.clear();
		if (strs != null && strs.length > 0) {
			for (int i = 0; i < strs.length; i++) {
				TabHolder holder = new TabHolder();
				holder.mTvName.setText(strs[i]);
				holder.mTabItem.setTag(i);
				holder.mTabItem.setGravity(Gravity.CENTER);
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mScreenWidth/strs.length, 
						RelativeLayout.LayoutParams.MATCH_PARENT);
				holder.mTabItem.setLayoutParams(lp);
				holder.mTabItem.setOnClickListener(tabClickListener);
				tabItemList.add(holder);
			}
			tabCount = tabItemList.size();
			refreshView();
		}
	}
	
	/**
	 * 设置tab
	 * <p>Title: setTabIndex
	 * <p>Description: 
	 * @param index
	 */
	public void setTabIndex(int index){
		if (index < 0 || index >= tabCount) {
			return;
		}
		changeTextState(index);
		moveCursor(index);
	}
	
	/**
	 * 刷新视图
	 * <p>Title: refreshView
	 * <p>Description:
	 */
	public void refreshView(){
		mTabContainer.removeAllViews();
		for (int i = 0; i < tabCount; i++) {
			TabHolder holder = tabItemList.get(i);
			if (textColorSelector > 0) {
				holder.mTvName.setBackgroundColor(context.getResources().getColor(textColorSelector));
			}
			mTabContainer.addView(holder.mTabItem);
		}
		invalidate();
	}

	/**
	 * 将tab附加到viewpager，随着viewpager滑动而滑动
	 * <p>注意：会占用ViewPage的OnPageChangeListener监听器，如果需要使用，则必须使用TabView的监听器</p>
	 * <p>Title: attachToViewPager
	 * <p>Description: 
	 * @param pager
	 */
	public void attachToViewPager(ViewPager pager){
		if (pager != null) {
			pager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
					changeTextState(arg0);
					moveCursor(arg0);
					if (onTabChangeListener != null) {
						onTabChangeListener.onTabSelected(arg0);
					}
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					if (onTabChangeListener != null) {
						onTabChangeListener.onTabScrolled(arg0, arg1, arg2);
					}
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					if (onTabChangeListener != null) {
						onTabChangeListener.onTabScrollStateChanged(arg0);
					}
				}
			});
		}
	}
	
	/**
	 * 设置tab切换监听器
	 * <p>Title: setTabChangedListener
	 * <p>Description: 
	 * @param onTabChangeListener
	 */
	public void setOnTabChangedListener(OnTabChangeListener onTabChangeListener){
		this.onTabChangeListener = onTabChangeListener;
	}
	
	private void changeTextState(int index){
		for (int i = 0; i < tabCount; i++) {
			TabHolder holder = tabItemList.get(i);
			if (index != i) {
				holder.mTvName.setSelected(false);
			}else{
				holder.mTvName.setSelected(true);
			}
		}
	}
	
	/**
	 * 移动选项卡指针
	 * @param arg0
	 */
	private void moveCursor(int arg0) {
		TranslateAnimation animation=new TranslateAnimation(mCursorCurPosition,arg0*mScreenWidth/tabCount,
				Animation.RELATIVE_TO_SELF,Animation.RELATIVE_TO_SELF);
		mCursorCurPosition=arg0*mScreenWidth/tabCount;
		animation.setDuration(200);
		animation.setInterpolator(new LinearInterpolator());
		animation.setFillAfter(true);
		mIvCursor.startAnimation(animation);
	}
	
	/**
	 * tab的每个item
	 * @ClassName: TabHolder
	 * @Description: TODO
	 * @author qh@plusub.com
	 * @date 2015-6-2 上午10:23:35
	 * @version v1.0
	 */
	private class TabHolder{
		
		public TabHolder(){
			mTabItem = (RelativeLayout) mInflater.inflate(R.layout.include_tab_item, null);
			mTvName = (TextView) mTabItem.findViewById(R.id.tab_item_tv);
			mIvBadge = (ImageView) mTabItem.findViewById(R.id.tab_item_badge);
		}
		
		RelativeLayout mTabItem;
		TextView mTvName;
		ImageView mIvBadge;
	}
	
	private OnClickListener tabClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int position = (Integer) v.getTag();
			if (onTabChangeListener != null) {
				onTabChangeListener.onTabSelected(position);
			}
		}
	};
	
	public interface OnTabChangeListener{
		public void onTabSelected(int position);
		public void onTabScrolled(int position, float positionOffset, int positionOffsetPixels);
		public void onTabScrollStateChanged(int state);
	}
	
}
