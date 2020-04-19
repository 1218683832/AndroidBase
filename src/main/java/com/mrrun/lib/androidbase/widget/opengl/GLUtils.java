package com.mrrun.lib.androidbase.widget.opengl;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lipin on 2017/9/15.
 */

public class GLUtils {

    private static final String TAG = GLUtils.class.getSimpleName();

    private GLUtils() {
    }

    public static int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public static int createTextureID() {
        int[] texture = new int[1];

        GLES20.glGenTextures(1, texture, 0);
        // Bind the texture handle to the 2D texture target.
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);// 绑定外部纹理
        // Configure min/mag filtering, i.e. what scaling method do we use if what we're rendering
        // is smaller or larger than the source image.
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        return 0;
    }

    /**
     * Creates a new program from the supplied vertex and fragment shaders.
     *
     * @return A handle to the program, or 0 on failure.
     */
    public static int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }

        int program = GLES20.glCreateProgram();// create empty OpenGL ES Program
        checkGlError("glCreateProgram");
        if (program == 0) {
            Log.i(TAG, "Could not create program");
        }
        GLES20.glAttachShader(program, vertexShader);// 将vertex shader添加到program
        checkGlError("glAttachShader");
        GLES20.glAttachShader(program, pixelShader);// 将fragment shader添加到 program
        checkGlError("glAttachShader");
        GLES20.glLinkProgram(program);// 创建一个可执行的 OpenGL ES program
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GLES20.GL_TRUE) {
            Log.i(TAG, "Could not link program: ");
            Log.i(TAG, GLES20.glGetProgramInfoLog(program));
            GLES20.glDeleteProgram(program);
            program = 0;
        }
        return program;
    }

    /**
     * Checks to see if a GLES error has been raised.
     */
    public static void checkGlError(String op) {
        int error = GLES20.glGetError();
        if (error != GLES20.GL_NO_ERROR) {
            String msg = op + ": glError 0x" + Integer.toHexString(error);
            Log.i(TAG, msg);
            throw new RuntimeException(msg);
        }
    }

    /**
     * Checks to see if the location we obtained is valid.  GLES returns -1 if a label
     * could not be found, but does not set the GL error.
     * <p>
     * Throws a RuntimeException if the location is invalid.
     */
    public static boolean checkLocation(int location, String label) {
        if (location < 0) {// throw new RuntimeException("Unable to locate '" + label + "' in program");
            return false;
        }
        return true;
    }

    /**
     * @param floats float 数组
     * @return 获取浮点形缓冲数据
     */
    public static FloatBuffer getFloatBuffer(float[] floats) {
        FloatBuffer buffer;
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // 坐标数组的长度 * 每个short所占的字节数
                floats.length * 4);
        // 使用设备硬件的字节顺序
        bb.order(ByteOrder.nativeOrder());
        // 从ByteBuffer中创建一个float型的buffer
        buffer = bb.asFloatBuffer();
        // 将坐标数组写入Buffer
        buffer.put(floats);
        // 设置buffer指向第一个坐标
        buffer.position(0);
        return buffer;
    }

    /**
     * @param shorts short 数组
     * @return 获取整形缓冲数据
     */
    public static ShortBuffer getShortBuffer(short[] shorts) {
        ShortBuffer buffer;
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // 坐标数组的长度 * 每个short所占的字节数
                shorts.length * 2);
        // 使用设备硬件的字节顺序
        bb.order(ByteOrder.nativeOrder());
        // 从ByteBuffer中创建一个short型的buffer
        buffer = bb.asShortBuffer();
        // 将坐标数组写入Buffer
        buffer.put(shorts);
        // 设置buffer指向第一个坐标
        buffer.position(0);
        return buffer;
    }

    /**
     * Reads in text from a resource file and returns a String containing the
     * text.
     */
    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder body = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not open resource: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + resourceId, nfe);
        }
        return body.toString();
    }
}
