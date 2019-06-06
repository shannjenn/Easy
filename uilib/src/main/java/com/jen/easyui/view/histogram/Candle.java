package com.jen.easyui.view.histogram;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * 蜡烛
 * 作者：ShannJenn
 * 时间：2019/5/20.
 */
public abstract class Candle<T> extends HistogramFactory<T> {
    private Paint candlePaint;

    private float candleWidth = 25;//蜡烛的宽度 宽度不要大于centerX

    @Override
    protected void init() {
        candlePaint = new Paint();
        candlePaint.setAntiAlias(true);
        candlePaint.setStrokeWidth(3f);
        candlePaint.setColor(Color.BLACK);
        candlePaint.setStyle(Paint.Style.FILL);
    }

    //绘制蜡烛
    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < config.data.size(); i++) {
            T t = config.data.get(i);
            int y1 = (int) valueChangY(candleLineTopValue(t));//计算出蜡烛顶端尖尖的Y轴坐标
            int y4 = (int) valueChangY(candleLineBottomValue(t));//计算出蜡烛底端尖尖的Y轴坐标
            int y2 = (int) valueChangY(candleTopValue(t));//计算出蜡烛顶端横线的Y轴坐标
            int y3 = (int) valueChangY(candleBottomValue(t));//计算出蜡烛底端横线的Y轴坐标

            float x = (i + 1) * config.centerX;
            candlePaint.setColor(candleColor(t));
            {//画蜡烛的方块主干
                int x1 = (int) (x - candleWidth / 2);
                int x2 = (int) (x + candleWidth / 2);
                if (y2 != y3/* && Math.abs(y2 - y3) > 1*/) {//非停牌且今开和收盘价差高于1块
                    Rect rect = new Rect();
                    if (y2 < y3) {
                        rect.set(x1, y2, x2, y3);
                    } else {
                        rect.set(x1, y3, x2, y2);
                    }
                    canvas.drawRect(rect, candlePaint);
                } else {//停牌,今开等于收盘
                    canvas.drawLine(x1, y2, x2, y2, candlePaint);
                }
            }
            {
                canvas.drawLine(x, y1, x, y2, candlePaint);//画蜡烛的上尖尖
                canvas.drawLine(x, y3, x, y4, candlePaint);//画蜡烛的下尖尖
            }
        }

    }

    /**
     * 烛顶端尖尖的Y轴值
     *
     * @param t .
     * @return .
     */
    public abstract float candleLineTopValue(T t);

    /**
     * 蜡烛底端尖尖的Y轴值
     *
     * @param t .
     * @return .
     */
    public abstract float candleLineBottomValue(T t);

    /**
     * 计算出蜡烛底端的Y轴值
     *
     * @param t .
     * @return .
     */
    public abstract float candleTopValue(T t);

    /**
     * 蜡烛底端的Y轴值
     *
     * @param t .
     * @return .
     */
    public abstract float candleBottomValue(T t);

    /**
     * 蜡烛颜色
     *
     * @param t .
     * @return .
     */
    public abstract int candleColor(T t);

    /**
     * @param candleWidth 蜡烛宽度
     */
    public void setCandleWidth(float candleWidth) {
        this.candleWidth = candleWidth;
    }
}
