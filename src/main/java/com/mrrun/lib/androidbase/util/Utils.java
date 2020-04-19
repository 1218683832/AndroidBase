package com.mrrun.lib.androidbase.util;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by lipin on 2017/7/30.
 * Utils初始化相关
 *
 * @author lipin
 * @version 1.0
 */
public final class Utils {

    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("Utils can't instantiate");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}
