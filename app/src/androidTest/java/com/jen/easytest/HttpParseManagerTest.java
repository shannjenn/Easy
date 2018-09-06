package com.jen.easytest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jen.easy.constant.TAG;
import com.jen.easy.log.EasyLog;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class HttpParseManagerTest {

    @Test
    public void test() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        String a = "abc";
        int b = 123;
        boolean c = true;
        float d = 1.212f;
        long e = 99999999999999999L;
        double f = 99999999999999999999.999999999d;
        short g = 1131;
        char h = 'c';
        byte i = 127;
        
        parseString(a,"a");
        parseString(b,"b");
        parseString(c,"c");
        parseString(d,"d");
        parseString(e,"e");
        parseString(f,"f");
        parseString(g,"g");
        parseString(h,"h");
        parseString(i,"i");
        
        parseInt(a,"a");
        parseInt(b,"b");
        parseInt(c,"c");
        parseInt(d,"d");
        parseInt(e,"e");
        parseInt(f,"f");
        parseInt(g,"g");
        parseInt(h,"h");
        parseInt(i,"i");
        
        parseBoolean(a,"a");
        parseBoolean(b,"b");
        parseBoolean(c,"c");
        parseBoolean(d,"d");
        parseBoolean(e,"e");
        parseBoolean(f,"f");
        parseBoolean(g,"g");
        parseBoolean(h,"h");
        parseBoolean(i,"i");

        parseFloat(a,"a");
        parseFloat(b,"b");
        parseFloat(c,"c");
        parseFloat(d,"d");
        parseFloat(e,"e");
        parseFloat(f,"f");
        parseFloat(g,"g");
        parseFloat(h,"h");
        parseFloat(i,"i");
        
        parseLong(a,"a");
        parseLong(b,"b");
        parseLong(c,"c");
        parseLong(d,"d");
        parseLong(e,"e");
        parseLong(f,"f");
        parseLong(g,"g");
        parseLong(h,"h");
        parseLong(i,"i");
        
        parseDouble(a,"a");
        parseDouble(b,"b");
        parseDouble(c,"c");
        parseDouble(d,"d");
        parseDouble(e,"e");
        parseDouble(f,"f");
        parseDouble(g,"g");
        parseDouble(h,"h");
        parseDouble(i,"i");
        
        parseShort(a,"a");
        parseShort(b,"b");
        parseShort(c,"c");
        parseShort(d,"d");
        parseShort(e,"e");
        parseShort(f,"f");
        parseShort(g,"g");
        parseShort(h,"h");
        parseShort(i,"i");
        
        parseCharacter(a,"a");
        parseCharacter(b,"b");
        parseCharacter(c,"c");
        parseCharacter(d,"d");
        parseCharacter(e,"e");
        parseCharacter(f,"f");
        parseCharacter(g,"g");
        parseCharacter(h,"h");
        parseCharacter(i,"i");
        
        parseByte(a,"a");
        parseByte(b,"b");
        parseByte(c,"c");
        parseByte(d,"d");
        parseByte(e,"e");
        parseByte(f,"f");
        parseByte(g,"g");
        parseByte(h,"h");
        parseByte(i,"i");
        
        
    }


    /**
     * 转String类型
     *
     * @param obj 值
     * @return 返回
     */
    private String parseString(Object obj, String param) {
        String res = null;
        if (obj instanceof String) {
            res = (String) obj;
        } else if (obj instanceof Integer) {
            res = String.valueOf(obj);
        } else if (obj instanceof Boolean) {
            res = String.valueOf(obj);
        } else if (obj instanceof Float) {
            res = String.valueOf(obj);
        } else if (obj instanceof Long) {
            res = String.valueOf(obj);
        } else if (obj instanceof Double) {
            res = String.valueOf(obj);
        } else if (obj instanceof Short) {
            res = String.valueOf(obj);
        } else if (obj instanceof Character) {
            res = String.valueOf(obj);
        } else if (obj instanceof Byte) {
            res = String.valueOf(obj);
        } else {
            showWarn("param=" + param + "该类型值不支持转换为 String 类型");
        }
        return res;
    }

    /**
     * 转Int类型
     *
     * @param obj 值
     * @return 返回
     */
    private int parseInt(Object obj, String param) {
        int res = 0;
        try {
            if (obj instanceof Integer) {
                res = (int) obj;
            } else if (obj instanceof String) {
                res = Integer.valueOf((String) obj);
            } else if (obj instanceof Boolean) {
                showWarn("param=" + param + "Boolean 值不支持转换为 Integer 类型");
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.intValue();
                showWarn("param=" + param + "Float 转换为 Integer 类型 可能会丢失精度");
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.intValue();
                showWarn("param=" + param + "Long 转换为 Integer 类型 可能会丢失精度");
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.intValue();
                showWarn("param=" + param + "Double 转换为 Integer 类型 可能会丢失精度");
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.intValue();
            } else if (obj instanceof Character) {
                res = Integer.valueOf(obj.toString());
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.intValue();
            } else {
                showWarn("param=" + param + "该类型值不支持转换为 Integer 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showWarn("NumberFormatException： 参数：" + param);
        }
        return res;
    }

    /**
     * 转Boolean类型
     *
     * @param obj 值
     * @return 返回
     */
    private boolean parseBoolean(Object obj, String param) {
        boolean res = false;
        if (obj instanceof Boolean) {
            res = (boolean) obj;
        } else if (obj instanceof String) {
            showWarn("param=" + param + "该 String 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Integer) {
            showWarn("param=" + param + "Integer 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Float) {
            showWarn("param=" + param + "Float 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Long) {
            showWarn("param=" + param + "Long 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Double) {
            showWarn("param=" + param + "Double 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Short) {
            showWarn("param=" + param + "Short 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Character) {
            showWarn("param=" + param + "Character 值不支持转换为 Boolean 类型");
        } else if (obj instanceof Byte) {
            showWarn("param=" + param + "Byte 值不支持转换为 Boolean 类型");
        } else {
            showWarn("param=" + param + "该类型值不支持转换为 Boolean 类型");
        }
        return res;
    }

    /**
     * 转Float类型
     *
     * @param obj 值
     * @return 返回
     */
    private float parseFloat(Object obj, String param) {
        float res = 0.0f;
        try {
            if (obj instanceof Float) {
                res = (float) obj;
            } else if (obj instanceof String) {
                res = Float.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                Integer value = (Integer) obj;
                res = value.floatValue();
                showWarn("param=" + param + "Integer 转换为 Float 类型 可能会丢失精度");
            } else if (obj instanceof Boolean) {
                showWarn("param=" + param + "Boolean 值不支持转换为 Float 类型");
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.floatValue();
                showWarn("param=" + param + "Long 转换为 Float 类型 可能会丢失精度");
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.floatValue();
                showWarn("param=" + param + "Double 转换为 Float 类型 可能会丢失精度");
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.floatValue();
            } else if (obj instanceof Character) {
                res = Float.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.floatValue();
            } else {
                showWarn("param=" + param + "该类型值不支持转换为 Float 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showWarn("NumberFormatException： 参数：" + param);
        }
        return res;
    }

    /**
     * 转Long类型
     *
     * @param obj 值
     * @return 返回
     */
    private long parseLong(Object obj, String param) {
        long res = 0;
        try {
            if (obj instanceof Long) {
                res = (long) obj;
            } else if (obj instanceof String) {
                res = Long.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                Integer value = (Integer) obj;
                res = value.longValue();
            } else if (obj instanceof Boolean) {
                showWarn("param=" + param + "Boolean 值不支持转换为 Long 类型");
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.longValue();
                showWarn("param=" + param + "Float 转换为 Long 类型 可能会丢失精度");
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.longValue();
                showWarn("param=" + param + "Double 转换为 Long 类型 可能会丢失精度");
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.longValue();
            } else if (obj instanceof Character) {
                res = Long.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.longValue();
            } else {
                showWarn("param=" + param + "该类型值不支持转换为 Long 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showWarn("NumberFormatException： 参数：" + param);
        }
        return res;
    }

    /**
     * 转Double类型
     *
     * @param obj 值
     * @return 返回
     */
    private double parseDouble(Object obj, String param) {
        double res = 0;
        try {
            if (obj instanceof Double) {
                res = (double) obj;
            } else if (obj instanceof String) {
                res = Double.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                Integer value = (Integer) obj;
                res = value.doubleValue();
                showWarn("param=" + param + "Integer 转换为 Double 类型 可能会丢失精度");
            } else if (obj instanceof Boolean) {
                showWarn("param=" + param + "Boolean 值不支持转换为 Double 类型");
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.doubleValue();
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.doubleValue();
                showWarn("param=" + param + "Long 转换为 Double 类型 可能会丢失精度");
            } else if (obj instanceof Short) {
                Short value = (Short) obj;
                res = value.doubleValue();
            } else if (obj instanceof Character) {
                res = Double.valueOf((Character) obj);
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.doubleValue();
            } else {
                showWarn("param=" + param + "该类型值不支持转换为 Double 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showWarn("NumberFormatException： 参数：" + param);
        }
        return res;
    }

    /**
     * 转Short类型
     *
     * @param obj 值
     * @return 返回
     */
    private short parseShort(Object obj, String param) {
        short res = 0;
        try {
            if (obj instanceof Short) {
                res = (short) obj;
            } else if (obj instanceof String) {
                res = Short.valueOf((String) obj);
            } else if (obj instanceof Integer) {
                Integer value = (Integer) obj;
                res = value.shortValue();
                showWarn("param=" + param + "Integer 转换为 Short 类型 可能会丢失精度");
            } else if (obj instanceof Boolean) {
                showWarn("param=" + param + "Boolean 值不支持转换为 Short 类型");
            } else if (obj instanceof Float) {
                Float value = (Float) obj;
                res = value.shortValue();
                showWarn("param=" + param + "Float 转换为 Short 类型 可能会丢失精度");
            } else if (obj instanceof Long) {
                Long value = (Long) obj;
                res = value.shortValue();
                showWarn("param=" + param + "Long 转换为 Short 类型 可能会丢失精度");
            } else if (obj instanceof Double) {
                Double value = (Double) obj;
                res = value.shortValue();
                showWarn("param=" + param + "Double 转换为 Short 类型 可能会丢失精度");
            } else if (obj instanceof Character) {
                res = Short.valueOf(String.valueOf(obj));
            } else if (obj instanceof Byte) {
                Byte value = (Byte) obj;
                res = value.shortValue();
            } else {
                showWarn("param=" + param + "该类型值不支持转换为 Short 类型");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showWarn("NumberFormatException： 参数：" + param);
        }
        return res;
    }

    /**
     * 转Character类型
     *
     * @param obj 值
     * @return 返回
     */
    private char parseCharacter(Object obj, String param) {
        char res = 0;
        if (obj instanceof Character) {
            res = (char) obj;
        } else if (obj instanceof String) {
            showWarn("param=" + param + "String 值不支持转换为 Character 类型");
        } else if (obj instanceof Integer) {
            showWarn("param=" + param + "Integer 值不支持转换为 Character 类型");
        } else if (obj instanceof Boolean) {
            showWarn("param=" + param + "Boolean 值不支持转换为 Character 类型");
        } else if (obj instanceof Float) {
            showWarn("param=" + param + "Float 值不支持转换为 Character 类型");
        } else if (obj instanceof Long) {
            showWarn("param=" + param + "Long 值不支持转换为 Character 类型");
        } else if (obj instanceof Double) {
            showWarn("param=" + param + "Double 值不支持转换为 Character 类型");
        } else if (obj instanceof Short) {
            showWarn("param=" + param + "Short 值不支持转换为 Character 类型");
        } else if (obj instanceof Byte) {
            showWarn("param=" + param + "Byte 值不支持转换为 Character 类型");
        } else {
            showWarn("param=" + param + "该类型值不支持转换为 Character 类型");
        }
        return res;
    }

    /**
     * 转Byte类型
     *
     * @param obj 值
     * @return 返回
     */
    private byte parseByte(Object obj, String param) {
        byte res = 0;
        if (obj instanceof Byte) {
            res = (byte) obj;
        } else if (obj instanceof String) {
            showWarn("param=" + param + "String 值不支持转换为 Byte 类型");
        } else if (obj instanceof Integer) {
            showWarn("param=" + param + "Integer 值不支持转换为 Byte 类型");
        } else if (obj instanceof Boolean) {
            showWarn("param=" + param + "Boolean 值不支持转换为 Byte 类型");
        } else if (obj instanceof Float) {
            showWarn("param=" + param + "Float 值不支持转换为 Byte 类型");
        } else if (obj instanceof Long) {
            showWarn("param=" + param + "Long 值不支持转换为 Byte 类型");
        } else if (obj instanceof Double) {
            showWarn("param=" + param + "Double 值不支持转换为 Byte 类型");
        } else if (obj instanceof Short) {
            showWarn("param=" + param + "Short 值不支持转换为 Byte 类型");
        } else if (obj instanceof Character) {
            showWarn("param=" + param + "Character 值不支持转换为 Byte 类型");
        } else {
            showWarn("param=" + param + "该类型值不支持转换为 Byte 类型");
        }
        return res;
    }

    /**
     * 警告Log
     *
     * @param error 错误信息
     */
    private void showWarn(String error) {
        EasyLog.w(TAG.EasyHttp, error);
        /*if (!mErrors.contains(error)) {
            mErrors.add(error);
        }*/
    }

}


