package com.plusub.lib.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * 自定义控件，用于显示宽度和ImageView相同，高度自适应的图片显示模式.
 * 除此之外，还添加了最大高度限制，若图片长度大于等于屏幕长度，则高度显示为屏幕的1/3
 * Created by zhaokaiqiang on 15/4/20.
 */
public class ShowMaxImageView extends ImageView {

	private float mHeight = 0;

	public ShowMaxImageView(Context context) {
		super(context);
	}

	public ShowMaxImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ShowMaxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {

		if (bm != null) {
			getHeight(bm);
		}

		super.setImageBitmap(bm);
		requestLayout();
	}

	@Override
	public void setImageDrawable(Drawable drawable) {

		if (drawable != null) {
			getHeight(drawableToBitamp(drawable));
		}

		super.setImageDrawable(drawable);
		requestLayout();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (mHeight != 0) {

			int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
			int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

			int resultHeight = (int) Math.max(mHeight, sizeHeight);

			if (resultHeight >= getScreenH((Activity) getContext())) {
				resultHeight = getScreenH((Activity) getContext()) / 3;
			}

			setMeasuredDimension(sizeWidth, resultHeight);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

	}

	private void getHeight(Bitmap bm) {

		float bitmapWidth = bm.getWidth();
		float bitmapHeight = bm.getHeight();

		if (bitmapWidth > 0 && bitmapHeight > 0) {
			float scaleWidth = getWidth() / bitmapWidth;
			mHeight = bitmapHeight * scaleWidth;
		}

	}


	private Bitmap drawableToBitamp(Drawable drawable) {

		if (drawable != null) {
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		} else {
			return null;
		}
	}

	private int getScreenH(Activity aty) {
		DisplayMetrics dm = new DisplayMetrics();
		aty.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int h = dm.heightPixels;
		return h;
	}

}
