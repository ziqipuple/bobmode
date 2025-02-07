package com.ld.myapplication;

import android.R;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import java.security.MessageDigest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Interceptor;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;
import android.graphics.Color;
import android.widget.FrameLayout;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import android.content.pm.Signature;
import java.security.MessageDigest;
import android.util.Log;


public class HOOK implements IXposedHookLoadPackage {
    private parameters parameters;
    private GLES3JNIView display; // IMGUI 渲染层
    private SurfaceView targetSurfaceView;

    @Override
public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
    if (!lpparam.packageName.equals("com.ztgame.bob")) {
        return;
    }

    XposedBridge.log("Game package loaded: " + lpparam.packageName);

    // Hook UnityPlayer 的 injectEvent 方法
    XposedHelpers.findAndHookMethod("com.unity3d.player.UnityPlayer", lpparam.classLoader,
            "injectEvent", InputEvent.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    InputEvent event = (InputEvent) param.args[0];
                    // XposedBridge.log("Inject event: " + event.toString());
                }
            });

        // Hook UnityPlayerActivity 的 onCreate 方法
        XposedHelpers.findAndHookMethod(
                "com.unity3d.player.UnityPlayerActivity",
                lpparam.classLoader,
                "onCreate",
                Bundle.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        final Activity activity = (Activity) param.thisObject;
                        XposedBridge.log("UnityPlayerActivity onCreate hooked");
                        
                        // 获取 UnityPlayer 实例
                        Object unityPlayerInstance =
                                XposedHelpers.getObjectField(param.thisObject, "mUnityPlayer");

                        // 包装上下文以应用指定主题
                        Context themedContext =
                                new ContextThemeWrapper(
                                        activity, android.R.style.Theme_Material_Light);

                        // 加载或初始化参数
                        parameters = ConfigUtil.loadConfigFromTargetAppDir();
                        if (parameters == null) {
                            parameters = new parameters();
                            ConfigUtil.saveConfigToTargetAppDir(parameters);
                        }
                  //  setupImGuiView(activity);
                  //  OpenCVLoader.initDebug();

                        // 设置 CustomLayout 居中显示
                        FrameLayout rootView =
                                (FrameLayout)
                                        activity.getWindow()
                                                .getDecorView()
                                                .findViewById(android.R.id.content);
                    
                        CustomLayout customLayout =
                                new CustomLayout(themedContext, parameters, rootView);
                        FrameLayout.LayoutParams layoutParams =
                                new FrameLayout.LayoutParams(
                                        dpToPx(371, activity), // 宽度
                                        dpToPx(257, activity) // 高度
                                        );
                        layoutParams.gravity = Gravity.CENTER; // 居中对齐
                        customLayout.setVisibility(View.GONE); // 初始隐藏

                        // 将 CustomLayout 添加到 rootView，并应用居中布局参数
                        rootView.addView(customLayout, layoutParams);
                        customLayout.setZ(5);

                        // 创建悬浮球并设置为圆形
                        ImageView floatingBall = createFloatingBall(themedContext, rootView);

                        // 设置触摸监听，实现拖动和点击显示/隐藏 CustomLayout
                        setFloatingBallTouchListener(floatingBall, customLayout, rootView);
                        floatingBall.setZ(5);

                        // 将悬浮球添加到 rootView
                        rootView.addView(floatingBall);

                        // 保留原有代码
                        RoundButtonCreator buttonCreator =
                                new RoundButtonCreator(activity, rootView);
                        buttonCreator.loadConfiguration();

                        // 从目标应用的私有目录加载配置
                        parameters = ConfigUtil.loadConfigFromTargetAppDir();
                        if (parameters == null) {
                            // 如果文件不存在或无法加载，创建默认配置并保存到目标应用目录
                            parameters = new parameters();
                            ConfigUtil.saveConfigToTargetAppDir(parameters);
                        }

                        // 传递 unityPlayerInstance 给 TouchEventHandler
                        TouchEventHandler.setUnityPlayerInstance(unityPlayerInstance);
                        // 添加网络拦截 Hook
                        // addNetworkResponseHook(lpparam);
                        hookAppSignature(lpparam);
                        traverseViews(activity.getWindow().getDecorView());
                    }
                });
    }





