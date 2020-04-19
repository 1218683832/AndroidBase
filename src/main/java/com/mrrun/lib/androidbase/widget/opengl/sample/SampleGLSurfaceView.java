package com.mrrun.lib.androidbase.widget.opengl.sample;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.util.AttributeSet;

import com.mrrun.lib.androidbase.widget.opengl.CommonGLSurfaceView;
import com.mrrun.lib.androidbase.widget.opengl.Drawer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lipin on 2017/9/15.
 */

public class SampleGLSurfaceView extends CommonGLSurfaceView {

    Drawer mDrawer;

    public SampleGLSurfaceView(Context context) {
        super(context);
        init(null);
    }

    public SampleGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initGLSurfaceView();
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
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.requestRender();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        mDrawer = new Triangle(Drawer.GL_DRAW_ARRAYS);
        mDrawer = new Square(Drawer.GL_DRAW_ELEMENTS);
        mDrawer = new Circle(Drawer.GL_DRAW_ARRAYS);
        mDrawer = new Pentagon(Drawer.GL_DRAW_ELEMENTS);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        // 设置投影矩阵
        mDrawer.setupProjectionMatrix(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mDrawer.draw();
    }
}
