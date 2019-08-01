package com.jen.easyui.util;

import android.content.res.Resources;

/**
 * 像素转换
 * 作者：zhanshang
 * 时间：2018/10/19.
 */

public class DensityUtil {
    /*
     * TypedValue.applyDimension是一个将各种单位的值转换为像素的方法(源码)
     * TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics())
     */

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue .
     * @return .
     */
    public static float dp2pxFloat(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    public static int dp2pxInt(float dpValue) {
        return (int) dp2pxFloat(dpValue);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue .
     * @return .
     */
    public static float px2dpFloat(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return pxValue / scale + 0.5f;
    }

    public static int px2dpInt(float pxValue) {
        return (int) px2dpFloat(pxValue);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue .
     * @return .
     */
    public static float px2spFloat(float pxValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return pxValue / fontScale + 0.5f;
    }

    public static int px2spInt(float pxValue) {
        return (int) px2spFloat(pxValue);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue .
     * @return .
     */
    public static float sp2pxFloat(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    public static int sp2pxInt(float spValue) {
        return (int) sp2pxFloat(spValue);
    }
}
