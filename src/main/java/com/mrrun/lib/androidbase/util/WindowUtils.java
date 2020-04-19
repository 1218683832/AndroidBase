package com.mrrun.lib.androidbase.util;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.Display;
import android.view.Surface;
import android.view.Window;
import android.view.WindowManager;

/**
 * <b>类功能描述:</b><br>
 * 窗口工具类
 * 
 * @author lipin
 * @version 1.0
 */
public final class WindowUtils {

	private WindowUtils() {
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取当前窗口的旋转角度<br>
	 * {@link Context#getSystemService(String)}<br>
	 * {@link WindowManager#getDefaultDisplay()}<br>
	 * {@link Display # getDisplayRotation(Context)}
	 * 
	 * @param context
	 *            {@link Context}
	 * @return int
	 * 
	 * @see Context
	 * @see Display
	 * @see WindowManager
	 */
	public static int getDisplayRotation(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int rotationAngle = windowManager.getDefaultDisplay().getRotation();
		switch (rotationAngle) {
		case Surface.ROTATION_0:
			return 0;
		case Surface.ROTATION_90:
			return 90;
		case Surface.ROTATION_180:
			return 180;
		case Surface.ROTATION_270:
			return 270;
		default:
			return 0;
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 当前是否是横屏
	 * 
	 * @param context
	 *            context
	 * @return boolean
	 */
	public static final boolean isLandscape(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 当前是否是竖屏
	 * 
	 * @param context
	 *            context
	 * @return boolean
	 */
	public static final boolean isPortrait(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 调整当前窗口的透明度 <br>
	 * 例如:frome=1.0f,to=0.5f当前窗口变暗
	 * 
	 * @param from
	 *            from>=0&&from<=1.0f
	 * @param to
	 *            to>=0&&to<=1.0f
	 * @param activity
	 *            当前的{@link Activity}
	 * @see Window
	 * @see ValueAnimator
	 */
	public static void adjustBackground(float from, float to, Activity activity) {
		final Window window = activity.getWindow();
		ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
		valueAnimator.setDuration(500);
		valueAnimator
				.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						WindowManager.LayoutParams params = window
								.getAttributes();
						params.alpha = (Float) animation.getAnimatedValue();
						window.setAttributes(params);
					}
				});
		valueAnimator.start();
	}
}