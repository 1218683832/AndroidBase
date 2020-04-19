package com.mrrun.lib.androidbase.util;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.security.auth.x500.X500Principal;

/**
 * <b>类功能描述:</b><br>
 * APP工具类 <br>
 * 获取关于本APP的相关信息。
 * 
 * @author lipin
 * @version 1.0
 */
public final class AppUtils {

	private final static X500Principal DEBUG_DN = new X500Principal(
			"CN=Android Debug,O=Android,C=US");

	private AppUtils() {
	}

	/**
	 * 获取元数据值
	 * @param metaKey
	 */
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return apiKey;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 得到软件版本号
	 * 
	 * @param context
	 *            上下文
	 * @return 当前版本Code <li>未获得-1</li> <li>获得versionCode</li>
	 */
	public static int getVersionCode(Context context) {
		int verCode = -1;
		try {
			String packageName = context.getPackageName();
			verCode = context.getPackageManager()
					.getPackageInfo(packageName, 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 得到软件版本名称
	 *
	 * @param context
	 *            上下文
	 * @return 当前版本名称 <li>未获得""</li> <li>获得versionName</li>
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			String packageName = context.getPackageName();
			verName = context.getPackageManager()
					.getPackageInfo(packageName, 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取应用运行的最大内存
	 * 
	 * @return 最大内存
	 */
	public static long getMaxMemory() {
		return Runtime.getRuntime().maxMemory() / 1024;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取应用签名
	 * 
	 * @param context
	 *            上下文
	 * @param pkgName
	 *            包名
	 * @return 返回应用的签名，否则返回null
	 */
	public static String getSignature(Context context, String pkgName) {
		try {
			PackageInfo pis = context.getPackageManager().getPackageInfo(
					pkgName, PackageManager.GET_SIGNATURES);
			return hexdigest(pis.signatures[0].toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 得到CPU核心数
	 * 
	 * @return CPU核心数
	 */
	public static int getNumCores() {
		try {
			File dir = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
                    return Pattern.matches("cpu[0-9]", pathname.getName());
                }
			});
			return files.length;
		} catch (Exception e) {
			return 1;
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取设备的可用内存大小
	 * 
	 * @param context
	 *            应用上下文对象context
	 * @return 当前内存大小
	 */
	public static int getDeviceUsableMemory(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		// 返回当前系统的可用内存
		return (int) (mi.availMem / (1024 * 1024));
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取手机系统SDK版本
	 * 
	 * @return 如API 17 则返回 17
	 */
	public static int getSDKVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取所有非系统程序应用
	 * 
	 * @param context
	 *            上下文
	 * @return 应用信息List
	 */
	public static List<PackageInfo> getAllCustomerApps(Context context) {
		List<PackageInfo> packageInfos = new ArrayList<PackageInfo>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> paklist = pm.getInstalledPackages(0);
		for (int i = 0; i < paklist.size(); i++) {
			PackageInfo pkgInfo = paklist.get(i);
			if ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
				// 非系统程序
				packageInfos.add(pkgInfo);
			} else {
				// 系统程序
			}
		}
		return packageInfos;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取所有系统程序应用
	 * 
	 * @param context
	 *            上下文
	 * @return 应用信息List
	 */
	public static List<PackageInfo> getAllSystemApps(Context context) {
		List<PackageInfo> packageInfos = new ArrayList<PackageInfo>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> paklist = pm.getInstalledPackages(0);
		for (int i = 0; i < paklist.size(); i++) {
			PackageInfo pkgInfo = paklist.get(i);
			if ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
				// 非系统程序
			} else {
				// 系统程序
				packageInfos.add(pkgInfo);
			}
		}
		return packageInfos;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取所有应用(系统与自装应用)
	 * 
	 * @param context
	 *            上下文
	 * @return 应用信息List
	 */
	public static List<PackageInfo> getAllApps(Context context) {
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
		return packageInfos;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 停止运行服务
	 * 
	 * @param context
	 *            上下文
	 * @param className
	 *            类名
	 * @return 是否执行成功
	 */
	public static boolean stopRunningService(Context context, String className) {
		Intent intent_service = null;
		boolean ret = false;
		try {
			intent_service = new Intent(context, Class.forName(className));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (intent_service != null) {
			ret = context.stopService(intent_service);
		}
		return ret;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 检测服务是否运行
	 * 
	 * @param context
	 *            上下文
	 * @param className
	 *            类名
	 * @return 是否运行的状态 <li>true, 正在运行<li>false，未运行
	 */
	public static boolean isServiceRunning(Context context, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> servicesList = activityManager
				.getRunningServices(Integer.MAX_VALUE);
		for (RunningServiceInfo si : servicesList) {
			if (className.equals(si.service.getClassName())) {
				isRunning = true;
			}
		}
		return isRunning;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 将签名字符串转换成需要的32位签名
	 * 
	 * @param paramArrayOfByte
	 *            签名byte数组
	 * @return 32位签名字符串，否则返回null
	 */
	private static String hexdigest(byte[] paramArrayOfByte) {
		final char[] hexDigits = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97,
				98, 99, 100, 101, 102 };
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramArrayOfByte);
			byte[] arrayOfByte = localMessageDigest.digest();
			char[] arrayOfChar = new char[32];
			for (int i = 0, j = 0;; i++, j++) {
				if (i >= 16) {
					return new String(arrayOfChar);
				}
				int k = arrayOfByte[i];
				arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
				arrayOfChar[++j] = hexDigits[(k & 0xF)];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0 <br>
	 * 支持4.1.2, 4.1.23, 4.1.rc111这种形式
	 * 
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int compareVersion(String version1, String version2)
			throws Exception {
		if (version1 == null || version2 == null) {
			throw new Exception("compareVersion error:illegal params.");
		}
		String[] versionArray1 = version1.split("\\.");// 注意此处为正则匹配，不能用"."；
		String[] versionArray2 = version2.split("\\.");
		int idx = 0;
		int minLength = Math.min(versionArray1.length, versionArray2.length);// 取最小长度值
		int diff = 0;
		while (idx < minLength
				&& (diff = versionArray1[idx].length()
						- versionArray2[idx].length()) == 0// 先比较长度
				&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {// 再比较字符
			++idx;
		}
		// 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
		diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
		return diff;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 清理后台进程与服务
	 * 
	 * @param context
	 *            应用上下文对象context
	 * @return 被清理的数量
	 */
	public static int gc(Context context) {
		int count = 0; // 清理掉的进程数
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取正在运行的service列表
		List<RunningServiceInfo> serviceList = am.getRunningServices(100);
		if (serviceList != null) {
			for (RunningServiceInfo service : serviceList) {
				if (service.pid == android.os.Process.myPid())
					continue;
				try {
					android.os.Process.killProcess(service.pid);
					count++;
				} catch (Exception e) {
					e.getStackTrace();
				}
			}
		}

		// 获取正在运行的进程列表
		List<RunningAppProcessInfo> processList = am.getRunningAppProcesses();
		if (processList != null) {
			for (RunningAppProcessInfo process : processList) {
				// 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
				// 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
				if (process.importance > RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
					// pkgList 得到该进程下运行的包名
					String[] pkgList = process.pkgList;
					for (String pkgName : pkgList) {
						try {
							am.killBackgroundProcesses(pkgName);
							count++;
						} catch (Exception e) { // 防止意外发生
							e.getStackTrace();
						}
					}
				}
			}
		}
		return count;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 检测当前应用是否含有给定进程名的进程
	 * 
	 * @param context
	 *            上下文
	 * @param processName
	 *            进程名
	 * @return 是否含有给定进程名的进程
	 */
	public static boolean isNamedProcess(Context context, String processName) {
		if (context == null || TextUtils.isEmpty(processName)) {
			return false;
		}

		int pid = android.os.Process.myPid();
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> processInfoList = manager
				.getRunningAppProcesses();
		if (processInfoList == null) {
			return true;
		}

		for (RunningAppProcessInfo processInfo : manager
				.getRunningAppProcesses()) {
			if (processInfo.pid == pid
					&& processName.equalsIgnoreCase(processInfo.processName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 检测应用程序是否处于后台
	 * <ul>
	 * <li>需要在Manife.xml中使用android.permission.GET_TASKS权限</li>
	 * </ul>
	 * 
	 * @param context
	 *            上下文
	 * @return 如果应用程序处于后台return true, 否则 return false
	 */
	@SuppressWarnings("deprecation")
	public static boolean isApplicationInBackground(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> taskList = am.getRunningTasks(1);
		if (taskList != null && !taskList.isEmpty()) {
			ComponentName topActivity = taskList.get(0).topActivity;
            return topActivity != null
                    && !topActivity.getPackageName().equals(
                    context.getPackageName());
		}
		return false;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 检测当前应用是否是Debug版本
	 * 
	 * @param ctx
	 *            上下文
	 * @return 是否是Debug版本
	 */
	public static boolean isDebuggable(Context ctx) {
		boolean debuggable = false;
		try {
			PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = pinfo.signatures;
			for (int i = 0; i < signatures.length; i++) {
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				ByteArrayInputStream stream = new ByteArrayInputStream(
						signatures[i].toByteArray());
				X509Certificate cert = (X509Certificate) cf
						.generateCertificate(stream);
				debuggable = cert.getSubjectX500Principal().equals(DEBUG_DN);
				if (debuggable)
					break;
			}
		} catch (NameNotFoundException e) {
		} catch (CertificateException e) {
		}
		return debuggable;
	}

}