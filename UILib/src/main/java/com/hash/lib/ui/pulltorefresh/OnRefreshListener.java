package com.hash.lib.ui.pulltorefresh;

/**
 * 下拉刷新接口
 * Created by 张中伟 on 2016/5/16.
 */
public interface OnRefreshListener {

    /**
     * 下拉或上拉刷新
     */
    void onRefresh();

    /**
     * 自动加载更多(相当于上拉刷新)
     */
    void onLoadMore();

}
