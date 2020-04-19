package com.mrrun.lib.androidbase.util;

import android.annotation.SuppressLint;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <b>类功能描述:</b><br>
 * 时间工具类<br>
 *
 * @author lipin
 * @version 1.0
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {

    /**
     * 如:yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private TimeUtils() {
    }

    /**
     * <b>方法功能描述:</b><br>
     * 时间毫秒转化成特定格式的字符串时间
     *
     * @param timeInMillis 毫秒
     * @param dateFormat   指定格式
     * @return String 特定格式的字符串时间
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        if (timeInMillis <= 0) {
            return "";
        }
        return dateFormat.format(new Date(timeInMillis));
    }

    public static void showTime(long time, TextView view) {
        long showtime = time;// 总共多少秒
        long hours = 0;// 总小时
        long minutes = showtime / 60;
        long seconds = showtime % 60;
        if (minutes > 60) {
            hours = minutes / 60;
            minutes = minutes % 60;
        }
        StringBuilder sb = new StringBuilder();
        if (hours < 10) {
            sb.append("0");
        }
        sb.append(hours);
        sb.append("时");
        if (minutes < 10) {
            sb.append("0");
        }
        sb.append(minutes);
        sb.append("钟");
        if (seconds < 10) {
            sb.append("0");
        }
        sb.append(seconds);
        sb.append("秒");
        view.setText(String.format("%s", sb.toString()));
    }

    public static void showTime2(long time, TextView view) {
        long showtime = time / 1000;// 总共多少秒
        long hours = 0;// 总小时
        long minutes = showtime / 60;
        long seconds = showtime % 60;
        if (minutes > 60) {
            hours = minutes / 60;
            minutes = minutes % 60;
        }
        StringBuilder sb = new StringBuilder();
        if (0 != hours) {
            sb.append(hours);
            sb.append(":");
        }
        if (minutes < 10) {
            sb.append("0");
        }
        sb.append(minutes);
        sb.append(":");
        if (seconds < 10) {
            sb.append("0");
        }
        sb.append(seconds);
        view.setText(String.format("%s", sb.toString()));
    }

    private static String unitFormat(long i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + i;
        else
            retStr = "" + i;
        return retStr;
    }

    public static String secToTime(long time) {
        String timeStr = null;
        long hour = 0;
        long minute = 0;
        long second = 0;
        if (time <= 0)
            return String.valueOf(0);
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "59:59:99";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
            return timeStr;
        }
    }

    /**
     * <b>方法功能描述:</b><br>
     * 时间毫秒转化成特定格式的字符串时间,指定格式是{@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis 毫秒
     * @return String 特定格式的字符串时间
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 获得当前时间， 以毫秒为单位
     *
     * @return long 毫秒
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * <b>方法功能描述:</b><br>
     * 获得当前时间,指定格式是 {@link #DEFAULT_DATE_FORMAT}
     *
     * @return String 特定格式的字符串时间
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * <b>方法功能描述:</b><br>
     * 获得当前时间，以毫秒为单位
     *
     * @param dateFormat 指定格式
     * @return String 特定格式的字符串时间
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {

        return getTime(getCurrentTimeInLong(), dateFormat);
    }
}