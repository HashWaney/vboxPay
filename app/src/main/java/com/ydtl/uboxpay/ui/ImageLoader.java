package com.ydtl.uboxpay.ui;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hash.lib.ui.banner.holder.Holder;
import com.ydtl.uboxpay.R;

/**
 * Created by HashWaney on 2019/8/29.
 */

public class ImageLoader extends Holder<Integer> {
    private ImageView imageView;

    public ImageLoader(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        imageView = itemView.findViewById(R.id.ivPost);
    }

    @Override
    public void updateItem(Integer data) {
        Log.e(this.getClass().getSimpleName(), "data:" + data);
//        GlideUtil.sharedInstance().display(imageView.getContext(), imageView, data.getPicPath(), 70, 70);
        imageView.setImageResource(data);

    }
}
