package com.jen.easy.util;

import com.jen.easy.util.imp.DataFormatImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Jen on 2017/7/20.
 */

public class DataFormatManager implements DataFormatImp {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);

    /**
     * 获取时间格式
     *
     * @return
     */
    @Override
    public SimpleDateFormat getFormat() {
        return format;
    }

    /**
     * 时间转字符串
     *
     * @param date
     * @return
     */
    @Override
    public String format(Date date) {
        if (date == null)
            return null;
        return format.format(date);
    }


    /**
     * 时间转字符串
     *
     * @param str
     * @return
     */
    @Override
    public String format(String str) {
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
     * @param dateStr
     * @return
     */
    @Override
    public Date parser(String dateStr) {
        if (dateStr == null)
            return null;
        return parse(dateStr);
    }

    @Override
    public long getTime(String dateStr) {
        if (dateStr == null)
            return 0;
        Date date = parse(dateStr);
        return date.getTime();
    }

    /**
     * 将北京时间转为当前时区时间
     *
     * @param time
     * @return
     */
    @Override
    public String BJ2Loacl(String time) {
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
        String loacl = format.format(dateTmp);
        return loacl;
    }

    /**
     * 将北京时间转为当前时区时间
     *
     * @param date
     * @return
     */
    @Override
    public Date BJ2Loacl(Date date) {
        if (date == null)
            return null;

        TimeZone oldZone = TimeZone.getTimeZone("GMT+8:00");
        TimeZone newZone = TimeZone.getDefault();
        int DST = newZone.getDSTSavings();

        int timeOffset = oldZone.getRawOffset() - (newZone.getRawOffset() + DST);
        Date dateTmp = new Date(date.getTime() - timeOffset);
        return dateTmp;
    }

    /**
     * 将当前时区时间转为北京时间
     *
     * @param time
     * @return
     */
    @Override
    public String locad2BJ(String time) {
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
        String BJTime = format(dateTmp);
        return BJTime;
    }

    /**
     * 将当前时区时间转为北京时间
     *
     * @param date
     * @return
     */
    @Override
    public Date locad2BJ(Date date) {
        if (date == null)
            return null;

        TimeZone bjZone = TimeZone.getTimeZone("GMT+8:00");
        TimeZone loadZone = TimeZone.getDefault();
        int DST = loadZone.getDSTSavings();

        int timeOffset = (loadZone.getRawOffset() + DST) - bjZone.getRawOffset();
        Date dateTmp = new Date(date.getTime() - timeOffset);
        return dateTmp;
    }


    /**
     * 设置时间格式
     *
     * @param dautformat
     */
    @Override
    public void setDefault(String dautformat) {
        if (dautformat == null)
            return;
        format = new SimpleDateFormat(dautformat, Locale.SIMPLIFIED_CHINESE);
    }

    private Date parse(String date) {
        try {
            SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
            return myFmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            return myFmt1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return myFmt2.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            SimpleDateFormat myFmt3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return myFmt3.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            SimpleDateFormat myFmt4 = new SimpleDateFormat("yy/MM/dd HH:mm");
            return myFmt4.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            SimpleDateFormat myFmt5 = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
            return myFmt5.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
