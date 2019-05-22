package com.jen.easyui.view.histogram;


/**
 * 这是一个示例
 *
 * @param <T>
 */
public class CandleImp<T extends Float> extends Candle<T> {

    @Override
    public float candleLineTopValue(T t) {
        float value = t;
        return (value + 20f);
    }

    @Override
    public float candleLineBottomValue(T t) {
        float value = t;
        return (value - 10f);
    }

    @Override
    public float candleTopValue(T t) {
        float value = t;
        return (value + 10f);
    }

    @Override
    public float candleBottomValue(T t) {
        float value = t;
        return (value - 2f);
    }

    @Override
    public int candleColor(T t) {
        float value = t;
        int color;
        if (value - 10 >= 10) {
            color = 0xffff0000;
        } else {
            color = 0xff0000ff;
        }
        return color;
    }
}
