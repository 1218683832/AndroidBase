package com.mrrun.lib.androidbase.widget.camera.util;

/**
 * Created by lipin on 2017/9/14.
 * <b>类功能描述:</b><br>
 * 摄像头Camera采集的数据格式工具类
 *
 * @author lipin
 * @version 1.0
 */

public class YuvUtils {
    /**
     * <b>方法功能描述:</b><br>
     * 将YUV格式对的byte数组转90°(不可靠的方法)
     *
     * @param data
     * @param imageWidth
     * @param imageHeight
     * @return
     */
    public static byte[] rotateYUV420Degree90(byte[] data, int imageWidth, int imageHeight){
        byte [] yuv = new byte[imageWidth*imageHeight*3/2];
        // Rotate the Y luma
        int i = 0;
        for(int x = 0;x < imageWidth;x++){
            for(int y = imageHeight-1;y >= 0;y--)
            {
                yuv[i] = data[y*imageWidth+x];
                i++;
            }
        }
        // Rotate the U and V color components
        i = imageWidth*imageHeight*3/2-1;
        for(int x = imageWidth-1;x > 0;x=x-2){
            for(int y = 0;y < imageHeight/2;y++){
                yuv[i] = data[(imageWidth*imageHeight)+(y*imageWidth)+x];
                i--;
                yuv[i] = data[(imageWidth*imageHeight)+(y*imageWidth)+(x-1)];
                i--;
            }
        }
        return yuv;
    }
}
