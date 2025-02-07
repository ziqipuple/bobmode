package com.ld.myapplication;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Handler; // 导入 Handler
import android.os.Looper; // 导入 Looper
import android.provider.SimPhonebookContract;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.util.Supplier;

import com.ld.myapplication.parameters;
import com.ld.myapplication.t3;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

import java.io.BufferedReader; // 导入 BufferedReader
import java.io.InputStreamReader; // 导入 InputStreamReader
import java.io.OutputStream; // 导入 OutputStream
import java.net.HttpURLConnection; // 导入 HttpURLConnection
import java.net.URL; // 导入 URL
import java.net.URLEncoder; // 导入 URLEncoder
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class CustomLayout extends LinearLayout {

    private LinearLayout verifyLayout; // 验证界面布局
    private EditText cardPasswordInput; // 卡密输入框
    private Button verifyButton; // 验证按钮
    private Button confirmButton;
    private LinearLayout announcementLayout; // 验证界面布局
    private LinearLayout updateLayout; // 验证界面布局
    private TextView updateStatusTextView;
    private LinearLayout topLayout; // 验证界面布局

    
    private FrameLayout contentFrame;
    private TextView gameSettings, skinSettings, operationSettings, advancedSettings;
    private parameters parameters;
    private SparseArray<LinearLayout> pageLayouts = new SparseArray<>(); // 用于存储页面布局
    private static FrameLayout rootView;
    private static RoundButtonCreator buttonCreator;
    private static Button 吐球;
    private static Button 三角;
    private static Button 蛇手左;
    private static Button 蛇手右;
    private static Button 四分测合左;
    private static Button 四分测合右;
    private static Button 后仰左;
    private static Button 后仰右;
    private static Button 旋转左;
    private static Button 旋转右;
    private static Button 四分;
    private static Button 冲球16分;
    private static Button 同步;
    private static Button 分身;
    private static Button 卡点;
    private static Button floatingButton16; // 在类顶部初始化
private static int savedInitialX = -1; // 按钮初始X位置，默认-1表示未初始化
private static int savedInitialY = -1; // 按钮初始Y位置，默认-1表示未初始化
    private static parameters cs;
    private static Memory memory;
    private static t3 t3;
    private static SimpleButtonController simpleButtonController;
    public static float joystickUp,joystickDown,joystickAngle;
    private Float initialAngle = null; // 用于存储首次获取的角度  
    private boolean isPressing = false; // 按钮是否被按下的标志
    private Thread pressThread; // 按下的线程
    private boolean isRunning = false;
    private int previousValue = 0;
    private long a1;
    private int fps;
    private long a2;
    private long a3;
    private long a4;
    private long a5;
    private long 粘合地址;
    private float 视图比例;
    private long aa;
    private boolean w1=true;
    private int 触摸id=5;
    private int 点击id=6;
    private DrawView drawView;//绘图类
    private long 数组;
    private long 数组地址 = 0; // 将数组地址变为类成员变量
    private List<String> 玩家信息列表 = new ArrayList<>();//玩家信息
    private Handler mainHandler = new Handler(Looper.getMainLooper()); // 主线程 Handler

    // private final CopyOnWriteArrayList<String> 玩家信息列表 = new CopyOnWriteArrayList<>();

    public CustomLayout(Context context, parameters parameters, FrameLayout rootView) {
        super(context);
        this.parameters = parameters;
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setLayoutParams(new LayoutParams(dpToPx(371), dpToPx(257)));
        parameters = new parameters();
        buttonCreator = new RoundButtonCreator(context, rootView);
        memory = new Memory();
        simpleButtonController = new SimpleButtonController(context, rootView);
        simpleButtonController.updateButtonVisibility(false);
        t3=new t3(context);
        
        // 在 CustomLayout 构造函数中初始化 drawView
    this.drawView = new DrawView(context);

        // 设置 DrawView 的布局参数
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
    this.drawView.setLayoutParams(params);

// 添加 DrawView 到根视图c
    rootView.addView(this.drawView);
    drawView.setZ(10);
    
    
//    
//    new Thread(() -> {
//    try {
//        Thread.sleep(00); 
//
//        while (true) {
//            try {
//                // 设置包名
//                memory.setPackageName("com.ztgame.bob");
//
//                // 获取模块地址
//                long moduleAddress = memory.getModuleAddress("libunity.so");
//
//                // 计算目标地址
//                long wq1 = moduleAddress + 0x747A98;
//
//                // 设置内存值
//                memory.setValue("16384", wq1, memory.TYPE_DWORD);
//
//                Thread.sleep(1);
//            } catch (Exception e) {
//                e.printStackTrace(); // 打印异常，防止线程崩溃
//            }
//        }
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    }
//}).start();
//    new Thread(() -> {
//
//        while (true) {
//            try {
//                // 设置包名
//                memory.setPackageName("com.ztgame.bob");
//                     //   memory.searchAndModifyMemory("com.ztgame.bob", "16384", memory.TYPE_DWORD, memory.RANGE_CODE_APP, "0", 40);
//
//        memory.setRange(9);
//        memory.RangeMemorySearch("16384", memory.TYPE_DWORD);
//        memory.MemoryWrite("0", memory.TYPE_DWORD, 0);
//        memory.clearResult();
//
//                Thread.sleep(10);
//            } catch (Exception e) {
//                e.printStackTrace(); // 打印异常，防止线程崩溃
//            }
//        }
//    
//}).start();
//    new Thread(() -> {
//
//        while (true) {
//            try {
//                // 设置包名
//                memory.setPackageName("com.ztgame.bob");
//                     //   memory.searchAndModifyMemory("com.ztgame.bob", "16384", memory.TYPE_DWORD, memory.RANGE_CODE_APP, "0", 40);
//
//        memory.setRange(9);
//        memory.RangeMemorySearch("16384", memory.TYPE_DWORD);
//        memory.MemoryWrite("0", memory.TYPE_DWORD, 0);
//        memory.clearResult();
//
//                Thread.sleep(10);
//            } catch (Exception e) {
//                e.printStackTrace(); // 打印异常，防止线程崩溃
//            }
//        }
//    
//}).start();


  //  monitorRootViewDrawFrequency(rootView);
// 初始化文件写入工具类
    PeriodicFileWriter fileWriter = new PeriodicFileWriter();

        // new Handler(Looper.getMainLooper()).postDelayed(() -> {
        //    try {
        //        // 设置文件路径（外置私有存储目录下的文件）
        //        String filePath =
        // "/storage/emulated/0/Android/data/com.ztgame.bob/files/vercache2022/android/common/data/unsafeconfig.unity3d_u_ea8c45dfb1d9954bdf55704e9aa0e339";
        //        fileWriter.initialize(context, filePath);
        //
        //        // 启动每隔 10 秒写入一次的任务
        //        fileWriter.startWriting(10_00); // 每 10 秒写入一次
        //    } catch (Exception e) {
        //        e.printStackTrace();
        //    }
        // }, 10_000); // 延迟 10 秒
        //

        // 停止任务（例如在 Activity 的 onDestroy 中）
        // fileWriter.stopWriting();

        // 设置整体背景色和圆角为紫色主题
        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.parseColor("#2D2A4A"));
        background.setCornerRadius(dpToPx(16));
        setBackground(background);

        Integer readValue = 文件读写.读取(context, "RankId.txt", Integer.class);
    if (readValue != null) {
        // 如果文件中有记录，就更新到指定排名ID变量
        specifiedRankId = readValue;
    } else {
        // 文件不存在或内容不对，使用默认值
        specifiedRankId = 10; // 或其他
    }
        Integer MyRankId1 = 文件读写.读取(context, "MyRankId.txt", Integer.class);
    if (MyRankId1 != null) {
        // 如果文件中有记录，就更新到指定排名ID变量
        myRankId = MyRankId1;
    } else {
        // 文件不存在或内容不对，使用默认值
        myRankId = 10; // 或其他
    }
        
       initializeUpdateUI(context);
        绘制玩家线段(context);
        
        //初始化验证界面
        //initializeVerificationUI(context);
      // initializeMainContent(context); // 初始化主内容
    }

    private void initializeUpdateUI(Context context) {
    // 创建更新界面布局
    updateLayout = new LinearLayout(context);
    updateLayout.setOrientation(VERTICAL);
    updateLayout.setGravity(Gravity.CENTER);
    LayoutParams layoutParams = new LayoutParams(dpToPx(350), dpToPx(200));
    updateLayout.setLayoutParams(layoutParams);

    // 设置更新界面背景
    GradientDrawable background = new GradientDrawable();
    background.setColor(Color.parseColor("#802D2A4A")); // 半透明深紫色背景
    background.setCornerRadius(dpToPx(16));
    updateLayout.setBackground(background);

    // 创建状态文本视图
    updateStatusTextView = new TextView(context);
    LayoutParams textParams = new LayoutParams(dpToPx(300), LayoutParams.WRAP_CONTENT);
    textParams.setMargins(0, dpToPx(20), 0, dpToPx(10));
    updateStatusTextView.setLayoutParams(textParams);
    updateStatusTextView.setText("正在检查更新...");
    updateStatusTextView.setTextColor(Color.parseColor("#FFD700")); // 设置为金黄色字体
    updateStatusTextView.setTextSize(16);
    updateStatusTextView.setGravity(Gravity.CENTER);

    // 创建确认按钮
    confirmButton = new Button(context);
    LayoutParams buttonParams = new LayoutParams(dpToPx(280), dpToPx(50));
    buttonParams.setMargins(0, dpToPx(10), 0, 0);
    confirmButton.setLayoutParams(buttonParams);
    confirmButton.setText("确认");
    confirmButton.setBackground(new GradientDrawable() {{
        setColor(Color.parseColor("#7A63FF"));
        setCornerRadius(dpToPx(10));
    }});
    confirmButton.setTextColor(Color.WHITE);
    confirmButton.setTextSize(16);
    confirmButton.setVisibility(View.GONE); // 初始隐藏

    // 设置确认按钮点击事件
    confirmButton.setOnClickListener(v -> {
        // 如果版本过期，可根据需求处理，例如退出程序
        removeView(updateLayout);
        updateStatusTextView.setText("版本过期，无法继续！");
    });

    // 将状态文本和按钮添加到更新界面
    updateLayout.addView(updateStatusTextView);
    updateLayout.addView(confirmButton);

    // 将更新界面添加到根布局
    addView(updateLayout);

    // 检查更新
    wy.更新(context, new wy.VersionCallback() {
        @Override
        public void onVersionReceived(String version) {
            new Handler(Looper.getMainLooper()).post(() -> {
                if (version.equals(wy.当前版本号)) {
                    // 版本一致，移除更新布局并延时初始化新的布局
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        removeView(updateLayout);
                        initializeAnnouncementUI(context);
                    }, 500); // 延时 2 秒
                } else {
                    // 版本不一致，显示版本已过期
                    updateStatusTextView.setText("版本已过期，请更新到最新版本！");
                    updateStatusTextView.setTextColor(Color.RED); // 将字体颜色改为红色
                    confirmButton.setVisibility(View.VISIBLE); // 显示确认按钮
                }
            });
        }

        @Override
        public void onError(Exception e) {
            new Handler(Looper.getMainLooper()).post(() -> {
                // 更新检测失败，显示错误信息
                updateStatusTextView.setText("更新检测失败: " + e.getMessage());
                updateStatusTextView.setTextColor(Color.RED); // 将字体颜色改为红色
                confirmButton.setVisibility(View.VISIBLE); // 显示确认按钮
            });
        }
    });
}

    // 初始化验证界面
    private void initializeVerificationUI(Context context) {
    verifyLayout = new LinearLayout(context);
    verifyLayout.setOrientation(VERTICAL);
    verifyLayout.setGravity(Gravity.CENTER);
    LayoutParams verifyLayoutParams = new LayoutParams(dpToPx(350), dpToPx(200));
    verifyLayout.setLayoutParams(verifyLayoutParams);

    // 设置验证界面背景
    GradientDrawable verifyBackground = new GradientDrawable();
    verifyBackground.setColor(Color.parseColor("#802D2A4A")); // 半透明深紫色背景
    verifyBackground.setCornerRadius(dpToPx(16));
    verifyLayout.setBackground(verifyBackground);

    // 创建卡密输入框
    cardPasswordInput = new EditText(context);
    LayoutParams cardParams = new LayoutParams(dpToPx(280), dpToPx(50));
    cardParams.setMargins(0, dpToPx(20), 0, dpToPx(10));
    cardPasswordInput.setLayoutParams(cardParams);
    cardPasswordInput.setHint("请输入卡密");
    cardPasswordInput.setTextColor(Color.WHITE);
    cardPasswordInput.setHintTextColor(Color.parseColor("#B9A6FF"));
    cardPasswordInput.setBackground(new GradientDrawable() {{
        setColor(Color.parseColor("#3A355A"));
        setCornerRadius(dpToPx(10));
    }});
    cardPasswordInput.setGravity(Gravity.CENTER);

    // 自动读取私有目录中的卡密并填充到输入框
    String savedCardPassword = 文件读写.读取私有目录(context, "km"); // 私有文件读取方法
    if (savedCardPassword != null && !savedCardPassword.isEmpty()) {
        cardPasswordInput.setText(savedCardPassword);
        // 自动尝试登录
        attemptLogin(savedCardPassword, context);
    }

    // 创建验证按钮
    verifyButton = new Button(context);
    LayoutParams buttonParams = new LayoutParams(dpToPx(280), dpToPx(50));
    buttonParams.setMargins(0, dpToPx(10), 0, 0);
    verifyButton.setLayoutParams(buttonParams);
    verifyButton.setText("登录");
    verifyButton.setBackground(new GradientDrawable() {{
        setColor(Color.parseColor("#7A63FF"));
        setCornerRadius(dpToPx(10));
    }});
    verifyButton.setTextColor(Color.WHITE);
    verifyButton.setTextSize(16);

    // 设置验证按钮的点击事件
    verifyButton.setOnClickListener(v -> {
        String cardPassword = cardPasswordInput.getText().toString();
        if (!cardPassword.isEmpty()) {
            attemptLogin(cardPassword, context);
        } else {
            Toast.makeText(context, "请输入卡密！", Toast.LENGTH_LONG).show();
        }
    });

    // 将输入框和按钮添加到验证界面
    verifyLayout.addView(cardPasswordInput);
    verifyLayout.addView(verifyButton);

    // 将验证界面添加到根布局
    addView(verifyLayout);
}

    /**
     * 尝试使用卡密进行登录
     *
     * @param cardPassword 卡密
     * @param context 上下文
     */
    private void attemptLogin(String cardPassword, Context context) {
    wy.登录(cardPassword, context, new wy.LoginCallback() {
        @Override
        public void onLoginSuccess(String message) {
            // 登录成功
            文件读写.读取私有目录(context, "km"); // 保存卡密到私有目录
            removeView(verifyLayout);
            initializeMainContent(context); // 初始化主内容
            RemoteVariables.initialize(cardPassword, context); // 初始化远程变量
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLoginFailure(String errorMessage) {
            // 登录失败
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }
    });
}

    private void initializeAnnouncementUI(Context context) {
    // 创建公告界面布局
    LinearLayout announcementLayout = new LinearLayout(context);
    announcementLayout.setOrientation(VERTICAL);
    announcementLayout.setGravity(Gravity.CENTER);
    LayoutParams layoutParams = new LayoutParams(dpToPx(350), dpToPx(200));
    announcementLayout.setLayoutParams(layoutParams);

    // 设置公告界面背景
    GradientDrawable background = new GradientDrawable();
    background.setColor(Color.parseColor("#802D2A4A")); // 半透明深紫色背景
    background.setCornerRadius(dpToPx(16));
    announcementLayout.setBackground(background);

    // 创建公告文本视图
    TextView announcementTextView = new TextView(context);
    LayoutParams textParams = new LayoutParams(dpToPx(300), LayoutParams.WRAP_CONTENT);
    textParams.setMargins(0, dpToPx(20), 0, dpToPx(10));
    
    announcementTextView.setLayoutParams(textParams);
    announcementTextView.setText("正在加载公告内容...");
    announcementTextView.setTextColor(Color.parseColor("#FFD700")); // 设置为金黄色字体
    announcementTextView.setTextSize(16);
    announcementTextView.setGravity(Gravity.CENTER);

    // 创建确认按钮
    Button confirmButton = new Button(context);
    LayoutParams buttonParams = new LayoutParams(dpToPx(280), dpToPx(50));
    buttonParams.setMargins(0, dpToPx(10), 0, 0);
    confirmButton.setLayoutParams(buttonParams);
    confirmButton.setText("确认");
    confirmButton.setBackground(new GradientDrawable() {{
        setColor(Color.parseColor("#7A63FF"));
        setCornerRadius(dpToPx(10));
    }});
    confirmButton.setTextColor(Color.WHITE);
    confirmButton.setTextSize(16);

    // 加载公告内容
    // 调用公告方法
wy.公告(context, new wy.Callback() {
    @Override
    public void onResult(String result) {
        // 在主线程更新 UI
        new Handler(Looper.getMainLooper()).post(() -> {
            announcementTextView.setText(result); // 显示公告内容
        });
    }

    @Override
    public void onError(Exception e) {
        // 在主线程更新 UI
        new Handler(Looper.getMainLooper()).post(() -> {
            announcementTextView.setText("公告加载失败: " + e.getMessage());
        });
    }
});

    // 设置确认按钮点击事件
    confirmButton.setOnClickListener(v -> {
        // 点击确认后，移除公告界面并执行下一步操作
        removeView(announcementLayout);
        initializeVerificationUI(context); // 进入验证界面
    });

    // 将公告文本和确认按钮添加到公告界面
    announcementLayout.addView(announcementTextView);
    announcementLayout.addView(confirmButton);

    // 将公告界面添加到根布局
    addView(announcementLayout);
}




    // 卡密验证逻辑
    private boolean validateCardPassword(String cardPassword) {
        // 这里可以添加具体的卡密验证逻辑
        return "123456".equals(cardPassword); // 示例验证卡密
    }

    // 创建主界面内容
    private void initializeMainContent(Context context) {
    // 标题栏
    LinearLayout topLayout = new LinearLayout(context);
    topLayout.setOrientation(VERTICAL);
    topLayout.setLayoutParams(new LayoutParams(dpToPx(371), dpToPx(50)));
    topLayout.setBackgroundColor(Color.TRANSPARENT);

    TextView textView = new TextView(context);
    LayoutParams textParams = new LayoutParams(dpToPx(326), dpToPx(40));
    textParams.setMargins(dpToPx(23), dpToPx(5), 0, 0);
    textView.setLayoutParams(textParams);
    textView.setText("游戏设置");
    textView.setTextColor(Color.parseColor("#B9A6FF"));
    textView.setTextSize(18);
    textView.setGravity(Gravity.CENTER_VERTICAL);
    textView.setPadding(dpToPx(8), 0, 0, 0);
    topLayout.addView(textView);

    addView(topLayout);

    // 主内容布局
    LinearLayout mainContentLayout = new LinearLayout(context);
    mainContentLayout.setLayoutParams(new LayoutParams(dpToPx(371), dpToPx(206)));
    mainContentLayout.setOrientation(HORIZONTAL);
    mainContentLayout.setBackgroundColor(Color.TRANSPARENT);

    // 左侧菜单布局
    LinearLayout menuLayout = new LinearLayout(context);
    LayoutParams menuLayoutParams = new LayoutParams(dpToPx(70), LayoutParams.MATCH_PARENT);
    menuLayoutParams.setMargins(dpToPx(16), dpToPx(8), 0, dpToPx(8));
    menuLayout.setLayoutParams(menuLayoutParams);
    menuLayout.setOrientation(VERTICAL);

    GradientDrawable menuBackground = new GradientDrawable();
    menuBackground.setColor(Color.parseColor("#3A355A"));
    menuBackground.setCornerRadius(dpToPx(16));
    menuLayout.setBackground(menuBackground);

    gameSettings = createMenuTextView(context, "内存功能", 0);
    skinSettings = createMenuTextView(context, "自动操作", 1);
    operationSettings = createMenuTextView(context, "参数调节", 2);
    advancedSettings = createMenuTextView(context, "还没想好", 3);

    menuLayout.addView(gameSettings);
    menuLayout.addView(skinSettings);
    menuLayout.addView(operationSettings);
    menuLayout.addView(advancedSettings);

    mainContentLayout.addView(menuLayout);

    // 右侧内容布局容器，用于加载不同的分页
    contentFrame = new FrameLayout(context);
    contentFrame.setLayoutParams(new LayoutParams(dpToPx(285), LayoutParams.MATCH_PARENT));
    contentFrame.setBackgroundColor(Color.TRANSPARENT);

    mainContentLayout.addView(contentFrame);
    addView(mainContentLayout);

    // 显示第一页内容
    showPage(0);

    // 启动更新检测线程
    startUpdateChecker(context, topLayout, mainContentLayout);
}

private void startUpdateChecker(Context context, View topLayout, View mainContentLayout) {
        new Thread(
                        () -> {
                            while (true) {
                                try {
                                    // 每隔 60 秒检查一次版本更新
                                    Thread.sleep(6000);

                                    wy.更新(
                                            context,
                                            new wy.VersionCallback() {
                                                @Override
                                                public void onVersionReceived(String version) {
                                                    if (!version.equals(wy.当前版本号)) {
                                                        // 切换到主线程更新视图
                                                        new Handler(Looper.getMainLooper())
                                                                .post(
                                                                        () -> {
                                                                            // 移除主内容布局并显示更新界面
                                                                            removeView(
                                                                                    mainContentLayout);
                                                                            removeView(topLayout);
                                                                            Button[]
                                                                                    floatingButtons =
                                                                                            new Button
                                                                                                    [] {
                                                                                                吐球,
                                                                                                        三角,
                                                                                                        蛇手左,
                                                                                                        蛇手右,
                                                                                                四分测合左,
                                                                                                        四分测合右,
                                                                                                        后仰左,
                                                                                                        后仰右,
                                                                                                旋转左,
                                                                                                        旋转右,
                                                                                                        四分,
                                                                                                        冲球16分,
                                                                                                同步,
                                                                                                        分身,
                                                                                                        卡点,
                                                                                                        floatingButton16
                                                                                            };

                                                                            // 使用循环隐藏所有按钮
                                                                            for (int i = 0;
                                                                                    i
                                                                                            < floatingButtons
                                                                                                    .length;
                                                                                    i++) {
                                                                                if (floatingButtons[
                                                                                                i]
                                                                                        != null) {
                                                                                    floatingButtons[
                                                                                            i]
                                                                                            .setVisibility(
                                                                                                    View
                                                                                                            .GONE); // 隐藏按钮
                                                                                }
                                                                            }

                                                                            initializeUpdateUI(
                                                                                    context);
                                                                        });
                                                    }
                                                }

                                                @Override
                                                public void onError(Exception e) {
                                                    // 可以选择记录日志或显示错误信息，当前略过
                                                }
                                            });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    break; // 停止线程
                                }
                            }
                        })
                .start();
}


    private TextView createMenuTextView(Context context, String text, int pageIndex) {
        TextView textView = new TextView(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(40));
        params.setMargins(0, dpToPx(6), 0, 0);
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextSize(14);
        textView.setTextColor(Color.parseColor("#B9A6FF"));
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(0, dpToPx(6), 0, 0);
        textView.setClickable(true);
        textView.setBackgroundColor(Color.TRANSPARENT);

        textView.setOnClickListener(v -> showPage(pageIndex));

        return textView;
    }

    private void showPage(int pageIndex) {
        contentFrame.removeAllViews();

        LinearLayout pageLayout = pageLayouts.get(pageIndex);
        if (pageLayout == null) {
            if (pageIndex == 0) {
                pageLayout = createPage1Layout(getContext());
            } else if (pageIndex == 1) {
                pageLayout = createPage2Layout(getContext());
            } else if (pageIndex == 2) {
                pageLayout = createPage3Layout(getContext());
            } else if (pageIndex == 3) {
                pageLayout = createPage4Layout(getContext());
            }
            pageLayouts.put(pageIndex, pageLayout);
        }

        contentFrame.addView(pageLayout);
        updateMenuHighlight(pageIndex);
    }

    private LinearLayout createPage1Layout(Context context) {
        LinearLayout page1Layout = new LinearLayout(context);
        page1Layout.setOrientation(VERTICAL);

        ScrollView scrollView = new ScrollView(context);
        scrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        LinearLayout switchContainer = new LinearLayout(context);
        switchContainer.setOrientation(VERTICAL);

        // 设置左右相同的边距
        LayoutParams switchContainerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        int margin = dpToPx(16); // 假设16dp的边距
        switchContainerParams.setMargins(margin, 0, margin, 0);
        switchContainer.setLayoutParams(switchContainerParams);

        // 创建折叠区域的视图，初始状态隐藏
        LinearLayout 视野 = new LinearLayout(context);
        视野.setOrientation(VERTICAL);
        视野.setVisibility(View.GONE); // 初始隐藏
        视野.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        // 变量保存搜索到的内存地址
        final long[] memoryAddressHolder = new long[1]; // 使用long数组保存内存地址
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        // 调用 addFloatSeekBarWithEditText 方法，传入初始值和范围
        addFloatSeekBarWithEditText(context, 视野, "视野", 0.5f, 0.5f, 10f, value -> {
            if (memoryAddressHolder[0] != 0) { // 判断是否已获取到内存地址
                // 将浮动值转换为字符串并启动线程执行内存写入
                String stringValue = String.valueOf(value);
                new Thread(() -> {
                    try {
                        // 使用已保存的内存地址进行写入操作
                        memory.setValue(stringValue, memoryAddressHolder[0], memory.TYPE_FLOAT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

                // 实时显示转换后的浮动数值
                System.out.println("当前双连点速度: " + value);
            }
        });
        LinearLayout 粘合 = new LinearLayout(context);
        粘合.setOrientation(VERTICAL);
        粘合.setVisibility(View.GONE); // 初始隐藏
        粘合.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));


        // 调用 addFloatSeekBarWithEditText 方法，传入初始值和范围
        addFloatSeekBarWithEditText(context, 粘合, "粘合", 0.58823525906f, -1f, 2f, value -> {
   // 提示.短时提示(context, "粘合值: " + value);

    if (粘合地址 != 0) { // 确保地址有效
        String stringValue = String.valueOf(value);

        new Thread(() -> {
            try {
                memory.setValue(stringValue, 粘合地址, memory.TYPE_FLOAT);
                System.out.println("粘合值写入成功: " + stringValue);

                new Handler(Looper.getMainLooper()).post(() -> {
                    float readValue = memory.readFloat(粘合地址);
                    //提示.短时提示(context, "粘合值: " + readValue);
                  //  System.out.println("提示显示完成: 粘合值: " + readValue);
                });
            } catch (Exception e) {
             提示.短时提示(context,"粘合写入失败: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    } else {
        System.out.println("粘合地址无效");
    }
});



        // 添加8个开关按钮
        String[] switchNames = { "过检测","双连点巨星星吐一颗球", "视野",  
                                "画圆","画线条","画排名","粘合","il2cpp工具", "阿巴阿巴", "自定义开关G"};

        for (int i = 0; i < switchNames.length; i++) {
            final String switchName = switchNames[i];
            Switch switchButton = new Switch(context);
            switchButton.setText(switchName);
            switchButton.setTag(switchName); // 设置Tag
            switchButton.setTextColor(Color.parseColor("#B9A6FF"));
            applySwitchStyle(switchButton);

            // 开关状态监听
            switchButton.setOnCheckedChangeListener(
                    (CompoundButton buttonView, boolean isChecked) -> {
                        String name = (String) buttonView.getTag();
                        if (isChecked) {
                            // 开关被打开时的操作
                            switch (name) {
                                case "解除帧率限制":
                                    unlockFrameRate("com.ztgame.bob", mainHandler);
                                    break;
                                case "双连点巨星星吐一颗球":
                                    performMemoryOperation(
                                            "com.ztgame.bob",
                                            memory.RANGE_ANONYMOUS,
                                            "197720",
                                            "-0.08");
                                    break;
                                case "视野":
                                    // 执行视野操作
                                    视野.setVisibility(View.VISIBLE);
                                    fetchMemoryAddressAndUpdateUI(
                                            "com.ztgame.bob",
                                            memory.RANGE_ANONYMOUS,
                                            "20000",
                                            mainHandler,
                                            memoryAddressHolder);
                                    break;
                                case "指针跳转读取":
                                    break;
                                case "绘图":
                                    绘制玩家线段(context);
                                    //    if (drawView != null) {
                                    //        drawView.addCircle(500, 500, 100, Color.BLUE, 5); //
                                    // 调用 addCircle 方法
                                    //    } else {
                                    //        XposedBridge.log("CustomLayout"+"DrawView
                                    // 未初始化，无法绘制圆形");
                                    //    }
                                    break;
                                case "il2cpp工具":
                                    try {
                                        // 动态加载 libexample.so，假设已经随 APK 部署到 /lib/armeabi-v7a/
                                        System.loadLibrary("Tool"); // 加载 libexample.so
                                        Log.d("il2cpp工具", "动态库加载成功！");
                                    } catch (UnsatisfiedLinkError e) {
                                        Log.e("il2cpp工具", "加载动态库失败: " + e.getMessage());
                                    }
                                    break;
                                case "画圆":
                                    enableDrawCircle = true;
                                    break;
                                case "画线条":
                                    enableDrawLine = true;
                                    break;
                                case "画排名":
                                    画排名 = true;
                                    break;
                                case "过检测":
                        

        
        
                                    break;
                                case "粘合":
                                    提示.短时提示(context, memory.readFloat(粘合地址)+"");
                                    粘合.setVisibility(View.VISIBLE);
                                    break;
                            }
                            showToast(context, name + " 开启");
                        } else {
                            // 开关被关闭时的操作
                            switch (name) {
                                case "解除帧率限制":
                                    // 关闭解除帧率限制的操作
                                    break;
                                case "双连点":
                                    // 关闭双连点的操作
                                    break;
                                case "视野":
                                    // 关闭视野操作
                                    视野.setVisibility(View.GONE);
                                    break;
                                case "阿巴阿巴":
                                    // 关闭阿巴阿巴操作
                                    break;
                                case "绘图":
                                    drawView.clearAllShapes();

                                    break;
                                case "画圆":
                                    enableDrawCircle = false;
                                    break;
                                case "画线条":
                                    enableDrawLine = false;
                                    break;
                                case "画排名":
                                    画排名 = false;
                                    break;
                                case "粘合":
                                    粘合.setVisibility(View.GONE);
                                    break;
                            }
                            showToast(context, name + " 关闭");
                        }
                    });

            // 将Switch按钮添加到容器中
            switchContainer.addView(switchButton);

            // 在“双连点”开关下方添加折叠区域
            if ("视野".equals(switchName)) {
                switchContainer.addView(视野);
            }
            if ("粘合".equals(switchName)) {
                switchContainer.addView(粘合);
            }
        }

        scrollView.addView(switchContainer);
        page1Layout.addView(scrollView);

        return page1Layout;
    }

    public int add(int a, int b) {
        return a + b; // 原始方法
    }
    // 内存操作方法：执行内存读取和写入
    private void performMemoryOperation(String packageName, int range, String searchValue, String writeValue) {
        new Thread(() -> {
            try {
                //memory.clearResult();
                memory.setPackageName(packageName);
                memory.setRange(range);
                memory.RangeMemorySearch(searchValue, memory.TYPE_DWORD);
                memory.MemoryOffset("3", memory.TYPE_DWORD, -44);
                memory.MemoryOffset("3", memory.TYPE_DWORD, -32);
                memory.MemoryOffset("4", memory.TYPE_DWORD, -28);
                memory.MemoryWrite(writeValue, memory.TYPE_FLOAT, -16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // 获取内存地址并更新UI
    private void fetchMemoryAddressAndUpdateUI(String packageName, int range, String searchValue, Handler mainHandler, final long[] memoryAddressHolder) {
        new Thread(() -> {
            try {
                memory.clearResult();
                memory.setPackageName(packageName);
                memory.setRange(range);
                memory.RangeMemorySearch(searchValue, memory.TYPE_FLOAT);
                memory.MemoryOffset("500", memory.TYPE_FLOAT, 12);
                memory.MemoryOffset("500", memory.TYPE_FLOAT, 24);

                int resultCount = memory.getResultCount();
                if (resultCount > 0) {
                    long[] resultAddresses = memory.getResult(resultCount);

                    for (long addr : resultAddresses) {
                        memoryAddressHolder[0] = addr - 388;

                        mainHandler.post(() -> {
                            System.out.println("已获取到内存地址: " + memoryAddressHolder[0]);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

  // =========================
// 全局变量与状态标记
// =========================
// =========================
// 全局变量 & 状态标识
// =========================

/************************************************
 * 1. 首先定义一个 PlayerEntity 结构体（替代原有的字符串存储方式）
 *    - 用于存储每个球体（对象）的关键信息
 ************************************************/
/****************************************************************************
 * 1. 定义 PlayerEntity 结构体
 *    - 不再使用全局编号，而是通过 rankId 分组。
 *    - 在同一个 rankId 组内，通过 subIndex 给它分组内的排名（0 表示该组最大半径）。
 ****************************************************************************/
/************************************************
 * 1) 定义 PlayerEntity 结构不变
 ************************************************/
/**************************************************************
 * 修复说明：
 * 1) 将所有的调试输出由 Android Log 改为 XposedBridge.log 方式；
 * 2) 同步按钮被点击后，先获取「指定排名ID」下最大球相对于「自身ID」下最大球的角度(角度1)；
 * 3) 触发分裂操作，等待分裂完成后，过滤掉原本分裂的那颗球(旧地址)，
 *    获取新的分裂球(新地址)中半径最大的那颗，并计算它相对于「自身ID」下最大球的角度(角度2)；
 * 4) 将角度1 和 角度2 存到全局变量，并用提示方式显示到主界面。
 **************************************************************/


/**************************************************************
 * 新增与调整的功能要点：
 * 1) 给每个球体分配一个唯一编号 (uniqueId)，以方便区分究竟是“旧球”还是“新球”。
 *   - 在扫描玩家结构体时，如果当前球体的内存地址尚未在全局映射中登记过，则分配一个自增的 uniqueId 并保存。
 *   - 将该 uniqueId 存入 PlayerEntity 中，以后在判断“旧地址/新地址”时，更可靠。
 *
 * 2) 在计算角度时，根据用户需求：
 *   - 如果“原本的是顺时针”就改为“逆时针”，反之亦然，这里采取最简单的方式：角度直接反转。
 *   - 反转方法之一： angle = 360 - angle (若想要直接转到对立角度)。
 *   - 示例：若原角度=78.9°，则反转后= 360-78.9=281.1°，即可视为“对侧”或“倒向”。
 *
 * 3) 当“指定排名球”分裂后，过滤旧球(通过 uniqueId 或 address 识别)，
 *   找到新分裂出来的球(同组中未出现过的 uniqueId 或 address)，并计算角度。
 *   若要对新角度也做同样的顺逆时针反转，可同理再处理。
 *
 * 下方示例代码在原基础上进行修改示意：
 **************************************************************/

/**************************************************************
 * 最终示例代码（示意）—— 使用 uniqueId 区分球体，
 * 并在分裂后自动吃掉队友旧球，再反转方向合给队友新球。
 *
 * 注意：
 * 1) 以下代码以“球球大作战”类似逻辑为参照，示例了整个流程：
 *    - 扫描内存并分配 uniqueId (用于识别球体)；
 *    - 在同步/分裂逻辑中，按下分身(不需手动移动)，让分裂球自动吃掉队友旧球；
 *    - 吃完后，再反转角度(或再次分裂、吐球等)把质量合给队友新球；
 * 2) 实际游戏逻辑可能更复杂，需根据你自己对游戏内存/地址/坐标的理解，
 *    以及分裂球“自动追踪队友旧球”的具体机制来做适配。
 * 3) 本示例仅供参考，部分函数(如 触发…功能)里包含线程延时、角度滑动等操作，
 *    需在实际环境中不断调试、调整参数。
 **************************************************************/


    // =========== 1) 数据结构 ===========

    /**************************************************************
 * 最终示例代码——在分裂前后分别计算旧球与新球相对于“我方最大球”的角度(角度1、角度2)，
 * 并同时保留原本的逻辑：用 uniqueId 区分球体，在分裂后自动吃掉队友旧球，再把质量合给队友新球。
 * 
 * 主要修改点：
 * 1) 在「同步」分裂前，先计算旧球相对于我方最大球的角度 => 角度1，并保存到全局变量。
 * 2) 在分裂完、吃掉旧球、重新获取队友组后，计算新球相对于我方最大球的角度 => 角度2，并保存到全局变量。
 * 3) 最后，将 angle1、angle2 一并通过提示或日志输出。
 **************************************************************/

    // =================== 1) 数据结构 ===================

    // 玩家球体信息
    private static class PlayerEntity {
        public long address;       // 内存地址(识别球体)
        public long uniqueId;      // 全局唯一编号
        public float x;            // 世界坐标 X
        public float y;            // 世界坐标 Y
        public float radius;       // 球体半径
        public float volume;       // 球体体积
        public int subIndex;       // 在同 rankId 分组内的排序(0=>最大球)
        public int rankId;         // rankId(组ID)

        public PlayerEntity(long address, long uniqueId, float x, float y, float radius, int rankId) {
            this.address = address;
            this.uniqueId = uniqueId;
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.rankId = rankId;
            this.volume = (float) ((4 / 3.0) * Math.PI * Math.pow(radius, 3));
        }
    }

    // rankId => List<PlayerEntity>
    private Map<Integer, List<PlayerEntity>> rankToPlayers = new HashMap<>();
    // 扁平映射: uniqueId => PlayerEntity
    private Map<Long, PlayerEntity> allPlayers = new HashMap<>();
    // address => uniqueId
    private Map<Long, Long> addressToUniqueIdMap = new HashMap<>();
    private static long globalUniqueCounter = 1;

    // =================== 2) 全局控制变量 ===================

    private boolean isDetecting = false;
    private int cardSelectedRank = -1;
    private int cardSelectedSubIndex = -1;
    private float 锁定球体初始体积 = -1;
    private boolean isSelectedCircleVisible = false;

    private boolean isSyncDetecting = false;
    private boolean isSyncCircleVisible = false;
    private int syncSelectedSubIndex = -1;
    private float 锁定同步球体初始体积 = -1;
    private boolean allowReacquire = true;

    // 在分裂前后记录的角度
    private float 角度1 = 0f;  // 旧球相对于我方最大球的角度
    private float 角度2 = 0f;  // 新球相对于我方最大球的角度

    // 指定队友rankId，我方rankId
    private int specifiedRankId = 10;
    private int myRankId = 99;  

    // 视图/绘制控制
    private boolean showCardButton = false;
    private boolean showRankButton = false;
    private boolean 画排名 = false;
    private boolean enableDrawCircle = false;
    private boolean enableDrawLine = false;

    // UI 按钮移动
    private boolean isMoved = false;
    private final float MOVE_THRESHOLD = 100f;
    private float downX, downY;

    private int 刷新延时 = 8;  


    /************************************************
     * 5) 获取玩家结构体 => rankToPlayers + allPlayers
     ************************************************/
    private ExecutorService executorService = Executors.newFixedThreadPool(8); // 使用固定大小的线程池

private void 获取玩家结构体(Context context, String fileName) {
    new Thread(() -> {
        try {
            int bits = 64;
            long 数组地址 = a5; // 你自己的偏移
            Handler handler = new Handler(Looper.getMainLooper());

            Runnable updateRunnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        // 临时保存 rankId -> 玩家列表
                        Map<Integer, List<PlayerEntity>> 临时map = new HashMap<>();

                        // 读取视图比例
                        float 视图比例1 = memory.readFloat(a3 - 0x38);
                        handler.post(() -> 视图比例 = 视图比例1);

                        // 1. 先收集所有“实际玩家”的基址列表
                        List<Long> 实际玩家地址列表 = new ArrayList<>();

                        // 遍历 0~3000 的玩家结构体指针位置，分批读取
                        int batchSize = 300; // 每次读取100个
                        for (int i = 0; i < 6000; i += batchSize) {
                            int end = Math.min(i + batchSize, 6000);  // 处理每一批
                            List<Callable<Void>> tasks = new ArrayList<>();

                            // 为每一批创建并发任务
                            for (int j = i; j < end; j++) {
                                int finalJ = j;
                                tasks.add(() -> {
                                    long[] 玩家结构体指针 = memory.pointerJump(
                                            数组地址 + finalJ * 0x18,
                                            new long[]{0x18, 0x0},
                                            bits
                                    );
                                    // 如果指针链无效，则跳过
                                    if (!checkPointerChain(玩家结构体指针)) {
                                        return null;
                                    }
                                    long playerBaseAddr = 玩家结构体指针[玩家结构体指针.length - 1];
                                    // 判断是否是实际玩家
                                    if (memory.readDword(playerBaseAddr + 0x20) == 1) {
                                        // 如果是实际玩家，就把地址保存到列表中
                                        synchronized (实际玩家地址列表) {
                                            实际玩家地址列表.add(playerBaseAddr);
                                        }
                                    }
                                    return null;
                                });
                            }

                            // 使用线程池并行处理任务
                            executorService.invokeAll(tasks);
                        }

                        // 2. 逐个读取实际玩家的详细信息
                        for (long playerBaseAddr : 实际玩家地址列表) {
                            // 读取玩家信息，依赖于 `playerBaseAddr`
                            // 这部分与之前的代码类似，可以并行化这个部分
                            readPlayerDetails(playerBaseAddr, 临时map);
                        }

                        // 3. 每组按半径降序 => 赋予 subIndex
                        for (Map.Entry<Integer, List<PlayerEntity>> e : 临时map.entrySet()) {
                            List<PlayerEntity> group = e.getValue();
                            // 将列表按照 radius 从大到小排序
                            group.sort((a, b) -> Float.compare(b.radius, a.radius));
                            // 依次赋值 subIndex
                            for (int j = 0; j < group.size(); j++) {
                                group.get(j).subIndex = j;
                            }
                        }

                        // 4. 更新全局映射关系
                        rankToPlayers.clear();
                        rankToPlayers.putAll(临时map);

                        // 5. 扁平化数据到 allPlayers
                        updateAllPlayers(rankToPlayers);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 延迟刷新
                    handler.postDelayed(this, 刷新延时);
                }
            };

            // 启动定时刷新线程
            handler.post(updateRunnable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }).start();
}

// 读取玩家的详细信息
private void readPlayerDetails(long playerBaseAddr, Map<Integer, List<PlayerEntity>> 临时map) {
    try {
        int bits = 64;
        // 读取坐标和排名ID
        long[] 坐标指针 = memory.pointerJump(playerBaseAddr, new long[]{0x18, 0x0}, bits);
        if (!checkPointerChain(坐标指针)) return;
        long 坐标地址 = 坐标指针[坐标指针.length - 1];

        long[] 排名id指针 = memory.pointerJump(坐标地址, new long[]{0x30, 0x0}, bits);
        if (!checkPointerChain(排名id指针)) return;
        long 排名id地址 = 排名id指针[排名id指针.length - 1];

        float 半径 = memory.readFloat(playerBaseAddr + 0x28);
        int rankId = memory.readDword(排名id地址 + 0x10);
        float x = memory.readFloat(坐标地址 + 0x80);
        float y = 200 - memory.readFloat(坐标地址 + 0x84);

        long uniqueId = globalUniqueCounter++;
        addressToUniqueIdMap.put(playerBaseAddr, uniqueId);

        PlayerEntity entity = new PlayerEntity(playerBaseAddr, uniqueId, x, y, 半径, rankId);
        // 按照 rankId 分组
        synchronized (临时map) {
            临时map.computeIfAbsent(rankId, k -> new ArrayList<>()).add(entity);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    // 扁平化
    private void updateAllPlayers(Map<Integer, List<PlayerEntity>> map) {
        allPlayers.clear();
        for (Map.Entry<Integer, List<PlayerEntity>> entry : map.entrySet()) {
            List<PlayerEntity> list = entry.getValue();
            if (list == null) continue;
            for (PlayerEntity p : list) {
                allPlayers.put(p.uniqueId, p);
            }
        }
    }

    private boolean checkPointerChain(long[] pointerResult) {
        if (pointerResult == null) return false;
        for (long addr : pointerResult) {
            if (addr == 0) return false;
        }
        return true;
    }

    /************************************************
     * 6) 绘制玩家线段 (示意)
     ************************************************/
    public void 绘制玩家线段(Context context) {
        if (specifiedRankId == -1) {
            specifiedRankId = 10;
        }

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable drawRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    drawView.clearAllShapes();
                    if (rankToPlayers == null || rankToPlayers.isEmpty()) {
                        handler.postDelayed(this, 8);
                        return;
                    }

                    float startX = parameters.分辨率x / 2f;
                    float startY = parameters.分辨率y / 2f;
                    float offsetX = memory.readFloat(a3 - 100);
                    float offsetY = 200 - memory.readFloat(a3 - 96);

                    // 遍历各组
                    for (Map.Entry<Integer, List<PlayerEntity>> entry : rankToPlayers.entrySet()) {
                        int rankId = entry.getKey();
                        List<PlayerEntity> groupList = entry.getValue();
                        if (groupList == null || groupList.isEmpty()) continue;

                        for (PlayerEntity entity : groupList) {
                            float screenXa = ((entity.x - offsetX)
                                    * 23.25f * parameters.比例
                                    * (23.25f * parameters.比例 / 视图比例))
                                    + startX;
                            float screenYa = ((entity.y - offsetY)
                                    * 23.25f * parameters.比例
                                    * (23.25f * parameters.比例 / 视图比例))
                                    + startY;
                            float drawRadius = entity.radius
                                    * 23.25f * parameters.比例
                                    * (23.25f * parameters.比例 / 视图比例);

                            if (!isSelectedCircleVisible) {
                                // 普通绘制
                                if (enableDrawLine) {
                                    drawView.addLine(
                                            startX, startY,
                                            screenXa, screenYa,
                                            Color.RED, 3f
                                    );
                                刷新延时=8;
                                }
                                if (enableDrawCircle) {
                                    if (isDetecting
                                            && rankId == cardSelectedRank
                                            && entity.subIndex == cardSelectedSubIndex) {
                                        drawView.addCircle(screenXa, screenYa, drawRadius, Color.YELLOW, 6f);
                                    刷新延时=1;
                                    } else {
                                        drawView.addCircle(screenXa, screenYa, drawRadius, Color.RED, 3f);
                                    刷新延时=8;
                                    }
                                }
                            } else {
                                // 如果处于“选中持续可见”状态
                                if (rankId == cardSelectedRank && entity.subIndex == cardSelectedSubIndex) {
                                    drawView.addCircle(screenXa, screenYa, drawRadius, Color.YELLOW, 6f);
                                刷新延时=1;
                                }
                            }

                            // 卡点判断
                            if (isDetecting && rankId == cardSelectedRank && entity.subIndex == cardSelectedSubIndex) {
                                float currentVolume = entity.volume;
                                if (锁定球体初始体积 < 0) {
                                刷新延时=1;
                                    锁定球体初始体积 = currentVolume;
                                } else if (currentVolume < 锁定球体初始体积 * 0.5f) {
                       
            HOOK.TouchEventHandler.按下(
                    simpleButtonController.分身x,
                    simpleButtonController.分身y,
                    点击id
            );
            HOOK.TouchEventHandler.抬起(
                    simpleButtonController.分身x,
                    simpleButtonController.分身y,
                    点击id
            );
                                    触发一分为二功能(context);
                                刷新延时=8;
                                    停止检测并恢复状态();
                                }
                            }

                            // 同步检测 => 分裂判断
                            if (isSyncDetecting && rankId == specifiedRankId) {
                                if (syncSelectedSubIndex < 0 || syncSelectedSubIndex >= groupList.size()) {
                                    if (allowReacquire) {
                                        reAcquireSyncBall(context);
                                    } else {
                                        停止检测并恢复状态();
                                    }
                                } else {
                                    PlayerEntity syncTarget = groupList.get(syncSelectedSubIndex);
                                    if (syncTarget.rankId != specifiedRankId) {
                                        if (allowReacquire) {
                                            reAcquireSyncBall(context);
                                        } else {
                                            停止检测并恢复状态();
                                        }
                                    } else {
                                        float currentVolume = syncTarget.volume;
                                        if (锁定同步球体初始体积 < 0) {
                                            锁定同步球体初始体积 = currentVolume;
                                        } else if (currentVolume < 锁定同步球体初始体积 * 0.5f) {
                                            触发排名一分为二(context);
                                            停止检测并恢复状态();
                                        }
                                    }
                                }
                            }
                        }

                        // 画排名(只画该组 subIndex=0)
                        if (画排名 && !groupList.isEmpty()) {
    PlayerEntity maxBall = groupList.get(0);
    float screenXa = ((maxBall.x - offsetX) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + startX;
    float screenYa = ((maxBall.y - offsetY) * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例)) + startY;
    float drawRadius = maxBall.radius * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例);
    
    // 计算文字显示位置：球体正上方的中间位置
    float textPositionY = screenYa - (drawRadius / 2f); // 在球体中心和边缘的中间位置

    // 根据球体半径调整文字大小
    float textSize = drawRadius * 0.5f; // 将文字大小与球体半径挂钩，例如设置为半径的 50%
    if (textSize < 10f) {
        textSize = 10f; // 设置最小文字大小，避免文字太小无法看清
    } else if (textSize > 100f) {
        textSize = 100f; // 设置最大文字大小，避免文字太大溢出
    }

    // 调整文字绘制的位置
    float textWidth = getTextWidth(maxBall.rankId, textSize); // 计算文字的宽度
    float textStartX = screenXa - (textWidth / 2f); // 将文字的起点设置为中心偏移半个宽度

    drawView.addTextWithFormatting(
            maxBall.rankId,
            textStartX, // 使用计算后的居中起点
            textPositionY, // 在球体正上方的中间位置
            Color.WHITE,
            textSize,
            5,
            1.5f
    );
}



                    }

                    // 若同步开启，绘制选中的球(蓝色圆)
                    if (isSyncCircleVisible && isSyncDetecting && rankToPlayers.containsKey(specifiedRankId)) {
                        List<PlayerEntity> syncGroup = rankToPlayers.get(specifiedRankId);
                        if (syncGroup != null
                                && syncSelectedSubIndex >= 0
                                && syncSelectedSubIndex < syncGroup.size()) {
                            PlayerEntity lockBall = syncGroup.get(syncSelectedSubIndex);
                            float screenXa = ((lockBall.x - offsetX)
                                    * 23.25f * parameters.比例
                                    * (23.25f * parameters.比例 / 视图比例)) + startX;
                            float screenYa = ((lockBall.y - offsetY)
                                    * 23.25f * parameters.比例
                                    * (23.25f * parameters.比例 / 视图比例)) + startY;
                            float drawRadius = lockBall.radius
                                    * 23.25f * parameters.比例
                                    * (23.25f * parameters.比例 / 视图比例);

                            drawView.addCircle(screenXa, screenYa, drawRadius, Color.BLUE, 6f);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.postDelayed(this, 刷新延时);
            }
        };
        handler.post(drawRunnable);

    if (卡点 == null) {
        卡点 = buttonCreator.createRoundButton(
                "卡点",
                300,
                Color.GREEN,
                100, 200,
                new RoundButtonCreator.OnButtonTouchListener() {
                    @Override
                    public void onButtonDown() {
                        downX = 卡点.getX();
                        downY = 卡点.getY();
                        isMoved = false;
                        RoundButtonCreator.isMovable = true;
                    }

                    @Override
                    public void onButtonUp() {
                        if (isMoved) {
                            XposedBridge.log("按钮事件: 移动超过阈值，取消功能触发");
                            isMoved = false;
                            RoundButtonCreator.isMovable = false;
                            return;
                        }
                        isDetecting = !isDetecting;
                        RoundButtonCreator.isMovable = false;

                        if (isDetecting) {
                            cardSelectedRank = -1;
                            cardSelectedSubIndex = -1;
                            锁定球体初始体积 = -1;

                            float centerX = parameters.分辨率x / 2f;
                            float centerY = parameters.分辨率y / 2f;
                            float offsetX = memory.readFloat(a3 - 100);
                            float offsetY = 200 - memory.readFloat(a3 - 96);

                            outerLoop:
                            for (Map.Entry<Integer, List<PlayerEntity>> entry : rankToPlayers.entrySet()) {
                                int rankId = entry.getKey();
                                List<PlayerEntity> groupList = entry.getValue();
                                if (groupList == null) continue;

                                for (PlayerEntity entity : groupList) {
                                    float drawX = ((entity.x - offsetX)
                                            * 23.25f * parameters.比例
                                            * (23.25f * parameters.比例 / 视图比例))
                                            + centerX;
                                    float drawY = ((entity.y - offsetY)
                                            * 23.25f * parameters.比例
                                            * (23.25f * parameters.比例 / 视图比例))
                                            + centerY;
                                    float drawRadius = entity.radius 
                                            * 23.25f * parameters.比例
                                            * (23.25f * parameters.比例 / 视图比例);

                                    float dist = (float)Math.sqrt(
                                            Math.pow(downX - drawX, 2) 
                                            + Math.pow(downY - drawY, 2)
                                    );
                                    if (dist <= drawRadius) {
                                        cardSelectedRank = rankId;
                                        cardSelectedSubIndex = entity.subIndex;
                                        isSelectedCircleVisible = true;
                                        刷新延时=1;
                                        break outerLoop;
                                    }
                                }
                            }
                        } else {
                            刷新延时=8;
                            停止检测并恢复状态();
                        }
                    }

                    @Override
                    public void onButtonMove(int x, int y) {
                        float dx = x - downX;
                        float dy = y - downY;
                        float distance = (float) Math.sqrt(dx * dx + dy * dy);
                        if (distance > MOVE_THRESHOLD) {
                            isMoved = true;
                        }
                        卡点.setX(x);
                        卡点.setY(y);
                    }
                }
        );
        卡点.setVisibility(View.GONE);
    }

    // --- 同步按钮 ---
    if (同步 == null) {
        同步 = buttonCreator.createRoundButton(
                "同步",
                300,
                Color.BLUE,
                100, 200,
                new RoundButtonCreator.OnButtonTouchListener() {
                    @Override
                    public void onButtonDown() {
                        downX = 同步.getX();
                        downY = 同步.getY();
                        isMoved = false;
                    }

                    @Override
                    public void onButtonUp() {
                        if (isMoved) {
                            XposedBridge.log("按钮事件: 移动超过阈值，取消功能触发(指定排名按钮)");
                            isMoved = false;
                            RoundButtonCreator.isMovable = false;
                            return;
                        }
                        RoundButtonCreator.isMovable = false;

                        // 点击“同步”按钮后逻辑：
                        if (!isSyncDetecting) {
                            isSyncDetecting = true;
                            isSyncCircleVisible = true;
                            syncSelectedSubIndex = -1;
                            锁定同步球体初始体积 = -1;

                            // 选中目标组 => subIndex=0 (该组最大球)
                            if (rankToPlayers.containsKey(specifiedRankId)) {
                                List<PlayerEntity> syncGroup = rankToPlayers.get(specifiedRankId);
                                if (syncGroup != null && !syncGroup.isEmpty()) {
                                    syncSelectedSubIndex = 0;
                                } else {
                                    isSyncDetecting = false;
                                    isSyncCircleVisible = false;
                                }
                            } else {
                                isSyncDetecting = false;
                                isSyncCircleVisible = false;
                            }
                        } else {
                            停止检测并恢复状态();
                        }
                    }

                    @Override
                    public void onButtonMove(int x, int y) {
                        float dx = x - downX;
                        float dy = y - downY;
                        float distance = (float) Math.sqrt(dx*dx + dy*dy);
                        if (distance > MOVE_THRESHOLD) {
                            isMoved = true;
                        }
                        同步.setX(x);
                        同步.setY(y);
                    }
                }
        );
        同步.setVisibility(View.GONE);
    }

    // 根据开关显示/隐藏按钮
    if (showCardButton) {
        卡点.setVisibility(View.VISIBLE);
    } else {
        卡点.setVisibility(View.GONE);
    }
    if (showRankButton) {
        同步.setVisibility(View.VISIBLE);
    } else {
        同步.setVisibility(View.GONE);
    }
    }
private float getTextWidth(int rankId, float textSize) {
    Paint paint = new Paint();
    paint.setTextSize(textSize);
    return paint.measureText(String.valueOf(rankId));
}

    /************************************************
     * 7) 停止检测并恢复状态
     ************************************************/
    private void 停止检测并恢复状态() {
        isDetecting = false;
        cardSelectedRank = -1;
        cardSelectedSubIndex = -1;
        锁定球体初始体积 = -1;
        isSelectedCircleVisible = false;

        isSyncDetecting = false;
        isSyncCircleVisible = false;
        syncSelectedSubIndex = -1;
        锁定同步球体初始体积 = -1;
        刷新延时=8;

        RoundButtonCreator.isMovable = false;
    }

    /************************************************
     * 8) 卡点分裂功能(原逻辑)
     ************************************************/
    private void 触发一分为二功能(Context context) {
        if (isRunning) return;
        isRunning = true;

        new Thread(() -> {
            try {
                if (initialAngle == null) {
                    initialAngle = joystickAngle;
                }
                Thread.sleep(parameters.卡点前摇);

                // 角度滑动
                HOOK.TouchEventHandler.角度滑动(
                        simpleButtonController.摇杆x,
                        simpleButtonController.摇杆y,
                        initialAngle,
                        0,
                        200,
                        30,
                        触摸id, w1
                );
                // 分裂次数
                for (int i = 0; i < parameters.卡点分身次数; i++) {
                    HOOK.TouchEventHandler.按下(
                            simpleButtonController.分身x,
                            simpleButtonController.分身y,
                            点击id
                    );
                    HOOK.TouchEventHandler.抬起(
                            simpleButtonController.分身x,
                            simpleButtonController.分身y,
                            点击id
                    );
                    Thread.sleep(parameters.卡点分身间隔);
                }
                RoundButtonCreator.isMovable = false;
                isSelectedCircleVisible = false;
                XposedBridge.log("球体分裂: 卡点球体已一分为二！");
                new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                isRunning = false;
            }
        }).start();
    }

    /************************************************
     * 9) 「同步」分裂功能 
     *    - 分裂前后计算 角度1(旧球相对我方最大球), 角度2(新球相对我方最大球)
     ************************************************/
    /**************************************************************
 * 思路：
 * 1) 点击“同步”后：
 *    a) 首先获取「指定排名ID」下的最大球 与 我方“最大球”之间的角度 => angle1，
 *       然后执行角度滑动(angle1, 0)，再点击一次分身 => 我方球就向该方向分裂；
 *
 * 2) 分裂后，我方会产生“新分裂球”。现在再获取「指定排名ID」下最新的最大球，
 *    与“我方新分裂的球”之间的角度 => angle2，
 *    然后执行角度滑动(angle2, 0)，再连续点击多次分身(比如10次)，
 *    就会把我方球体朝此角度的方向分裂并“合”到目标。
 *
 * 3) 注意点：
 *    - 如何识别“我方新分裂球”？常见方式：比较分裂前后“地址/uniqueId”差异，
 *      取新的那颗(通常半径较小)即为“新分裂球”。
 *    - 如果游戏机制是“最大的那颗仍算自己本体，小的才是新分裂球”，
 *      也可以根据实际需求来确认由哪颗球去做“对准移动”。
 *    - 以下代码仅作示例；你需结合自己项目中对“内存解析”、“分裂后球体识别”等逻辑做适配。

 **************************************************************/
private void 触发排名一分为二(Context context) {
    if (isRunning) {
        return;
    }
    isRunning = true;

    new Thread(() -> {
        try {
            // ============ 1) 获取指定rankId最大球 & 我方最大球 ============
            List<PlayerEntity> friendGroup = rankToPlayers.get(specifiedRankId);
            if (friendGroup == null || friendGroup.isEmpty()) {
                XposedBridge.log("同步分裂: 指定Rank组为空, 无法获取目标球");
                return;
            }

            // 获取我方最大球
            PlayerEntity myLargestBallBeforeSplit = getMyLargestBall();
            if (myLargestBallBeforeSplit == null) {
                XposedBridge.log("同步分裂: 我方最大球不存在");
                return;
            }

            // 记录排名球体分裂前的最大球体（用于之后获取分裂后的最大球体）
            PlayerEntity targetMaxBeforeSplit = friendGroup.get(0);

            // ============ 2) 分裂后获取排名ID的新最大球体 ============
            rankToPlayers.clear();
            获取玩家结构体(context, "xx");  // 重新读取数据
            Thread.sleep(50);

            List<PlayerEntity> updatedFriendGroup = rankToPlayers.get(specifiedRankId);
            if (updatedFriendGroup == null || updatedFriendGroup.size() < 2) {
                XposedBridge.log("分裂后: 排名组未找到两个同样大小的球体");
                return;
            }

            // 找到排名分裂后新增的最大球体
            PlayerEntity newTargetMax = null;
            for (PlayerEntity entity : updatedFriendGroup) {
                if (entity.radius == targetMaxBeforeSplit.radius) {
                    // 如果体积和原本排名最大球相同，并且subIndex=1表示新分裂的球
                    if (entity.subIndex == 1) {
                        newTargetMax = entity;
                        break;
                    }
                }
            }

            if (newTargetMax == null) {
                XposedBridge.log("分裂后未找到新的最大球体");
                return;
            }

            // ============ 3) 计算角度: 我方最大球与排名新分裂最大球之间的角度 ============
            float dx = newTargetMax.x - myLargestBallBeforeSplit.x;  // 使用新分裂的排名球体
            float dy = newTargetMax.y - myLargestBallBeforeSplit.y;
            float angle = (float) Math.toDegrees(Math.atan2(dy, dx));
            if (angle < 0) angle += 360f;  // 保证角度是正值

            XposedBridge.log("【分裂后】排名分裂后的最大球体 vs 我方最大球体 angle = " + angle + "°");

            // ============ 4) 朝 angle 方向滑动，然后点击一次分身 ============
            angle = 360f - angle;  // 修正角度
            HOOK.TouchEventHandler.角度滑动(
                    simpleButtonController.摇杆x,
                    simpleButtonController.摇杆y,
                    angle,    // 朝目标角度滑动
                    0,          // 目标角度
                    parameters.同步滑动长度,               // 偏移长度=0 (直线滑动)
                    parameters.同步滑动时长,             // 滑动时长
                    触摸id, w1
            );

            // 连续点击10次分身
            for (int i = 0; i < parameters.同步分身次数; i++) {
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(1);
            }
            XposedBridge.log("已向 angle=" + angle + "° 方向分裂一次");

            final float angleFinal = angle;

            // ============ 5) 在主线程提示结果 ============
            new Handler(Looper.getMainLooper()).post(() -> {
                提示.短时提示(context,
                        "已完成分裂流程：\n" +
                        "angle(排名分裂后最大球体 vs 我方最大球) = " + angleFinal + "°"
                );
            });
            // 重置 initialAngle
            new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            isRunning = false;
        }
    }).start();
}

// 新增方法：获取排名ID的最大球体
private PlayerEntity getRankIdMaxBall(int rankId) {
    List<PlayerEntity> playerGroup = rankToPlayers.get(rankId);
    if (playerGroup == null || playerGroup.isEmpty()) {
        return null;
    }
    return playerGroup.get(0); // 获取该组最大球（通常是第一个）
}


                /*
private void 触发指定排名一分为二功能(Context context) {
    if (isRunning) {
        return;
    }
    isRunning = true;

    new Thread(() -> {
        try {
            // ============ 1) 获取指定rankId最大球 & 我方最大球 ============
            List<PlayerEntity> friendGroup = rankToPlayers.get(specifiedRankId);
            if (friendGroup == null || friendGroup.isEmpty()) {
                XposedBridge.log("同步分裂: 指定Rank组为空, 无法获取目标球");
                return;
            }
            // 取该组最大球(下标=0)
            PlayerEntity targetMax = friendGroup.get(0);

            // 获取我方最大球
            PlayerEntity myLargestBallBeforeSplit = getMyLargestBall();
            if (myLargestBallBeforeSplit == null) {
                XposedBridge.log("同步分裂: 我方最大球不存在");
                return;
            }

            // 计算 angle1 (分裂前，目标球 vs 我方最大球)
            float dx1 = targetMax.x - myLargestBallBeforeSplit.x;
            float dy1 = targetMax.y - myLargestBallBeforeSplit.y;
            float angle1 = (float) Math.toDegrees(Math.atan2(dy1, dx1));
            if (angle1 < 0) angle1 += 360f;
            XposedBridge.log("【分裂前】指定Rank最大球 vs 我方最大球 angle1 = " + angle1 + "°");

            // ============ 2) 朝 angle1 方向滑动，然后点一次分身 ============
            angle1 = 360f - angle1;  // 修正角度
            HOOK.TouchEventHandler.角度滑动(
                    simpleButtonController.摇杆x,
                    simpleButtonController.摇杆y,
                    angle1,    // 起始角度
                    0,          // 目标角度
                    150,               // 偏移长度=0(仅示例：表示直线朝此角度“短滑动”)
                    200,             // 滑动时长(可适当加大)
                    触摸id, w1
            );
            // 同步一下当前摇杆角度

            // 分裂一次
            HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
            HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);

            XposedBridge.log("已向 angle1=" + angle1 + "° 方向分裂一次");

            // 给点时间让分裂球出现
            Thread.sleep(50);

            // ============ 3) 分裂后重新获取我方最大球 ============
            rankToPlayers.clear();
            获取玩家结构体(context, "xx");  // 重新读结构
            Thread.sleep(50);

            // 重新获取我方最大球
            myLargestBallBeforeSplit = getMyLargestBall();  // 获取新的最大球
            if (myLargestBallBeforeSplit == null) {
                XposedBridge.log("分裂后: 我方最大球不存在");
                return;
            }

            // ============ 4) 重新获取排名ID的最大球体 ============
            PlayerEntity newTargetMax = getRankIdMaxBall(specifiedRankId);
            if (newTargetMax == null) {
                XposedBridge.log("同步分裂: 排名ID最大球体获取失败");
                return;
            }

            // ============ 5) 计算 angle2: 我方新最大球与排名ID最大球之间的角度 ============
            float dx2 = newTargetMax.x - myLargestBallBeforeSplit.x;  // 使用新分裂球
            float dy2 = newTargetMax.y - myLargestBallBeforeSplit.y;
            float angle2 = (float) Math.toDegrees(Math.atan2(dy2, dx2));
            if (angle2 < 0) angle2 += 360f;
            XposedBridge.log("【分裂后】指定Rank最大球 vs 我方新最大球 angle2 = " + angle2 + "°");

            // ============ 6) 朝 angle2 方向滑动，然后点击多次分身============
            angle2 = 360f - angle2;  // 修正角度
            HOOK.TouchEventHandler.角度滑动(
                    simpleButtonController.摇杆x,
                    simpleButtonController.摇杆y,
                    angle2,   // 当前角度(上一轮分裂后)
                    0,          // 目标角度
                    150,               // 偏移长度=0 => 直线朝该方向
                    50,             // 滑动时长(可调)
                    触摸id, w1
            );

            // 连续点击10次分身
            for (int i = 0; i < 10; i++) {
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(100);
            }

            XposedBridge.log("已向 angle2=" + angle2 + "° 方向连续分裂10次 => 把我方球体合给队友最新最大球");

            final float angle1Final = angle1;
            final float angle2Final = angle2;

            // ============ 7) 在主线程提示结果 ============
            new Handler(Looper.getMainLooper()).post(() -> {
                提示.短时提示(context,
                        "已完成两次分裂流程：\n" +
                        "angle1(队友旧Max vs 我方旧Max) = " + angle1Final + "°\n" +
                        "angle2(队友新Max vs 我方新球) = " + angle2Final + "°"
                );
            });
            // 重置 initialAngle
            new Handler(Looper.getMainLooper()).post(() -> initialAngle = null);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            isRunning = false;
        }
    }).start();
}

// 新增方法：获取排名ID的最大球体
private PlayerEntity getRankIdMaxBall(int rankId) {
    List<PlayerEntity> playerGroup = rankToPlayers.get(rankId);
    if (playerGroup == null || playerGroup.isEmpty()) {
        return null;
    }
    return playerGroup.get(0); // 获取该组最大球（通常是第一个）
}



    /************************************************
     * 10) 重新获取最大球(若有需要)
     ************************************************/
    private void reAcquireSyncBall(Context context) {
        if (!isSyncDetecting) return;
        if (rankToPlayers.containsKey(specifiedRankId)) {
            List<PlayerEntity> syncGroup = rankToPlayers.get(specifiedRankId);
            if (syncGroup != null && !syncGroup.isEmpty()) {
                syncSelectedSubIndex = 0;
                锁定同步球体初始体积 = -1;
            } else {
                停止检测并恢复状态();
            }
        } else {
            停止检测并恢复状态();
        }
    }

    /************************************************
     * 11) 获取我方最大球(示例)
     ************************************************/
    private PlayerEntity getMyLargestBall() {
        if (!rankToPlayers.containsKey(myRankId)) {
            return null;
        }
        List<PlayerEntity> myGroup = rankToPlayers.get(myRankId);
        if (myGroup == null || myGroup.isEmpty()) {
            return null;
        }
        return myGroup.get(0);
    }











    // 解除帧率限制操作
    private void unlockFrameRate(String packageName, Handler mainHandler) {
        new Thread(() -> {
            try {
                memory.setPackageName(packageName);
                long moduleAddress = memory.getModuleAddress("libunity.so");
                long targetAddress = moduleAddress + 0x179B990;
                memory.setValue("999", targetAddress, memory.TYPE_DWORD);

                //mainHandler.post(() -> showToast(null, "解除帧率限制 开启"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    

    // Page2 的布局创建方法
    private LinearLayout createPage2Layout(Context context) {
    LinearLayout page2Layout = new LinearLayout(context);
    page2Layout.setOrientation(VERTICAL);
    
    ScrollView scrollView = new ScrollView(context);
    scrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    LinearLayout switchContainer = new LinearLayout(context);
    switchContainer.setOrientation(VERTICAL);

    // 设置左右相同的边距
    LayoutParams switchContainerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    int margin = dpToPx(16);
    switchContainerParams.setMargins(margin, 0, margin, 0);
    switchContainer.setLayoutParams(switchContainerParams);

    // 自定义开关名称数组
    String[] switchNames = {"初始化训练营开启","切换合球方式" ,"吐球", "4分","冲球16分","分身","三角", "蛇手左",
                             "蛇手右", "四分测合左", "四分测合右","后仰左","后仰右" ,"旋转左","旋转右","卡点","同步","调试按钮位置"};

    // 动态创建开关并添加到容器中
    for (int i = 0; i < switchNames.length; i++) {
            final String switchName = switchNames[i];
            Switch switchButton = new Switch(context);
            switchButton.setText(switchName);
            switchButton.setTag(switchName); // 设置Tag
            switchButton.setTextColor(Color.parseColor("#B9A6FF"));
            applySwitchStyle(switchButton);

            // 开关状态监听
            switchButton.setOnCheckedChangeListener(
                    (CompoundButton buttonView, boolean isChecked) -> {
                        if (isChecked) {
                            showToast(context, switchName + " 打开");
                            switch (switchName) {
                                case "切换合球方式":
                                    w1 = false;

                                    break;
                                case "初始化训练营开启":
                                    // 开关A的开启功能

                                    // 主线程中的Handler
                                    Handler mainHandler = new Handler(Looper.getMainLooper());

                                    new Thread(
                                                    () -> {
                                                        try {
                                                            // 先执行搜索相关的内存操作
                                                            memory.clearResult();
                                                            memory.setPackageName("com.ztgame.bob");
                                                            memory.setRange(memory.RANGE_ANONYMOUS);
                                                            memory.RangeMemorySearch(
                                                                    "20000", memory.TYPE_FLOAT);
                                                            memory.MemoryOffset(
                                                                    "500", memory.TYPE_FLOAT, 12);
                                                            memory.MemoryOffset(
                                                                    "500", memory.TYPE_FLOAT, 24);

                                                            if (memory.getResultCount() > 0) {
                                                                long[] resultAddresses =
                                                                        memory.getResult(1);
                                                                a1 = resultAddresses[0];
                                                            }

                                                            memory.clearResult();
                                                            memory.setPackageName("com.ztgame.bob");
                                                            memory.setRange(memory.RANGE_ANONYMOUS);
                                                            memory.RangeMemorySearch(
                                                                    "2333", memory.TYPE_DWORD);
                                                            memory.MemoryOffset(
                                                                    "-1", memory.TYPE_DWORD, 12);
                                                            memory.MemoryOffset(
                                                                    "-1", memory.TYPE_DWORD, 36);
                                                            if (memory.getResultCount() > 0) {
                                                                // 获取所有结果地址
                                                                long[] resultAddresses =
                                                                        memory.getResult(
                                                                                memory
                                                                                        .getResultCount());

                                                                for (long address :
                                                                        resultAddresses) {
                                                                    // 使用 memory.readFloat 判断地址 +
                                                                    // 0x48 的值是否为 0
                                                                    float value =
                                                                            memory.readFloat(
                                                                                    address
                                                                                            + 提取变量(
                                                                                                    RemoteVariables
                                                                                                            .qq));

                                                                    if (value == 0.0f) {
                                                                        // 如果值为 0，则保存该地址并退出循环
                                                                        a5 = address;
                                                                        break;
                                                                    }
                                                                }
                                                            }

                                                            memory.clearResult();
                                                            memory.setPackageName("com.ztgame.bob");
                                                            memory.setRange(memory.RANGE_ANONYMOUS);
                                                            memory.RangeMemorySearch(
                                                                    "14.5", memory.TYPE_FLOAT);
                                                            memory.MemoryOffset(
                                                                    "800", memory.TYPE_FLOAT, -104);
                                                            if (memory.getResultCount() > 0) {
                                                                long[] resultAddresses =
                                                                        memory.getResult(1);
                                                                a3 = resultAddresses[0];
                                                            }
                                                            memory.clearResult();

                                                            memory.clearResult();
                                                            memory.setRange(memory.RANGE_ANONYMOUS);
                                                            memory.RangeMemorySearch(
                                                                    "0.58823525906",
                                                                    memory.TYPE_FLOAT);
                                                            memory.MemoryOffset(
                                                                    "1.70000004768",
                                                                    memory.TYPE_FLOAT,
                                                                    -24);
                                                            // memory.MemoryOffset("2.20405331e-39",
                                                            // memory.TYPE_FLOAT, 72);

                                                            if (memory.getResultCount() > 0) {
                                                                long[] resultAddresses =
                                                                        memory.getResult(1);
                                                                粘合地址 = resultAddresses[0];
                                                            }
                                                            获取玩家结构体(context, "玩家坐标数据.txt");

                                                            //   黑屏值(context);
                                                            //
                                                            // memory.setValue("850",a3-104,memory.TYPE_FLOAT);

                                                            long lastToastTime = 0;

                                                            while (true) {
                                                                joystickUp =
                                                                        memory.readFloat(
                                                                                a1 - 0x550);
                                                                joystickDown =
                                                                        memory.readFloat(
                                                                                a1 - 0x54c);

                                                                joystickAngle =
                                                                        (float)
                                                                                Math.toDegrees(
                                                                                        Math.atan2(
                                                                                                joystickDown,
                                                                                                joystickUp));
                                                                if (joystickAngle < 0) {
                                                                    joystickAngle += 360;
                                                                }

                                                                // 计算角度
                                                                long currentTime =
                                                                        System.currentTimeMillis();
                                                                if (currentTime - lastToastTime
                                                                        >= 1000) {
                                                                    lastToastTime = currentTime;

                                                                    mainHandler.post(
                                                                            () -> {
                                                                                joystickAngle =
                                                                                        joystickAngle;
                                                                            });
                                                                }

                                                                //
                                                                // 文件读写.写入(context,"1.txt",joystickAngle,false,false);

                                                                Thread.sleep(10);
                                                            }
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                    })
                                            .start();
                                    break;
                                case "吐球":
                                    if (吐球 == null) {
                                        // 在UI线程中创建按钮
                                        吐球 =
                                                buttonCreator.createRoundButton(
                                                        "吐",
                                                        300,
                                                        Color.GREEN,
                                                        200,
                                                        200,
                                                        new RoundButtonCreator
                                                                .OnButtonTouchListener() {

                                                            boolean 按钮按下 = false;
                                                            Thread 按钮线程 = null;

                                                            @Override
                                                            public void onButtonDown() {
                                                                int x = simpleButtonController.吐球x;
                                                                int y = simpleButtonController.吐球y;
                                                                int 触摸id = 4;
                                                                long 抬起延时 = 0;
                                                                long 一组延时 = 0;
                                                                long 按下延时 =
                                                                        (1000 / parameters.吐球1秒次数)
                                                                                - (抬起延时 + 一组延时);

                                                                if (按钮按下 || 按钮线程 != null) return;

                                                                按钮按下 = true;
                                                                按钮线程 =
                                                                        new Thread(
                                                                                () -> {
                                                                                    try {
                                                                                        for (int i =
                                                                                                        0;
                                                                                                按钮按下;
                                                                                                i++) {
                                                                                            if (Thread
                                                                                                    .interrupted()) {
                                                                                                break;
                                                                                            }

                                                                                            HOOK
                                                                                                    .TouchEventHandler
                                                                                                    .按下(
                                                                                                            x,
                                                                                                            y,
                                                                                                            触摸id);
                                                                                            Thread
                                                                                                    .sleep(
                                                                                                            按下延时);

                                                                                            if (Thread
                                                                                                    .interrupted()) {
                                                                                                break;
                                                                                            }

                                                                                            HOOK
                                                                                                    .TouchEventHandler
                                                                                                    .抬起(
                                                                                                            x,
                                                                                                            y,
                                                                                                            触摸id);
                                                                                            Thread
                                                                                                    .sleep(
                                                                                                            抬起延时);

                                                                                            if (Thread
                                                                                                    .interrupted()) {
                                                                                                break;
                                                                                            }

                                                                                            Thread
                                                                                                    .sleep(
                                                                                                            一组延时);
                                                                                        }
                                                                                    } catch (
                                                                                            InterruptedException
                                                                                                    e) {
                                                                                        e
                                                                                                .printStackTrace();
                                                                                    } finally {
                                                                                        // 确保抬起动作在任何情况下都会触发
                                                                                        HOOK
                                                                                                .TouchEventHandler
                                                                                                .抬起(
                                                                                                        x,
                                                                                                        y,
                                                                                                        触摸id);
                                                                                        按钮线程 = null;
                                                                                    }
                                                                                });
                                                                按钮线程.start();
                                                            }

                                                            @Override
                                                            public void onButtonUp() {
                                                                synchronized (this) {
                                                                    int x =
                                                                            simpleButtonController
                                                                                    .吐球x;
                                                                    int y =
                                                                            simpleButtonController
                                                                                    .吐球y;
                                                                    int 触摸id = 4;

                                                                    按钮按下 = false;

                                                                    // 停止线程
                                                                    if (按钮线程 != null) {
                                                                        按钮线程.interrupt();
                                                                        try {
                                                                            按钮线程.join(); // 等待线程完全停止
                                                                        } catch (
                                                                                InterruptedException
                                                                                        e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                        按钮线程 = null;
                                                                    }

                                                                    // 确保触发抬起操作
                                                                    HOOK.TouchEventHandler.抬起(
                                                                            x, y, 触摸id);
                                                                }
                                                            }
                                                        });
                                    } else {
                                        吐球.setVisibility(View.VISIBLE);
                                    }

                                    break;
                                case "三角":
                                    // 开关C的开启功能
                                    if (三角 == null) {
                                        三角 =
                                                buttonCreator.createRoundButton(
                                                        "三角",
                                                        300,
                                                        Color.GREEN,
                                                        300,
                                                        300,
                                                        new RoundButtonCreator
                                                                .OnButtonTouchListener() {
                                                            @Override
                                                            public void onButtonDown() {
                                                               三角();
                                                        
                                                        //提示.短时提示(context,joystickAngle+"");
                                                            }

                                                            @Override
                                                            public void onButtonUp() {}
                                                        });

                                    } else {
                                        三角.setVisibility(View.VISIBLE);
                                    }

                                    break;
                                case "4分":
                                    if (四分 == null) {
                                        四分 =
                                                buttonCreator.createRoundButton(
                                                        "四分",
                                                        300,
                                                        Color.GREEN,
                                                        300,
                                                        300,
                                                        new RoundButtonCreator
                                                                .OnButtonTouchListener() {

                                                            boolean 按钮按下 = false;
                                                            Thread 长按线程 = null;

                                                            @Override
                                                            public void onButtonDown() {
                                                                按钮按下 = true;

                                                                开始4分();

                                                                长按线程 =
                                                                        new Thread(
                                                                                () -> {
                                                                                    try {
                                                                                        Thread
                                                                                                .sleep(
                                                                                                        parameters
                                                                                                                .四分后延时);
                                                                                        if (按钮按下) {
                                                                                            开始冲球16分();
                                                                                        }
                                                                                    } catch (
                                                                                            InterruptedException
                                                                                                    e) {
                                                                                        e
                                                                                                .printStackTrace();
                                                                                    }
                                                                                });
                                                                长按线程.start();
                                                            }

                                                            @Override
                                                            public void onButtonUp() {
                                                                按钮按下 = false;

                                                                if (长按线程 != null) {
                                                                    长按线程.interrupt();
                                                                    try {
                                                                        长按线程.join();
                                                                    } catch (
                                                                            InterruptedException
                                                                                    e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    长按线程 = null;
                                                                }
                                                            }

                                                            private void 开始4分() {
                                                                new Thread(
                                                                                () -> {
                                                                                    try {
                                                                                        for (int i =
                                                                                                        0;
                                                                                                i
                                                                                                        < 2;
                                                                                                i++) {
                                                                                            int x =
                                                                                                    simpleButtonController
                                                                                                            .分身x;
                                                                                            int y =
                                                                                                    simpleButtonController
                                                                                                            .分身y;
                                                                                            int
                                                                                                    触摸id =
                                                                                                            5;

                                                                                            HOOK
                                                                                                    .TouchEventHandler
                                                                                                    .按下(
                                                                                                            x,
                                                                                                            y,
                                                                                                            触摸id);
                                                                                            HOOK
                                                                                                    .TouchEventHandler
                                                                                                    .抬起(
                                                                                                            x,
                                                                                                            y,
                                                                                                            触摸id);
                                                                                            Thread
                                                                                                    .sleep(
                                                                                                            parameters
                                                                                                                    .四分延时);
                                                                                        }
                                                                                    } catch (
                                                                                            InterruptedException
                                                                                                    e) {
                                                                                        e
                                                                                                .printStackTrace();
                                                                                    } finally {
                                                                                        HOOK
                                                                                                .TouchEventHandler
                                                                                                .抬起(
                                                                                                        simpleButtonController
                                                                                                                .分身x,
                                                                                                        simpleButtonController
                                                                                                                .分身y,
                                                                                                        5);
                                                                                    }
                                                                                })
                                                                        .start();
                                                            }

                                                            private void 开始冲球16分() {
                                                                new Thread(
                                                                                () -> {
                                                                                    try {
                                                                                        while (按钮按下) {
                                                                                            int x =
                                                                                                    simpleButtonController
                                                                                                            .分身x;
                                                                                            int y =
                                                                                                    simpleButtonController
                                                                                                            .分身y;
                                                                                            int
                                                                                                    触摸id =
                                                                                                            5;

                                                                                            HOOK
                                                                                                    .TouchEventHandler
                                                                                                    .按下(
                                                                                                            x,
                                                                                                            y,
                                                                                                            触摸id);
                                                                                            HOOK
                                                                                                    .TouchEventHandler
                                                                                                    .抬起(
                                                                                                            x,
                                                                                                            y,
                                                                                                            触摸id);
                                                                                            Thread
                                                                                                    .sleep(
                                                                                                            parameters
                                                                                                                    .四分后分身间隔);
                                                                                        }
                                                                                    } catch (
                                                                                            InterruptedException
                                                                                                    e) {
                                                                                        e
                                                                                                .printStackTrace();
                                                                                    } finally {
                                                                                        HOOK
                                                                                                .TouchEventHandler
                                                                                                .抬起(
                                                                                                        simpleButtonController
                                                                                                                .分身x,
                                                                                                        simpleButtonController
                                                                                                                .分身y,
                                                                                                        5);
                                                                                    }
                                                                                })
                                                                        .start();
                                                            }
                                                        });

                                    } else {
                                        四分.setVisibility(View.VISIBLE);
                                    }

                                    break;

                                case "冲球16分":
                                    // 开关C的开启功能
                                    if (冲球16分 == null) {
                                        冲球16分 =
                                                buttonCreator.createRoundButton(
                                                        "冲球",
                                                        300,
                                                        Color.GREEN,
                                                        300,
                                                        300,
                                                        new RoundButtonCreator
                                                                .OnButtonTouchListener() {

                                                            @Override
                                                            public void onButtonDown() {
                                                                Thread 按钮线程 =
                                                                        new Thread(
                                                                                () -> {
                                                                                    try {
                                                                                        for (int i =
                                                                                                        0;
                                                                                                i
                                                                                                        < parameters
                                                                                                                .冲球指定次数;
                                                                                                i++) {
                                                                                            int x =
                                                                                                    simpleButtonController
                                                                                                            .分身x;
                                                                                            int y =
                                                                                                    simpleButtonController
                                                                                                            .分身y;
                                                                                            int
                                                                                                    触摸id =
                                                                                                            6;

                                                                                            // 触发按下动作
                                                                                            HOOK
                                                                                                    .TouchEventHandler
                                                                                                    .按下(
                                                                                                            x,
                                                                                                            y,
                                                                                                            触摸id);
                                                                                            Thread
                                                                                                    .sleep(
                                                                                                            parameters
                                                                                                                    .冲球分身间隔);

                                                                                            // 触发抬起动作
                                                                                            HOOK
                                                                                                    .TouchEventHandler
                                                                                                    .抬起(
                                                                                                            x,
                                                                                                            y,
                                                                                                            触摸id);
                                                                                            Thread
                                                                                                    .sleep(
                                                                                                            parameters
                                                                                                                    .冲球分身间隔);
                                                                                        }
                                                                                    } catch (
                                                                                            InterruptedException
                                                                                                    e) {
                                                                                        e
                                                                                                .printStackTrace();
                                                                                    } finally {
                                                                                        // 确保抬起动作在所有情况下都能触发
                                                                                        HOOK
                                                                                                .TouchEventHandler
                                                                                                .抬起(
                                                                                                        simpleButtonController
                                                                                                                .分身x,
                                                                                                        simpleButtonController
                                                                                                                .分身x,
                                                                                                        6);
                                                                                    }
                                                                                });

                                                                按钮线程.start();
                                                            }

                                                            @Override
                                                            public void onButtonUp() {
                                                                // 不需要实现
                                                            }
                                                        });

                                    } else {
                                        冲球16分.setVisibility(View.VISIBLE);
                                    }

                                    break;

                                case "分身":
                                    // 开关C的开启功能
                                    if (分身 == null) {
                                        分身 =
                                                buttonCreator.createRoundButton(
                                                        "分",
                                                        300,
                                                        Color.GREEN,
                                                        300,
                                                        300,
                                                        new RoundButtonCreator
                                                                .OnButtonTouchListener() {

                                                            boolean 按钮按下 = false;
                                                            Thread 按钮线程 = null;
                                                            long 按下时间 = 0;

                                                            @Override
                                                            public void onButtonDown() {
                                                                按钮按下 = true;
                                                                按下时间 = System.currentTimeMillis();

                                                                // 启动线程检测是否为长按
                                                                按钮线程 =
                                                                        new Thread(
                                                                                () -> {
                                                                                    try {
                                                                                        Thread
                                                                                                .sleep(
                                                                                                        parameters
                                                                                                                .长按分身延时); // 等待0.2秒
                                                                                        if (按钮按下) {
                                                                                            // 如果仍然按下，进入连续分身
                                                                                            trigger连击();
                                                                                        }
                                                                                    } catch (
                                                                                            InterruptedException
                                                                                                    e) {
                                                                                        e
                                                                                                .printStackTrace();
                                                                                    }
                                                                                });
                                                                按钮线程.start();
                                                            }

                                                            @Override
                                                            public void onButtonUp() {
                                                                按钮按下 = false;

                                                                long 松开时间 =
                                                                        System.currentTimeMillis();
                                                                long 持续时间 = 松开时间 - 按下时间;

                                                                // 如果按下时间不足0.2秒，触发单次分身
                                                                if (持续时间 < 100) {
                                                                    trigger单次分身();
                                                                }

                                                                // 停止线程
                                                                if (按钮线程 != null) {
                                                                    按钮线程.interrupt();
                                                                    try {
                                                                        按钮线程.join();
                                                                    } catch (
                                                                            InterruptedException
                                                                                    e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    按钮线程 = null;
                                                                }
                                                            }

                                                            private void trigger单次分身() {
                                                                new Thread(
                                                                                () -> {
                                                                                    try {
                                                                                        int x =
                                                                                                simpleButtonController
                                                                                                        .分身x;
                                                                                        int y =
                                                                                                simpleButtonController
                                                                                                        .分身y;
                                                                                        int 触摸id =
                                                                                                7;

                                                                                        // 单次分身
                                                                                        HOOK
                                                                                                .TouchEventHandler
                                                                                                .按下(
                                                                                                        x,
                                                                                                        y,
                                                                                                        触摸id);
                                                                                        Thread
                                                                                                .sleep(
                                                                                                        0);
                                                                                        HOOK
                                                                                                .TouchEventHandler
                                                                                                .抬起(
                                                                                                        x,
                                                                                                        y,
                                                                                                        触摸id);
                                                                                    } catch (
                                                                                            InterruptedException
                                                                                                    e) {
                                                                                        e
                                                                                                .printStackTrace();
                                                                                    } finally {
                                                                                        HOOK
                                                                                                .TouchEventHandler
                                                                                                .抬起(
                                                                                                        simpleButtonController
                                                                                                                .分身x,
                                                                                                        simpleButtonController
                                                                                                                .分身y,
                                                                                                        7);
                                                                                    }
                                                                                })
                                                                        .start();
                                                            }

                                                            private void trigger连击() {
                                                                new Thread(
                                                                                () -> {
                                                                                    try {
                                                                                        while (按钮按下) {
                                                                                            int x =
                                                                                                    simpleButtonController
                                                                                                            .分身x;
                                                                                            int y =
                                                                                                    simpleButtonController
                                                                                                            .分身y;
                                                                                            int
                                                                                                    触摸id =
                                                                                                            7;

                                                                                            // 连续分身
                                                                                            HOOK
                                                                                                    .TouchEventHandler
                                                                                                    .按下(
                                                                                                            x,
                                                                                                            y,
                                                                                                            触摸id);
                                                                                            HOOK
                                                                                                    .TouchEventHandler
                                                                                                    .抬起(
                                                                                                            x,
                                                                                                            y,
                                                                                                            触摸id);
                                                                                            Thread
                                                                                                    .sleep(
                                                                                                            parameters
                                                                                                                    .长按分身间隔);
                                                                                        }
                                                                                    } catch (
                                                                                            InterruptedException
                                                                                                    e) {
                                                                                        e
                                                                                                .printStackTrace();
                                                                                    } finally {
                                                                                        HOOK
                                                                                                .TouchEventHandler
                                                                                                .抬起(
                                                                                                        simpleButtonController
                                                                                                                .分身x,
                                                                                                        simpleButtonController
                                                                                                                .分身y,
                                                                                                        7);
                                                                                    }
                                                                                })
                                                                        .start();
                                                            }
                                                        });

                                    } else {
                                        分身.setVisibility(View.VISIBLE);
                                    }
                                    break;

                                case "蛇手左":
                                    // 开关D的开启功能
                                    if (蛇手左 == null) {
                                        蛇手左 =
                                                buttonCreator.createRoundButton(
                                                        "蛇手左",
                                                        300,
                                                        Color.GREEN,
                                                        300,
                                                        300,
                                                        new RoundButtonCreator
                                                                .OnButtonTouchListener() {

                                                            @Override
                                                            public void onButtonDown() {
                                                               蛇手左();
                                                        
                       
                                                            }

                                                            @Override
                                                            public void onButtonUp() {}
                                                        });

                                    } else {
                                        蛇手左.setVisibility(View.VISIBLE);
                                    }

                                    break;
                                case "蛇手右":
                                    // 开关E的开启功能
                                    if (蛇手右 == null) {
                                        蛇手右 =
                                                buttonCreator.createRoundButton(
                                                        "蛇手右",
                                                        300,
                                                        Color.GREEN,
                                                        300,
                                                        300,
                                                        new RoundButtonCreator
                                                                .OnButtonTouchListener() {

                                                            @Override
                                                            public void onButtonDown() {
                                                                蛇手右();
                                                            }

                                                            @Override
                                                            public void onButtonUp() {
                                                                // 按钮释放后的操作
                                                            }
                                                        });

                                    } else {
                                        蛇手右.setVisibility(View.VISIBLE);
                                    }

                                    break;
                                case "四分测合左":
                                    // 开关F的开启功能
                                    if (四分测合左 == null) {
                                        四分测合左 =
                                                buttonCreator.createRoundButton(
                                                        "四分测合左",
                                                        300,
                                                        Color.GREEN,
                                                        200,
                                                        200,
                                                        new RoundButtonCreator
                                                                .OnButtonTouchListener() {

                                                            @Override
                                                            public void onButtonDown() {
                                                                四分测合左();
                                                            }

                                                            @Override
                                                            public void onButtonUp() {}
                                                        });

                                    } else {
                                        四分测合左.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                case "四分测合右":
                                    // 开关G的开启功能
                                    if (四分测合右 == null) {
                                        四分测合右 =
                                                buttonCreator.createRoundButton(
                                                        "四分测合右",
                                                        300,
                                                        Color.GREEN,
                                                        200,
                                                        200,
                                                        new RoundButtonCreator
                                                                .OnButtonTouchListener() {

                                                            @Override
                                                            public void onButtonDown() {
                                                                四分测合右();
                                                            }

                                                            @Override
                                                            public void onButtonUp() {}
                                                        });

                                    } else {
                                        四分测合右.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                case "后仰左":
                                    // 开关G的开启功能
                                    if (后仰左 == null) {
                                        后仰左 =
                                                buttonCreator.createRoundButton(
                                                        "后仰左",
                                                        300,
                                                        Color.GREEN,
                                                        200,
                                                        200,
                                                        new RoundButtonCreator
                                                                .OnButtonTouchListener() {

                                                            @Override
                                                            public void onButtonDown() {
                                                                后仰左();
                                                            }

                                                            @Override
                                                            public void onButtonUp() {}
                                                        });

                                    } else {
                                        后仰左.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                case "后仰右":
                                    // 开关G的开启功能
                                    if (后仰右 == null) {
                                        后仰右 =
                                                buttonCreator.createRoundButton(
                                                        "后仰右",
                                                        300,
                                                        Color.GREEN,
                                                        200,
                                                        200,
                                                        new RoundButtonCreator
                                                                .OnButtonTouchListener() {

                                                            @Override
                                                            public void onButtonDown() {
                                                                后仰右();
                                                            }

                                                            @Override
                                                            public void onButtonUp() {}
                                                        });

                                    } else {
                                        后仰右.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                case "旋转左":   
    // 开关G的开启功能   
    if (旋转左 == null) {   
        旋转左 = buttonCreator.createRoundButton(
            "旋转左", 
            300, 
            Color.GREEN, 
            200, 
            200, 
            new RoundButtonCreator.OnButtonTouchListener() {
                private float startX; // 按下时X坐标   
                private float startY; // 按下时Y坐标   
                private float endX;   // 抬起时X坐标   
                private float endY;   // 抬起时Y坐标   
                private boolean isMoved; // 标记是否移动过    

                @Override
                public void onButtonDown() {   
                    // 重置状态   
                    RoundButtonCreator.isMovable = true;    
                    isMoved = false; 

                    // 记录按钮初始中心位置   
                    FrameLayout.LayoutParams layoutParams = 
                        (FrameLayout.LayoutParams) 旋转左.getLayoutParams();
                    startX = layoutParams.leftMargin + 旋转左.getWidth() / 2f; 
                    startY = layoutParams.topMargin + 旋转左.getHeight() / 2f;   
                }

                @Override
                public void onButtonMove(int x, int y) { 
                    isMoved = true; 
                    endX = x + 旋转左.getWidth() / 2f; 
                    endY = y + 旋转左.getHeight() / 2f; 
                }

                @Override
                public void onButtonUp() { 
                    RoundButtonCreator.isMovable = false; 

                    if (!isMoved) { 
                        // 如果未移动，保持endX和endY与startX和startY一致   
                        endX = startX; 
                        endY = startY; 
                    }

                    // 计算滑动距离与角度   
                    float dx = endX - startX; 
                    float dy = endY - startY; 
                    double distance = Math.sqrt(dx * dx + dy * dy);  
                    double angle = Math.toDegrees(Math.atan2(dy, dx));  

                    // 将角度转换到0-360范围   
                    if (angle < 0) { 
                        angle += 360; 
                    }

                    // 根据滑动距离判断操作   
                    if (distance > 50) { 
                        提示.短时提示(context, 
                            "滑动距离: " + distance + "，超过执行滑动，滑动角度: " + angle);
                    } else { 
                        提示.短时提示(context, 
                            "滑动距离: " + distance + "，不足执行点击");
                    }

                    // 判断滑动角度相对于joystickAngle的位置   
                    提示.短时提示(context, "当前角度: " + joystickAngle + "。");
                    boolean isRight;
                    if (Math.abs(angle - joystickAngle) <= 180) { 
                        isRight = angle > joystickAngle; 
                    } else { 
                        isRight = angle < joystickAngle; 
                    }

                    if (isRight) { 
                        四分测合左(); 
                        提示.短时提示(context, "滑动角度在 joystickAngle 的右边");
                    } else { 
                        四分测合右(); 
                        提示.短时提示(context, "滑动角度在 joystickAngle 的左边");
                    }

                    // 回到初始位置   
                    FrameLayout.LayoutParams layoutParams = 
                        (FrameLayout.LayoutParams) 旋转左.getLayoutParams();
                    layoutParams.leftMargin = (int) (startX - 旋转左.getWidth() / 2f); 
                    layoutParams.topMargin = (int) (startY - 旋转左.getHeight() / 2f); 
                    旋转左.setLayoutParams(layoutParams);   
                }
            }
        );
    } else {   
        旋转左.setVisibility(View.VISIBLE);   
    }   
    break;

                                case "卡点":
                                    showCardButton = true;
                                    if (卡点 != null) {
                                        卡点.setVisibility(View.VISIBLE);
                                    }
                                    卡点.setX(500);
                                    卡点.setY(500);

                                    // 是否显示“指定排名”按钮

                                    break;

                                case "同步":
                                    showRankButton = true;
                                    if (同步 != null) {
                                        同步.setVisibility(View.VISIBLE);
                                    }

                                    break;

                                case "旋转右":
                                    // 开关G的开启功能
                                    if (旋转右 == null) {
                                        旋转右 =
                                                buttonCreator.createRoundButton(
                                                        "旋转右",
                                                        300,
                                                        Color.GREEN,
                                                        200,
                                                        200,
                                                        new RoundButtonCreator
                                                                .OnButtonTouchListener() {

                                                            @Override
                                                            public void onButtonDown() {
                                                                旋转右();
                                                            }

                                                            @Override
                                                            public void onButtonUp() {}
                                                        });

                                    } else {
                                        旋转右.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                case "调试按钮位置":
                                    // 开关H的开启功能
                                    RoundButtonCreator.setDebugMode(true);
                                    simpleButtonController.updateButtonVisibility(true);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            showToast(context, switchName + " 关闭");
                            switch (switchName) {
                                case "切换合球方式":
                                    // 开关A的关闭功能
                                    w1 = true;

                                    break;
                                case "初始化训练营开启":
                                    // 开关A的关闭功能

                                    break;
                                case "吐球":
                                    // 开关B的关闭功能
                                    if (吐球 != null) {
                                        吐球.setVisibility(View.GONE);
                                    }
                                    break;
                                case "4分":
                                    // 开关B的关闭功能
                                    if (四分 != null) {
                                        四分.setVisibility(View.GONE);
                                    }
                                    break;
                                case "冲球16分":
                                    // 开关B的关闭功能
                                    if (冲球16分 != null) {
                                        冲球16分.setVisibility(View.GONE);
                                    }
                                    break;
                                case "分身":
                                    // 开关B的关闭功能
                                    if (分身 != null) {
                                        分身.setVisibility(View.GONE);
                                    }
                                    break;
                                case "三角":
                                    // 开关C的关闭功能
                                    if (三角 != null) {
                                        三角.setVisibility(View.GONE);
                                    }
                                    break;
                                case "蛇手左":
                                    // 开关D的关闭功能
                                    if (蛇手左 != null) {
                                        蛇手左.setVisibility(View.GONE);
                                    }
                                    break;
                                case "蛇手右":
                                    // 开关E的关闭功能
                                    if (蛇手右 != null) {
                                        蛇手右.setVisibility(View.GONE);
                                    }
                                    break;
                                case "四分测合左":
                                    // 开关F的关闭功能
                                    if (四分测合左 != null) {
                                        四分测合左.setVisibility(View.GONE);
                                    }
                                    break;
                                case "四分测合右":
                                    // 开关G的关闭功能
                                    if (四分测合右 != null) {
                                        四分测合右.setVisibility(View.GONE);
                                    }
                                    break;
                                case "后仰左":
                                    // 开关G的关闭功能
                                    if (后仰左 != null) {
                                        后仰左.setVisibility(View.GONE);
                                    }
                                    break;
                                case "后仰右":
                                    // 开关G的关闭功能
                                    if (后仰右 != null) {
                                        后仰右.setVisibility(View.GONE);
                                    }
                                    break;
                                case "旋转左":
                                    // 开关G的关闭功能
                                    if (旋转左 != null) {
                                        旋转左.setVisibility(View.GONE);
                                    }
                                    break;
                                case "旋转右":
                                    // 开关G的关闭功能
                                    if (旋转右 != null) {
                                        旋转右.setVisibility(View.GONE);
                                    }
                                    break;
                                case "卡点":
                                    showCardButton = true;
                                    if (卡点 != null) {
                                        卡点.setVisibility(View.GONE);
                                    }
                                    break;

                                case "同步":
                                    showRankButton = true;
                                    if (同步 != null) {
                                        同步.setVisibility(View.GONE);
                                    }
                                    break;
                                case "调试按钮位置":
                                    // 开关H的关闭功能
                                    RoundButtonCreator.setDebugMode(false);
                                    simpleButtonController.updateButtonVisibility(false);
                                    break;
                                default:
                                    break;
                            }
                        }
                    });

        switchContainer.addView(switchButton);
    }

    scrollView.addView(switchContainer);
    page2Layout.addView(scrollView);

    return page2Layout;
}



    private LinearLayout createPage3Layout(Context context) {
    // 分页3
    LinearLayout page3Layout = new LinearLayout(context);
    page3Layout.setOrientation(VERTICAL);

    ScrollView scrollView = new ScrollView(context);
    scrollView.setLayoutParams(new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
    ));
    LinearLayout.LayoutParams spacingParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(8)); // 8dp间隔

    GradientDrawable scrollBackground = new GradientDrawable();
    scrollBackground.setColor(Color.parseColor("#2D2A4A"));
    scrollBackground.setCornerRadius(16); // 圆角背景
    scrollView.setBackground(scrollBackground);

    LinearLayout mainLayout = new LinearLayout(context);
    mainLayout.setOrientation(VERTICAL);
    mainLayout.setLayoutParams(new LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    ));
    int sidePadding = dpToPx(16); // 设置左右边距
    mainLayout.setPadding(sidePadding, 0, sidePadding, 0);

    // 吐球分身设置
    mainLayout.addView(createCollapsibleSection(context, "分身吐球卡点设置", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);
                    
    addSeekBarWithEditText(context, layout, "分辨率x", parameters.分辨率x, 0, 4000, value -> parameters.分辨率x = value);
    addSeekBarWithEditText(context, layout, "分辨率y", parameters.分辨率y, 0, 2000, value -> parameters.分辨率y = value);
   addFloatSeekBarWithEditText(context, layout, "比例", parameters.比例, 0, 2, value -> parameters.比例 = value);
    addSeekBarWithEditText(context, layout, "吐球1秒次数", parameters.吐球1秒次数, 00, 1000, value -> parameters.吐球1秒次数 = value);
    addSeekBarWithEditText(context, layout, "四分延时", parameters.四分延时, 0, 100, value -> parameters.四分延时 = value);
    addSeekBarWithEditText(context, layout, "四分后延时", parameters.四分后延时, 0, 1000, value -> parameters.四分后延时 = value);
    addSeekBarWithEditText(context, layout, "四分后分身间隔", parameters.四分后分身间隔, 0, 100, value -> parameters.四分后分身间隔 = value);
    addSeekBarWithEditText(context, layout, "冲球指定次数", parameters.冲球指定次数, 0, 100, value -> parameters.冲球指定次数 = value);
    addSeekBarWithEditText(context, layout, "冲球分身间隔", parameters.冲球分身间隔, 0, 100, value -> parameters.冲球分身间隔 = value);

    addSeekBarWithEditText(context, layout, "长按分身延时", parameters.长按分身延时, 0, 1000, value -> parameters.长按分身延时 = value);
    addSeekBarWithEditText(context, layout, "长按分身间隔", parameters.长按分身间隔, 0, 100, value -> parameters.长按分身间隔 = value);
                    
  //  addSeekBarWithEditText(context, layout, "卡点过滤", parameters.卡点过滤, 0, 10, value -> parameters.卡点过滤 = value);
    addSeekBarWithEditText(context, layout, "卡点分身前摇", parameters.卡点前摇, 0, 1000, value -> parameters.卡点前摇 = value);
    addSeekBarWithEditText(context, layout, "卡点分身次数", parameters.卡点分身次数, 0, 100, value -> parameters.卡点分身次数 = value);
    addSeekBarWithEditText(context, layout, "卡点分身间隔", parameters.卡点分身间隔, 0, 100, value -> parameters.卡点分身间隔 = value);
    addSeekBarWithEditText(context, layout, "同步分身前摇", parameters.同步前摇, 0, 1000, value -> parameters.同步前摇 = value);
    addSeekBarWithEditText(context, layout, "同步滑动长度", parameters.同步滑动长度, 0, 100, value -> parameters.同步滑动长度 = value);
    addSeekBarWithEditText(context, layout, "同步滑动时长", parameters.同步滑动时长, 0, 100, value -> parameters.同步滑动时长 = value);
    addSeekBarWithEditText(context, layout, "同步分身次数", parameters.同步分身次数, 0, 100, value -> parameters.同步分身次数 = value);
    addSeekBarWithEditText(context, layout, "同步分身间隔", parameters.同步分身间隔, 0, 100, value -> parameters.同步分身间隔 = value);





    return layout;
}));

mainLayout.addView(new View(context), spacingParams);

// 三角参数
mainLayout.addView(createCollapsibleSection(context, "三角参数", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);

    addSeekBarWithEditText(context, layout, "三角1滑动角度", (int) parameters.三角1滑动角度, -180, 180, value -> parameters.三角1滑动角度 = value);
    addSeekBarWithEditText(context, layout, "三角1滑动长度", parameters.三角1滑动长度, 0, 1000, value -> parameters.三角1滑动长度 = value);
    addSeekBarWithEditText(context, layout, "三角1滑动时长", parameters.三角1滑动时长, 0, 500, value -> parameters.三角1滑动时长 = value);
    addSeekBarWithEditText(context, layout, "三角1点击延时", parameters.三角1点击延时, 0, 500, value -> parameters.三角1点击延时 = value);
    addSeekBarWithEditText(context, layout, "三角2滑动延时", parameters.三角2滑动延时, 0, 500, value -> parameters.三角2滑动延时 = value);

    addSeekBarWithEditText(context, layout, "三角2滑动角度", (int) parameters.三角2滑动角度, -180, 180, value -> parameters.三角2滑动角度 = value);
    addSeekBarWithEditText(context, layout, "三角2滑动长度", parameters.三角2滑动长度, 0, 1000, value -> parameters.三角2滑动长度 = value);
    addSeekBarWithEditText(context, layout, "三角2滑动时长", parameters.三角2滑动时长, 0, 500, value -> parameters.三角2滑动时长 = value);
    addSeekBarWithEditText(context, layout, "三角2点击延时", parameters.三角2点击延时, 0, 500, value -> parameters.三角2点击延时 = value);
    addSeekBarWithEditText(context, layout, "三角3滑动延时", parameters.三角3滑动延时, 0, 500, value -> parameters.三角3滑动延时 = value);

    addSeekBarWithEditText(context, layout, "三角3滑动角度", (int) parameters.三角3滑动角度, -180, 180, value -> parameters.三角3滑动角度 = value);
    addSeekBarWithEditText(context, layout, "三角3滑动长度", parameters.三角3滑动长度, 0, 1000, value -> parameters.三角3滑动长度 = value);
    addSeekBarWithEditText(context, layout, "三角3滑动时长", parameters.三角3滑动时长, 0, 500, value -> parameters.三角3滑动时长 = value);

    addSeekBarWithEditText(context, layout, "三角分身次数", parameters.三角分身次数, 0, 100, value -> parameters.三角分身次数 = value);
    addSeekBarWithEditText(context, layout, "三角分身延时", parameters.三角分身延时, 0, 500, value -> parameters.三角分身延时 = value);

    return layout;
}));

mainLayout.addView(new View(context), spacingParams);

// 蛇手左参数
mainLayout.addView(createCollapsibleSection(context, "蛇手左", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);

    addSeekBarWithEditText(context, layout, "蛇手左滑动1角度", (int) parameters.蛇手左滑动1角度, -180, 180, value -> parameters.蛇手左滑动1角度 = value);
    addSeekBarWithEditText(context, layout, "蛇手左滑动1长度", parameters.蛇手左滑动1长度, 0, 1000, value -> parameters.蛇手左滑动1长度 = value);
    addSeekBarWithEditText(context, layout, "蛇手左滑动1时长", parameters.蛇手左滑动1时长, 0, 500, value -> parameters.蛇手左滑动1时长 = value);
    addSeekBarWithEditText(context, layout, "蛇手左分身延时1", parameters.蛇手左分身延时1, 0, 500, value -> parameters.蛇手左分身延时1 = value);
    addSeekBarWithEditText(context, layout, "蛇手左滑动延时2", parameters.蛇手左滑动延时2, 0, 500, value -> parameters.蛇手左滑动延时2 = value);

    addSeekBarWithEditText(context, layout, "蛇手左滑动2角度", (int) parameters.蛇手左滑动2角度, -180, 180, value -> parameters.蛇手左滑动2角度 = value);
    addSeekBarWithEditText(context, layout, "蛇手左滑动2长度", parameters.蛇手左滑动2长度, 0, 1000, value -> parameters.蛇手左滑动2长度 = value);
    addSeekBarWithEditText(context, layout, "蛇手左滑动2时长", parameters.蛇手左滑动2时长, 0, 500, value -> parameters.蛇手左滑动2时长 = value);
    addSeekBarWithEditText(context, layout, "蛇手左分身延时2", parameters.蛇手左分身延时2, 0, 500, value -> parameters.蛇手左分身延时2 = value);
    addSeekBarWithEditText(context, layout, "蛇手左滑动延时3", parameters.蛇手左滑动延时3, 0, 500, value -> parameters.蛇手左滑动延时3 = value);

    addSeekBarWithEditText(context, layout, "蛇手左滑动3角度", (int) parameters.蛇手左滑动3角度, -180, 180, value -> parameters.蛇手左滑动3角度 = value);
    addSeekBarWithEditText(context, layout, "蛇手左滑动3长度", parameters.蛇手左滑动3长度, 0, 1000, value -> parameters.蛇手左滑动3长度 = value);
    addSeekBarWithEditText(context, layout, "蛇手左滑动3时长", parameters.蛇手左滑动3时长, 0, 500, value -> parameters.蛇手左滑动3时长 = value);

    addSeekBarWithEditText(context, layout, "蛇手左分身点击次数", parameters.蛇手左分身点击次数, 0, 100, value -> parameters.蛇手左分身点击次数 = value);
    addSeekBarWithEditText(context, layout, "蛇手左分身点击延时", parameters.蛇手左分身点击延时, 0, 500, value -> parameters.蛇手左分身点击延时 = value);

    return layout;
}));

mainLayout.addView(new View(context), spacingParams);

// 蛇手右参数
mainLayout.addView(createCollapsibleSection(context, "蛇手右", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);

    addSeekBarWithEditText(context, layout, "蛇手右滑动1角度", (int) parameters.蛇手右滑动1角度, -180, 180, value -> parameters.蛇手右滑动1角度 = value);
    addSeekBarWithEditText(context, layout, "蛇手右滑动1长度", parameters.蛇手右滑动1长度, 0, 1000, value -> parameters.蛇手右滑动1长度 = value);
    addSeekBarWithEditText(context, layout, "蛇手右滑动1时长", parameters.蛇手右滑动1时长, 0, 500, value -> parameters.蛇手右滑动1时长 = value);
    addSeekBarWithEditText(context, layout, "蛇手右分身延时1", parameters.蛇手右分身延时1, 0, 500, value -> parameters.蛇手右分身延时1 = value);
    addSeekBarWithEditText(context, layout, "蛇手右滑动延时2", parameters.蛇手右滑动延时2, 0, 500, value -> parameters.蛇手右滑动延时2 = value);

    addSeekBarWithEditText(context, layout, "蛇手右滑动2角度", (int) parameters.蛇手右滑动2角度, -180, 180, value -> parameters.蛇手右滑动2角度 = value);
    addSeekBarWithEditText(context, layout, "蛇手右滑动2长度", parameters.蛇手右滑动2长度, 0, 1000, value -> parameters.蛇手右滑动2长度 = value);
    addSeekBarWithEditText(context, layout, "蛇手右滑动2时长", parameters.蛇手右滑动2时长, 0, 500, value -> parameters.蛇手右滑动2时长 = value);
    addSeekBarWithEditText(context, layout, "蛇手右分身延时2", parameters.蛇手右分身延时2, 0, 500, value -> parameters.蛇手右分身延时2 = value);
    addSeekBarWithEditText(context, layout, "蛇手右滑动延时3", parameters.蛇手右滑动延时3, 0, 500, value -> parameters.蛇手右滑动延时3 = value);

    addSeekBarWithEditText(context, layout, "蛇手右滑动3角度", (int) parameters.蛇手右滑动3角度, -180, 180, value -> parameters.蛇手右滑动3角度 = value);
    addSeekBarWithEditText(context, layout, "蛇手右滑动3长度", parameters.蛇手右滑动3长度, 0, 1000, value -> parameters.蛇手右滑动3长度 = value);
    addSeekBarWithEditText(context, layout, "蛇手右滑动3时长", parameters.蛇手右滑动3时长, 0, 500, value -> parameters.蛇手右滑动3时长 = value);

    addSeekBarWithEditText(context, layout, "蛇手右分身点击次数", parameters.蛇手右分身点击次数, 0, 100, value -> parameters.蛇手右分身点击次数 = value);
    addSeekBarWithEditText(context, layout, "蛇手右分身点击延时", parameters.蛇手右分身点击延时, 0, 500, value -> parameters.蛇手右分身点击延时 = value);

    return layout;
}));

        
    mainLayout.addView(new View(context), spacingParams);

    // 四分测合左
    mainLayout.addView(createCollapsibleSection(context, "四分测合左", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);
    
    addSeekBarWithEditText(context, layout, "滑动1角度", (int) parameters.四分测合左1角度, -180, 180, value -> parameters.四分测合左1角度 = value);
    addSeekBarWithEditText(context, layout, "滑动1长度", parameters.四分测合左1滑动长度, 0, 1000, value -> parameters.四分测合左1滑动长度 = value);
    addSeekBarWithEditText(context, layout, "滑动1时长", parameters.四分测合左1滑动时长, 0, 500, value -> parameters.四分测合左1滑动时长 = value);
                                    
    addSeekBarWithEditText(context, layout, "4分前延时", parameters.四分测合左2分前延时, 0, 500, value -> parameters.四分测合左2分前延时 = value);
    addSeekBarWithEditText(context, layout, "4分延时", parameters.四分测合左2分身延时, 0, 500, value -> parameters.四分测合左2分身延时 = value);
    addSeekBarWithEditText(context, layout, "4分后延时", parameters.四分测合左2分后延时, 0, 500, value -> parameters.四分测合左2分后延时 = value);
    
    addSeekBarWithEditText(context, layout, "滑动2角度", (int) parameters.四分测合左2角度, -180, 180, value -> parameters.四分测合左2角度 = value);
    addSeekBarWithEditText(context, layout, "滑动2长度", parameters.四分测合左2滑动长度, 0, 1000, value -> parameters.四分测合左2滑动长度 = value);
    addSeekBarWithEditText(context, layout, "滑动2时长", parameters.四分测合左2滑动时长, 0, 500, value -> parameters.四分测合左2滑动时长 = value);

    addSeekBarWithEditText(context, layout, "点击次数", parameters.四分测合左点击分身次数, 0, 100, value -> parameters.四分测合左点击分身次数 = value);
    addSeekBarWithEditText(context, layout, "点击延时", parameters.四分测合左点击延时, 0, 500, value -> parameters.四分测合左点击延时 = value);

    return layout;
}));

mainLayout.addView(new View(context), spacingParams);

// 四分测合右
mainLayout.addView(createCollapsibleSection(context, "四分测合右", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);
    
    addSeekBarWithEditText(context, layout, "滑动1角度", (int) parameters.四分测合右1角度, -180, 180, value -> parameters.四分测合右1角度 = value);
    addSeekBarWithEditText(context, layout, "滑动1长度", parameters.四分测合右1滑动长度, 0, 1000, value -> parameters.四分测合右1滑动长度 = value);
    addSeekBarWithEditText(context, layout, "滑动1时长", parameters.四分测合右1滑动时长, 0, 500, value -> parameters.四分测合右1滑动时长 = value);
                                    
    addSeekBarWithEditText(context, layout, "4分前延时", parameters.四分测合右2分前延时, 0, 500, value -> parameters.四分测合右2分前延时 = value);
    addSeekBarWithEditText(context, layout, "4分延时", parameters.四分测合右2分延时, 0, 500, value -> parameters.四分测合右2分延时 = value);
    addSeekBarWithEditText(context, layout, "4分后延时", parameters.四分测合右2分后延时, 0, 500, value -> parameters.四分测合右2分后延时 = value);
    
    addSeekBarWithEditText(context, layout, "滑动2角度", (int) parameters.四分测合右2角度, -180, 180, value -> parameters.四分测合右2角度 = value);
    addSeekBarWithEditText(context, layout, "滑动2长度", parameters.四分测合右2滑动长度, 0, 1000, value -> parameters.四分测合右2滑动长度 = value);
    addSeekBarWithEditText(context, layout, "滑动2时长", parameters.四分测合右2滑动时长, 0, 500, value -> parameters.四分测合右2滑动时长 = value);

    addSeekBarWithEditText(context, layout, "点击次数", parameters.四分测合右点击分身次数, 0, 100, value -> parameters.四分测合右点击分身次数 = value);
    addSeekBarWithEditText(context, layout, "点击延时", parameters.四分测合右点击延时, 0, 500, value -> parameters.四分测合右点击延时 = value);

    return layout;
}));
        
        mainLayout.addView(new View(context), spacingParams);

// 后仰左
mainLayout.addView(createCollapsibleSection(context, "后仰左", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);
    
    addSeekBarWithEditText(context, layout, "滑动1角度", (int) parameters.后仰左滑动1角度, -180, 180, value -> parameters.后仰左滑动1角度 = value);
    addSeekBarWithEditText(context, layout, "滑动1长度", parameters.后仰左滑动1长度, 0, 1000, value -> parameters.后仰左滑动1长度 = value);
    addSeekBarWithEditText(context, layout, "滑动1时长", parameters.后仰左滑动1时长, 0, 500, value -> parameters.后仰左滑动1时长 = value);

    addSeekBarWithEditText(context, layout, "分身延时1", parameters.后仰左分身延时1, 0, 500, value -> parameters.后仰左分身延时1 = value);
    addSeekBarWithEditText(context, layout, "滑动延时2", parameters.后仰左滑动延时2, 0, 500, value -> parameters.后仰左滑动延时2 = value);

    addSeekBarWithEditText(context, layout, "滑动2角度", (int) parameters.后仰左滑动2角度, -180, 180, value -> parameters.后仰左滑动2角度 = value);
    addSeekBarWithEditText(context, layout, "滑动2长度", parameters.后仰左滑动2长度, 0, 1000, value -> parameters.后仰左滑动2长度 = value);
    addSeekBarWithEditText(context, layout, "滑动2时长", parameters.后仰左滑动2时长, 0, 500, value -> parameters.后仰左滑动2时长 = value);

    addSeekBarWithEditText(context, layout, "分身延时2", parameters.后仰左分身延时2, 0, 500, value -> parameters.后仰左分身延时2 = value);
    addSeekBarWithEditText(context, layout, "滑动延时3", parameters.后仰左滑动延时3, 0, 500, value -> parameters.后仰左滑动延时3 = value);

    addSeekBarWithEditText(context, layout, "滑动3角度", (int) parameters.后仰左滑动3角度, -180, 180, value -> parameters.后仰左滑动3角度 = value);
    addSeekBarWithEditText(context, layout, "滑动3长度", parameters.后仰左滑动3长度, 0, 1000, value -> parameters.后仰左滑动3长度 = value);
    addSeekBarWithEditText(context, layout, "滑动3时长", parameters.后仰左滑动3时长, 0, 500, value -> parameters.后仰左滑动3时长 = value);

    addSeekBarWithEditText(context, layout, "分身点击次数", parameters.后仰左分身点击次数, 0, 100, value -> parameters.后仰左分身点击次数 = value);
    addSeekBarWithEditText(context, layout, "点击延时", parameters.后仰左分身点击延时, 0, 500, value -> parameters.后仰左分身点击延时 = value);

    return layout;
}));
        
        mainLayout.addView(new View(context), spacingParams);

// 后仰右
mainLayout.addView(createCollapsibleSection(context, "后仰右", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);
    
    addSeekBarWithEditText(context, layout, "滑动1角度", (int) parameters.后仰右滑动1角度, -180, 180, value -> parameters.后仰右滑动1角度 = value);
    addSeekBarWithEditText(context, layout, "滑动1长度", parameters.后仰右滑动1长度, 0, 1000, value -> parameters.后仰右滑动1长度 = value);
    addSeekBarWithEditText(context, layout, "滑动1时长", parameters.后仰右滑动1时长, 0, 500, value -> parameters.后仰右滑动1时长 = value);

    addSeekBarWithEditText(context, layout, "分身延时1", parameters.后仰右分身延时1, 0, 500, value -> parameters.后仰右分身延时1 = value);
    addSeekBarWithEditText(context, layout, "滑动延时2", parameters.后仰右滑动延时2, 0, 500, value -> parameters.后仰右滑动延时2 = value);

    addSeekBarWithEditText(context, layout, "滑动2角度", (int) parameters.后仰右滑动2角度, -180, 180, value -> parameters.后仰右滑动2角度 = value);
    addSeekBarWithEditText(context, layout, "滑动2长度", parameters.后仰右滑动2长度, 0, 1000, value -> parameters.后仰右滑动2长度 = value);
    addSeekBarWithEditText(context, layout, "滑动2时长", parameters.后仰右滑动2时长, 0, 500, value -> parameters.后仰右滑动2时长 = value);

    addSeekBarWithEditText(context, layout, "分身延时2", parameters.后仰右分身延时2, 0, 500, value -> parameters.后仰右分身延时2 = value);
    addSeekBarWithEditText(context, layout, "滑动延时3", parameters.后仰右滑动延时3, 0, 500, value -> parameters.后仰右滑动延时3 = value);

    addSeekBarWithEditText(context, layout, "滑动3角度", (int) parameters.后仰右滑动3角度, -180, 180, value -> parameters.后仰右滑动3角度 = value);
    addSeekBarWithEditText(context, layout, "滑动3长度", parameters.后仰右滑动3长度, 0, 1000, value -> parameters.后仰右滑动3长度 = value);
    addSeekBarWithEditText(context, layout, "滑动3时长", parameters.后仰右滑动3时长, 0, 500, value -> parameters.后仰右滑动3时长 = value);

    addSeekBarWithEditText(context, layout, "分身点击次数", parameters.后仰右分身点击次数, 0, 100, value -> parameters.后仰右分身点击次数 = value);
    addSeekBarWithEditText(context, layout, "点击延时", parameters.后仰右分身点击延时, 0, 500, value -> parameters.后仰右分身点击延时 = value);

    return layout;
}));
        mainLayout.addView(new View(context), spacingParams);
        mainLayout.addView(createCollapsibleSection(context, "旋转左", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);

    addSeekBarWithEditText(context, layout, "滑动1角度", (int) parameters.旋转左滑动1角度, -180, 180, value -> parameters.旋转左滑动1角度 = value);
    addSeekBarWithEditText(context, layout, "滑动1长度", parameters.旋转左滑动1长度, 0, 1000, value -> parameters.旋转左滑动1长度 = value);
    addSeekBarWithEditText(context, layout, "滑动1时长", parameters.旋转左滑动1时长, 0, 500, value -> parameters.旋转左滑动1时长 = value);

    addSeekBarWithEditText(context, layout, "分身延时1", parameters.旋转左分身延时1, 0, 500, value -> parameters.旋转左分身延时1 = value);
    addSeekBarWithEditText(context, layout, "滑动延时2", parameters.旋转左滑动延时2, 0, 500, value -> parameters.旋转左滑动延时2 = value);

    addSeekBarWithEditText(context, layout, "滑动2角度", (int) parameters.旋转左滑动2角度, -180, 180, value -> parameters.旋转左滑动2角度 = value);
    addSeekBarWithEditText(context, layout, "滑动2长度", parameters.旋转左滑动2长度, 0, 1000, value -> parameters.旋转左滑动2长度 = value);
    addSeekBarWithEditText(context, layout, "滑动2时长", parameters.旋转左滑动2时长, 0, 500, value -> parameters.旋转左滑动2时长 = value);

    addSeekBarWithEditText(context, layout, "分身延时2", parameters.旋转左分身延时2, 0, 500, value -> parameters.旋转左分身延时2 = value);
    addSeekBarWithEditText(context, layout, "滑动延时3", parameters.旋转左滑动延时3, 0, 500, value -> parameters.旋转左滑动延时3 = value);

    addSeekBarWithEditText(context, layout, "滑动3角度", (int) parameters.旋转左滑动3角度, -180, 180, value -> parameters.旋转左滑动3角度 = value);
    addSeekBarWithEditText(context, layout, "滑动3长度", parameters.旋转左滑动3长度, 0, 1000, value -> parameters.旋转左滑动3长度 = value);
    addSeekBarWithEditText(context, layout, "滑动3时长", parameters.旋转左滑动3时长, 0, 500, value -> parameters.旋转左滑动3时长 = value);

    addSeekBarWithEditText(context, layout, "分身延时3", parameters.旋转左分身延时3, 0, 500, value -> parameters.旋转左分身延时3 = value);
    addSeekBarWithEditText(context, layout, "滑动延时4", parameters.旋转左滑动延时4, 0, 500, value -> parameters.旋转左滑动延时4 = value);

    addSeekBarWithEditText(context, layout, "滑动4角度", (int) parameters.旋转左滑动4角度, -180, 180, value -> parameters.旋转左滑动4角度 = value);
    addSeekBarWithEditText(context, layout, "滑动4长度", parameters.旋转左滑动4长度, 0, 1000, value -> parameters.旋转左滑动4长度 = value);
    addSeekBarWithEditText(context, layout, "滑动4时长", parameters.旋转左滑动4时长, 0, 500, value -> parameters.旋转左滑动4时长 = value);

    addSeekBarWithEditText(context, layout, "摇杆点击延时4", parameters.旋转左摇杆点击延时4, 0, 500, value -> parameters.旋转左摇杆点击延时4 = value);
    addSeekBarWithEditText(context, layout, "分身连击延时", parameters.旋转左分身连击延时, 0, 500, value -> parameters.旋转左分身连击延时 = value);

    addSeekBarWithEditText(context, layout, "分身点击次数", parameters.旋转左分身点击次数, 0, 100, value -> parameters.旋转左分身点击次数 = value);
    addSeekBarWithEditText(context, layout, "点击延时", parameters.旋转左分身点击延时, 0, 500, value -> parameters.旋转左分身点击延时 = value);

    return layout;
}));

        mainLayout.addView(new View(context), spacingParams);

mainLayout.addView(createCollapsibleSection(context, "旋转右", () -> {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(VERTICAL);

    addSeekBarWithEditText(context, layout, "滑动1角度", (int) parameters.旋转右滑动1角度, -180, 180, value -> parameters.旋转右滑动1角度 = value);
    addSeekBarWithEditText(context, layout, "滑动1长度", parameters.旋转右滑动1长度, 0, 1000, value -> parameters.旋转右滑动1长度 = value);
    addSeekBarWithEditText(context, layout, "滑动1时长", parameters.旋转右滑动1时长, 0, 500, value -> parameters.旋转右滑动1时长 = value);

    addSeekBarWithEditText(context, layout, "分身延时1", parameters.旋转右分身延时1, 0, 500, value -> parameters.旋转右分身延时1 = value);
    addSeekBarWithEditText(context, layout, "滑动延时2", parameters.旋转右滑动延时2, 0, 500, value -> parameters.旋转右滑动延时2 = value);

    addSeekBarWithEditText(context, layout, "滑动2角度", (int) parameters.旋转右滑动2角度, -180, 180, value -> parameters.旋转右滑动2角度 = value);
    addSeekBarWithEditText(context, layout, "滑动2长度", parameters.旋转右滑动2长度, 0, 1000, value -> parameters.旋转右滑动2长度 = value);
    addSeekBarWithEditText(context, layout, "滑动2时长", parameters.旋转右滑动2时长, 0, 500, value -> parameters.旋转右滑动2时长 = value);

    addSeekBarWithEditText(context, layout, "分身延时2", parameters.旋转右分身延时2, 0, 500, value -> parameters.旋转右分身延时2 = value);
    addSeekBarWithEditText(context, layout, "滑动延时3", parameters.旋转右滑动延时3, 0, 500, value -> parameters.旋转右滑动延时3 = value);

    addSeekBarWithEditText(context, layout, "滑动3角度", (int) parameters.旋转右滑动3角度, -180, 180, value -> parameters.旋转右滑动3角度 = value);
    addSeekBarWithEditText(context, layout, "滑动3长度", parameters.旋转右滑动3长度, 0, 1000, value -> parameters.旋转右滑动3长度 = value);
    addSeekBarWithEditText(context, layout, "滑动3时长", parameters.旋转右滑动3时长, 0, 500, value -> parameters.旋转右滑动3时长 = value);

    addSeekBarWithEditText(context, layout, "分身延时3", parameters.旋转右分身延时3, 0, 500, value -> parameters.旋转右分身延时3 = value);
    addSeekBarWithEditText(context, layout, "滑动延时4", parameters.旋转右滑动延时4, 0, 500, value -> parameters.旋转右滑动延时4 = value);

    addSeekBarWithEditText(context, layout, "滑动4角度", (int) parameters.旋转右滑动4角度, -180, 180, value -> parameters.旋转右滑动4角度 = value);
    addSeekBarWithEditText(context, layout, "滑动4长度", parameters.旋转右滑动4长度, 0, 1000, value -> parameters.旋转右滑动4长度 = value);
    addSeekBarWithEditText(context, layout, "滑动4时长", parameters.旋转右滑动4时长, 0, 500, value -> parameters.旋转右滑动4时长 = value);

    addSeekBarWithEditText(context, layout, "摇杆点击延时4", parameters.旋转右摇杆点击延时4, 0, 500, value -> parameters.旋转右摇杆点击延时4 = value);
    addSeekBarWithEditText(context, layout, "分身连击延时", parameters.旋转右分身连击延时, 0, 500, value -> parameters.旋转右分身连击延时 = value);

    addSeekBarWithEditText(context, layout, "分身点击次数", parameters.旋转右分身点击次数, 0, 100, value -> parameters.旋转右分身点击次数 = value);
    addSeekBarWithEditText(context, layout, "点击延时", parameters.旋转右分身点击延时, 0, 500, value -> parameters.旋转右分身点击延时 = value);

    return layout;
}));



    
    scrollView.addView(mainLayout);
    page3Layout.addView(scrollView);

    return page3Layout;
}
// 可折叠区域的创建方法
private LinearLayout createCollapsibleSection(Context context, String title, CollapsibleContentProvider contentProvider) {
    LinearLayout sectionLayout = new LinearLayout(context);
    sectionLayout.setOrientation(VERTICAL);

    // 设置折叠标题的样式
    TextView titleView = new TextView(context);
    titleView.setText(title);
    titleView.setTextSize(12); // 使用相同的小字体大小
    titleView.setTextColor(Color.parseColor("#B9A6FF")); // 字体颜色
    titleView.setGravity(Gravity.CENTER_VERTICAL);
    titleView.setPadding(16, 16, 16, 16);

    // 设置圆角背景
    GradientDrawable background = new GradientDrawable();
    background.setColor(Color.parseColor("#3A355A")); // 背景色契合主窗体
    background.setCornerRadius(16); // 圆角背景
    titleView.setBackground(background);

    // 创建内容容器并设置为折叠状态
    LinearLayout contentLayout = contentProvider.provideContent();
    contentLayout.setVisibility(GONE); // 初始折叠
    sectionLayout.addView(titleView);
    sectionLayout.addView(contentLayout);

    // 设置点击折叠/展开逻辑
    titleView.setOnClickListener(v -> {
        if (contentLayout.getVisibility() == VISIBLE) {
            contentLayout.setVisibility(GONE);
        } else {
            contentLayout.setVisibility(VISIBLE);
        }
    });

    return sectionLayout;
}

    // 分页布局4
    private LinearLayout createPage4Layout(Context context) {
    // 创建 ScrollView 作为最外层布局
    ScrollView scrollView = new ScrollView(context);
    scrollView.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
    ));

    // 创建原有的 LinearLayout（垂直方向）
    LinearLayout page4Layout = new LinearLayout(context);
    page4Layout.setOrientation(LinearLayout.VERTICAL);
    int margin = dpToPx(16);
    LinearLayout.LayoutParams page4LayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );
    page4LayoutParams.setMargins(margin, margin, margin, margin);
    page4Layout.setLayoutParams(page4LayoutParams);

    // ------------------ 标题文字 ------------------
    TextView textView = new TextView(context);
    textView.setText("在此处设置指定排名ID并保存到文件。");
    textView.setTextSize(16);
    textView.setTextColor(Color.parseColor("#B9A6FF")); // 紫色
    page4Layout.addView(textView);

    // ------------------ 横向容器: 左侧 EditText，右侧按钮 (指定排名ID) ------------------
    LinearLayout inputRowLayout1 = createInputRowLayout(
            context,
            "请输入新的排名ID",
            "保存并更新排名ID",
            () -> specifiedRankId,
            (newId) -> specifiedRankId = newId,
            "RankId.txt"
    );
    page4Layout.addView(inputRowLayout1);

    // ------------------ 横向容器: 左侧 EditText，右侧按钮 (自身ID) ------------------
    LinearLayout inputRowLayout2 = createInputRowLayout(
            context,
            "请输入新的自身ID",
            "保存并更新自身ID",
            () -> myRankId,
            (newId) -> myRankId = newId,
            "MyRankId.txt"
    );
    page4Layout.addView(inputRowLayout2);

    // ------------------ 查询排名功能 ------------------
    LinearLayout queryLayout = new LinearLayout(context);
    queryLayout.setOrientation(LinearLayout.VERTICAL);
    queryLayout.setPadding(0, dpToPx(8), 0, dpToPx(8));

    // 输入框
    EditText queryEditText = new EditText(context);
    queryEditText.setHint("请输入要查询的ID");
    queryEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
    queryEditText.setTextSize(16);
    queryEditText.setTextColor(Color.parseColor("#B9A6FF"));
    queryEditText.setHintTextColor(Color.parseColor("#50B9A6FF"));
    queryEditText.setBackground(null);

    // 下划线
    View queryUnderlineView = new View(context);
    queryUnderlineView.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dpToPx(2)
    ));
    queryUnderlineView.setBackgroundColor(Color.parseColor("#50B9A6FF"));
    queryEditText.setOnFocusChangeListener((v, hasFocus) -> {
        queryUnderlineView.setBackgroundColor(hasFocus ? Color.parseColor("#B9A6FF") : Color.parseColor("#50B9A6FF"));
    });

    LinearLayout queryInputContainer = new LinearLayout(context);
    queryInputContainer.setOrientation(LinearLayout.VERTICAL);
    queryInputContainer.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    ));
    queryInputContainer.addView(queryEditText);
    queryInputContainer.addView(queryUnderlineView);

    queryLayout.addView(queryInputContainer);

    // 查询按钮
    Button queryButton = new Button(context);
    queryButton.setText("查询排名");
    queryButton.setTextSize(16);
    queryButton.setTextColor(Color.parseColor("#B9A6FF"));

    GradientDrawable buttonBg = new GradientDrawable();
    buttonBg.setCornerRadius(dpToPx(8));
    buttonBg.setColor(Color.parseColor("#33000000"));
    buttonBg.setStroke(dpToPx(1), Color.parseColor("#B9A6FF"));
    queryButton.setBackground(buttonBg);

    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );
    buttonParams.topMargin = dpToPx(8);
    queryButton.setLayoutParams(buttonParams);

    queryLayout.addView(queryButton);

    // 日志窗口
    TextView logTextView = new TextView(context);
    logTextView.setTextSize(14);
    logTextView.setTextColor(Color.parseColor("#000000"));
    logTextView.setBackgroundColor(Color.parseColor("#F0F0F0"));
    logTextView.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
    logTextView.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dpToPx(150)
    ));
    logTextView.setText("日志窗口：\n");
    queryLayout.addView(logTextView);

    // 查询按钮点击事件
    queryButton.setOnClickListener(v -> {
    String inputId = queryEditText.getText().toString().trim();
    if (TextUtils.isEmpty(inputId)) {
        Toast.makeText(context, "输入不能为空", Toast.LENGTH_SHORT).show();
        return;
    }

    logTextView.setText("正在查询排名信息...\n");

    new Thread(() -> {
        try {
            //网站
            String url = "https://rank.mysqil.top";
            String params = "playerId=" + URLEncoder.encode(inputId, "UTF-8");

            // 配置 HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // 发送 POST 数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(params.getBytes());
            outputStream.flush();
            outputStream.close();

            // 获取响应数据
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder htmlResponse = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    htmlResponse.append(line);
                }
                reader.close();

                // 从 HTML 中解析数据
                String resultMessage = parseRankFromHtml(htmlResponse.toString());

                // 更新日志窗口
                String finalMessage = resultMessage;
                new Handler(Looper.getMainLooper()).post(() -> logTextView.setText(finalMessage));
            } else {
                // 处理服务器错误
                new Handler(Looper.getMainLooper()).post(() -> logTextView.setText("查询失败：服务器错误。\n"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 处理网络或解析错误
            new Handler(Looper.getMainLooper()).post(() -> logTextView.setText("查询失败：网络错误或解析错误。\n"));
        }
    }).start();
});




    page4Layout.addView(queryLayout);

    // 将原有布局添加到 ScrollView 中
    scrollView.addView(page4Layout);

    // 外层布局，包含滚动布局
    LinearLayout finalLayout = new LinearLayout(context);
    finalLayout.setOrientation(LinearLayout.VERTICAL);
    finalLayout.addView(scrollView);

    return finalLayout; // 返回最终布局
}

// 工具方法：将 dp 转换为 px

                /**
 * 从 HTML 响应中提取排名数据。
 */
private String parseRankFromHtml(String html) {
    try {
        // 根据返回的 HTML 提取相关字段
        String name = html.split("中文ID:")[1].split("</p>")[0].trim();
        String qqId = html.split("数字ID:")[1].split("</p>")[0].trim();
        String rank = html.split("全服排名:")[1].split("</p>")[0].trim();

        return "查询成功！\n" +
                "中文ID: " + name + "\n" +
                "数字ID: " + qqId + "\n" +
                "全服排名: " + rank;
    } catch (Exception e) {
        e.printStackTrace();
        return "查询失败：无法解析网页内容。\n";
    }
}


    private LinearLayout createInputRowLayout(
            Context context,
            String hintText,
            String buttonText,
            Supplier<Integer> currentValueGetter,
            Consumer<Integer> valueUpdater,
            String fileName) {
    LinearLayout inputRowLayout = new LinearLayout(context);
    inputRowLayout.setOrientation(LinearLayout.HORIZONTAL);
    inputRowLayout.setLayoutParams(new LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
    ));
    inputRowLayout.setPadding(0, dpToPx(8), 0, dpToPx(8));

    // ---- EditText ----
    EditText editText = new EditText(context);
    editText.setHint(hintText);
    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
    editText.setTextSize(16);
    editText.setTextColor(Color.parseColor("#B9A6FF"));       // 字体颜色
    editText.setHintTextColor(Color.parseColor("#50B9A6FF")); // Hint 半透明紫
    editText.setHighlightColor(Color.parseColor("#50B9A6FF")); // 选中文字高亮颜色

    // 去掉原生的下划线背景
    editText.setBackground(null);

    // 包裹 EditText 的容器，带底部横线
    LinearLayout editTextContainer = new LinearLayout(context);
    editTextContainer.setOrientation(LinearLayout.VERTICAL);
    editTextContainer.setLayoutParams(new LinearLayout.LayoutParams(
            0,
            LayoutParams.WRAP_CONTENT,
            1f
    ));

    editTextContainer.addView(editText);

    View underlineView = new View(context);
    underlineView.setLayoutParams(new LayoutParams(
            LayoutParams.MATCH_PARENT,
            dpToPx(2)
    ));
    underlineView.setBackgroundColor(Color.parseColor("#50B9A6FF"));
    editTextContainer.addView(underlineView);

    editText.setOnFocusChangeListener((v, hasFocus) -> {
        if (hasFocus) {
            underlineView.setBackgroundColor(Color.parseColor("#B9A6FF"));
        } else {
            underlineView.setBackgroundColor(Color.parseColor("#50B9A6FF"));
        }
    });

    inputRowLayout.addView(editTextContainer);

    // 预先显示当前变量值
    editText.setText(String.valueOf(currentValueGetter.get()));

    // ---- 按钮 ----
    Button saveButton = new Button(context);
    saveButton.setText(buttonText);
    saveButton.setTextSize(16);
    saveButton.setTextColor(Color.parseColor("#B9A6FF"));

    GradientDrawable buttonBg = new GradientDrawable();
    buttonBg.setCornerRadius(dpToPx(8));
    buttonBg.setColor(Color.parseColor("#33000000"));
    buttonBg.setStroke(dpToPx(1), Color.parseColor("#B9A6FF"));
    saveButton.setBackground(buttonBg);

    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
    );
    buttonParams.leftMargin = dpToPx(16);
    saveButton.setLayoutParams(buttonParams);

    saveButton.setOnClickListener(v -> {
    String inputText = editText.getText().toString().trim(); // 获取用户输入
    if (TextUtils.isEmpty(inputText)) {
        Toast.makeText(context, "输入不能为空", Toast.LENGTH_SHORT).show();
        return;
    }
    try {
        // 转换用户输入为数字
        int newId = Integer.parseInt(inputText);
        valueUpdater.accept(newId); // 更新内存中的值

        // 删除旧文件（如果存在）
        boolean 删除成功 = 文件读写.删除文件(context, fileName);
        if (删除成功) {
       // Toast.makeText(context, "旧文件删除成功", Toast.LENGTH_SHORT).show();
        } else {
          //  Toast.makeText(context, "旧文件不存在或删除失败，继续写入新文件", Toast.LENGTH_SHORT).show();
        }

        // 写入新文件到外置存储私有目录
        boolean 写入成功 = 文件读写.写入(context, fileName, newId, false, false);
        if (写入成功) {
            Toast.makeText(context, "保存成功，新的ID=" + newId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "保存失败，请检查存储状态", Toast.LENGTH_SHORT).show();
        }
    } catch (NumberFormatException e) {
        Toast.makeText(context, "输入格式错误，请输入数字", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
});



    inputRowLayout.addView(saveButton);

    return inputRowLayout;
}



    private void addSeekBarWithEditText(Context context, LinearLayout layout, String labelText, int initialValue, int min, int max, SeekBar.OnSeekBarChangeListener listener) {
        LinearLayout seekBarLayout = new LinearLayout(context);
        seekBarLayout.setOrientation(HORIZONTAL);
        seekBarLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(40)));
        seekBarLayout.setPadding(dpToPx(4), dpToPx(6), dpToPx(4), dpToPx(6));

        TextView label = new TextView(context);
        label.setText(labelText);
        label.setTextColor(Color.WHITE);
        label.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        seekBarLayout.addView(label);

        SeekBar seekBar = new SeekBar(context);
        seekBar.setMax(max - min);
        seekBar.setProgress(initialValue - min);
        seekBar.setLayoutParams(new LayoutParams(dpToPx(160), LayoutParams.WRAP_CONTENT));
        seekBar.setOnSeekBarChangeListener(listener);
        seekBarLayout.addView(seekBar);

        EditText valueInput = new EditText(context);
        valueInput.setText(String.valueOf(initialValue));
        valueInput.setTextColor(Color.WHITE);
        valueInput.setBackgroundColor(Color.TRANSPARENT);
        valueInput.setLayoutParams(new LayoutParams(dpToPx(50), LayoutParams.WRAP_CONTENT));
        valueInput.setGravity(Gravity.CENTER);
        valueInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    int value = Integer.parseInt(charSequence.toString());
                    seekBar.setProgress(value - min);
                } catch (NumberFormatException e) {}
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        seekBarLayout.addView(valueInput);
        layout.addView(seekBarLayout);
    }

    private LinearLayout createCollapsibleSection(Context context, String title, CollapsibleSectionContentProvider contentProvider) {
        LinearLayout sectionLayout = new LinearLayout(context);
        sectionLayout.setOrientation(VERTICAL);
        sectionLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        TextView sectionTitle = new TextView(context);
        sectionTitle.setText(title);
        sectionTitle.setTextColor(Color.WHITE);
        sectionTitle.setTextSize(16);
        sectionTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(40)));
        sectionLayout.addView(sectionTitle);

        sectionLayout.addView(contentProvider.provideContent());
        return sectionLayout;
    }
    
    //整数
    private void addSeekBarWithEditText(Context context, LinearLayout parent, String label, int initialValue, int min, int max, ValueChangeListener listener) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.HORIZONTAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    ));
    layout.setPadding(8, 4, 8, 4);

    TextView labelView = new TextView(context);
    labelView.setText(label);
    labelView.setTextSize(10); // 小字体
    labelView.setTextColor(Color.parseColor("#B9A6FF"));
    labelView.setGravity(Gravity.CENTER_VERTICAL);
    labelView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.2f));
    layout.addView(labelView);

    // 设置 SeekBar 居中
    SeekBar seekBar = new SeekBar(context);
    LinearLayout.LayoutParams seekBarParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2f);
    seekBarParams.setMargins(0, 20, 0, 0); // 调整上下边距以居中
    seekBar.setLayoutParams(seekBarParams);
    seekBar.setMax(max - min);
    seekBar.setProgress(initialValue - min);
    seekBar.getThumb().setColorFilter(Color.parseColor("#6F5A9A"), android.graphics.PorterDuff.Mode.SRC_IN);
    seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#A390E4"), android.graphics.PorterDuff.Mode.SRC_IN);
    layout.addView(seekBar);

    // 配置带圆角背景的输入框
    EditText valueEditText = new EditText(context);
    valueEditText.setText(String.valueOf(initialValue));
    valueEditText.setTextSize(10);
    valueEditText.setTextColor(Color.parseColor("#B9A6FF"));
    valueEditText.setGravity(Gravity.CENTER);
    
    // 设置输入框圆角背景
    GradientDrawable editTextBackground = new GradientDrawable();
    editTextBackground.setColor(Color.parseColor("#3A355A")); // 背景颜色
    editTextBackground.setCornerRadius(16); // 圆角半径
    valueEditText.setBackground(editTextBackground);

    valueEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.8f));
    layout.addView(valueEditText);

    // 滑动条和输入框的联动逻辑
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int value = progress + min;
            valueEditText.setText(String.valueOf(value));
            valueEditText.setSelection(valueEditText.getText().length());  // 保持光标在末尾
            listener.onValueChanged(value);
            ConfigUtil.loadConfig(context);
            saveCurrentConfig(); // 保存当前配置
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    });

    valueEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                int value = Integer.parseInt(s.toString());
                if (value >= min && value <= max) {
                    seekBar.setProgress(value - min);
                    listener.onValueChanged(value);
                    saveCurrentConfig(); // 保存当前配置
                }
            } catch (NumberFormatException ignored) {}
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {
            // 保持光标在末尾
            valueEditText.setSelection(valueEditText.getText().length());
        }
    });

    parent.addView(layout);
}

    
    
    //浮点
    private void addFloatSeekBarWithEditText(Context context, LinearLayout parent, String label, float initialValue, float min, float max, FloatValueChangeListener listener) {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.HORIZONTAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    ));
    layout.setPadding(8, 4, 8, 4);

    TextView labelView = new TextView(context);
    labelView.setText(label);
    labelView.setTextSize(10); // 小字体
    labelView.setTextColor(Color.parseColor("#B9A6FF"));
    labelView.setGravity(Gravity.CENTER_VERTICAL);
    labelView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.2f));
    layout.addView(labelView);

    // 设置 SeekBar 居中
    SeekBar seekBar = new SeekBar(context);
    LinearLayout.LayoutParams seekBarParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2f);
    seekBarParams.setMargins(0, 20, 0, 0); // 调整上下边距以居中
    seekBar.setLayoutParams(seekBarParams);
    seekBar.setMax((int) ((max - min) * 1_000));  // 这里乘以 1,000,000,000 保留 10 位小数精度
    seekBar.setProgress((int) ((initialValue - min) * 1_000)); // 初始化时也需要乘以精度
    seekBar.getThumb().setColorFilter(Color.parseColor("#6F5A9A"), android.graphics.PorterDuff.Mode.SRC_IN);
    seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#A390E4"), android.graphics.PorterDuff.Mode.SRC_IN);
    layout.addView(seekBar);

    // 配置带圆角背景的输入框
    EditText valueEditText = new EditText(context);
    valueEditText.setText(String.format("%.3f", initialValue));  // 显示 3位小数
    valueEditText.setTextSize(10);
    valueEditText.setTextColor(Color.parseColor("#B9A6FF"));
    valueEditText.setGravity(Gravity.CENTER);

    // 设置输入框圆角背景
    GradientDrawable editTextBackground = new GradientDrawable();
    editTextBackground.setColor(Color.parseColor("#3A355A")); // 背景颜色
    editTextBackground.setCornerRadius(16); // 圆角半径
    valueEditText.setBackground(editTextBackground);

    valueEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.8f));
    layout.addView(valueEditText);

    // 滑动条和输入框的联动逻辑
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // 计算浮动值，progress 被放大 1_000 倍来保持精度
            float value = min + (progress / 1_000.0f);
            valueEditText.setText(String.format("%.3f", value)); // 更新输入框的显示
            listener.onValueChanged(value);  // 传递浮动数值给监听器
            saveCurrentConfig(); // 保存当前配置
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    });

    valueEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                // 获取浮动数值
                float value = Float.parseFloat(s.toString());
                if (value >= min && value <= max) {
                    // 使用浮动数值更新 SeekBar 进度
                    int progress = (int) ((value - min) * 1_000); // 保留  位小数的浮动处理
                    seekBar.setProgress(progress); // 更新滑动条进度
                    listener.onValueChanged(value);  // 传递浮动数值给监听器
                    saveCurrentConfig(); // 保存当前配置
                }
            } catch (NumberFormatException ignored) {}
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {
            valueEditText.setSelection(valueEditText.getText().length());
        }
    });

    parent.addView(layout);
}

