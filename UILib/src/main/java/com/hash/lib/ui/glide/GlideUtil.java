package com.hash.lib.ui.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
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
                        .overrideOf(width,height)
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
                        .override(width,height))
                .transition(GenericTransitionOptions.withNoTransition())
                .into(mView);
    }

    /*加载圆角*/
    @SuppressWarnings("unchecked")
    public void displayRound(Context context,ImageView mView,String url,int placeHolder,int round){
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.
                        bitmapTransform(new RoundedCorners(PixelUtil.dip2px(context,round)))
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
     * @param context
     * @param mView
     * @param url
     * @param placeholder
     */
    public void displayCircle(Context context, ImageView mView, String url,int placeholder) {
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
    public void display(Context context,String url, int placeholder, SimpleTarget target) {
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
}
