package com.ydtl.uboxpay.activity.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.hash.lib.ui.utils.NetUtil;
import com.hash.lib.ui.utils.PathUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpParams;
import com.ydtl.uboxpay.bean.ShopData;
import com.ydtl.uboxpay.tool.LogUtils;
import com.ydtl.uboxpay.tool.NetworkSignal;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class MainApplication extends Application {

    public static Context sContext;

    private static MainApplication mInstance;
    public static ArrayList<Activity> activityStack = new ArrayList<Activity>();
    private static String deviceId;
    public volatile ShopData shopData = new ShopData();

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        mInstance = this;
        deviceId = getSn(sContext);
        shopData.setDeviceId(deviceId);
        initOKGo();
        PathUtil.init(this);
        NetUtil.init(this);
        NetworkSignal.getInstance().init(this);
    }

    @SuppressLint("MissingPermission")
    public String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(context.TELEPHONY_SERVICE);
        String imei = "";
        if (telephonyManager.getDeviceId() != null) {
//            imei = telephonyManager.getDeviceId();
            imei = android.os.Build.SERIAL;
        } else {
            //android.provider.Settings;
            imei = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        LogUtils.e("imei: " + imei);
        return imei;
    }

    public String getSn(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(context.TELEPHONY_SERVICE);
        String sn = android.os.Build.SERIAL;
        LogUtils.e("sn: " + sn);
        return sn;
    }

    public static String getCPUSerial() {
        String str = "", strCPU = "", cpuAddress = "0000000000000000";
        try {
            //读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            //查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    //查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        //提取序列号
                        strCPU = str.substring(str.indexOf(":") + 1,
                                str.length());
                        //去空格
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    //文件结尾
                    break;
                }
            }
        } catch (Exception ex) {
            //赋予默认值
            ex.printStackTrace();
        }
        LogUtils.e("cpuAddress: " + cpuAddress);
        return cpuAddress;
    }

    /**
     * 初始化okgo
     */
    private void initOKGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.SEVERE);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //超时时间设置，默认60秒
        builder.readTimeout(60000, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(60000, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(60000, TimeUnit.MILLISECONDS);   //全局的连接超时时间
//        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .addCommonParams(new HttpParams("code", deviceId))
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(1);                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }

    /**
     * 单一实例
     */
    public synchronized static MainApplication getInstance() {
        if (null == mInstance) {
            mInstance = new MainApplication();
        }
        return mInstance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new ArrayList<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 移除Activity
     */
    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }


    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getActivity() {
        if (activityStack == null) {
            return null;
        }
        return activityStack.get(activityStack.size() - 1);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
