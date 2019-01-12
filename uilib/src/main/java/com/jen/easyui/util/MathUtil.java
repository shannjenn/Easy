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

//    BigDecimal.setScale()方法用于格式化小数点
//    setScale(2);//表示保留2位小数，默认用四舍五入方式 
//    setScale(2,BigDecimal.ROUND_DOWN);//直接删除多余的小数位  11.116约=11.11
//    setScale(2,BigDecimal.ROUND_UP);//临近位非零，则直接进位；临近位为零，不进位。11.114约=11.12
//    setScale(2,BigDecimal.ROUND_HALF_UP);//四舍五入 2.335约=2.33，2.3351约=2.34
//    setScaler(2,BigDecimal.ROUND_HALF_DOWN);//四舍五入；2.335约=2.33，2.3351约=2.34，11.117约11.12

    /**
     * double保留小数位
     *
     * @param value 。
     * @param scale 小数位，不足位数用0补位
     * @param round 是否四舍五入
     * @return 。
     */
    public static String roundToStr(Object value, int scale, boolean round) {
        if (value == null) {
            EasyLog.e(TAG, "roundToStr error -------- ");
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
            EasyLog.e(TAG, "roundToStr NumberFormatException error -------- ");
            return "";
        }
        if (round) {
            result = roundUp(result, scale);
        }
        String unit = "%." + scale + "f";
        return String.format(unit, result);
    }

    /**
     * double保留小数位(舍去)
     *
     * @param value 。
     * @param scale 小数位，不足位数用0补位
     * @param round 是否四舍五入
     * @return 。
     */
    public static Double roundToDouble(Object value, int scale, boolean round) {
        if (value == null) {
            EasyLog.e(TAG, "roundToStr error -------- ");
            return null;
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
            EasyLog.e(TAG, "roundToStr NumberFormatException error -------- ");
            return null;
        }
        if (round) {
            result = roundUp(result, scale);
        } else {
            result = roundDown(result, scale);
        }
        return result;
    }

    /**
     * double保留小数位(舍去)
     *
     * @param value 。
     * @param scale 小数位
     * @param round 是否四舍五入
     * @return 。
     */
    public static double round(Object value, int scale, boolean round) {
        if (value == null) {
            EasyLog.e(TAG, "roundToStr error -------- ");
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
            EasyLog.e(TAG, "roundToStr NumberFormatException error -------- ");
            return 0;
        }
        if (round) {
            result = roundUp(result, scale);
        } else {
            result = roundDown(result, scale);
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
    private static double roundUp(Double value, int scale) {
        BigDecimal b = new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 舍去
     *
     * @param value .值
     * @param scale .位数
     * @return .
     */
    private static double roundDown(Double value, int scale) {
        BigDecimal b = new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }
}
