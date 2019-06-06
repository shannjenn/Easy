package com.jen.easyui.view.histogram;

import com.jen.easy.log.EasyLog;

import java.util.ArrayList;
import java.util.List;

public class HistogramConfig<T> {
    public float totalHeight;//View高度
    public float totalWidth;//View宽度
    public int centerX = 30;//第一个位置X轴中心点坐标/每一根间距
    public float maxValue = 10f, minValue = 0;
    public float paddingTop = 0, paddingBottom = 20;//坐标上下空隙
    public List<T> data = new ArrayList<>();

    public HistogramConfig setTotalHeight(float totalHeight) {
        this.totalHeight = totalHeight;
        return this;
    }

    public HistogramConfig setTotalWidth(float totalWidth) {
        this.totalWidth = totalWidth;
        return this;
    }

    public HistogramConfig setCenterX(int centerX) {
        this.centerX = centerX;
        return this;
    }

    public HistogramConfig setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public HistogramConfig setMinValue(float minValue) {
        this.minValue = minValue;
        return this;
    }

    public HistogramConfig setPaddingTop(float paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public HistogramConfig setPaddingBottom(float paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;
    }

    public HistogramConfig setData(List<T> data) {
        if (data == null) {
            EasyLog.e("HistogramConfig setData error data == null");
            return this;
        }
        this.data = data;
        return this;
    }
}
