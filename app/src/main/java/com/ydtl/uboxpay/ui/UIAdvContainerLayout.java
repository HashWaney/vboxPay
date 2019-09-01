package com.ydtl.uboxpay.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hash.lib.ui.anim.ZoomInEnter;
import com.ydtl.uboxpay.R;

import java.util.ArrayList;

/**
 * Created by HashWaney on 2019/8/28.
 */

public class UIAdvContainerLayout extends LinearLayout {
    private UIAdvBannerLayout bannerLayout;

    private Context context;

    public UIAdvContainerLayout(Context context) {
        this(context, null);
        this.context = context;
    }

    public UIAdvContainerLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIAdvContainerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_goods_adv_header, null);
//        View view = View.inflate(context, R.layout.layout_goods_adv_header, null);
        bannerLayout = view.findViewById(R.id.banner);
        // TODO: 2019/8/30 添加到控件中
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

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
