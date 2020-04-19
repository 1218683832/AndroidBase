package com.mrrun.lib.androidbase.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtils {

	/**
	 * <b>类功能描述:</b><br>
	 * 网络工具类
	 * 
	 * @author lipin
	 * @version 1.0
	 */
	private NetWorkUtils() {
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断网络是否可用
	 * 
	 * @param context
	 * @return <li>true，网络可用</li><li>false，网络不可用</li>
	 */
	public static boolean isNetAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return null != networkInfo && networkInfo.isConnected();
    }

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取当前网络类型名称
	 * 
	 * @param context
	 * @return <li>WIFI，WiFi网络</li><li>MOBILE，手机网络</li><li>null，未获取到网络类型</li>
	 */
	public static String getNetworkTypeName(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.getTypeName();
		}
		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断手机网络是否可用
	 * 
	 * @return <li>true，手机网络可用</li><li>false，手机网络不可用</li>
	 */
	@SuppressWarnings("deprecation")
	public static boolean isMobileAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileNetworkInfo = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetworkInfo != null) {
			return mobileNetworkInfo.isAvailable();
		}
		return false;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断WiFi网络是否可用
	 * 
	 * @param context
	 * @return <li>true，WiFi网络可用</li><li>false，WiFi网络不可用</li>
	 */
	@SuppressWarnings("deprecation")
	public static boolean isWiFiAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetworkInfo != null) {
			return wifiNetworkInfo.isAvailable();
		}
		return false;
	}
}
