package com.ld.myapplication;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;

public class PeriodicFileWriter {

    private File targetFile;
    private Handler handler;
    private Runnable writeTask;
    private boolean isRunning = false;

    /**
     * 初始化文件写入工具类
     *
     * @param context  上下文
     * @param filePath 文件路径
     * @throws Exception 如果路径无效或创建文件失败
     */
    public void initialize(Context context, String filePath) throws Exception {
        targetFile = new File(filePath);

        // 确保父目录存在
        if (!targetFile.getParentFile().exists()) {
            if (!targetFile.getParentFile().mkdirs()) {
                throw new Exception("无法创建父目录：" + targetFile.getParentFile().getAbsolutePath());
            }
        }

        // 创建文件
        if (!targetFile.exists()) {
            if (!targetFile.createNewFile()) {
                throw new Exception("无法创建文件：" + filePath);
            }
        }

        // 初始化 Handler
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 启动定期写入任务
     *
     * @param intervalMillis 写入间隔（毫秒）
     */
    public void startWriting(long intervalMillis) {
        if (isRunning) {
            return; // 如果已经在运行，则不重复启动
        }
        isRunning = true;

        writeTask = new Runnable() {
            @Override
            public void run() {
                try {
                    // 向文件写入内容
                    String content = "这是一段定时写入的内容，时间戳：" + System.currentTimeMillis();
                    FileOutputStream fos = new FileOutputStream(targetFile, false); // false 表示覆盖写入
                    fos.write(content.getBytes());
                    fos.flush();
                    fos.close();

                    System.out.println("文件写入成功：" + content);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("文件写入失败：" + e.getMessage());
                }

                // 再次执行任务
                if (isRunning) {
                    handler.postDelayed(this, intervalMillis);
                }
            }
        };

        // 启动任务
        handler.post(writeTask);
    }

    /**
     * 停止定期写入任务
     */
    public void stopWriting() {
        if (isRunning && handler != null) {
            handler.removeCallbacks(writeTask); // 移除任务
            isRunning = false;
            System.out.println("定期写入任务已停止");
        }
    }
}
