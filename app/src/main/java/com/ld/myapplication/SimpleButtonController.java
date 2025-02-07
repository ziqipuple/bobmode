package com.ld.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class SimpleButtonController {

    private static final String PREF_NAME = "button_config";
    private static final String BUTTON_CLONE_X_KEY = "button_clone_x";
    private static final String BUTTON_CLONE_Y_KEY = "button_clone_y";
    private static final String BUTTON_SHOOT_X_KEY = "button_shoot_x";
    private static final String BUTTON_SHOOT_Y_KEY = "button_shoot_y";
    private static final String BUTTON_JOYSTICK_X_KEY = "button_joystick_x";
    private static final String BUTTON_JOYSTICK_Y_KEY = "button_joystick_y";

    private Context context;
    private FrameLayout rootView;
    private Button buttonClone;
    private Button buttonShoot;
    private Button buttonJoystick;
    private SharedPreferences sharedPreferences;

    // 用于存储按钮位置的公开变量
    public int 分身x, 分身y;
    public int 吐球x, 吐球y;
    public int 摇杆x, 摇杆y;

    public SimpleButtonController(Context context, FrameLayout rootView) {
        this.context = context;
        this.rootView = rootView;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        createButtons();
    }

    private void createButtons() {
        // 创建“分身”按钮并加载位置配置
        buttonClone = createRoundButton("分身", dpToPx(30), Color.RED,
                sharedPreferences.getInt(BUTTON_CLONE_X_KEY, 100),
                sharedPreferences.getInt(BUTTON_CLONE_Y_KEY, 100));

        // 创建“吐球”按钮并加载位置配置
        buttonShoot = createRoundButton("吐球", dpToPx(30), Color.BLUE,
                sharedPreferences.getInt(BUTTON_SHOOT_X_KEY, 300),
                sharedPreferences.getInt(BUTTON_SHOOT_Y_KEY, 300));

        // 创建“摇杆”按钮并加载位置配置
        buttonJoystick = createRoundButton("摇杆", dpToPx(30), Color.GREEN,
                sharedPreferences.getInt(BUTTON_JOYSTICK_X_KEY, 500),
                sharedPreferences.getInt(BUTTON_JOYSTICK_Y_KEY, 500));
    }

    private Button createRoundButton(String text, int size, int color, int x, int y) {
        Button button = new Button(context);
        button.setText(text);
        
     // button.setTextColor(Color.WHITE); // 设置字体颜色为白色
        button.setTextSize(6); // 设置字体大小为16sp

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(color);
        button.setBackground(shape);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
        layoutParams.setMargins(x, y, 0, 0);
        layoutParams.gravity = Gravity.TOP | Gravity.START;
        rootView.addView(button, layoutParams);

        // 更新公开变量的初始位置
        if (text.equals("分身")) {
            分身x = x;
            分身y = y;
        } else if (text.equals("吐球")) {
            吐球x = x;
            吐球y = y;
        } else if (text.equals("摇杆")) {
            摇杆x = x;
            摇杆y = y;
        }

        button.setOnTouchListener(new View.OnTouchListener() {
            int offsetX;
            int offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        offsetX = (int) event.getRawX() - layoutParams.leftMargin;
                        offsetY = (int) event.getRawY() - layoutParams.topMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        layoutParams.leftMargin = (int) event.getRawX() - offsetX;
                        layoutParams.topMargin = (int) event.getRawY() - offsetY;
                        button.setLayoutParams(layoutParams);

                        // 更新按钮的公开位置变量
                        if (button == buttonClone) {
                            分身x = layoutParams.leftMargin;
                            分身y = layoutParams.topMargin;
                        } else if (button == buttonShoot) {
                            吐球x = layoutParams.leftMargin;
                            吐球y = layoutParams.topMargin;
                        } else if (button == buttonJoystick) {
                            摇杆x = layoutParams.leftMargin;
                            摇杆y = layoutParams.topMargin;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        saveButtonPosition(button, layoutParams.leftMargin, layoutParams.topMargin);
                        break;
                }
                return true;
            }
        });

        return button;
    }

    // 保存按钮位置
    private void saveButtonPosition(Button button, int x, int y) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (button == buttonClone) {
            editor.putInt(BUTTON_CLONE_X_KEY, x);
            editor.putInt(BUTTON_CLONE_Y_KEY, y);
        } else if (button == buttonShoot) {
            editor.putInt(BUTTON_SHOOT_X_KEY, x);
            editor.putInt(BUTTON_SHOOT_Y_KEY, y);
        } else if (button == buttonJoystick) {
            editor.putInt(BUTTON_JOYSTICK_X_KEY, x);
            editor.putInt(BUTTON_JOYSTICK_Y_KEY, y);
        }
        editor.apply();
    }

    // 调用接口方法来决定按钮的显示状态
    public void updateButtonVisibility(boolean buttonsVisible) {
        if (buttonsVisible) {
            buttonClone.setVisibility(View.VISIBLE);
            buttonShoot.setVisibility(View.VISIBLE);
            buttonJoystick.setVisibility(View.VISIBLE);
        } else {
            buttonClone.setVisibility(View.GONE);
            buttonShoot.setVisibility(View.GONE);
            buttonJoystick.setVisibility(View.GONE);
        }
    }

    public interface ButtonVisibilityProvider {
        boolean shouldShowButtons(); // 接口方法，返回布尔值
    }
    private int dpToPx(int dp) {
    float density = context.getResources().getDisplayMetrics().density;
    return (int) (dp * density + 0.5f);
}

}
