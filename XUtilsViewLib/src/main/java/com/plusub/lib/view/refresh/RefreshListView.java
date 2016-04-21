package com.plusub.lib.view.refresh;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.plusub.lib.view.R;
import com.plusub.lib.view.floatingview.ScrollDirectionListener;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 刷新ListView
 * 使用说明：
 * <br>
 * <b>1:设置基本属性</b>
 	mRefreshList.setDropDownStyle(true); //设置是否可下拉，默认true
   mRefreshList.setOnBottomStyle(true); //设置是否有底部加载更多，默认false
   mRefreshList.setShowFooterProgressBar(true); //是否显示底部加载时显示动画,默认为true
   <br>注：如下设置都是针对setOnBottomStyle设置为<b>true</b>时有效
  <br>mRefreshList.setAutoLoadOnBottom(true); //是否滑动到底部自动加载，默认false
   mRefreshList.setShowFooterWhenNoMore(true); //设置是否显示底部按钮，当没有数据时，默认true
   mRefreshList.setBottomShowOutOfScreen(true); //设置是否数据列表超出屏幕才显示加载更多按钮，默认false
   
<br>
   <b>2.设置刷新监听</b>
   mRefreshList.setOnRefreshListener //下拉监听
   mRefreshList.setOnBottomListener //底部按钮监听
   
   <br>
   <b>3.其他特性</b>
   mRefreshList.onManualRefresh(); //设置自动下拉刷新
   
   <br>
   <b>4.注意事项</b>
   mRefreshList.onRefreshComplete(); //在刷新完成之后，必须手动调用(头部属性和底部加载都一样)
   mRefreshList.setNoMore(); //如果设置了setShowFooterWhenNoMore(true)，这个会改变加载完成后底部加载更多按钮状态为不可点击
   <br><b>注意：在选择具体数据的时候onItemClick，获取数据都需要mAdapter.getItem(position-1)，实际数据位置在position-1</b>
   <br>如果需要重新下拉刷新和底部加载更多的视图，直接重新覆盖布局文件include_pull_to_refreshing_header和include_pull_to_refreshing_footer
 * @author service@plusub.com
 *
 */
public class RefreshListView extends HandyListView {
	private final static int RELEASE_TO_REFRESH = 0;
	private final static int PULL_TO_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;
	private final static int RATIO = 2;

	private View mHeader;
	private boolean  isDropDownStyle = true;

	private TextView mHtvTitle;
	private TextView mHtvTime;
	private ImageView mIvArrow;
	private ImageView mIvLoading;

	private android.view.animation.RotateAnimation mPullAnimation;
	private android.view.animation.RotateAnimation mReverseAnimation;
	private Animation mLoadingAnimation;

	private boolean mIsRecored;
	private int mHeaderHeight;
	private int mStartY;
	private int mState = DONE;

	private Context context;
	private boolean mIsBack;
	private OnRefreshListener mOnRefreshListener;
	private OnBottomListener mOnBottomListener;
	private ScrollDirectionListener scrollDirectionListener;
	private boolean mIsRefreshable = false;
	private boolean mLastItemVisible = false;
	private boolean mIsShowBottomOutOfScreen = false; //是否在数据超出屏幕范围才显示更多按钮
	private boolean mIsOutScreen = false; //是否数据列表超出屏幕
	
    private String headerPullText;
    private String headerReleaseText;
    private String headerLoadingText;
    private String headerSecondText; //日期
    private String footerDefaultText;
    private String footerLoadingText;
    private String footerNoMoreText;
    
    //底部加载更多
    private boolean isOnBottomStyle = false;
    private boolean isAutoLoadOnBottom = false;
    /** footer layout view **/
   private RelativeLayout     footerLayout;
   private ImageView        footerProgressBar;
   private RelativeLayout footerBotton;
   private TextView footerText;
   
