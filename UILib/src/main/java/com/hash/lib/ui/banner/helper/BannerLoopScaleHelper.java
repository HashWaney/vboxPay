package com.hash.lib.ui.banner.helper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.hash.lib.ui.banner.adapter.BannerPageAdapter;
import com.hash.lib.ui.banner.listener.OnPageChangeListener;
import com.hash.lib.ui.banner.widget.BannerLooperViewPage;

/**
 * Created by HashWaney on 2019/8/29.
 */

public class BannerLoopScaleHelper {
    private BannerLooperViewPage mLooperRecycleView;
    private int mPagePadding = 0;//卡片的padding 卡片间的距离等于2倍的mPagePadding
    private int mShowLeftCardWidth = 0;//左边卡片显示大小

    private int mFirstItemPos;
    private PagerSnapHelper mPageSnapHelper = new PagerSnapHelper();
    private OnPageChangeListener onPageChangeListener;

    public void attachToRecyclerView(final BannerLooperViewPage mLooperRecycleView) {
        if (mLooperRecycleView == null) {
            return;
        }
        this.mLooperRecycleView = mLooperRecycleView;
        mLooperRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int position = getCurrentItem();
                //change the point for looper
                BannerPageAdapter adapter = (BannerPageAdapter) mLooperRecycleView.getAdapter();
                int count = adapter.getRealItemCount();
                if (adapter.isCanLooper()) {
                    if (position < count) {
                        position = count + position;
                        setCurrentItem(position);
                    } else if (position >= 2 * count) {
                        position = position - count;
                        setCurrentItem(position);
                    }
                }

                if (onPageChangeListener != null) {
                    onPageChangeListener.onScrollStateChanged(mLooperRecycleView, newState);
                    if (count != 0) {
                        onPageChangeListener.onPageSelected(position % count);
                    }

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onPageChangeListener != null) {
                    onPageChangeListener.onScrolled(recyclerView, dx, dy);
                }
                onScrolledChangedCallback();
            }
        });
        initWidth();
        mPageSnapHelper.attachToRecyclerView(mLooperRecycleView);
    }

    private void initWidth() {
        mLooperRecycleView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLooperRecycleView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                scrollToPosition(mFirstItemPos);
            }
        });


    }

    private void onScrolledChangedCallback() {


    }

    public int getCurrentItem() {
        try {
            RecyclerView.LayoutManager layoutManager = mLooperRecycleView.getLayoutManager();
            View view = mPageSnapHelper.findSnapView(layoutManager);
            if (view != null) {
                return layoutManager.getPosition(view);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int getRealCurrentItem() {
        BannerPageAdapter adapter = (BannerPageAdapter) mLooperRecycleView.getAdapter();
        int realItemCount = adapter.getRealItemCount();
        return getCurrentItem() % realItemCount;
    }

    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        if (mLooperRecycleView == null) {
            return;
        }
        if (smoothScroll) {
            mLooperRecycleView.smoothScrollToPosition(item);
        } else {
            scrollToPosition(item);
        }
    }

    public void scrollToPosition(int pos) {
        if (mLooperRecycleView == null) {
            return;
        }
        ((LinearLayoutManager) mLooperRecycleView.getLayoutManager()).
                scrollToPositionWithOffset(pos,
                        (mPagePadding + mShowLeftCardWidth));
        mLooperRecycleView.post(new Runnable() {
            @Override
            public void run() {
                onScrolledChangedCallback();
            }
        });
    }


    public void setFirstItemPos(int firstItemPos) {
        this.mFirstItemPos = firstItemPos;
    }

    public int getFirstItemPos() {
        return mFirstItemPos;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

}
