package com.mrrun.lib.androidbase.widget.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;

/**
 * Created by lipin on 2017/9/12.
 * <b>类功能描述:</b><br>
 * Camera控制器接口
 *
 * @author lipin
 * @version 1.0
 */

public interface ICameraController {
    interface OnReStartCameraPreViewListener{
        /**
         * <b>方法功能描述:</b><br>
         * 重新给相机设置新参数
         */
        void onSetNewParameterCallBack();
    }
    interface OnPreviewFrameListener{
        void doFrame(byte[] data, Camera camera);
    }
    /**
     * <b>方法功能描述:</b><br>
     * 设置相机是否自动对焦
     *
     * @param autoFocused
     */
    CameraController setCameraAutoFocused(boolean autoFocused);
    /**
     * <b>方法功能描述:</b><br>
     * 设置相机摄像头方向
     *
     * @param orientation
     */
    CameraController setCameraOrientation(String orientation);
    /**
     * <b>方法功能描述:</b><br>
     * 设置相机闪关灯模式
     *
     * @param value
     */
    CameraController setCameraFlashMode(String value);
    /**
     * <b>方法功能描述:</b><br>
     * 设置相片格式
     *
     * @param pixel_format
     * @return
     */
    CameraController setCameraPictureFormat(int pixel_format);
    /**
     * <b>方法功能描述:</b><br>
     * 设置预览格式
     *
     * @param pixel_format
     * @return
     */
    CameraController setCameraPreviewFormat(int pixel_format);
    /**
     * <b>方法功能描述:</b><br>
     * 设置照片分辨率大小
     *
     * @param width
     * @param height
     * @return
     */
    CameraController setCameraPictureSize(int width, int height);
    /**
     * <b>方法功能描述:</b><br>
     * 设置预览分辨率大小
     *
     * @param width
     * @param height
     * @return
     */
    CameraController setCameraPreviewSize(int width, int height);
    /**
     * <b>方法功能描述:</b><br>
     * 设置配置参数,将Camera.Parameters设定于Camera
     *
     * @return
     */
    CameraController setCameraParameters();
    /**
     * <b>方法功能描述:</b><br>
     * 开启相机预览
     *
     * @param holder
     */
    void startCameraPreView(SurfaceHolder holder);
    /**
     * <b>方法功能描述:</b><br>
     * 停止相机预览
     */
    void stopCameraPreView();
    /**
     * <b>方法功能描述:</b><br>
     * 重新启动相机预览
     *
     * @param holder
     */
    void reStartCameraPreView(SurfaceHolder holder,
                              OnReStartCameraPreViewListener onReStartCameraPreViewListener);
    /**
     * <b>方法功能描述:</b><br>
     * 重新启动相机预览
     *
     * @param surfaceTexture
     */
    void reStartCameraPreView2(SurfaceTexture surfaceTexture,
                               OnReStartCameraPreViewListener onReStartCameraPreViewListener);
    /**
     * <b>方法功能描述:</b><br>
     * 退出相机
     * 停止预览并释放摄像头资源
     */
    void exitCamera();
}
