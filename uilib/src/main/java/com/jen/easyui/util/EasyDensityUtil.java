package com.jen.easyui.util;

import android.content.res.Resources;

/**
 * 像素转换
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public class EasyDensityUtil {
    /**
     * TypedValue.applyDimension是一个将各种单位的值转换为像素的方法(源码)
     * TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics())
     */

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     * @return
     */
    public static float dp2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue
     * @return
     */
    public static float px2dp(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return pxValue / scale + 0.5f;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static float px2sp(float pxValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return pxValue / fontScale + 0.5f;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static float sp2px(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }
}