   /** whether bottom listener has more **/
	private boolean hasMore = true;
	  /** whether show footer loading progress bar when loading **/
	private boolean isShowFooterProgressBar = true;
	  /** whether show footer when no more data **/
	private boolean isShowFooterWhenNoMore = true;
	  /** whether is on bottom loading **/
	private boolean isOnBottomLoading = false;
	 /**是否只是底部加载更多*/
	 private boolean isBottomOnly = false;
    
    //滑动方向
	 private int mLastScrollY;
	 private int mPreviousFirstVisibleItem;
	 private int mScrollThreshold = 4;

	public RefreshListView(Context context) {
		super(context);
		init(context);
	}

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		initOnBottomStyle();
		initDropDownStyle();
	}
	
	/**
     * init drop down style, only init once
     */
    private void initDropDownStyle() {
//        if (mHeader != null) {
//            if (isDropDownStyle) {
//                addHeaderView(mHeader, null, false); //解决点击头部报错问题-1
//            } else {
//                removeHeaderView(mHeader);
//            }
//            return;
//        }
//        if (!isDropDownStyle) {
//            return;
//        }
    	
    	if (mHeader != null && getHeaderViewsCount() == 0) {
    		addHeaderView(mHeader, null, false); //解决点击头部报错问题-1
    		return;
		}
    	
    	if (getHeaderViewsCount() == 1) {
			return;
		}
        
        mHeader = mInflater.inflate(R.layout.include_pull_to_refreshing_header,
				null);
		mHtvTitle = (TextView) mHeader
				.findViewById(R.id.refreshing_header_htv_title);
		mHtvTime = (TextView) mHeader
				.findViewById(R.id.refreshing_header_htv_time);
		mIvArrow = (ImageView) mHeader
				.findViewById(R.id.refreshing_header_iv_arrow);
		mIvLoading = (ImageView) mHeader
				.findViewById(R.id.refreshing_header_iv_loading);
		/**bug:修复点击头部，抛出异常*/
		mHeader.setOnClickListener(null);
		
        headerPullText = context.getString(R.string.drop_down_list_header_pull_text);
        headerReleaseText = context.getString(R.string.drop_down_list_header_release_text);
        headerLoadingText = context.getString(R.string.drop_down_list_header_loading_text);

		measureView(mHeader);
		addHeaderView(mHeader);

		mHeaderHeight = mHeader.getMeasuredHeight();
		mHeader.setPadding(0, -1 * mHeaderHeight, 0, 0);
		mHeader.invalidate();

		mHtvTitle.setText(headerPullText);
		mHtvTime.setText(headerSecondText);

		mPullAnimation = new android.view.animation.RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mPullAnimation.setInterpolator(new LinearInterpolator());
		mPullAnimation.setDuration(250);
		mPullAnimation.setFillAfter(true);

		mReverseAnimation = new android.view.animation.RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseAnimation.setInterpolator(new LinearInterpolator());
		mReverseAnimation.setDuration(200);
		mReverseAnimation.setFillAfter(true);

		mLoadingAnimation = new RotateAnimation(0f, 359f,Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		mLoadingAnimation.setDuration(1000);
		mLoadingAnimation.setInterpolator(new LinearInterpolator());
		mLoadingAnimation.setRepeatCount(Animation.INFINITE);
		mLoadingAnimation.setRepeatMode(Animation.RESTART);

		mState = DONE;
		mIsRefreshable = false;
    }

	@Override
	public void onDown(MotionEvent ev) {
		if (mIsRefreshable && isDropDownStyle) {
			if (mFirstVisibleItem == 0 && !mIsRecored) {
				mIsRecored = true;
				mStartY = mDownPoint.y;
			}
		}
	}

	@Override
	public void onMove(MotionEvent ev) {
		if (isOnBottomLoading || isBottomOnly) {
			return;
		}
		if (mIsRefreshable && isDropDownStyle) {
			if (!mIsRecored && mFirstVisibleItem == 0) {
				mIsRecored = true;
				mStartY = mMovePoint.y;
			}
			if (mState != REFRESHING && mIsRecored && mState != LOADING) {
				if (mState == RELEASE_TO_REFRESH) {
					setSelection(0);
					if (((mMovePoint.y - mStartY) / RATIO < mHeaderHeight)
							&& (mMovePoint.y - mStartY) > 0) {
						mState = PULL_TO_REFRESH;
						changeHeaderViewByState();
					} else if (mMovePoint.y - mStartY <= 0) {
						mState = DONE;
						changeHeaderViewByState();
					}
				}
				if (mState == PULL_TO_REFRESH) {
					setSelection(0);
					if ((mMovePoint.y - mStartY) / RATIO >= mHeaderHeight) {
						mState = RELEASE_TO_REFRESH;
						mIsBack = true;
						changeHeaderViewByState();
					} else if (mMovePoint.y - mStartY <= 0) {
						mState = DONE;
						changeHeaderViewByState();
					}
				}
				if (mState == DONE) {
					if (mMovePoint.y - mStartY > 0) {
						mState = PULL_TO_REFRESH;
						changeHeaderViewByState();
					}
				}
				if (mState == PULL_TO_REFRESH) {
					mHeader.setPadding(0, -1 * mHeaderHeight
							+ (mMovePoint.y - mStartY) / RATIO, 0, 0);
				}
				if (mState == RELEASE_TO_REFRESH) {
					mHeader.setPadding(0, (mMovePoint.y - mStartY) / RATIO
							- mHeaderHeight, 0, 0);
				}

			}

		}
	}

	@Override
	public void onUp(MotionEvent ev) {
		if (isOnBottomLoading || isBottomOnly) {
			return;
		}
		if (isDropDownStyle) {
			if (mState != REFRESHING && mState != LOADING) {
				if (mState == PULL_TO_REFRESH) {
					mState = DONE;
					changeHeaderViewByState();
				}
				if (mState == RELEASE_TO_REFRESH) {
					mState = REFRESHING;
					changeHeaderViewByState();
					onRefresh();
					
				}
			}
			mIsRecored = false;
			mIsBack = false;
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	private void changeHeaderViewByState() {
		switch (mState) {
		case RELEASE_TO_REFRESH:
			mIvArrow.setVisibility(View.VISIBLE);
			mIvLoading.setVisibility(View.GONE);
			mHtvTitle.setVisibility(View.VISIBLE);
			mHtvTime.setVisibility(View.VISIBLE);
			mIvArrow.clearAnimation();
			mIvArrow.startAnimation(mPullAnimation);
			mIvLoading.clearAnimation();
			mHtvTitle.setText(headerReleaseText);
			break;
		case PULL_TO_REFRESH:
			mIvArrow.setVisibility(View.VISIBLE);
			mIvLoading.setVisibility(View.GONE);
			mHtvTitle.setVisibility(View.VISIBLE);
			mHtvTime.setVisibility(View.VISIBLE);
			mIvLoading.clearAnimation();
			mIvArrow.clearAnimation();
			if (mIsBack) {
				mIsBack = false;
				mIvArrow.clearAnimation();
				mIvArrow.startAnimation(mReverseAnimation);
				mHtvTitle.setText(headerPullText);
			} else {
				mHtvTitle.setText(headerPullText);
			}
			break;

		case REFRESHING:
			mHeader.setPadding(0, 0, 0, 0);
			mIvLoading.setVisibility(View.VISIBLE);
			mIvArrow.setVisibility(View.GONE);
			mIvLoading.clearAnimation();
			mIvLoading.startAnimation(mLoadingAnimation);
			mIvArrow.clearAnimation();
			mHtvTitle.setText(headerLoadingText);
			mHtvTime.setVisibility(View.VISIBLE);
			break;
		case DONE:
			mHeader.setPadding(0, -1 * mHeaderHeight, 0, 0);

			mIvLoading.setVisibility(View.GONE);
			mIvArrow.clearAnimation();
			mIvLoading.clearAnimation();
			mIvArrow.setImageResource(R.drawable.ic_common_droparrow);
			mHtvTitle.setText(headerPullText);
			mHtvTime.setVisibility(View.VISIBLE);
			break;
		}
	}

	public void onRefreshComplete() {
		//下拉更新
		if (isDropDownStyle && mHeader != null) {
			mState = DONE;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
			String date = format.format(new Date());
			mHtvTime.setText(context.getString(R.string.drop_down_update_at) + date);
			changeHeaderViewByState();
		}
		
		//底部加载
		onBottomComplete();
	}

	private void onRefresh() {
		if (mOnRefreshListener != null) {
			mOnRefreshListener.onRefresh();
			//解决：底部加载完成后，底部加载更多无法点击
			if (isOnBottomStyle && !hasMore) {
				footerText.setText(footerDefaultText);
                footerBotton.setEnabled(true);
                hasMore = true;
			}
			
			//在到底部没有数据时，加载更多按钮消失，下拉刷新后没有在出现
			if (isOnBottomStyle && !isShowFooterWhenNoMore) {
				if (getFooterViewsCount() == 0) {
					initOnBottomStyle();
				}
			}
		}
	}

	public void onManualRefresh() {
		if (isDropDownStyle && mIsRefreshable) {
			mState = REFRESHING;
			changeHeaderViewByState();
			onRefresh();
		}
	}

	public void setOnRefreshListener(OnRefreshListener l) {
		mOnRefreshListener = l;
		mIsRefreshable = true;
	}
	

	/**
	 * header view is show
	 * <p>Title: isHeaderShown
	 * <p>Description: 
	 * @return
	 */
	public boolean isHeaderShown(){
		return mState == DONE ? false:true;
	}
	
	/**
     * @return isDropDownStyle
     */
    public boolean isDropDownStyle() {
        return isDropDownStyle;
    }

    /**
     * @param isDropDownStyle
     */
    public void setDropDownStyle(boolean isDropDownStyle) {
        if (this.isDropDownStyle != isDropDownStyle) {
            this.isDropDownStyle = isDropDownStyle;
            initDropDownStyle();
        }
        
        if (!isDropDownStyle && isOnBottomStyle) {
			this.isBottomOnly = true;
		}else{
			this.isBottomOnly = false;
		}
    }
    
    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        if (isDropDownStyle) {
            setSecondPositionVisible();
        }
    }
    
    /**
     * set second position visible(index is 1), because first position is header layout
     */
    public void setSecondPositionVisible() {
        if (getAdapter() != null && getAdapter().getCount() > 0 && getFirstVisiblePosition() == 0) {
            setSelection(1);
        }
    }
    
    /**
     * 是否显示刷新时间的组件
     * <p>Title: setShowHeaderSecondText
     * <p>Description: 
     * @param isVisible
     */
    public void setShowHeaderSecondText(boolean isVisible){
    	if (mHtvTime != null) {
    		if (isVisible) {
    			mHtvTime.setVisibility(View.VISIBLE);
			}else{
				mHtvTime.setVisibility(View.GONE);
			}
		}
    }
    
    public void setLoadingText(String loadingText){
    	headerLoadingText = loadingText;
    	mHtvTitle.setText(headerLoadingText);
    }

	public interface OnRefreshListener {
		public void onRefresh();
	}
	
	public interface OnBottomListener {
		public void onBottom();
	}
	
	/**----------------------------------底部加载start-----------------------------------**/
	 /**
     * init on bottom style, only init once
     */
    private void initOnBottomStyle() {
    	//如果设置为当数据填充满屏幕才显示更多按钮，则要数据填充满屏幕才能添加bottom view
    	if (mIsShowBottomOutOfScreen && !mIsOutScreen) { //mIsOutScreen为true表示数据列表填充满了屏幕
			return;
		}
    	
        if (footerLayout != null) {
            if (isOnBottomStyle) {
                addFooterView(footerLayout);
            } else {
            	if (getAdapter() != null) { //fix android version error
            		removeFooterView(footerLayout);
				}
            }
            return;
        }
        if (!isOnBottomStyle) {
            return;
        }

        footerDefaultText = context.getString(R.string.drop_down_list_footer_default_text);
        footerLoadingText = context.getString(R.string.drop_down_list_footer_loading_text);
        footerNoMoreText = context.getString(R.string.drop_down_list_footer_no_more_text);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerLayout = (RelativeLayout)inflater.inflate(R.layout.include_pull_to_refreshing_footer, this, false);
        footerBotton = (RelativeLayout) footerLayout.findViewById(R.id.drop_down_footer_layout);
        footerBotton.setEnabled(true);
        footerBotton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//必须是非下拉刷新状态才能点击
				if (mOnBottomListener != null && mState == DONE) {
					mOnBottomListener.onBottom();
					onBottom();
				}
			}
		});
        footerText = (TextView)footerLayout.findViewById(R.id.drop_down_list_footer_text);
        footerProgressBar = (ImageView)footerLayout.findViewById(R.id.drop_down_list_footer_progress_bar);
        addFooterView(footerLayout);
    }
    
    /**
     * 加载更多按钮
     * @return isOnBottomStyle
     */
    public boolean isShowMoreBottom() {
        return isOnBottomStyle;
    }

    /**
     * 设置加载更多view
     * @param isOnBottomStyle
     */
    public void setOnBottomStyle(boolean isShowMoreBottomStyle) {
        if (this.isOnBottomStyle != isShowMoreBottomStyle) {
            this.isOnBottomStyle = isShowMoreBottomStyle;
            initOnBottomStyle();
        }
        if (!isDropDownStyle && isShowMoreBottomStyle) {
			this.isBottomOnly = true;
		}else{
			this.isBottomOnly = false;
		}
    }
    

    /**
     * @return isAutoLoadOnBottom
     */
    public boolean isAutoLoadOnBottom() {
        return isAutoLoadOnBottom;
    }
    
    /**
     * set whether auto load when on bottom
     * 
     * @param isAutoLoadOnBottom
     */
    public void setAutoLoadOnBottom(boolean isAutoLoadOnBottom) {
        this.isAutoLoadOnBottom = isAutoLoadOnBottom;
    }

    /**
     * get whether show footer loading progress bar when loading
     * 
     * @return the isShowFooterProgressBar
     */
    public boolean isShowFooterProgressBar() {
        return isShowFooterProgressBar;
    }

    /**
     * set whether show footer loading progress bar when loading
     * 
     * @param isShowFooterProgressBar
     */
    public void setShowFooterProgressBar(boolean isShowFooterProgressBar) {
        this.isShowFooterProgressBar = isShowFooterProgressBar;
    }

    /**
     * get isShowFooterWhenNoMore
     * 
     * @return the isShowFooterWhenNoMore
     */
    public boolean isShowFooterWhenNoMore() {
        return isShowFooterWhenNoMore;
    }

    /**
     * 设置当加载更多没有数据时是否显示更多按钮，默认为true
     * <br>当加载更多没有数据时需要手动调用{@link RefreshListView#setNoMore()}, 这样才能显示
     * 没有更多的底部提示}
     * <br><b>注意：如果设置为false，当再次下拉刷新时，更多按钮就不能再显示出来</b>
     * <br>
     * @param isShowFooterWhenNoMore the isShowFooterWhenNoMore to set
     */
    public void setShowFooterWhenNoMore(boolean isShowFooterWhenNoMore) {
        this.isShowFooterWhenNoMore = isShowFooterWhenNoMore;
    }
    
    /**
     * @param onBottomListener
     */
    public void setOnBottomListener(OnBottomListener onBottomListener) {
    	this.mOnBottomListener = onBottomListener;
    }
    
    @Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		 // if isOnBottomStyle and isAutoLoadOnBottom and hasMore, then call onBottom function auto
