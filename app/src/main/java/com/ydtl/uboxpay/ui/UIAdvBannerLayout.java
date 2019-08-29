package com.ydtl.uboxpay.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.hash.lib.ui.banner.BaseIndicatorBanner;
import com.hash.lib.ui.glide.GlideUtil;
import com.hash.lib.ui.glide.GlideView;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.bean.GoodsBannerInfo;
import com.ydtl.uboxpay.tool.AndroidUtil;

/**
 * Created by HashWaney on 2019/8/28.
 */

public class UIAdvBannerLayout extends BaseIndicatorBanner<Object, UIAdvBannerLayout> {

    public UIAdvBannerLayout(Context context) {
        this(context, null);
    }

    public UIAdvBannerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIAdvBannerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTitleShow(false);

    }

    @Override
    public View onCreateItemView(int position) {
        View bannerView = View.inflate(getContext(), R.layout.layout_adv_banner_item, null);
        GlideView ivBanner = bannerView.findViewById(R.id.iv_adv);
        GoodsBannerInfo bannerInfo = (GoodsBannerInfo) getDefaultList().get(position);
        GlideUtil.sharedInstance().display(getContext(), ivBanner, AndroidUtil.isEmpty(bannerInfo.getPicPath()) ? "" : bannerInfo.getPicPath(), R.drawable.shape_confirm_bg);
        return bannerView;
    }
}
