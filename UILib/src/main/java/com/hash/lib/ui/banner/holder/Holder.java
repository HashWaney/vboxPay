package com.hash.lib.ui.banner.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by HashWaney on 2019/8/29.
 */

public abstract class Holder<T> extends RecyclerView.ViewHolder {

    public Holder(View itemView) {
        super(itemView);
        initView(itemView);
    }
    protected abstract void initView(View itemView);

    public abstract void updateItem(T data);
}
