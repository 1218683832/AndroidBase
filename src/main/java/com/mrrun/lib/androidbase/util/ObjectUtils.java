package com.mrrun.lib.androidbase.util;

/**
 * <b>类功能描述:</b><br>
 * 对象工具类 <br>
 * 
 * @author lipin
 * @version 1.0
 * 
 */
public class ObjectUtils {

	private ObjectUtils() {
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断某对象是否为Null
	 * 
	 * @param object
	 *            对象
	 * @return 如果对象为null返回true
	 */
	public static boolean isNull(Object object) {
		return (null == object);
	}
	
	/**
	 * <b>方法功能描述:</b><br>
	 * 判断某对象是否不为Null
	 * 
	 * @param object
	 *            对象
	 * @return 如果对象不为null返回true
	 */
	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}
}
