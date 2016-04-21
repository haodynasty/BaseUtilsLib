/*
 * FileName: HeaderLayout.java
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
 * date     : 2014-8-29 下午4:36:11
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.view;


import com.plusub.lib.example.R;
import com.plusub.lib.util.ImageUtils;
import com.plusub.lib.util.TextUtils;
import com.plusub.lib.view.ScrollingTextView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 公共头部
 * @author service@plusub.com
 *
 */
public class HeaderLayout extends LinearLayout {
	
	/**屏幕的宽度*/
	protected int mScreenWidth;
	private HeaderStyle currentStyle;
	
	private LayoutInflater mInflater;
	private View mHeader;
	private LinearLayout mLayoutLeftContainer;
	private LinearLayout mLayoutMiddleContainer;
	private LinearLayout mLayoutRightContainer;
	
	//left text 
	private LinearLayout mLayoutLeftTitle;
	private ScrollingTextView mLeftStvTitle;
	private TextView mLeftHtvSubTitle;
	//left button
	private LinearLayout mLayoutLeftButton;
	private ImageButton mIbLeftButton;
	
	//middle
	private LinearLayout mLayouMiddleTitle;
	private ScrollingTextView mMiddleStvTitle;
	
	//right
	private LinearLayout mLayoutRightButton;
	private ImageButton mIbRightImageButton;
	private LinearLayout mHtvRightTextLayout;
	private TextView mHtvRightText;
	
	//listener
	private onRightImageButtonClickListener mRightImageButtonClickListener;
	private onRightImageButtonClickListener2 mRightImageButtonClickListener2;
	private onMiddleImageButtonClickListener mMiddleImageButtonClickListener;
	private onLeftImageButtonClickListener onLeftImageButtonClickListener;
	
	
	/**
	 * 头部风格
	 * @author service@plusub.com
	 *
	 */
	public enum HeaderStyle {
		DEFAULT_TITLE, 
		TITLE_LEFT_IMAGEBUTTON,
		TITLE_RIGHT_TEXT, 
		TITLE_RIGHT_TEXT_BUTTON, 
		TITLE_RIGHT_IMAGEBUTTON,
		TITLE_LEFT_BUTTON_AND_RIGHT_BUTTON,
		TITLE_LEFT_BUTTON_AND_RIGHT_TEXT,
		TITLE_CHAT,
		TITLE_MIDDLE_TEXT;
	}

	public HeaderLayout(Context context) {
		super(context);
		init(context);
	}

