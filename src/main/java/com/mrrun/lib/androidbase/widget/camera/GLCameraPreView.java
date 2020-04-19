package com.mrrun.lib.androidbase.widget.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.util.AttributeSet;
import android.util.Log;

import com.mrrun.lib.androidbase.widget.opengl.CommonGLSurfaceView;
import com.mrrun.lib.androidbase.widget.opengl.Drawer;
import com.mrrun.lib.androidbase.widget.opengl.GLUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lipin on 2017/9/14.
 * GLSurfaceView是专门用来渲染绘制OpenGL ES图形,真正的渲染绘制是由GLSurfaceView.Renderer来完成.
 */

public class GLCameraPreView extends CommonGLSurfaceView {

    private static final String TAG = GLCameraPreView.class.getSimpleName();

    private int mTextureID = 0;

    private DirectDrawer mDirectDrawer;

    private CameraController mController;

    public GLCameraPreView(Context context) {
        super(context);
        this.mContext = context;
        init(null);
    }

    public GLCameraPreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initGLSurfaceView();
        initController();
    }

    private void initController() {
        mController = new CameraController(mContext);
    }

    private void initGLSurfaceView() {
        // 设置OpenGl ES的版本为2.0
        setEGLContextClientVersion(2);
        // 设置与当前GLSurfaceView绑定的Renderer
        setRenderer(this);
        // 设置渲染的模式
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onPause() {
        super.onPause();
        mController.stopCameraPreView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mController.exitCamera();
    }

    // 在有新数据到来时，会被调用，在其中调用requestRender()，就可以完成新数据的渲染
    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        Log.i(TAG, "onFrameAvailable...");
        this.requestRender();
    }

    // 系统在创建GLSurfaceView时调用它一次。我们可以使用它来设置OpenGL的环境变量，或是初始化OpenGL的图形物体。
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i(TAG, "onSurfaceCreated...");
        mTextureID = GLUtils.createTextureID();// 创建纹理ID
        mSurface = new SurfaceTexture(111);// 根据纹理ID创建SurfaceTexture绑定在一起
        mSurface.setOnFrameAvailableListener(this);// 通知GLSurfaceView什么时候需要渲染图像
        mDirectDrawer = new DirectDrawer(Drawer.GL_DRAW_ELEMENTS);
        // 纹理ID和DirectDrawer绑定在一起，DirectDrawer用来绘制图像
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);// 设置背景帧颜色
    }

    // 系统在GLSurfaceView的几何属性发生改变时调用该方法，包括大小或是设备屏幕的方向发生变化。
    // 例如，系统在屏幕从直立变为水平使调用它。这个方法主要用来对GLSurfaceView容器的变化进行响应。
    @Override
    public void onSurfaceChanged(GL10 gl, final int width, final int height) {
        Log.i(TAG, "onSurfaceChanged...,width=" + width + ",height=" + height);
        // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(w,h)指定了视口的大小
        GLES20.glViewport(0, 0, width, height);
        mDirectDrawer.setupProjectionMatrix(width, height);
        mController.reStartCameraPreView2(mSurface,
                new ICameraController.OnReStartCameraPreViewListener() {
                    @Override
                    public void onSetNewParameterCallBack() {
                        mController
                                // 预览开始的方向为竖屏，宽高调换
                                .setCameraPreviewSize(height, width)
                                .setCameraParameters();
                    }
                });
    }

    // 系统在每次重绘GLSurfaceView时调用这个方法。这个方法主要完成绘制图形的操作。
    @Override
    public void onDrawFrame(GL10 gl) {
        Log.i(TAG, "onDrawFrame...");
        // 清除屏幕和深度缓存
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        // 更新纹理
        mSurface.updateTexImage();
        mDirectDrawer.draw();
    }
}
