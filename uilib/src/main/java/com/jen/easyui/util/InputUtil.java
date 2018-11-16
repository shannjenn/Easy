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

    /**
     * 只能输入整数
     *
     * @param editText input
     */
    public static void setInputInteger(EditText editText) {
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    /**
     * 只能输入整数
     *
     * @param editText input
     * @param point    保留几位小数
     */
    public static void setInputDecimal(EditText editText, final int point) {
        editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if (mlength == point + 1 && dstart >= dest.length() - point) {
                        return "";
                    }
                }
                return null;
            }
        }});
    }

}
