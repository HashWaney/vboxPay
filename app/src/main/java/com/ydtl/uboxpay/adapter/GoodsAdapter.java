package com.ydtl.uboxpay.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.hash.lib.ui.anim.ZoomInEnter;
import com.hash.lib.ui.glide.GlideUtil;
import com.hash.lib.ui.recyclerview.BaseViewHolder;
import com.hash.lib.ui.recyclerview.UIBaseLayout;
import com.hash.lib.ui.recyclerview.UIGridLayout;
import com.hash.lib.ui.recyclerview.UINormalLayout;
import com.hash.lib.ui.recyclerview.adapter.BaseMultiItemQuickAdapter;
import com.hash.lib.ui.recyclerview.adapter.BaseQuickAdapter;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.bean.GoodsDetailModel;
import com.ydtl.uboxpay.bean.GoodsInfo;
import com.ydtl.uboxpay.bean.ProductBean;
import com.ydtl.uboxpay.bean.ProductInfo;
import com.ydtl.uboxpay.tool.AndroidUtil;
import com.ydtl.uboxpay.ui.UIAdvBannerLayout;

import java.util.ArrayList;

/**
 * Created by HashWaney on 2019/8/28.
 */

public class GoodsAdapter extends BaseQuickAdapter<ProductInfo, BaseViewHolder> {

    public GoodsAdapter(ArrayList<ProductInfo> data) {
        super(R.layout.layout_goods_item, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, ProductInfo bean) {
        TextView tvName = helper.getView(R.id.tvName);
        TextView tvPrice = helper.getView(R.id.tvPrice);
        ImageView ivPic = helper.getView(R.id.ivPic);
        tvName.setText(AndroidUtil.isEmpty(bean.getProductName()) ? "" : bean.getProductName());
        String productPrice = AndroidUtil.getAmountString( Integer.parseInt(bean.getProductPrice()));
        tvPrice.setText(AndroidUtil.isEmpty(bean.getProductPrice()) ? "" : "￥" + productPrice);
        GlideUtil.sharedInstance().display(helper.getContext(), ivPic, AndroidUtil.isEmpty(bean.getProductPic()) ? "" : bean.getProductPic(),
                R.drawable.shop_default_bg, 70, 70);

    }


}
