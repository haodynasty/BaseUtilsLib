/*
 * FileName: RotateImageView.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : quhao <blakequ@gmail.com>
 * date     : 2014-6-7 下午3:51:29
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * 旋转动画组件ImageView
 * @author blakequ Blakequ@gmail.com
 *
 */
public class RotateImageView extends ImageView {

	private RotateAnimation mAnimation;
	private boolean mIsHasAnimation;
	private Context context;
	private long mDuration = 700L;
	private Interpolator mInterpolator;
	private int mRepeatCount = 0;
	private int mMode;
	private float startDegrees;
	private float endDegrees;
	
	public RotateImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	public RotateImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public RotateImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context){
		this.context = context;
		mInterpolator = new LinearInterpolator();
		mRepeatCount = 0;
		mMode = Animation.RESTART;
		startDegrees = 0f;
		endDegrees = 359f;
	}
	

	/**
	 * 动画持续时间,毫秒
	 * <p>Title: setAnimationDuration
	 * <p>Description: 
	 * @param mDuration
	 */
	public void setAnimationDuration(long mDuration) {
		this.mDuration = mDuration;
	}

	/**
	 * 动画加速器，默认LinearInterpolator
	 * <p>Title: setAnimationInterpolator
	 * <p>Description: 
	 * @param mInterpolator
	 */
	public void setAnimationInterpolator(Interpolator mInterpolator) {
		this.mInterpolator = mInterpolator;
	}

	/**
	 * 动画重复次数，-1为循环，0为循环一次，默认0
	 * <p>Title: setAnimationRepeatCount
	 * <p>Description: 
	 * @param mRepeatCount
	 */
	public void setAnimationRepeatCount(int mRepeatCount) {
		this.mRepeatCount = mRepeatCount;
	}

	/**
	 * 动画模式, 默认Animation.RESTART
	 * <p>Title: setAnimationMode
	 * <p>Description: 
	 * @param mMode Animation.REVERSE or Animation.RESTART
	 */
	public void setAnimationMode(int mMode) {
		this.mMode = mMode;
	}
	
	/**
	 * 动画开始角度，默认0
	 * <p>Title: setAnimationStartDegrees
	 * <p>Description: 
	 * @param startDegrees
	 */
	public void setAnimationStartDegrees(float startDegrees) {
		this.startDegrees = startDegrees;
	}
	
	/**
	 * 设置动画结束角度，默认359
	 * <p>Title: setAnimationEndDegrees
	 * <p>Description: 
	 * @param endDegrees 可为负数
	 */
	public void setAnimationEndDegrees(float endDegrees) {
		this.endDegrees = endDegrees;
	}
	
	/**
	 * 设置动画参数
	 * <p>Title: setAnimationParams
	 * <p>Description: 
	 * @param mDuration
	 * @param mRepeatCount -1则表示循环
	 * @param startDegrees 动画开始角度
	 * @param endDegrees 动画结束角度
	 */
	public void setAnimationParams(long mDuration, int mRepeatCount, float startDegrees, float endDegrees){
		this.mDuration = mDuration;
		this.mRepeatCount = mRepeatCount;
		this.startDegrees = startDegrees;
		this.endDegrees = endDegrees;
	}

	private void setRotateAnimation() {
		if (mIsHasAnimation == false && getWidth() > 0
				&& getVisibility() == View.VISIBLE) {
			mIsHasAnimation = true;
			mAnimation = new RotateAnimation(startDegrees, endDegrees, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			mAnimation.setDuration(mDuration);
			mAnimation.setInterpolator(mInterpolator);
			mAnimation.setRepeatCount(mRepeatCount); //无限循环
			mAnimation.setRepeatMode(mMode);
			setAnimation(mAnimation);
		}
	}

	/**
	 * 清除动画
	 * <p>Title: clearRotateAnimation
	 * <p>Description:
	 */
	private void clearRotateAnimation() {
		if (mIsHasAnimation) {
			mIsHasAnimation = false;
			setAnimation(null);
			mAnimation = null;
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		setRotateAnimation();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		clearRotateAnimation();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (w > 0) {
			setRotateAnimation();
		}
	}

	/**
	 * 开始动画
	 * <p>Title: startAnimation
	 * <p>Description:
	 */
	public void startAnimation() {
		if (mIsHasAnimation) {
			super.startAnimation(mAnimation);
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		if (visibility == View.INVISIBLE || visibility == View.GONE) {
			clearRotateAnimation();
		} else {
			setRotateAnimation();
		}
	}
}
