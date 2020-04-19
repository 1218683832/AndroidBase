package com.mrrun.lib.androidbase.widget.camera;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import com.mrrun.lib.androidbase.widget.camera.util.CameraUtils;

import java.io.IOException;

import static android.hardware.Camera.CameraInfo;
import static android.hardware.Camera.open;

/**
 * Created by lipin on 2017/9/12.
 * <b>类功能描述:</b><br>
 * Camera控制器接口实现类.
 *
 * @author lipin
 * @version 1.0
 */

public class CameraController implements ICameraController, Camera.PreviewCallback {

    private static final String TAG = CameraController.class.getSimpleName();

    Context mContext;
    /**
     * Camera对象，相机预览
     */
    Camera mCamera = null;
    /**
     * Camera对象设置参数
     */
    Camera.Parameters mParameters = null;
    /**
     * 是否已开始预览，默认false
     */
    boolean mPreViewStarted = false;
    /**
     * 横竖屏镜头自动调整,开始预览的方向为竖屏
     */
    String mOrientation = CameraHelper.OrientationHelper.ORIENTATION_AUTO;

    OnPreviewFrameListener mOnPreviewFrameListener = null;

    public void setOnPreviewFrameListener(OnPreviewFrameListener onPreviewFrameListener) {
        this.mOnPreviewFrameListener = onPreviewFrameListener;
    }

    public CameraController(Context context) {
        this.mContext = context;
        buildCamera();
    }

