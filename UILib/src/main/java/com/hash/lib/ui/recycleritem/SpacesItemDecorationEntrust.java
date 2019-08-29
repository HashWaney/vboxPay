package com.hash.lib.ui.recycleritem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：请叫我百米冲刺 on 2016/12/6 上午11:37
 * 邮箱：mail@hezhilin.cc
 */

public abstract class SpacesItemDecorationEntrust {

    protected Drawable mDivider;

    protected int leftRight;

    protected int topBottom;

    public SpacesItemDecorationEntrust(Context context, int leftRight, int topBottom, int drawableId) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
        if (drawableId != 0) {
            mDivider = ContextCompat.getDrawable(context, drawableId);
        }
    }

    abstract void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state);

    abstract void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state);
}
