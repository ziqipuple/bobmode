package com.ld.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/** 使用多个 List 记录不同形状的绘制参数，不使用额外的“构造函数类”。 仅通过方法将需要绘制的形状参数存储进来，然后在 onDraw() 中一次性绘制。 */
public class DrawView extends View {

    private Paint paint; // 画笔

    // --------------------- 1. 矩形参数的存储 ---------------------
    // 我们用 List 来存储一批矩形的参数
    // (left, top, right, bottom, color, strokeWidth)
    private final List<Float> rectLefts = new ArrayList<>();
    private final List<Float> rectTops = new ArrayList<>();
    private final List<Float> rectRights = new ArrayList<>();
    private final List<Float> rectBottoms = new ArrayList<>();
    private final List<Integer> rectColors = new ArrayList<>();
    private final List<Float> rectStrokes = new ArrayList<>();

    // --------------------- 2. 圆形参数的存储 ---------------------
    // (centerX, centerY, radius, color, strokeWidth)
    private final List<Float> circleCenterXs = new ArrayList<>();
    private final List<Float> circleCenterYs = new ArrayList<>();
    private final List<Float> circleRadiuses = new ArrayList<>();
    private final List<Integer> circleColors = new ArrayList<>();
    private final List<Float> circleStrokes = new ArrayList<>();

    // --------------------- 3. 线段参数的存储 ---------------------
    // (startX, startY, endX, endY, color, strokeWidth)
    private final List<Float> lineStartXs = new ArrayList<>();
    private final List<Float> lineStartYs = new ArrayList<>();
    private final List<Float> lineEndXs = new ArrayList<>();
    private final List<Float> lineEndYs = new ArrayList<>();
    private final List<Integer> lineColors = new ArrayList<>();
    private final List<Float> lineStrokes = new ArrayList<>();
    // 添加需要存储的文字参数
    // (text, x, y, color, textSize)
    private final List<String> texts = new ArrayList<>();
private final List<Float> textXs = new ArrayList<>();
private final List<Float> textYs = new ArrayList<>();
private final List<Integer> textColors = new ArrayList<>();
private final List<Float> textSizes = new ArrayList<>();
private final List<Float> textThicknesses = new ArrayList<>();
private final List<Float> textStrokeWidths = new ArrayList<>();


    // ----------------------------------------------------------------------

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化：主要初始化画笔
     */
    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
    }

    /**
     * 在此方法中，遍历上面定义的各种 List，将其中的形状绘制出来
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // --------------------- 绘制矩形 ---------------------
        int rectCount = rectLefts.size();
        for (int i = 0; i < rectCount; i++) {
            paint.setColor(rectColors.get(i));
            paint.setStrokeWidth(rectStrokes.get(i));

            float left = rectLefts.get(i);
            float top = rectTops.get(i);
            float right = rectRights.get(i);
            float bottom = rectBottoms.get(i);

            canvas.drawRect(left, top, right, bottom, paint);
        }

        // --------------------- 绘制圆形 ---------------------
        // --------------------- 绘制圆形 ---------------------
int circleCount = circleCenterXs.size();
for (int i = 0; i < circleCount; i++) {
    paint.setColor(circleColors.get(i));
    paint.setStrokeWidth(circleStrokes.get(i));

    // **在这里强制指定圆的 Style = STROKE**
    paint.setStyle(Paint.Style.STROKE);

    float cx = circleCenterXs.get(i);
    float cy = circleCenterYs.get(i);
    float r  = circleRadiuses.get(i);

    canvas.drawCircle(cx, cy, r, paint);
}


        // --------------------- 绘制线段 ---------------------
        int lineCount = lineStartXs.size();
        for (int i = 0; i < lineCount; i++) {
            paint.setColor(lineColors.get(i));
            paint.setStrokeWidth(lineStrokes.get(i));

            float sx = lineStartXs.get(i);
            float sy = lineStartYs.get(i);
            float ex = lineEndXs.get(i);
            float ey = lineEndYs.get(i);

            canvas.drawLine(sx, sy, ex, ey, paint);
        }
        // --------------------- 绘制文字 ---------------------
    int textCount = texts.size();
    for (int i = 0; i < textCount; i++) {
        float x = textXs.get(i);
        float y = textYs.get(i);
        int textColor = textColors.get(i);
        float textSize = textSizes.get(i);
        float thickness = textThicknesses.get(i);
        float strokeWidth = textStrokeWidths.get(i); // 获取描边宽度

        // 设置文字大小
        paint.setTextSize(textSize);

        // **绘制黑色描边**
        paint.setColor(Color.BLACK); // 设置描边颜色为黑色
        paint.setStyle(Paint.Style.STROKE); // 描边模式
        paint.setStrokeWidth(strokeWidth); // 使用动态的描边宽度

        canvas.drawText(texts.get(i), x, y, paint); // 绘制文字的黑色描边

        // **绘制文字填充颜色**
        paint.setColor(textColor); // 设置文字颜色
        paint.setStyle(Paint.Style.FILL); // 填充模式

        canvas.drawText(texts.get(i), x, y, paint); // 绘制文字的填充颜色
        }
    }

    // ----------------------------------------------------------------------
    // 接下来是对外提供的几个方法，用于“添加”各种形状

    /**
     * 添加矩形
     *
     * @param left   左 x
     * @param top    上 y
     * @param right  右 x
     * @param bottom 下 y
     * @param color  颜色
     * @param stroke 线条宽度
     */
    public void addRectangle(float left, float top, float right, float bottom, int color, float stroke) {
        rectLefts.add(left);
        rectTops.add(top);
        rectRights.add(right);
        rectBottoms.add(bottom);
        rectColors.add(color);
        rectStrokes.add(stroke);
        invalidate(); // 通知重绘
    }

    /**
     * 添加圆形
     *
     * @param cx     圆心 x
     * @param cy     圆心 y
     * @param radius 半径
     * @param color  颜色
     * @param stroke 线条宽度
     */
    public void addCircle(float cx, float cy, float radius, int color, float stroke) {
        circleCenterXs.add(cx);
        circleCenterYs.add(cy);
        circleRadiuses.add(radius);
        circleColors.add(color);
        circleStrokes.add(stroke);
        invalidate();
    }

    /**
     * 添加线段
     *
     * @param sx     起点 x
     * @param sy     起点 y
     * @param ex     终点 x
     * @param ey     终点 y
     * @param color  颜色
     * @param stroke 线条宽度
     */
    public void addLine(float sx, float sy, float ex, float ey, int color, float stroke) {
        lineStartXs.add(sx);
        lineStartYs.add(sy);
        lineEndXs.add(ex);
        lineEndYs.add(ey);
        lineColors.add(color);
        lineStrokes.add(stroke);
        invalidate();
    }
