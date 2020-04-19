package com.mrrun.lib.androidbase.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <b>类功能描述:</b><br>
 * 系统信息工具类
 * 
 * @author lipin
 * @version 1.0
 */
public class SystemUtils {

	private SystemUtils() {
	}

	/**
	 * <b>类功能描述:</b><br>
	 * 区域
	 * 
	 * @author lipin
	 * @version 1.0
	 * 
	 * @see java.util.Locale
	 */
	public static class Locale {
		/**
		 * 中国(简体)
		 */
		public static final String CHINA = "zh_CN";

		/**
		 * 台湾(繁体)
		 */
		public static final String TAIWAN = "zh_TW";

		/**
		 * 香港(繁体)
		 */
		public static final String HONGKONG = "zh_HK";

		/**
		 * 美国
		 */
		public static final String US = "en_US";

		/**
		 * 英国
		 */
		public static final String UK = "en_GB";

		/**
		 * <b>方法功能描述:</b><br>
		 * 获取区域代码:语言代码_国家代码，可以用来区分汉语简体繁体 {@link java.util.Locale#getDefault()}
		 * <p>
		 * 例如:
		 * <li>zh_CN表示汉语-中国(简体)</li>
		 * <li>zh_TW表示汉语-台湾(繁体)</li>
		 * <li>zh_HK表示汉语-中国(繁体)</li>
		 * 
		 * <li>en_AU表示英语-澳洲</li>
		 * <li>en_CA表示英语-加拿大</li>
		 * <li>en_ZA表示英语-南非</li>
		 * <li>en_GB表示英语-英国</li>
		 * <li>en_US表示英语-美国</li>
		 * 
		 * @return String value or null
		 * 
		 */
		public static String getLocaleCode() {
			return java.util.Locale.getDefault().toString();
		}
	}

	/**
	 * <b>类功能描述:</b><br>
	 * 语言
	 * 
	 * @author lipin
	 * @version 1.0
	 */
	public static class Language {
		/**
		 * <b>方法功能描述:</b><br>
		 * 获取语言代码如:汉语、英语、韩语、日语等 {@link java.util.Locale#getLanguage()}
		 * <p>
		 * 例如:
		 * <li>zh表示汉语</li>
		 * <li>en表示英语</li>
		 * <li>af表示公用荷兰语</li>
		 * <li>sq表示阿尔巴尼亚</li>
		 * <li>ar表示阿拉伯语</li>
		 * <li>hy表示亚美尼亚</li>
		 * <li>az表示Azeri</li>
		 * <li>eu表示巴斯克</li>
		 * <li>be表示Belarusian</li>
		 * <li>bg表示保加利亚</li>
		 * <li>ca表示嘉泰罗尼亚</li>
		 * <li>hr表示克罗埃西亚</li>
		 * <li>cs表示捷克</li>
		 * <li>da表示丹麦文</li>
		 * <li>div表示Dhivehi</li>
		 * <li>nl表示荷兰</li>
		 * <li>et表示爱沙尼亚</li>
		 * <li>fo表示Faroese</li>
		 * <li>fa表示波斯语</li>
		 * <li>fi表示芬兰语</li>
		 * <li>fr表示法国</li>
		 * <li>gl表示加利西亚</li>
		 * <li>ka表示格鲁吉亚州</li>
		 * <li>de表示德国</li>
		 * <li>el表示希腊</li>
		 * <li>gu表示Gujarati</li>
		 * <li>he表示希伯来</li>
		 * <li>hi表示北印度语</li>
		 * <li>hu表示匈牙利</li>
		 * <li>is表示冰岛语</li>
		 * <li>id表示印尼</li>
		 * <li>it表示意大利</li>
		 * <li>ja表示日本</li>
		 * <li>kn表示卡纳达语</li>
		 * <li>kk表示Kazakh</li>
		 * <li>kok表示Konkani</li>
		 * <li>ko表示韩国</li>
		 * <li>mn表示蒙古</li>
		 * <li>pl表示波兰</li>
		 * <li>ru表示俄国</li>
		 * <li>es表示西班牙</li>
		 * <li>sv表示瑞典</li>
		 * <li>th表示泰国</li>
		 * <li>uk表示乌克兰</li>
		 * <li>ur表示Urdu</li>
		 * <li>uz表示Uzbek</li>
		 * <li>vi表示越南</li>
		 * 
		 * @return String value or null
		 */
		public static String getLanguageCode() {
			return java.util.Locale.getDefault().getLanguage();
		}

	}

