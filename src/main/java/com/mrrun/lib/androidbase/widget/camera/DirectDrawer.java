package com.mrrun.lib.androidbase.widget.camera;

import android.opengl.GLES20;

import com.mrrun.lib.androidbase.R;
import com.mrrun.lib.androidbase.base.app.BaseApp;
import com.mrrun.lib.androidbase.widget.opengl.Drawer;
import com.mrrun.lib.androidbase.widget.opengl.GLUtils;

/**
 * Created by lipin on 2017/9/15.
 */

public class DirectDrawer extends Drawer {

    private float mTextHeightRatio = 0.0f;

    public DirectDrawer(int glDrawWay) {
        super(glDrawWay);
        init();
        setupVertex();
        setupDrawOrder();
        setupTexture();
        setupColor();
        setupProjectionMatrix(0, 0);
        setupProgram();
        setupHandle();
    }

    public void resetMatrix() {
        mat4f_LoadOrtho(-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f, mMVP);
    }

    @Override
    public void setupVertex() {
        final float w = 1.0f;
        final float h = 1.0f;
        this.mVertexCoords = new float[]{
                -w, h, 0,
                -w, -h, 0,
                w, -h, 0,
                w, h, 0
        };
        this.mVertexCount = this.mVertexCoords.length / COORDS_PER_VERTEX;
        this.mVertexBuffer = GLUtils.getFloatBuffer(this.mVertexCoords);
    }

    @Override
    public void setupProgram() {
        this.mVertextShaderCode = GLUtils.readTextFileFromResource(
                BaseApp.appContext, R.raw.camera_vertex_shader);
        this.mFragmentShaderCode = GLUtils.readTextFileFromResource(
                BaseApp.appContext, R.raw.camera_normal_fragment_shader);
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
                //　参数说明：
                //　- mode：使用不同类型画出的效果也不一样
                //　GL_POINTS_单独画出顶点；GL_LINES_单独画出直线；GL_LINE_STRIP_连贯地将直线画出来；
                //　GL_LINE_LOOP_ 连贯地将直线画出来，自动将最后一个顶点和第一个顶点通过直线连接起来。
                //　GL_TRIANGLES_OpenGL使用三个顶点来组成图形；
                //　GL_TRIANGLE_STRIP_最开始的两个顶点出发，遍历每个顶点并使用前2个顶点一起组成一个三角形。
                //　- first：开始点，指明在允许访问的矩阵中的起始索引。
                //　- count：总共顶点数，指明要渲染的索引的数量。
                // 绘制图形，这里采用glDrawArrays方法(默认是逆时针方向)
                GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, this.mVertexCount);
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

    @Override
    public void setupTexture() {
        this.mTextureCoords = new float[]{
                0, 1 - this.mTextHeightRatio, 0,
                1, 1 - this.mTextHeightRatio, 0,
                1, 0 + this.mTextHeightRatio, 0,
                0, 0 + this.mTextHeightRatio, 0
        };
        this.mTextureCoordsBuffer = GLUtils.getFloatBuffer(this.mTextureCoords);
    }

    @Override
    public void setupDrawOrder() {
        this.mDrawOrder = new short[]{0, 2, 1, 0, 3, 2}; // order to draw vertices
        this.mDrawOrderBuffer = GLUtils.getShortBuffer(this.mDrawOrder);
    }

    @Override
    public void setupProjectionMatrix(int width, int height) {
        resetMatrix();
    }

    /**
     * 创建投影矩阵
     *
     * @param left
     * @param right
     * @param bottom
     * @param top
     * @param near
     * @param far
     * @param mout
     */
    public static void mat4f_LoadOrtho(float left, float right, float bottom,
                                       float top, float near, float far, float[] mout) {
        float r_l = right - left;
        float t_b = top - bottom;
        float f_n = far - near;
        float tx = -(right + left) / (right - left);
        float ty = -(top + bottom) / (top - bottom);
        float tz = -(far + near) / (far - near);

        mout[0] = 2.0f / r_l;
        mout[1] = 0.0f;
        mout[2] = 0.0f;
        mout[3] = 0.0f;

        mout[4] = 0.0f;
        mout[5] = 2.0f / t_b;
        mout[6] = 0.0f;
        mout[7] = 0.0f;

        mout[8] = 0.0f;
        mout[9] = 0.0f;
        mout[10] = -2.0f / f_n;
        mout[11] = 0.0f;

        mout[12] = tx;
        mout[13] = ty;
        mout[14] = tz;
        mout[15] = 1.0f;
    }
}
