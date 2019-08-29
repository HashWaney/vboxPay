package com.hash.lib.ui.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

/**
 * 生成带圆角的glide
 */
public class GlideRoundTransform extends BitmapTransformation {

    private static final String ID = "com.bumptech.glide.transformations.FillSpace";

    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private int radius = 0;

    public GlideRoundTransform(Context context) {
        this(context, 4);
    }

    public GlideRoundTransform(Context context, int dp) {
        super(context);
        this.radius = (int) (Resources.getSystem().getDisplayMetrics().density * dp);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
        return TransformationUtils.roundedCorners(pool,bitmap, radius);
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}