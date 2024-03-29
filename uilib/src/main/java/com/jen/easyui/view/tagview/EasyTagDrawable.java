package com.jen.easyui.view.tagview;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jen.easyui.util.DensityUtil;

/**
 * EasyTagDrawable
 * 作者：ShannJenn
 * 时间：2018/06/20.
 */

public class EasyTagDrawable extends Drawable {
    private Paint backgroundPaint;
    private Paint strokePaint;
    private Paint textPaint;

    private int backgroundWidth;
    private int backgroundHeight = 18;//dp
    private int backgroundColor = 0xffcccccc;
    private int strokeColor = 0xff666666;
    private float strokeWidth = 0.5f;//dp
    private int textColor = 0xff333333;
    private String text = "";
    private float textSize = 14;//sp

    private final int PADDING = 20;
    private final int MARGIN = 4;

    public static EasyTagDrawable build() {
        return new EasyTagDrawable();
    }


    private EasyTagDrawable() {
    }

    public EasyTagDrawable init() {
        //背景画笔
        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(backgroundColor);

        //边框画笔
        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(strokeColor);
        strokePaint.setStrokeWidth(DensityUtil.sp2pxFloat(strokeWidth));

        //文字画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(DensityUtil.sp2pxFloat(textSize));

        //测量文字的宽高
        Rect textRect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textRect);
        //背景宽度要比文字宽度宽一些
        backgroundWidth = textRect.width() + (textRect.width() / text.length());
        //背景高度是文字高度的1.5倍
//        backgroundHeight = (int) (textRect.height() * 1.5);
        backgroundHeight = DensityUtil.dp2pxInt(backgroundHeight);

        return this;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect rect = getBounds();
        //画背景（圆角矩形框）
        RectF rectF = new RectF(getBounds());
        rectF.bottom = backgroundHeight + PADDING;
        //留出空白，以免添加多个tag时出现挤在一起的情况
        rectF.left = rectF.left + MARGIN;
        rectF.right = rectF.right - MARGIN / 2;
        rectF.top = rectF.top + MARGIN;
        rectF.bottom = rectF.bottom - MARGIN / 2;
        canvas.translate(rect.left, rect.top + 15);
        canvas.drawRoundRect(rectF, 360, 360, backgroundPaint);
        canvas.drawRoundRect(rectF, 360, 360, strokePaint);
        int count = canvas.save();
        canvas.translate(rect.left, rect.top);

        //画文字（居中）
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离
        int baseLineY = (int) (rectF.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        canvas.drawText(text, rectF.centerX(), baseLineY, textPaint);
        canvas.restoreToCount(count);
    }

    @Override
    public void setAlpha(int i) {
        textPaint.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        textPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return backgroundWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return backgroundHeight;
    }

    public void setBounds() {
        setBounds(0, 0, 0, 0);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        //设置drawable的宽高
        super.setBounds(left, top, backgroundWidth + PADDING, backgroundHeight + PADDING);
    }

    /**
     * 改变tag的状态
     */
    public void invalidateDrawable() {
        init();
        invalidateSelf();
    }


    public int getBackgroundColor() {
        return backgroundColor;
    }

    public EasyTagDrawable setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public int getTextColor() {
        return textColor;
    }

    public EasyTagDrawable setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public String getText() {
        return text;
    }

    public EasyTagDrawable setText(String text) {
        if (text != null) {
            this.text = text.trim();//去掉两边空格
        }
        return this;
    }

    public float getTextSize() {
        return textSize;
    }

    public EasyTagDrawable setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public EasyTagDrawable setBackgroundHeight(int backgroundHeight) {
        this.backgroundHeight = backgroundHeight;
        return this;
    }

    public EasyTagDrawable setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    public EasyTagDrawable setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }
}