private void addDoubleSeekBarWithEditText(Context context, LinearLayout parent, String label, double initialValue, double min, double max, DoubleValueChangeListener listener) {
    // 使用大范围的缩放因子，支持小数点后 10 位
    int precisionScale = 1_000_000; // 支持小数点后 6 位


    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.HORIZONTAL);
    layout.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    ));
    layout.setPadding(8, 4, 8, 4);

    TextView labelView = new TextView(context);
    labelView.setText(label);
    labelView.setTextSize(10); // 小字体
    labelView.setTextColor(Color.parseColor("#B9A6FF"));
    labelView.setGravity(Gravity.CENTER_VERTICAL);
    labelView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.2f));
    layout.addView(labelView);

    // 设置 SeekBar
    SeekBar seekBar = new SeekBar(context);
    LinearLayout.LayoutParams seekBarParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2f);
    seekBarParams.setMargins(0, 20, 0, 0); // 调整上下边距以居中
    seekBar.setLayoutParams(seekBarParams);
    seekBar.setMax((int) ((max - min) * precisionScale));  // 放大到 10^9 范围
    seekBar.setProgress((int) ((initialValue - min) * precisionScale)); // 初始化时放大
    seekBar.getThumb().setColorFilter(Color.parseColor("#6F5A9A"), android.graphics.PorterDuff.Mode.SRC_IN);
    seekBar.getProgressDrawable().setColorFilter(Color.parseColor("#A390E4"), android.graphics.PorterDuff.Mode.SRC_IN);
    layout.addView(seekBar);

    // 配置输入框
    EditText valueEditText = new EditText(context);
    valueEditText.setText(String.format("%.10f", initialValue));  // 显示 10 位小数
    valueEditText.setTextSize(10);
    valueEditText.setTextColor(Color.parseColor("#B9A6FF"));
    valueEditText.setGravity(Gravity.CENTER);

    // 设置输入框圆角背景
    GradientDrawable editTextBackground = new GradientDrawable();
    editTextBackground.setColor(Color.parseColor("#3A355A")); // 背景颜色
    editTextBackground.setCornerRadius(16); // 圆角半径
    valueEditText.setBackground(editTextBackground);

    valueEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.8f));
    layout.addView(valueEditText);

    // 联动逻辑：SeekBar -> EditText
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // 计算浮动值，进度值除以缩放因子
            double value = min + (progress / (double) precisionScale);
            valueEditText.setText(String.format("%.10f", value)); // 更新输入框
            listener.onValueChanged(value);  // 回调监听器
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    });

    // 联动逻辑：EditText -> SeekBar
    valueEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                // 获取双精度浮动值
                double value = Double.parseDouble(s.toString());
                if (value >= min && value <= max) {
                    // 将浮动值转换为 SeekBar 的进度
                    int progress = (int) ((value - min) * precisionScale);
                    seekBar.setProgress(progress); // 更新滑动条进度
                    listener.onValueChanged(value);  // 回调监听器
                }
            } catch (NumberFormatException ignored) {}
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    });

    parent.addView(layout);
}

