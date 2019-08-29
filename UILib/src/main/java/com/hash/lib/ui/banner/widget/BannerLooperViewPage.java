package com.hash.lib.ui.banner.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by HashWaney on 2019/8/29.
 */

public class BannerLooperViewPage extends RecyclerView {

    private static final int FLING_MAX_VELOCITY = 3000;//最大顺时滑动速度
    private static final boolean mEnableLimitVelocity = true;//最大顺时滑动速度

    public BannerLooperViewPage(Context context) {
        super(context);
    }

    public BannerLooperViewPage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerLooperViewPage(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        if (mEnableLimitVelocity) {
            velocityX = solveVelocity(velocityX);
            velocityY = solveVelocity(velocityY);
        }

        return super.fling(velocityX, velocityY);
    }

    private int solveVelocity(int velocity) {
        if (velocity > 0) {
            return Math.min(velocity, FLING_MAX_VELOCITY);
        } else {
            return Math.max(velocity, -FLING_MAX_VELOCITY);
        }
    }
}
