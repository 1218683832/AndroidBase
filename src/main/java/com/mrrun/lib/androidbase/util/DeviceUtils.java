package com.mrrun.lib.androidbase.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import java.lang.reflect.Field;

/**
 * <b>类功能描述:</b><br>
 * Android设备配置信息工具类 <br>
 * 包括硬件配置、软件系统配置、唯一标识码
 * 
 * @author lipin
 * @version 1.0
 */
public class DeviceUtils {

	private DeviceUtils() {
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 主板
	 * 
	 * @return
	 */
	public static String getBoard() {
		return Build.BOARD;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 系统启动程序版本号
	 * 
	 * @return
	 */
	public static String getBootLoader() {
		return Build.BOOTLOADER;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 系统定制商
	 * 
	 * @return
	 */
	public static String getBrand() {
		return Build.BRAND;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * cpu指令集
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getCpuAbi() {
		return Build.CPU_ABI;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * cpu指令集2
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getCpuAbi2() {
		return Build.CPU_ABI2;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 设备参数
	 * 
	 * @return
	 */
	public static String getDevice() {
		return Build.DEVICE;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 显示屏参数
	 * 
	 * @return
	 */
	public static String getDisplay() {
		return Build.DISPLAY;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 无线电固件版本
	 * 
	 * @return
	 */
	public static String getRadioVersion() {
		return Build.getRadioVersion();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 硬件识别码
	 * 
	 * @return
	 */
	public static String getFingerPrint() {
		return Build.FINGERPRINT;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 硬件名称
	 * 
	 * @return
	 */
	public static String getHardWare() {
		return Build.HARDWARE;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * HOST
	 * 
	 * @return
	 */
	public static String getHost() {
		return Build.HOST;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 修订版本列表
	 * 
	 * @return
	 */
	public static String getId() {
		return Build.ID;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 硬件制造商
	 * 
	 * @return
	 */
	public static String getManufacturer() {
		return Build.MANUFACTURER;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 版本
	 * 
	 * @return
	 */
	public static String getModel() {
		return Build.MODEL;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 该设备制造商
	 * 
	 * @return
	 */
	public static String getProduct() {
		return Build.PRODUCT;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 描述Build的标签
	 * 
	 * @return
	 */
	public static String getTags() {
		return Build.TAGS;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * TIME
	 * 
	 * @return
	 */
	public static long getTime() {
		return Build.TIME;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * builder类型
	 * 
	 * @return
	 */
	public static String getType() {
		return Build.TYPE;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * USER
	 * 
	 * @return
	 */
	public static String getUser() {
		return Build.USER;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取唯一标识码
	 * <p>
	 * <ol>
	 * 获取步骤:
	 * <li>
	 * {@link #getDeviceId(Context)}</li>
	 * <li>
	 * {@link #getAndroidId(Context)}</li>
	 * <li>
	 * {@link #getSerial()}</li>
	 * <ol>
	 * 
	 * @param context
	 * @return String value or null
	 * 
	 * @see #getDeviceId(Context)
	 * @see #getAndroidId(Context)
	 * @see #getSerial()
	 */
	public static String getUniqueId(Context context) {
		String uniqueId = null;
		uniqueId = getDeviceId(context);
		if (StringUtils.isEmpty(uniqueId)) {
			uniqueId = getAndroidId(context);
		}
		if (StringUtils.isEmpty(uniqueId)) {
			uniqueId = getSerial();
		}
		return uniqueId;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取DEVICE_ID<br>
	 * 这是Android系统为开发者提供的用于标识手机设备的串号，也是各种方法中普适性较高的，可以说几乎所有的设备都可以返回这个串号，并且唯一性良好。
	 * <p>
	 * <b>问题点:</b><br>
	 * 它会根据不同的手机设备返回IMEI，MEID或者ESN码，但在使用的过程中有以下问题：
	 * <li>
	 * 非手机设备：最开始搭载Android系统都手机设备，
	 * 而现在也出现了非手机设备：如平板电脑、电子书、电视、音乐播放器等。这些设备没有通话的硬件功能，系统中也就没有TELEPHONY_SERVICE
	 * ，自然也就无法通过上面的方法获得DEVICE_ID.</li>
	 * <li>
	 * 权限问题：获取DEVICE_ID需要READ_PHONE_STATE权限，如果只是为了获取DEVICE_ID而没有用到其他的通话功能
	 * ，申请这个权限一来大才小用，二来部分用户会怀疑软件的安全性。</li>
	 * <li>
	 * 厂商定制系统中的Bug：少数手机设备上，由于该实现有漏洞，会返回垃圾，如:zeros或者asterisks</li>
	 * 
	 * @param context
	 * @return String value or null
	 */
	public static String getDeviceId(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (null == manager) {
			return null;
		}
		return manager.getDeviceId();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取ANDROID_ID<br>
	 * 在设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来，这个16进制的字符串就是ANDROID_ID，
	 * 当设备被wipe后该值会被重置。
	 * <p>
	 * <b>问题点:</b><br>
	 * ANDROID_ID可以作为设备标识，但需要注意：
	 * <li>厂商定制系统的Bug：不同的设备可能会产生相同的ANDROID_ID：9774d56d682e549c。</li>
	 * <li>厂商定制系统的Bug：有些设备返回的值为null。</li>
	 * <li>设备差异：对于CDMA设备，ANDROID_ID和TelephonyManager.getDeviceId() 返回相同的值。</li>
	 * 
	 * @param context
	 * @return String value or null
	 */
	public static String getAndroidId(Context context) {
		return Secure
				.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取硬件序列号Serial Number
	 * 
	 * @return
	 */
	public static String getSerial() {
		return Build.SERIAL;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取Android设备所有的配置信息
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String deviceToString1() {
		StringBuffer sb = new StringBuffer();
		sb.append("主板：" + Build.BOARD);
		sb.append("\n系统启动程序版本号：" + Build.BOOTLOADER);
		sb.append("\n系统定制商：" + Build.BRAND);
		sb.append("\ncpu指令集：" + Build.CPU_ABI);
		sb.append("\ncpu指令集2：" + Build.CPU_ABI2);
		sb.append("\n设备参数：" + Build.DEVICE);
		sb.append("\n显示屏参数：" + Build.DISPLAY);
		sb.append("\n无线电固件版本：" + Build.getRadioVersion());
		sb.append("\n硬件识别码：" + Build.FINGERPRINT);
		sb.append("\n硬件名称：" + Build.HARDWARE);
		sb.append("\nHOST:" + Build.HOST);
		sb.append("\n修订版本列表：" + Build.ID);
		sb.append("\n硬件制造商：" + Build.MANUFACTURER);
		sb.append("\n版本：" + Build.MODEL);
		sb.append("\n硬件序列号：" + Build.SERIAL);
		sb.append("\n设备制造商：" + Build.PRODUCT);
		sb.append("\n描述Build的标签：" + Build.TAGS);
		sb.append("\nTIME:" + Build.TIME);
		sb.append("\nbuilder类型：" + Build.TYPE);
		sb.append("\nUSER:" + Build.USER);
		return sb.toString();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 通过反射获取所有的字段信息
	 * 
	 * @return
	 */
	public static String deviceToString2() {
		StringBuilder sbBuilder = new StringBuilder();
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				sbBuilder.append("\n" + field.getName() + ":"
						+ field.get(null).toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return sbBuilder.toString();
	}
}