public interface DoubleValueChangeListener {
    void onValueChanged(double value);
}



    // 保存配置方法
    private void saveCurrentConfig() {
        ConfigUtil.saveConfigToTargetAppDir(parameters);
    }
    private void updateMenuHighlight(int pageIndex) {
        resetMenuBackground(gameSettings);
        resetMenuBackground(skinSettings);
        resetMenuBackground(operationSettings);
        resetMenuBackground(advancedSettings);

        if (pageIndex == 0) {
            setRoundedBackground(gameSettings, "#6F5A9A");
        } else if (pageIndex == 1) {
            setRoundedBackground(skinSettings, "#6F5A9A");
        } else if (pageIndex == 2) {
            setRoundedBackground(operationSettings, "#6F5A9A");
        } else if (pageIndex == 3) {
            setRoundedBackground(advancedSettings, "#6F5A9A");
        }
    }
    private void resetMenuBackground(TextView textView) {
        textView.setBackgroundColor(Color.TRANSPARENT);
    }

    private void setRoundedBackground(TextView textView, String color) {
        GradientDrawable roundedBackground = new GradientDrawable();
        roundedBackground.setColor(Color.parseColor(color));
        roundedBackground.setCornerRadius(dpToPx(16));
        textView.setBackground(roundedBackground);
    }

    // 接口用于实时更新参数值
    interface ValueChangeListener {
        void onValueChanged(int value);
    }

    // 浮动值
    interface FloatValueChangeListener {
    void onValueChanged(float value); 
    }

    private void applySwitchStyle(Switch switchButton) {
        switchButton.getThumbDrawable().setColorFilter(Color.parseColor("#6F5A9A"), PorterDuff.Mode.SRC_IN); // 紫色滑块
        switchButton.getTrackDrawable().setColorFilter(Color.parseColor("#A390E4"), PorterDuff.Mode.SRC_IN); // 浅紫色轨道
    }
    private void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // 辅助方法：转换 dp 为 px
    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
    // 接口定义
    public interface CollapsibleContentProvider {
    LinearLayout provideContent();
    }


    // 辅助接口：提供可折叠区域的内容
    interface CollapsibleSectionContentProvider {
        View provideContent();
        
    }
    
