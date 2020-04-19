package com.mrrun.lib.androidbase.widget.opengl.sample;

import android.opengl.GLES20;

import com.mrrun.lib.androidbase.R;
import com.mrrun.lib.androidbase.base.app.BaseApp;
import com.mrrun.lib.androidbase.widget.opengl.Drawer;
import com.mrrun.lib.androidbase.widget.opengl.GLUtils;

/**
 * Created by lipin on 2017/9/18.
 * Define a Circle
 */

public class Circle extends Drawer {

    // 圆心坐标
    private float x = 0.0f;
    private float y = 0.0f;
    private float z = 0.0f;
    // 半径
    private float r = 0.7f;
    // 分割的三角形数量
    private int mTriangleCount = 30;

    public Circle(int glDrawWay) {
        super(glDrawWay);
        init();
    }

    @Override
    public void setupVertex() {
        // 顶点的个数，我们分割count个三角形，有count+1个点，再加上圆心共有count+2个点
        this.mVertexCount = this.mTriangleCount + 2;
        this.mVertexCoords = new float[this.mVertexCount * COORDS_PER_VERTEX];
        // x y z
        int offset = 0;
        // 每个三角形占有的弧度值
        float everyArcInRadians = ((float) 1 / (float) this.mTriangleCount) * ((float) Math.PI * 2f);
        // 中心点
        this.mVertexCoords[offset++] = x;
        this.mVertexCoords[offset++] = y;
        this.mVertexCoords[offset++] = z;
        for (int i = 0; i < this.mVertexCount - 1; i++) {
            this.mVertexCoords[offset++] = x + r * (float)Math.sin(everyArcInRadians * i);
            this.mVertexCoords[offset++] = y + r * (float)Math.cos(everyArcInRadians * i);
            this.mVertexCoords[offset++] = z;
        }
        this.mVertexBuffer = GLUtils.getFloatBuffer(this.mVertexCoords);
    }

    @Override
    public void setupColor() {
        this.mColor = new float[]{0.0f, 0.0f, 1.0f, 1.0f};
    }

    @Override
    public void setupProgram() {
        this.mVertextShaderCode = GLUtils.readTextFileFromResource(
                BaseApp.appContext, R.raw.circle_vertex_shader);
        this.mFragmentShaderCode = GLUtils.readTextFileFromResource(
                BaseApp.appContext, R.raw.circle_fragment_shader);
        this.mProgram = GLUtils.createProgram(this.mVertextShaderCode, this.mFragmentShaderCode);
        if (this.mProgram == 0) {
            throw new RuntimeException("Unable to create program");
        }
        GLES20.glUseProgram(this.mProgram);// 将program添加到OpenGL ES环境中
    }

    @Override
    public void setupHandle() {
        this.mPositionHandle = GLES20.glGetAttribLocation(this.mProgram, Drawer.VPOSITION);
        this.mColorHandle = GLES20.glGetUniformLocation(this.mProgram, Drawer.VCOLOR);
        this.mTextureCoordHandle = GLES20.glGetAttribLocation(this.mProgram, Drawer.INPUTTEXTURECOORDINATE);
        this.mMVPMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, Drawer.UMVPMATRIX);
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
        if (GLUtils.checkLocation(this.mTextureCoordHandle, Drawer.INPUTTEXTURECOORDINATE)
                && this.mTextureCoordsBuffer != null) {
            GLES20.glEnableVertexAttribArray(this.mTextureCoordHandle);// 启用顶点着色器(VertextShader)中inputTextureCoordinate(Texture)的句柄
            GLES20.glVertexAttribPointer(this.mTextureCoordHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false,
                    VERTEX_STRIDE, this.mTextureCoordsBuffer);// 设置Texture坐标数据
        }
        if (GLUtils.checkLocation(this.mMVPMatrixHandle, Drawer.UMVPMATRIX) && this.mMVP != null) {
            GLES20.glUniformMatrix4fv(this.mMVPMatrixHandle, 1, false, this.mMVP, 0);// 传入投影矩阵
        }
        switch (this.mGLDrawWay) {
            case GL_DRAW_ARRAYS:
                //参数说明：
                //- mode：使用不同类型画出的效果也不一样
                //GL_POINTS_单独画出顶点；GL_LINES_单独画出直线；GL_LINE_STRIP_连贯地将直线画出来；
                //GL_LINE_LOOP_ 连贯地将直线画出来，自动将最后一个顶点和第一个顶点通过直线连接起来。
                //GL_TRIANGLES_OpenGL使用三个顶点来组成图形；
                //GL_TRIANGLE_STRIP_最开始的两个顶点出发，遍历每个顶点并使用前2个顶点一起组成一个三角形。
                //- first：开始点，指明在允许访问的矩阵中的起始索引。
                //- count：总共顶点数，指明要渲染的索引的数量。
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
        GLES20.glDisableVertexAttribArray(this.mTextureCoordHandle);
    }
}
