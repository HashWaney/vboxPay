package com.hash.lib.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.hash.lib.ui.recyclerview.adapter.WrapAdapter;

import java.util.ArrayList;
import java.util.List;


public class WrapRecyclerView extends RecyclerView {

    private WrapAdapter mWrapAdapter;
    private boolean shouldAdjustSpanSize;

    // 临时头部View集合,用于存储没有设置Adapter之前添加的头部
    private ArrayList<View> mTmpHeaderView = new ArrayList<>();
    // 临时尾部View集合,用于存储没有设置Adapter之前添加的尾部
    private ArrayList<View> mTmpFooterView = new ArrayList<>();

    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if(adapter instanceof WrapAdapter) {
            mWrapAdapter = (WrapAdapter) adapter;
            super.setAdapter(adapter);
        } else {
            mWrapAdapter = new WrapAdapter(adapter);
            for(View view : mTmpHeaderView) {
                mWrapAdapter.addHeaderView(view);
            }
            if(mTmpHeaderView.size() > 0) {
                mTmpHeaderView.clear();
            }

            for(View view : mTmpFooterView) {
                mWrapAdapter.addFooterView(view);
            }
            if(mTmpFooterView.size() > 0) {
                mTmpFooterView.clear();
            }

            super.setAdapter(mWrapAdapter);
        }

        if(shouldAdjustSpanSize) {
            mWrapAdapter.adjustSpanSize(this);
        }

        getWrappedAdapter().registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
    }


    @Override
    public WrapAdapter getAdapter() {
        return mWrapAdapter;
    }


    public Adapter getWrappedAdapter() {
        if(mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        return mWrapAdapter.getWrappedAdapter();
    }


    public void addHeaderView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the view to add must not be null!");
        } else if(mWrapAdapter == null) {
            mTmpHeaderView.add(view);
        } else {
            mWrapAdapter.addHeaderView(view);
        }
    }


    public void addFooterView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the view to add must not be null!");
        } else if(mWrapAdapter == null) {
            mTmpFooterView.add(view);
        } else {
            mWrapAdapter.addFooterView(view);
        }
    }


    public void addFooterView(View view, boolean reverse) {
        if (null == view) {
            throw new IllegalArgumentException("the view to add must not be null!");
        } else if(mWrapAdapter == null) {
            mTmpFooterView.add(view);
        } else {
            mWrapAdapter.addFooterView(view, reverse);
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout instanceof GridLayoutManager || layout instanceof StaggeredGridLayoutManager) {
            this.shouldAdjustSpanSize = true;
        }
    }


    public List<View> getHeadersView() {
        if(mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        return mWrapAdapter.getHeadersView();
    }


    public List<View> getFootersView() {
        if(mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        return mWrapAdapter.getFootersView();
    }


    public void setFooterVisibility(boolean shouldShow) {
        if(mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        mWrapAdapter.setFooterVisibility(shouldShow);
    }


    public void setHeaderVisibility(boolean shouldShow) {
        if(mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        mWrapAdapter.setHeaderVisibility(shouldShow);
    }


    public int getHeadersCount() {
        if(mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        return mWrapAdapter.getHeadersCount();
    }


    public int getFootersCount() {
        if(mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        return mWrapAdapter.getFootersCount();
    }

    private final AdapterDataObserver mDataObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

}