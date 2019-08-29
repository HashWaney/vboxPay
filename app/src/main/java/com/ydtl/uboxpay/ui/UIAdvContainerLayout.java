package com.ydtl.uboxpay.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.hash.lib.ui.anim.ZoomInEnter;
import com.ydtl.uboxpay.R;

import java.util.ArrayList;

/**
 * Created by HashWaney on 2019/8/28.
 */

public class UIAdvContainerLayout extends LinearLayout {
    private LinearLayout bannerContainer;
    private UIAdvBannerLayout bannerLayout;

    public UIAdvContainerLayout(Context context) {
        super(context);
        View view = View.inflate(context, R.layout.layout_goods_adv_header, null);
        bannerContainer = view.findViewById(R.id.llGlleryLayout);
        bannerLayout = view.findViewById(R.id.banner);
//        ButterKnife.bind(this, view);
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.layout_goods_adv_header;
//    }


    public void setData(ArrayList<Object> mData) {
        if (mData != null && mData.size() > 0) {
            bannerLayout.setSelectAnimClass(ZoomInEnter.class)
                    .setDelay(15)
                    .setPeriod(10)
                    .setSource(mData)
                    .setTransformerClass(null)
                    .startScroll();

        }
    }

}
