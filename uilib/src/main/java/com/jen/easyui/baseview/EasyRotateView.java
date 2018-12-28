package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyLog;
import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;
import com.jen.easyui.util.EasyDisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2018/12/06.
 * 说明：目前只支持0/90度文字(布局只设置2两颜色，多种颜色请调用appendText)
 * 暂时不支持换行显示
 */
public class EasyRotateView extends View {
    private TextPaint textPaint;
    private StaticLayout staticLayout;
    private DrawClipRect drawXY = new DrawClipRect();

    private String textDefault;
    private int textSizeDefault;
    private int textColorDefault;
    private int degree;

    private String totalText;
    private final List<Span> mDefault = new ArrayList<>();
    private final List<Span> mSpans = new ArrayList<>();
    private int spanTextColor;
    private int spanIndexStart;
    private int spanIndexEnd;

    private int layoutWidth;
    private int layoutHeight;

    private int screenWidth;
    private float baseline;
    //    private float textHeight;
    private float drawY;
    private int widthSpec;
    private int heightSpec;

    private class Span {
        public String text;
        public int color;
    }

    private class DrawClipRect {
        public float l;
        public float r;
        public float t;
        public float b;
    }

    public EasyRotateView(Context context) {
        super(context);
    }

    public EasyRotateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public EasyRotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }


    private void initAttrs(AttributeSet attrs) {
        int txtSize = (int) EasyDensityUtil.dp2px(14);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EasyRotateView);
        textDefault = a.getString(R.styleable.EasyRotateView_rotateText);
        textSizeDefault = a.getDimensionPixelSize(R.styleable.EasyRotateView_rotateTextSize, txtSize);
        textColorDefault = a.getColor(R.styleable.EasyRotateView_rotateTextColor, 0xff000000);
        degree = a.getInt(R.styleable.EasyRotateView_rotateDegree, 90);

        spanTextColor = a.getColor(R.styleable.EasyRotateView_rotateSpanTextColor, 0xff000000);
        spanIndexStart = a.getInt(R.styleable.EasyRotateView_rotateSpanIndexStart, -1);
        spanIndexEnd = a.getInt(R.styleable.EasyRotateView_rotateSpanIndexEnd, -1);

        layoutWidth = a.getLayoutDimension(R.styleable.EasyRotateView_android_layout_width, 0);
        layoutHeight = a.getLayoutDimension(R.styleable.EasyRotateView_android_layout_height, 0);

        screenWidth = EasyDisplayUtil.getScreenWidth(getContext());
        a.recycle();
        init();
    }

    private void init() {
        if (textDefault == null) {
            textDefault = "";
        }

        textPaint = new TextPaint();
        textPaint.setColor(textColorDefault);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSizeDefault);
        StringBuilder builder = new StringBuilder(textDefault);
        for (int i = 0; i < mSpans.size(); i++) {
            builder.append(mSpans.get(i).text);
        }
        totalText = builder.toString();

        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        baseline = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        Rect rect = new Rect();
        textPaint.getTextBounds(totalText, 0, totalText.length(), rect);
