package com.hash.lib.ui.glide;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hash.lib.ui.R;

/**
 * Created by Administrator on 2017/6/26.
 */

public class GlideView extends android.support.v7.widget.AppCompatImageView {

    private Drawable placeholder;

    private Drawable error;

    private boolean isCircle = false;
    private float round;

    // TODO: 2017/10/10 用来替代setTag
    private Object newTag;

    public GlideView(Context context) {
        this(context, null, -1);
    }

    public GlideView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public GlideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.FIT_XY);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyGlide);//TypedArray是一个数组容器
            placeholder = a.getDrawable(R.styleable.MyGlide_placeholder);//
            error = a.getDrawable(R.styleable.MyGlide_error);//
            isCircle = a.getBoolean(R.styleable.MyGlide_circle, false);//
            round = a.getDimension(R.styleable.MyGlide_round, -1);//
            a.recycle();//我的理解是：返回以前取回的属性，供以后使用。以前取回的可能就是textSize和textColor初始化的那段
        }
    }

    public void setPic(String pic) {
        if (isCircle) {
            setCircleImageView(pic);
        } else if (round >= 1) {
            setRoundImageView(pic, (int) round);
        } else {
            setNormalImageView(pic);
        }
    }

    public void setPic(int pic) {
        if (isCircle) {
            setCircleImageView(pic);
        } else if (round >= 1) {
            setRoundImageView(pic, (int) round);
        } else {
            setNormalImageView(pic);
        }
    }


    /* 圆形 */
    public void setCircleImageView(String pic) {
        Glide.with(getContext())
                .load(pic)
                .apply(RequestOptions.circleCropTransform().placeholder(placeholder).error(placeholder))
                .into(this);
    }

    /* 圆形 */
    public void setCircleImageView(int pic) {
        Glide.with(getContext())
                .load(pic)
                .apply(RequestOptions
                        .circleCropTransform()
                        .dontAnimate()
                        .placeholder(placeholder)
                        .error(placeholder))
                .into(this);
    }

    /* 圆角 */
    @SuppressWarnings("unchecked")
    public void setRoundImageView(String pic, int round) {
        RequestOptions options = RequestOptions.
                bitmapTransform(new MultiTransformation(new CenterCrop(), new GlideRoundTransform(getContext(), round)))
                .dontAnimate()
                .error(placeholder)
                .placeholder(placeholder);
        Glide.with(getContext())
                .load(pic)
                .apply(options)
                .into(this);
    }

    /* 圆角 */
    @SuppressWarnings("unchecked")
    public void setRoundImageView(int pic, int round) {
        RequestOptions options = RequestOptions.
                bitmapTransform(new MultiTransformation(new CenterCrop(), new GlideRoundTransform(getContext(), round)))
                .dontAnimate()
                .error(placeholder)
                .placeholder(placeholder);
        Glide.with(getContext())
                .load(pic)
                .apply(options)
                .into(this);
    }

    /* 默认的 */
    @SuppressWarnings("unchecked")
    public void setNormalImageView(String pic) {
        Glide.with(getContext())
                .load(pic)
                .apply(RequestOptions
                        .centerCropTransform()
                        .dontAnimate()
                        .placeholder(placeholder)
                        .error(placeholder))
                .into(this);
    }

    /* 默认的 */
    public void setNormalImageView(int pic) {
        Glide.with(getContext())
                .load(pic)
                .apply(RequestOptions
                        .centerCropTransform()
                        .dontAnimate()
                        .placeholder(placeholder)
                        .error(placeholder))
                .into(this);
    }

    public Object getNewTag() {
        return newTag;
    }

    public void setNewTag(Object newTag) {
        this.newTag = newTag;
    }
}
