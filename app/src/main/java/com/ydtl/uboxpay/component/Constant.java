package com.ydtl.uboxpay.component;

public class Constant {
    public static String web_url="http://39.107.122.82/soupshop/a/api/";//正式
    public static String img_url="http://39.107.122.82/";
//    public static String web_url="http://39.107.90.26/soupshop/a/api/";//测试
//    public static String img_url="http://39.107.90.26/";
//    public static String web_url="http://192.168.0.243:8080/soupshop/a/api/";//本地
//    public static String img_url="http://192.168.0.243/";//本地

    public final static int TH_SUCC = 10000; // 请求成功
    public final static int TH_FAILD = 10001; // 请求失败
    public final static int TH_EMPTY = 10002; // 暂无数据
    public final static int TH_INTERFACE_FAILED = 0; // 接口响应失败
    public final static String SETUP_VM_ID="setup_vm_id"; //是否设置了机器号
}