private void traverseViews(android.view.View rootView) {
        if (rootView instanceof SurfaceView) {
            targetSurfaceView = (SurfaceView) rootView;
            XposedBridge.log("找到 SurfaceView，准备进行操作");
            捕获画面.initialize(targetSurfaceView);
            return;
        }

        if (rootView instanceof android.view.ViewGroup) {
            android.view.ViewGroup group = (android.view.ViewGroup) rootView;
            for (int i = 0; i < group.getChildCount(); i++) {
                traverseViews(group.getChildAt(i));
            }
        }
    }

    



    public static class TouchEventHandler {
    private static Object unityPlayerInstance;
    private static boolean 指定次数 = false;
    private static boolean 连击 = false;
    private static Thread autoClickThread;

    public static void setUnityPlayerInstance(Object instance) {
        unityPlayerInstance = instance;
    }

    // 启动带重复次数的自动点击
    public static void 指定次数点击(int x, int y, long 按下延时, long 抬起延时, long 一组延时, int 次数, int 触摸Id) {
        指定次数 = true;
        autoClickThread = new Thread(() -> {
            try {
                for (int i = 0; i < 次数 && 指定次数; i++) {
                    // 按下事件
                    按下(x, y, 触摸Id);
                    Thread.sleep(按下延时);

                    // 抬起事件
                    抬起(x, y, 触摸Id);
                    Thread.sleep(抬起延时);

                    // 组延时
                    Thread.sleep(一组延时);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                指定次数 = false;
                if (autoClickThread != null) {
                    autoClickThread.interrupt();  // 终止线程
                    autoClickThread = null;
                }
                XposedBridge.log("自动点击已停止。");
            }
        });
        autoClickThread.start();
    }

    // 启动无限循环自动点击
    public static void 无限循环点击(int x, int y, long 按下延时, long 抬起延时, long 一组延时, int 触摸Id) {
        连击 = true;
        autoClickThread = new Thread(() -> {
            try {
                while (连击) {
                    按下(x, y, 触摸Id);
                    Thread.sleep(按下延时);
                    抬起(x, y, 触摸Id);
                    Thread.sleep(抬起延时);
                    Thread.sleep(一组延时);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        autoClickThread.start();
    }



    public static void 无限循环点击停止() {
    连击 = false;  // 将连击设置为false，停止点击循环
    if (autoClickThread != null && autoClickThread.isAlive()) {
        autoClickThread.interrupt();  // 中断线程（如果线程仍在运行）
    }
}


    // sendTouchDown 方法
    public static void 按下(int x, int y, int pointerId) {
        if (unityPlayerInstance == null || pointerId == -1) return;

        MotionEvent.PointerProperties[] pointerProperties = new MotionEvent.PointerProperties[1];
        MotionEvent.PointerProperties pp = new MotionEvent.PointerProperties();
        pp.id = pointerId;
        pp.toolType = MotionEvent.TOOL_TYPE_FINGER;
        pointerProperties[0] = pp;

        MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[1];
        MotionEvent.PointerCoords pc = new MotionEvent.PointerCoords();
        pc.x = x;
        pc.y = y;
        pc.pressure = 1;
        pc.size = 1;
        pointerCoords[0] = pc;

        long downTime = System.currentTimeMillis();
        MotionEvent eventDown = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 1, pointerProperties, pointerCoords, 0, 0, 1.0f, 1.0f, 0, 0, InputDevice.SOURCE_TOUCHSCREEN, 0);
        XposedHelpers.callMethod(unityPlayerInstance, "injectEvent", eventDown);
        XposedBridge.log("发送触摸按下事件，位置 (" + x + ", " + y + ")，指针 ID: " + pointerId);
    }

    // sendTouchUp 方法
    public static void 抬起(int x, int y, int pointerId) {
        if (unityPlayerInstance == null || pointerId == -1) return;

        MotionEvent.PointerProperties[] pointerProperties = new MotionEvent.PointerProperties[1];
        MotionEvent.PointerProperties pp = new MotionEvent.PointerProperties();
        pp.id = pointerId;
        pp.toolType = MotionEvent.TOOL_TYPE_FINGER;
        pointerProperties[0] = pp;

        MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[1];
        MotionEvent.PointerCoords pc = new MotionEvent.PointerCoords();
        pc.x = x;
        pc.y = y;
        pc.pressure = 1;
        pc.size = 1;
        pointerCoords[0] = pc;

        long eventTime = System.currentTimeMillis();
        MotionEvent eventUp = MotionEvent.obtain(eventTime, eventTime, MotionEvent.ACTION_UP, 1, pointerProperties, pointerCoords, 0, 0, 1.0f, 1.0f, 0, 0, InputDevice.SOURCE_TOUCHSCREEN, 0);
        XposedHelpers.callMethod(unityPlayerInstance, "injectEvent", eventUp);
        XposedBridge.log("发送触摸抬起事件，位置 (" + x + ", " + y + ")，指针 ID: " + pointerId);
    }

    // 移动方法
    public static void 移动(int 起始x, int 起始y, int 终点x, int 终点y, long 滑动时长, int 触摸Id, boolean 抬起) {
    if (unityPlayerInstance == null || 触摸Id == -1) return;

    long downTime = System.currentTimeMillis();
    long endTime = downTime + 滑动时长;

    // 按下起始位置
    按下(起始x, 起始y, 触摸Id);

    // 模拟滑动过程
    while (System.currentTimeMillis() < endTime) {
        long eventTime = System.currentTimeMillis();

        MotionEvent.PointerProperties[] pointerProperties = new MotionEvent.PointerProperties[1];
        MotionEvent.PointerProperties pp = new MotionEvent.PointerProperties();
        pp.id = 触摸Id;
        pp.toolType = MotionEvent.TOOL_TYPE_FINGER;
        pointerProperties[0] = pp;

        MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[1];
        MotionEvent.PointerCoords pc = new MotionEvent.PointerCoords();
        pc.x = 起始x + (终点x - 起始x) * (eventTime - downTime) / 滑动时长;
        pc.y = 起始y + (终点y - 起始y) * (eventTime - downTime) / 滑动时长;
        pc.pressure = 1;
        pc.size = 1;
        pointerCoords[0] = pc;

        MotionEvent moveEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, 1, pointerProperties, pointerCoords, 0, 0, 1.0f, 1.0f, 0, 0, InputDevice.SOURCE_TOUCHSCREEN, 0);
        XposedHelpers.callMethod(unityPlayerInstance, "injectEvent", moveEvent);
    }

    // 根据参数决定是否抬起
    if (抬起) {
        抬起(终点x, 终点y, 触摸Id);
    }

    XposedBridge.log("发送触摸移动事件，起始位置 (" + 起始x + ", " + 起始y + ") 到终点位置 (" + 终点x + ", " + 终点y + ")，指针 ID: " + 触摸Id + "，抬起: " + 抬起);
}

public static int[] 角度滑动(int 起始x, int 起始y, float 初始角度, float 目标角度, int 滑动距离, int 滑动时长, int 触摸id, boolean 抬起) {
    float targetAngle = 初始角度 + 目标角度;
    double radianAngle = Math.toRadians(targetAngle);

    // 计算终点坐标
    int 长度x = (int) (Math.cos(radianAngle) * 滑动距离);
    int 长度y = (int) (Math.sin(radianAngle) * 滑动距离);

    int 终点x = 起始x + 长度x;
    int 终点y = 起始y - 长度y;

    XposedBridge.log("Starting swipe from (" + 起始x + ", " + 起始y + ") to (" + 终点x + ", " + 终点y + ") with angle: " + targetAngle + "°");

    // 如果不抬起，先回到起点
    if (!抬起) {
        XposedBridge.log("Returning swipe back to starting point (" + 起始x + ", " + 起始y + ") from (" + 终点x + ", " + 终点y + ")");
        移动(终点x, 终点y, 起始x, 起始y, 滑动时长, 触摸id, false);
    }

    // 使用移动方法进行滑动到终点
    移动(起始x, 起始y, 终点x, 终点y, 滑动时长, 触摸id, 抬起);

    // 返回终点坐标
    return new int[]{终点x, 终点y};
}




}
public void hookAppSignature(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        try {
        // Hook AppUtils.getAppSignatureSHA1()
        XposedHelpers.findAndHookMethod(
            "com.ztgame.mobileappsdk.utils.AppUtils", // 目标类名
            loadPackageParam.classLoader,
            "getAppSignatureSHA1", // 方法名
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    // 获取 SHA1 签名
                    String sha1Signature = (String) param.getResult();
                    XposedBridge.log("Hooked getAppSignatureSHA1(): " + sha1Signature);
                }
            }
        );

        // Hook AppUtils.getAppSignature()
        XposedHelpers.findAndHookMethod(
            "com.ztgame.mobileappsdk.utils.AppUtils", // 目标类名
            loadPackageParam.classLoader,
            "getAppSignature", // 方法名
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    // 获取签名数组
                    Signature[] signatures = (Signature[]) param.getResult();
                    if (signatures != null) {
                        for (Signature signature : signatures) {
                            String rawSignature = signature.toCharsString();
                            XposedBridge.log("Raw Signature: " + rawSignature);

                            String sha1Signature = encryptSHA1ToString(signature.toByteArray());
                            XposedBridge.log("SHA1 Signature: " + sha1Signature);
                        }
                    } else {
                        XposedBridge.log("getAppSignature() returned null.");
                    }
                }
            }
        );

    } catch (Throwable t) {
        XposedBridge.log("Error while hooking signature methods: " + t.getMessage());
    }
}

