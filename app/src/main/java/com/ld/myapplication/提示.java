package com.ld.myapplication;

import android.content.Context;
import android.widget.Toast;

public class 提示 {

    /**
     * 显示一个短时间的 Toast 提示
     *
     * @param context 上下文对象
     * @param message 提示文本
     */
    public static void 短时提示(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长时间的 Toast 提示
     *
     * @param context 上下文对象
     * @param message 提示文本
     */
    public static void 长时提示(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
