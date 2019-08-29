package com.hash.lib.ui.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.hash.lib.ui.recyclerview.adapter.BaseQuickAdapter;
import com.hash.lib.ui.recyclerview.loadmore.SimpleLoadMoreView;
import com.hash.lib.ui.utils.NetUtil;

import java.util.ArrayList;

public class SuperSwipyRefreshLayout2 extends SwipyRefreshLayout implements BaseQuickAdapter.RequestLoadMoreListener {

    private ArrayList<View> mHeaderViewList;

    private ArrayList<View> mFooterViewlist;

    /**
     * 当前刷新控件绑定的 BaseQuickAdapter
     */
    private BaseQuickAdapter mQuickAdapter;

    private RecyclerView mRecyclerView;

    public SuperSwipyRefreshLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperSwipyRefreshLayout2(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mRecyclerView = getRecyclerView();
    }

    /**
     * 获取其子控件中的ListView或者是WrapRecyclerView
     *
     * @return
     */
    private RecyclerView getRecyclerView() {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView instanceof RecyclerView) {
                return (RecyclerView) childView;
            }
        }
        return null;
    }

    /**
     * 返回头部view集合
     *
     * @return
     */
    public ArrayList<View> getHeaderViewList() {
        if (mHeaderViewList == null) {
            mHeaderViewList = new ArrayList<>();
        }
        return mHeaderViewList;
    }

    /**
     * 返回尾部集合
     *
     * @return
     */
    public ArrayList<View> getFooterViewList() {
        if (mFooterViewlist == null) {
            mFooterViewlist = new ArrayList<>();
        }
        return mFooterViewlist;
    }

    /**
     * 添加一个头部布局
     *
     * @param headerView
     */
    public void addHeaderView(View headerView) {
        if (headerView != null) {
            getHeaderViewList().add(headerView);
        }
    }

    /**
     * 添加一个尾部布局
     *
     * @param footerView
     */
    public void addFooterView(View footerView) {
        if (footerView != null) {
            getFooterViewList().add(footerView);
        }
    }

    /**
     * 设置异常或空布局
     */
    public void setEmptyView(View emptyView) {
        super.setEmptyView(emptyView);
    }

    @Override
    public void setHeaderViewEmpty() {
        super.setHeaderViewEmpty();
        if (getEmptyView() != null) {
            setEmptyView(null);
        }
    }

    /**
     * 页面加载完毕并且设置setAdapter后这里才有值
     * 无法供外部调用
     *
     * @return
     */
    private BaseQuickAdapter getQuickAdapter() {
        if (mQuickAdapter == null) {
            if (mRecyclerView != null) {
                RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
                if (adapter instanceof BaseQuickAdapter) {
                    mQuickAdapter = (BaseQuickAdapter) adapter;
                }
            }
        }
        return mQuickAdapter;
    }

    /**
     * 加载下一页操作
     */
    @Override
    public void onLoadMoreRequested() {
        mQuickAdapter = getQuickAdapter();
        if (mQuickAdapter != null) {
            if (isHaveMore()) {
                //还有下一页数据显示正在加载中
                if (mListener != null) {
                    mListener.onLoadMore();
                }
            } else {
                if (NetUtil.isNetworkAvailable()) {
                    mQuickAdapter.loadMoreEnd(true);
                } else {
                    mQuickAdapter.loadMoreFail();
                }
            }
        }
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        if (!refreshing) {
            mQuickAdapter = getQuickAdapter();
            if (mQuickAdapter != null) {
                mQuickAdapter.loadMoreComplete();
            }
        }
    }

    @Override
    public void setHasMore(boolean hasMore) {
        super.setHasMore(hasMore);
        mQuickAdapter = getQuickAdapter();
        if (mQuickAdapter != null) {
            mQuickAdapter.setLoadMoreView(new SimpleLoadMoreView());
        }
    }

}