//		System.out.println(" --totalItemCount-"+totalItemCount +" visibleItemCount "+ visibleItemCount);
		checkScrollDirection(view, firstVisibleItem, visibleItemCount, totalItemCount);
        if (isOnBottomStyle && hasMore && totalItemCount > visibleItemCount) {
        	mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
        }else{
        	mLastItemVisible = false;
        }
	}
    
    @Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
    	/**
		 * Check that the scrolling has stopped, and that the last item is
		 * visible.
		 */
    	//判断超出屏幕外
    	if (footerLayout == null) {
    		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && mLastItemVisible){
    			if (mIsShowBottomOutOfScreen && getFooterViewsCount() == 0) {
    				mIsOutScreen = true;
    				initOnBottomStyle();
    			}else{
    				mIsOutScreen = false;
    			}
    		}
		}else{
			//当非自动加载更多
			if (!isAutoLoadOnBottom) {
				if(!hasMore){
					onBottomComplete();
				}
				return;
			}
			
			//正在加载更多的时候返回
			if(isOnBottomLoading){
				return;
			}else if(!hasMore){
				onBottomComplete();
			}
			
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && mLastItemVisible && mState == DONE) {
				if (hasMore) {
					isOnBottomLoading = false;
					onBottomComplete();
					if (isOnBottomStyle && !isOnBottomLoading) {
						isOnBottomLoading = true;
						if (isAutoLoadOnBottom && footerBotton != null) {
							onBottomBegin();
							footerBotton.performClick();
						}
					}
				}else{
					onBottomComplete();
				}
			}
		}
	}
    	

	/**
     * on bottom begin, adjust view status
     */
    private void onBottomBegin() {
        if (isOnBottomStyle && footerLayout != null) {
        	mState = DONE;
            if (isShowFooterProgressBar) {
                footerProgressBar.setVisibility(View.VISIBLE);
                footerProgressBar.clearAnimation();
                footerProgressBar.startAnimation(mLoadingAnimation);
            }
            footerText.setText(footerLoadingText);
            footerBotton.setEnabled(false);
        }
    }

    /**
     * on bottom loading, you should call it by manual
     */
    private void onBottom() {
        if (isOnBottomStyle && !isOnBottomLoading) {
            isOnBottomLoading = true;
            onBottomBegin();
        }
    }

    /**
     * on bottom load complete, restore view status
     */
    private void onBottomComplete() {
        if (isOnBottomStyle && footerLayout != null) {
            if (isShowFooterProgressBar) {
            	footerProgressBar.clearAnimation();
                footerProgressBar.setVisibility(View.GONE);
            }
            if (!hasMore) {
                footerText.setText(footerNoMoreText);
                footerBotton.setEnabled(false);
                if (!isShowFooterWhenNoMore) {
                    removeFooterView(footerLayout);
                }
            } else {
                footerText.setText(footerDefaultText);
                footerBotton.setEnabled(true);
            }
            isOnBottomLoading = false;
        }
    }
    
    /**
     * set whether has more. if hasMore is false, onBottm will not be called when listView scroll to bottom
     * 
     * @param hasMore
     */
    public void setNoMore() {
        this.hasMore = false;
        //bug:解决，需要下拉两次才能显示没有更多
        onBottomComplete();
    }

    /**
     * get whether has more
     * 
     * @return
     */
    public boolean isHasMore() {
        return hasMore;
    }
    
    /**
     * get footer layout view
     * 
     * @return
     */
    public RelativeLayout getFooterLayout() {
        return footerLayout;
    }
    
    /**
     * set footer default text, default is R.string.drop_down_list_footer_default_text
     * 
     * @param footerDefaultText
     */
    public void setFooterDefaultText(String footerDefaultText) {
        this.footerDefaultText = footerDefaultText;
        if (footerText != null) {
            footerText.setText(footerDefaultText);
        }
    }

	/**----------------------------------底部加载end-----------------------------------**/

	public void setHeaderPullText(String headerPullText) {
		this.headerPullText = headerPullText;
	}

	public void setHeaderReleaseText(String headerReleaseText) {
		this.headerReleaseText = headerReleaseText;
	}

	public void setHeaderLoadingText(String headerLoadingText) {
		this.headerLoadingText = headerLoadingText;
	}

	public void setFooterLoadingText(String footerLoadingText) {
		this.footerLoadingText = footerLoadingText;
	}

	/**
	 * 当没有加载更多时，没有数据时显示的文字
	 * <p>Title: setFooterNoMoreText
	 * <p>Description: 
	 * @param footerNoMoreText
	 */
	public void setFooterNoMoreText(String footerNoMoreText) {
		this.footerNoMoreText = footerNoMoreText;
	}

	public void setHeaderSecondText(String headerSecondText) {
		this.headerSecondText = headerSecondText;
		mHtvTime.setText(headerSecondText);
	}
	
	/**
	 * 设置是否当数据填充满屏幕才显示更多按钮（false：未填充满也显示，true:填充满才显示更多按钮）
	 * <p>Title: setShowBottomOutOfScreen
	 * <p>Description:
	 */
	public void setBottomShowOutOfScreen(boolean isShow){
		this.mIsShowBottomOutOfScreen = isShow;
		if (!isShow && getFooterViewsCount() == 0) {
			initOnBottomStyle();
		}else if(isShow && !mIsOutScreen && footerLayout != null){
			removeFooterView(footerLayout);
			footerLayout = null;
		}
		
	}

	/**
	 * 滑动方向监听器
	 * <p>Title: setScrollDirectionListener
	 * <p>Description: 
	 * @param scrollDirectionListener
	 */
	public void setOnScrollDirectionListener(
			ScrollDirectionListener scrollDirectionListener) {
		this.scrollDirectionListener = scrollDirectionListener;
	}
	
	/**
	 * 检查滑动方向
	 * <p>Title: checkScrollDirection
	 * <p>Description: 
	 * @param view
	 * @param firstVisibleItem
	 * @param visibleItemCount
	 * @param totalItemCount
	 */
	private void checkScrollDirection(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(totalItemCount == 0) return;
        if (isSameRow(firstVisibleItem)) {
            int newScrollY = getTopItemScrollY(view);
            boolean isSignificantDelta = Math.abs(mLastScrollY - newScrollY) > mScrollThreshold;
            if (isSignificantDelta) {
            	if (scrollDirectionListener != null) {
            		if (mLastScrollY > newScrollY) {
            			scrollDirectionListener.onScrollUp();
            		} else {
            			scrollDirectionListener.onScrollDown();
            		}
				}
            }
            mLastScrollY = newScrollY;
        } else {
        	if (scrollDirectionListener != null) {
        		if (firstVisibleItem > mPreviousFirstVisibleItem) {
        			scrollDirectionListener.onScrollUp();
        		} else {
        			scrollDirectionListener.onScrollDown();
        		}
        	}

            mLastScrollY = getTopItemScrollY(view);
            mPreviousFirstVisibleItem = firstVisibleItem;
        }
    }
	
	 private boolean isSameRow(int firstVisibleItem) {
	        return firstVisibleItem == mPreviousFirstVisibleItem;
	    }

	  private int getTopItemScrollY(AbsListView mListView) {
		  if (mListView == null || mListView.getChildAt(0) == null) return 0;
	        View topChild = mListView.getChildAt(0);
	        return topChild.getTop();
	   }
}
