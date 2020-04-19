package com.mrrun.lib.androidbase.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

/**
 * <b>类功能描述:</b><br>
 * 资源访问操作工具类 <br>
 * <li>strings.xml : 存放字符串资源</li> <li>colors.xml : 存放颜色资源</li> <li>dimens.xml :
 * 存放尺寸值资源</li> <li>styles.xml : 存放样式资源</li> <li>arrays.xml : 存放数组资源</li> <li>
 * res/xml</li>
 * 
 * @author lipin
 * 
 */
public class ResUtils {

	private ResUtils() {
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 通过字符串资源id获取字符串资源<br>
	 * {@link String}
	 * 
	 * @param context
	 * @param id
	 *            字符串资源id <li>R.string.xxx</li>
	 * @return 字符串{@link String}
	 * 
	 * @see Resources#getString(int)
	 * 
	 */
	public static String getString(Context context, int id) {
		return context.getResources().getString(id);
	}
	
	/**
	 * <b>方法功能描述:</b><br>
	 * 通过资源id获取{@link CharSequence}资源
	 * @param context
	 * @param id 资源id
	 * @return {@link CharSequence}，与资源绑定在一起的字符串
	 */
	public static CharSequence getText(Context context, int id) {
		return context.getResources().getText(id);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 通过资源名获取资源id
	 * 
	 * @param context
	 * @param resName
	 *            所需的资源的名称
	 * @param resType
	 *            资源类型，资源所属的类 <li>drawable</li> <li>id</li> <li>string</li> <li>
	 *            layout</li> <li>......</li>
	 * @param packageName
	 *            包名, 包名指定了R的 全限定名的包名部分 <li>包名为空或者""或者" "表示默认本类的包名</li> <li>
	 *            如果R的全限定名为 android.R则包名在这里就是"android"</li> <li>或者
	 *            com.company.R，则包名在这里就是"com.company"</li>
	 * @return <li>resId=-1获取资源id出错</li> <li>resId=R.java中的任何资源id</li>
	 * 
	 * @see Resources#getIdentifier(String, String, String)
	 */
	public static int getIdentifier(Context context, String resName,
			String resType, String packageName) {
		int resId = -1;
		if (StringUtils.isBlank(packageName)) {
			packageName = context.getPackageName();
		}
		try {
			resId = context.getResources().getIdentifier(resName, resType,
					packageName);
			return resId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resId;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 通过XML资源id获得{@link XmlResourceParser}对象
	 * 
	 * @param context
	 * @param id
	 *            XML资源id <li>R.xml.xxx</li>
	 * @return 返回一个新的{@link XmlResourceParser}实例， 可以读到XML数据
	 * 
	 * @see Resources#getXml(int)
	 */
	public static XmlResourceParser getXml(Context context, int id) {
		return context.getResources().getXml(id);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 通过资源id获取{@link String}数组资源<br>
	 * 
	 * @param context
	 * @param id
	 *            数组资源id <li>R.array.xxx</li>
	 * @return 返回{@link String}数组
	 * 
	 * @see Resources#getStringArray(int)
	 */
	public static String[] getStringArray(Context context, int id) {
		return context.getResources().getStringArray(id);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 通过资源id获取{@link int}数组资源<br>
	 * 
	 * @param context
	 * @param id
	 *            数组资源id <li>R.array.xxx</li>
	 * @return 返回{@link int}数组
	 * 
	 * @see Resources#getIntArray(int)
	 */
	public static int[] getIntArray(Context context, int id) {
		return context.getResources().getIntArray(id);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 通过资源id获取颜色资源<br>
	 * 
	 * @param context
	 * @param id
	 *            颜色资源id<li>R.color.xxx</li>
	 * @return 返回一个颜色值
	 * 
	 * @see Resources#getColor(int)
	 */
	@SuppressWarnings("deprecation")
	public static int getColor(Context context, int id) {
		return context.getResources().getColor(id);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 通过资源id获取尺寸资源<br>
	 * 
	 * @param context
	 * @param id
	 *            尺寸资源id<li>R.dimen.xxx</li>
	 * @return 尺寸数值(单位:px)
	 * 
	 * @see DisplayMetrics
	 * @see Resources#getDimension(int)
	 */
	public static float getDimension(Context context, int id) {
		return context.getResources().getDimension(id);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 通过资源id获取{@link Drawable}对象<br>
	 * 
	 * @param context
	 * @param id
	 *            图形资源id<li>R.drawable.xxx</li> <li>R.color.xxx</li> <li>......</li>
	 * @return {@link Drawable}对象
	 * 
	 * @see Resources#getDrawable(int)
	 */
	@SuppressWarnings("deprecation")
	public static Drawable getDrawable(Context context, int id) {
		return context.getResources().getDrawable(id);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取显示度量
	 * 
	 * @param context
	 * @return The resource's current display metrics
	 * 
	 * @see DisplayMetrics
	 * @see Resources#getDisplayMetrics()
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		return context.getResources().getDisplayMetrics();
	}
}
