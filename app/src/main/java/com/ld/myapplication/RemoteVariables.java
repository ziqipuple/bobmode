package com.ld.myapplication;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.Field;

public class RemoteVariables {

    // 定义全局静态变量
    public static String qq;
    public static String qqq;
    public static String qqqq;
    public static String qqqqq;

    // 标记初始化是否完成
    private static boolean isInitialized = false;

    /**
     * 初始化方法，用于批量获取所有变量的值
     *
     * @param kami    卡密
     * @param context 上下文
     */
    public static void initialize(String kami, Context context) {
        if (isInitialized) {
            Log.d("RemoteVariables", "初始化已完成，请勿重复");
            return;
        }

        // 使用反射自动获取类中所有静态字段
        fetchAllVariables(kami, context);

        isInitialized = true;
        Log.d("RemoteVariables", "远程变量初始化完成");
    }

    /**
     * 通过反射遍历类中的所有静态变量并获取其值
     *
     * @param kami    卡密
     * @param context 上下文
     */
    private static void fetchAllVariables(String kami, Context context) {
        // 获取当前类的所有字段
        Field[] fields = RemoteVariables.class.getDeclaredFields();

        for (Field field : fields) {
            // 仅处理静态变量
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                // 获取变量名
                String key = field.getName();
                // 调用fetchVariable方法
                fetchVariable(key, kami, context);
            }
        }
    }

    /**
     * 获取远程变量并存储到静态变量
     *
     * @param key      变量名
     * @param kami     卡密
     * @param context  上下文
     */
    private static void fetchVariable(String key, String kami, Context context) {
    wy.getValue(key, kami, context, new wy.Callback() {
        @Override
        public void onResult(String result) {
            // 根据 key 存储变量值
            try {
                Field field = RemoteVariables.class.getDeclaredField(key);
                field.setAccessible(true);

                // 根据字段类型处理赋值
                if (field.getType() == String.class) {
                    field.set(null, result);  // 如果字段是 String 类型，直接赋值
                } else if (field.getType() == boolean.class) {
                    // 如果字段是 boolean 类型，根据字符串值进行转换
                    field.set(null, Boolean.parseBoolean(result));
                } else if (field.getType() == int.class) {
                    // 如果字段是 int 类型，根据字符串值进行转换
                    field.set(null, Integer.parseInt(result));
                } else if (field.getType() == long.class) {
                    // 如果字段是 long 类型，根据字符串值进行转换
                    field.set(null, Long.parseLong(result));
                } else if (field.getType() == float.class) {
                    // 如果字段是 float 类型，根据字符串值进行转换
                    field.set(null, Float.parseFloat(result));
                } else if (field.getType() == double.class) {
                    // 如果字段是 double 类型，根据字符串值进行转换
                    field.set(null, Double.parseDouble(result));
                } else {
                    // 如果字段是其他类型，则忽略或处理其他类型转换
                    Log.e("RemoteVariables", "不支持的字段类型: " + field.getType());
                }
                
                Log.d("RemoteVariables", "变量 " + key + " 值: " + result);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Log.e("RemoteVariables", "设置变量 " + key + " 失败: " + e.getMessage());
            }
        }

        @Override
        public void onError(Exception e) {
            Log.e("RemoteVariables", "获取变量 " + key + " 失败: " + e.getMessage());
        }
    });
}

}
