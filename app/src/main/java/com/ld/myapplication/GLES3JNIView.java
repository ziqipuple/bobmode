package com.ld.myapplication;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLES3JNIView extends GLSurfaceView implements GLSurfaceView.Renderer {
    static {
        System.loadLibrary("IMGUI");
    }

    public static byte[] fontData; // 确保正确加载字体数据

    public GLES3JNIView(Context context) {
        super(context);

        // 配置 OpenGL
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        setEGLContextClientVersion(3); // OpenGL ES 3.0
        setRenderer(this); // 设置渲染器
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // 设置透明背景

        // 初始化 ImGui
        init(getHolder().getSurface());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        resize(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        step();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        imgui_Shutdown();
    }
    public static native boolean isImGuiComponentTouched(float x, float y);

    // Native 方法
    public static native void init(Object surface);
    public static native void resize(int width, int height);
    public static native void step();
    public static native void imgui_Shutdown();
    public static native void MotionEventClick(boolean down, float posX, float posY);
}