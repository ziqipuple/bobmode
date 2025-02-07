package com.ld.myapplication;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.PixelCopy;
import android.view.SurfaceView;

import de.robv.android.xposed.XposedBridge;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class 捕获画面 {

    private static SurfaceView surfaceView;
    private static AtomicInteger frameCounter = new AtomicInteger(0); // 统计帧数
    private static Handler mainHandler = new Handler(Looper.getMainLooper()); // 主线程Handler
    private static FPSCallback fpsCallback; // FPS 回调接口

    private 捕获画面() {
        // 工具类不需要实例化
    }

    /**
     * 初始化工具类
     *
     * @param targetSurfaceView 要操作的 SurfaceView
     */
    public static void initialize(SurfaceView targetSurfaceView) {
        surfaceView = targetSurfaceView;
        XposedBridge.log("捕获画面工具初始化完成");
    }

    /**
     * 开始统计刷新率
     *
     * @param detectionRate 检测频率（毫秒），建议每帧检测一次（如 16ms -> 60FPS）
     * @param callback      检测到 FPS 后的回调函数
     */
    public static void startFPSDetection(int detectionRate, FPSCallback callback) {
        if (surfaceView == null) {
            XposedBridge.log("SurfaceView 尚未初始化，无法统计刷新率");
            return;
        }

        fpsCallback = callback; // 保存回调接口
        frameCounter.set(0);    // 重置帧计数器

        // 定时任务每秒统计 FPS
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int fps = frameCounter.getAndSet(0); // 获取当前帧数并重置计数器
                XposedBridge.log("实时刷新率 (FPS): " + fps);

                // 通过回调函数发送 FPS
                if (fpsCallback != null) {
                    fpsCallback.onFPSDetected(fps);
                }

                // 继续下一秒的统计
                mainHandler.postDelayed(this, 1000);
            }
        }, 1000); // 每秒统计一次 FPS

        // 开始帧捕获任务
        startFrameCapture(detectionRate);
    }

    /**
     * 开始捕获每一帧
     *
     * @param detectionRate 捕获频率（毫秒）
     */
    private static void startFrameCapture(int detectionRate) {
        Timer captureTimer = new Timer();
        captureTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (surfaceView.getWidth() > 0 && surfaceView.getHeight() > 0) {
                        // 捕获整个 SurfaceView 的画面
                        Rect fullScreenRect = new Rect(0, 0, surfaceView.getWidth(), surfaceView.getHeight());

                        // 捕获一帧
                        captureFrame(fullScreenRect);
                    } else {
                        XposedBridge.log("SurfaceView 宽高尚未准备好，无法捕获");
                    }
                } catch (Exception e) {
                    XposedBridge.log("帧捕获任务失败：" + e.getMessage());
                }
            }
        }, 0, detectionRate); // 每帧捕获一次
    }

    /**
     * 捕获指定区域的画面
     *
     * @param rect 捕获区域
     */
    private static void captureFrame(Rect rect) {
        Bitmap bitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);

        PixelCopy.request(surfaceView, rect, bitmap, copyResult -> {
            if (copyResult == PixelCopy.SUCCESS) {
                frameCounter.incrementAndGet(); // 每次捕获成功，增加帧计数
            } else {
                XposedBridge.log("PixelCopy 捕获失败，错误代码：" + copyResult);
            }
        }, mainHandler);
    }

    /**
     * FPS 回调接口
     */
    public interface FPSCallback {
        void onFPSDetected(int fps);
    }
}
