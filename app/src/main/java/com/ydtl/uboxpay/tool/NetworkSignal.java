package com.ydtl.uboxpay.tool;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by Nanxiaofeng on 2019/06/03.
 */
public class NetworkSignal {

    private String TAG = "[NetworkSignal]";

    private int dbm = 0;

    private volatile static NetworkSignal networkSignal;

    private NetworkSignal() {

    }

    public static NetworkSignal getInstance() {
        if (networkSignal == null) {
            synchronized (NetworkSignal.class) {
                if (networkSignal == null) {
                    networkSignal = new NetworkSignal();
                }
            }
        }
        return networkSignal;
    }

    public class NetInfo {
        public boolean connected;
        public int signal;
        public String type;
        public String ISP;
    }

    /**
     * 初始化监听信号强度，仅初始化一次即可
     *
     * @param context Application Context
     */
    public void init(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            tm.listen(mSignalListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        }
    }

    public NetInfo getNetworkInfo(Context context) {
        NetInfo network = new NetInfo();
        network.ISP = String.valueOf(NetworkUtils.getNetworkOperatorName(context));
        NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType(context);
        switch (networkType) {
            case NETWORK_NO:
                network.type = "NO";
                network.connected = false;
                network.signal = 0;
                break;
            case NETWORK_ETHERNET:
                network.type = "Ethernet";
                network.connected = true;
                network.signal = 4;
                break;
            case NETWORK_2G:
                network.type = "2G";
                network.connected = true;
                network.signal = getMobileSignal();
                break;
            case NETWORK_3G:
                network.type = "3G";
                network.connected = true;
                network.signal = getMobileSignal();
                break;
            case NETWORK_4G:
                network.type = "4G";
                network.connected = true;
                network.signal = getMobileSignal();
                break;
            case NETWORK_WIFI:
                network.type = "WiFi";
                network.connected = true;
                network.signal = getWifiSignal(context);
                break;
            case NETWORK_UNKNOWN:
                network.type = "Unknown";
                network.connected = true;
                network.signal = getMobileSignal();
                break;
        }
        return network;
    }

    private int getWifiSignal(Context context) {
        int signal = 0;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int wifi = wifiInfo.getRssi();
                if (wifi <= 0 && wifi >= -55) {
                    signal = 4;
                } else if (wifi < -55 && wifi >= -70) {
                    signal = 3;
                } else if (wifi < -70 && wifi >= -85) {
                    signal = 2;
                } else if (wifi < -85 && wifi >= -100) {
                    signal = 1;
                } else {
                    signal = 0;
                }
                Log.d(TAG, String.format("WiFi signal dbm: %s, level: %s", wifi, signal));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signal;
    }

    /**
     * 信号 (0)  —— (-55)dbm  4格(满格)
     * 信号(-55) —— (-70)dbm  3格
     * 信号(-70) —— (-85)dbm　2格
     * 信号(-85) —— (-100)dbm 1格信号
     */
    public int getMobileSignal() {
        int signal;
        if (dbm <= 0 && dbm >= -55) {
            signal = 4;
        } else if (dbm < -55 && dbm >= -70) {
            signal = 3;
        } else if (dbm < -70 && dbm >= -85) {
            signal = 2;
        } else if (dbm < -85 && dbm >= -100) {
            signal = 1;
        } else {
            signal = 0;
        }
        return signal;
    }

    private PhoneStateListener mSignalListener = new PhoneStateListener() {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            try {
                Method method = signalStrength.getClass().getMethod("getDbm");
                dbm = (int) method.invoke(signalStrength);
                Log.d(TAG, String.format("Mobile signal dbm: %s, level: %s", dbm, getMobileSignal()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

}
