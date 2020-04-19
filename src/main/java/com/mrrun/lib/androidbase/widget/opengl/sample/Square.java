package com.mrrun.lib.androidbase.widget.opengl.sample;

import android.opengl.GLES20;

import com.mrrun.lib.androidbase.R;
import com.mrrun.lib.androidbase.base.app.BaseApp;
import com.mrrun.lib.androidbase.widget.opengl.Drawer;
import com.mrrun.lib.androidbase.widget.opengl.GLUtils;

/**
 * Created by lipin on 2017/9/15.
 * Define a Square
 */

public class Square extends Drawer {

    public Square(int glDrawWay) {
        super(glDrawWay);
        init();
    }

    @Override
    public void setupVertex() {
        this.mVertexCoords = new float[]{// 以逆时针顺序:
                -0.5f, 0.5f, 0.0f,// top left
                -0.5f, -0.5f, 0.0f,// bottom left
                0.5f, -0.5f, 0.0f,// bottom right
                0.5f, 0.5f, 0.0f// top right;
        };
        this.mVertexCount = this.mVertexCoords.length / COORDS_PER_VERTEX;
        this.mVertexBuffer = GLUtils.getFloatBuffer(this.mVertexCoords);
    }

    @Override
    public void setupDrawOrder() {
        this.mDrawOrder = new short[]{ 0, 1, 2, 0, 2, 3 }; // 顶点绘制顺序
        this.mDrawOrderBuffer = GLUtils.getShortBuffer(this.mDrawOrder);
    }

    @Override
    public void setupColor() {
        this.mColor = new float[]{ 0.6f, 0.7f, 0.2f, 1.0f };
    }

    @Override
    public void setupProgram() {
        this.mVertextShaderCode = GLUtils.readTextFileFromResource(
                BaseApp.appContext, R.raw.square_vertex_shader);
        this.mFragmentShaderCode = GLUtils.readTextFileFromResource(
                BaseApp.appContext, R.raw.square_fragment_shader);
        this.mProgram = GLUtils.createProgram(this.mVertextShaderCode, this.mFragmentShaderCode);
        if (this.mProgram == 0) {
            throw new RuntimeException("Unable to create program");
        }
        GLES20.glUseProgram(this.mProgram);
    }

    @Override
    public void setupHandle() {
        this.mPositionHandle = GLES20.glGetAttribLocation(this.mProgram, Drawer.VPOSITION);
        this.mColorHandle = GLES20.glGetUniformLocation(this.mProgram, Drawer.VCOLOR);
        this.mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, Drawer.UMVPMATRIX);
    }

    @Override
    public void draw() {
        if (GLUtils.checkLocation(this.mPositionHandle, Drawer.VPOSITION)
                && this.mVertexBuffer != null) {
            GLES20.glEnableVertexAttribArray(this.mPositionHandle);// 启用顶点着色器(VertextShader)中vPosition(顶点)的句柄
            GLES20.glVertexAttribPointer(this.mPositionHandle, COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT, false, VERTEX_STRIDE, this.mVertexBuffer);// 设置顶点坐标数据
        }
        if (GLUtils.checkLocation(this.mColorHandle, Drawer.VCOLOR)
                && this.mColor != null) {
            GLES20.glUniform4fv(this.mColorHandle, 1, this.mColor, 0);// 传入颜色数据
        }
        if (GLUtils.checkLocation(this.mMVPMatrixHandle, Drawer.UMVPMATRIX) && this.mMVP != null) {
            GLES20.glUniformMatrix4fv(this.mMVPMatrixHandle, 1, false, this.mMVP, 0);// 传入投影矩阵
        }
        switch (this.mGLDrawWay) {
            case GL_DRAW_ARRAYS:
                // - mode——指明渲染哪一种图元。允许的符号常量有GL_POINTS,GL_LINE_STRIP,GL_LINE_LOOP, GL_LINES,
                //           GL_TRIANGLE_STRIP,GL_TRIANGLE_FAN和GL_TRIANGLES。
                // - first——指明在允许访问的矩阵中的起始索引。
                // - count——指明要渲染的索引的数量。
                // 绘制图形，这里采用glDrawArrays方法(默认是逆时针方向)
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, this.mVertexCount);
                break;
            case GL_DRAW_ELEMENTS:
                // 绘制图形，glDrawElements(绘制方向自定)
                GLES20.glDrawElements(GLES20.GL_TRIANGLES, this.mDrawOrder.length,
                        GLES20.GL_UNSIGNED_SHORT, this.mDrawOrderBuffer);
                break;
            default:
                break;
        }
        GLES20.glDisableVertexAttribArray(this.mPositionHandle);
    }
}