private void 三角() {
    if (isRunning) {
        return;  // 如果当前操作正在执行，则不做任何事情
    }
    
    isRunning = true;  // 标记为正在执行

    new Thread(new Runnable() {
        @Override
        public void run() {
            try {

                if (initialAngle == null) { initialAngle = joystickAngle; }
                // 执行三角操作
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.三角1滑动角度, parameters.三角1滑动长度, parameters.三角1滑动时长, 触摸id,w1);
                Thread.sleep(parameters.三角1点击延时); 

                // 按下和抬起
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.三角2滑动延时); 

                // 第二次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.三角2滑动角度, parameters.三角2滑动长度, parameters.三角2滑动时长, 触摸id,w1);
                Thread.sleep(parameters.三角2点击延时);

                // 按下和抬起
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.三角3滑动延时);

                // 第三次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.三角3滑动角度, parameters.三角3滑动长度, parameters.三角3滑动时长, 触摸id,w1);
                    
                // 循环点击
                for (int i = 0; i < parameters.三角分身次数; i++) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.三角分身延时);
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        initialAngle = null;
                 HOOK.TouchEventHandler.抬起(simpleButtonController.摇杆x, simpleButtonController.摇杆x, 点击id);
    
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 操作完成后恢复标志位
                isRunning = false;
            }
        }
    }).start();
}

