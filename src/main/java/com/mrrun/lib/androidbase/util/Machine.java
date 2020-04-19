package com.mrrun.lib.androidbase.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

/**
 * 
 * <br>类描述: 用户设备相关信息
 * <br>功能详细描述:
 * 
 */
public class Machine {

    public static final int NETTYPE_MOBILE = 0; // 中国移动
    public static final int NETTYPE_UNICOM = 1; // 中国联通
    public static final int NETTYPE_TELECOM = 2; // 中国电信
    
    //CPU,内存等相关信息
    public final static String CPU_FILE_DIR = "/sys/devices/system/cpu/";
    public final static int HIGH_DEVICE = 0x1; // 高端
    public final static int NORMAL_DEVICE = 0x2; // 普通
    public final static int LOW_DEVICE = 0x3; // 低端
    public final static long MEMORY_SIZE_HIGH = 680; // 单位是MB
    public final static long MEMORY_SIZE_NORMAL = 500; // 单位是MB
    public final static long HEAP_SIZE_HIGH = 48; // 单位是MB
    public final static long HEAP_SIZE_NORMAL = 28; // 单位是MB
    public final static long HEAP_SIZE_HIGH_LARGE = 128; // large_heap模式单位是MB
    public final static long HEAP_SIZE_NORMAL_LARGE = 64; // large_heap模式单位是MB
    public final static int CPU_CORE_HIGH = 2; // cpu个数两个或以上的就认为在计算能力上是高配
    public final static int CPU_CLOCK_HIGH = 1200000; // cpu高配的频率
    public final static int CPU_CLOCK_NORMAL = 900000; // cpu中配的频率
    private static int sLevel = -1; // -1标识level还未获取过硬件设备的情况，获取过之后值就不为-1，下次就直接放回level值
    
    public static final boolean IS_SDK_ABOVE_L = Build.VERSION.SDK_INT >= 21;//sdk是否5.0或以上
    public static final boolean IS_SDK_ABOVE_KITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;//android4.4以上
    
    /**
     * 获取机器等级
     */
    public static int getDeviceLevel() {
        if (sLevel != -1) {
            return sLevel;
        }
        final int cpuLevel = getComputeLevel();
        final int memoryLevel = getMemoryLevel();
        final int totalPoint = cpuLevel + memoryLevel;
        if (totalPoint <= HIGH_DEVICE * 2) {
            sLevel = HIGH_DEVICE; // 两个都为高才为高
        } else if (cpuLevel <= NORMAL_DEVICE && memoryLevel <= NORMAL_DEVICE) {
            sLevel = NORMAL_DEVICE;
        } else {
            sLevel = LOW_DEVICE; // 两个级别，其中一个为低则为低
        }
        return sLevel;
    } 
    
    /**
     * 偏重计算的机器等级
     */
    private static int getComputeLevel() {
        int result = LOW_DEVICE;
        try {
            Integer cpuClock = Integer.valueOf(CpuManager.getMaxCpuFreq());
            if (cpuClock >= CPU_CLOCK_HIGH) {
                result = HIGH_DEVICE;
            } else if (cpuClock >= CPU_CLOCK_NORMAL) {
                result = NORMAL_DEVICE;
            } else {
                result = LOW_DEVICE;
            }
        } catch (Exception e) {
            result = NORMAL_DEVICE;
        }
        return result;
    }

    /**
     * 偏重内存的机器等级
     */
    private static int getMemoryLevel() {
        int result = LOW_DEVICE;
        final long heapSize = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        final long heapSizeHigh = Build.VERSION.SDK_INT >= 14
                ? HEAP_SIZE_HIGH_LARGE
                : HEAP_SIZE_HIGH;
        final long heapSizeNormal = Build.VERSION.SDK_INT >= 14
                ? HEAP_SIZE_NORMAL_LARGE
                : HEAP_SIZE_NORMAL;
        final long totalMemorySize = getTotalMemory() / 1024;
        if (totalMemorySize >= MEMORY_SIZE_HIGH && heapSize >= heapSizeHigh) {
            result = HIGH_DEVICE;
        } else if (totalMemorySize >= MEMORY_SIZE_NORMAL && heapSize >= heapSizeNormal) {
            result = NORMAL_DEVICE;
        }
        return result;
    }
    
