package com.ld.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import com.google.android.material.card.MaterialCardView;
import org.json.JSONObject; // 导入 JSONObject

public class RoundButtonCreator {

    private static final String PREF_NAME = "button_config";
    private static final String CONFIG_KEY = "button_data_";
    private Context context;
    private FrameLayout rootView;
    private static boolean debugMode = false;
    private Bitmap colorPickerBitmap;
    private View transparentLayer;  // 添加透明层
    public static boolean isMovable = false; // 默认为假，不允许移动

    public RoundButtonCreator(Context context, FrameLayout rootView) {
        this.context = context;
        this.rootView = rootView;
        loadConfiguration(); // 初始化时加载配置
    }

    public static void setDebugMode(boolean enabled) {
        debugMode = enabled;
    }

    public Button createRoundButton(String text, int size, int color, int x, int y, OnButtonTouchListener listener) {
    Button mainButton = new Button(context);
    mainButton.setId(View.generateViewId()); // 生成唯一ID
    mainButton.setText(text);

    // 设置圆形背景
    GradientDrawable shape = new GradientDrawable();
    shape.setShape(GradientDrawable.OVAL);
    shape.setColor(color);
    mainButton.setBackground(shape);

    // 设置按钮布局参数
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
    layoutParams.setMargins(x, y, 0, 0);
    layoutParams.gravity = Gravity.TOP | Gravity.START;
    rootView.addView(mainButton, layoutParams);

    // 设置触摸监听器
    mainButton.setOnTouchListener((v, event) -> {
        int offsetX = 0;
        int offsetY = 0;

        if (!debugMode) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 调用按下事件
                    if (listener != null) {
                        listener.onButtonDown();
                    }
                    // 记录手指按下时与按钮中心的偏移
                    offsetX = (int) event.getRawX() - (layoutParams.leftMargin + mainButton.getWidth() / 2);
                    offsetY = (int) event.getRawY() - (layoutParams.topMargin + mainButton.getHeight() / 2);
                     break;

                case MotionEvent.ACTION_MOVE:
                    // 计算新位置并调用移动事件
                    if (isMovable) {
    // 记录手指点击时的偏移
    int newLeftMargin = (int) event.getRawX() - offsetX - mainButton.getWidth() / 2;
    int newTopMargin = (int) event.getRawY() - offsetY - mainButton.getHeight() / 2;

    // 不再限制移动范围，允许按钮移出屏幕范围
    layoutParams.leftMargin = newLeftMargin;
    layoutParams.topMargin = newTopMargin;
    mainButton.setLayoutParams(layoutParams);

    // 调用移动事件监听
    if (listener != null) {
        listener.onButtonMove(newLeftMargin, newTopMargin);
    }
}

                    break;

                case MotionEvent.ACTION_UP:
                    // 调用抬起事件
                    if (listener != null) {
                        listener.onButtonUp();
                    }
                    break;
            }
            return false; // 消费触摸事件
        } else {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 记录手指按下时与按钮中心的偏移
                    offsetX = (int) event.getRawX() - (layoutParams.leftMargin + mainButton.getWidth() / 2);
                    offsetY = (int) event.getRawY() - (layoutParams.topMargin + mainButton.getHeight() / 2);
                    break;

                case MotionEvent.ACTION_MOVE:
                    // 使用偏移量计算新的按钮位置
                    layoutParams.leftMargin = (int) event.getRawX() - offsetX - mainButton.getWidth() / 2;
                    layoutParams.topMargin = (int) event.getRawY() - offsetY - mainButton.getHeight() / 2;

                    // 限制按钮移动范围
                    layoutParams.leftMargin = Math.max(0, Math.min(layoutParams.leftMargin, rootView.getWidth() - mainButton.getWidth()));
                    layoutParams.topMargin = Math.max(0, Math.min(layoutParams.topMargin, rootView.getHeight() - mainButton.getHeight()));

                    mainButton.setLayoutParams(layoutParams);
                    break;

                case MotionEvent.ACTION_UP:
                    // 保存按钮位置
                    String buttonName = mainButton.getText().toString();
                    saveConfiguration(mainButton, layoutParams.leftMargin, layoutParams.topMargin, mainButton.getWidth(), color, mainButton.getBackground().getAlpha(), buttonName);
                    toggleDebugUI(mainButton);
                    break;
            }
            return true;
        }
    });

    // 加载按钮配置
    String buttonName = mainButton.getText().toString(); // 获取按钮名称作为唯一标识
    loadButtonConfiguration(mainButton, layoutParams, buttonName);

    return mainButton;
}



    private void toggleDebugUI(Button mainButton) {
    // 每个按钮检查是否已有透明层和调试UI
    View debugUI = (View) mainButton.getTag(R.id.debug_ui);
    View transparentLayer = (View) mainButton.getTag(R.id.transparent_layer);

    // 如果透明层和调试UI不存在，则创建它们
    if (debugUI == null) {
        debugUI = createDebugUI(mainButton,280,160,context);
        mainButton.setTag(R.id.debug_ui, debugUI); // 将调试UI与按钮关联
        rootView.addView(debugUI);
    }

    if (transparentLayer == null) {
        transparentLayer = new View(context);
        transparentLayer.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        transparentLayer.setBackgroundColor(Color.parseColor("#80000000")); // 半透明
        transparentLayer.setVisibility(View.GONE);  // 初始时不显示

        // 为透明层添加点击事件，点击时隐藏透明层和对应的调试UI
        final View finalDebugUI = debugUI;
        final View finalTransparentLayer = transparentLayer;

        transparentLayer.setOnClickListener(v -> {
            finalDebugUI.setVisibility(View.GONE);
            finalTransparentLayer.setVisibility(View.GONE);
        });

        mainButton.setTag(R.id.transparent_layer, transparentLayer);
        rootView.addView(transparentLayer);
    }

    debugUI.setZ(10);  // 设置调试UI的Z轴更高
    mainButton.setZ(5); // 确保按钮的Z值低于调试UI

    if (debugUI.getVisibility() == View.VISIBLE) {
        debugUI.setVisibility(View.GONE);
        transparentLayer.setVisibility(View.GONE);
    } else {
        debugUI.setVisibility(View.VISIBLE);
        transparentLayer.setVisibility(View.VISIBLE);
    }
}

