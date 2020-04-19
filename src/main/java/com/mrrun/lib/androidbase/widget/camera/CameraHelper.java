package com.mrrun.lib.androidbase.widget.camera;

import android.graphics.ImageFormat;
import android.hardware.Camera;

/**
 * Created by lipin on 2017/9/12.
 * <b>类功能描述:</b><br>
 * Camera帮助类.
 * 1、提供相机预览方向参数配置
 * 2、提供相机预览角度配置
 * 3、提供闪光灯参数配置
 * 4、提供对焦模式参数配置
 * 5、提供场景模式参数配置
 * 6、提供媒体格式参数配置
 *
 * @author lipin
 * @version 1.0
 */

public class CameraHelper {
    /**
     * <b>类功能描述:</b><br>
     * 相机预览方向参数配置
     */
    public static class OrientationHelper {
        public static final String ORIENTATION = "orientation";// 摄像头方向
        public static final String ORIENTATION_AUTO= "auto";// 摄像头横竖屏自动调整
        public static final String ORIENTATION_PORTRAIT= "portrait";// 摄像头竖拍
        public static final String ORIENTATION_LANDSCAPE = "landscape";// 摄像头横拍
    }
    /**
     * <b>类功能描述:</b><br>
     * 相机预览角度配置
     */
    public static class RotationHelper {
        public static final String ROTATION = "rotation";// 镜头角度
        public static final int ROTATION_0 = 0;// 镜头角度转0度
        public static final int ROTATION_90 = 90;// 镜头角度转90度
        public static final int ROTATION_180 = 180;// 镜头角度转180度
        public static final int ROTATION_270 = 270;// 镜头角度转270度
    }
    /**
     * <b>类功能描述:</b><br>
     * 闪光灯参数配置
     */
    public static class FlashHelper {
        public static final String FLASH_MODE_AUTO = Camera.Parameters.FLASH_MODE_AUTO;// 自动模式，当光线较暗时自动打开闪光灯
        public static final String FLASH_MODE_OFF = Camera.Parameters.FLASH_MODE_OFF;// 关闭闪光灯
        public static final String FLASH_MODE_ON = Camera.Parameters.FLASH_MODE_ON;// 拍照时闪光灯
        public static final String FLASH_MODE_RED_EYE = Camera.Parameters.FLASH_MODE_RED_EYE;// 闪光灯参数，防红眼模式，科普一下：防红眼
    }
    /**
     * <b>类功能描述:</b><br>
     * 对焦模式参数配置
     */
    public static class FocusHelper {
        public static final String FOCUS_MODE_AUTO = Camera.Parameters.FOCUS_MODE_AUTO;// 自动对焦模式，摄影小白专用模式
        public static final String FOCUS_MODE_FIXED = Camera.Parameters.FOCUS_MODE_FIXED;// 固定焦距模式，拍摄老司机模式
        public static final String FOCUS_MODE_EDOF = Camera.Parameters.FOCUS_MODE_EDOF;// 景深模式，文艺女青年最喜欢的模式
        public static final String FOCUS_MODE_INFINITY = Camera.Parameters.FOCUS_MODE_INFINITY;// 远景模式，拍风景大场面的模式
        public static final String FOCUS_MODE_MACRO = Camera.Parameters.FOCUS_MODE_MACRO;// 微焦模式，拍摄小花小草小蚂蚁专用模式
        public static final String FOCUS_MODE_CONTINUOUS_VIDEO = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO;// 视频持续对焦模式
        public static final String FOCUS_MODE_CONTINUOUS_PICTURE = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE;// 拍照持续对焦模式
    }
    /**
     * <b>类功能描述:</b><br>
     * 场景模式参数配置
     */
    public static class SceneHelper {
        public static final String SCENE_MODE_BARCODE = Camera.Parameters.SCENE_MODE_BARCODE;// 扫描条码场景，NextQRCode项目会判断并设置为这个场景
        public static final String SCENE_MODE_ACTION = Camera.Parameters.SCENE_MODE_ACTION;// 动作场景，就是抓拍跑得飞快的运动员、汽车等场景用的
        public static final String SCENE_MODE_AUTO = Camera.Parameters.SCENE_MODE_AUTO;// 自动选择场景
        public static final String SCENE_MODE_HDR = Camera.Parameters.SCENE_MODE_HDR;// 高动态对比度场景，通常用于拍摄晚霞等明暗分明的照片
        public static final String SCENE_MODE_NIGHT = Camera.Parameters.SCENE_MODE_NIGHT;// 夜间场景
    }
    /**
     * <b>类功能描述:</b><br>
     * 媒体格式参数配置
     */
    public static class FormatHelper {
        public static final int JPEG = ImageFormat.JPEG;
        public static final int YUY2 = ImageFormat.YUY2;// 摄像头Camera采集的原始数据
        public static final int NV21 = ImageFormat.NV21;// 摄像头Camera采集的原始数据
    }
}