    private static long getTotalMemory() {
        long totalMemroy = 0;
        try {
            String cat = null;
            FileReader fileReader = new FileReader("/proc/meminfo");
            BufferedReader bReader = new BufferedReader(fileReader, 4096);
            cat = bReader.readLine(); // 读取第一行“MemTotal: 415268 kB”
            bReader.close();

            if (cat != null) {
                String[] array = cat.split("\\s+");
                if (array != null && array.length >= 1) {
                    // 数组第2个为内存大小
                    totalMemroy = Long.parseLong(array[1]);
                }
                array = null;
            }
            cat = null;
            bReader = null;
            fileReader = null;
        } catch (NumberFormatException e) {
        } catch (FileNotFoundException e1) {
        } catch (IOException e) {
        }
        return totalMemroy;
    }
    
    /**
     * 功能简述:获取Android ID的方法 
     */
    public static String getAndroidId(Context context) {
        String androidId = "";
        if (context != null) {
            try {
                androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }catch (Exception e){

            }
        }
        return androidId;
    }
    
    /**
     * 功能简述:获取版本号
     */
    public static int getVersion(Context context) {
        PackageManager packageManager = context.getPackageManager(); // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionCode;
    }
    
    /**
     * 功能简述:获取版本名
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager(); // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionName;
    }
    
    public static int getChannelId(Context context) {
        String channelId = AppUtils.getMetaValue(context, "TD_CHANNEL_ID");
        if (LocalTextUtil.isBlank(channelId)) {
            return 200;
        }
        return StringUtils.toInteger(channelId, 200);
    }
    
    /**
     * 获取国家
     */
    public static String getCountry(Context context) {
        String ret = null;

        try {
            TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telManager != null) {
                ret = telManager.getSimCountryIso().toLowerCase();
            }
        } catch (Throwable e) {
            // e.printStackTrace();
        }
        if (ret == null || ret.equals("")) {
            ret = Locale.getDefault().getCountry().toLowerCase();
        }
        return ret;
    }
    
    /**
     * 获取当前的语言
     */
    public static String getLanguage(Context context) {
        String language = context.getResources().getConfiguration().locale.getLanguage();
        return language;
    }
    
    /**
     * 功能简述: 获取真实的imei号。
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }
    
    /**
     * 获取用户运营商代码
     */
    public static String getImsi(Context context) {
        String simOperator = "000";
        try {
            if (context != null) {
                // 从系统服务上获取了当前网络的MCC(移动国家号)，进而确定所处的国家和地区
                TelephonyManager manager = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                simOperator = manager.getSimOperator();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return simOperator;
    }
    
    /**
     * <br>功能简述:手机分辨率（720*1280）
     */
    public static String getDpi(Context context) {
        final WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        String dpi = metrics.widthPixels + "*" + metrics.heightPixels;
        return dpi;
    }
    
    
    /**
     * 获取用户运营商代码
     */
    public static String getSimOperator(Context context) {
        String simOperator = "000";
        try {
            if (context != null) {
                // 从系统服务上获取了当前网络的MCC(移动国家号)，进而确定所处的国家和地区
                TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                simOperator = manager.getSimOperator();
            }
        } catch (Throwable e) {
        }

        // 当客户端运营商编码不存在时，simOperator可能被设置为空，服务器端不会受理
        // 故如果simOperator字符串为空，则置为“000”
        return TextUtils.isEmpty(simOperator) ? "000" : simOperator;
    }
    
    /**
     * 获取网络类型
     * @return 1 for 移动，2 for 联通，3 for 电信，-1 for 不能识别
     */
    public static int getNetWorkType(Context context) {
        int netType = -1;
        String simOperator = getSimOperator(context);
        if (simOperator != null) {
            if (simOperator.startsWith("46000") || simOperator.startsWith("46002")) {
                // 因为移动网络编号46000下的IMSI已经用完，
                // 所以虚拟了一个46002编号，134/159号段使用了此编号
                // 中国移动
                netType = NETTYPE_MOBILE;
            } else if (simOperator.startsWith("46001")) {
                // 中国联通
                netType = NETTYPE_UNICOM;
            } else if (simOperator.startsWith("46003")) {
                // 中国电信
                netType = NETTYPE_TELECOM;
            }
        }
        return netType;
    }
    
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }

    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();
    }
    
    public static void notifyVoice(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, getSystemDefultNotifyUri(context));
        try {  
            mediaPlayer.prepare();  
        } catch (IllegalStateException e) {
            e.printStackTrace();  
        } catch (IOException e) {
            e.printStackTrace();  
        }  
        mediaPlayer.start();  
    }  
  
    public static Uri getSystemDefultNotifyUri(Context context) {
        return RingtoneManager.getActualDefaultRingtoneUri(context,
                RingtoneManager.TYPE_NOTIFICATION);
    }
    
    public static void vibrator(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启   
        vibrator.vibrate(pattern, -1); 
    }
    
}
