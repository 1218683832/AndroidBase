package com.mrrun.lib.androidbase.ablog;

import java.io.File;

/** 
 * <b>类功能描述:</b><br> 
 * 这个类使用在继承自{@link com.mrrun.lib.androidbase.base.app.BaseApp}中,(1)用于Log输出开关控制;(2)用户于打印各种格式的Log信息.<br>
 * @see com.mrrun.lib.androidbase.base.app.BaseApp
 * @author lipin 
 * @version 1.0 
 */
import com.socks.library.KLog;

/**
 * <b>类功能描述:</b><br>
 * Log控制类
 * 
 * @author lipin
 * @version 1.0
 * @see KLog
 */
public class ABLog {

	/**
	 * 默认TAG标签,不可更改
	 */
	protected static final String TAG_DEFAULT = "ABLog";

	/**
	 * 全局TAG标签,可更改
	 */
	private static String mGlobalTag;

	/**
	 * <b>方法功能描述:</b><br>
	 * 静态方法<br>
	 * 初始化Log输出工具,并设置是否打开Log输出开关.
	 * 
	 * @param isShowLog
	 *            boolean类型,是否打开Log输出开关
	 * @return 无返回值
	 */
	public static void init(boolean isShowLog) {
		KLog.init(isShowLog);
		if (isShowLog) {
			KLog.i(TAG_DEFAULT, "ABLog 已打开.");
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 静态方法<br>
	 * 初始化Log输出工具,并设置是否打开Log输出开关和全局TAG标签.
	 * 
	 * @param isShowLog
	 *            boolean类型,是否打开Log输出开关
	 * @param tag
	 *            String类型
	 * @return 无返回值
	 */
	public static void init(boolean isShowLog, String tag) {
		ABLog.mGlobalTag = tag;
		KLog.init(isShowLog, ABLog.mGlobalTag);
		if (isShowLog) {
			KLog.i(TAG_DEFAULT, "ABLog 已打开." + "全局TAG标签为:" + mGlobalTag);
		}
	}

	public static void v() {
		KLog.v();
	}

	public static void v(Object msg) {
		KLog.v(msg);
	}

	public static void v(String tag, Object... objects) {
		KLog.v(tag, objects);
	}

	public static void d() {
		KLog.d();
	}

	public static void d(Object msg) {
		KLog.d(msg);
	}

	public static void d(String tag, Object... objects) {
		KLog.d(tag, objects);
	}

	public static void i() {
		KLog.i();
	}

	public static void i(Object msg) {
		KLog.i(msg);
	}

	public static void i(String tag, Object... objects) {
		KLog.i(tag, objects);
	}

	public static void w() {
		KLog.w();
	}

	public static void w(Object msg) {
		KLog.w(msg);
	}

	public static void w(String tag, Object... objects) {
		KLog.w(tag, objects);
	}

	public static void e() {
		KLog.e();
	}

	public static void e(Object msg) {
		KLog.e(msg);
	}

	public static void e(String tag, Object... objects) {
		KLog.e(tag, objects);
	}

	public static void a() {
		KLog.a();
	}

	public static void a(Object msg) {
		KLog.a(msg);
	}

	public static void a(String tag, Object... objects) {
		KLog.a(tag, objects);
	}

	public static void json(String jsonFormat) {
		KLog.json(jsonFormat);
	}

	public static void json(String tag, String jsonFormat) {
		KLog.json(tag, jsonFormat);
	}

	public static void xml(String xml) {
		KLog.xml(xml);
	}

	public static void xml(String tag, String xml) {
		KLog.xml(tag, xml);
	}

	public static void file(File targetDirectory, Object msg) {
		KLog.file(targetDirectory, msg);
	}

	public static void file(String tag, File targetDirectory, Object msg) {
		KLog.file(tag, targetDirectory, msg);
	}

	public static void file(String tag, File targetDirectory, String fileName,
			Object msg) {
		KLog.file(tag, targetDirectory, fileName, msg);
	}
}