package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easyui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2018/12/06.
 * 说明：目前只支持0/90度文字(布局只设置2两颜色，多种颜色请调用appendText)
 * 暂时不支持换行显示
 */
public class EasyRotateSpanTextView extends View {
    private TextPaint textPaint;
    private DrawClipRect drawXY = new DrawClipRect();

    private String text;
    private int textSize;
    private int textColorDefault;
    private int degree;

    private final List<Span> mSpans = new ArrayList<>();

    private int layoutWidth;
    private int layoutHeight;

    private float baseline;
    private float drawY;
    private int textWidth;
    private int textHeight;

    private int spanTextColor1;
    private int spanIndexStart1;
    private int spanIndexEnd1;

    private int spanTextColor2;
    private int spanIndexStart2;
    private int spanIndexEnd2;

    private int spanTextColor3;
    private int spanIndexStart3;
    private int spanIndexEnd3;

    private int spanTextColor4;
    private int spanIndexStart4;
    private int spanIndexEnd4;

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

    public EasyRotateSpanTextView(Context context) {
        super(context);
    }

    public EasyRotateSpanTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public EasyRotateSpanTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }


    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EasyRotateSpanTextView);
        text = a.getString(R.styleable.EasyRotateSpanTextView_rotateText);
        textSize = a.getDimensionPixelSize(R.styleable.EasyRotateSpanTextView_rotateTextSize, 38);
        textColorDefault = a.getColor(R.styleable.EasyRotateSpanTextView_rotateTextColorDefault, 0xff000000);
        degree = a.getInt(R.styleable.EasyRotateSpanTextView_rotateDegree, 90);

        spanTextColor1 = a.getColor(R.styleable.EasyRotateSpanTextView_rotateSpanTextColor1, textColorDefault);
        spanIndexStart1 = a.getInt(R.styleable.EasyRotateSpanTextView_rotateSpanIndexStart1, -1);
        spanIndexEnd1 = a.getInt(R.styleable.EasyRotateSpanTextView_rotateSpanIndexEnd1, -1);

        spanTextColor2 = a.getColor(R.styleable.EasyRotateSpanTextView_rotateSpanTextColor2, textColorDefault);
        spanIndexStart2 = a.getInt(R.styleable.EasyRotateSpanTextView_rotateSpanIndexStart2, -2);
        spanIndexEnd2 = a.getInt(R.styleable.EasyRotateSpanTextView_rotateSpanIndexEnd2, -2);

        spanTextColor3 = a.getColor(R.styleable.EasyRotateSpanTextView_rotateSpanTextColor3, textColorDefault);
        spanIndexStart3 = a.getInt(R.styleable.EasyRotateSpanTextView_rotateSpanIndexStart3, -3);
        spanIndexEnd3 = a.getInt(R.styleable.EasyRotateSpanTextView_rotateSpanIndexEnd3, -3);

        spanTextColor4 = a.getColor(R.styleable.EasyRotateSpanTextView_rotateSpanTextColor4, textColorDefault);
        spanIndexStart4 = a.getInt(R.styleable.EasyRotateSpanTextView_rotateSpanIndexStart4, -4);
        spanIndexEnd4 = a.getInt(R.styleable.EasyRotateSpanTextView_rotateSpanIndexEnd4, -4);

        layoutWidth = a.getLayoutDimension(R.styleable.EasyRotateSpanTextView_android_layout_width, 0);
        layoutHeight = a.getLayoutDimension(R.styleable.EasyRotateSpanTextView_android_layout_height, 0);
        a.recycle();

        init();
    }

    private void init() {
        if (text == null) {
            text = "";
        }
//        if (
//                spanIndexStart1 >= spanIndexEnd1
//                || spanIndexStart2 >= spanIndexEnd2
//                || spanIndexStart3 >= spanIndexEnd3
//                || spanIndexStart4 >= spanIndexEnd4
//
//                || spanIndexStart2 < spanIndexEnd1
//                || spanIndexStart3 < spanIndexEnd2
//                || spanIndexStart4 < spanIndexEnd3
//
//                || (spanIndexStart1 != -1 && spanIndexStart1 < 0)
//                spanIndexEnd4 > text.length()) {
//            Log.e(getClass().getName(), "初始化错误");
//            return;
//        }

        int end;
        int size;
        if (spanIndexStart4 != -4) {
            size = 4;
            end = spanIndexEnd4;
        } else if (spanIndexStart3 != -3) {
            size = 3;
            end = spanIndexEnd3;
        } else if (spanIndexStart2 != -2) {
            size = 2;
            end = spanIndexEnd2;
        } else if (spanIndexStart1 != -1) {
            size = 1;
            end = spanIndexEnd1;
        } else {
            size = 0;
            end = text.length();
        }

        mSpans.clear();
        for (int i = 1; i <= size; i++) {
            switch (i) {
                case 1: {
                    if (spanIndexStart1 > 0) {
                        addSpan(text.substring(0, spanIndexStart1), textColorDefault);
                    }
                    addSpan(text.substring(spanIndexStart1, spanIndexEnd1), spanTextColor1);
                    break;
                }
                case 2: {
                    if (spanIndexStart2 > spanIndexEnd1) {
                        addSpan(text.substring(spanIndexEnd1, spanIndexStart2), textColorDefault);
                    }
                    addSpan(text.substring(spanIndexStart2, spanIndexEnd2), spanTextColor2);
                    break;
                }
                case 3: {
                    if (spanIndexStart3 > spanIndexEnd2) {
                        addSpan(text.substring(spanIndexEnd2, spanIndexStart3), textColorDefault);
                    }
                    addSpan(text.substring(spanIndexStart3, spanIndexEnd3), spanTextColor3);
                    break;
                }
                case 4: {
                    if (spanIndexStart4 > spanIndexEnd3) {
                        addSpan(text.substring(spanIndexEnd3, spanIndexStart4), textColorDefault);
                    }
                    addSpan(text.substring(spanIndexStart4, spanIndexEnd4), spanTextColor4);
                    break;
                }
            }
        }
        if (size == 0) {
            addSpan(text, textColorDefault);
        }
        if (end < text.length()) {
            addSpan(text.substring(end), textColorDefault);
        }

        textPaint = new TextPaint();
        textPaint.setColor(textColorDefault);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);

        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        baseline = ((fontMetrics.bottom - fontMetrics.top) >> 1) - fontMetrics.bottom;
        int myTextWidth = (int) getTextWidth(textPaint, text);

        if (degree == 0) {//0度
            textHeight = fontMetrics.bottom - fontMetrics.top;
            textWidth = myTextWidth + 1;
        } else if (degree % 90 == 0) {//90度
            textWidth = fontMetrics.bottom - fontMetrics.top;
            textHeight = myTextWidth + 1;
        }
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
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int lines = 1;
        if (layoutWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
            if (degree == 0) {
                int widthMeasureSpec1 = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY);
                int widthMeasureSpec2 = MeasureSpec.makeMeasureSpec(textWidth, MeasureSpec.EXACTLY);
                if (widthMeasureSpec2 > widthMeasureSpec1) {
                    widthMeasureSpec = widthMeasureSpec1;
                    int temp = widthMeasureSpec2 - widthMeasureSpec1;
                    while (temp > 0) {
                        lines++;
                        temp = temp - widthMeasureSpec1;
                    }
                } else {
                    widthMeasureSpec = widthMeasureSpec2;
                }
            } else if (degree % 90 == 0) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(textWidth, MeasureSpec.EXACTLY);
            }
        }

        if (layoutHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            if (degree == 0) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(textHeight * lines, MeasureSpec.EXACTLY);
            } else if (degree % 90 == 0) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(textHeight + 5, MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        EasyLog.d("left=" + left + " top=" + top + " right=" + right + " bottom=" + bottom);
//        EasyLog.d("textWidth=" + textWidth);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(degree);
        if (degree == 0) {//0度
            drawXY.l = 1;
            drawXY.t = textHeight - 10;
            drawXY.b = drawXY.t + textHeight;
            for (int i = 0; i < mSpans.size(); i++) {
                Span span = mSpans.get(i);
                drawXY.r = drawXY.l + getTextWidth(textPaint, span.text);
                drawXY.l = drawText0(canvas, span, drawXY);
            }
        } else if (degree % 90 == 0) {//90度
            drawY = ((getWidth() - textWidth) >> 1) + baseline;
            float x = 1;
            for (int i = 0; i < mSpans.size(); i++) {
                Span span = mSpans.get(i);
                x = drawText90(canvas, span, x);
            }
        }
    }

    /**
     * measureText() 与 getTextBounds 区别
     * 1.二者返回结果确实不同，且 measureText() 返回结果会略微大于 getTextBounds() 所得到的宽度信息
     * 2.measureText() 会在文本的左右两侧加上一些额外的宽度，这部分额外的宽度叫做 Glyph's AdvanceX （具体应该是属于字型方面的范畴，我猜测这部分宽度是类似字间距之类的东西）
     * 3.getTextBounds() 返回的则是当前文本所需要的最小宽度，也就是整个文本外切矩形的宽度
     *
     * @param text .
     * @return .
     */
    private float getTextWidth(TextPaint paint, String text) {
//        Rect rect = new Rect();
//        textPaint.getTextBounds(text, 0, text.length(), rect);
//        return rect.right - rect.left;
        return paint.measureText(text);
    }

    private float drawText0(Canvas canvas, Span span, DrawClipRect drawXY) {
        if (getWidth() < drawXY.r) {//换行
            Span span2 = new Span();
            span2.color = span.color;
            for (int i = 0; i < span.text.length(); i++) {
                String myText = span.text.substring(0, i + 1);
                float myTextWidth = getTextWidth(textPaint, myText);
                if (myTextWidth + drawXY.l > getWidth()) {
                    if (i > 0) {
                        String str = span.text.substring(0, i);
                        float left = drawXY.l;
                        textPaint.setColor(span.color);
                        canvas.save();
                        canvas.drawText(str, left, drawXY.t, textPaint);
                        canvas.restore();
                    }
                    String str = span.text.substring(i);
                    float left = 1;
                    textPaint.setColor(span.color);
                    canvas.save();
                    canvas.drawText(str, left, drawXY.t + textHeight, textPaint);
                    canvas.restore();
                    break;
                }
            }
        } else {
            textPaint.setColor(span.color);
            canvas.save();
            canvas.drawText(span.text, drawXY.l, drawXY.t, textPaint);
            canvas.restore();
        }
        return drawXY.r;
    }

    private float drawText90(Canvas canvas, Span span, float x) {
        canvas.save();
        textPaint.setColor(span.color);
        canvas.drawText(span.text, x, -drawY, textPaint);
        canvas.restore();
        x = getTextWidth(textPaint, span.text) + x;
        return x;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public EasyRotateSpanTextView setText(String textDefault) {
        this.text = textDefault;
        return this;
    }

    public int getTextSize() {
        return textSize;
    }

    public EasyRotateSpanTextView setTextSize(int textSizeDefault) {
        this.textSize = textSizeDefault;
        return this;
    }

    public int getTextColorDefault() {
        return textColorDefault;
    }

    public EasyRotateSpanTextView setTextColorDefault(int textColorDefault) {
        this.textColorDefault = textColorDefault;
        return this;
    }

    public EasyRotateSpanTextView setSpanTextColor1(int spanTextColor1) {
        this.spanTextColor1 = spanTextColor1;
        return this;
    }

    public EasyRotateSpanTextView setSpanIndex1(int spanIndexStart1, int spanIndexEnd1) {
        this.spanIndexStart1 = spanIndexStart1;
        this.spanIndexEnd1 = spanIndexEnd1;
        return this;
    }

    public EasyRotateSpanTextView setSpanTextColor2(int spanTextColor2) {
        this.spanTextColor2 = spanTextColor2;
        return this;
    }

    public EasyRotateSpanTextView setSpanIndex2(int spanIndexStart2, int spanIndexEnd2) {
        this.spanIndexStart2 = spanIndexStart2;
        this.spanIndexEnd2 = spanIndexEnd2;
        return this;
    }

    public EasyRotateSpanTextView setSpanTextColor3(int spanTextColor3) {
        this.spanTextColor3 = spanTextColor3;
        return this;
    }

    public EasyRotateSpanTextView setSpanIndex3(int spanIndexStart3, int spanIndexEnd3) {
        this.spanIndexStart3 = spanIndexStart3;
        this.spanIndexEnd3 = spanIndexEnd3;
        return this;
    }

    public void setSpanTextColor4(int spanTextColor4) {
        this.spanTextColor4 = spanTextColor4;
    }

    public EasyRotateSpanTextView setSpanIndex4(int spanIndexStart4, int spanIndexEnd4) {
        this.spanIndexStart4 = spanIndexStart4;
        this.spanIndexEnd4 = spanIndexEnd4;
        return this;
    }
}