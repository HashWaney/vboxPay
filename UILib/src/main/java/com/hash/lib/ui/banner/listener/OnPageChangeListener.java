package com.hash.lib.ui.banner.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by HashWaney on 2019/8/29.
 */

public interface OnPageChangeListener {
    void onScrollStateChanged(RecyclerView recyclerView, int newState);

    void onScrolled(RecyclerView recyclerView, int dx, int dy);

    void onPageSelected(int index);


}
