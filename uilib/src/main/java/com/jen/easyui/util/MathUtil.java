package com.jen.easyui.util;

import com.jen.easy.log.EasyLog;

import java.math.BigDecimal;

/**
 * 数据工具 请继承使用
 * 作者：ShannJenn
 * 时间：2018/11/15.
 * 说明：
 */
public class MathUtil {
    private static final String TAG = MathUtil.class.getSimpleName();
    public static int myScale = 2;//默认保留小数位数

    /**
     * 计算方法
     * 其他方法后续可加
     */
    public enum Math {
        Add,//加法
        Subtract,//减法
        Multiply,//乘法
        Divide,//除法
    }

    /**
     * double保留小数位
     * <p>
     * %s        字符串类型 
     * String.format("字符%s","串")             结果：  "字符串"
     * -----------------------------------------------------------------
     * %c        字符类型
     * String.format("字符%c",'A')              结果：  "字符A"
     * -----------------------------------------------------------------
     * %b        布尔类型
     * String.format("字符%b",1==2)             结果：  "字符false"
     * -----------------------------------------------------------------
     * %d        整数类型（十进制）
     * String.format("字符%d",20/2)             结果：  "字符10"
     * -----------------------------------------------------------------
     * %x        整数类型（十六进制）
     * String.format("字符%x",100)              结果：  "64"
     * -----------------------------------------------------------------
     * %o        整数类型（八进制）
     * String.format("字符%o",100)              结果：  "字符144"
     * -----------------------------------------------------------------
     * %f         浮点类型
     * String.format("字符%f",10*0.05)          结果：  "字符0.500000"
     * -----------------------------------------------------------------
     * %h        散列码
     * String.format("字符%h",'A')              结果：  "字符41"
     * -----------------------------------------------------------------
     * %%       百分比类型
     * String.format("字符%d%%",85)              结果：  "字符85%"
     * -----------------------------------------------------------------
     * %a        浮点类型（十六进制）
     * %e        指数类型
     * %g        通用浮点类型
     * %n        换行符
     * %tx       日期与时间类型（x代表不同的日期与时间转换符）
     * -----------------------------------------------------------------
     *
     * @param value 。
     * @param scale 小数位，不足位数用0补位
     * @return 。
     */
    public static String format(Object value, int scale) {
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
        String unit = "%." + scale + "f";
        return String.format(unit, result);
    }

    public static String format(Object value) {
        return format(value, myScale);
    }


    /**
     * @param value1 值1
     * @param value2 值2
     * @param math   方法
     * @return .
     */
    public static BigDecimal math(Object value1, Object value2, Math math) {
        return math(value1, value2, math, myScale);
    }

