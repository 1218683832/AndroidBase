package com.mrrun.lib.androidbase.widget.camera.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lipin on 2017/9/13.
 * <b>类功能描述:</b><br>
 * Camera工具类.
 *
 * @author lipin
 * @version 1.0
 */

public class CameraUtils {

    private static final String TAG = CameraUtils.class.getSimpleName();

    /**
     * <b>方法功能描述:</b><br>
     * 判断手机设备是否有相机设备
     *
     * @param context
     * @return
     */
    public static boolean hasCameraDevice(Context context){
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
    /**
     * <b>方法功能描述:</b><br>
     * 判断是否支持XX对焦模式
     *
     * @param params
     * @param value
     * @return
     */
    public static boolean isXXFocusSupported(Camera.Parameters params, String value) {
        List<String> modes = params.getSupportedFocusModes();
        return modes!= null && modes.contains(value) ? true : false;
    }
    /**
     * <b>方法功能描述:</b><br>
     * 判断是否支持XX闪光灯模式
     *
     * @param params
     * @param value
     * @return
     */
    public static boolean isXXFlashSupported(Camera.Parameters params, String value) {
        List<String> modes = params.getSupportedFlashModes();
        return modes!= null && modes.contains(value) ? true : false;
    }
    /**
     * <b>方法功能描述:</b><br>
     * 判断是否支持XX照片格式
     *
     * @param params
     * @param pixel_format
     * @return
     */
    public static boolean isXXPictureFormatSupported(Camera.Parameters params, Integer pixel_format) {
        List<Integer> modes = params.getSupportedPictureFormats();
        return modes!= null && modes.contains(pixel_format) ? true : false;
    }
    /**
     * <b>方法功能描述:</b><br>
     * 判断是否支持XX预览格式
     *
     * @param params
     * @param pixel_format
     * @return
     */
    public static boolean isXXPreviewFormatSupported(Camera.Parameters params, Integer pixel_format) {
        List<Integer> modes = params.getSupportedPreviewFormats();
        return modes!= null && modes.contains(pixel_format) ? true : false;
    }
    /**
     * <b>方法功能描述:</b><br>
     * 判断是否支持XX图片分辨率
     *
     * @param params
     * @param width
     * @param height
     * @return
     */
    public static boolean isXXPictureSizeSupported(Camera.Parameters params, int width, int height) {
        boolean flag = false;
        List<Camera.Size> modes = params.getSupportedPictureSizes();
        if (modes != null && modes.size() > 0) {
            for (Camera.Size size : modes) {
                Log.i(TAG, "SupportedPictureSize: width=" + size.width + ",height=" + size.height);
                if (size.width == width && size.height == height) {
                    flag = true;
                }
            }
        }
        return flag;
    }
    /**
     * <b>方法功能描述:</b><br>
     * 判断是否支持XX预览分辨率
     *
     * @param params
     * @param width
     * @param height
     * @return
     */
    public static boolean isXXPreviewSizeSupported(Camera.Parameters params, int width, int height) {
        boolean flag = false;
        List<Camera.Size> modes = params.getSupportedPreviewSizes();
        if (modes != null && modes.size() > 0) {
            for (Camera.Size size : modes) {
                Log.i(TAG, "SupportedPreviewSize: width=" + size.width + ",height=" + size.height);
                if (size.width == width && size.height == height) {
                    flag = true;
                }
            }
        }
        return flag;
    }
    /**
     * <b>方法功能描述:</b><br>
     * 按升序重新排序
     * 1、如果要按照升序排序，则o1小于o2，返回-1（负数），相等返回0，01大于02返回1（正数）
     * 2、如果要按照降序排序，则o1小于o2，返回1（正数），相等返回0，01大于02返回-1（负数）
     *
     * @param list
     */
    public static void sortByAsc(List list){
        Collections.sort(list, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size o1, Camera.Size o2) {
                if (o1.width < o2.width) {
                    return -1;
                } else if (o1.width == o2.width){
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

    /**
     * <b>方法功能描述:</b><br>
     * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
     *
     * @param params
     *          可以得到需要对比的预览尺寸列表
     * @param width
     *          需要被进行对比的原宽
     * @param height
     *          需要被进行对比的原高
     * @return 得到与原宽高比例最接近的尺寸
     */
    public static Camera.Size getBestCameraPreviewSize(Camera.Parameters params, int width, int height){
        Camera.Size bestSize = null;
        float reqRatio = (float)width / height;// 屏幕要求的比例
        Log.i(TAG, "屏幕要求的比例:reqRatio=" + reqRatio);
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        List<Camera.Size> modes = params.getSupportedPreviewSizes();
        if (modes != null && modes.size() > 0) {
            sortByAsc(modes);
            for (Camera.Size size : modes) {
                Log.i(TAG, "SupportedPreviewSize: width=" + size.width + ",height=" + size.height);
                if (size.width == width && size.height == height) {// 查找preview中是否存在与surfaceview相同宽高的尺寸
                    bestSize = size;
                    break;
                } else {// 计算与传入的宽高比最接近的size
                    curRatio = ((float) size.width) / size.height;
                    deltaRatio = Math.abs(reqRatio - curRatio);
                    if (deltaRatio < deltaRatioMin) {
                        deltaRatioMin = deltaRatio;
                        bestSize = size;
                    }
                }
            }
        }
        if (null != bestSize)
            Log.i(TAG, "得到与宽高比最接近的尺寸是:width=" + bestSize.width + ",height=" + bestSize.height);
        return bestSize;
    }
}