	/**
	 * 
	 * <b>类功能描述:</b><br>
	 * 国家
	 * 
	 * @author lipin
	 * @version 1.0
	 */
	public static class Country {
		/**
		 * <b>方法功能描述:</b><br>
		 * 获取国家代码 {@link java.util.Locale#getCountry()}
		 * <p>
		 * 例如:
		 * <li>CN表示中国</li>
		 * <li>HK表示香港</li>
		 * <li>MO表示澳门</li>
		 * <li>SG表示新加坡</li>
		 * <li>TW表示台湾</li>
		 * 
		 * <li>AU表示澳洲</li>
		 * <li>BZ表示伯利兹</li>
		 * <li>CA表示加拿大</li>
		 * <li>CB表示加勒比海</li>
		 * <li>IE表示爱尔兰</li>
		 * <li>JM表示牙买加</li>
		 * <li>NZ表示新西兰</li>
		 * <li>PH表示菲律宾共和国</li>
		 * <li>ZA表示南非</li>
		 * <li>TT表示千里达托贝哥共和国</li>
		 * <li>GB表示英国</li>
		 * <li>US表示美国</li>
		 * <li>ZW表示津巴布韦</li>
		 * 
		 * @return String value or null
		 */
		public static String getCountryCode() {
			return java.util.Locale.getDefault().getCountry();
		}
	}

	public static class SDK {

		public static final int V2_2 = 8;
		public static final int V2_3 = 9;
		public static final int V2_3_3 = 10;
		public static final int V3_0 = 11;
		public static final int V3_1 = 12;
		public static final int V3_2 = 13;
		public static final int V4_0 = 14;
		public static final int V4_0_3 = 15;
		public static final int V4_1 = 16;
		public static final int V4_2 = 17;
		public static final int V4_3 = 18;
		public static final int V4_4 = 19;

		/**
		 * 
		 * <b>方法功能描述:</b><br>
		 * 获取系统SDK版本信息
		 * 
		 * @param
		 * @return int
		 */
		public static int getSystemVersion() {
			return android.os.Build.VERSION.SDK_INT;
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取建议默认线程池的大小
	 * 
	 * @return if 2 * availableProcessors + 1 less than 8, return it, else
	 *         return 8;
	 * @see {@link #getDefaultThreadPoolSize(int)} max is 8
	 */
	public static int getDefaultThreadPoolSize() {
		return getDefaultThreadPoolSize(8);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取建议默认线程池的大小
	 * 
	 * @param max
	 * @return if 2 * availableProcessors + 1 less than max, return it, else
	 *         return max;
	 */
	public static int getDefaultThreadPoolSize(int max) {
		int availableProcessors = 2 * Runtime.getRuntime()
				.availableProcessors() + 1;
		return availableProcessors > max ? max : availableProcessors;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取手机当前的Runtime
	 * 
	 * @return 正常情况下可能取值Dalvik, ART, ART debug build;
	 */
	public static String getCurrentRuntimeValue() {
		try {
			Class<?> systemProperties = Class
					.forName("android.os.SystemProperties");
			try {
				Method get = systemProperties.getMethod("get", String.class,
						String.class);
				if (get == null) {
					return "WTF?!";
				}
				try {
					final String value = (String) get.invoke(systemProperties,
							"persist.sys.dalvik.vm.lib",
							/* Assuming default is */"Dalvik");
					if ("libdvm.so".equals(value)) {
						return "Dalvik";
					} else if ("libart.so".equals(value)) {
						return "ART";
					} else if ("libartd.so".equals(value)) {
						return "ART debug build";
					}
					return value;
				} catch (IllegalAccessException e) {
					return "IllegalAccessException";
				} catch (IllegalArgumentException e) {
					return "IllegalArgumentException";
				} catch (InvocationTargetException e) {
					return "InvocationTargetException";
				}
			} catch (NoSuchMethodException e) {
				return "SystemProperties.get(String key, String def) method is not found";
			}
		} catch (ClassNotFoundException e) {
			return "SystemProperties class is not found";
		}
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 是否Dalvik(虚拟机)模式
	 * 
	 * @return true or false
	 */
	public static boolean isDalvik() {
		return "Dalvik".equals(getCurrentRuntimeValue());
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 是否ART模式
	 * 
	 * @return 结果
	 */
	public static boolean isART() {
		String currentRuntime = getCurrentRuntimeValue();
		return "ART".equals(currentRuntime)
				|| "ART debug build".equals(currentRuntime);
	}

}