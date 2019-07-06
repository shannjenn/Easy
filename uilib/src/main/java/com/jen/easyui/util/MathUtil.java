package com.jen.easyui.util;

import com.jen.easy.log.EasyLog;

import java.math.BigDecimal;

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
     * @param value 。
     * @param point 小数位，不足位数用0补位
     * @param round 是否四舍五入
     * @return 。
     */
    public static String changeToStr(Object value, int point, boolean round) {
        if (value == null) {
            EasyLog.e(TAG, "changeToStr error -------- ");
            return "";
        }
        Double result = null;
        if (value instanceof Double) {
            result = (Double) value;
        } else {
            try {
                result = Double.parseDouble(String.valueOf(value));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (result == null) {
            EasyLog.e(TAG, "changeToStr NumberFormatException error -------- ");
            return "";
        }
        if (round) {
            result = roundUp(result, point);
        }
        String unit = "%." + point + "f";
        return String.format(unit, result);
    }

    /**
     * double保留小数位(舍去)
     *
     * @param value 。
     * @param point 小数位
     * @param round 是否四舍五入
     * @return 。
     */
    public static double turnToDouble(Object value, int point, boolean round) {
        if (value == null) {
            EasyLog.e(TAG, "changeToStr error -------- ");
            return 0;
        }
        double result = 0;
        if (value instanceof Double) {
            result = (Double) value;
        } else {
            try {
                result = Double.parseDouble(String.valueOf(value));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (result == 0) {
            EasyLog.e(TAG, "changeToStr NumberFormatException error -------- ");
            return 0;
        }
        if (round) {
            result = roundUp(result, point);
        } else {
            result = roundDown(result, point);
        }
        return result;
    }

    /**
     * 四舍五入
     *
     * @param value .值
     * @param scale .位数
     * @return .
     */
    public static double roundUp(Double value, int scale) {
        if (value == null) {
            return 0;
        }
        double res = 0;
        try {
            BigDecimal b = new BigDecimal(Double.toString(value));
            BigDecimal one = new BigDecimal("1");
            res = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 舍去
     *
     * @param value .值
     * @param scale .位数
     * @return .
     */
    public static double roundDown(Double value, int scale) {
        if (value == null) {
            return 0.0d;
        }
        double res = 0;
        try {
            BigDecimal b = new BigDecimal(Double.toString(value));
            BigDecimal one = new BigDecimal("1");
            res = b.divide(one, scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 转int
     *
     * @param value .
     * @return .
     */
    public static int changeToInt(String value) {
        int ret = 0;
        if (value != null && value.trim().length() > 0) {
            try {
                ret = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 转int
     *
     * @param value .
     * @return .
     */
    public static long changeToLong(String value) {
        long ret = 0;
        if (value != null && value.trim().length() > 0) {
            try {
                ret = Long.parseLong(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 转float
     *
     * @param value .
     * @return .
     */
    public static float changeToFloat(String value) {
        float ret = 0.0f;
        if (value != null && value.trim().length() > 0) {
            try {
                ret = Float.parseFloat(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * double
     *
     * @param value .
     * @return .
     */
    public static double changeToDouble(String value) {
        double ret = 0.0d;
        if (value != null && value.trim().length() > 0) {
            try {
                ret = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
