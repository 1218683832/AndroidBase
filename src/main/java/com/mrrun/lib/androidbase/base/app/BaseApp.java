package com.mrrun.lib.androidbase.base.app;

import com.mrrun.lib.androidbase.ablog.ABLog;

import android.app.Application;
import android.content.Context;

/**
 * <b>类功能描述:</b><br>
 * 这个类继承自{@link Application}.<br>
 *
 * @author lipin
 * @version 1.0
 * 
 * @see Application
 */
public abstract class BaseApp extends Application {

	/**
	 * <b>属性功能描述:</b><br>
	 * 公有静态变量，应用中的全局{@link Context}，可替代Activity中的{@link Context}使用。<br>
	 * 直接使用即可
	 */
	public static Context appContext;

	/**
	 * <b>方法功能描述:</b><br>
	 * 抽象方法<br>
	 * 第三方的设置、数据库的加载等，具体可以根据项目需求进行定制 你只需要根据项目需求进行定制,覆盖该方法即可
	 * 
	 * @return 无返回值
	 */
	public abstract void initConfings();

	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
		// 是否打开Log:true-打开,false-关闭
		ABLog.init(true);
		initConfings();
	}
}