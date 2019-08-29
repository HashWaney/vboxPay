package com.ydtl.uboxpay.activity.ubox;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hash.lib.ui.anim.ZoomInEnter;
import com.hash.lib.ui.glide.GlideUtil;
import com.hash.lib.ui.pulltorefresh.SuperSwipyRefreshLayout2;
import com.hash.lib.ui.recycleritem.GridSpacingItemDecoration;
import com.hash.lib.ui.recyclerview.BaseRecyclerView;
import com.hash.lib.ui.utils.PixelUtil;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.activity.base.BaseActivity;
import com.ydtl.uboxpay.adapter.GoodsAdapter;
import com.ydtl.uboxpay.bean.GoodsBannerInfo;
import com.ydtl.uboxpay.bean.GoodsInfo;
import com.ydtl.uboxpay.ui.UIAdvBannerLayout;

import java.util.ArrayList;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.bgabanner.BGABanner;


public class MainActivity2 extends BaseActivity /*implements OnRefreshListener*/ {

    private SuperSwipyRefreshLayout2 refreshLayout;
    private BaseRecyclerView mRecyclerView;

    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567002249582&di=59134cd07acf3d9b8a0d8ed4ea260d8d&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fdc54564e9258d1092f7663c9db58ccbf6c814d30.jpg";
    private ArrayList<GoodsInfo> mData;
    private ArrayList<Object> mBannerInfoData;
    private UIAdvBannerLayout mBanner;
    private GoodsAdapter mContentAdapter;

    @Override
    public void setCustomLayout() {
        super.setCustomLayout();
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.baseRecycleView);
        refreshLayout = findViewById(R.id.refreshView);
    }

    @Override
    public void initView() {
        super.initView();
        initRequestData();
        initAdapter();
    }

    private void initAdapter() {
        View header = initHeader();
        refreshLayout.addHeaderView(header);
        mContentAdapter = new GoodsAdapter(mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, PixelUtil.dip2px(this, 10), false));
        mRecyclerView.setAdapter(mContentAdapter);

    }

    private View initHeader() {
        View header = LayoutInflater.from(this).inflate(R.layout.layout_goods_adv_header, null);
        UIAdvBannerLayout mBanner = header.findViewById(R.id.banner);

        for (int i = 0; i < 3; i++) {
            GoodsBannerInfo bannerInfo = new GoodsBannerInfo();
            bannerInfo.setJumpUrl("");
            if (i == 0) {
                bannerInfo.setPicPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567002250082&di=ee90ed9f43fc490b6eba70086bae776e&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fc2cec3fdfc03924590b2a9b58d94a4c27d1e2500.jpg");
            } else if (1 == i) {
                bannerInfo.setPicPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567002250082&di=2e9843cc0590503739ee28a941a99eea&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fb812c8fcc3cec3fdb850efcfdc88d43f87942719.jpg");
            } else {
                bannerInfo.setPicPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567002250082&di=3191d009773ae6efe63d27d173797afd&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F0ff41bd5ad6eddc40189fc4133dbb6fd52663319.jpg");
            }
            mBannerInfoData.add(bannerInfo);

        }

        mBanner.setSelectAnimClass(ZoomInEnter.class)
                .setDelay(15)
                .setPeriod(10)
                .setSource(mBannerInfoData)
                .setTransformerClass(null)
                .startScroll();

        return header;
    }


    private void initRequestData() {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        if (mBannerInfoData == null) {
            mBannerInfoData = new ArrayList<>();
        }
        mBannerInfoData.clear();
        mData.clear();

        for (int i = 0; i < 10; i++) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setGoodsName("可口可乐300ml");
            goodsInfo.setGoodsPrice(String.valueOf(i + 3.4));
            goodsInfo.setGoodsPic(url);
            mData.add(goodsInfo);
        }
    }


    @Override
    public void initData() {
        super.initData();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}

