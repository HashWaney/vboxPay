package com.hash.lib.ui.recycleritem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * 不规则的网格分割
 */
public class StaggeredGridEntrust extends SpacesItemDecorationEntrust {


    public StaggeredGridEntrust(Context context, int leftRight, int topBottom, int drawableId) {
        super(context,leftRight, topBottom, drawableId);
    }

    @Override
    void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
        final StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        final int childPosition = parent.getChildAdapterPosition(view);
        final int spanCount = layoutManager.getSpanCount();
        final int count = lp.isFullSpan() ? layoutManager.getSpanCount() : 1;

        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {

            if (childPosition + count - 1 < spanCount) {//第一排的需要上面
                outRect.top = topBottom;
            }
            if (lp.getSpanIndex() + count == spanCount) {//最边上的需要右边,这里需要考虑到一个合并项的问题
                outRect.right = leftRight;
            }
            outRect.bottom = topBottom;
            outRect.left = leftRight;

        } else {
            if (childPosition + count - 1 < spanCount) {//第一排的需要left
                outRect.left = leftRight;
            }
            if (lp.getSpanIndex() + count == spanCount) {//最边上的需要bottom
                outRect.bottom = topBottom;
            }
            outRect.right = leftRight;
            outRect.top = topBottom;
        }
    }
}
