package com.mrrun.lib.androidbase.util;

/**
 * Created by lipin on 2017/10/18.
 */

public class ColorMatrixUtils {

    private ColorMatrixUtils instance;

    public ColorMatrixUtils getInstance(){
        if (instance == null) {
            synchronized (this){
                if (instance == null){
                    instance = new ColorMatrixUtils();
                }
            }
        }
        return instance;
    }

    private ColorMatrixUtils(){
    }

    /**
     * 怀旧效果矩阵
     * 求像素点的怀旧效果算法
     * newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
     * newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
     * newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
     */
    private float[] mNostalgiaMatrx = new float[]{
            0.393f, 0.769f, 0.189f, 0, 0,
            0.349f, 0.686f, 0.168f, 0, 0,
            0.272f, 0.534f, 0.131f, 0, 0,
            0, 0, 0, 1, 0
    };

    /**
     * 灰度效果矩阵
     * 求像素点的灰度效果算法
     * newR = (int) (0.33 * pixR + 0.59 * pixG + 0.11 * pixB);
     * newG = (int) (0.33 * pixR + 0.59 * pixG + 0.11 * pixB);
     * newB = (int) (0.33 * pixR + 0.59 * pixG + 0.11 * pixB);
     */
    private float[] mGrayMatrix = new float[]{
            0.33f, 0.59f, 0.11f, 0, 0,
            0.33f, 0.59f, 0.11f, 0, 0,
            0.33f, 0.59f, 0.11f, 0, 0,
            0, 0, 0, 1, 0
    };

    /**
     * 图像反转效果矩阵
     */
    private float[] mRevMatrix = new float[]{
            -1, 0, 0, 1, 1,
            0, -1, 0, 1, 1,
            0, 0, -1, 1, 1,
            0, 0, 0, 1, 0
    };

    /**
     * 去色效果矩阵
     */
    private float[] mDecolorationMatrix = new float[]{
            1.5f, 1.5f, 1.5f, 0, -1,
            1.5f, 1.5f, 1.5f, 0, -1,
            1.5f, 1.5f, 1.5f, 0, -1,
            0, 0, 0, 1, 0
    };

    /**
     * 高饱和度效果矩阵
     */
    private float[] mHighSaturateMatrix = new float[]{
            1.438f, -0.122f, -0.016f, 0, -0.03f,
            -0.062f, 1.378f, -0.016f, 0, 0.05f,
            -0.062f, -0.122f, 1.483f, 0, -0.02f,
            0, 0, 0, 1, 0
    };
}
