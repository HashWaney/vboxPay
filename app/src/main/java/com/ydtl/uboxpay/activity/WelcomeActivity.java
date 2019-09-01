package com.ydtl.uboxpay.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;
import com.ydtl.uboxpay.R;
import com.ydtl.uboxpay.activity.base.BaseActivity;
import com.ydtl.uboxpay.activity.base.MainApplication;
import com.ydtl.uboxpay.activity.ubox.MainActivity;
import com.ydtl.uboxpay.activity.ubox.UBoxSetUpVmIdActivity;
import com.ydtl.uboxpay.bean.ShopBean;
import com.ydtl.uboxpay.bean.ShopSortBean;
import com.ydtl.uboxpay.component.Constant;
import com.ydtl.uboxpay.component.callback;
import com.ydtl.uboxpay.tool.AndroidUtil;
import com.ydtl.uboxpay.tool.LogUtils;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class WelcomeActivity extends BaseActivity {

    private Message message;
    private boolean isStop = false;
    private MainApplication mainApplication;
    private boolean isInit = false; //判断初始化是否结束
    private boolean isDown = false; //判断倒计时是否结束
    private List<ShopBean> shopBeans;
    private List<ShopSortBean> shopSortBeans;
    private int shopType = 1;

    @Override
    public void setCustomLayout() {
        super.setCustomLayout();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_info);
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void initData() {
        super.initData();
        isInit = false;
        isDown = false;
        initDownTime();
        getUpData();
    }

    @Override
    public void BindComponentEvent() {
        super.BindComponentEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStop = true;
        OkGo.getInstance().cancelTag(this);
    }

    //获取商户信息
//    private void getShopData(){
//        OkGo.<String>post(Constant.web_url+"shop/goodsQuery")
//                .tag(this)
//                .params("equiment_code", shopData.getDeviceId())
//                .execute(new callback() {
//                    @Override
//                    public void onresponse(String sJson) {
//                        JSONObject jsonob = JSONObject.parseObject(sJson);
//                        Boolean success = jsonob.getBoolean("success");
//                        String msg = jsonob.getString("msg");
//                        String body = jsonob.getString("body");
//                        if(success && !AndroidUtil.isEmpty(body)) {
//                            JSONObject jsonBody = JSONObject.parseObject(body);
//                            shopType = jsonBody.getIntValue("type");
//                            String list = jsonBody.getString("list");
//                            if (!AndroidUtil.isEmpty(list)){
//                                if(shopType == 2) {
//                                    List<ShopSortBean> sortBeans = JSONObject.parseArray(list, ShopSortBean.class);
//                                    if (sortBeans != null) {
//                                        shopSortBeans = new ArrayList<>();
//                                        for(int i = 0; i < sortBeans.size(); i++){
//                                            if(sortBeans.get(i).getGoodslist() != null && sortBeans.get(i).getGoodslist().size() > 0){
//                                                shopSortBeans.add(sortBeans.get(i));
//                                            }
//                                        }
//                                        if(shopSortBeans != null && shopSortBeans.size() > 0) {
//                                            isInit = true;
//                                            startMainActivity();
//                                        }else {
//                                            message = splashHandler.obtainMessage(6, "商户分类未添加商品，请联系商户添加商品");
//                                            splashHandler.sendMessage(message);
//                                        }
//                                    } else {
//                                        message = splashHandler.obtainMessage(6, "商户未添加商品，请联系商户添加商品");
//                                        splashHandler.sendMessage(message);
//                                    }
//                                }else{
//                                    shopBeans = JSONObject.parseArray(list, ShopBean.class);
//                                    if (shopBeans != null && shopBeans.size() > 0) {
//                                        isInit = true;
//                                        startMainActivity();
//                                    } else {
//                                        message = splashHandler.obtainMessage(6, "商户未添加商品，请联系商户添加商品");
//                                        splashHandler.sendMessage(message);
//                                    }
//                                }
//                            }else {
//                                message = splashHandler.obtainMessage(6, ""+msg);
//                                splashHandler.sendMessage(message);
//                            }
//                        }else{
//                            message = splashHandler.obtainMessage(6, ""+msg);
//                            splashHandler.sendMessage(message);
//                        }
//                    }
//
//                    @Override
//                    public void onfailure(Response<String> response, int rtnCode) {
//                        message = splashHandler.obtainMessage(6, "请求商品信息失败,请检查网络配置后重试!");
//                        splashHandler.sendMessage(message);
//                    }
//                });
//    }

    //获取更新信息
    private void getUpData() {
        OkGo.<String>post(Constant.web_url + "shop/equimentVersion")
                .tag(this)
                .params("equiment_code", shopData.getDeviceId())
                .execute(new callback() {
                    @Override
                    public void onresponse(String sJson) {
                        JSONObject jsonob = JSONObject.parseObject(sJson);
                        Boolean success = jsonob.getBoolean("success");
                        String msg = jsonob.getString("msg");
                        String body = jsonob.getString("body");
                        if (success && !AndroidUtil.isEmpty(body)) {
                            JSONObject jsonBody = JSONObject.parseObject(body);
                            String appversion = jsonBody.getString("appversion");
                            JSONObject appObject = JSONObject.parseObject(appversion);
                            int newCode = appObject.getIntValue("version");
                            String url = appObject.getString("url");
                            int nowCode = AndroidUtil.getAppVersionCode(_context);
                            if (nowCode < newCode) {
                                downUpApk(url);
                            } else {
                                isInit = true;
                                startMainActivity();
                            }
                        } else {
                            message = splashHandler.obtainMessage(6, "" + msg);
                            splashHandler.sendMessage(message);
                        }
                    }

                    @Override
                    public void onfailure(Response<String> response, int rtnCode) {
                        message = splashHandler.obtainMessage(6, "请求更新软件失败,请检查网络配置后重试!");
                        splashHandler.sendMessage(message);
                    }
                });
    }

    private void downUpApk(String url) {
        if (AndroidUtil.isEmpty(url)) {
            message = splashHandler.obtainMessage(6, "更新地址请求失败!");
            splashHandler.sendMessage(message);
            return;
        }
        getUpDialog(_context);
//        rl_up_dialog.setVisibility(View.VISIBLE);
        try {
            OkGo.<File>get(url)
                    .tag(this)
                    .execute(new FileCallback() {
                        @Override
                        public void onSuccess(Response<File> response) {
                            if (response.body().exists()) {
                                try {
                                    File outputFile = new File(response.body().getAbsolutePath());
                                    if (!outputFile.exists()) {
                                        LogUtils.e("安装更新文件失败");
                                        message = splashHandler.obtainMessage(6, "安装更新文件失败，请退出重试!");
                                        splashHandler.sendMessage(message);
                                        return;
                                    }
                                    Intent i = new Intent(Intent.ACTION_MAIN);
                                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("file", outputFile);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.putExtras(bundle);
                                    i.setClass(_context, InstallPackageActivity.class);
                                    _context.startActivity(i);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    LogUtils.e("安装更新文件失败");
                                    message = splashHandler.obtainMessage(6, "安装更新文件失败，请退出重试");
                                    splashHandler.sendMessage(message);
                                }
                            }
                        }

                        @Override
                        public void onError(Response<File> response) {
                            super.onError(response);
                            LogUtils.e("下载更新文件失败");
                            message = splashHandler.obtainMessage(6, "下载更新文件失败，请退出重试");
                            splashHandler.sendMessage(message);
                        }

                    });
        } catch (Exception e) {
            LogUtils.e("下载更新文件失败!");
            message = splashHandler.obtainMessage(6, "下载更新文件失败，请退出重试!");
            splashHandler.sendMessage(message);
        }
    }


    private Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isStop)
                return;
            switch (msg.what) {
                case 1:
                    getUpDialog(_context);
                    break;
                case 6:
                    String mString = (String) msg.obj;
                    if (AndroidUtil.isEmpty(mString)) {
                        getDialog(_context, "服务器连接失败，请稍后重试");
                    } else {
                        getDialog(_context, "" + mString);
                    }
                    break;
                case 7:
                    getUpData();
                    break;
            }
        }
    };

    private void initDownTime() {
        new CountDownTimer(3 * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
//                getUpDialog(context);
                isDown = true;
                startMainActivity();
            }
        }.start();
    }

    private void startMainActivity() {
        if (isDown && isInit) {
            boolean isSetUp = AndroidUtil.getConfigValue(this, Constant.IS_SET_UP_VM_ID, false);
            if (isSetUp) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.setClass(this, MainActivity.class);
                bundle.putSerializable("shopBean", (Serializable) shopBeans);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            } else {
                //未设置机器号
                Intent intent = new Intent(this, UBoxSetUpVmIdActivity.class);
                startActivity(intent);
                finish();

            }

        }
    }

    public Dialog getUpDialog(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Dialog dialog = new Dialog(context, R.style.custom_window_dialog);
        View layout = inflater.inflate(R.layout.splash_up_dialog, null);

        dialog.setCanceledOnTouchOutside(false);

        dialog.addContentView(layout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.setContentView(layout);
        dialog.show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 1 / 2); // 设置宽度
        lp.height = (int) (display.getHeight() * 1 / 3); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public Dialog getDialog(Context context, String text_data) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Dialog dialog = new Dialog(context, R.style.custom_window_dialog);
        View layout = inflater.inflate(R.layout.splash_warr_dialog, null);

        TextView tvText = (TextView) layout.findViewById(R.id.splash_warr_text);
        TextView btn1 = (TextView) layout.findViewById(R.id.splash_warr_btn_1);
        TextView btn2 = (TextView) layout.findViewById(R.id.splash_warr_btn_2);

        dialog.setCanceledOnTouchOutside(false);


        tvText.setText(text_data);
        btn1.setText("重新连接");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splashHandler.sendEmptyMessage(7);
                dialog.dismiss();
            }
        });

        dialog.addContentView(layout, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.setContentView(layout);
        dialog.show();
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 6 / 11); // 设置宽度
        lp.height = (int) (display.getHeight() * 1 / 3); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }
}
