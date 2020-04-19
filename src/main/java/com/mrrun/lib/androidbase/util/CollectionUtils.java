package com.mrrun.lib.androidbase.util;

import java.util.Collection;
import java.util.List;

/**
 * <b>类功能描述:</b><br>
 * 集合工具类 <br>
 * 
 * @author lipin
 * @version 1.0
 * 
 */
public class CollectionUtils {

	private CollectionUtils() {
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断给定的集合是否为null或者是空的
	 * 
	 * @param collection
	 *            集合对象
	 * @return true:集合对象为null或者是空的
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断给定的集合数组中的所有集合是否都为null或者是空的
	 * 
	 * @param collections
	 *            给定的集合
	 * @return true:所有集合都为null或者是空的
	 */
	public static boolean isEmpty(Collection<?>... collections) {
		for (Collection<?> collection : collections) {
			if (isNotEmpty(collection)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断给定的集合是否不为null且不为空
	 * 
	 * @param collection
	 *            集合对象
	 * @return true:集合对象不为null且不为空
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 判断给定的集合数组中是否全部都不为null且不为空
	 * 
	 * @param collections
	 *            给定的集合
	 * @return true:所有集合都不为null且不为空
	 */
	public static boolean isNotEmpty(Collection<?>... collections) {
		for (Collection<?> collection : collections) {
			if (isEmpty(collection)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <b>方法功能描述:</b><br>
	 * 获取给定集合的大小
	 * 
	 * @param collection
	 *            集合对象
	 * @return 给定的集合为null或者是空的, return 0, 否则 return size
	 */
	public static int size(Collection<?> collection) {
		if (isNotEmpty(collection)) {
			return collection.size();
		}
		return 0;
	}
	
	public static <T> Object getObjecByIndex(List<T> list, int index) {
		if (isEmpty(list)) {
			return null;
		}
		if (index < 0 || index > list.size()) {
			return null;
		}
		return list.get(index);
	}
}