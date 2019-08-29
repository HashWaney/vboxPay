package com.ydtl.uboxpay.tool;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtil {

    public static void setImageUrl(Context context, ImageView view, String url, Drawable placeholderRes, Drawable errorRes) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholderRes)
                .error(errorRes)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(view);
    }

    public static void setImageUrl(Context context, ImageView view, String url, int placeholderRes, int errorRes) {

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholderRes)
                .error(errorRes)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(view);
    }
}
