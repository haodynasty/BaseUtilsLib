package com.plusub.lib.view;

/**
 * 包含刷新和加载更多地接口方法
 * @ClassName: KJRefreshListener
 * @Description: TODO
 * @author qh@plusub.com
 * @date 2014-12-1 下午9:35:03
 * @version v1.0
 */
public interface PPRefreshListener {
    /**
     * 下拉刷新回调接口
     */
    public void onRefresh();

    /**
     * 上拉刷新回调接口
     */
    public void onLoadMore();
}