// 全局变量声明
// 全局变量声明
private ImageView colorShadeView;



public View createDebugUI(Button mainButton, int widthDp, int heightDp, Context context) {
    int widthPx = dpToPx(widthDp, context);
    int heightPx = dpToPx(heightDp, context);

    // 创建一个根布局作为主容器，横向排列
    LinearLayout mainContainer = new LinearLayout(context);
    mainContainer.setOrientation(LinearLayout.HORIZONTAL);
    mainContainer.setGravity(Gravity.CENTER_VERTICAL);
    mainContainer.setPadding(dpToPx(10, context), dpToPx(10, context), dpToPx(10, context), dpToPx(10, context));
    LinearLayout.LayoutParams mainContainerParams = new LinearLayout.LayoutParams(widthPx, heightPx);
    mainContainer.setLayoutParams(mainContainerParams);

    // 设置背景和圆角
    GradientDrawable backgroundDrawable = new GradientDrawable();
    backgroundDrawable.setColor(Color.parseColor("#F1F1F1")); // 背景色
    backgroundDrawable.setCornerRadius(dpToPx(10, context)); // 圆角
    mainContainer.setBackground(backgroundDrawable);

    // 左侧的颜色深浅选择区域，放大并居左
    ImageView colorShadeView = new ImageView(context);
    Bitmap colorShadeBitmap = createShadeBitmap(dpToPx(155, context), dpToPx(155, context), Color.RED); // 初始颜色为红色
    colorShadeView.setImageBitmap(colorShadeBitmap);
    LinearLayout.LayoutParams shadeParams = new LinearLayout.LayoutParams(dpToPx(155, context), dpToPx(155, context));
    shadeParams.setMargins(0, 0, dpToPx(20, context), 0); // 添加右边距
    colorShadeView.setLayoutParams(shadeParams);
    mainContainer.addView(colorShadeView);

    // 新增的全色域滑动条
    SeekBar hueSeekBar = new SeekBar(context);
    hueSeekBar.setMax(360); // 色调范围0-360
    LinearLayout.LayoutParams hueSeekBarParams = new LinearLayout.LayoutParams(dpToPx(143, context), dpToPx(11, context)); // 长度143dp，宽度11dp
    hueSeekBarParams.setMargins(dpToPx(-65, context), 0, dpToPx(-50, context), 0); // 调整边距
    hueSeekBar.setLayoutParams(hueSeekBarParams);
    hueSeekBar.setRotation(90); // 旋转90度
    hueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // 根据进度值更新颜色深浅选择区域的颜色
            int color = Color.HSVToColor(new float[]{progress, 1, 1});
            colorShadeView.setImageBitmap(createShadeBitmap(dpToPx(155, context), dpToPx(155, context), color));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    });
    mainContainer.addView(hueSeekBar);

    // 中间的透明度滑动条
    SeekBar opacitySeekBar = new SeekBar(context);
    opacitySeekBar.setMax(255);
    opacitySeekBar.setProgress(mainButton.getBackground().getAlpha());
    opacitySeekBar.setRotation(90); // 旋转90度
    LinearLayout.LayoutParams opacityParams = new LinearLayout.LayoutParams(dpToPx(143, context), dpToPx(11, context)); // 长度143dp，宽度11dp
    opacityParams.setMargins(dpToPx(-65, context), 0, dpToPx(-50, context), 0); // 调整边距
    opacitySeekBar.setLayoutParams(opacityParams);
    mainContainer.addView(opacitySeekBar);

    // 最右边的控件大小滑动条
    SeekBar sizeSeekBar = new SeekBar(context);
    sizeSeekBar.setMax(1000);
    sizeSeekBar.setProgress(mainButton.getLayoutParams().width);
    sizeSeekBar.setRotation(90); // 旋转90度
    LinearLayout.LayoutParams sizeSeekBarParams = new LinearLayout.LayoutParams(dpToPx(143, context), dpToPx(11, context)); // 长度143dp，宽度11dp
    sizeSeekBarParams.setMargins(dpToPx(-65, context), 0, dpToPx(20, context), 0); // 添加右边距
    sizeSeekBar.setLayoutParams(sizeSeekBarParams);
    mainContainer.addView(sizeSeekBar);

    // 获取按钮的 LayoutParams（确保获取到正确的值）
    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mainButton.getLayoutParams();

    // 监听大小滑动条的变化，调整按钮大小
    sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            layoutParams.width = progress;
            layoutParams.height = progress;
            mainButton.setLayoutParams(layoutParams); // 更新按钮布局参数
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    });

    // 设置透明度滑动条的监听器
    opacitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // 仅更新透明度，不改变位置、大小或颜色
            mainButton.getBackground().setAlpha(progress);

            // 保存配置时传递 layoutParams
            saveConfiguration(mainButton, layoutParams.leftMargin, layoutParams.topMargin, layoutParams.width, Color.RED, progress, mainButton.getText().toString()); // 保存透明度配置
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    });

    // 颜色深浅选择方块的触摸事件
    // 颜色深浅选择方块的触摸事件
