package com.hash.lib.ui.pulltorefresh;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hash.lib.ui.R;
import com.hash.lib.ui.recyclerview.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 包含头部和尾部的下拉上拉控件
 *
 * @author Administrator
 */
public class SuperSwipyRefreshLayout extends SwipyRefreshLayout implements OnScrollListener {

    /**
     * 页脚的容器
     */
    private LinearLayout footView;

    /**
     * 页头的容器
     */
    private LinearLayout headView;

    /**
     * 页头中常驻view
     */
    private LinearLayout headAlways;

    /**
     * 页头可变View
     */
    private LinearLayout headChange;

    /**
     * 底部view容器
     */
    private LinearLayout pullDownFootView;

    private View footer;

    private ListView mListView;

    private WrapRecyclerView mRecyclerView;

    private ProgressBar refProgressBar;

    private TextView refTextView;

    /**
     * 忽略事件的视图
     */
    private List<View> mIgnoredViews = new ArrayList<View>();

    /**
     * 列表是否滚动到底部
     */
    private boolean isEnd = false;

    /**
     * 是否自动加载
     */
    private boolean autoLoading = true;

    /**
     * 加载底部view时是否显示
     */
    private boolean isShowBottomMoreView = true;

    public SuperSwipyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperSwipyRefreshLayout(Context context) {
        super(context);
    }

    /******************************************************
     * V5.9.6版本修改
     ****************************************/

