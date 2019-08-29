package com.hash.lib.ui.banner.listener;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by HashWaney on 2019/8/29.
 */

public class BannerPageChangeListener implements OnPageChangeListener {

    private ArrayList<ImageView> indicatorView;
    private int[] pageIndicator;
    private OnPageChangeListener onPageChangeListener;

    public BannerPageChangeListener(ArrayList<ImageView> indicatorView, int[] pageIndicator) {
        this.indicatorView = indicatorView;
        this.pageIndicator = pageIndicator;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (onPageChangeListener != null) {
            onPageChangeListener.onScrollStateChanged(recyclerView, newState);
        }

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (onPageChangeListener != null) {
            onPageChangeListener.onScrolled(recyclerView, dx, dy);
        }
    }

    @Override
    public void onPageSelected(int index) {
        for (int i = 0; i < indicatorView.size(); i++) {
            indicatorView.get(index).setImageResource(pageIndicator[1]);
            if (index != i) {
                indicatorView.get(i).setImageResource(pageIndicator[0]);
            }
        }
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(index);
        }

    }


    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }
}
