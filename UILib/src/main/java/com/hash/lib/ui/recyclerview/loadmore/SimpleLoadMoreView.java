package com.hash.lib.ui.recyclerview.loadmore;


import com.hash.lib.ui.R;

/**
 * Created by BlingBling on 2016/10/11.
 */

public final class SimpleLoadMoreView extends LoadMoreView {

    @Override public int getLayoutId() {
        return R.layout.quick_view_load_more;
    }

    @Override protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }


    //R.id.load_more_load_end_view

    /**
     * R.id.load_more_load_end_view
     * @return
     */
    @Override protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
