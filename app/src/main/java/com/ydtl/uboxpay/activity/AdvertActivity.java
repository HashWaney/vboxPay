package com.ydtl.uboxpay.activity;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.activity.base.BaseActivity;
import com.ydtl.uboxpay.adapter.ViceAdvertPageAdapter;
import com.ydtl.uboxpay.bean.ShopBean;
import com.ydtl.uboxpay.component.autoviewpager.AutoScrollViewPager;
import com.ydtl.uboxpay.tool.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class AdvertActivity extends BaseActivity implements ViceAdvertPageAdapter.OnButtonOnClickListener {

    private LinearLayout ll_advert;
    private AutoScrollViewPager advertViewpager;
    private ViceAdvertPageAdapter viewpagerAdapter;
    private List<String> autoplayList = null; //广告内容

    @Override
    public void setCustomLayout() {
        super.setCustomLayout();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_advert_info);
    }

    @Override
    public void initView() {
        super.initView();
        ll_advert = (LinearLayout) findViewById(R.id.activity_advert_layout);
        advertViewpager = (AutoScrollViewPager)findViewById(R.id.vice_advert_viewpager);
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = this.getIntent();
        if (null != intent) {
            autoplayList = (List<String>) intent.getExtras().getSerializable("autoplayList");
        }
//        if(autoplayList != null && autoplayList.size() > 0){
//
//        }else {
//            List<Integer> advertList = new ArrayList<Integer>();
//            advertList.add(R.drawable.welcome_bg);
//            advertList.add(R.drawable.welcome_bg1);
//            advertList.add(R.drawable.welcome_bg2);
//        }
        viewpagerAdapter = new ViceAdvertPageAdapter(_context,autoplayList,R.layout.vice_advert_viewpager_item);
        advertViewpager.setAdapter(viewpagerAdapter);
        advertViewpager.setVisibility(View.VISIBLE);
        advertViewpager.startAutoScroll();
        advertViewpager.setInterval(5000);
        viewpagerAdapter.setOnButtonOnClickListener(this);
    }

    @Override
    public void BindComponentEvent() {
        super.BindComponentEvent();
        ll_advert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.e("AdvertActivity   ll_advert");
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void setOnclick() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
