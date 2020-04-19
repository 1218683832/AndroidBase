package com.mrrun.lib.androidbase.util;

import android.text.TextUtils;

/**
 * 
 * <br>
 * 类描述:本地封装的文本工具 <br>
 * 功能详细描述:
 */
public class LocalTextUtil {

	public static boolean isNoBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isBlank(String str) {
		return TextUtils.isEmpty(str) || "null".equals(str);
	}

	public static String getShortStr(String originalStr, int num) {
		String destStr = null;
		if (originalStr == null) {
			return null;
		} else if (originalStr.isEmpty() || num == 0) {
			return "";
		} else if (originalStr.length() <= num) {
			return originalStr;
		} else {
			destStr = originalStr.substring(0, num);
			destStr += "...";
		}
		
    	return destStr;
    }
}
