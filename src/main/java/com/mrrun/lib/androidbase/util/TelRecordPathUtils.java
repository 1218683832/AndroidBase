package com.mrrun.lib.androidbase.util;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TelRecordPathUtils {

    private TelRecordPathUtils() {
    }

    private static class FileComparator implements Comparator<File> {
        @Override
        public int compare(File o1, File o2) {
            if (o1.lastModified() < o2.lastModified()) {
                return 1;// 最后修改的文件在前
            } else {
                return -1;
            }

        }
    }

    public static String getRootPath() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        String rootFilePath = "";
        String brand = android.os.Build.BRAND.toLowerCase();
        switch (brand) {
            case "xiaomi":
                rootFilePath = new File(externalStorageDirectory, "MIUI/sound_recorder/call_rec").getAbsolutePath();
                break;
            case "honor":
                rootFilePath = new File(externalStorageDirectory, "Sounds/Contact").getAbsolutePath();
                break;
            case "huawei":
                rootFilePath = new File(externalStorageDirectory, "record").getAbsolutePath();
                break;
            case "360":
                rootFilePath = new File(externalStorageDirectory, "360OS/My Records/Call Records").getAbsolutePath();
                break;
            case "oppo":
                rootFilePath = new File(externalStorageDirectory, "Recordings").getAbsolutePath();
                break;
            default:
                rootFilePath = "";
                break;
        }
        return rootFilePath;
    }

    public static String getRecordFilePath(long callTime, String telPhone) {
        String recordFilePath = "";
        if (callTime <= 0L || TextUtils.isEmpty(telPhone)) {
            return recordFilePath;
        }
        List<File> files = FileUtils.getChildFiles(TelRecordPathUtils.getRootPath());
        if (Lang.count(files) > 0) {
            Collections.sort(files, new FileComparator());
            for (int i = files.size() - 1; i >= 0; i--) {
                if (files.get(i).lastModified() >= callTime) {
                    if (files.get(i).getAbsolutePath().replace(" ", "").contains(telPhone)) {
                        recordFilePath = files.get(i).getPath();
                        break;
                    }
                }
            }
        }
        return recordFilePath;
    }
}
