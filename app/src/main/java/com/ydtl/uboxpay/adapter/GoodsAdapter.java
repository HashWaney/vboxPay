package com.ydtl.uboxpay.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.hash.lib.ui.glide.GlideUtil;
import com.hash.lib.ui.recyclerview.BaseViewHolder;
import com.hash.lib.ui.recyclerview.adapter.BaseMultiItemQuickAdapter;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.bean.GoodsInfo;
import com.ydtl.uboxpay.tool.AndroidUtil;

import java.util.ArrayList;

/**
 * Created by HashWaney on 2019/8/28.
 */

public class GoodsAdapter extends BaseMultiItemQuickAdapter<GoodsInfo,BaseViewHolder> {

    public GoodsAdapter(ArrayList<GoodsInfo> data) {
        super(data);
        addItemType(GoodsInfo.NORMAL, R.layout.layout_goods_item);
    }

//    public GoodsAdapter(List<GoodsInfo> data) {
//        super(R.layout.layout_goods_item, data);
//    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsInfo item) {
        int itemType = item.getItemType();
        if (GoodsInfo.NORMAL == itemType) {
            TextView tvName = helper.getView(R.id.tvName);
            TextView tvPrice = helper.getView(R.id.tvPrice);
            ImageView ivPic = helper.getView(R.id.ivPic);
            tvName.setText(AndroidUtil.isEmpty(item.getGoodsName()) ? "" : item.getGoodsName());
            tvPrice.setText(AndroidUtil.isEmpty(item.getGoodsPrice()) ? "" : "￥" + item.getGoodsPrice());
            GlideUtil.sharedInstance().display(helper.getContext(), ivPic, AndroidUtil.isEmpty(item.getGoodsPic()) ? "" : item.getGoodsPic(),
                    R.drawable.shop_default_bg, 70, 70);
        }

    }


}
