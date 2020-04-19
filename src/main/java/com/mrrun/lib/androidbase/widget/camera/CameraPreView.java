package com.mrrun.lib.androidbase.widget.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by lipin on 2017/9/12.
 * <b>类功能描述:</b><br>
 * Camera普通预览窗口.支持使用相机进行简单操作.
 * 1、照片拍摄
 * 2、视频录制
 *
 * @author lipin
 * @version 1.0
 */

public class CameraPreView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = CameraPreView.class.getSimpleName();

    Context mContext;
    /**
     * SurfaceHolder对象：(抽象接口)SurfaceView支持类
     */
    SurfaceHolder mSurfaceHolder = null;
    /**
     * 相机控制器
     */
    CameraController mController = null;

    public CameraPreView(Context context) {
        super(context);
        this.mContext = context;
        init(null);
    }

    public CameraPreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public CameraPreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 组件初始化
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        initSurfaceView();
        initController();
    }

    private void initController() {
        mController = new CameraController(mContext);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 初始化SufaceView控件
     */
    private void initSurfaceView() {
        mSurfaceHolder = this.getHolder();// 取得SurfaceHolder对象
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 设置显示器类型，setType必须设置
        mSurfaceHolder.addCallback(this);// SurfaceHolder加入回调接口
    }

    public CameraController getController() {
        return mController;
    }

    /**
     * <b>方法功能描述:</b><br>
     * 在surfaceCreated方法中开启预览
     *
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
    }



    /**
     * <b>方法功能描述:</b><br>
     * 重新设置相机参数,开启预览
     *
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, final int width, final int height) {
        mSurfaceHolder = holder;
        mSurfaceHolder.setFixedSize(width, height);// 设置预览大小
        mController.reStartCameraPreView(mSurfaceHolder,
                new ICameraController.OnReStartCameraPreViewListener() {
            @Override
            public void onSetNewParameterCallBack() {
                mController
                        // 预览开始的方向为竖屏，宽高调换
                        .setCameraPreviewSize(height, width)
                        .setCameraParameters();
            }
        });
        Log.i(TAG,  "width=" + width + ",height=" + height);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 在surfaceDestroyed中销毁视频预览
     *
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mController.exitCamera();
    }
}