    /**
     * 获取其子控件中的ListView或者是WrapRecyclerView
     * @return
     */
    private View getListViewOrWrapRecyclerView(){
        for (int i=0;i<getChildCount();i++){
            View childView=getChildAt(i);
            if (childView instanceof ListView||childView instanceof WrapRecyclerView){
                return childView;
            }
        }
        return null;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            footView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.ui_library_pulldownview_header_layout, null);
            headView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.ui_library_pulldownview_header_layout, null);
            headAlways = (LinearLayout) headView.findViewById(R.id.pulldownview_header_childone);
            headChange = (LinearLayout) headView.findViewById(R.id.pulldownview_header_childtwo);
            setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
            View view = getListViewOrWrapRecyclerView();
            if (view instanceof ListView) {
                mListView = (ListView) view;
                mListView.addFooterView(footView, null, false);
                mListView.addHeaderView(headView, null, false);
                mListView.setOnScrollListener(this);
                //添加一个加载更多的布局
                pullDownFootView = (LinearLayout) footView.findViewById(R.id.pulldownview_header_childzero);
                footer = LayoutInflater.from(getContext()).inflate(R.layout.ui_library_refresh_foot, null);
                refProgressBar = (ProgressBar) footer.findViewById(R.id.refbar);
                refTextView = (TextView) footer.findViewById(R.id.ref);
                footer.setVisibility(View.GONE);
                pullDownFootView.addView(footer);
            } else if (view instanceof WrapRecyclerView) {
                footView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.ui_library_pulldownview_header_layout, this, false);
                headView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.ui_library_pulldownview_header_layout, this, false);
                headAlways = (LinearLayout) headView.findViewById(R.id.pulldownview_header_childone);
                headChange = (LinearLayout) headView.findViewById(R.id.pulldownview_header_childtwo);
                setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

                mRecyclerView = (WrapRecyclerView) view;
                mRecyclerView.removeAllViews();
                mRecyclerView.addHeaderView(headView);
                mRecyclerView.addFooterView(footView);
                //添加一个加载更多的布局
                pullDownFootView = (LinearLayout) footView.findViewById(R.id.pulldownview_header_childzero);
                footer = LayoutInflater.from(getContext()).inflate(R.layout.ui_library_refresh_foot, this, false);
                refProgressBar = (ProgressBar) footer.findViewById(R.id.refbar);
                refTextView = (TextView) footer.findViewById(R.id.ref);
                footer.setVisibility(View.GONE);
                pullDownFootView.addView(footer);
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        switch (newState) {
                            case OnScrollListener.SCROLL_STATE_FLING:           // 正在滑动
                            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:    // 手指触摸滚动
                                break;
                            case OnScrollListener.SCROLL_STATE_IDLE:// 滚动停止了
                                if (isEnd && isHaveMore() && autoLoading) {
                                    if (footer != null && footer.getVisibility() == View.GONE) {
                                        footer.setVisibility(View.VISIBLE);
                                        footer.setEnabled(false);
                                    }
                                    if (mListener != null) {
                                        mListener.onLoadMore();
                                    }
                                } else {
                                    updateFootView();
                                }
                                break;
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int topRowVerticalPosition =
                                (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                        setEnabled(topRowVerticalPosition >= 0);

                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        firstVisibleItem = ((LinearLayoutManager) layoutManager)
                                .findFirstVisibleItemPosition();
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        if (firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
                            if (isAutoLoading()) {
                                isEnd = true;
                            } else {
                                if (mRecyclerView != null) {
                                    View lastItemView = mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1);
                                    if (lastItemView != null && (mRecyclerView.getBottom()) == lastItemView.getBottom()) {
                                        isEnd = true;
                                    } else {
                                        isEnd = false;
                                    }
                                }
                            }
                        } else {
                            isEnd = false;
                        }
                    }
                });
            }
        }
    }

    /**
     * 在添加这个View时,是否需要显示加载更多
     *
     * @param view
     * @param isShowBottomMoreView
     */
    public void addFootView(View view, boolean isShowBottomMoreView) {
        if (pullDownFootView != null) {
            this.isShowBottomMoreView = isShowBottomMoreView;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            pullDownFootView.addView(view, params);
        }
    }

    /**
     * 将底部的footView置空
     */
    public void setFootViewEmpty() {
        footView.removeAllViews();
    }

    /**
     * 删除所有headAlways额外的View
     */
    public void setAdditionalEmpty() {
        headAlways.removeAllViews();
    }

    /**
     * 添加自定义的view到header，这部分相当于额外的view
     *
     * @param header
     */
    public void setAdditionalView(View header) {
        setAdditionalView(header, null);
    }

    public void setAdditionalView(View header, ViewGroup.LayoutParams params) {
        headAlways.removeAllViews();
        if (params == null) {
            headAlways.addView(header);
        } else {
            headAlways.addView(header, params);
        }
    }

    public void setAdditionalView(View header, int index, boolean isIgnoredView) {
        int childCount = headAlways.getChildCount();
        if (childCount > 2) {
            headAlways.removeAllViews();
        }
        if (index > childCount - 1) {
            headAlways.addView(header, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            headAlways.addView(header, index, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        if (isIgnoredView) {
            mIgnoredViews.clear();
            mIgnoredViews.add(header);
        }
    }

    /**
     * 添加自定义的view到header，这部分相当于额外的view
     *
     * @param header
     */
    public void setAdditionalView(View header, boolean isIgnoredView) {
        setAdditionalView(header);
        if (isIgnoredView) {
            mIgnoredViews.clear();
            mIgnoredViews.add(header);
        }
    }

    /**
     * 添加自定义的view到header，这部分相当于额外的LayoutParams
     *
     * @param header
     */
    public void setAdditionalViewWithLp(View header, ViewGroup.LayoutParams lp) {
        headAlways.removeAllViews();
        headAlways.addView(header, lp);
    }

    /**
     * 添加自定义的header
     *
     * @param header
     */
    public void setHeaderView(View header) {
        if (headView.getChildCount() < 2) {
            throw new IllegalStateException("headView childview count must have at less two child");
        }
        headChange.removeAllViews();
        headChange.addView(header, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 设置空布局
     * @param header
     */
    public void setEmptyView(View header){
        try{
            ViewGroup.LayoutParams params= headView.getLayoutParams();
            if (params!=null){
                params.height=ViewGroup.LayoutParams.MATCH_PARENT;
                params.width=ViewGroup.LayoutParams.MATCH_PARENT;
                header.setLayoutParams(params);
            }
            super.setEmptyView(header);
            setHeaderView(header);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将可变View置为空
     */
    public void setHeaderViewEmpty() {
        try{
            ViewGroup.LayoutParams params= headView.getLayoutParams();
            if (params!=null){
                params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
                params.width=ViewGroup.LayoutParams.MATCH_PARENT;
                headView.setLayoutParams(params);
            }
            super.setHeaderViewEmpty();
            if (headChange != null) {
                headChange.removeAllViews();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 是否在指定View上面
     *
     * @param ev
     * @return
     */
    private boolean isInIgnoredView(MotionEvent ev) {
        try {
            Rect rect = new Rect();
            for (View v : mIgnoredViews) {
                if (v != null) {
                    v.getHitRect(rect);
                    int x = (int) ev.getX();
                    int y = (int) ev.getY();
                    if (rect.contains(x, y)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (isInIgnoredView(ev)) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    public boolean isShowBottomMoreView() {
        return isShowBottomMoreView;
    }

    public void setShowBottomMoreView(boolean showBottomMoreView) {
        isShowBottomMoreView = showBottomMoreView;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public boolean isAutoLoading() {
        return autoLoading;
    }

    public void setAutoLoading(boolean autoLoading) {
        this.autoLoading = autoLoading;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case OnScrollListener.SCROLL_STATE_FLING:// 正在滑动
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 手指触摸滚动
                break;
            case OnScrollListener.SCROLL_STATE_IDLE:// 滚动停止了
                if (isEnd && isHaveMore() && autoLoading) {
                    if (footer != null && footer.getVisibility() == View.GONE) {
                        footer.setVisibility(View.VISIBLE);
                        footer.setEnabled(false);
                    }
                    if (mListener != null) {
                        mListener.onLoadMore();
                    }
                } else {
                    updateFootView();
                }
                break;
        }
    }

    private void updateFootView() {
        //到达底部,且有下一页数据,并且不是自动加载
        if (isEnd && isHaveMore() && !isAutoLoading() && isShowBottomMoreView) {
            footer.setVisibility(VISIBLE);
            footer.setEnabled(true);
            refProgressBar.setVisibility(GONE);
            refTextView.setText(R.string.ui_library_load_more);
            refTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ui_library_icon_bgz_jt, 0);
            footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refProgressBar.setVisibility(View.VISIBLE);
                    refTextView.setText(R.string.ui_library_update_content);
                    refTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    if (mListener != null) {
                        mListener.onLoadMore();
                    }
                }
            });
        } else {
            if (footer != null && footer.getVisibility() == View.VISIBLE) {
                footer.setVisibility(View.GONE);
                footer.setEnabled(false);
            }
        }
    }

    private int firstVisibleItem = 0;

    /**
     * firstVisibleItem 第一个可见单元格的索引
     * visibleItemCount 可见单元格数
     * totalItemCount   列表适配器当中的条数
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        if (firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
            if (isAutoLoading()) {
                isEnd = true;
            } else {
                if (mListView != null) {
                    View lastItemView = mListView.getChildAt(mListView.getChildCount() - 1);
                    if (lastItemView != null && (mListView.getBottom()) == lastItemView.getBottom()) {
                        isEnd = true;
                    } else {
                        isEnd = false;
                    }
                }
            }
        } else {
            isEnd = false;
        }

        if (mScroller != null) {
            if (firstVisibleItem == 0) {
                mScroller.isScrollToTop(true);
            } else {
                mScroller.isScrollToTop(false);
            }
        }
    }

    private onScrollListener mScroller;

    public void setOnScrollListener(onScrollListener mScroller) {
        this.mScroller = mScroller;
    }

    public interface onScrollListener {
        /**
         * 是否滚动到顶部了
         *
         * @param isTop
         */
        void isScrollToTop(boolean isTop);

        /**
         * 是向上滚还是向下滚
         *
         * @param isUp
         */
        void isUp(boolean isUp);

    }

    public int getFirstVisibleItem() {
        return firstVisibleItem;
    }

    public void setFirstVisibleItem(int firstVisibleItem) {
        this.firstVisibleItem = firstVisibleItem;
    }
}
