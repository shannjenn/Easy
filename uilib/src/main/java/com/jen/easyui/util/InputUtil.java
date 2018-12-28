package com.jen.easyui.util;

import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.widget.EditText;

/**
 * 数据工具
 * 作者：ShannJenn
 * 时间：2018/11/15.
 * 说明：
 */
public class InputUtil {

    public enum Type {
        NUMBER,//正整数
        NUMBER_SIGNED,//正整数、负整数
        NUMBER_DECIMAL,//正整数、小数
        SIGNED_DECIMAL,//负整数、小数
        NUMBER_SIGNED_DECIMAL,//整数、负整数、小数
    }

    /**
     * 键盘输入数字类型类型
     *
     * @param editText input
     */
    public static void setInputInteger(EditText editText, Type type) {
        switch (type) {
            case NUMBER: {
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            }
            case NUMBER_SIGNED: {
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
                break;
            }
            case NUMBER_DECIMAL: {
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
                break;
            }
            case SIGNED_DECIMAL: {//未验证
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            }
            case NUMBER_SIGNED_DECIMAL: {
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            }
            default:
                break;
        }
    }

    /**
     * @param editText .
     * @param type     类型
     * @param point    小数位数
     * @param maxMin   最大值\最小值(两位数组，不设置传null)
     */
    public static void setInputMaxMinPoint(EditText editText, Type type, final int point, final double[] maxMin) {
        setInputInteger(editText, type);
        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }

                StringBuilder builder = new StringBuilder(dest.toString());
                if (dStart == dEnd) {
                    builder.insert(dStart, source);
                } else {
                    builder.replace(dStart, dEnd, source.toString());
                }

                if (builder.toString().contains(".")) {
                    int index = builder.indexOf(".");
                    int length = builder.substring(index).length();
                    if (length - 1 > point) {
                        return "";
                    }
                }

                if (maxMin != null && maxMin.length == 2) {
                    try {
                        double value = Double.parseDouble(builder.toString());
                        if (value == 0) {
                            return null;
                        } else if (value < maxMin[1] || value > maxMin[0]) {
                            return "";
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }});
    }

}
