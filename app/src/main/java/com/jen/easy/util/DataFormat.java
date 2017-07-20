package com.jen.easy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jen on 2017/7/20.
 */

public class DataFormat {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);

    private DataFormat() {

    }

    /**
     * 获取时间格式
     *
     * @return
     */
    public static SimpleDateFormat getFormat() {
        return format;
    }

    /**
     * 时间转字符串
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        if (date == null)
            return null;
        return format.format(date);
    }

    /**
     * 时间转字符串
     *
     * @param dateStr
     * @return
     */
    public static Date parser(String dateStr) {
        if (dateStr == null)
            return null;
        try {
            Date date = format.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 设置时间格式
     *
     * @param dautformat
     */
    public static void setDefault(String dautformat) {
        if (dautformat == null)
            return;
        format = new SimpleDateFormat(dautformat, Locale.SIMPLIFIED_CHINESE);
    }

}