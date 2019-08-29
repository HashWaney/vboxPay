package com.hash.lib.ui.banner.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hash.lib.ui.banner.utils.ScreenUtil;

/**
 * Created by HashWaney on 2019/8/29.
 */

public class BannerPageAdapterHelper {
    private static int sPagePadding = 0;
    public static int sShowLeftCardWidth = 0;

    public void onCreateViewHolder(ViewGroup parent, View itemView) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        lp.width = parent.getWidth() - ScreenUtil.dip2px(itemView.getContext(), 2 * (sPagePadding + sShowLeftCardWidth));
        itemView.setLayoutParams(lp);
    }

    public void onBindViewHolder(View itemView, final int position, int itemCount) {
        int padding = ScreenUtil.dip2px(itemView.getContext(), sPagePadding);
        itemView.setPadding(padding, 0, padding, 0);
        int leftMargin = position == 0 ? padding + ScreenUtil.dip2px(itemView.getContext(), sShowLeftCardWidth)
                : 0;
        int rightMargin = position == itemCount - 1 ?
                padding + ScreenUtil.dip2px(itemView.getContext(), sShowLeftCardWidth) : 0;
        setViewMargin(itemView, leftMargin, 0, rightMargin, 0);

    }

    private void setViewMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp.leftMargin != left
                || lp.topMargin != top
                || lp.rightMargin != right
                || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);
        }
    }

    public void setPagePadding(int pagePadding) {
        sPagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        sShowLeftCardWidth = showLeftCardWidth;
    }
}
