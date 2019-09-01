package com.ydtl.uboxpay.activity.ubox;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.hash.lib.ui.glide.GlideUtil;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.activity.base.BaseActivity;
import com.ydtl.uboxpay.bean.ProductInfo;
import com.ydtl.uboxpay.component.Constant;
import com.ydtl.uboxpay.tool.AndroidUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HashWaney on 2019/8/31.
 */

public class ProductPayActivity extends BaseActivity {

    @BindView(R.id.ivClickFacePay)
    ImageView ivClickFacePay;
    @BindView(R.id.tvProductName)
    TextView tvProductName;
    @BindView(R.id.tvProductPrice)
    TextView tvProductPrice;
    @BindView(R.id.ivProductPic)
    ImageView ivProductPic;
    @BindView(R.id.tvVmId)
    TextView tvVmId;

    private ProductInfo productInfo;

    @Override
    public void setCustomLayout() {
        super.setCustomLayout();
        setContentView(R.layout.activity_product_pay);
        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        if (intent != null) {
            productInfo = (ProductInfo) intent.getExtras().getSerializable("productInfo");
            tvProductName.setText(productInfo.getProductName());
            String price = AndroidUtil.getAmountString(Integer.parseInt(productInfo.getProductPrice()));
            tvProductPrice.setText("￥" + price);
            GlideUtil.sharedInstance().display(this, ivProductPic, productInfo.getProductPic(), R.drawable.shop_default_bg);
        }
        tvVmId.setText("机器号: " + AndroidUtil.getConfigValue(this, Constant.GET_VM_ID, ""));

    }

    @OnClick(R.id.ivClickFacePay)
    public void clickTopFace() {
        Intent intent = new Intent(this, ProductShipmentStatusAct.class);
        startActivity(intent);

    }


}
