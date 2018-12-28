package com.jen.easyui.util;

import com.jen.easy.log.EasyLog;

/**
 * 数据工具
 * 作者：ShannJenn
 * 时间：2018/11/15.
 * 说明：
 */
public class MathUtil {
    private static final String TAG = MathUtil.class.getSimpleName();

    /**
     * double保留小数位
     *
     * @param value   。
     * @param decimal 小数位，不足位数用0补位
     * @return 。
     */
    public static String formatDecimal(Object value, int decimal) {
        if (value == null) {
            EasyLog.e(TAG, "formatDecimal error -------- ");
            return "";
        }
        Double valueD = null;
        if (value instanceof Double) {
            valueD = (Double) value;
        } else {
            try {
                valueD = Double.parseDouble(String.valueOf(value));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (valueD == null) {
            EasyLog.e(TAG, "formatDecimal NumberFormatException error -------- ");
            return "";
        }
        String unit = "%." + decimal + "f";//f>float
        return String.format(unit, valueD);
    }

}