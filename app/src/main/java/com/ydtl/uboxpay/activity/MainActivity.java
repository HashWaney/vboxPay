package com.ydtl.uboxpay.activity;

import android.content.Context;
import android.graphics.Rect;
import android.nfc.Tag;
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

import com.hash.lib.ui.banner.listener.OnItemClickListener;
import com.hash.lib.ui.glide.GlideUtil;
import com.hash.lib.ui.recycleritem.GridSpacingItemDecoration;
import com.hash.lib.ui.recyclerview.BaseRecyclerView;
import com.hash.lib.ui.utils.PixelUtil;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.activity.base.BaseActivity;
import com.ydtl.uboxpay.adapter.GoodsAdapter;
import com.ydtl.uboxpay.bean.GoodsBannerInfo;
import com.ydtl.uboxpay.bean.GoodsInfo;

import java.util.ArrayList;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;


public class MainActivity extends BaseActivity implements BGABanner.Adapter, BGABanner.Delegate, BGAOnRVItemClickListener /*implements OnRefreshListener*/ {

    private RecyclerView mRecyclerView;

    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567002249582&di=59134cd07acf3d9b8a0d8ed4ea260d8d&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fdc54564e9258d1092f7663c9db58ccbf6c814d30.jpg";
    private ArrayList<GoodsInfo> mData;
    private ArrayList<GoodsBannerInfo> mBannerInfoData;
    private BGABanner mBanner;
    private ContentAdapter mContentAdapter;

    @Override
    public void setCustomLayout() {
        super.setCustomLayout();
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.baseRecycleView);
    }

    @Override
    public void initView() {
        super.initView();
        Log.e(this.getClass().getSimpleName(), "initView");
        initRequestData();
        initAdapter();
    }

    private void initAdapter() {
        mContentAdapter = new ContentAdapter(mRecyclerView, this);
        View header = initHeader();
        mContentAdapter.addHeaderView(header);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3,LinearLayoutManager.VERTICAL,false));
        /**
         * new GridSpacingItemDecoration(3, PixelUtil.dip2px(this, 10), false) {
        @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        // 注意：由于加了一个 HeaderView，所以是大于 0 时才加分隔间隙。onCanvas 就不演示了
        if (position > 0) {
        int halfPadding = BGABannerUtil.dp2px(view.getContext(), 10);
        outRect.set(halfPadding, halfPadding, halfPadding, halfPadding);
        }
        }
        }
         */
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, PixelUtil.dip2px(this, 10), false));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, PixelUtil.dip2px(this, 10), false));
        mContentAdapter.setData(mData);
        mContentAdapter.setOnRVItemClickListener(this);
        mRecyclerView.setAdapter(mContentAdapter.getHeaderAndFooterAdapter());

    }

    private View initHeader() {
        View header = LayoutInflater.from(this).inflate(R.layout.layout_header, null);
        mBanner = header.findViewById(R.id.banner);
        mBanner.setAdapter(this);
        mBanner.setDelegate(this);
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
        mBanner.setData(mBannerInfoData, null);
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


    @Override
    public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {
        Toast.makeText(this, "position:" + position, Toast.LENGTH_LONG).show();

    }

    @Override
    public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
        GoodsBannerInfo bannerInfo = (GoodsBannerInfo) model;
        GlideUtil.sharedInstance()
                .display(this, ((ImageView) itemView), bannerInfo.getPicPath());


    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        GoodsInfo item = mContentAdapter.getItem(position);
        Log.e(this.getClass().getSimpleName(), "item: price:" + item.getGoodsPrice());
    }

    private class ContentAdapter extends BGARecyclerViewAdapter<GoodsInfo> {

        private Context context;

        public ContentAdapter(RecyclerView recyclerView, Context context) {
            super(recyclerView, R.layout.layout_goods_item);
            this.context = context;
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, GoodsInfo model) {
            ImageView ivPic = helper.getView(R.id.ivPic);
            TextView tvName = helper.getView(R.id.tvName);
            TextView tvPrice = helper.getView(R.id.tvPrice);
            GlideUtil.sharedInstance().display(context, ivPic, model.getGoodsPic());
            tvName.setText(model.getGoodsName());
            tvPrice.setText(model.getGoodsPrice());


        }
    }

}

