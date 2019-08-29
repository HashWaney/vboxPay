package com.hash.lib.ui.utils;

import android.content.Context;

public class PixelUtil {
    /**
     * dp转px.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int dip2px(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }

    /**
     * px转dp.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int px2dip(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) ((value * 160) / scale + 0.5f);
    }
}
