package com.ydtl.uboxpay.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.ydtl.uboxpay.component.com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AndroidUtil {
    private static String defaultfilename = "config";
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static SimpleDateFormat dateFormat;
    private static final int MIN_DELAY_TIME= 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static void showToastShort(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 判断字符串是否为null
     *
     * @param str
     * @return null true
     */
    public static boolean isEmpty(String str) {
        if (null != str && !str.trim().equals("") && !"null".equals(str)) {
            return false;
        }
        return true;
    }


    public static int formatNum(String intStr, int defaultValue) {
        int value;
        if (TextUtils.isEmpty(intStr) || intStr.equals("") || intStr.equals("null"))
            return defaultValue;
        try {
            value = Integer.parseInt(intStr);
        } catch (Exception e) {
            value = defaultValue;
        }
        return value;
    }
    /**
     * 判断list是否为null
     *
     * @param list
     * @return null true
     */
    public static boolean isEmpty(List<?> list) {
        if (null != list && list.size() != 0) {
            return false;
        }
        return true;
    }

    /**
     * 日期转换为字符串格式
     *
     * @param date
     *            Date类型
     * @param sFormate
     *            : "yyyy-MM-dd HH:mm:ss"
     * @return 字符串
     */
    public static String getDateToString(Date date, String sFormate) {
        dateFormat = new SimpleDateFormat(sFormate);
        if (date == null) {
            return "";
        } else {
            return dateFormat.format(date);
        }
    }

    /**
     * 根据日期获取星期几
     *
     * @param "yyyy-MM-dd"
     * @return
     */
    public static String getWeek(Date date) {
        int i = getDayOfWeek(date);
        String week = getWeek(i, 0);
        return week;
    }

    /**
     * 获取当前日期是本周的第几天
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        return dayOfWeek;

    }

    // 将阿拉伯数字转换成中文或英文 status 0 表示中文 1表示英文
    public static String getWeek(int day_of_week, int status) {
        String week = "";
        switch (day_of_week) {
            case 0:
                week = status == 0 ? "星期日" : "Sunday";
                break;
            case 1:
                week = status == 0 ? "星期一" : "Monday";
                break;
            case 2:
                week = status == 0 ? "星期二" : "Tuesday";
                break;
            case 3:
                week = status == 0 ? "星期三" : "Wednesday";
                break;
            case 4:
                week = status == 0 ? "星期四" : "Thursday";
                break;
            case 5:
                week = status == 0 ? "星期五" : "Friday";
                break;
            case 6:
                week = status == 0 ? "星期六" : "Saturday";
                break;
        }
        return week;
    }

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    /**
     * DIP -> PX 转换
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager manage = context.getPackageManager();
            PackageInfo info = manage.getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static SharedPreferences getPreferencesInstance(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(defaultfilename, Context.MODE_PRIVATE);
        }
        return preferences;
    }

    /**
     * 获得参数
     *
     * @param context
     * @param key
     * @return
     */
    public static String getConfigValue(Context context, String key, String defvalue) {
        return getPreferencesInstance(context).getString(key, defvalue);
    }

    public static int getConfigValue(Context context, String key, int defvalue) {
        return Integer.parseInt(getConfigValue(context, key, Integer.toString(defvalue)));
    }

    public static long getConfigValue(Context context, String key, long defvalue) {
        return Integer.parseInt(getConfigValue(context, key, Long.toString(defvalue)));
    }

    public static boolean getConfigValue(Context context, String key, boolean defvalue) {
        return new Boolean(getConfigValue(context, key, Boolean.toString(defvalue)));
    }

    /**
     * 保存参数
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setConfigValue(Context context, String key, String value) {
        editor = getPreferencesInstance(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setConfigValue(Context context, String key, int value) {
        if(value > 10){
            value = 10;
        }
        setConfigValue(context, key, Integer.toString(value));
    }

    public static void setConfigValue(Context context, String key, long value) {
        setConfigValue(context, key, Long.toString(value));
    }

    public static void setConfigValue(Context context, String key, boolean value) {
        setConfigValue(context, key, Boolean.toString(value));
    }

    //获取全部纸巾数量
    public static int getAllShipmentNum(Context context){
        int allNum = 0;
        allNum = allNum + AndroidUtil.getConfigValue(context,"seller_num_1",0);
        allNum = allNum + AndroidUtil.getConfigValue(context,"seller_num_2",0);
        allNum = allNum + AndroidUtil.getConfigValue(context,"seller_num_3",0);
        allNum = allNum + AndroidUtil.getConfigValue(context,"seller_num_4",0);
        allNum = allNum + AndroidUtil.getConfigValue(context,"seller_num_5",0);
        allNum = allNum + AndroidUtil.getConfigValue(context,"seller_num_6",0);
        allNum = allNum + AndroidUtil.getConfigValue(context,"seller_num_7",0);
        allNum = allNum + AndroidUtil.getConfigValue(context,"seller_num_8",0);
        return allNum;
    }

    //获取有纸巾的通道1-8，如全无则为0
    public static int getShipmentMouth(Context context){
        int month = 0;
        if(AndroidUtil.getConfigValue(context,"seller_num_1",0) > 0){
            month = 1;
        }else if(AndroidUtil.getConfigValue(context,"seller_num_2",0) > 0){
            month = 2;
        }else if(AndroidUtil.getConfigValue(context,"seller_num_3",0) > 0){
            month = 3;
        }else if(AndroidUtil.getConfigValue(context,"seller_num_4",0) > 0){
            month = 4;
        }else if(AndroidUtil.getConfigValue(context,"seller_num_5",0) > 0){
            month = 5;
        }else if(AndroidUtil.getConfigValue(context,"seller_num_6",0) > 0){
            month = 6;
        }else if(AndroidUtil.getConfigValue(context,"seller_num_7",0) > 0){
            month = 7;
        }else if(AndroidUtil.getConfigValue(context,"seller_num_8",0) > 0){
            month = 8;
        }
        return month;
    }

    //byte[]转十六进制
    public static String bytesTohex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            boolean flag = false;
            if (b < 0) flag = true;
            int absB = Math.abs(b);
            if (flag) absB = absB | 0x80;
            System.out.println(absB & 0xFF);
            String tmp = Integer.toHexString(absB & 0xFF);
            if (tmp.length() == 1) { //转化的十六进制不足两位，需要补0
                hex.append("0");
            }
            hex.append(tmp.toLowerCase());
        }
        return hex.toString();
    }

    //将分转化为元
    public static String getNumDigits(float num){
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format(num/100);
    }

    //判断时间相差分钟数
    public static int getTimeDisparity(Date nowDate, String downDate){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long minutes = 0;
        try
        {
            Date d1 = df.parse(downDate);
            long diff = nowDate.getTime() - d1.getTime();//这样得到的差值是微秒级别

            minutes = diff/(1000* 60);
        }
        catch (Exception e)
        {
        }
        return (int)minutes;
    }

    /**
     * 自定义ProgressDialog 返回 dialog
     */
    public static KProgressHUD getKProgressDialog(Context context, String content) {
        KProgressHUD pro = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel(content)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setSize(180, 180)
                .setDimAmount(0.5f);
        return pro;
    }

    //分转化为金额，有小数保留
    public static String getAmountString(int price){
        String mString = "0";
        if(price >= 100){
            mString = ""+price/100;
        }
        price = price%100;
        if(price > 0){
            if(price%10 > 0){
                if(price/10 > 0) {
                    mString = mString + "." + price;
                }else if(AndroidUtil.isEmpty(mString)){
                    mString = "0.0" + price;
                }else{
                    mString = mString + ".0" + price;
                }
            }else{
                mString = mString + "." + price/10;
            }
        }
        LogUtils.e("getAmountString:"+mString);
        return mString;
    }

    public static String getAmountVoiceString(int price){
        String mString = "共消费";
        if(price >= 10000000){
            return "";
        }
        if(price >= 1000000){
            mString = mString + getLargeAmount(price/1000000) + "万";
        }
        if(price >= 100000){
            price = price%1000000;
            if(price/100000 > 0) {
                mString = mString + getLargeAmount(price / 100000) + "千";
            }
        }
        if(price >= 10000){
            price = price%100000;
            if(price/10000 > 0) {
                mString = mString + getLargeAmount(price / 10000) + "百";
            }
        }
        if(price >= 1000){
            price = price%10000;
            if(price/1000 > 0) {
                mString = mString + getLargeAmount(price / 1000) + "拾";
            }
        }
        if(price >= 100){
            price = price%1000;
            if(price/100 > 0) {
                mString = mString + getLargeAmount(price / 100) + "元";
            }else{
                mString = mString + "元";
            }
        }
        if(price >= 10){
            price = price%100;
            if(price/10 > 0) {
                mString = mString + getLargeAmount(price / 10) + "角";
            }
        }
        if(price >= 1){
            price = price%10;
            if(price > 0) {
                mString = mString + getLargeAmount(price) + "分";
            }
        }
        LogUtils.e("mString:"+mString);
        return mString;
    }

    public static String getLargeAmount(int num){
        String mString = "";
        switch (num){
            case 0:
                mString = "零";
                break;
            case 1:
                mString = "壹";
                break;
            case 2:
                mString = "贰";
                break;
            case 3:
                mString = "叁";
                break;
            case 4:
                mString = "肆";
                break;
            case 5:
                mString = "伍";
                break;
            case 6:
                mString = "六";
                break;
            case 7:
                mString = "柒";
                break;
            case 8:
                mString = "捌";
                break;
            case 9:
                mString = "玖";
                break;
        }
        return mString;
    }
}