/**
 * 添加文字
 *
 * @param text        要绘制的文字
 * @param x           起点 x
 * @param y           起点 y
 * @param color       填充颜色
 * @param textSize    字体大小
 * @param thickness   字体粗细（内部填充粗细）
 * @param strokeWidth 描边宽度
 */
public void addText(String text, float x, float y, int color, float textSize, float thickness, float strokeWidth) {
    texts.add(text);
    textXs.add(x);
    textYs.add(y);
    textColors.add(color);
    textSizes.add(textSize);
    textThicknesses.add(thickness);
    textStrokeWidths.add(strokeWidth);
    invalidate(); // 通知重绘
}

    public void addTextWithFormatting(int rankId, float x, float y, int color, float textSize, float thickness, float strokeWidth) {
    String displayText;

    if (rankId < 10000) {
        // 如果小于 10,000，直接显示原始值
        displayText = String.valueOf(rankId);
    } else if (rankId < 100000000) {
        // 如果大于等于 10,000 且小于 100,000,000
        int wanPart = rankId / 10000; // 整万部分
        int remainder = rankId % 10000; // 零头部分
        displayText = remainder == 0 
            ? String.format("%d万", wanPart) 
            : String.format("%d万%d", wanPart, remainder); // 格式为“多少万 零头”
    } else {
        // 如果大于等于 100,000,000
        int yiPart = rankId / 100000000; // 整亿部分
        int wanPart = (rankId % 100000000) / 10000; // 亿后面的整万部分
        int remainder = rankId % 10000; // 零头部分

        if (wanPart == 0 && remainder == 0) {
            // 如果没有万和零头
            displayText = String.format("%d亿", yiPart);
        } else if (remainder == 0) {
            // 如果只有万，没有零头
            displayText = String.format("%d亿%d万", yiPart, wanPart);
        } else {
            // 全部显示
            displayText = String.format("%d亿%d万%d", yiPart, wanPart, remainder);
        }
    }

    // 调用 addText 方法添加文字
    addText(displayText, x, y, color, textSize, thickness, strokeWidth);
}

    /**
     * 清除已保存的所有形状
     */
    public void clearAllShapes() {
        // 清空矩形参数
        rectLefts.clear();
        rectTops.clear();
        rectRights.clear();
        rectBottoms.clear();
        rectColors.clear();
        rectStrokes.clear();

        // 清空圆形参数
        circleCenterXs.clear();
        circleCenterYs.clear();
        circleRadiuses.clear();
        circleColors.clear();
        circleStrokes.clear();

        // 清空线段参数
        lineStartXs.clear();
        lineStartYs.clear();
        lineEndXs.clear();
        lineEndYs.clear();
        lineColors.clear();
        lineStrokes.clear();

        // 清空文字参数
    texts.clear();
    textXs.clear();
    textYs.clear();
    textColors.clear();
    textSizes.clear();
        invalidate(); // 通知重绘
    }
}
