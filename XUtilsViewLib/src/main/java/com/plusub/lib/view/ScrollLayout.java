package com.plusub.lib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Scroller;

/**
 * 滑动视图，可以使根布局下面的所有子视图具有ViewPager滑动效果（只适用于简单的两个视图滑动效果，复杂的还是用ViewPager）
 * <a href="http://www.cnblogs.com/wader2011/archive/2011/10/10/2205142.html" target="_blank">博客</a>
 * 
 * <h1>使用方法(子视图1和子视图2有ViewPager滑动效果)：</h1>
 * <pre class="prettyprint">
 * &lt;com.plusub.basechatdemo.view.ScrollLayout
                android:id="@+id/chat_bottom_editor_gallery"
                android:layout_width="fill_parent"
                android:layout_height="@dimen/message_editor_hight"
                android:layout_above="@id/chat_bottom_emotes_layout"
                android:background="@drawable/bg_chatbar_textmode" &gt;
               //子视图1
                &lt;include layout="@layout/include_chat_bottom_texteditor" /&gt;
                //子视图2
                &lt;include layout="@layout/include_chat_bottom_audioeditor" /&gt;
            &lt;/com.plusub.basechatdemo.view.ScrollLayout&gt;
 * </pre>
 * <h1>使用示例：</h1>
 * <li>具有滑动的效果切换{@link #snapToScreen(int)}
 * <li>没有滑动效果的切换{@link {@link #setToScreen(int)}
 * <li>设置是否可以滑动进行切换{@link #setScrollable(boolean)}}
 * 
 * @author blakequ Blakequ@gmail.com
 *
 */
public class ScrollLayout extends ViewGroup {

	public static boolean startTouch = true;
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;
	private static int mCurScreen;

	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;

	private int mTouchState = TOUCH_STATE_REST;
	private static final int SNAP_VELOCITY = 600;
	private int mTouchSlop;

	private float mLastMotionX;
	private float mLastMotionY;

	private OnScrollToScreenListener onScrollToScreen = null;

	public ScrollLayout(Context context) {
		super(context);
	}

	public ScrollLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				final int childWidth = childView.getMeasuredWidth();
				childView.layout(childLeft, 0, childLeft + childWidth,
						childView.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					"ScrollLayout only can run at EXACTLY mode!");
		}
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					"ScrollLayout only can run at EXACTLY mode!");
		}
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		scrollTo(mCurScreen * width, 0);
		doScrollAction(mCurScreen);
	}

	/**
	 * 滑动到滑动的方向
	 * <p>Title: snapToDestination
	 * <p>Description:
	 */
	public void snapToDestination() {
		final int screenWidth = getWidth();
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		snapToScreen(destScreen);
	}

	/**
	 * snapToScreen 方法描述：滑动到到第whichScreen（从0开始）个界面，有过渡效果
	 * <p>Title: snapToScreen
	 * <p>Description: 
	 * @param whichScreen
	 */
	public void snapToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		if (getScrollX() != (whichScreen * getWidth())) {
			final int delta = whichScreen * getWidth() - getScrollX();
			mScroller.startScroll(getScrollX(), 0, delta, 0,
					Math.abs(delta) * 2);
			mCurScreen = whichScreen;
			doScrollAction(mCurScreen);
			invalidate();
		}
	}

	/**
	 * 指定并跳转到第whichScreen（从0开始）个界面，无过渡效果
	 * <p>Title: setToScreen
	 * <p>Description: 
	 * @param whichScreen
	 */
	public void setToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		mCurScreen = whichScreen;
		scrollTo(whichScreen * getWidth(), 0);
		doScrollAction(whichScreen);
	}

	public int getCurScreen() {
		return mCurScreen;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}
	
	/**
	 * 设置是否可滑动
	 * <p>Title: setScrollDisable
	 * <p>Description:
	 * @param isScroll true则可滑动，false为不可滑动
	 */
	public void setScrollable(boolean isScroll){
		if (!isScroll) {
			setOnTouchListener(new OnTouchListener() {//禁止滑动
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					return true;
				}
			});
		}else{
			setOnTouchListener(null);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
		final int action = event.getAction();
		final float x = event.getX();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mLastMotionX = x;
			break;

		case MotionEvent.ACTION_MOVE:
			int deltaX = (int) (mLastMotionX - x);
			mLastMotionX = x;
			if (!(mCurScreen == 0 && deltaX < 0 || mCurScreen == getChildCount() - 1
					&& deltaX > 0)) {
				scrollBy(deltaX, 0);
			}
			break;

		case MotionEvent.ACTION_UP:
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			int velocityX = (int) velocityTracker.getXVelocity();
			if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
				snapToScreen(mCurScreen - 1);
			} else if (velocityX < -SNAP_VELOCITY
					&& mCurScreen < getChildCount() - 1) {
				snapToScreen(mCurScreen + 1);
			} else {
				snapToDestination();
			}
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			mTouchState = TOUCH_STATE_REST;
			break;

		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE)
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}
		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			mLastMotionY = y;
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;
			break;

		case MotionEvent.ACTION_MOVE:
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			if (xDiff > mTouchSlop) {
				if (Math.abs(mLastMotionY - y) / Math.abs(mLastMotionX - x) < 1)
					mTouchState = TOUCH_STATE_SCROLLING;
			}
			break;

		case MotionEvent.ACTION_CANCEL:

		case MotionEvent.ACTION_UP:
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return mTouchState != TOUCH_STATE_REST;
	}

	private void doScrollAction(int whichScreen) {
		if (onScrollToScreen != null) {
			onScrollToScreen.doAction(whichScreen);
		}
	}

	/**
	 * 设置滑动监听器
	 * <p>Title: setOnScrollToScreen
	 * <p>Description: 
	 * @param paramOnScrollToScreen
	 */
	public void setOnScrollToScreen(
			OnScrollToScreenListener paramOnScrollToScreen) {
		onScrollToScreen = paramOnScrollToScreen;
	}

	public abstract interface OnScrollToScreenListener {
		public void doAction(int whichScreen);
	}

	public void setDefaultScreen(int position) {
		mCurScreen = position;
	}
}
