package com.ydtl.uboxpay.component;

public class Constant {
    public static String web_url = "http://39.107.122.82/soupshop/a/api/";//正式
    public static String img_url = "http://39.107.122.82/";
    public static String UBOX_BASE = "http://uboxapi.ubox.cn/opentrade/";//正式
//    http://uboxapi.dev.uboxol.com/opentrade/orderCode
    //http://uboxapi.ubox.cn/
//    public static String web_url="http://39.107.90.26/soupshop/a/api/";//测试
//    public static String img_url="http://39.107.90.26/";
//    public static String web_url="http://192.168.0.243:8080/soupshop/a/api/";//本地
//    public static String img_url="http://192.168.0.243/";//本地

    public final static int TH_SUCC = 10000; // 请求成功
    public final static int TH_FAILD = 10001; // 请求失败
    public final static int TH_EMPTY = 10002; // 暂无数据
    public final static int TH_INTERFACE_FAILED = 0; // 接口响应失败
    public final static String IS_SET_UP_VM_ID = "setup_vm_id"; //是否设置了机器号
    public final static String GET_VM_ID = "get_vm_id";
    public final static String APP_ID = "b525319379eb9679c83236b30c06632b"; //openapi 生成id
    public final static String APP_KEY = "67331c20d1cde6128723f4043f502d60"; //openapi 生成key
    public static String PRODUCT_LIST_URL = UBOX_BASE + "productList";//商品列表
    public static String ORDER_URL = UBOX_BASE + "orderCode";//下单接口

    public static final String PRODUCT_DETAIL_INFO="product_detail_info";
}
