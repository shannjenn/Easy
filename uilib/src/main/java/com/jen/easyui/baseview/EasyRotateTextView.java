package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;

/**
 * 作者：ShannJenn
 * 时间：2018/12/06.
 * 说明：
 */
public class EasyRotateTextView extends View {
    private Paint paint;
    private String text;
    private int textSize;
    private int textColor;
    private int degree;

    private int layoutWidth;
    private int layoutHeight;

    private float baseline;
    private int widthSpec;
    private int heightSpec;

    public EasyRotateTextView(Context context) {
        super(context);
    }

    public EasyRotateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public EasyRotateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }


    private void initAttrs(AttributeSet attrs) {
        int txtSize = (int) EasyDensityUtil.dp2px(14);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EasyRotateTextView);
        text = a.getString(R.styleable.EasyRotateTextView_rotateText);
        textSize = a.getDimensionPixelSize(R.styleable.EasyRotateTextView_rotateTextSize, txtSize);
        textColor = a.getColor(R.styleable.EasyRotateTextView_rotateTextColor, 0xff000000);
        degree = a.getInt(R.styleable.EasyRotateTextView_rotateDegree, 90);

        layoutWidth = a.getLayoutDimension(R.styleable.EasyRotateTextView_android_layout_width, 0);
        layoutHeight = a.getLayoutDimension(R.styleable.EasyRotateTextView_android_layout_height, 0);

        a.recycle();
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(textColor);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);

        if (text == null) {
            text = "";
        }
        //计算baseline
        //dy 代表的是：高度的一半到 baseLine的距离
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // top 是一个负值  bottom 是一个正值    top，bttom的值代表是  bottom是baseLine到文字底部的距离（正值）
        // 必须要清楚的，可以自己打印就好
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        baseline = getHeight() / 2 + dy;

        widthSpec = fontMetrics.bottom - fontMetrics.top;

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        heightSpec = rect.width() + 2;
//        int h = rect.height();
    }

    private void update() {
        init();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (layoutWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
            if (degree % 90 == 0) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSpec, MeasureSpec.EXACTLY);
            }
        }
        if (layoutHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            if (degree % 90 == 0) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSpec, MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(degree);
//        baseline = 1000;
        canvas.drawText(text, 1, -baseline, paint);
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = text;
        update();
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        update();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        update();
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
        update();
    }
}