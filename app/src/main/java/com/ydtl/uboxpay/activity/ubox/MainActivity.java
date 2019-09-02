package com.ydtl.uboxpay.activity.ubox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hash.lib.ui.recycleritem.GridSpacingItemDecoration;
import com.hash.lib.ui.recyclerview.adapter.BaseQuickAdapter;
import com.hash.lib.ui.utils.PixelUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.activity.base.BaseActivity;
import com.ydtl.uboxpay.adapter.GoodsAdapter;
import com.ydtl.uboxpay.bean.ProductBean;
import com.ydtl.uboxpay.bean.ProductInfo;
import com.ydtl.uboxpay.component.Constant;
import com.ydtl.uboxpay.component.callback;
import com.ydtl.uboxpay.tool.AndroidUtil;
import com.ydtl.uboxpay.tool.DataResolveUtils;
import com.ydtl.uboxpay.tool.NetworkSignal;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.MediaType;


public class MainActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    private GoodsAdapter mContentAdapter;
    private ArrayList<ProductInfo> mData = new ArrayList<>();
    private ArrayList<ProductInfo> productInfoLists;
    private ProductBean productBean;
    @BindView(R.id.baseRecycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.loadView)
    ProgressBar loadingProgressBar;
    @BindView(R.id.tvVmId)
    TextView tvVmId;
    @BindView(R.id.ivNetStatus)
    ImageView ivNetStatus;

    @Override
    public void setCustomLayout() {
        super.setCustomLayout();
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.baseRecycleView);
        loadingProgressBar = findViewById(R.id.loadView);
        mRecyclerView.setVisibility(View.GONE);
        loadingProgressBar.setVisibility(View.VISIBLE);
        ivNetStatus = findViewById(R.id.ivNetStatus);
        tvVmId = findViewById(R.id.tvVmId);
        tvVmId.setText("机器号: " + AndroidUtil.getConfigValue(this, Constant.GET_VM_ID, ""));


    }

    @Override
    public void initView() {
        super.initView();
        initAdapter();
        Bundle bundle = this.getIntent().getExtras();
        productBean = bundle.getParcelable("productBean");
        productInfoLists = (ArrayList<ProductInfo>) bundle.getSerializable("productList");
        if (productBean == null) {
            productBean = new ProductBean();
            productBean.setApp_id(Constant.APP_ID);
            productBean.setVmid(AndroidUtil.getConfigValue(this, Constant.GET_VM_ID, ""));
        }
        if (productInfoLists != null) {
            resetLoading();
            mData.clear();
            mData.addAll(productInfoLists);
            mContentAdapter.notifyDataSetChanged();
        } else {
            productInfoLists = new ArrayList<>();
            initNetRequest();
        }
        Log.e(this.getClass().getSimpleName(), "productBean:" + productBean + " \n" + "productList:" + productInfoLists);


    }

    private void resetLoading() {
        if (loadingProgressBar != null && loadingProgressBar.getVisibility() == View.VISIBLE) {
            loadingProgressBar.setVisibility(View.GONE);
        }
        if (mRecyclerView != null && mRecyclerView.getVisibility() == View.GONE) {
            mRecyclerView.setVisibility(View.VISIBLE);
        }

    }

    private void initNetRequest() {
        String sign = DataResolveUtils.formatSignParam(productBean);
        String param = DataResolveUtils.buildRequestParam(productBean) + sign;
        OkGo.<String>post(Constant.productList_url)
                .upString(param, MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"))
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .tag(this)
                .execute(new callback() {
                    @Override
                    public void onresponse(String sJson) {
                        Log.e(this.getClass().getSimpleName(), "sJson" + sJson);
                        JSONObject jsonObject = JSONObject.parseObject(sJson);
                        ArrayList<ProductInfo> productInfoList = DataResolveUtils.parseProductList(jsonObject);
                        Log.e(this.getClass().getSimpleName(), "productList:" + productInfoList);
                        if (productInfoList != null) {
                            if (productInfoLists != null) {
                                productInfoLists.clear();
                                productInfoLists.addAll(productInfoList);
                            }
                            resetLoading();
                            mData.clear();
                            mData.addAll(productInfoList);
                            mContentAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onfailure(Response<String> response, int rtnCode) {
                        Toast.makeText(MainActivity.this, "数据加载失败,请检查网络是否正常", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void initAdapter() {
        mContentAdapter = new GoodsAdapter(mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(5, PixelUtil.dip2px(this, 10), false));
        mRecyclerView.setAdapter(mContentAdapter);
        mContentAdapter.setOnItemClickListener(this);
    }


    @Override
    public void initData() {
        super.initData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initNetRequest();
        int mobileSignal = NetworkSignal.getInstance().getMobileSignal();
        ivNetStatus.setVisibility(View.VISIBLE);
        if (mobileSignal == 0) {
            ivNetStatus.setImageResource(R.drawable.net_status_fail);
        } else if (mobileSignal == 1) {
            ivNetStatus.setImageResource(R.drawable.net_status_weak);
        } else if (mobileSignal == 2) {
            ivNetStatus.setImageResource(R.drawable.net_status_normal);
        } else if (mobileSignal == 3) {
            ivNetStatus.setImageResource(R.drawable.net_status_high);
        } else if (mobileSignal == 4) {
            ivNetStatus.setImageResource(R.drawable.net_status_strong);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ProductInfo item = (ProductInfo) adapter.getItem(position);
        Log.e(this.getClass().getSimpleName(), "item:" + item);
        if ("0".equalsIgnoreCase(item.getProductNum())) {
            Toast.makeText(this, "该商品已售空,换其他商品试试吧", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ProductPayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("productInfo", item);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
}

