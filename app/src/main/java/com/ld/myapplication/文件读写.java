package com.ld.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class 文件读写 {

    private static final String TAG = "FileUtil";

    /**
     * 写入数据到外置存储私有目录。
     *
     * @param context             上下文对象
     * @param fileName            文件名
     * @param content             要写入的内容（支持任意类型，会转成字符串）
     * @param createMultipleFiles 是否创建多个文件：true - 每次生成唯一文件名；false - 写入指定文件
     * @param append              是否追加内容：true - 追加；false - 覆盖
     * @param <T>                 要写入内容的类型
     * @return 写入是否成功
     */
    public static <T> boolean 写入(Context context, String fileName, T content, boolean createMultipleFiles, boolean append) {
        // 确定存储文件名
        String actualFileName = fileName;
        if (createMultipleFiles) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : "";
            String baseName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
            actualFileName = baseName + "_" + timeStamp + extension;
        }

        // 获取外置存储私有目录
        File directory = context.getExternalFilesDir(null); // 默认目录
        if (directory == null) {
            Log.e(TAG, "外置存储不可用");
            return false;
        }

        File file = new File(directory, actualFileName); // 创建目标文件对象

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            writer.write(String.valueOf(content)); // 写入内容
            writer.flush();
            Log.i(TAG, "文件写入成功: " + file.getAbsolutePath());
            return true;
        } catch (IOException e) {
            Log.e(TAG, "文件写入失败: " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * 读取外置存储私有目录中的文件内容。
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param type     目标类型的 Class 对象
     * @param <T>      返回的数据类型
     * @return 文件内容（转换后的类型），若失败返回 null
     */
    public static <T> T 读取(Context context, String fileName, Class<T> type) {
        File directory = context.getExternalFilesDir(null); // 外置存储私有目录
        if (directory == null) {
            Log.e(TAG, "外置存储不可用");
            return null;
        }

        File file = new File(directory, fileName); // 找到目标文件
        if (!file.exists()) {
            Log.e(TAG, "文件不存在: " + file.getAbsolutePath());
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String content = reader.readLine(); // 假设文件只存储单行内容
            return convertContent(content, type);
        } catch (IOException e) {
            Log.e(TAG, "文件读取失败: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 转换文件内容为指定类型。
     */
    private static <T> T convertContent(String content, Class<T> type) {
        if (type == Integer.class) {
            return type.cast(Integer.parseInt(content));
        } else if (type == Float.class) {
            return type.cast(Float.parseFloat(content));
        } else if (type == Boolean.class) {
            return type.cast(Boolean.parseBoolean(content));
        } else {
            return type.cast(content); // 默认返回字符串
        }
    }

    /**
     * 删除外置存储私有目录中的文件。
     *
     * @param context  上下文对象
     * @param fileName 要删除的文件名
     * @return 是否删除成功
     */
    public static boolean 删除文件(Context context, String fileName) {
        File directory = context.getExternalFilesDir(null); // 外置存储私有目录
        if (directory == null) {
            Log.e(TAG, "外置存储不可用");
            return false;
        }

        File file = new File(directory, fileName); // 找到目标文件
        if (file.exists()) {
            boolean result = file.delete();
            if (result) {
                Log.i(TAG, "文件删除成功: " + file.getAbsolutePath());
            } else {
                Log.e(TAG, "文件删除失败: " + file.getAbsolutePath());
            }
            return result;
        } else {
            Log.e(TAG, "文件不存在: " + file.getAbsolutePath());
        }
        return false;
    }

    /**
     * 获取外置存储私有目录中所有文件列表。
     *
     * @param context 上下文
     * @return 文件名列表
     */
    public static String[] 获取文件列表(Context context) {
        File directory = context.getExternalFilesDir(null); // 外置存储私有目录
        if (directory == null) {
            Log.e(TAG, "外置存储不可用");
            return new String[0];
        }

        String[] fileList = directory.list();
        if (fileList != null) {
            Log.i(TAG, "文件列表获取成功: " + directory.getAbsolutePath());
            return fileList;
        } else {
            Log.e(TAG, "无法获取文件列表: " + directory.getAbsolutePath());
        }
        return new String[0];
    }
    public static String 读取私有目录(Context context, String fileName) {
    try (InputStream in = context.openFileInput(fileName);
         BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        return content.toString();
    } catch (IOException e) {
        Log.e(TAG, "读取私有目录文件失败: " + e.getMessage());
        return null;
    }
}
}