private void 蛇手左() {
    if (isRunning) {
        return;  // 如果当前操作正在执行，则不做任何事情
    }

    isRunning = true;  // 标记为正在执行

    new Thread(new Runnable() {
        @Override
        public void run() {
            try {

                if (initialAngle == null) { initialAngle = joystickAngle; }
                // 执行蛇手左操作
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.蛇手左滑动1角度, parameters.蛇手左滑动1长度, parameters.蛇手左滑动1时长, 触摸id,w1);
                Thread.sleep(parameters.蛇手左分身延时1); 

                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.蛇手左滑动延时2);

                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.蛇手左滑动2角度, parameters.蛇手左滑动2长度, parameters.蛇手左滑动2时长, 触摸id,w1);
                Thread.sleep(parameters.蛇手左分身延时2);
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.蛇手左滑动延时3); // 延时
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.蛇手左滑动3角度, parameters.蛇手左滑动3长度, parameters.蛇手左滑动3时长, 触摸id,w1);

                // 循环按下和抬起
                for (int i = 0; i < parameters.蛇手左分身点击次数; i++) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.蛇手左分身点击延时);
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        initialAngle = null;
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 操作完成后恢复标志位
                isRunning = false;
            }
        }
    }).start();
}

private void 蛇手右() {
    if (isRunning) {
        return;  // 如果当前操作正在执行，则不做任何事情
    }

    isRunning = true;  // 标记为正在执行

    new Thread(new Runnable() {
        @Override
        public void run() {
            try {

                if (initialAngle == null) { initialAngle = joystickAngle; }
                // 执行蛇手右操作
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.蛇手右滑动1角度, parameters.蛇手右滑动1长度, parameters.蛇手右滑动1时长, 触摸id,w1);
                Thread.sleep(parameters.蛇手右分身延时1);
                        
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.蛇手右滑动延时2);

                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.蛇手右滑动2角度, parameters.蛇手右滑动2长度, parameters.蛇手右滑动2时长, 触摸id,w1);
                Thread.sleep(parameters.蛇手右分身延时2);
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.蛇手右滑动延时3); // 延时
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.蛇手右滑动3角度, parameters.蛇手右滑动3长度, parameters.蛇手右滑动3时长, 触摸id,w1);

                // 循环按下和抬起
                for (int i = 0; i < parameters.蛇手右分身点击次数; i++) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.蛇手右分身点击延时);
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        initialAngle = null;
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 操作完成后恢复标志位
                isRunning = false;
            }
        }
    }).start();
}

