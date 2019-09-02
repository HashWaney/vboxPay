package com.ydtl.uboxpay.activity.ubox;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hash.lib.ui.glide.GlideUtil;
import com.hash.lib.ui.utils.PixelUtil;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.activity.base.BaseActivity;
import com.ydtl.uboxpay.component.Constant;
import com.ydtl.uboxpay.tool.AndroidUtil;
import com.ydtl.uboxpay.tool.NetworkSignal;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private boolean isShipmentFail = false; //是否出货失败

    @Override
    public void setCustomLayout() {
        super.setCustomLayout();
        setContentView(R.layout.activity_product_shipment_status);
        ButterKnife.bind(this);
        // TODO: 2019/9/1 伪代码
        // 1.点击刷脸支付完成 进行出货,展示出货进行中的ui

        shipmentProcessShowUi();
        //2.异步回调出货接口,完成出货的状态,展示出货成功或者失败的ui
//        shipmentFinalShowUi();
        tvVmId.setText("机器号: " + AndroidUtil.getConfigValue(this, Constant.GET_VM_ID, ""));

    }

    private void shipmentFinalShowUi() {
        if (shipmentProcessStatus != null && shipmentProcessStatus.getVisibility() == View.VISIBLE) {
            shipmentProcessStatus.setVisibility(View.GONE);
        }

        if (shipmentFinalStatus != null && shipmentFinalStatus.getVisibility() == View.GONE) {
            shipmentFinalStatus.setVisibility(View.VISIBLE);

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
