package com.mrrun.lib.androidbase.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * <b>类功能描述:</b><br>
 * WiFi工具类
 * 
 * @author lipin
 * @version 1.0
 */
public class WiFiUtils {

	private WiFiUtils() {
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断是否已连接WIFI
	 * 
	 * @param context
	 *            上下文
	 * @return boolean 如果已连接wifi返回true,否则返回false
	 */
	@SuppressWarnings("deprecation")
	public static boolean isWiFiConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null == cm) {
			return false;
		}
		NetworkInfo wifiNetworkInfo = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return null != wifiNetworkInfo && wifiNetworkInfo.isConnected();
    }

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取WiFi下的Mac地址
	 * 
	 * @param context
	 * @return String value or null
	 */
	public static String getMacAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.getConnectionInfo().getMacAddress();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断是wifi还是其他网络<br>
	 * 用户的体现性在这里了，提醒用户wifi下建议下载或者在线播放
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWiFi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null == cm) {
			return false;
		}
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        return networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断WiFi是否打开
	 * 
	 * @param context
	 * @return <li>true, WiFi已打开</li><li>false, WiFi未打开</li>
	 * 
	 */
	public static boolean isWiFiOpened(Context context) {
		WifiManager wifimanager;
		wifimanager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
        return null != wifimanager && wifimanager.isWifiEnabled();
    }

	/**
	 * <b>方法功能描述:</b><br>
	 * 打开WiFi
	 * 
	 * @param context
	 * @return <li>true，WiFi打开成功</li><li>false，WiFI打开失败</li>
	 */
	public static boolean openWiFi(Context context) {
		if (isWiFiOpened(context)) {
			return true;
		}
		WifiManager wifimanager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (null == wifimanager) {
			return false;
		}
		return wifimanager.setWifiEnabled(true);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 关闭WiFi
	 * 
	 * @param context
	 * @return <li>true，WiFi关闭成功</li><li>false，WiFI关闭失败</li>
	 */
	public static boolean closeWiFi(Context context) {
		if (!isWiFiOpened(context)) {
			return true;
		}
		WifiManager wifimanager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (null == wifimanager) {
			return false;
		}
		return wifimanager.setWifiEnabled(false);
	}
}