    /**
     * @param value1 值1
     * @param value2 值2
     * @param math   方法
     * @param scale  保留小数（只有除法）
     * @return .
     */
    public static BigDecimal math(Object value1, Object value2, Math math, int scale) {
        boolean isError = false;
        if (value1 instanceof String) {
            if (((String) value1).length() == 0) {
                isError = true;
            }
        } else if (value2 instanceof String) {
            if (((String) value2).length() == 0) {
                isError = true;
            }
        } else if (!(value1 instanceof Double) && !(value1 instanceof Integer) && !(value1 instanceof Long) && !(value1 instanceof Float)) {
            isError = true;
        } else if (!(value2 instanceof Double) && !(value2 instanceof Integer) && !(value2 instanceof Long) && !(value2 instanceof Float)) {
            isError = true;
        }
        if (isError) {
            EasyLog.e(TAG, "math方法：数据类型错误");
            return new BigDecimal(0);
        }
        try {
            BigDecimal bd1 = new BigDecimal(String.valueOf(value1));
            BigDecimal bd2 = new BigDecimal(String.valueOf(value2));
            switch (math) {
                case Add:
                    return bd1.add(bd2);
                case Subtract:
                    return bd1.subtract(bd2);
                case Multiply:
                    return bd1.multiply(bd2);
                case Divide:
                    return bd1.divide(bd2, scale);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return new BigDecimal(0);
    }

    /**
     * 逢一进一
     *
     * @param value .
     * @return .
     */
    public static BigDecimal roundUp(Object value) {
        return roundUp(value, myScale);
    }

    /**
     * 逢一进一
     *
     * @param value .
     * @param scale 保留位数
     * @return .
     */
    public static BigDecimal roundUp(Object value, int scale) {
        return round(value, scale, BigDecimal.ROUND_UP);
    }

    /**
     * 舍去
     *
     * @param value .
     * @return .
     */
    public static BigDecimal roundDown(Object value) {
        return roundDown(value, myScale);
    }

    /**
     * 舍去
     *
     * @param value .
     * @param scale 保留位数
     * @return .
     */
    public static BigDecimal roundDown(Object value, int scale) {
        return round(value, scale, BigDecimal.ROUND_UP);
    }

    /**
     * 逢一进一(注意正负)
     *
     * @param value .
     * @return .
     */
    public BigDecimal roundCeiling(Object value) {
        return roundCeiling(value, myScale);
    }

    /**
     * 逢一进一(注意正负)
     *
     * @param value .
     * @param scale 保留位数
     * @return .
     */
    public static BigDecimal roundCeiling(Object value, int scale) {
        return round(value, scale, BigDecimal.ROUND_CEILING);
    }

    /**
     * 舍去(注意正负)
     *
     * @param value .
     * @return .
     */
    public static BigDecimal roundFloor(Object value) {
        return roundFloor(value, myScale);
    }

    /**
     * 舍去(注意正负)
     *
     * @param value .
     * @param scale 保留位数
     * @return .
     */
    public static BigDecimal roundFloor(Object value, int scale) {
        return round(value, scale, BigDecimal.ROUND_FLOOR);
    }

    /**
     * 四舍五入
     *
     * @param value .
     * @return .
     */
    public static BigDecimal roundHalfUp(Object value) {
        return roundHalfUp(value, myScale);
    }

    /**
     * 四舍五入
     *
     * @param value .
     * @param scale 保留位数
     * @return .
     */
    public static BigDecimal roundHalfUp(Object value, int scale) {
        return round(value, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 五舍六入
     *
     * @param value .
     * @return .
     */
    public static BigDecimal roundHalfDown(Object value) {
        return roundHalfDown(value, myScale);
    }

    /**
     * 五舍六入
     *
     * @param value .
     * @param scale 保留位数
     * @return .
     */
    public static BigDecimal roundHalfDown(Object value, int scale) {
        return round(value, scale, BigDecimal.ROUND_HALF_DOWN);
    }

    /**
     * 奇偶舍入
     *
     * @param value .
     * @return .
     */
    public static BigDecimal roundHalfEven(Object value) {
        return roundHalfEven(value, myScale);
    }

    /**
     * 奇偶舍入
     *
     * @param value .
     * @param scale 保留位数
     * @return .
     */
    public static BigDecimal roundHalfEven(Object value, int scale) {
        return round(value, scale, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 1、ROUND_UP
     * 舍入远离零的舍入模式。
     * 在丢弃非零部分之前始终增加数字(始终对非零舍弃部分前面的数字加1)。
     * 注意，此舍入模式始终不会减少计算值的大小。
     * <p>
     * 2、ROUND_DOWN
     * 接近零的舍入模式。
     * 在丢弃某部分之前始终不增加数字(从不对舍弃部分前面的数字加1，即截短)。
     * 注意，此舍入模式始终不会增加计算值的大小。
     * <p>
     * 3、ROUND_CEILING
     * 接近正无穷大的舍入模式。
     * 如果 BigDecimal 为正，则舍入行为与 ROUND_UP 相同;
     * 如果为负，则舍入行为与 ROUND_DOWN 相同。
     * 注意，此舍入模式始终不会减少计算值。
     * <p>
     * 4、ROUND_FLOOR
     * 接近负无穷大的舍入模式。
     * 如果 BigDecimal 为正，则舍入行为与 ROUND_DOWN 相同;
     * 如果为负，则舍入行为与 ROUND_UP 相同。
     * 注意，此舍入模式始终不会增加计算值。
     * <p>
     * 5、ROUND_HALF_UP
     * 向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。
     * 如果舍弃部分 >= 0.5，则舍入行为与 ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同。
     * 注意，这是我们大多数人在小学时就学过的舍入模式(四舍五入)。
     * <p>
     * 6、ROUND_HALF_DOWN
     * 向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为上舍入的舍入模式。
     * 如果舍弃部分 > 0.5，则舍入行为与 ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同(五舍六入)。
     * <p>
     * 7、ROUND_HALF_EVEN
     * 向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。
     * 如果舍弃部分左边的数字为奇数，则舍入行为与 ROUND_HALF_UP 相同;
     * 如果为偶数，则舍入行为与 ROUND_HALF_DOWN 相同。
     * 注意，在重复进行一系列计算时，此舍入模式可以将累加错误减到最小。
     * 此舍入模式也称为“银行家舍入法”，主要在美国使用。四舍六入，五分两种情况。
     * 如果前一位为奇数，则入位，否则舍去。
     *
     * @param value 值
     * @param scale 保留位数
     * @param type  类型
     * @return .
     */
    private static BigDecimal round(Object value, int scale, int type) {
        boolean isError = false;
        if (value instanceof String) {
            if (((String) value).length() == 0) {
                isError = true;
            }
        } else if (!(value instanceof Double) && !(value instanceof Integer) && !(value instanceof Long) && !(value instanceof Float)) {
            isError = true;
        }
        if (isError) {
            EasyLog.e(TAG, "round方法：数据类型错误");
            return new BigDecimal(0);
        }
        try {
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
            bigDecimal = bigDecimal.setScale(scale, type);
            return bigDecimal;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new BigDecimal(0);
        }
    }

}
