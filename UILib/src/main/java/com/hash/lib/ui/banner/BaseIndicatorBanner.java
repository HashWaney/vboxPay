package com.hash.lib.ui.banner;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hash.lib.ui.BaseAnimator;
import com.hash.lib.ui.R;

import java.util.ArrayList;

public abstract class BaseIndicatorBanner<E, T extends BaseIndicatorBanner<E, T>> extends BaseBanner<E, T> {
    public static final int STYLE_DRAWABLE_RESOURCE = 0;
    public static final int STYLE_CORNER_RECTANGLE = 1;

    private ArrayList<ImageView> mIndicatorViews = new ArrayList<>();
    private int mIndicatorStyle;
    private int mIndicatorWidth;
    private int mIndicatorHeight;

    private int mIndicatorUnSlectWidth;
    private int mIndicatorUnSlectHeight;

    private int mIndicatorGap;
    private int mIndicatorCornerRadius;
    private int mIndicatorUnSlectCornerRadius;

    private Drawable mSelectDrawable;
    private Drawable mUnSelectDrawable;
    private int mSelectColor;
    private int mUnselectColor;

    private Class<? extends BaseAnimator> mSelectAnimClass;
    private Class<? extends BaseAnimator> mUnselectAnimClass;

    private LinearLayout mLlIndicators;

    public BaseIndicatorBanner(Context context) {
        this(context, null, 0);
    }

