package com.jen.easyui.view.histogram;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class HistogramTest extends View {
    protected float viewHeight;//View高度
    protected float viewWidth;//宽度
    private HistogramFactory<Float> coordinate;
    private HistogramFactory<Float> candle;
    private HistogramConfig<Float> histogramConfig;

    public HistogramTest(Context context) {
        super(context);
        init();
    }

    public HistogramTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HistogramTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec - getPaddingTop() - getPaddingBottom());
        viewWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        histogramConfig.setTotalHeight(viewHeight);
        histogramConfig.setTotalWidth(viewWidth);
    }

    private void init() {
        List<Float> list = new ArrayList<>();
        list.add(11f);
        list.add(50f);
        list.add(17f);
        list.add(30f);
        list.add(12f);
        list.add(60f);
        list.add(50f);
        list.add(30f);
        list.add(10.1f);
        list.add(18f);
        list.add(70f);
        list.add(13f);
//        list.add("");
//        list.add("");
//        list.add("");
//        list.add("");
//        list.add("");
//        list.add("");
//        list.add("");
//        list.add("");
//        list.add("");
//        list.add("");
//        list.add("");
//        list.add("");
//        list.add("");

        coordinate = new CoordinateImp<>();
        candle = new CandleImp<>();
        histogramConfig = new HistogramConfig<>();
        coordinate.setConfig(histogramConfig);
        candle.setConfig(histogramConfig);
        histogramConfig.setData(list);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        coordinate.draw(canvas);
        candle.draw(canvas);
    }


}