colorShadeView.setOnTouchListener((v, event) -> {
    if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (x >= 0 && x < colorShadeView.getWidth() && y >= 0 && y < colorShadeView.getHeight()) {
            Bitmap bitmap = ((BitmapDrawable) colorShadeView.getDrawable()).getBitmap();
            int color = bitmap.getPixel(x, y);

            // 获取当前按钮背景的透明度（alpha）
            int alpha = mainButton.getBackground().getAlpha();

            // 设置新颜色，并保持原透明度
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.OVAL);
            drawable.setColor(color); // 设置新颜色
            drawable.setAlpha(alpha); // 保持原透明度
            mainButton.setBackground(drawable);

            // 保存颜色配置，并传递当前透明度
            saveConfiguration(mainButton, layoutParams.leftMargin, layoutParams.topMargin, layoutParams.width, color, alpha, mainButton.getText().toString());
        }
    }
    return true;
});


    return mainContainer;
}



// dp 转 px 辅助方法
private int dpToPx(int dp, Context context) {
    return Math.round(dp * (context.getResources().getDisplayMetrics().density));
}

public Drawable getSmoothRainbowGradientDrawable() {
    int[] colors = {
        Color.RED, Color.parseColor("#FF7F00"), 
        Color.YELLOW, Color.GREEN, Color.CYAN, 
        Color.BLUE, Color.parseColor("#8B00FF"), 
        Color.RED
    };
    GradientDrawable gradient = new GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT, colors  // 改为LEFT_RIGHT以实现从左到右的渐变
    );
    gradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    return gradient;
}

