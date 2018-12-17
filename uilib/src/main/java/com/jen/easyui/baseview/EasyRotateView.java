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

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2018/12/06.
 * 说明：目前只支持0/90度文字(布局只设置2两颜色，多种颜色请调用appendText)
 * 暂时不支持换行显示
 */
public class EasyRotateView extends View {
    private Paint paint;
    private String textDefault;
    private int textSizeDefault;
    private int textColorDefault;
    private int degree;

    private final List<Span> mDefault = new ArrayList<>();
    private final List<Span> mSpans = new ArrayList<>();
    private int spanTextColor;
    private int spanIndexStart;
    private int spanIndexEnd;

    private int layoutWidth;
    private int layoutHeight;

    private float baseline;
    private float drawY;
    private int widthSpec;
    private int heightSpec;

    private class Span {
        public String text;
        public int color;
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

        a.recycle();
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(textColorDefault);
        paint.setAntiAlias(true);
        paint.setTextSize(textSizeDefault);

        if (textDefault == null) {
            textDefault = "";
        }
        StringBuilder builder = new StringBuilder(textDefault);
        for (int i = 0; i < mSpans.size(); i++) {
            builder.append(mSpans.get(i).text);
        }
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        baseline = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        Rect rect = new Rect();
        paint.getTextBounds(builder.toString(), 0, builder.length(), rect);

        if (degree == 0) {//0度
            heightSpec = fontMetrics.bottom - fontMetrics.top;
            widthSpec = rect.left + rect.right + 2;
        } else if (degree % 90 == 0) {//90度
            widthSpec = fontMetrics.bottom - fontMetrics.top;
            heightSpec = rect.left + rect.right + 2;
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
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSpec, MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(degree);
        if (degree == 0) {//0度
            drawY = getHeight() / 2 + baseline;
            float x = 1;
            for (int i = 0; i < mDefault.size(); i++) {
                Span span = mDefault.get(i);
                x = drawText0(canvas, span, x);
            }
            for (int i = 0; i < mSpans.size(); i++) {
                Span span = mSpans.get(i);
                x = drawText0(canvas, span, x);
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

    private float drawText0(Canvas canvas, Span span, float x) {
        canvas.save();
        paint.setColor(span.color);
        canvas.drawText(span.text, x, drawY, paint);
        canvas.restore();

        Rect rect = new Rect();
        paint.getTextBounds(span.text, 0, span.text.length(), rect);
        x = rect.right + x;
        return x;
    }

    private float drawText90(Canvas canvas, Span span, float x) {
        canvas.save();
        paint.setColor(span.color);
        canvas.drawText(span.text, x, -drawY, paint);
        canvas.restore();

        Rect rect = new Rect();
        paint.getTextBounds(span.text, 0, span.text.length(), rect);
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