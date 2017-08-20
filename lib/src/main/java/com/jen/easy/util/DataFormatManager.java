package com.jen.easy.util;

import com.jen.easy.util.imp.DataFormatImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
