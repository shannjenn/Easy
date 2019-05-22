package com.jen.easytest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jen.easyui.util.EasyDensityUtil;

public class DrawViewTest extends View {
    /**
     * 虚线的方向
     */
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    /**
     * 默认为水平方向
     */
    public static final int DEFAULT_DASH_ORIENTATION = ORIENTATION_HORIZONTAL;
    /**
     * 间距宽度
     */
    private float dashWidth = 5;
    /**
     * 线段高度
     */
    private float lineHeight = 2;
    /**
     * 线段宽度
     */
    private float lineWidth = 50;
    /**
     * 线段颜色
     */
    private int lineColor = 0xffff0000;
    private int dashOrientation;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int widthSize = 500;
    private int heightSize = 500;

    public DrawViewTest(Context context) {
        super(context);
        init();
    }

    public DrawViewTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawViewTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DashView);
//        dashWidth = typedArray.getDimension(R.styleable.DashView_dashWidth, DEFAULT_DASH_WIDTH);
//        lineHeight = typedArray.getDimension(R.styleable.DashView_lineHeight, DEFAULT_LINE_HEIGHT);
//        lineWidth = typedArray.getDimension(R.styleable.DashView_lineWidth, DEFAULT_LINE_WIDTH);
//        lineColor = typedArray.getColor(R.styleable.DashView_lineColor, DEFAULT_LINE_COLOR);
//        dashOrientation = typedArray.getInteger(R.styleable.DashView_dashOrientation, DEFAULT_DASH_ORIENTATION);
//
        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(lineHeight);
//        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        heightSize = MeasureSpec.getSize(heightMeasureSpec - getPaddingTop() - getPaddingBottom());
//        Log.d(TAG, "onMeasure: " + widthSize + "----" + heightSize);
//        Log.d(TAG, "dashOrientation: " + dashOrientation);
//        if (dashOrientation == ORIENTATION_HORIZONTAL) {
//            //不管在布局文件中虚线高度设置为多少，虚线的高度统一设置为实体线段的高度
//            setMeasuredDimension(widthSize, (int) lineHeight);
//        } else {
//            setMeasuredDimension((int) lineHeight, heightSize);
//        }

    }
    Paint paint = new Paint();
    Path path = new Path();
    PathEffect pathEffect = new DashPathEffect(new float[]{5,5,5,5},1);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawVerticalLine(canvas);
        drawHorizontalLine(canvas);
//        switch (dashOrientation) {
//            case ORIENTATION_VERTICAL:
//                break;
//            default:
//        }


        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.BLUE);
        paint.setPathEffect(pathEffect);

        path.moveTo(10,0);
        path.lineTo(10, EasyDensityUtil.dp2pxFloat(600));
        canvas.drawPath(path, paint);
    }

    /**
     * 画水平方向虚线
     *
     * @param canvas
     */
    public void drawHorizontalLine(Canvas canvas) {
        float totalWidth = 0;
        canvas.save();
        float[] pts = {0, 0, lineWidth, 0};
        //在画线之前需要先把画布向下平移办个线段高度的位置，目的就是为了防止线段只画出一半的高度
        //因为画线段的起点位置在线段左下角
        canvas.translate(0, lineHeight / 2+300);
        while (totalWidth <= widthSize) {
            canvas.drawLines(pts, mPaint);
            canvas.translate(lineWidth + dashWidth, 0);
            totalWidth += lineWidth + dashWidth;
        }
        canvas.restore();
    }

    /**
     * 画竖直方向虚线
     *
     * @param canvas
     */
    public void drawVerticalLine(Canvas canvas) {
        float totalWidth = 0;
        canvas.save();
        float[] pts = {0, 0, 0, lineWidth};
        //在画线之前需要先把画布向右平移半个线段高度的位置，目的就是为了防止线段只画出一半的高度
        //因为画线段的起点位置在线段左下角
        canvas.translate(lineHeight / 2, 0);
        while (totalWidth <= heightSize) {
            canvas.drawLines(pts, mPaint);
            canvas.translate(0, lineWidth + dashWidth);
            totalWidth += lineWidth + dashWidth;
        }
        canvas.restore();
    }
}
