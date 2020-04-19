package com.mrrun.lib.androidbase.base.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import java.lang.ref.WeakReference;

/**
 * Created by lipin on 2017/8/25.
 * b>类功能描述:</b><br>
 *
 * @author lipin
 * @version 1.2
 */

public abstract class BaseService<O> extends Service {

    protected WeakReference<O> mRefObject;

    /**
     * <b>方法功能描述:</b><br>
     * 绑定并启动服务
     *
     * @param context
     * @param intent
     * @param conn
     * @param flags
     * @return boolean
     */
    public static boolean bindService(Context context, Intent intent, ServiceConnection conn, int flags){
        return context.bindService(intent, conn, flags);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 解绑并关闭服务
     *
     * @param context
     * @param conn
     */
    public static void unbindService(Context context, ServiceConnection conn){
        context.unbindService(conn);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 开启服务
     *
     * @param context
     * @param intent
     * @return ComponentName
     */
    public static ComponentName startService(Context context, Intent intent){
        return context.startService(intent);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 关闭服务
     *
     * @param context
     * @param intent
     * @return boolean
     */
    public static boolean stopService(Context context, Intent intent){
        return context.stopService(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachOFromService();
    }

    /**
     * <b>方法功能描述:</b><br>
     * 将与此Service绑定的对象或启动此Service的对象(一般是Activity、Fragment)附加到此Service中;
     * 这样在此Service中可以使用对象的公开方法;
     *
     * @param obj
     */
    public void attachOToService(O obj) {
        mRefObject = new WeakReference<O>((O) obj);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 将与此Service附加的对象(一般是Activity、Fragment)与此Service分离;
     */
    protected void detachOFromService() {
        if (mRefObject != null) {
            mRefObject.clear();
            mRefObject = null;
        }
    }
}
