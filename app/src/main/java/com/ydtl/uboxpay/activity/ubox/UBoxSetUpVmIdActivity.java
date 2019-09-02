package com.ydtl.uboxpay.activity.ubox;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.activity.base.BaseActivity;
import com.ydtl.uboxpay.bean.ProductBean;
import com.ydtl.uboxpay.bean.ProductInfo;
import com.ydtl.uboxpay.component.Constant;
import com.ydtl.uboxpay.component.callback;
import com.ydtl.uboxpay.tool.AndroidUtil;
import com.ydtl.uboxpay.tool.DataResolveUtils;
import com.ydtl.uboxpay.tool.NetworkSignal;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;

/**
 * Created by HashWaney on 2019/8/28.
 */

public class UBoxSetUpVmIdActivity extends BaseActivity {
    @BindView(R.id.num0)
    Button btnNum0;
    @BindView(R.id.num1)
    Button btnNum1;
    @BindView(R.id.num2)
    Button btnNum2;
    @BindView(R.id.num3)
    Button btnNum3;
    @BindView(R.id.num4)
    Button btnNum4;
    @BindView(R.id.num5)
    Button btnNum5;
    @BindView(R.id.num6)
    Button btnNum6;
    @BindView(R.id.num7)
    Button btnNum7;
    @BindView(R.id.num8)
    Button btnNum8;
    @BindView(R.id.num9)
    Button btnNum9;
    @BindView(R.id.del)
    RelativeLayout btnDel;
    @BindView(R.id.clear)
    Button btnClear;
    @BindView(R.id.confirm)
    Button btnConfirm;
    @BindView(R.id.etInputId)
    EditText etInput;
    @BindView(R.id.tvTips)
    TextView tvTips;
    @BindView(R.id.ivNetStatus)
    ImageView ivNetStatus;
    @BindView(R.id.tvVmId)
    TextView tvVmId;

    private StringBuilder vimIdInput;
    private String TAG = this.getClass().getSimpleName();

    @Override
    public void setCustomLayout() {
        super.setCustomLayout();
        setContentView(R.layout.activity_setup_vm_id2);
        ButterKnife.bind(this);
        vimIdInput = new StringBuilder();
        etInput.setInputType(InputType.TYPE_NULL);
        tvVmId.setVisibility(View.GONE);
        ivNetStatus.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.clear)
    public void clearInput() {
        vimIdInput.setLength(0);
        etInput.setText("");
        if (tvTips != null && tvTips.getVisibility() == View.VISIBLE) {
            tvTips.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.del)
    public void delInput() {
        if (vimIdInput.length() > 0) {
            vimIdInput.deleteCharAt(vimIdInput.length() - 1);
            etInput.setText(vimIdInput.toString());
            checkInputStr(vimIdInput);
            Log.e(TAG, "after del vi id:" + vimIdInput);
            if (null != tvTips && tvTips.getVisibility() == View.VISIBLE) {
                tvTips.setVisibility(View.GONE);
            }

        }
    }


    @OnClick(R.id.confirm)
    public void confirm() {
        Log.e(TAG, "校验输入值:" + vimIdInput + "checkInputStr(vimIdInput):" + checkInputStr(vimIdInput));
        if (checkInputStr(vimIdInput)) {
            // TODO: 2019/8/28 请求接口 通过接口返回值 确定机器号是否合法
            final ProductBean productBean = new ProductBean();
            productBean.setVmid(vimIdInput.toString());
            productBean.setApp_id(Constant.APP_ID);
            String sign = DataResolveUtils.formatSignParam(productBean);
            String param = DataResolveUtils.buildRequestParam(productBean) + sign;
            OkGo.<String>post(Constant.productList_url)
                    .upString(param, MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"))
                    .tag(this)
                    .execute(new callback() {
                        @Override
                        public void onresponse(String sJson) {
                            Log.e(this.getClass().getSimpleName(), "sJson" + sJson);
                            JSONObject jsonObject = JSONObject.parseObject(sJson);
                            ArrayList<ProductInfo> productInfoList = DataResolveUtils.parseProductList(jsonObject);
                            Log.e(this.getClass().getSimpleName(), "productList:" + productInfoList);
                            if (productInfoList != null) {
                                AndroidUtil.setConfigValue(UBoxSetUpVmIdActivity.this, Constant.IS_SET_UP_VM_ID, true);
                                AndroidUtil.setConfigValue(UBoxSetUpVmIdActivity.this, Constant.GET_VM_ID, productBean.getVmid());
                                Intent intent = new Intent(UBoxSetUpVmIdActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("productList", (Serializable) productInfoList);
                                bundle.putParcelable("productBean", productBean);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            } else {
                                if (null != tvTips && tvTips.getVisibility() == View.GONE) {
                                    tvTips.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onfailure(Response<String> response, int rtnCode) {
                            Toast.makeText(UBoxSetUpVmIdActivity.this, "请输入有效的机器号", Toast.LENGTH_LONG).show();
                            if (null != tvTips && tvTips.getVisibility() == View.GONE) {
                                tvTips.setVisibility(View.VISIBLE);
                            }

                        }
                    });

        }

    }

    @OnClick({R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7, R.id.num8, R.id.num9})
    public void createVmId(View view) {
        Button button = (Button) view;
        vimIdInput.append(button.getText());
        checkInputStr(vimIdInput);
        etInput.setText(vimIdInput);
        Log.e(TAG, "当前选中的值:" + button.getText() + " append vm id :" + vimIdInput);

    }

    /**
     * 校验输入的值
     *
     * @param inputInfo
     */
    private boolean checkInputStr(StringBuilder inputInfo) {
        //75502237
        if (inputInfo.length() >= 5) {
            btnConfirm.setClickable(true);
            btnConfirm.setBackgroundResource(R.drawable.shape_confirm_bg);
            return true;
        } else if (inputInfo.length() > 10) {
            btnConfirm.setBackgroundResource(R.drawable.shape_confirm_bg);
            inputInfo.deleteCharAt(10);
            return true;
        } else {
            btnConfirm.setClickable(false);
            btnConfirm.setBackgroundResource(R.drawable.shape_confirm_bg_enabled);
            if (inputInfo.length() > 0) {
                etInput.setTextColor(Color.BLACK);
            } else {
                etInput.setTextColor(Color.parseColor("#CCCCCC"));
            }
            return false;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