//        textHeight = rect.bottom;

        if (widthSpec == 0 || heightSpec == 0) {
            if (degree == 0) {//0度
                heightSpec = fontMetrics.bottom - fontMetrics.top;
                widthSpec = rect.left + rect.right + 2;
            } else if (degree % 90 == 0) {//90度
                widthSpec = fontMetrics.bottom - fontMetrics.top;
                heightSpec = rect.left + rect.right + 2;
            }
        }

        mDefault.clear();
        if (spanIndexStart >= 0 & spanIndexEnd > 0 && spanIndexEnd <= textDefault.length()
                && spanIndexEnd > spanIndexStart) {
            if (spanIndexStart > 0) {
                addDefault(textDefault.substring(0, spanIndexStart), textColorDefault);
            }
            addDefault(textDefault.substring(spanIndexStart, spanIndexEnd), spanTextColor);
            if (spanIndexEnd < textDefault.length()) {
                addDefault(textDefault.substring(spanIndexEnd, textDefault.length()), textColorDefault);
            }
        } else {
            addDefault(textDefault, textColorDefault);
        }
    }

    private void addDefault(String text, int color) {
        if (text == null || text.length() == 0) {
            return;
        }
        Span span = new Span();
        span.text = text;
        span.color = color;
        mDefault.add(span);
    }

    private void addSpan(String text, int color) {
        if (text == null || text.length() == 0) {
            return;
        }
        Span span = new Span();
        span.text = text;
        span.color = color;
        mSpans.add(span);
    }

    /**
     * 最后要更新
     */
    public void update() {
        staticLayout = null;
        init();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (layoutWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
            if (degree == 0 || degree % 90 == 0) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSpec, MeasureSpec.EXACTLY);
            }
        }
        if (layoutHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            if (degree == 0 || degree % 90 == 0) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSpec + 5, MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        EasyLog.d("left=" + left + " top=" + top + " right=" + right + " bottom=" + bottom);
        EasyLog.d("widthSpec=" + widthSpec);
        if (screenWidth < right) {
            widthSpec = screenWidth - left;
            requestLayout();
            invalidate();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    requestLayout();
//                    invalidate();
//                }
//            }, 1000);
            return;
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(degree);

        if (staticLayout == null) {
            staticLayout = new StaticLayout(totalText, textPaint, getWidth(),
                    Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        }
        if (degree == 0) {//0度
            drawXY.l = 1;
            drawXY.t = 0;
            drawXY.b = heightSpec;
            for (int i = 0; i < mDefault.size(); i++) {
                Span span = mDefault.get(i);
                drawXY.r = drawXY.l + getTextWidth(span.text);
                drawXY = drawText0(canvas, span, drawXY);
            }
            for (int i = 0; i < mSpans.size(); i++) {
                Span span = mSpans.get(i);
                drawXY = drawText0(canvas, span, drawXY);
            }
        } else if (degree % 90 == 0) {//90度
            drawY = (getWidth() - widthSpec) / 2 + baseline;
            float x = 1;
            for (int i = 0; i < mDefault.size(); i++) {
                Span span = mDefault.get(i);
                x = drawText90(canvas, span, x);
            }
            for (int i = 0; i < mSpans.size(); i++) {
                Span span = mSpans.get(i);
                x = drawText90(canvas, span, x);
            }
        }
    }

    private float getTextWidth(String text) {
        Rect rect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), rect);
        return rect.right;
    }

    private DrawClipRect drawText0(Canvas canvas, Span span, DrawClipRect drawXY) {
        textPaint.setColor(span.color);

        canvas.save();
        RectF rectF = new RectF(drawXY.l, drawXY.t, drawXY.r, drawXY.b);
        canvas.clipRect(rectF);
        staticLayout.draw(canvas);
        canvas.restore();

        if (drawXY.l < getWidth() && getWidth() < drawXY.r) {
            Span spanCut = new Span();
            spanCut.color = span.color;
            for (int i = 0; i < span.text.length(); i++) {
                Rect rect2 = new Rect();
                textPaint.getTextBounds(span.text, 0, i + 1, rect2);
                if (rect2.right + drawXY.l > getWidth()) {
                    spanCut.text = span.text.substring(i, span.text.length());
                    drawXY.l = 1;
                    drawXY.r = drawXY.l + getTextWidth(span.text);
                    drawXY.t = drawXY.t + heightSpec;
                    drawXY.b = drawXY.t + heightSpec;
                    break;
                }
            }
            return drawText0(canvas, spanCut, drawXY);
        }
        drawXY.l = drawXY.r;
        return drawXY;
    }

    private float drawText90(Canvas canvas, Span span, float x) {
        canvas.save();
        textPaint.setColor(span.color);
        canvas.drawText(span.text, x, -drawY, textPaint);
        canvas.restore();

        Rect rect = new Rect();
        textPaint.getTextBounds(span.text, 0, span.text.length(), rect);
        x = rect.right + x;
        return x;
    }

    public String getText() {
        return textDefault == null ? "" : textDefault;
    }

    public void setText(String textDefault) {
        this.textDefault = textDefault;
    }

    public int getTextSize() {
        return textSizeDefault;
    }

    public void setTextSize(int textSizeDefault) {
        this.textSizeDefault = textSizeDefault;
    }

    public int getTextColor() {
        return textColorDefault;
    }

    public void setTextColor(int textColorDefault) {
        this.textColorDefault = textColorDefault;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getSpanTextColor() {
        return spanTextColor;
    }

    public void setSpanTextColor(int spanTextColor) {
        this.spanTextColor = spanTextColor;
    }

    public int getSpanIndexStart() {
        return spanIndexStart;
    }

    public void setSpanIndexStart(int spanIndexStart) {
        this.spanIndexStart = spanIndexStart;
    }

    public int getSpanIndexEnd() {
        return spanIndexEnd;
    }

    public void setSpanIndexEnd(int spanIndexEnd) {
        this.spanIndexEnd = spanIndexEnd;
    }

    public void appendText(String text, int color) {
        addSpan(text, color);
    }
}