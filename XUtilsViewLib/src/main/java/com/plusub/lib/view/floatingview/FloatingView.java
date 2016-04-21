/*
 * FileName: FloatingView.java
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
 * date     : 2015-4-21 下午8:33:22
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.view.floatingview;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * from:https://github.com/makovkastar/FloatingActionButton
 * <h2>使用方法：</h2>
 * <ol>
 *     <li>将FloatingView放置在ListView或ScrollView视图上（使用FrameLayout布局）</li>
 *     <li>直接调用方法{@link #attachToListView(AbsListView)}或者{@link #attachToScrollView(ObservableScrollView)}即可实现随着
 *     ListView或ScrollView的滑动FloatingView同步隐藏或出现</li>
 *     <li>如果需要在滑动监听方向或滑动，使用方法{@link #attachToScrollView(ObservableScrollView, ScrollDirectionListener, ObservableScrollView.OnScrollChangedListener)}即可,</li>
 *     		注意，如果设置了监听器，就会覆盖ListView和ViewPager内自己设置的OnScrollChangedListener监听器，即setOnScrollChangedListener的方法无效
 *     <li>可手动控制显示和隐藏，通过方法{@link #show()}和{@link #hide()}</li>
 * </ol>
 * <h2>布局实例：</h2>
	<pre class="prettyprint">
  &lt;FrameLayout 
        android:layout_width="match_parent"
        android:layout_height="match_parent"&gt;
	     &lt;ListView
		        android:id="@id/common_listview"
		        style="@style/SimpleListView"
		        android:drawSelectorOnTop="false"
		        android:layout_width="fill_parent"
		        android:layout_height="fill_parent"
		        android:divider="@null"
		        android:dividerHeight="0.0dip" /&gt;
	     &lt;com.plusub.lib.view.floatingview.FloatingView
	        android:id="@+id/fab"
	        android:layout_width="wrap_content"
	        android:layout_height="wrap_content"
	        android:layout_gravity="bottom|right"
	        android:layout_margin="16dp"
	        android:src="@drawable/ic_add_white_24dp"&gt;
	        &lt;TextView
	        	android:layout_width="fill_parent"
		        android:layout_height="wrap_content"
		        text="你好"
	        	/&gt;
	        中间和可以添加任何其他视图
	      &lt;/com.plusub.lib.view.floatingview.FloatingView&gt;
    &lt;/FrameLayout&gt;
 *
 * @ClassName: FloatingView
 * @Description: TODO 浮动在ListView或ScrollView上的视图组件
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-4-21 下午8:33:22<br>
 *     <b>最后修改时间：</b>2015-4-21 下午8:33:22
 * @version v1.0
 */
public class FloatingView extends LinearLayout {

	private Context mContext;
	private boolean mVisible;
	//动画加速器
	private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
	//动画持续时间(毫秒)
	private static final int TRANSLATE_DURATION_MILLIS = 200;
	//引起滑动的最小距离(灵敏度)
	private int mScrollThreshold = 4;
	
	public FloatingView(Context context){
		super(context);
	}
	
	public FloatingView(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	@SuppressLint("NewApi")
	public FloatingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	private void init(Context context){
		this.mContext = context;
		mVisible = true;
		mScrollThreshold = Dp2Px(context, 4);
	}
	
	private int Dp2Px(Context context, float dp) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (dp * scale + 0.5f); 
    } 

	/**
	 * 是否显示
	 * <p>Title: isVisible
	 * <p>Description: 
	 * @return
	 */
	public boolean isVisible() {
        return mVisible;
    }
	
	/**
	 * 显示悬浮视图
	 * <p>Title: show
	 * <p>Description:
	 */
	public void show() {
        show(true);
    }

	/**
	 * 隐藏悬浮视图
	 * <p>Title: hide
	 * <p>Description:
	 */
    public void hide() {
        hide(true);
    }

   /**
    * 显示悬浮视图
    * <p>Title: show
    * <p>Description: 
    * @param animate 是否显示动画
    */
    public void show(boolean animate) {
        toggle(true, animate, false);
    }

    /**
     * 隐藏悬浮视图
     * <p>Title: show
     * <p>Description: 
     * @param animate 是否显示动画
     */
    public void hide(boolean animate) {
        toggle(false, animate, false);
    }
	
