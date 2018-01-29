package com.jen.easyui.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

abstract class DataFormatManager {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);

    /**
     * 获取时间格式
     *
     * @return SimpleDateFormat
     */
    protected SimpleDateFormat getFormat() {
        return format;
    }

    /**
     * 时间转字符串
     *
     * @param date 时间
     * @return 时间字符串
     */
    protected String format(Date date) {
        if (date == null)
            return null;
        return format.format(date);
    }


    /**
     * 时间转字符串
     *
     * @param str 时间
     * @return 格式化时间
     */
    protected String format(String str) {
        if (str == null)
            return null;
        Date date = parser(str);
        if (date == null)
            return null;
        return format.format(date);
    }

    /**
     * 时间转字符串
     *
     * @param dateStr 时间
     * @return 时间
     */
    protected Date parser(String dateStr) {
        if (dateStr == null)
            return null;
        return parse(dateStr);
    }

    protected long getTime(String dateStr) {
        if (dateStr == null)
            return 0;
        Date date = parse(dateStr);
        if (date != null) {
            return date.getTime();
        } else {
            return 0;
        }
    }

    /**
     * 将北京时间转为当前时区时间
     *
     * @param time 时间
     * @return 格式化时间
     */
    protected String BJ2Loacl(String time) {
        if (time == null)
            return null;
        Date date = parse(time);
        if (date == null) {
            return "";
        }

        TimeZone oldZone = TimeZone.getTimeZone("GMT+8:00");
        TimeZone newZone = TimeZone.getDefault();
        int DST = newZone.getDSTSavings();

        int timeOffset = oldZone.getRawOffset() - (newZone.getRawOffset() + DST);
        Date dateTmp = new Date(date.getTime() - timeOffset);
        return format.format(dateTmp);
    }

    /**
     * 将北京时间转为当前时区时间
     *
     * @param date 时间
     * @return 时间
     */
    protected Date BJ2Loacl(Date date) {
        if (date == null)
            return null;

        TimeZone oldZone = TimeZone.getTimeZone("GMT+8:00");
        TimeZone newZone = TimeZone.getDefault();
        int DST = newZone.getDSTSavings();

        int timeOffset = oldZone.getRawOffset() - (newZone.getRawOffset() + DST);
        return new Date(date.getTime() - timeOffset);
    }

    /**
     * 将当前时区时间转为北京时间
     *
     * @param time 时间
     * @return 时间
     */
    protected String locad2BJ(String time) {
        if (time == null)
            return null;
        Date date = parse(time);
        if (date == null) {
            return "";
        }

        TimeZone bjZone = TimeZone.getTimeZone("GMT+8:00");
        TimeZone loadZone = TimeZone.getDefault();
        int DST = loadZone.getDSTSavings();

        int timeOffset = (loadZone.getRawOffset() + DST) - bjZone.getRawOffset();
        Date dateTmp = new Date(date.getTime() - timeOffset);
        return format(dateTmp);
    }

    /**
     * 将当前时区时间转为北京时间
     *
     * @param date 时间
     * @return 时间
     */
    protected Date locad2BJ(Date date) {
        if (date == null)
            return null;

        TimeZone bjZone = TimeZone.getTimeZone("GMT+8:00");
        TimeZone loadZone = TimeZone.getDefault();
        int DST = loadZone.getDSTSavings();

        int timeOffset = (loadZone.getRawOffset() + DST) - bjZone.getRawOffset();
        return new Date(date.getTime() - timeOffset);
    }


    /**
     * 设置时间格式
     *
     * @param dautformat 格式
     */
    protected void setDefault(String dautformat) {
        if (dautformat == null)
            return;
        format = new SimpleDateFormat(dautformat, Locale.SIMPLIFIED_CHINESE);
    }

    private Date parse(String date) {
        try {
            SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
            return myFmt.parse(date);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        try {
            SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            return myFmt1.parse(date);
        } catch (ParseException e) {
//            e.printStackTrace();
        }

        try {
            SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return myFmt2.parse(date);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        try {
            SimpleDateFormat myFmt3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return myFmt3.parse(date);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        try {
            SimpleDateFormat myFmt4 = new SimpleDateFormat("yy/MM/dd HH:mm");
            return myFmt4.parse(date);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        try {
            SimpleDateFormat myFmt5 = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
            return myFmt5.parse(date);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return null;
    }

}