private void 四分测合左() {
    if (isRunning) {
        return;  // 如果当前操作正在执行，则不做任何事情
    }

    isRunning = true;  // 标记为正在执行

    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
               
                if (initialAngle == null) { initialAngle = joystickAngle; }


                // 第一次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.四分测合左1角度, parameters.四分测合左1滑动长度, parameters.四分测合左1滑动时长, 触摸id,w1);
                Thread.sleep(parameters.四分测合左2分前延时); // 延时
                for(int i = 0; i < 2; ++i) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                	HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.四分测合左2分身延时); // 延时
                }
                Thread.sleep(parameters.四分测合左2分后延时); // 延时

                // 第二次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.四分测合左2角度, parameters.四分测合左2滑动长度, parameters.四分测合左2滑动时长, 触摸id,w1);

                // 循环按下和抬起，带有延时
                for (int i = 0; i < parameters.四分测合左点击分身次数; i++) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.四分测合左点击延时); // 延时
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        initialAngle = null;
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 操作完成后恢复标志位
                isRunning = false;
            }
        }
    }).start();
}

private void 四分测合右() {
    if (isRunning) {
        return;  // 如果当前操作正在执行，则不做任何事情
    }

    isRunning = true;  // 标记为正在执行

    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                        
                if (initialAngle == null) {
                    initialAngle = joystickAngle;
                }


                // 第一次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.四分测合右1角度, parameters.四分测合右1滑动长度, parameters.四分测合右1滑动时长, 触摸id,w1);
                Thread.sleep(parameters.四分测合右2分前延时); // 延时
                for(int i = 0; i < 2; ++i) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                	HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.四分测合右2分延时); // 延时
                }
                Thread.sleep(parameters.四分测合右2分后延时); // 延时

                // 第二次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.四分测合右2角度, parameters.四分测合右2滑动长度, parameters.四分测合右2滑动时长, 触摸id,w1);
                

                // 循环按下和抬起，带有延时
                for (int i = 0; i < parameters.四分测合右点击分身次数; i++) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.四分测合右点击延时); // 延时
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        initialAngle = null;
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 操作完成后恢复标志位
                isRunning = false;
            }
        }
    }).start();
}

