package com.mrrun.lib.androidbase.util;

import android.content.Context;
import android.widget.Toast;

/**
 * <b>类功能描述:</b><br>
 * Toast工具类<br>
 * 避免Toast多次以及延长出现
 * 
 * @author lipin
 * @version 1.0
 * 
 */
public class ToastUtils {

	public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;

	public static final int LENGTH_LONG = Toast.LENGTH_LONG;

	private static Toast mToast = null;

	private ToastUtils() {
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 静态方法<br>
	 * 
	 * @param mContext
	 *            Activity上下文
	 * @param text
	 *            Toast文本
	 * @return 无返回值
	 */
	public static void showToast(Context mContext, String text) {
		if (mToast != null) {
			mToast.setText(text);
		} else {
			mToast = Toast.makeText(mContext, text, ToastUtils.LENGTH_SHORT);
		}
		mToast.show();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 静态方法<br>
	 * 
	 * @param mContext
	 *            Activity上下文
	 * @param resId
	 *            Toast文本Id
	 * @return 无返回值
	 */
	public static void showToast(Context mContext, int resId) {
		showToast(mContext, mContext.getResources().getString(resId));
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 静态方法<br>
	 * 
	 * @param mContext
	 *            Activity上下文
	 * @param text
	 *            Toast文本
	 * @param duration
	 *            时长
	 * @return 无返回值
	 */
	public static void showToast(Context mContext, String text, int duration) {
		if (mToast != null) {
			mToast.setText(text);
		} else {
			mToast = Toast.makeText(mContext, text, duration);
		}
		mToast.show();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 静态方法<br>
	 * 
	 * @param mContext
	 *            Activity上下文
	 * @param resId
	 *            Toast文本Id
	 * @param duration
	 *            时长
	 * @return 无返回值
	 */
	public static void showToast(Context mContext, int resId, int duration) {
		showToast(mContext, mContext.getResources().getString(resId), duration);
	}
}