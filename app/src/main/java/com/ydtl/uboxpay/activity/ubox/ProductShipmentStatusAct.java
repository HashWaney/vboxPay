package com.ydtl.uboxpay.activity.ubox;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hash.lib.ui.glide.GlideUtil;
import com.hash.lib.ui.utils.PixelUtil;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.activity.base.BaseActivity;
import com.ydtl.uboxpay.bean.ProductInfo;
import com.ydtl.uboxpay.component.Constant;
import com.ydtl.uboxpay.tool.AndroidUtil;
import com.ydtl.uboxpay.tool.NetworkSignal;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HashWaney on 2019/8/31.
 * 针对的是刷脸支付跳转的页面
 */

public class ProductShipmentStatusAct extends BaseActivity {

    @BindView(R.id.ivShipmentProcess)
    ImageView ivShipmentProcess;

    @BindView(R.id.shipmentProcessStatus)
    LinearLayout shipmentProcessStatus; //出货中

    @BindView(R.id.shipmentFinalStatus)
    LinearLayout shipmentFinalStatus; //出货完成

    @BindView(R.id.tvShipment)
    TextView tvShipment;

    @BindView(R.id.tvShipmentTips)
    TextView tvShipmentTips;//针对出货完成情况的出货失败进行展示

    @BindView(R.id.ivShipmentStatus)
    ImageView ivShipmentStatus;


    @BindView(R.id.tvProductPrice)
    TextView tvProductPrice;


    @BindView(R.id.tvProductName)
    TextView tvProductName;

    @BindView(R.id.ivProductPic)
    ImageView ivProductPic;

    @BindView(R.id.shipmentTips)
    TextView shipmentTips;

    @BindView(R.id.tvVmId)
    TextView tvVmId;
    @BindView(R.id.ivNetStatus)
    ImageView ivNetStatus;


    @BindView(R.id.backToPrePage)
    RelativeLayout backToPage;

    private ProductInfo productInfo;

    private boolean isShipmentSuccess = false;//出货成功

    @Override
    public void setCustomLayout() {
        super.setCustomLayout();
        setContentView(R.layout.activity_product_shipment_status);
        ButterKnife.bind(this);
        productInfo = (ProductInfo) AndroidUtil.getObject(this, Constant.PRODUCT_DETAIL_INFO);
        tvVmId.setText("机器号: " + AndroidUtil.getConfigValue(this, Constant.GET_VM_ID, ""));
        Log.e(this.getClass().getSimpleName(), "读取存储的商品信息:" + productInfo);
        // TODO: 2019/9/1 伪代码
        // 1.点击刷脸支付完成 进行出货,展示出货进行中的ui

        shipmentProcessShowUi();
        //2.异步回调出货接口,完成出货的状态,展示出货成功或者失败的ui
//        shipmentFinalShowUi();

    }


    // TODO: 2019/9/2 此处是展示 出货失败或者出货成功的ui 暂时以isShipmentFail模拟服务端结果返回的出货成功或者出货失败的状态, 该方法在调用结果有返回值后调用
    private void shipmentFinalShowUi() {
        if (shipmentProcessStatus != null && shipmentProcessStatus.getVisibility() == View.VISIBLE) {
            shipmentProcessStatus.setVisibility(View.GONE);
        }

        if (shipmentFinalStatus != null && shipmentFinalStatus.getVisibility() == View.GONE) {
            shipmentFinalStatus.setVisibility(View.VISIBLE);
        }
        //展示返回按钮
        if (backToPage != null && backToPage.getVisibility() == View.GONE) {
            backToPage.setVisibility(View.VISIBLE);

        }
        tvProductName.setText(productInfo.getProductName());
        String productPrice = AndroidUtil.getAmountString(Integer.parseInt(productInfo.getProductPrice()));
        tvProductPrice.setText("￥" + productPrice);
        GlideUtil.sharedInstance().display(this, ivProductPic, productInfo.getProductPic(),R.drawable.shop_default_bg);
        if (isShipmentSuccess) { //出货成功
            ivShipmentStatus.setImageResource(R.drawable.shipment_success);
            tvShipment.setText("商品出货成功");
            tvShipment.setTextColor(Color.parseColor("#37BD57"));
            tvShipmentTips.setVisibility(View.GONE);
        } else {
            ivShipmentStatus.setImageResource(R.drawable.shipment_fail);
            tvShipment.setText("商品出货失败");
            tvShipment.setTextColor(Color.parseColor("#FF7F00"));
            tvShipmentTips.setVisibility(View.VISIBLE);

        }


    }

    private void shipmentProcessShowUi() {
        if (shipmentProcessStatus != null && shipmentProcessStatus.getVisibility() == View.GONE) {
            shipmentProcessStatus.setVisibility(View.VISIBLE);
            GlideUtil.sharedInstance().displayGif(this, R.drawable.product_shipment, ivShipmentProcess, PixelUtil.dip2px(this, 121f),
                    PixelUtil.dip2px(this, 111.5f));
        }

        if (shipmentFinalStatus != null && shipmentFinalStatus.getVisibility() == View.VISIBLE) {
            shipmentFinalStatus.setVisibility(View.GONE);

        }
    }

    @OnClick(R.id.backToPrePage)
    public void finish(View view) {
        if (null != shipmentProcessStatus && shipmentProcessStatus.getVisibility() == View.GONE) {
            // TODO: 2019/9/2 建议移除所有Activity 跳转到MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
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
}
