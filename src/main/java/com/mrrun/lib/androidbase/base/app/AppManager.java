package com.mrrun.lib.androidbase.base.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.mrrun.lib.androidbase.ablog.ABLog;

import java.util.Stack;

/**
 * <b>类功能描述:</b><br>
 * 这是一个Actiivty栈管理类,利用单例模式管理Activity,在Activity中使用它退出应用程序.<br>
 * 
 * <ol>
 * <b>用法:</b>
 * <li>获取Activity栈管理实例调用{@link AppManager#getAppManager()}方法
 * <li>在Activity的onCreate()方法中调用{@link #addActivity(Activity)}方法</li>
 * <li>只退出当前Activity时在onDestroy()中调用{@link #finishActivity(Activity)}</li>
 * <li>退出应用程序调用{@link #AppExit(Context)}</li>
 * <ol>
 * 
 * @author lipin
 * @version 1.1
 */
public class AppManager {

	protected static final String TAG = "AppManager";

	private static Stack<Activity> activityStack = new Stack<Activity>();

	private static AppManager instance;

	private AppManager() {
		ABLog.i(TAG, "Do not need instantiate!");
	}

	private static AppManager newInstance() {
		return new AppManager();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 静态方法<br>
	 * 单例模式中获取唯一的{@link AppManager}实例.
	 * 
	 * @return instance {@link AppManager}的实例
	 */
	public static AppManager getAppManager() {
		ABLog.i(TAG, "getAppManager Instance");
		if (null == instance) {
			synchronized (AppManager.class) {
				if (null == instance) {
					instance = newInstance();
				}
			}
		}
		ABLog.i(TAG, "instance=" + instance);
		return instance;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 添加Activity到容器中.
	 * 
	 * @param activity
	 * @return 无返回值
	 */
	public void addActivity(Activity activity) {
		ABLog.i(TAG, "addActivity");
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 结束所有Activity
	 *
	 * @return 无返回值
	 */
	public void finishAllActivity() {
		ABLog.i(TAG, "exitAll");
		if (activityStack == null){
			return;
		}
		for (Activity activity : activityStack) {
			if (null != activity) {
				activity.finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 结束指定的Activity
	 * 
	 * @param activity
	 * @return 无返回值
	 */
	public void finishActivity(Activity activity) {
		ABLog.i(TAG, "exit");
		if (activity != null) {
			activityStack.remove(activity);
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 结束指定类名的Activity
	 *
	 * @param cls
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}
	}
}