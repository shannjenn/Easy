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
        //        SIGNED_DECIMAL,//负整数、小数
        NUMBER_SIGNED_DECIMAL,//整数、负整数、小数
        TEXT_NORMAL,//所有文本
    }

    /**
     * 键盘输入数字类型类型
     *
     * @param editText input
     */
    public static void setInputNumType(EditText editText, Type type) {
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
//            case SIGNED_DECIMAL: {//未验证
//                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//                break;
//            }
            case NUMBER_SIGNED_DECIMAL: {
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            }
            case TEXT_NORMAL: {
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
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
     * @param max      最大值(不设置传null)
     * @param min      最小值(不设置传null)
     */
    public static void setInputNumTypeAndPoint(EditText editText, Type type, final int point, final Double min, final Double max) {
        setInputNumType(editText, type);

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
                if (builder.length() > 0) {
                    double value = 0d;
                    try {
                        value = Double.parseDouble(builder.toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (max != null) {
                        if (value > max) {
                            return "";
                        }
                    }
                    if (min != null) {
                        if (min >= 0) {
                            if (value < min) {
                                return "";
                            }
                        } else {
                            if (value < min && value != 0) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }
        }});
    }

    /**
     * @param editText .
     * @param length   最大长度
     */
    public static void setInputLength(EditText editText, int length) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    /*public static class EditorListener implements TextView.OnEditorActionListener {
        private Double min, max;

        public EditorListener(Double min, Double max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String text = v.getText().toString();
                try {
                    Long num = Long.parseLong(text);
                    num = num / minNum * minNum;
                    v.setText(String.valueOf(num));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String text = et_input.getText().toString();
                try {
                    Long num = Long.parseLong(text);
                    num = num / minNum * minNum;
                    et_input.setText(String.valueOf(num));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    };*/

}
