package com.mrrun.lib.androidbase.widget.opengl;

import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by lipin on 2017/9/15.
 */

public abstract class Drawer {

    public static final String VPOSITION = "vPosition";
    public static final String VCOLOR = "vColor";
    public static final String INPUTTEXTURECOORDINATE = "inputTextureCoordinate";
    public static final String UMVPMATRIX = "uMVPMatrix";
    /**
     * 每个顶点的坐标数目
     */
    public static final int COORDS_PER_VERTEX = 3;
    /**
     * 每个顶点的幅度,4 bytes per vertex
     */
    public final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4;
    /**
     * 数组方式绘制图形
     */
    public static final int GL_DRAW_ARRAYS = 1;
    /**
     * 元素方式绘制图形
     */
    public static final int GL_DRAW_ELEMENTS  = 2;
    /**
     * 绘制方式
     */
    protected int mGLDrawWay = GL_DRAW_ARRAYS;
    /**
     * 顶点坐标缓冲区
     */
    protected FloatBuffer mVertexBuffer  = null;
    /**
     * 顶点绘制顺序缓冲区
     */
    protected ShortBuffer mDrawOrderBuffer;
    /**
     * 纹理坐标缓冲区
     */
    protected FloatBuffer mTextureCoordsBuffer = null;
    /**
     * 顶点坐标数组
     */
    protected float[] mVertexCoords = null;
    /**
     * 顶点的绘制顺序
     */
    protected short[] mDrawOrder = null;
    /**
     * 2x2x2纹理坐标数据
     */
    protected float[] mTextureCoords = new float[8];
    /**
     * 图形RGB值和透明度
     */
    protected float mColor[] = null;
    /**
     * 4x4投影矩阵
     */
    public float[] mMVP = new float[16];
    /**
     * 顶点坐标数目
     */
    protected int mVertexCount;
    /**
     * 顶点着色器(VertextShader)中vPosition(顶点)的句柄
     */
    protected int mPositionHandle;
    /**
     * 片段着色器(FragmentShader)中vColor(填充色)的句柄
     */
    protected int mColorHandle;
    /**
     * 顶点着色器(VertextShader)中inputTextureCoordinate(纹理)的句柄
     */
    protected int mTextureCoordHandle;
    /**
     * 顶点着色器(VertextShader)中uMVPMatrix(投影矩阵)的句柄
     */
    protected int mMVPMatrixHandle;
    /**
     * 指令集,OpenGL ES对象,包含了用来绘制一个或者多个shader(着色器)
     */
    protected int mProgram;
    /**
     * 顶点着色器代码，用来绘制图形的形状
     */
    protected String mVertextShaderCode;
    /**
     * 片段着色器代码，用来绘制图形的颜色或者纹理
     */
    protected String mFragmentShaderCode;

    public Drawer(int glDrawWay) {
        this.mGLDrawWay = glDrawWay;
    }

    public void init(){
        setupVertex();
        setupDrawOrder();
        setupTexture();
        setupColor();
        setupProjectionMatrix(0, 0);
        setupProgram();
        setupHandle();
    }

    public abstract void setupVertex();
    public void setupDrawOrder(){};
    public void setupTexture(){};
    public void setupColor(){};
    public abstract void setupProgram();
    public abstract void setupHandle();
    public abstract void draw();

    /**
     * 根据屏幕的width和height创建投影矩阵
     *
     * @param width
     * @param height
     */
    public void setupProjectionMatrix(int width, int height){
        if (width <= 0 || height <= 0) {
            return;
        }
        final float aspectRatio = width > height ?
                (float) width / (float) height :
                (float) height / (float) width;
        if(width > height){
            Matrix.orthoM(this.mMVP, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        }else{
            Matrix.orthoM(this.mMVP, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }
}
