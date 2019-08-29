package com.hash.lib.ui.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hash.lib.ui.R;
import com.hash.lib.ui.banner.adapter.BannerPageAdapter;
import com.hash.lib.ui.banner.helper.BannerLoopScaleHelper;
import com.hash.lib.ui.banner.holder.BannerItemHolderCreator;
import com.hash.lib.ui.banner.listener.BannerPageChangeListener;
import com.hash.lib.ui.banner.listener.OnItemClickListener;
import com.hash.lib.ui.banner.listener.OnPageChangeListener;
import com.hash.lib.ui.banner.widget.BannerLooperViewPage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HashWaney on 2019/8/29.
 */

public class SimpleBanner<T> extends RelativeLayout {

    private List<T> mData;
    private int[] indicatorId;
    private ArrayList<ImageView> indicatorView = new ArrayList<>();
    private BannerPageAdapter pageAdapter;
    private BannerLooperViewPage viewPager;
    private ViewGroup indicatorContainer;
    private long autoTurningTime = -1;//翻页时间
    private boolean turning; //翻页
    private boolean canTurn = false;
    private boolean canLoop = true;
    private BannerLoopScaleHelper scaleHelper;
    private BannerPageChangeListener pageChangeListener;
    private OnPageChangeListener onPageChangeListener;
    private AdSwitchTask adSwitchTask;

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
    }

    public SimpleBanner(Context c) {
        super(c);
        init(c);
    }

    public SimpleBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        canLoop = t.getBoolean(R.styleable.ConvenientBanner_canLoop, true);
        autoTurningTime = t.getInteger(R.styleable.ConvenientBanner_autoTurningTime, -1);
        t.recycle();
        init(context);
    }

    private void init(Context context) {
        Log.e(this.getClass().getSimpleName(), "init()");
        View view = LayoutInflater.from(context).inflate(R.layout.include_viewpager, this, false);
        viewPager = view.findViewById(R.id.cbLoopViewPager);
        indicatorContainer = view.findViewById(R.id.loPageTurningPoint);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        viewPager.setLayoutManager(linearLayoutManager);
        scaleHelper = new BannerLoopScaleHelper();
        // TODO: 2019/8/29
        adSwitchTask = new AdSwitchTask(this);
    }

    public SimpleBanner setPages(BannerItemHolderCreator creator, List<T> data) {
        this.mData = data;
        Log.e(this.getClass().getSimpleName(), "setPages");
        pageAdapter = new BannerPageAdapter(creator, mData, canLoop);
        viewPager.setAdapter(pageAdapter);
        if (indicatorId != null) {
            setPageIndicator(indicatorId);
        }
        scaleHelper.setFirstItemPos(canLoop ? mData.size() : 0);
        scaleHelper.attachToRecyclerView(viewPager);
        pageAdapter.notifyDataSetChanged();
        return this;
    }

    public SimpleBanner setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        pageAdapter.setCanLoop(canLoop);
        notifyDataSetChanged();
        return this;
    }

    //update data change
    public void notifyDataSetChanged() {
        viewPager.getAdapter().notifyDataSetChanged();
        if (indicatorId != null) {
            setPageIndicator(indicatorId);
        }
        scaleHelper.setCurrentItem(canLoop ? mData.size() : 0);


    }

    public SimpleBanner setPointViewVisible(boolean visible) {
        indicatorContainer.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    /**
     * 底部指示器资源图片
     *
     * @param page_indicatorId
     */
    public SimpleBanner setPageIndicator(int[] page_indicatorId) {
        indicatorContainer.removeAllViews();
        indicatorView.clear();
        this.indicatorId = page_indicatorId;
        if (mData == null) return this;
        for (int count = 0; count < mData.size(); count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(5, 0, 5, 0);
            if (scaleHelper.getFirstItemPos() % mData.size() == count)
                pointView.setImageResource(page_indicatorId[1]);
            else
                pointView.setImageResource(page_indicatorId[0]);
            indicatorView.add(pointView);
            indicatorContainer.addView(pointView);
        }
        pageChangeListener = new BannerPageChangeListener(indicatorView,
                page_indicatorId);
        scaleHelper.setOnPageChangeListener(pageChangeListener);
        if (onPageChangeListener != null)
            pageChangeListener.setOnPageChangeListener(onPageChangeListener);

        return this;
    }

    public OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    /**
     * 设置翻页监听器
     *
     * @param onPageChangeListener
     * @return
     */
    public SimpleBanner setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        //如果有默认的监听器（即是使用了默认的翻页指示器）则把用户设置的依附到默认的上面，否则就直接设置
        if (pageChangeListener != null)
            pageChangeListener.setOnPageChangeListener(onPageChangeListener);
        else scaleHelper.setOnPageChangeListener(onPageChangeListener);
        return this;
    }

    /**
     * 监听item点击
     *
     * @param onItemClickListener
     */
    public SimpleBanner setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        if (onItemClickListener == null) {
            pageAdapter.setOnItemClickListener(null);
            return this;
        }
        pageAdapter.setOnItemClickListener(onItemClickListener);
        return this;
    }

    /**
     * 获取当前页对应的position
     *
     * @return
     */
    public int getCurrentItem() {
        return scaleHelper.getRealCurrentItem();
    }

    /**
     * 设置当前页对应的position
     *
     * @return
     */
    public SimpleBanner<T> setCurrentItem(int position, boolean smoothScroll) {
        scaleHelper.setCurrentItem(canLoop ? mData.size() + position : position, smoothScroll);
        return this;
    }

    /**
     * 设置第一次加载当前页对应的position
     * setPageIndicator之前使用
     *
     * @return
     */
    public SimpleBanner<T> setFirstItemPos(int position) {
        scaleHelper.setFirstItemPos(canLoop ? mData.size() + position : position);
        return this;
    }

    /**
     * 指示器的方向
     *
     * @param align 三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中 （RelativeLayout.CENTER_HORIZONTAL），居右 （RelativeLayout.ALIGN_PARENT_RIGHT）
     * @return
     */
    public SimpleBanner<T> setPageIndicatorAlign(PageIndicatorAlign align) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) indicatorContainer.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, align == PageIndicatorAlign.ALIGN_PARENT_LEFT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, align == PageIndicatorAlign.ALIGN_PARENT_RIGHT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, align == PageIndicatorAlign.CENTER_HORIZONTAL ? RelativeLayout.TRUE : 0);
        indicatorContainer.setLayoutParams(layoutParams);
        return this;
    }

    /***
     * 是否开启了翻页
     * @return
     */
    public boolean isTurning() {
        return turning;
    }

    /***
     * 开始翻页
     * @param autoTurningTime 自动翻页时间
     * @return
     */
    public SimpleBanner<T> startTurning(long autoTurningTime) {
        if (autoTurningTime < 0) return this;
        //如果是正在翻页的话先停掉
        if (turning) {
            stopTurning();
        }
        //设置可以翻页并开启翻页
        canTurn = true;
        this.autoTurningTime = autoTurningTime;
        turning = true;
        postDelayed(adSwitchTask, autoTurningTime);
        return this;
    }

    public SimpleBanner<T> startTurning() {
        startTurning(autoTurningTime);
        return this;
    }


    public void stopTurning() {
        turning = false;
        removeCallbacks(adSwitchTask);
    }

    //触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页

    float startX, startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            if (canTurn) startTurning(autoTurningTime);
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (canTurn) stopTurning();
        }

        return super.dispatchTouchEvent(ev);
    }

    static class AdSwitchTask implements Runnable {

        private final WeakReference<SimpleBanner> reference;

        AdSwitchTask(SimpleBanner convenientBanner) {
            this.reference = new WeakReference<SimpleBanner>(convenientBanner);
        }

        @Override
        public void run() {
            SimpleBanner convenientBanner = reference.get();

            if (convenientBanner != null) {
                if (convenientBanner.viewPager != null && convenientBanner.turning) {
                    int page = convenientBanner.scaleHelper.getCurrentItem() + 1;
                    convenientBanner.scaleHelper.setCurrentItem(page, true);
                    convenientBanner.postDelayed(convenientBanner.adSwitchTask, convenientBanner.autoTurningTime);
                }
            }
        }
    }

}
