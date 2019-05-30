package com.jen.easyui.view.histogram;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;

import com.jen.easy.log.EasyLog;
import com.jen.easyui.util.EasyDensityUtil;

/**
 * 坐标
 * 作者：ShannJenn
 * 时间：2019/5/20.
 */
public abstract class Coordinate<T> extends HistogramFactory<T> {
    private Paint yPaint;
    private Paint xPaint;//x轴文字
    private Paint linePaint;
    private Path linePath;

    private int valueTextColor;
    private float valueTextSize;
    private int timeTextColor;
    private float timeTextSize;
    private int lineColor;

    @Override
   protected void init() {
        valueTextColor = 0xff666666;
        valueTextSize = EasyDensityUtil.sp2pxFloat(10);
        timeTextColor = 0xff000000;
        timeTextSize = EasyDensityUtil.sp2pxFloat(10);
        lineColor = 0xff666666;

        yPaint = new Paint();
        yPaint.setAntiAlias(true);
        yPaint.setColor(valueTextColor);
        yPaint.setStrokeWidth(1.5f);
        yPaint.setTextSize(valueTextSize);

        xPaint = new Paint();
        xPaint.setAntiAlias(true);
        xPaint.setColor(timeTextColor);
        xPaint.setStrokeWidth(1.5f);
        xPaint.setTextSize(timeTextSize);

        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(1f);
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        linePaint.setPathEffect(effects);
        linePath = new Path();
    }

    @Override
    public void draw(Canvas canvas) {
        linePath.reset();
        //画竖线时间
        {
            int ySize = ySpaceSize();
            if (ySize == 0) {
                EasyLog.w("ySpaceSize竖线返回个数要大于0");
                return;
            }
            for (int i = 0; i < config.data.size(); i += ySize) {
                float x = (i + 1) * config.centerX;
                linePath.moveTo(x, config.paddingTop);
                linePath.lineTo(x, valueChangY(config.minValue));
                canvas.drawPath(linePath, linePaint);

                String time = xText(config.data.get(i));
                if (time == null) {
                    time = "";
                }
                Paint.FontMetrics fm = new Paint.FontMetrics();
                yPaint.getFontMetrics(fm);
                float height = Math.abs(fm.ascent);//文字高度
                float width = yPaint.measureText(time);
                float xTime;
                if (x - width / 2 < 0) {
                    xTime = 0;
                } else if (x + width / 2 > config.totalWidth) {
                    xTime = x - width;
                } else {
                    xTime = x - width / 2;
                }
                canvas.drawText(time, xTime, valueChangY(config.minValue) + height + 10, xPaint);
            }
        }
        //画横线价格
        {
            float unitValue = xNextValue();
            if (unitValue == 0) {
                EasyLog.w("xNextValue返回值要大于0");
                return;
            }
            for (float i = config.minValue; i <= config.maxValue; i += unitValue) {
                float y = valueChangY(i);
                String text = i + "";
                linePath.moveTo(0, y);
                linePath.lineTo(config.totalWidth, y);
                canvas.drawPath(linePath, linePaint);
                canvas.drawText(text, 0, y - 10, yPaint);
                if (config.maxValue != i) {//绘制最后一根
                    y = valueChangY(config.maxValue);
                    text = config.maxValue + "";
                    linePath.moveTo(0, y);
                    linePath.lineTo(config.totalWidth, y);
                    canvas.drawPath(linePath, linePaint);
                    canvas.drawText(text, 0, y - 10, yPaint);
                }
            }
        }
    }

    /**
     * x轴文字
     *
     * @param t .
     * @return .
     */
    public abstract String xText(T t);

    /**
     * 画横线(y轴值value相差多大画一次:如10)
     *
     * @return .
     */
    public abstract float xNextValue();

    /**
     * 画竖线(隔几个画一次:如5个)
     *
     * @return .
     */
    public abstract int ySpaceSize();

    //setter===================================================================================================
    public void setValueTextColor(int valueTextColor) {
        this.valueTextColor = valueTextColor;
    }

    public void setValueTextSize(float valueTextSize) {
        this.valueTextSize = valueTextSize;
    }

    public void setTimeTextColor(int timeTextColor) {
        this.timeTextColor = timeTextColor;
    }

    public void setTimeTextSize(float timeTextSize) {
        this.timeTextSize = timeTextSize;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }
}