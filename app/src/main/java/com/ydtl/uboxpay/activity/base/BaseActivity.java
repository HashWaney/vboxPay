package com.ydtl.uboxpay.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ydtl.uboxpay.bean.ShopData;

public class BaseActivity extends AppCompatActivity {

    protected Activity _context;
    protected MainApplication application;
    protected ShopData shopData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // setTheme(android.R.style.Theme_Translucent_NoTitleBar);
        super.onCreate(savedInstanceState);
        //AndroidUtil.setMinHeapSize(0.85f);
        application=(MainApplication) this.getApplication();
        application.addActivity(this);
        _context = this;
        shopData = application.shopData;
        setCustomLayout();
        initView();
        BindComponentEvent();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setCustomLayout(){

    }
    public void initView(){

    }

    /**
     * 绑定控件事件
     * */
    public void BindComponentEvent(){

    }

    /**
     * 初始化数据
     * */
    public void initData(){

    }
}