// 辅助方法：将字节数组转为 SHA1 字符串
private String encryptSHA1ToString(byte[] data) {
    try {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        messageDigest.update(data);
        byte[] digest = messageDigest.digest();

        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xFF & b).toUpperCase();
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    } catch (Exception e) {
        XposedBridge.log("Error in encryptSHA1ToString(): " + e.getMessage());
        return null;
    }
}



// 添加网络拦截逻辑
private void addNetworkResponseHook(final XC_LoadPackage.LoadPackageParam lpparam) {
    new Thread(() -> {
        try {
            // 空格计数
            final int[] spaceCount = {1};

            while (true) {
                XposedHelpers.findAndHookMethod(
                    "com.ztgame.mobileappsdk.http.httpservice.ZTHttpService",
                    lpparam.classLoader,
                    "init",
                    boolean.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Object originalClient = param.getResult();
                            if (originalClient == null) {
                                XposedBridge.log("Original OkHttpClient is null, skipping hook.");
                                return;
                            }

                            Object builder = XposedHelpers.callMethod(originalClient, "newBuilder");
                            if (builder == null) {
                                XposedBridge.log("Failed to create OkHttpClient builder, skipping hook.");
                                return;
                            }

                            Object interceptor = Proxy.newProxyInstance(
                                builder.getClass().getClassLoader(),
                                new Class[]{
                                    builder.getClass().getClassLoader().loadClass("com.ztgame.mobileappsdk.http.okhttp3.Interceptor")
                                },
                                (proxy, method, args) -> {
                                    if ("intercept".equals(method.getName())) {
                                        Object chain = args[0];
                                        Object request = XposedHelpers.callMethod(chain, "request");
                                        Object httpUrl = XposedHelpers.callMethod(request, "url");
                                        String url = XposedHelpers.callMethod(httpUrl, "toString").toString();

                                        XposedBridge.log("Request URL: " + url);

                                        // 获取请求体
                                        Object body = XposedHelpers.callMethod(request, "body");
                                        if (body != null) {
                                            StringBuilder requestBody = new StringBuilder();

                                            if (body.getClass().getName().contains("FormBody")) {
                                                int size = (int) XposedHelpers.callMethod(body, "size");
                                                for (int i = 0; i < size; i++) {
                                                    String name = (String) XposedHelpers.callMethod(body, "name", i);
                                                    String value = (String) XposedHelpers.callMethod(body, "value", i);
                                                    requestBody.append(name).append("=").append(value).append("&");
                                                }
                                                if (requestBody.length() > 0) {
                                                    requestBody.setLength(requestBody.length() - 1); // 移除最后的 "&"
                                                }
                                            }

                                            XposedBridge.log("Request Body: " + requestBody.toString());
                                        } else {
                                            XposedBridge.log("Request Body: null");
                                        }

                                        if (url.contains("http://fp-it.fengkongcloud.com/deviceprofile/v4")) {
                                            XposedBridge.log("Target link detected: " + url);

                                            // 动态构造返回体
                                            StringBuilder spaces = new StringBuilder();
                                            for (int i = 0; i < spaceCount[0]; i++) {
                                                spaces.append(" ");
                                            }
                                            String fakeResponse = "{\"code\":9101,\"message\":\"\\u65e0\\u6743\\u9650\\u64cd\\u4f5c(invalid organization or service not avaliable)\",\"requestId\":\"" + spaces + "\"}";

                                            Class<?> responseBuilderClass = builder.getClass().getClassLoader().loadClass("com.ztgame.mobileappsdk.http.okhttp3.Response$Builder");
                                            Object responseBuilder = responseBuilderClass.getConstructor().newInstance();
                                            XposedHelpers.callMethod(responseBuilder, "request", request);
                                            XposedHelpers.callMethod(responseBuilder, "protocol",
                                                Enum.valueOf((Class<Enum>) builder.getClass().getClassLoader().loadClass("com.ztgame.mobileappsdk.http.okhttp3.Protocol"), "HTTP_1_1"));
                                            XposedHelpers.callMethod(responseBuilder, "code", 200);
                                            XposedHelpers.callMethod(responseBuilder, "message", "OK");
                                            XposedHelpers.callMethod(responseBuilder, "body",
                                                XposedHelpers.callStaticMethod(builder.getClass().getClassLoader().loadClass("com.ztgame.mobileappsdk.http.okhttp3.ResponseBody"),
                                                    "create",
                                                    XposedHelpers.callStaticMethod(builder.getClass().getClassLoader().loadClass("com.ztgame.mobileappsdk.http.okhttp3.MediaType"),
                                                        "parse", "application/json"),
                                                    fakeResponse));

                                            // 更新空格计数
                                            spaceCount[0]++;
                                            if (spaceCount[0] > 3) {
                                                spaceCount[0] = 1; // 重置为 1
                                            }

                                            return XposedHelpers.callMethod(responseBuilder, "build");
                                        }

                                        return XposedHelpers.callMethod(chain, "proceed", request);
                                    }
                                    return null;
                                }
                            );

                            XposedHelpers.callMethod(builder, "addInterceptor", interceptor);
                            Object newClient = XposedHelpers.callMethod(builder, "build");
                            param.setResult(newClient);
                        }
                    });

                Thread.sleep(1000); // 每秒检测一次
            }
        } catch (Exception e) {
            XposedBridge.log("Error in addNetworkResponseHook: " + e.getMessage());
        }
    }).start();
}


    // 创建悬浮球
    private ImageView createFloatingBall(Context themedContext, FrameLayout rootView) {
    ImageView floatingBall = new ImageView(themedContext);
    
    // 使用 themedContext 代替 activity 获取屏幕密度
    FrameLayout.LayoutParams ballParams =
            new FrameLayout.LayoutParams(dpToPx(50, themedContext), dpToPx(50, themedContext));
    ballParams.gravity = Gravity.TOP | Gravity.LEFT;
    ballParams.setMargins(100, 300, 0, 0);
    floatingBall.setLayoutParams(ballParams);

    // 创建圆形背景
    GradientDrawable drawable = new GradientDrawable();
    drawable.setShape(GradientDrawable.OVAL); // 设置为圆形
    drawable.setColor(Color.parseColor("#80FF0000")); // 设置颜色
    floatingBall.setBackground(drawable);

    // 使用 Glide 从网络加载图片
    new Handler(Looper.getMainLooper()).post(() -> {
        Glide.with(themedContext)  // 使用 themedContext 加载图片
                .load("http://103.40.13.76:54216/view.php/210750a9f293ba795097b90f246e7f9d.jpg")
                .apply(RequestOptions.bitmapTransform(new CircleCrop())) // 应用圆形裁剪
                .into(floatingBall);
    });

    return floatingBall;
}

