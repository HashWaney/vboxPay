package com.hash.lib.ui.banner.holder;

import android.view.View;

/**
 * Created by HashWaney on 2019/8/29.
 */

public interface BannerItemHolderCreator {
    Holder createHolder(View itemView);

    int getLayoutId();
}
