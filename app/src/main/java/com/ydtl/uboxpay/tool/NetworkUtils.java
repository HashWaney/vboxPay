package com.ydtl.uboxpay.tool;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Nanxiaofeng on 2019/06/01.
 */
public class NetworkUtils {

    private static String TAG = "[NetworkUtils]";

    private NetworkUtils() {
        throw new UnsupportedOperationException(String.format("Can not be instantiated %s class", TAG));
    }

    public enum NetworkType {
        NETWORK_NO,
        NETWORK_ETHERNET,
        NETWORK_2G,
        NETWORK_3G,
        NETWORK_4G,
        NETWORK_WIFI,
        NETWORK_UNKNOWN
    }

    public static void openWirelessSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(android.provider.Settings.ACTION_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm == null ? null : cm.getActiveNetworkInfo();
    }

    public static boolean isAvailable(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    public static boolean isConnected(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isConnected();
    }

    public static boolean getWifiEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null)
            throw new NullPointerException("WiFi Service is null ?");
        return wifiManager.isWifiEnabled();
    }

    public static void setWifiEnabled(Context context, boolean enabled) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null)
            return;
        if (enabled) {
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
        } else {
            if (wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(false);
            }
        }
    }

    public static NetworkType getNetworkType(Context context) {
        NetworkType networkType = NetworkType.NETWORK_NO;
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager == null)
                return networkType;
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                    networkType = NetworkType.NETWORK_ETHERNET;
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    networkType = NetworkType.NETWORK_WIFI;
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    String subtypeName = networkInfo.getSubtypeName();
                    int subType = networkInfo.getSubtype();
                    switch (subType) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:        //1: 2G(2.5) General Packet Radia Service 114kbps
                        case TelephonyManager.NETWORK_TYPE_EDGE:        //2: 2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
                        case TelephonyManager.NETWORK_TYPE_CDMA:        //4: 2G 电信 Code Division Multiple Access 码分多址
                        case TelephonyManager.NETWORK_TYPE_1xRTT:       //7: 2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡
                        case TelephonyManager.NETWORK_TYPE_IDEN:        //11:2G Integrated Dispatch Enhanced Networks 集成数字增强型网络
                            networkType = NetworkType.NETWORK_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:        //3: 3G WCDMA 联通3G Universal MOBILE Telecommunication System 完整的3G移动通信技术标准
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:      //5: 3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:      //6: 3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
                        case TelephonyManager.NETWORK_TYPE_HSDPA:       //8: 3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
                        case TelephonyManager.NETWORK_TYPE_HSUPA:       //9: 3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
                        case TelephonyManager.NETWORK_TYPE_HSPA:        //10:3G(分HSDPA,HSUPA) High Speed Packet Access
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:      //12:3G EV-DO Rev.B 14.7Mbps 下行 3.5G
                        case TelephonyManager.NETWORK_TYPE_EHRPD:       //14:3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
                        case TelephonyManager.NETWORK_TYPE_HSPAP:       //15:3G HSPAP 比 HSDPA 快些
                        case TelephonyManager.NETWORK_TYPE_TD_SCDMA:    //17:3G Time Division - Synchronous CDMA（中国自有3G技术,移动3G的过度）
                            networkType = NetworkType.NETWORK_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:         //13:4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
                        case TelephonyManager.NETWORK_TYPE_IWLAN:
                            networkType = NetworkType.NETWORK_4G;
                            break;
                        default:
                            if (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) {
                                networkType = NetworkType.NETWORK_3G;
                            } else {
                                networkType = NetworkType.NETWORK_UNKNOWN;
                            }
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, String.format("Network type: %s", networkType));
        return networkType;
    }

    public static String getNetworkOperatorName(Context context) {
        String operatorName = "未知网络";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null) {
                return operatorName;
            }
            String operator = tm.getNetworkOperator();
            if (operator == null || operator.equals("")) {
                operatorName = tm.getNetworkOperatorName();
            } else if (operator.equals("46000") || operator.equals("46002") || operator.equals("46004") || operator.equals("46007")) {
                operatorName = "中国移动";
            } else if (operator.equals("46001") || operator.equals("46006") || operator.equals("46009") || operator.equals("46010")) {
                operatorName = "中国联通";
            } else if (operator.equals("46003") || operator.equals("46005") || operator.equals("46011")) {
                operatorName = "中国电信";
            } else {
                operatorName = operator;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operatorName;
    }

    public static String getPhoneIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm == null ? null : tm.getSubscriberId();
    }

    public static String getPhoneIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm == null ? null : tm.getDeviceId();
    }

    public static String getSimSerialNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm == null ? null : tm.getSimSerialNumber();
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements(); ) {
                NetworkInterface ni = nis.nextElement();
                // 防止返回10.0.2.15
                if (!ni.isUp()) continue;
                for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4) return hostAddress;
                        } else {
                            if (!isIPv4) {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getIPAddressByDomain(final String domain) {
        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            Future<String> fs = exec.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    InetAddress inetAddress;
                    try {
                        inetAddress = InetAddress.getByName(domain);
                        return inetAddress.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            return fs.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}