public Bitmap createShadeBitmap(int width, int height, int baseColor) {
    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint();

    // 渐变效果：白色 -> baseColor -> 黑色，从左上到右下
    int[] colors = {Color.WHITE, baseColor, Color.BLACK};
    LinearGradient gradient = new LinearGradient(0, 0, width, height, colors, null, Shader.TileMode.CLAMP);
    paint.setShader(gradient);
    canvas.drawRect(0, 0, width, height, paint);

    return bitmap;
}




    
    
    
    
    
    
    
    public int getColorFromTouch(int x, int y) {
        if (colorPickerBitmap != null && x >= 0 && y >= 0 && x < colorPickerBitmap.getWidth() && y < colorPickerBitmap.getHeight()) {
            return colorPickerBitmap.getPixel(x, y);
        }
        return Color.WHITE;
    }

    public void setButtonColor(Button mainButton, int color) {
        GradientDrawable shape = (GradientDrawable) mainButton.getBackground();
        shape.setColor(color);
    }

    private void saveConfiguration(Button mainButton, int leftMargin, int topMargin, int size, int color, int opacity, String buttonName) {
    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mainButton.getLayoutParams();

    // 创建一个 JSONObject 对象来存储按钮配置
    JSONObject buttonData = new JSONObject();
    try {
        buttonData.put("leftMargin", leftMargin);
        buttonData.put("topMargin", topMargin);
        buttonData.put("size", size);
        buttonData.put("color", color);
        buttonData.put("opacity", opacity);
    } catch (Exception e) {
        e.printStackTrace();
    }

    // 将配置信息存储到 SharedPreferences 中
    SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(CONFIG_KEY + buttonName, buttonData.toString()); // 使用按钮名称作为唯一标识
    editor.apply();
   }

    
    public void loadConfiguration() {
    SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    for (int i = 0; i < rootView.getChildCount(); i++) {
        View view = rootView.getChildAt(i);
        if (view instanceof Button) {
            String buttonName = ((Button) view).getText().toString(); // 获取按钮名称
            loadButtonConfiguration((Button) view, (FrameLayout.LayoutParams) view.getLayoutParams(), buttonName);
        }
    }
}

private void loadButtonConfiguration(Button button, FrameLayout.LayoutParams layoutParams, String buttonName) {
    SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    String buttonDataString = preferences.getString(CONFIG_KEY + buttonName, null); // 使用按钮名称作为唯一标识加载配置
    if (buttonDataString != null) {
        try {
            // 解析存储的配置数据
            JSONObject buttonData = new JSONObject(buttonDataString);

            // 读取并设置按钮位置（保持不变）
            layoutParams.leftMargin = buttonData.getInt("leftMargin");
            layoutParams.topMargin = buttonData.getInt("topMargin");

            // 读取并设置按钮大小（保持不变）
            int size = buttonData.getInt("size");
            layoutParams.width = size; // 设置宽度
            layoutParams.height = size; // 设置高度

            // 设置颜色
            int color = buttonData.getInt("color");
            ((GradientDrawable) button.getBackground()).setColor(color);

            // 设置透明度
            int opacity = buttonData.getInt("opacity");
            button.getBackground().setAlpha(opacity);

            // 应用更新的布局参数
            button.setLayoutParams(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




public interface OnButtonTouchListener {
    default void onButtonDown() {
        // 默认空实现
    }

    default void onButtonUp() {
        // 默认空实现
    }

    default void onButtonMove(int x, int y) {
        // 默认空实现
    }
}

}
