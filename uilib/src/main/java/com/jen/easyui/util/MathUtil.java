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

    public enum Type {
        Round,//四舍五入
        Ceil,//逢一进一
        Floor//舍去
    }

    /**
     * double保留小数位
     *
     * @param value 。
     * @param point 小数位，不足位数用0补位
     * @param type  类型
     * @return 。
     */
    public static String changeToStr(Object value, int point, Type type) {
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
        switch (type) {
            case Round: {
                result = round(result, point);
                break;
            }
            case Ceil:
                result = ceil(result, point);
                break;
            case Floor:
                result = floor(result, point);
                break;
        }
        String unit = "%." + point + "f";
        return String.format(unit, result);
    }

    /**
     * 四舍五入
     * <p>
     * 小数点后第一位=5
     * 正数：Math.round(11.5)=12
     * 负数：Math.round(-11.5)=-11
     * 总结：（小数点后第一位）大于五全部加，等于五正数加，小于五全不加。
     *
     * @param value .
     * @param point 保留位数
     * @return .
     */
    public static double round(Double value, int point) {
        if (value == null || value == 0) {
            return 0.0d;
        }
        double pow = Math.pow(10, point);
        double res = Math.round(value * pow);
        return res / pow;
    }

    /**
     * 逢一进一
     * <p>
     * 2.Math.ceil()：根据“ceil”的字面意思“天花板”去理解；
     * 例如：
     * Math.ceil(11.46)=Math.ceil(11.68)=Math.ceil(11.5)=12
     * Math.ceil(-11.46)=Math.ceil(-11.68)=Math.ceil(-11.5)=-11
     *
     * @param value .
     * @param point 保留位数
     * @return .
     */
    public static double ceil(Double value, int point) {
        if (value == null || value == 0) {
            return 0.0d;
        }
        double pow = Math.pow(10, point);
        double res = Math.ceil(value * pow);
        return res / pow;
    }

    /**
     * 舍去
     * <p>
     * 3.Math.floor()：根据“floor”的字面意思“地板”去理解；
     * 例如：
     * Math.floor(11.46)=Math.floor(11.68)=Math.floor(11.5)=11
     * Math.floor(-11.46)=Math.floor(-11.68)=Math.floor(-11.5)=-12
     *
     * @param value .
     * @param point 保留位数
     * @return .
     */
    public static double floor(Double value, int point) {
        if (value == null || value == 0) {
            return 0.0d;
        }
        double pow = Math.pow(10, point);
        double res = Math.floor(value * pow);
        return res / pow;
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