	public HeaderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		// TODO Auto-generated method stub
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mInflater = LayoutInflater.from(context);
		mHeader = mInflater.inflate(R.layout.common_headerbar, null);
		mHeader.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ImageUtils.Dp2Px(context, 54)));
		addView(mHeader);
		initViews();
	}
	
	public View findViewByHeaderId(int id) {
		return mHeader.findViewById(id);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		//left
		mLayoutLeftContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_leftview_container);
		
		//left text 
		mLayoutLeftTitle = (LinearLayout) findViewByHeaderId(R.id.header_layout_title);
		mLeftStvTitle = (ScrollingTextView) findViewByHeaderId(R.id.header_left_stv_title);
		mLeftHtvSubTitle = (TextView) findViewByHeaderId(R.id.header_left_htv_subtitle);
		//left button
		mLayoutLeftButton = (LinearLayout) findViewByHeaderId(R.id.header_layout_leftview_imagebutton);
		mIbLeftButton = (ImageButton) findViewByHeaderId(R.id.header_ib_left_imagebutton);
		
		//middle
		mLayoutMiddleContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_middleview_container);
		mLayouMiddleTitle = (LinearLayout) findViewByHeaderId(R.id.header_layout_middleview_text);
		mMiddleStvTitle = (ScrollingTextView) findViewByHeaderId(R.id.header_middle_stv_text);
		
		//right
		mLayoutRightContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_rightview_container);
		mLayoutRightButton = (LinearLayout) findViewByHeaderId(R.id.header_layout_rightview_imagebutton);
		mIbRightImageButton = (ImageButton) findViewByHeaderId(R.id.header_ib_right_imagebutton);
	}
	
	/**
	 * 初始化视图
	 * <p>Title: init
	 * <p>Description: 
	 * @param style
	 */
	public void init(HeaderStyle style) {
		currentStyle = style;
		switch (style) {
			case DEFAULT_TITLE:
				defaultTitle();
				break;
			case TITLE_LEFT_IMAGEBUTTON:
				titleLeftButton();
				break;
			case TITLE_RIGHT_TEXT:
				titleRightText();
				break;
			case TITLE_RIGHT_TEXT_BUTTON:
				titleRightTextButton();
				break;
			case TITLE_RIGHT_IMAGEBUTTON:
				setTitleRightButton();
				break;
			case TITLE_LEFT_BUTTON_AND_RIGHT_BUTTON:
				setTitleLeftButtonAndRightButton();
				break;
			case TITLE_LEFT_BUTTON_AND_RIGHT_TEXT:
				setTitleLeftButtonAndRightTextButton();
				break;
			case TITLE_CHAT:
				titleChat();
				break;
			case TITLE_MIDDLE_TEXT:
				setTitleMiddleText();
				break;
			default:
				break;
		}
	}
	
	/**
	 * 设置中间文本
	 * <p>Title: setTitleMiddleText
	 * <p>Description:
	 */
	private void setTitleMiddleText(){
		mLayoutLeftTitle.setVisibility(View.VISIBLE);
		mLayoutRightContainer.setVisibility(View.GONE);
		mLayoutMiddleContainer.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 设置中间标题(可单独调用)
	 * <p>Title: setMiddleTitle
	 * <p>Description: 
	 * @param title
	 */
	public void setMiddleTitle(String title){
		mLayoutMiddleContainer.setVisibility(View.VISIBLE);
		if (!TextUtils.isEmpty(title)) {
			mMiddleStvTitle.setText(title);
		}else{
			mMiddleStvTitle.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 默认只有标题
	 * @see HeaderStyle.DEFAULT_TITLE
	 */
	private void defaultTitle() {
		mLayoutLeftTitle.setVisibility(View.VISIBLE);
//		mLayoutMiddleContainer.removeAllViews();
//		mLayoutRightContainer.removeAllViews();
		mLayoutRightContainer.setVisibility(View.GONE);
	}

	/**
	 * 添加默认标题内容
	 * @see HeaderStyle.DEFAULT_TITL
	 * @param title
	 * @param subTitle
	 */
	public void setDefaultTitle(CharSequence title, CharSequence subTitle) {
		if (title != null) {
			if (currentStyle == HeaderStyle.TITLE_CHAT) {
				ViewGroup.LayoutParams lp = mLeftStvTitle.getLayoutParams();
				lp.width = mScreenWidth*3/5 - 50;
				mLeftStvTitle.setLayoutParams(lp);
			}
			mLeftStvTitle.setText(title);
		} else {
			mLeftStvTitle.setVisibility(View.GONE);
		}
		if (subTitle != null) {
			mLeftHtvSubTitle.setText(subTitle);
		} else {
			mLeftHtvSubTitle.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 标题以及右边有文本内容
	 * @see HeaderStyle.TITLE_RIGHT_TEXT
	 */
	private void titleRightText() {
		mLayoutLeftTitle.setVisibility(View.VISIBLE);
		mLayoutMiddleContainer.removeAllViews();
		mLayoutRightContainer.removeAllViews();
		View mRightText = mInflater.inflate(R.layout.include_header_righttext, null);
		mRightText.setClickable(false);
		mLayoutRightContainer.addView(mRightText);
		mHtvRightText = (TextView) mRightText
				.findViewById(R.id.header_htv_righttext);
	}
	
	
	/**
	 * 添加标题以及右边文本内容
	 * @see HeaderStyle.TITLE_RIGHT_TEXT
	 * @param title
	 * @param subTitle
	 * @param rightText
	 */
	public void setTitleRightText(CharSequence title, CharSequence subTitle,
			CharSequence rightText) {
		setDefaultTitle(title, subTitle);
		if (mHtvRightText != null && rightText != null) {
			mHtvRightText.setText(rightText);
		}
	}
	
	/**
	 * 右边文本按钮
	 * @see HeaderStyle.TITLE_RIGHT_TEXT_BUTTON
	 * <p>Title: titleRightTextButton
	 * <p>Description:
	 */
	private void titleRightTextButton(){
		mLayoutLeftTitle.setVisibility(View.VISIBLE);
//		mLayoutMiddleContainer.removeAllViews();
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		mLayoutRightContainer.removeAllViews();
		mHtvRightTextLayout = (LinearLayout) mInflater.inflate(R.layout.include_header_righttext, null);
		mHtvRightTextLayout.setClickable(true);
		mLayoutRightContainer.addView(mHtvRightTextLayout);
		ViewGroup.LayoutParams lp = mHtvRightTextLayout.getLayoutParams();
		lp.height = ViewGroup.LayoutParams.FILL_PARENT;
		mHtvRightTextLayout.setLayoutParams(lp);
		mHtvRightText = (TextView) mHtvRightTextLayout
				.findViewById(R.id.header_htv_righttext);
		mHtvRightTextLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mRightImageButtonClickListener != null) {
					mRightImageButtonClickListener.onClick();
				}
			}
		});
	}
	
	/**
	 * 添加标题以及右边可点击文本内容
	 * @see HeaderStyle.TITLE_RIGHT_TEXT_BUTTON
	 * @param title
	 * @param subTitle
	 * @param rightText
	 */
	public void setTitleRightTextButton(CharSequence title, CharSequence subTitle,
			CharSequence rightText,
			onRightImageButtonClickListener listener) {
		setDefaultTitle(title, subTitle);
		if (mHtvRightText != null && rightText != null) {
			mHtvRightText.setText(rightText);
		}
		setOnRightImageButtonClickListener(listener);
	}
	
	/**
	 * 设置右边按钮文本内容
	 * <p>Title: setTitlteRightTextButton
	 * <p>Description: 
	 * @param title
	 * @param subTitle
	 * @param rightText
	 */
	public void setTitleRightTextButton(CharSequence title, CharSequence subTitle,
			CharSequence rightText){
		setDefaultTitle(title, subTitle);
		if (mHtvRightText != null && rightText != null) {
			mHtvRightText.setText(rightText);
		}
	}
	
	/**
	 * 标题以及右边按钮
	 * @see HeaderStyle.TITLE_RIGHT_IMAGEBUTTON
	 * <p>Title: setTitleRightButton
	 * <p>Description:
	 */
	private void setTitleRightButton(){
		mLayoutLeftTitle.setVisibility(View.VISIBLE);
//		mLayoutMiddleContainer.removeAllViews();
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		mIbRightImageButton.setVisibility(View.VISIBLE);
		mLayoutRightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mRightImageButtonClickListener != null) {
					mRightImageButtonClickListener.onClick();
				}
			}
		});
	}

	/**
	 * 标题以及右边按钮
	 * @see HeaderStyle.TITLE_RIGHT_IMAGEBUTTON
	 * <p>Title: setTitleRightImageButton
	 * <p>Description: 
	 * @param title
	 * @param subTitle
	 * @param id
	 * @param listener
	 */
	public void setTitleRightImageButton(CharSequence title,
			CharSequence subTitle, int id,
			onRightImageButtonClickListener listener) {
		setDefaultTitle(title, subTitle);
		if (mIbRightImageButton != null && id > 0) {
			mIbRightImageButton.setImageResource(id);
			setOnRightImageButtonClickListener(listener);
		}
	}
	
	
	/**
	 * 标题左边按钮及中间文字
	 * @see HeaderStyle.TITLE_LEFT_IMAGEBUTTON
	 * <p>Title: titleLeftButton
	 * <p>Description:
	 */
	private void titleLeftButton(){
		mLayoutLeftTitle.setVisibility(View.GONE);
		mLayoutLeftButton.setVisibility(View.VISIBLE);
		mLayoutMiddleContainer.setVisibility(View.VISIBLE);
//		mLayoutRightContainer.removeAllViews();
		mLayoutLeftButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onLeftImageButtonClickListener != null) {
					onLeftImageButtonClickListener.onClick();
				}
			}
		});
	}
	
	
	/**
	 * 标题左边按钮及中间文字
	 * @see HeaderStyle.TITLE_LEFT_IMAGEBUTTON
	 * <p>Title: setTitleRightImageButton
	 * <p>Description: 
	 * @param title
	 * @param subTitle
	 * @param id
	 * @param listener
	 */
	public void setTitleLeftImageButton(String middleTitle, int id,
			onLeftImageButtonClickListener listener) {
		if (!TextUtils.isEmpty(middleTitle)) {
			mMiddleStvTitle.setText(middleTitle);
		}else{
			mMiddleStvTitle.setVisibility(View.GONE);
		}
		if (mIbLeftButton != null && id > 0) {
			mIbLeftButton.setImageResource(id);
			setOnLeftImageButtonClickListener(listener);
		}
	}
	
	
	/**
	 * 左边右边按钮
	 * @see HeaderStyle.TITLE_LEFT_BUTTON_AND_RIGHT_BUTTON
	 * <p>Title: setTitleLeftButtonAndRightButton
	 * <p>Description:
	 */
	private void setTitleLeftButtonAndRightButton(){
		mLayoutLeftTitle.setVisibility(View.GONE);
		mLayoutLeftButton.setVisibility(View.VISIBLE);
//		mLayoutMiddleContainer.removeAllViews();
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		mIbRightImageButton.setVisibility(View.VISIBLE);
		mLayoutLeftButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onLeftImageButtonClickListener != null) {
					onLeftImageButtonClickListener.onClick();
				}
			}
		});
		mLayoutRightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mRightImageButtonClickListener != null) {
					mRightImageButtonClickListener.onClick();
				}
			}
		});
	}
	
	/**
	 * 左边右边按钮
	 * @see HeaderStyle.TITLE_LEFT_BUTTON_AND_RIGHT_BUTTON
	 * <p>Title: setTitleLeftButtonAndRightButton
	 * <p>Description: 
	 * @param leftResId
	 * @param listener
	 * @param rightResId
	 * @param listener2
	 */
	public void setTitleLeftButtonAndRightButton(int leftResId,
			onLeftImageButtonClickListener listener, int rightResId,
			onRightImageButtonClickListener listener2){
		if (mIbLeftButton != null && leftResId > 0) {
			mIbLeftButton.setImageResource(leftResId);
			setOnLeftImageButtonClickListener(listener);
		}
		if (mIbRightImageButton != null && rightResId > 0) {
			mIbRightImageButton.setImageResource(rightResId);
			setOnRightImageButtonClickListener(listener2);
		}
	}
	
	
	/**
	 * 右边按钮
	 * <p>Title: setTitltleRightImage
	 * <p>Description: 
	 * @param resId
	 */
	public void setTitltleRightImage(int resId){
		if (mIbRightImageButton != null && resId != 0) {
			mIbRightImageButton.setImageResource(resId);
		}
	}
	
	public void setTitleRightButtonStatus(boolean isVisiable){
		if (isVisiable) {
			mLayoutRightContainer.setVisibility(View.VISIBLE);
//			mIbRightImageButton.setVisibility(View.VISIBLE);
		}else{
			mLayoutRightContainer.setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * 左边按钮，右边文本按钮
	 * @see HeaderStyle.TITLE_LEFT_BUTTON_AND_RIGHT_TEXT
	 * <p>Title: setTitleLeftButtonAndRightText
	 * <p>Description:
	 */
	private void setTitleLeftButtonAndRightTextButton(){
		mLayoutLeftTitle.setVisibility(View.GONE);
		mLayoutLeftButton.setVisibility(View.VISIBLE);
//		mLayoutMiddleContainer.removeAllViews();
		mLayoutLeftButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onLeftImageButtonClickListener != null) {
					onLeftImageButtonClickListener.onClick();
				}
			}
		});
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		mLayoutRightContainer.removeAllViews();
		mHtvRightTextLayout = (LinearLayout) mInflater.inflate(R.layout.include_header_righttext, null);
		mHtvRightTextLayout.setClickable(true);
		mLayoutRightContainer.addView(mHtvRightTextLayout);
		ViewGroup.LayoutParams lp = mHtvRightTextLayout.getLayoutParams();
		lp.height = ViewGroup.LayoutParams.FILL_PARENT;
		mHtvRightTextLayout.setLayoutParams(lp);
		mHtvRightText = (TextView) mHtvRightTextLayout
				.findViewById(R.id.header_htv_righttext);
		mHtvRightTextLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mRightImageButtonClickListener != null) {
					mRightImageButtonClickListener.onClick();
				}
			}
		});
	}
	
	/**
	 *  左边按钮，右边文本按钮
	 * @see HeaderStyle.TITLE_LEFT_BUTTON_AND_RIGHT_TEXT
	 * <p>Title: setTitleLeftButtonAndRightTextButton
	 * <p>Description: 
	 * @param title
	 * @param subTitle
	 * @param rightText
	 * @param listener
	 */
	public void setTitleLeftButtonAndRightTextButton(int leftResId,
			onLeftImageButtonClickListener listener, 
			CharSequence rightText,
			onRightImageButtonClickListener listener2) {
		if (mIbLeftButton != null && leftResId > 0) {
			mIbLeftButton.setImageResource(leftResId);
			setOnLeftImageButtonClickListener(listener);
		}
		if (mHtvRightText != null && rightText != null) {
			mHtvRightText.setText(rightText);
		}
		setOnRightImageButtonClickListener(listener2);
	}
	
	/**
	 * 右边边文本按钮
	 * @see HeaderStyle.TITLE_LEFT_BUTTON_AND_RIGHT_TEXT
	 * <p>Title: setTitltleRightText
	 * <p>Description: 
	 * @param text
	 */
	public void setTitltleRightText(String text){
		if (mHtvRightText != null && text != null) {
			mHtvRightText.setText(text);
		}
	}
	
	/**
	 * 设置右边文本按钮是否可见
	 * @see HeaderStyle.TITLE_LEFT_BUTTON_AND_RIGHT_TEXT
	 * <p>Title: setTitleRightTextStatus
	 * <p>Description: 
	 * @param isVisiable
	 */
	public void setTitleRightTextStatus(boolean isVisiable){
		if (isVisiable) {
			mHtvRightTextLayout.setVisibility(View.VISIBLE);
		}else{
			mHtvRightTextLayout.setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * 左边按钮，右边文本按钮时，设置右边文本
	 * <p>Title: setTitleRightText
	 * <p>Description: 
	 * @param text
	 */
	public void setTitleRightText(String text){
		if (mHtvRightText != null && text != null) {
			mHtvRightText.setText(text);
		}
	}
	
	
	/**
	 * 设置聊天顶部视图
	 * @see HeaderStyle.TITLE_CHAT
	 * <p>Title: titleChat
	 * <p>Description:
	 */
	public void titleChat(){
		mLayoutLeftTitle.setVisibility(View.VISIBLE);
//		mLayoutMiddleContainer.removeAllViews();
		mLayoutRightContainer.setVisibility(View.VISIBLE);
		mLayoutRightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mRightImageButtonClickListener != null) {
					mRightImageButtonClickListener.onClick();
				}
			}
		});
	}
	
	
	/**
	 * 标题以及右边按钮
	 * @see HeaderStyle.TITLE_CHAT
	 * <p>Title: setTitleRightImageButton
	 * <p>Description: 
	 * @param title
	 * @param subTitle
	 * @param id
	 * @param listener
	 */
	public void setChat(CharSequence title,
			CharSequence subTitle, 
			int rightImageIdRes, onRightImageButtonClickListener rightListener) {
		setDefaultTitle(title, subTitle);
		if (mIbRightImageButton != null && rightImageIdRes > 0) {
			mIbRightImageButton.setImageResource(rightImageIdRes);
			setOnRightImageButtonClickListener(rightListener);
		}
	}
	
	
	public void setOnLeftImageButtonClickListener(
			onLeftImageButtonClickListener listener) {
		onLeftImageButtonClickListener = listener;
	}

	public void setOnRightImageButtonClickListener(
			onRightImageButtonClickListener listener) {
		mRightImageButtonClickListener = listener;
	}
	
	public void setOnRightImageButtonClickListener2(
			onRightImageButtonClickListener2 listener) {
		mRightImageButtonClickListener2 = listener;
	}

	public void setOnMiddleImageButtonClickListener(
			onMiddleImageButtonClickListener listener) {
		mMiddleImageButtonClickListener = listener;
	}

	public interface onLeftImageButtonClickListener {
		void onClick();
	}

	public interface onRightImageButtonClickListener {
		void onClick();
	}
	
	public interface onRightImageButtonClickListener2 {
		void onClick();
	}

	public interface onMiddleImageButtonClickListener {
		void onClick();
	}
	
}