    /**
     * 平移视图
     * <p>Title: toggle
     * <p>Description: 
     * @param visible
     * @param animate
     * @param force
     */
	private void toggle(final boolean visible, final boolean animate, boolean force) {
        if (mVisible != visible || force) {
            mVisible = visible;
            int height = getHeight();
            if (height == 0 && !force) {
                ViewTreeObserver vto = getViewTreeObserver();
                if (vto.isAlive()) {
                    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver currentVto = getViewTreeObserver();
                            if (currentVto.isAlive()) {
                                currentVto.removeOnPreDrawListener(this);
                            }
                            toggle(visible, animate, true);
                            return true;
                        }
                    });
                    return;
                }
            }
            int translationY = visible ? 0 : height + getMarginBottom();
            if (animate) {
                ViewPropertyAnimator.animate(this).setInterpolator(mInterpolator)
                    .setDuration(TRANSLATE_DURATION_MILLIS)
                    .translationY(translationY);
            } else {
                ViewHelper.setTranslationY(this, translationY);
            }

            // On pre-Honeycomb a translated view is still clickable, so we need to disable clicks manually
            if (!hasHoneycombApi()) {
                setClickable(visible);
            }
        }
    }
	
	/**
	 * 与底部的边距
	 * <p>Title: getMarginBottom
	 * <p>Description: 
	 * @return
	 */
	private int getMarginBottom() {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }

	@SuppressLint("NewApi")
	private boolean hasHoneycombApi() {//Android 3.0
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }
	
	/**
	 * 将视图附加到ListView实现同步
	 * <p>Title: attachToListView
	 * <p>Description: 
	 * @param listView 待附加的ListView
	 */
	public void attachToListView(AbsListView listView) {
        attachToListView(listView, null, null);
    }

	/**
	 *  将视图附加到ListView实现同步
	 * <p>Title: attachToListView
	 * <p>Description: 
	 * @param listView 待附加的ListView
	 * @param scrollDirectionListener 滑动方向监听器
	 */
    public void attachToListView(AbsListView listView,
                                 ScrollDirectionListener scrollDirectionListener) {
        attachToListView(listView, scrollDirectionListener, null);
    }


    /**
     * 将视图附加到ScrollView
     * <p>Title: attachToScrollView
     * <p>Description: 
     * @param scrollView 待附加的ScrollView
     */
    public void attachToScrollView(ObservableScrollView scrollView) {
        attachToScrollView(scrollView, null, null);
    }

    /**
     * 将视图附加到ScrollView
     * <p>Title: attachToScrollView
     * <p>Description: 
     * @param scrollView 待附加的ScrollView
     * @param scrollDirectionListener 滑动方向监听器
     */
    public void attachToScrollView(ObservableScrollView scrollView,
                                   ScrollDirectionListener scrollDirectionListener) {
        attachToScrollView(scrollView, scrollDirectionListener, null);
    }

    /**
	 *  将视图附加到ListView实现同步
	 * <p>Title: attachToListView
	 * <p>Description: 
	 * @param listView 待附加的ListView
	 * @param scrollDirectionListener 滑动方向监听器
	 * @param onScrollListener 滑动监听器
	 */
    public void attachToListView(AbsListView listView,
                                 ScrollDirectionListener scrollDirectionListener,
                                 AbsListView.OnScrollListener onScrollListener) {
    	if (listView == null) {
			return;
		}
        AbsListViewScrollDetectorImpl scrollDetector = new AbsListViewScrollDetectorImpl();
        scrollDetector.setScrollDirectionListener(scrollDirectionListener);
        scrollDetector.setOnScrollListener(onScrollListener);
        scrollDetector.setListView(listView);
        scrollDetector.setScrollThreshold(mScrollThreshold);
      //将实现了的滑动检测scrollDetector设置为listView的监听器
        listView.setOnScrollListener(scrollDetector);
    }


    /**
     * 将视图附加到ScrollView
     * <p>Title: attachToScrollView
     * <p>Description: 
     * @param scrollView 待附加的ScrollView
     * @param scrollDirectionListener 滑动方向监听器
     * @param onScrollChangedListener 滑动监听器
     */
    public void attachToScrollView(ObservableScrollView scrollView,
                                   ScrollDirectionListener scrollDirectionListener,
                                   ObservableScrollView.OnScrollChangedListener onScrollChangedListener) {
    	if (scrollView == null) {
			return;
		}
        ScrollViewScrollDetectorImpl scrollDetector = new ScrollViewScrollDetectorImpl();
        scrollDetector.setScrollDirectionListener(scrollDirectionListener);
        scrollDetector.setOnScrollChangedListener(onScrollChangedListener);
        scrollDetector.setScrollThreshold(mScrollThreshold);
        //将实现了的滑动检测scrollDetector设置为scrollView的监听器
        scrollView.setOnScrollChangedListener(scrollDetector);
    }
    
    /**
     * ListView滑动检测实现
     * @ClassName: AbsListViewScrollDetectorImpl
     * @Description: TODO
     * @author qh@plusub.com
     * @date 2015-4-21 下午8:52:52
     * @version v1.0
     */
    private class AbsListViewScrollDetectorImpl extends AbsListViewScrollDetector {
        private ScrollDirectionListener mScrollDirectionListener;
        private AbsListView.OnScrollListener mOnScrollListener;

        private void setScrollDirectionListener(ScrollDirectionListener scrollDirectionListener) {
            mScrollDirectionListener = scrollDirectionListener;
        }

        public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
            mOnScrollListener = onScrollListener;
        }

        @Override
        public void onScrollDown() {
            show();
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollDown();
            }
        }

        @Override
        public void onScrollUp() {
            hide();
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollUp();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                             int totalItemCount) {
            if (mOnScrollListener != null) {
                mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }

            super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrollStateChanged(view, scrollState);
            }

            super.onScrollStateChanged(view, scrollState);
        }
    }

    /**
     * ScrollView检测实现
     * @ClassName: ScrollViewScrollDetectorImpl
     * @Description: TODO
     * @author qh@plusub.com
     * @date 2015-4-21 下午8:52:36
     * @version v1.0
     */
    private class ScrollViewScrollDetectorImpl extends ScrollViewScrollDetector {
        private ScrollDirectionListener mScrollDirectionListener;

        private ObservableScrollView.OnScrollChangedListener mOnScrollChangedListener;

        private void setScrollDirectionListener(ScrollDirectionListener scrollDirectionListener) {
            mScrollDirectionListener = scrollDirectionListener;
        }

        public void setOnScrollChangedListener(ObservableScrollView.OnScrollChangedListener onScrollChangedListener) {
            mOnScrollChangedListener = onScrollChangedListener;
        }

        @Override
        public void onScrollDown() {
            show();
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollDown();
            }
        }

        @Override
        public void onScrollUp() {
            hide();
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollUp();
            }
        }

        @Override
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
            if (mOnScrollChangedListener != null) {
                mOnScrollChangedListener.onScrollChanged(who, l, t, oldl, oldt);
            }

            super.onScrollChanged(who, l, t, oldl, oldt);
        }
    }
}