// dpToPx 方法改为使用 Context 代替 Activity
private int dpToPx(int dp, Context context) {
    return Math.round(dp * (context.getResources().getDisplayMetrics().density));
}


    // 设置悬浮球触摸监听
    private void setFloatingBallTouchListener(ImageView floatingBall, CustomLayout customLayout, FrameLayout rootView) {
        floatingBall.setOnTouchListener(new View.OnTouchListener() {
            private float initialTouchX;
            private float initialTouchY;
            private int initialX;
            private int initialY;
            private boolean isCustomLayoutVisible = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = ((FrameLayout.LayoutParams) floatingBall.getLayoutParams()).leftMargin;
                        initialY = ((FrameLayout.LayoutParams) floatingBall.getLayoutParams()).topMargin;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        FrameLayout.LayoutParams ballParams =
                                (FrameLayout.LayoutParams) floatingBall.getLayoutParams();
                        ballParams.leftMargin = initialX + (int) (event.getRawX() - initialTouchX);
                        ballParams.topMargin = initialY + (int) (event.getRawY() - initialTouchY);
                        rootView.updateViewLayout(floatingBall, ballParams);
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(event.getRawX() - initialTouchX) < 10
                                && Math.abs(event.getRawY() - initialTouchY) < 10) {
                            // 切换 CustomLayout 的显示状态
                            isCustomLayoutVisible = !isCustomLayoutVisible;
                            customLayout.setVisibility(isCustomLayoutVisible ? View.VISIBLE : View.GONE);
                        }
                        return true;
                }
                return false;
            }
        });
    }

