package com.ydtl.uboxpay.activity.ubox;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hash.lib.ui.glide.GlideUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.activity.base.BaseActivity;
import com.ydtl.uboxpay.bean.OrderInfo;
import com.ydtl.uboxpay.bean.ProductInfo;
import com.ydtl.uboxpay.component.Constant;
import com.ydtl.uboxpay.component.callback;
import com.ydtl.uboxpay.tool.AndroidUtil;
import com.ydtl.uboxpay.tool.DataResolveUtils;
import com.ydtl.uboxpay.tool.NetworkSignal;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;

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
    @BindView(R.id.ivNetStatus)
    ImageView ivNetStatus;
    @BindView(R.id.backToPrePage)
    RelativeLayout backToPrePage;


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
        backToPrePage.setVisibility(View.VISIBLE);
        tvVmId.setText("机器号: " + AndroidUtil.getConfigValue(this, Constant.GET_VM_ID, ""));

    }

    @OnClick(R.id.ivClickFacePay)
    public void clickTopFace() {
        // TODO: 2019/9/2 进行下单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setVmid(AndroidUtil.getConfigValue(this, Constant.GET_VM_ID, ""));
        orderInfo.setApp_id(Constant.APP_ID);
        orderInfo.setProduct_id(productInfo.getProductId());
        orderInfo.setApp_tran_id("21313");
        orderInfo.setApp_uid("11233");
        String sign = DataResolveUtils.formatSignParam(orderInfo);
        String param = DataResolveUtils.buildRequestParam(orderInfo) + sign;
        OkGo.<String>post(Constant.ORDER_URL)
                .upString(param, MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"))
                .cacheMode(CacheMode.NO_CACHE)
                .tag(this)
                .execute(new callback() {
                    @Override
                    public void onresponse(String sJson) {
                        Log.e(this.getClass().getSimpleName(), "sJson" + sJson);
                        //  {"head":{"return_code":200,"return_msg":"\u6b63\u5e38\u54cd\u5e94"},
                        // "body":{"delivery_code":"13923958","vmid":"75502237","expire_time":1567419752,"box_number":"","tran_id":"144495456"}}
                        JSONObject jsonObject = JSONObject.parseObject(sJson);
                        if (null != jsonObject.getJSONObject("head")) {
                            JSONObject head = jsonObject.getJSONObject("head");
                            int return_code = head.getIntValue("return_code");
                            if (200 == return_code) {
                                if (null != jsonObject.getJSONObject("body")) {
                                    JSONObject body = jsonObject.getJSONObject("body");
                                    if (!AndroidUtil.isEmpty(body.getString("tran_id"))) {
                                        if (productInfo != null) {
                                            productInfo.setOrderId(body.getString("tran_id"));
                                            // TODO: 2019/9/2 将下单的bean进行本地存储,以便在出货结果页面,展示对应的商品信息
                                            AndroidUtil.putObject(ProductPayActivity.this, Constant.PRODUCT_DETAIL_INFO, productInfo);
                                            Log.e(ProductPayActivity.this.getClass().getSimpleName(), "下单之后生成的bean:" + productInfo);
                                            Intent intent = new Intent(ProductPayActivity.this, ProductShipmentStatusAct.class);
                                            startActivity(intent);
                                        }
                                    } else {
                                        Toast.makeText(ProductPayActivity.this, "订单生成失败,请重新下单", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } else {
                                Toast.makeText(ProductPayActivity.this, "下单失败,请重试", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onfailure(Response<String> response, int rtnCode) {
                        Toast.makeText(ProductPayActivity.this, "下单失败,请重试", Toast.LENGTH_SHORT).show();
                    }
                });


    }


    @OnClick(R.id.backToPrePage)
    public void finish(View view) {
        finish();
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
