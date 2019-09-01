package com.hash.lib.ui.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.hash.lib.ui.R;
import com.hash.lib.ui.utils.PixelUtil;


/**
 * Created by Administrator on 2017/6/26.
 */
public class GlideUtil {

    private static GlideUtil glideUtil;

    public static GlideUtil sharedInstance() {
        if (glideUtil == null) {
            glideUtil = new GlideUtil();
        }
        return glideUtil;
    }

    public void displayCenterInsideTransform(Context context, ImageView mView, String url) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions
                        .centerInsideTransform()
                        .dontAnimate())
                .transition(GenericTransitionOptions.withNoTransition())
                .into(mView);
    }

    /**
     * 加载图片
     */
    public void display(Context context, ImageView mView, String url) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions
                        .centerCropTransform()
                        .dontAnimate())
                .transition(GenericTransitionOptions.withNoTransition())
                .into(mView);
    }

    /**
     * 加载图片
     */
    public void display(Context context, ImageView mView, int url) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions
                        .centerCropTransform()
                        .dontAnimate())
                .transition(GenericTransitionOptions.withNoTransition())
                .into(mView);
    }

    /**
     * 加载图片
     */
    public void display(Context context, ImageView mView, String url, int placeholder) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions
                        .centerCropTransform()
                        .dontAnimate()
                        .placeholder(placeholder)
                        .error(placeholder))
                .transition(GenericTransitionOptions.withNoTransition())
                .into(mView);
    }

    /**
     * 加载图片
     */
    public void display(Context context, ImageView mView, String url, int width, int height) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions
                        .overrideOf(width, height)
                        .dontAnimate())
                .transition(GenericTransitionOptions.withNoTransition())
                .into(mView);
    }

    /**
     * 加载图片
     */
    public void display(Context context, ImageView mView, String url, int placeholder, int width, int height) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions
                        .centerCropTransform()
                        .dontAnimate()
                        .placeholder(placeholder)
                        .error(placeholder)
                        .override(width, height))
                .transition(GenericTransitionOptions.withNoTransition())
                .into(mView);
    }

    /*加载圆角*/
    @SuppressWarnings("unchecked")
    public void displayRound(Context context, ImageView mView, String url, int placeHolder, int round) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.
                        bitmapTransform(new RoundedCorners(PixelUtil.dip2px(context, round)))
                        .autoClone()
                        .dontAnimate()
                        .error(placeHolder)
                        .placeholder(placeHolder))
                .transition(GenericTransitionOptions.withNoTransition())
                .into(mView);
    }

    /* 圆形 */
    public void displayCircle(Context context, ImageView mView, String url) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions
                        .circleCropTransform()
                        .dontAnimate())
                .transition(GenericTransitionOptions.withNoTransition())
                .into(mView);
    }

    /**
     * 圆形加载
     *
     * @param context
     * @param mView
     * @param url
     * @param placeholder
     */
    public void displayCircle(Context context, ImageView mView, String url, int placeholder) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.
                        circleCropTransform()
                        .dontAnimate()
                        .placeholder(placeholder)
                        .error(placeholder))
                .transition(GenericTransitionOptions.withNoTransition())
                .into(mView);
    }

    @SuppressWarnings("unchecked")
    public void display(Context context, String url, int placeholder, SimpleTarget target) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.
                        centerCropTransform()
                        .dontAnimate()
                        .placeholder(placeholder)
                        .error(placeholder))
                .transition(GenericTransitionOptions.withNoTransition())
                .into(target);
    }

    public static void displayGif(Context context, @Nullable int resourceId, ImageView mView, int width, int height) {


        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.placeholder(R.mipmap.ic_launcher_round)
                .error(android.R.drawable.stat_notify_error)
                .priority(Priority.HIGH)
                //.skipMemoryCache(true)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Glide.with(context)
                .load(resourceId)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(options)
                .into(mView);
    }
}
