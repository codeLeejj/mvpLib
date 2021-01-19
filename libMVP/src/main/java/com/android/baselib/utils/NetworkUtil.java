package com.android.baselib.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

/**
 * 需要的用户权限：
 * android.permission.ACCESS_NETWORK_STATE
 * android.permission.CHANGE_NETWORK_STATE
 * android.permission.ACCESS_WIFI_STATE
 * android.permission.CHANGE_WIFI_STATE
 * Created by zhangdianle on 2015/10/20.
 */
public class NetworkUtil {

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    /**
     * 获取网络类型描述
     *
     * @param context
     * @return
     */
    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    /**
     * wifi开关
     *
     * @param context
     * @param enabled
     */
    public static void toggleWifi(Context context, boolean enabled) {
        WifiManager wifiMng = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (enabled && !wifiMng.isWifiEnabled()) {
            wifiMng.setWifiEnabled(true);
        } else if (!enabled && wifiMng.isWifiEnabled()) {
            wifiMng.setWifiEnabled(false);
        }
    }

    /**
     * 移动网络开关(由于系统没有直接提供开放的设置数据网络的接口，所以只能通过反射来调用)
     *
     * @param context
     * @param enabled
     */
    public static void toggleMobileDate(Context context, boolean enabled) {
        ConnectivityManager connMng = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Method method = connMng.getClass().getMethod("setMobileDataEnabled", new Class[]{boolean.class});
            method.invoke(connMng, enabled);
        } catch (Exception e) {

        }
    }

}

