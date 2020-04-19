package com.mrrun.lib.androidbase.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * <b>类功能描述:</b><br>
 * SharePreferences数据存储工具类 <br>
 * 
 * @author lipin
 * @version 1.0
 */
public class PreferencesUtils {

	private PreferencesUtils() {
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 写入Sting型参数键值对
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putString(Context context, String name, String key,
			String value) {
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获得Sting型指定键值的参数值
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or null. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a string
	 * @see #getString(Context, String, String)
	 */
	public static String getString(Context context, String name, String key) {
		return getString(context, name, key, null);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获得Sting型指定键值的参数值
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a string
	 */
	public static String getString(Context context, String name, String key,
			String defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return settings.getString(key, defaultValue);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 写入int型参数键值对
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putInt(Context context, String name, String key,
			int value) {
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获得int型指定键值的参数值
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a int
	 * @see #getInt(Context, String, String, int)
	 */
	public static int getInt(Context context, String name, String key) {
		return getInt(context, name, key, -1);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获得int型指定键值的参数值
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a int
	 */
	public static int getInt(Context context, String name, String key,
			int defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return settings.getInt(key, defaultValue);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 写入long型参数键值对
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putLong(Context context, String name, String key,
			long value) {
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获得long型指定键值的参数值
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a long
	 * @see # getLong(Context, String, long)
	 */
	public static long getLong(Context context, String name, String key) {
		return getLong(context, name, key, -1);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获得long型指定键值的参数值
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a long
	 */
	public static long getLong(Context context, String name, String key,
			long defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return settings.getLong(key, defaultValue);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 写入float型参数键值对
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putFloat(Context context, String name, String key,
			float value) {
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获得float型指定键值的参数值
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param context
	 *            context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or -1. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a float
	 * @see # getFloat(Context, String, float)
	 */
	public static float getFloat(Context context, String name, String key) {
		return getFloat(context, name, key, -1);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获得float型指定键值的参数值
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a float
	 */
	public static float getFloat(Context context, String name, String key,
			float defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return settings.getFloat(key, defaultValue);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 写入boolean型参数键值对
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to modify
	 * @param value
	 *            The new value for the preference
	 * @return True if the new values were successfully written to persistent
	 *         storage.
	 */
	public static boolean putBoolean(Context context, String name, String key,
			boolean value) {
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	/**
	 * get boolean preferences, default is false
	 * 
	 * @param context
	 *            context
	 * @param key
	 *            The name of the preference to retrieve
	 * @return The preference value if it exists, or false. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a boolean
	 * @see # getBoolean(Context, String, boolean)
	 */
	public static boolean getBoolean(Context context, String name, String key) {
		return getBoolean(context, name, key, false);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获得boolean型指定键值的参数值
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            file name
	 * @param key
	 *            The name of the preference to retrieve
	 * @param defaultValue
	 *            Value to return if this preference does not exist
	 * @return The preference value if it exists, or defValue. Throws
	 *         ClassCastException if there is a preference with this name that
	 *         is not a boolean
	 */
	public static boolean getBoolean(Context context, String name, String key,
			boolean defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		return settings.getBoolean(key, defaultValue);
	}
}