private void 后仰左() {
    if (isRunning) {
        return;  // 如果当前操作正在执行，则不做任何事情
    }

    isRunning = true;  // 标记为正在执行

    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                // 确保 initialAngle 被正确初始化
                if (initialAngle == null) {
                    initialAngle = joystickAngle;
                }

                // 第一次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.后仰左滑动1角度, parameters.后仰左滑动1长度, parameters.后仰左滑动1时长, 触摸id,w1);
                Thread.sleep(parameters.后仰左分身延时1); // 延时
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.后仰左滑动延时2); // 延时
                // 第二次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.后仰左滑动2角度, parameters.后仰左滑动2长度, parameters.后仰左滑动2时长, 触摸id,w1);
                Thread.sleep(parameters.后仰左分身延时2); // 延时
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.后仰左滑动延时3); // 延时

                // 第三次滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.后仰左滑动3角度, parameters.后仰左滑动3长度, parameters.后仰左滑动3时长, 触摸id,w1);

                // 循环按下和抬起，带有延时
                for (int i = 0; i < parameters.后仰左分身点击次数; i++) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.后仰左分身点击延时); // 延时
                }

                // 恢复 initialAngle
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        initialAngle = null;
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 操作完成后恢复标志位
                isRunning = false;
            }
        }
    }).start();
}