    /**
     * <b>方法功能描述:</b><br>
     * 构造相机
     *
     * @return
     */
    private CameraController buildCamera() {
        if(mCamera == null){
            mCamera = open(CameraInfo.CAMERA_FACING_BACK);// 开启摄像头(2.3版本后支持多摄像头,需传入参数)
        }
        /* Camera Service settings */
        mParameters = mCamera.getParameters();
        setCameraPictureFormat(CameraHelper.FormatHelper.JPEG);// 设置相片格式为JPEG，默认为NV21(YCbCr_420_SP)
        setCameraPreviewFormat(CameraHelper.FormatHelper.NV21);// Sets the image format for preview pictures.默认为NV21(YCbCr_420_SP).常用格式：NV21/YV12
        setCameraFlashMode(CameraHelper.FlashHelper.FLASH_MODE_OFF);// 设置相机闪关灯模式
        // 设置拍照和预览图片大小
        // 如果这两个属性设置的和真实手机的不一样时，画面拉伸
        // setCameraPictureSize(1280, 720);// 设置拍照图片的大小
        // setCameraPreviewSize(1280, 720);// 设置preview的大小
        setCameraAutoFocused(true);// 设置相机是否自动对焦
        setCameraOrientation(CameraHelper.OrientationHelper.ORIENTATION_AUTO);// 设置相机摄像头方向
        // 这是最后一步一定要有,不然没有效果
        setCameraParameters();// 设定配置参数,将Camera.Parameters设定于Camera
        Log.i(TAG, "buildCamera");
        return this;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 设置相机是否自动对焦
     *
     * @param autoFocused
     */
    @Override
    public CameraController setCameraAutoFocused(boolean autoFocused) {
        if (mCamera == null || mParameters == null) {
            return this;
        }
        if (autoFocused) {// Camera自动对焦
            if (CameraUtils.isXXFocusSupported(mParameters,
                    CameraHelper.FocusHelper.FOCUS_MODE_CONTINUOUS_VIDEO)){
                mParameters.setFocusMode(CameraHelper.FocusHelper.FOCUS_MODE_CONTINUOUS_VIDEO);// 设置对焦模式为持续对焦
            }
        }else {// Camera不自动对焦
        }
        return this;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 设置相机摄像头方向
     *
     * @param orientation
     */
    @Override
    public CameraController setCameraOrientation(String orientation) {
        if (mCamera == null || mParameters == null) {
            return this;
        }
        mOrientation = orientation;
        switch (mOrientation) {
            case CameraHelper.OrientationHelper.ORIENTATION_AUTO:// 横竖屏镜头自动调整
                if (mContext.getResources().getConfiguration().orientation
                        != Configuration.ORIENTATION_LANDSCAPE){// 如果是竖屏
                    mParameters.set(CameraHelper.OrientationHelper.ORIENTATION,
                            CameraHelper.OrientationHelper.ORIENTATION_PORTRAIT);
                    mParameters.set(CameraHelper.RotationHelper.ROTATION,
                            CameraHelper.RotationHelper.ROTATION_90);// 镜头角度转90度（默认摄像头是横拍，预览开始的方向为竖屏
                    mCamera.setDisplayOrientation(90);// 在2.2以上可以使用
                } else {// 如果是横屏
                    mParameters.set(CameraHelper.OrientationHelper.ORIENTATION,
                            CameraHelper.OrientationHelper.ORIENTATION_LANDSCAPE);
                    mParameters.set(CameraHelper.RotationHelper.ROTATION,
                            CameraHelper.RotationHelper.ROTATION_0);
                    mCamera.setDisplayOrientation(0);// 在2.2以上可以使用
                }
                break;
            case CameraHelper.OrientationHelper.ORIENTATION_LANDSCAPE:// 横屏镜头
                break;
            case CameraHelper.OrientationHelper.ORIENTATION_PORTRAIT:// 竖屏镜头
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 设置相机闪关灯模式
     *
     * @param value
     */
    @Override
    public CameraController setCameraFlashMode(String value) {
        if (mCamera == null || mParameters == null) {
            return this;
        }
        if (CameraUtils.isXXFlashSupported(mParameters, value)) {
            mParameters.setFlashMode(value);
        }
        return this;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 设置相片格式
     *
     * @param pixel_format
     * @return
     */
    @Override
    public CameraController setCameraPictureFormat(int pixel_format) {
        if (mCamera == null || mParameters == null) {
            return this;
        }
        if (CameraUtils.isXXPictureFormatSupported(mParameters, pixel_format)) {
            mParameters.setPictureFormat(pixel_format);
        }
        return this;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 设置预览格式
     *
     * @param pixel_format
     * @return
     */
    @Override
    public CameraController setCameraPreviewFormat(int pixel_format) {
        if (mCamera == null || mParameters == null) {
            return  this;
        }
        if (CameraUtils.isXXPreviewFormatSupported(mParameters, pixel_format)){
            mParameters.setPreviewFormat(pixel_format);
        }
        return this;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 设置照片分辨率大小,默认是640*480
     *
     * @param width
     * @param height
     * @return
     */
    @Override
    public CameraController setCameraPictureSize(int width, int height) {
        if (mCamera == null || mParameters == null) {
            return this;
        }
        if (CameraUtils.isXXPictureSizeSupported(mParameters, width, height)) {
        }
        mParameters.setPictureSize(width, height);
        return this;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 设置预览分辨率大小,默认是640*480
     *
     * @param width
     * @param height
     * @return
     */
    @Override
    public CameraController setCameraPreviewSize(int width, int height) {
        if (mCamera == null || mParameters == null) {
            return this;
        }
        Camera.Size bestSize = CameraUtils.getBestCameraPreviewSize(mParameters, width, height);
        if (null != bestSize)
            mParameters.setPreviewSize(bestSize.width, bestSize.height);
        return this;
    }

    byte[] mPreBuffer;
    /**
     * <b>方法功能描述:</b><br>
     * 设置配置参数,将Camera.Parameters设定于Camera
     *
     * @return
     */
    @Override
    public CameraController setCameraParameters() {
        if (mCamera == null || mParameters == null) {
            return this;
        }
        mCamera.setParameters(mParameters);// 设定配置参数,将Camera.Parameters设定于Camera
        Camera.Size ps = mCamera.getParameters().getPictureSize();
        Camera.Size s = mCamera.getParameters().getPreviewSize();
        mPreBuffer = new byte[((s.width * s.height) * ImageFormat.getBitsPerPixel(
                mCamera.getParameters().getPreviewFormat())) / 8];
        this.mCamera.addCallbackBuffer(mPreBuffer);
        this.mCamera.setPreviewCallbackWithBuffer(this);// 为了能够更好地管理处理预览图像时使用的内存
        Log.i(TAG, "图片width=" + ps.width + ",pheight=" + ps.height);
        Log.i(TAG, "预览width=" + s.width + ",height=" + s.height);
        return this;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 开启相机预览
     *
     * @param holder
     */
    @Override
    public void startCameraPreView(SurfaceHolder holder) {
        if(mCamera != null && !mPreViewStarted){
            try {
                mCamera.setPreviewDisplay(holder);// set the surface to be used for live preview
                mCamera.startPreview();// 打开预览画面
                mPreViewStarted = true;
            } catch (IOException e) {
                if (mCamera != null) {
                    mCamera.release();
                    mCamera = null;
                    mPreViewStarted = false;
                }
            }
        }
        Log.i(TAG, "startCameraPreView");
    }

    public void startCameraPreView2(SurfaceTexture surfaceTexture) {
        if(mCamera != null && !mPreViewStarted){
            try {
                mCamera.setPreviewTexture(surfaceTexture);// set the surface to be used for live preview
                mCamera.startPreview();// 打开预览画面
                mPreViewStarted = true;
            } catch (IOException e) {
                if (mCamera != null) {
                    mCamera.release();
                    mCamera = null;
                    mPreViewStarted = false;
                }
            }
        }
        Log.i(TAG, "startCameraPreView");
    }

    /**
     * <b>方法功能描述:</b><br>
     * 停止相机预览
     */
    @Override
    public void stopCameraPreView() {
        if(mCamera != null && mPreViewStarted){
            mCamera.setPreviewCallback(null);
            mCamera.setPreviewCallbackWithBuffer(null);
            mCamera.stopPreview();
            mPreViewStarted = false;
        }
        Log.i(TAG, "stopCameraPreView");
    }

    /**
     * <b>方法功能描述:</b><br>
     * 重新启动相机预览
     *
     * @param holder
     */
    @Override
    public void reStartCameraPreView(SurfaceHolder holder,
                                     OnReStartCameraPreViewListener onReStartCameraPreViewListener ) {
        if (mCamera == null) {
            buildCamera();
        } else {
            stopCameraPreView();
        }
        if (null != onReStartCameraPreViewListener){
            onReStartCameraPreViewListener.onSetNewParameterCallBack();
        }
        startCameraPreView(holder);
        Log.i(TAG, "reStartCameraPreView");
    }

    /**
     * <b>方法功能描述:</b><br>
     * 重新启动相机预览
     *
     * @param surfaceTexture
     */
    @Override
    public void reStartCameraPreView2(SurfaceTexture surfaceTexture,
                                     OnReStartCameraPreViewListener onReStartCameraPreViewListener ) {
        if (mCamera == null) {
            buildCamera();
        } else {
            stopCameraPreView();
        }
        if (null != onReStartCameraPreViewListener){
            onReStartCameraPreViewListener.onSetNewParameterCallBack();
        }
        startCameraPreView2(surfaceTexture);
        Log.i(TAG, "reStartCameraPreView");
    }

    /**
     * <b>方法功能描述:</b><br>
     * 退出相机
     * 停止预览并释放摄像头资源
     */
    @Override
    public void exitCamera() {
        if (mCamera != null) {
            stopCameraPreView();
            mCamera.release();
            mCamera = null;
        }
        Log.i(TAG, "exitCamera");
    }

    /**
     * <b>方法功能描述:</b><br>
     * 获取相机是否已开始预览
     *
     * @return
     */
    public boolean isPreViewStarted() {
        return mPreViewStarted;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (data == null) {
            return;
        }
        if (mOnPreviewFrameListener != null) {
            mOnPreviewFrameListener.doFrame(data, camera);
        }
        this.mCamera.addCallbackBuffer(mPreBuffer);
    }
}
