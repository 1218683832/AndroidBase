package com.mrrun.lib.androidbase.util;

import android.util.Base64;

/**
 * Created by lipin on 2017/11/8.
 *   Base64是网络上最常见的用于传输8Bit字节代码的编码方式之一，
 *   Base64并不是安全领域的加密算法，其实Base64只能算是一个编码算法，
 *   对数据内容进行编码来适合传输.
 */

public class Base64Utils {

    public static String encodeToString(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static String decode(String encodedString){
        return new String(Base64.decode(encodedString, Base64.DEFAULT));
    }

    public static String decode(byte[] bytes){
        return new String(Base64.decode(bytes, Base64.DEFAULT));
    }

    public static String encodeToString(byte[] bytes, int flags) {
        return Base64.encodeToString(bytes, flags);
    }

    public static String decode(String encodedString, int flags){
        return new String(Base64.decode(encodedString, flags));
    }

    public static String decode(byte[] bytes, int flags){
        return new String(Base64.decode(bytes, flags));
    }
}