private void 后仰右() {
    if (isRunning) {
        return;  // 如果当前操作正在执行，则不做任何事情
    }

    isRunning = true;  // 标记为正在执行

    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                        
                if (initialAngle == null) {
                    initialAngle = joystickAngle;
                }

                // 使用定义的参数进行滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.后仰右滑动1角度, parameters.后仰右滑动1长度, parameters.后仰右滑动1时长, 触摸id,w1);
                Thread.sleep(parameters.后仰右分身延时1); // 延时
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.后仰右滑动延时2); // 延时

                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.后仰右滑动2角度, parameters.后仰右滑动2长度, parameters.后仰右滑动2时长, 触摸id,w1);
                Thread.sleep(parameters.后仰右分身延时2); // 延时
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.后仰右滑动延时3); // 延时

                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.后仰右滑动3角度, parameters.后仰右滑动3长度, parameters.后仰右滑动3时长, 触摸id,w1);

                // 循环按下和抬起，带有延时
                for (int i = 0; i < parameters.后仰右分身点击次数; i++) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.后仰右分身点击延时); // 延时
                }

                // 重置角度
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        initialAngle = null;
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 操作完成后恢复标志位
                isRunning = false;
            }
        }
    }).start();
}
    
    private void 旋转右() {
    if (isRunning) {
        return;  // 如果当前操作正在执行，则不做任何事情
    }

    isRunning = true;  // 标记为正在执行

    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                        
                if (initialAngle == null) {
                    initialAngle = joystickAngle;
                }

                // 使用定义的参数进行滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.旋转右滑动1角度, parameters.旋转右滑动1长度, parameters.旋转右滑动1时长, 触摸id,w1);
                Thread.sleep(parameters.旋转右分身延时1); // 延时
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.旋转右滑动延时2); // 延时

                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.旋转右滑动2角度, parameters.旋转右滑动2长度, parameters.旋转右滑动2时长, 触摸id,w1);
                Thread.sleep(parameters.旋转右分身延时2); // 延时
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.旋转右滑动延时3); // 延时

                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.旋转右滑动3角度, parameters.旋转右滑动3长度, parameters.旋转右滑动3时长, 触摸id,w1);
                Thread.sleep(parameters.旋转右分身延时3); // 延时
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.旋转右滑动延时4); // 延时
                        
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.旋转右滑动4角度, parameters.旋转右滑动4长度, parameters.旋转右滑动4时长, 触摸id,w1);
                Thread.sleep(parameters.旋转右摇杆点击延时4); // 延时
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);

                Thread.sleep(parameters.旋转右分身连击延时); // 延时
             
                // 循环按下和抬起，带有延时
                for (int i = 0; i < parameters.后仰右分身点击次数; i++) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.后仰右分身点击延时); // 延时
                }

                // 重置角度
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        initialAngle = null;
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 操作完成后恢复标志位
                isRunning = false;
            }
        }
    }).start();
}

    
    private void 旋转左() {
    if (isRunning) {
        return;  // 如果当前操作正在执行，则不做任何事情
    }

    isRunning = true;  // 标记为正在执行

    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                if (initialAngle == null) {
                    initialAngle = joystickAngle;
                }

                // 使用定义的参数进行滑动
                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.旋转左滑动1角度, parameters.旋转左滑动1长度, parameters.旋转左滑动1时长, 触摸id,w1);
                Thread.sleep(parameters.旋转左分身延时1); // 延时
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.旋转左滑动延时2); // 延时

                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.旋转左滑动2角度, parameters.旋转左滑动2长度, parameters.旋转左滑动2时长, 触摸id,w1);
                Thread.sleep(parameters.旋转左分身延时2); // 延时
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.旋转左滑动延时3); // 延时

                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.旋转左滑动3角度, parameters.旋转左滑动3长度, parameters.旋转左滑动3时长, 触摸id,w1);
                Thread.sleep(parameters.旋转左分身延时3); // 延时
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                Thread.sleep(parameters.旋转左滑动延时4); // 延时

                HOOK.TouchEventHandler.角度滑动(simpleButtonController.摇杆x, simpleButtonController.摇杆y, initialAngle, parameters.旋转左滑动4角度, parameters.旋转左滑动4长度, parameters.旋转左滑动4时长, 触摸id,w1);
                Thread.sleep(parameters.旋转左摇杆点击延时4); // 延时
                HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);

                Thread.sleep(parameters.旋转左分身连击延时); // 延时

                // 循环按下和抬起，带有延时
                for (int i = 0; i < parameters.旋转左分身点击次数; i++) {
                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
                    Thread.sleep(parameters.旋转左分身点击延时); // 延时
                }

                // 重置角度
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        initialAngle = null;
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 操作完成后恢复标志位
                isRunning = false;
            }
        }
    }).start();
}

   private void 黑屏值(Context context) {
    new Thread(() -> {
        try {
            // 定义文件名
            String fileName = "memory_value.txt";

            // 初始化文件路径，并确保文件存在，默认值为800
            文件读写.写入(context, fileName, "800", false, false); // 确保文件存在且初始化为 "800"

            long q1 = a3 - 104;

            while (true) {
                // 每隔500ms检查一次文件内容
                String value = 文件读写.读取(context, fileName, String.class);

                // 如果文件有内容，进行写入操作
                if (value != null && !value.isEmpty()) {
                    // 写入内存
                    memory.setValue(value, q1, memory.TYPE_FLOAT);

                    // 清空文件内容，防止重复执行
                    文件读写.写入(context, fileName, "", false, false);
                }

                // 每隔500ms再次检查文件
                Thread.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}).start();
}


    
    
    

//private void 内存卡点() {
//    new Thread(() -> {
//        try {
//            // 初始读取地址
//            int initialValue = memory.readDword(a2 - 208);
//            previousValue = initialValue;
//
//            while (true) {
//                // 每隔10毫秒读取一次当前地址的值
//                int currentValue = memory.readDword(a2 - 208);
//
//                // 判断值是否增加超过2
//                if (currentValue - previousValue > parameters.卡点过滤) {
//
//                 for (int i = 0; i < parameters.卡点分身次数; i++) {
//                    HOOK.TouchEventHandler.按下(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
//                    HOOK.TouchEventHandler.抬起(simpleButtonController.分身x, simpleButtonController.分身y, 点击id);
//                    Thread.sleep(parameters.卡点分身间隔); // 延时
//                
//                  }
//
//                    // 结束线程
//                    break;
//                }
//
//                // 更新之前的值
//                previousValue = currentValue;
//
//                Thread.sleep(parameters.卡点循环判断);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }).start();
//}

    
// 定义全局字符串变量，用于存储结果
private static String resultData;

// 方法：获取公告
public static void getAnnouncement(final Context context) {
    wy.公告(context, new wy.Callback() {
        @Override
        public void onResult(String result) {
            resultData = result; // 将公告内容存储到变量中
        }

        @Override
        public void onError(Exception e) {
            resultData = "公告获取失败: " + e.getMessage(); // 存储错误信息
        }
    });
}

  public static long[] 提取偏移链(String input) {
    if (input == null || input.isEmpty()) {
        return new long[0]; // 返回空数组
    }

    try {
        // 去掉字符串中的非数字和逗号部分
        input = input.replaceAll("[^0-9A-Fa-fx,]", "").trim();

        // 按逗号分割
        String[] hexValues = input.split(",");

        // 转换为 long 数组
        long[] offsets = new long[hexValues.length];
        for (int i = 0; i < hexValues.length; i++) {
            offsets[i] = Long.decode(hexValues[i].trim()); // 支持 0x 开头的十六进制转换
        }

        return offsets;
    } catch (Exception e) {
        Log.e("提取偏移链", "解析失败: " + e.getMessage());
        return new long[0]; // 如果解析失败返回空数组
    }
}
    public static long[] 提取字符(String input) {
    if (input == null || input.isEmpty()) {
        return new long[0]; // 返回空数组
    }

    try {
        // 去掉字符串中的非数字和逗号部分
        input = input.replaceAll("[^0-9A-Fa-fx,]", "").trim();

        // 按逗号分割
        String[] hexValues = input.split(",");

        // 转换为 long 数组
        long[] offsets = new long[hexValues.length];
        for (int i = 0; i < hexValues.length; i++) {
            offsets[i] = Long.decode(hexValues[i].trim()); // 支持 0x 开头的十六进制转换
        }

        return offsets;
    } catch (Exception e) {
        Log.e("提取偏移链", "解析失败: " + e.getMessage());
        return new long[0]; // 如果解析失败返回空数组
    }
}

        
        // 将 long[] 数组转换为字符串的方法
public static String 数组转字符串(long[] array) {
    if (array == null || array.length == 0) {
        return "空数组";
    }

    StringBuilder result = new StringBuilder();
    for (long value : array) {
        result.append(String.format("0x%X, ", value)); // 格式化为十六进制
    }

    // 去掉最后的逗号和空格
    return result.substring(0, result.length() - 2);
}

    public static int 提取变量(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("输入字符串不能为空");
        }

        // 匹配引号中的数字，例如 "96"
        String regex = "\"msg\":\"(\\d+)\"";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(input);

        // 查找匹配
        if (matcher.find()) {
            // 提取第一个捕获组中的数字并转换为 int 类型
            return Integer.parseInt(matcher.group(1));
        }

        // 如果没有匹配到数字，返回 -1 或其他默认值
        return -1;
    }
    
    
    
    
/// 定义一个全局变量用于显示刷新率和 FPS
TextView fpsTextView;
private Choreographer.FrameCallback frameCallback;
private int 捕获画面FPS = 0; // 捕获画面获取的刷新率

private void monitorRootViewDrawFrequency(View rootView) {
    // 初始化 TextView，用于显示 FPS 和刷新率
    fpsTextView = new TextView(rootView.getContext());
    fpsTextView.setTextColor(Color.RED);
    fpsTextView.setTextSize(16);
    fpsTextView.setBackgroundColor(Color.argb(128, 0, 0, 0)); // 半透明背景

    // 将 TextView 添加到根布局
    if (rootView instanceof ViewGroup) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        ((ViewGroup) rootView).addView(fpsTextView, params);
    }

    // 获取设备支持的屏幕刷新率
    final float refreshRate = ((WindowManager) rootView.getContext().getSystemService(Context.WINDOW_SERVICE))
            .getDefaultDisplay()
            .getRefreshRate(); // 真实的设备刷新率，例如 120Hz 或 60Hz

    // 使用 Choreographer 来统计 FPS
    frameCallback = new Choreographer.FrameCallback() {
        private long lastFrameTimeNanos = 0; // 上一次帧的时间
        private int frameCount = 0;         // 帧计数

        @Override
        public void doFrame(long frameTimeNanos) {
            if (lastFrameTimeNanos == 0) {
                lastFrameTimeNanos = frameTimeNanos;
            }

            frameCount++;

            // 计算时间间隔是否超过 1 秒
            if ((frameTimeNanos - lastFrameTimeNanos) >= 1_000_000_000L) { // 1 秒 = 10^9 纳秒
                int appFPS = frameCount; // 当前应用帧率
                frameCount = 0;          // 重置帧计数
                lastFrameTimeNanos = frameTimeNanos;

                // 更新 TextView 显示
                fpsTextView.setText(String.format(
                        "刷新率: %.1f Hz\nFPS: 当前应用刷新率 %d + 捕获画面刷新率 %d\n捕获画面实际刷新率: %d",
                        refreshRate, appFPS, 捕获画面FPS, 捕获画面FPS));
            }

            // 注册下一帧回调
            Choreographer.getInstance().postFrameCallback(this);
        }
    };

    // 启动帧回调
    Choreographer.getInstance().postFrameCallback(frameCallback);

    // 启动捕获画面 FPS 检测
    捕获画面.startFPSDetection(16, new 捕获画面.FPSCallback() {
        @Override
        public void onFPSDetected(int fps) {
            // 更新捕获画面的刷新率
            捕获画面FPS = fps;
        }
    });
}
            
            
            
            
// 全局变量
private boolean 允许运行 = false; // 控制线程是否可以正常运行

public void 绘制目标球体(int 目标排名ID) {
    new Thread(() -> {
        boolean 已检测到分裂 = false; // 用于标记是否已经检测到目标球体分裂
        while (允许运行) { // 线程运行受全局变量控制
            try {
                // 清除之前绘制的形状
                drawView.clearAllShapes();

                // 用于记录目标排名ID的最大体积球体
                int 目标序号 = -1;
                float 最大半径 = -1;
                int 相同排名ID数量 = 0; // 用于统计相同排名ID的球体数量

                // 遍历玩家信息列表
                for (int i = 0; i < 玩家信息列表.size(); i++) {
                    String 玩家信息 = 玩家信息列表.get(i);

                    try {
                        // 解析玩家信息中的编号、坐标和半径
                        String[] parts = 玩家信息.split(" ");
                        String 编号部分 = parts[0].replace(".", ""); // "1."
                        int 编号 = Integer.parseInt(编号部分);
                        float screenX = Float.parseFloat(parts[1].replace("X=", ""));
                        float screenY = Float.parseFloat(parts[2].replace("Y=", ""));
                        float radius = Float.parseFloat(parts[3].replace("半径=", ""));
                        int rankId = Integer.parseInt(parts[4].replace("排名ID=", ""));

                        // 判断是否为目标排名ID
                        if (rankId == 目标排名ID) {
                            相同排名ID数量++; // 统计目标排名ID的球体数量

                            // 判断是否为最大半径球体
                            if (radius > 最大半径) {
                                最大半径 = radius;
                                目标序号 = 编号;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace(); // 处理解析失败的异常
                    }
                }

                // 判断目标球体是否一分为二
                if (相同排名ID数量 >= 2 && !已检测到分裂) {
                    已检测到分裂 = true; // 标记已经检测到分裂


                    // 结束当前线程
                    break;
                }

                // 如果找到了目标球体，计算屏幕坐标并绘制
                if (目标序号 != -1) {
                    for (int i = 0; i < 玩家信息列表.size(); i++) {
                        String 玩家信息 = 玩家信息列表.get(i);

                        try {
                            // 解析玩家信息中的编号、坐标和半径
                            String[] parts = 玩家信息.split(" ");
                            String 编号部分 = parts[0].replace(".", ""); // "1."
                            int 编号 = Integer.parseInt(编号部分);
                            float screenX = Float.parseFloat(parts[1].replace("X=", ""));
                            float screenY = Float.parseFloat(parts[2].replace("Y=", ""));
                            float radius = Float.parseFloat(parts[3].replace("半径=", ""));

                            // 如果是目标序号，进行绘制
                            if (编号 == 目标序号) {
                                // 屏幕中心坐标与比例
                                float 屏幕中心相对地图的x = memory.readFloat(a3 - 100);
                                float 屏幕中心相对地图的y = 200 - memory.readFloat(a3 - 96);

                                float startX = parameters.分辨率x / 2;
                                float startY = parameters.分辨率y / 2;

                                // 计算屏幕坐标
                                float screenXa = ((screenX - 屏幕中心相对地图的x)
                                        * 23.25f * parameters.比例
                                        * (23.25f * parameters.比例 / 视图比例)) + startX;
                                float screenYa = ((screenY - 屏幕中心相对地图的y)
                                        * 23.25f * parameters.比例
                                        * (23.25f * parameters.比例 / 视图比例)) + startY;

                                // 绘制球体
                                drawView.addCircle(
                                        screenXa,
                                        screenYa,
                                        radius * 23.25f * parameters.比例 * (23.25f * parameters.比例 / 视图比例),
                                        Color.YELLOW,
                                        6f
                                );
                                break; // 已找到并绘制，退出循环
                            }
                        } catch (Exception e) {
                            e.printStackTrace(); // 处理解析失败的异常
                        }
                    }
                }

                // 每10毫秒刷新一次
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();
}

    


}
