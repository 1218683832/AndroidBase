package com.mrrun.lib.androidbase.util;

import java.util.Collection;
import java.util.List;

public class Lang {
    public static <T> T[] arrayOf(T... tArr) {
        return tArr;
    }

    public static int toInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static long toLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static <T> boolean isNotEquals(T t, T t2) {
        if (t == null && t2 == null) {
            return false;
        }
        if (t == null || t2 == null) {
            return true;
        }
        return !t.equals(t2);
    }


    public static String snull(Object obj, String str) {
        return obj == null ? str : obj.toString();
    }


    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isEmpty(Collection<?> collection2) {
        return collection2 == null || collection2.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static int count(Collection<?> collection2) {
        if (collection2 == null) {
            return 0;
        }
        return collection2.size();
    }

    /**
     * list最后一个元素
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T lastElement(List<T> list) {
        return list.get(count(list) - 1);
    }
}
