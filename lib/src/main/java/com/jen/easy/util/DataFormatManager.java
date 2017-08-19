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
    public SimpleDateFormat getFormat() {
        return format;
    }

    /**
     * 时间转字符串
     *
     * @param date
     * @return
     */
    public String format(Date date) {
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
    public Date parser(String dateStr) {
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
    public void setDefault(String dautformat) {
        if (dautformat == null)
            return;
        format = new SimpleDateFormat(dautformat, Locale.SIMPLIFIED_CHINESE);
    }

}
