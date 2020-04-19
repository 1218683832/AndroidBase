package com.mrrun.lib.androidbase.widget.opengl;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lipin on 2017/9/15.
 * GLSurfaceView是专门用来渲染绘制OpenGL ES图形,真正的渲染绘制是由GLSurfaceView.Renderer来完成.
 */

public abstract class CommonGLSurfaceView extends GLSurfaceView
        implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener{

    protected Context mContext;

    protected SurfaceTexture mSurface;

    public CommonGLSurfaceView(Context context) {
        super(context);
        this.mContext = context;
    }

    public CommonGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.requestRender();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
    }

    @Override
    public void onDrawFrame(GL10 gl) {
    }
}
