package com.mrrun.lib.androidbase.widget.opengl.sample;

import android.opengl.GLES20;

import com.mrrun.lib.androidbase.R;
import com.mrrun.lib.androidbase.base.app.BaseApp;
import com.mrrun.lib.androidbase.widget.opengl.Drawer;
import com.mrrun.lib.androidbase.widget.opengl.GLUtils;

/**
 * Created by lipin on 2017/9/15.
 * Define a Triangle
 */

public class Triangle extends Drawer {

    public Triangle(int glDrawWay) {
        super(glDrawWay);
        init();
    }

    public void setupHandle() {
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, Drawer.VPOSITION);
        mColorHandle = GLES20.glGetUniformLocation(mProgram, Drawer.VCOLOR);
    }

    public void setupColor() {
        mColor = new float[] {0.6f, 0.7f, 0.2f, 1.0f};
    }

    public void setupProgram() {
        mVertextShaderCode = GLUtils.readTextFileFromResource(
                BaseApp.appContext, R.raw.triangle_vertex_shader);
        mFragmentShaderCode = GLUtils.readTextFileFromResource(
                BaseApp.appContext, R.raw.triangle_fragment_shader);
        mProgram = GLUtils.createProgram(mVertextShaderCode, mFragmentShaderCode);
        if (mProgram == 0) {
            throw new RuntimeException("Unable to create program");
        }
        GLES20.glUseProgram(mProgram);// 将program添加到OpenGL ES环境中
    }

    public void setupVertex() {
        mVertexCoords = new float[]{// 以逆时针顺序:
                0.0f,  0.4f, 0.0f,// top
                -0.6f, -0.4f, 0.0f,// bottom left
                0.4f, -0.3f, 0.0f// bottom right
        };
        mVertexCount = mVertexCoords.length / COORDS_PER_VERTEX;
        mVertexBuffer = GLUtils.getFloatBuffer(mVertexCoords);
    }

    @Override
    public void draw() {
        if (GLUtils.checkLocation(mPositionHandle, Drawer.VPOSITION)
                && mVertexBuffer != null) {
            GLES20.glEnableVertexAttribArray(mPositionHandle);// 启用顶点着色器(VertextShader)中vPosition(顶点)的句柄
            GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT, false, VERTEX_STRIDE, mVertexBuffer);// 设置顶点坐标数据
        }
        if (GLUtils.checkLocation(mColorHandle, Drawer.VCOLOR)
                && mColor != null) {
            GLES20.glUniform4fv(mColorHandle, 1, mColor, 0);// 传入颜色数据
        }
        if (GLUtils.checkLocation(mTextureCoordHandle, Drawer.INPUTTEXTURECOORDINATE)
                && mTextureCoordsBuffer != null) {
            GLES20.glEnableVertexAttribArray(mTextureCoordHandle);// 启用顶点着色器(VertextShader)中inputTextureCoordinate(Texture)的句柄
            GLES20.glVertexAttribPointer(mTextureCoordHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false,
                    VERTEX_STRIDE, mTextureCoordsBuffer);// 设置Texture坐标数据
        }
        if (GLUtils.checkLocation(mMVPMatrixHandle, Drawer.UMVPMATRIX) && mMVP != null) {
            GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVP, 0);// 传入投影矩阵
        }
        switch (mGLDrawWay) {
            case GL_DRAW_ARRAYS:
                // - mode——指明渲染哪一种图元。允许的符号常量有GL_POINTS,GL_LINE_STRIP,GL_LINE_LOOP, GL_LINES,
                //           GL_TRIANGLE_STRIP,GL_TRIANGLE_FAN和GL_TRIANGLES。
                // - first——指明在允许访问的矩阵中的起始索引。
                // - count——指明要渲染的索引的数量。
                // 绘制图形，这里采用glDrawArrays方法(默认是逆时针方向)
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, mVertexCount);
                break;
            case GL_DRAW_ELEMENTS:
                // 绘制图形，glDrawElements(绘制方向自定)
                GLES20.glDrawElements(GLES20.GL_TRIANGLES, mDrawOrder.length,
                        GLES20.GL_UNSIGNED_SHORT, mDrawOrderBuffer);
                break;
        }
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTextureCoordHandle);
    }
}