private void setupImGuiView(Activity activity) {
    FrameLayout rootView = activity.findViewById(android.R.id.content);

    // 创建渲染层
    display = new GLES3JNIView(activity);

    // 设置透明背景
    display.setZOrderOnTop(true);
    display.getHolder().setFormat(android.graphics.PixelFormat.TRANSLUCENT);

    // 设置触摸事件处理逻辑
    display.setOnTouchListener((v, event) -> {
    int action = event.getAction();

    // 判断触摸点是否在 ImGui 窗口范围内
    boolean isImGuiTouched = HOOK.isImGuiComponentTouched(event.getRawX(), event.getRawY());

    if (isImGuiTouched) {
        // 如果触摸在悬浮窗区域内，处理事件并消费掉
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                GLES3JNIView.MotionEventClick(true, event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                GLES3JNIView.MotionEventClick(false, event.getRawX(), event.getRawY());
                break;
        }
        return true; // 消费事件，阻止事件继续传递
    }

    // 如果触摸点不在悬浮窗范围内，事件传递到游戏层
    return false;
});

    // 添加到根布局
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
    );
    display.setLayoutParams(layoutParams);

    rootView.addView(display);
}

    

public static float[] getImGuiWindowBounds() {
    // 通过 JNI 调用 native 方法获取动态边界
    return nativeGetImGuiWindowBounds();
}
    
    
    
    
    public static boolean isImGuiComponentTouched(float x, float y) {
    float[] bounds = nativeGetImGuiWindowBounds();
    if (bounds.length == 4) {
        float left = bounds[0];
        float top = bounds[1];
        float right = bounds[2];
        float bottom = bounds[3];
        return x >= left && x <= right && y >= top && y <= bottom;
    }
    return false;
}
    private static native float[] nativeGetImGuiWindowBounds();
    
    // dp 转换为 px 的方法


}
