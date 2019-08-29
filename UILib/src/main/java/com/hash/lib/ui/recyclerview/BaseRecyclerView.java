package com.hash.lib.ui.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

import com.hash.lib.ui.pulltorefresh.SuperSwipyRefreshLayout2;
import com.hash.lib.ui.recyclerview.adapter.BaseQuickAdapter;


/**
 * 创建一个RecyclerView基类
 * Created by 张中伟 on 2017/4/3.
 */
public class BaseRecyclerView extends RecyclerView {


    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        handleRecyclerViewHeaderOrFooter(adapter);
        setAutoLoadView(adapter);
        super.setAdapter(adapter);
    }

    /**
     * 设置自动加载的View
     */
    private void setAutoLoadView(Adapter adapter) {
        if (adapter instanceof BaseQuickAdapter) {
            BaseQuickAdapter quickAdapter = (BaseQuickAdapter) adapter;
            ViewParent viewParent = getParent();
            if (viewParent instanceof BaseQuickAdapter.RequestLoadMoreListener) {
                quickAdapter.setOnLoadMoreListener((BaseQuickAdapter.RequestLoadMoreListener) viewParent, this);
            }
        }
    }

    /**
     * 添加HeaderView或者是FooerView
     */
    private void handleRecyclerViewHeaderOrFooter(Adapter adapter) {
        if (adapter instanceof BaseQuickAdapter) {
            BaseQuickAdapter quickAdapter = (BaseQuickAdapter) adapter;
            //获取RecyclerView的父类
            ViewParent viewParent = getParent();
            if (viewParent instanceof SuperSwipyRefreshLayout2) {
                SuperSwipyRefreshLayout2 refreshLayout = (SuperSwipyRefreshLayout2) viewParent;

                //添加头部
                if (!refreshLayout.getHeaderViewList().isEmpty()) {
                    quickAdapter.removeAllHeaderView();
                    for (View header : refreshLayout.getHeaderViewList()) {
                        quickAdapter.addHeaderView(header);
                    }
                }

                //添加尾部
                if (!refreshLayout.getFooterViewList().isEmpty()) {
                    quickAdapter.removeAllFooterView();
                    for (View footer : refreshLayout.getFooterViewList()) {
                        quickAdapter.addFooterView(footer);
                    }
                }

                //添加异常和空布局
                if (refreshLayout.getEmptyView() != null) {
                    quickAdapter.setEmptyView(refreshLayout.getEmptyView());
                }
            }
        }
    }
}
