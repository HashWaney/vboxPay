package com.hash.lib.ui.glide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;
import com.hash.lib.ui.utils.PathUtil;


/**
 * Created by HashWaney on 2019/8/28.
 */

public class UBoxGlideModule implements GlideModule {

    private String TAG = this.getClass().getSimpleName();


    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {

        // TODO: 2017/8/1 内存缓存大小
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 4;//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));

        // TODO: 2017/8/1 磁盘缓存大小
        int diskCacheSize = 1024 * 1024 * 300;//最多可以缓存多少字节的数据
        //设置磁盘缓存大小
        builder.setDiskCache(new DiskLruCacheFactory(PathUtil.getInstance().getWebImgePath(), "glide", diskCacheSize));
        // TODO: 2017/8/1 设置图片解码格式
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        // TODO: 2017/8/1 设置BitmapPool缓存内存大小
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));
        Log.e(TAG, "glide applyOptions被调用了:" + memoryCacheSize);

    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        Log.e(TAG, "registerComponents");

    }


}
