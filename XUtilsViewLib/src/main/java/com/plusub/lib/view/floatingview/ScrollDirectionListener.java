package com.plusub.lib.view.floatingview;

/**
 * 滑动方向接口
 * @ClassName: ScrollDirectionListener
 * @Description: TODO
 * @author qh@plusub.com
 * @date 2015-4-21 下午8:29:30
 * @version v1.0
 */
public interface ScrollDirectionListener {
	/**
	 * 向下滑动
	 * <p>Title: onScrollDown
	 * <p>Description:
	 */
    void onScrollDown();

    /**
     * 向上滑动
     * <p>Title: onScrollUp
     * <p>Description:
     */
    void onScrollUp();
}