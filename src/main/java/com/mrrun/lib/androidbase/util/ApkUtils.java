package com.mrrun.lib.androidbase.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

/**
 * <b>类功能描述:</b><br>
 * APK工具类 <br>
 * APK安装、卸载、获取APK安装包信息等等
 * 
 * @author lipin
 * @version 1.0
 * 
 */
public class ApkUtils {

	/**
	 * <b>类功能描述:</b><br>
	 * 封装App基本信息
	 * 
	 * @author lipin
	 * @version 1.0
	 */
	public static class ApkBaseInfo {
		/**
		 * 应用名称
		 */
		public String name;
		/**
		 * 应用图标
		 */
		public Drawable icon;
		/**
		 * 应用包名
		 */
		public String packageName;
		/**
		 * 应用版本名称
		 */
		public String versionName;
		/**
		 * 应用版本号
		 */
		public int versionCode;
	}

	private ApkUtils() {
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 安装apk(普通方法)
	 * 
	 * @param context
	 *            上下文
	 * @param file
	 *            APK文件
	 */
	public static void installApkNormal(Context context, File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 安装apk(普通方法)
	 * 
	 * @param context
	 *            上下文
	 * @param uri
	 *            APK文件uri
	 */
	public static void installApkNormal(Context context, Uri uri) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 卸载apk(普通方法)
	 * 
	 * @param context
	 *            上下文
	 * @param packageName
	 *            包名
	 */
	public static void uninstallApkNormal(Context context, String packageName) {
		Intent intent = new Intent(Intent.ACTION_DELETE);
		Uri packageURI = Uri.parse("package:" + packageName);
		intent.setData(packageURI);
		context.startActivity(intent);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 打开APK
	 * 
	 * @param packagename
	 * @param context
	 */
	public static void openAPK(String packagename, Context context) {
		PackageManager packageManager = context.getPackageManager();
		Intent intent = new Intent();
		intent = packageManager.getLaunchIntentForPackage(packagename);
		context.startActivity(intent);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断APK是否安装
	 * 
	 * @param packagename
	 * @param context
	 * @return
	 */
	public static boolean isApkInstalled(Context context, String packagename) {
		PackageManager packageManager = context.getPackageManager();
		try {
			packageManager.getPackageInfo(packagename,
					PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			return false;
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * md5加密
	 * 
	 * @param string
	 * @return
	 */
	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		}

        StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取apk包的信息：版本号，名称，图标等
	 * 
	 * @param absPath
	 *            apk安装包的绝对路径
	 * @param context
	 * @return <li>null</li> <li>App基本信息对象{@link ApkBaseInfo}</li>
	 */
	public static ApkBaseInfo getApkInfo(String absPath, Context context) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pkgInfo = pm.getPackageArchiveInfo(absPath,
				PackageManager.GET_ACTIVITIES);
		if (pkgInfo != null) {
			ApplicationInfo appInfo = pkgInfo.applicationInfo;
			appInfo.sourceDir = absPath;
			appInfo.publicSourceDir = absPath;
			ApkBaseInfo apkBaseInfo = new ApkBaseInfo();
			try {
				apkBaseInfo.name = appInfo.loadLabel(pm).toString();// 得到应用名
				apkBaseInfo.packageName = appInfo.packageName;// 得到包名
				apkBaseInfo.versionName = pkgInfo.versionName;// 得到版本名称
				apkBaseInfo.versionCode = pkgInfo.versionCode;// 得到版本号
				apkBaseInfo.icon = appInfo.loadIcon(pm);// 得到图标
				return apkBaseInfo;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取Apk的签名
	 * 
	 * @param absPath
	 *            apk安装包的绝对路径
	 * @return
	 */
	public static String getApkSignature(String absPath) {
		String PATH_PackageParser = "android.content.pm.PackageParser";
		try {
			// apk包的文件路径
			// 这是一个Package 解释器, 是隐藏的
			// 构造函数的参数只有一个, apk文件的路径
			Class<?> pkgParserCls = Class.forName(PATH_PackageParser);
			Class<?>[] typeArgs = new Class[1];
			typeArgs[0] = String.class;
			Object[] valueArgs = new Object[1];
			valueArgs[0] = absPath;
			Object pkgParser;
			if (Build.VERSION.SDK_INT > 19) {
				pkgParser = pkgParserCls.newInstance();
			} else {
				Constructor<?> constructor = pkgParserCls
						.getConstructor(typeArgs);
				pkgParser = constructor.newInstance(valueArgs);
			}
			// 这个是与显示有关的, 里面涉及到一些像素显示等等, 我们使用默认的情况
			DisplayMetrics metrics = new DisplayMetrics();
			metrics.setToDefaults();
			// PackageParser.Package mPkgInfo = packageParser.parsePackage(new
			// File(apkPath), apkPath,
			// metrics, 0);
			Object pkgParserPkg = null;
			if (Build.VERSION.SDK_INT > 19) {
				valueArgs = new Object[2];
				valueArgs[0] = new File(absPath);
				valueArgs[1] = PackageManager.GET_SIGNATURES;
				Method pkgParser_parsePackageMtd = pkgParserCls
						.getDeclaredMethod("parsePackage", typeArgs);
				pkgParser_parsePackageMtd.setAccessible(true);

				typeArgs = new Class[2];
				typeArgs[0] = File.class;
				typeArgs[1] = int.class;
				pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,
						valueArgs);
			} else {
				typeArgs = new Class[4];
				typeArgs[0] = File.class;
				typeArgs[1] = String.class;
				typeArgs[2] = DisplayMetrics.class;
				typeArgs[3] = int.class;

				Method pkgParser_parsePackageMtd = pkgParserCls
						.getDeclaredMethod("parsePackage", typeArgs);
				pkgParser_parsePackageMtd.setAccessible(true);

				valueArgs = new Object[4];
				valueArgs[0] = new File(absPath);
				valueArgs[1] = absPath;
				valueArgs[2] = metrics;
				valueArgs[3] = PackageManager.GET_SIGNATURES;
				pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,
						valueArgs);
			}

			typeArgs = new Class[2];
			typeArgs[0] = pkgParserPkg.getClass();
			typeArgs[1] = int.class;
			Method pkgParser_collectCertificatesMtd = pkgParserCls
					.getDeclaredMethod("collectCertificates", typeArgs);
			valueArgs = new Object[2];
			valueArgs[0] = pkgParserPkg;
			valueArgs[1] = PackageManager.GET_SIGNATURES;
			pkgParser_collectCertificatesMtd.invoke(pkgParser, valueArgs);
			// 应用程序信息包, 这个公开的, 不过有些函数, 变量没公开
			Field packageInfoFld = pkgParserPkg.getClass().getDeclaredField(
					"mSignatures");
			Signature[] info = (Signature[]) packageInfoFld.get(pkgParserPkg);
			return info[0].toCharsString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取已安装apk签名
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getInstallPackageSignature(Context context,
			String packageName) {
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> apps = pm
				.getInstalledPackages(PackageManager.GET_SIGNATURES);

		Iterator<PackageInfo> iter = apps.iterator();
		while (iter.hasNext()) {
			PackageInfo packageinfo = iter.next();
			String thisName = packageinfo.packageName;
			if (thisName.equals(packageName)) {
				return packageinfo.signatures[0].toCharsString();
			}
		}
		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取APK名称
	 * 
	 * @param context
	 * @param absPath
	 *            apk安装包的绝对路径
	 * @return <li>未获得""</li> <li>获得appName</li>
	 */
	public static String getApkName(Context context, String absPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(absPath,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			appInfo.sourceDir = absPath;
			appInfo.publicSourceDir = absPath;
			try {
				return appInfo.loadLabel(pm).toString();
			} catch (Exception e) {
			}
		}
		return "";
	}

	/*
	 * 采用了新的办法获取APK图标，之前的失败是因为android中存在的一个BUG,通过 appInfo.publicSourceDir =
	 * apkPath
	 */
	/**
	 * <b>方法功能描述:</b><br>
	 * 获取APK图标
	 * 
	 * @param context
	 * @param absPath
	 *            apk安装包的绝对路径
	 * @return
	 */
	public static Drawable getApkIcon(Context context, String absPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(absPath,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			appInfo.sourceDir = absPath;
			appInfo.publicSourceDir = absPath;
			try {
				return appInfo.loadIcon(pm);
			} catch (OutOfMemoryError e) {
			}
		}
		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取APK包名
	 * 
	 * @param context
	 * @param absPath
	 *            apk安装包的绝对路径
	 * @return
	 */
	public static String getApkPackageName(Context context, String absPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(absPath,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			appInfo.sourceDir = absPath;
			appInfo.publicSourceDir = absPath;
			try {
				return appInfo.packageName;
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取APK版本名称
	 * 
	 * @param context
	 * @param absPath
	 *            apk安装包的绝对路径
	 * @return <li>未获得""</li> <li>获得versionName</li>
	 */
	public static String getApkVersionName(Context context, String absPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(absPath,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			appInfo.sourceDir = absPath;
			appInfo.publicSourceDir = absPath;
			try {
				return info.versionName == null ? "" : info.versionName;
			} catch (Exception e) {
			}
		}
		return "";
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取APK版本号
	 * 
	 * @param context
	 * @param absPath
	 *            apk安装包的绝对路径
	 * @return <li>未获得-1</li> <li>获得versionCode</li>
	 */
	public static int getApkVersionCode(Context context, String absPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(absPath,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			appInfo.sourceDir = absPath;
			appInfo.publicSourceDir = absPath;
			try {
				return info.versionCode;
			} catch (Exception e) {
			}
		}
		return -1;
	}
}