    public BaseIndicatorBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseIndicatorBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseIndicatorBanner);
        mIndicatorStyle = ta.getInt(R.styleable.BaseIndicatorBanner_bb_indicatorStyle, STYLE_CORNER_RECTANGLE);
        mIndicatorWidth = ta.getDimensionPixelSize(R.styleable.BaseIndicatorBanner_bb_indicatorWidth, dp2px(6));
        mIndicatorHeight = ta.getDimensionPixelSize(R.styleable.BaseIndicatorBanner_bb_indicatorHeight, dp2px(6));

        mIndicatorUnSlectWidth = ta.getDimensionPixelSize(R.styleable.BaseIndicatorBanner_bb_indicatorUnSelectWidth, dp2px(6));
        mIndicatorUnSlectHeight = ta.getDimensionPixelSize(R.styleable.BaseIndicatorBanner_bb_indicatorUnSelectHeight, dp2px(6));

        mIndicatorGap = ta.getDimensionPixelSize(R.styleable.BaseIndicatorBanner_bb_indicatorGap, dp2px(6));
        mIndicatorCornerRadius = ta.getDimensionPixelSize(R.styleable.BaseIndicatorBanner_bb_indicatorCornerRadius, dp2px(3));
        mIndicatorUnSlectCornerRadius = ta.getDimensionPixelSize(R.styleable.BaseIndicatorBanner_bb_indicatorUnSelectCornerRadius, dp2px(3));

        mSelectColor = ta.getColor(R.styleable.BaseIndicatorBanner_bb_indicatorSelectColor, Color.parseColor("#ffffff"));
        mUnselectColor = ta.getColor(R.styleable.BaseIndicatorBanner_bb_indicatorUnselectColor, Color.parseColor("#88ffffff"));

        int selectRes = ta.getResourceId(R.styleable.BaseIndicatorBanner_bb_indicatorSelectRes, 0);
        int unselectRes = ta.getResourceId(R.styleable.BaseIndicatorBanner_bb_indicatorUnselectRes, 0);
        ta.recycle();

        //create indicator container
        mLlIndicators = new LinearLayout(context);
        mLlIndicators.setGravity(Gravity.CENTER);

        setIndicatorSelectorRes(unselectRes, selectRes);
    }

    @Override
    public View onCreateIndicator() {
        if (mIndicatorStyle == STYLE_CORNER_RECTANGLE) {//rectangle
            this.mUnSelectDrawable = getDrawable(mUnselectColor, mIndicatorUnSlectCornerRadius);
            this.mSelectDrawable = getDrawable(mSelectColor, mIndicatorCornerRadius);
        }

        int size = mDatas.size();
        mIndicatorViews.clear();

        mLlIndicators.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setImageDrawable(i == mCurrentPositon ? mSelectDrawable : mUnSelectDrawable);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mIndicatorUnSlectWidth, mIndicatorUnSlectHeight);
            lp.leftMargin = i == 0 ? 0 : mIndicatorGap;
            mLlIndicators.addView(iv, lp);
            mIndicatorViews.add(iv);
        }

        setCurrentIndicator(mCurrentPositon);

        return mLlIndicators;
    }

    @Override
    public void setCurrentIndicator(int position) {
        for (int i = 0; i < mIndicatorViews.size(); i++) {
            mIndicatorViews.get(i).setImageDrawable(i == position ? mSelectDrawable : mUnSelectDrawable);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mIndicatorViews.get(i).getLayoutParams();
            if (i == position) {
                lp.width = mIndicatorWidth;
                lp.height = mIndicatorHeight;
            } else {
                lp.width = mIndicatorUnSlectWidth;
                lp.height = mIndicatorUnSlectHeight;
            }
            mIndicatorViews.get(i).setLayoutParams(lp);
        }
        try {
            if (mSelectAnimClass != null) {
                if (position == mLastPositon) {
                    mSelectAnimClass.newInstance().playOn(mIndicatorViews.get(position));
                } else {
                    mSelectAnimClass.newInstance().playOn(mIndicatorViews.get(position));
                    if (mUnselectAnimClass == null) {
                        mSelectAnimClass.newInstance().interpolator(new ReverseInterpolator()).playOn(mIndicatorViews.get(mLastPositon));
                    } else {
                        mUnselectAnimClass.newInstance().playOn(mIndicatorViews.get(mLastPositon));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置显示样式,STYLE_DRAWABLE_RESOURCE or STYLE_CORNER_RECTANGLE
     */
    public T setIndicatorStyle(int indicatorStyle) {
        this.mIndicatorStyle = indicatorStyle;
        return (T) this;
    }

    /**
     * 设置显示宽度,单位dp,默认6dp
     */
    public T setIndicatorWidth(float indicatorWidth) {
        this.mIndicatorWidth = dp2px(indicatorWidth);
        return (T) this;
    }

    /**
     * 设置显示器高度,单位dp,默认6dp
     */
    public T setIndicatorHeight(float indicatorHeight) {
        this.mIndicatorHeight = dp2px(indicatorHeight);
        return (T) this;
    }

    /**
     * 设置两个显示器间距,单位dp,默认6dp
     */
    public T setIndicatorGap(float indicatorGap) {
        this.mIndicatorGap = dp2px(indicatorGap);
        return (T) this;
    }

    /**
     * 设置显示器选中颜色(for STYLE_CORNER_RECTANGLE),默认"#ffffff"
     */
    public T setIndicatorSelectColor(int selectColor) {
        this.mSelectColor = selectColor;
        return (T) this;
    }

    /**
     * 设置显示器未选中颜色(for STYLE_CORNER_RECTANGLE),默认"#88ffffff"
     */
    public T setIndicatorUnselectColor(int unselectColor) {
        this.mUnselectColor = unselectColor;
        return (T) this;
    }

    /**
     * 设置显示器圆角弧度(for STYLE_CORNER_RECTANGLE),单位dp,默认3dp
     */
    public T setIndicatorCornerRadius(float indicatorCornerRadius) {
        this.mIndicatorCornerRadius = dp2px(indicatorCornerRadius);
        return (T) this;
    }

    /**
     * 设置显示器选中以及未选中资源(for STYLE_DRAWABLE_RESOURCE)
     */
    public T setIndicatorSelectorRes(int unselectRes, int selectRes) {
        try {
            if (mIndicatorStyle == STYLE_DRAWABLE_RESOURCE) {
                if (selectRes != 0) {
                    this.mSelectDrawable = getResources().getDrawable(selectRes);
                }
                if (unselectRes != 0) {
                    this.mUnSelectDrawable = getResources().getDrawable(unselectRes);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return (T) this;
    }

    /**
     * 设置显示器选中动画
     */
    public T setSelectAnimClass(Class<? extends BaseAnimator> selectAnimClass) {
        this.mSelectAnimClass = selectAnimClass;
        return (T) this;
    }

    /**
     * 设置显示器未选中动画
     */
    public T setUnselectAnimClass(Class<? extends BaseAnimator> unselectAnimClass) {
        this.mUnselectAnimClass = unselectAnimClass;
        return (T) this;
    }

    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

    private GradientDrawable getDrawable(int color, float raduis) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(raduis);
        drawable.setColor(color);
        return drawable;
    }
}
