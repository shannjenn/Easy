package com.jen.easyui.view.histogram;

import android.graphics.Canvas;

/**
 * 柱状图工厂
 * 作者：ShannJenn
 * 时间：2019/5/20.
 */
public abstract class HistogramFactory<T> {
    protected HistogramConfig<T> config = new HistogramConfig<>();

    public HistogramFactory() {
        init();
    }

    protected abstract void init();

    public abstract void draw(Canvas canvas);

    /**
     * 价格转坐标
     */
    public float valueChangY(float value) {
        float val = (1f - value / (config.maxValue - config.minValue));
        if (val <= 0) {
            return config.paddingTop;
        }
        float ret = val * (config.totalHeight - config.paddingBottom - config.paddingTop) + config.paddingTop - config.paddingBottom;
        if (ret >= config.totalHeight - config.paddingBottom) {
            return config.totalHeight - config.paddingBottom;
        } else {
            return ret;
        }
    }

    public HistogramConfig<T> getConfig() {
        return config;
    }

    public void setConfig(HistogramConfig<T> config) {
        this.config = config;
    }
}
