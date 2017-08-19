package com.jen.easy.util.imp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jen on 2017/7/20.
 */

public interface DataFormatImp {

    /**
     * 获取时间格式
     *
     * @return
     */
    SimpleDateFormat getFormat();

    /**
     * 时间转字符串
     *
     * @param date
     * @return
     */
    String format(Date date);

    /**
     * 时间转字符串
     *
     * @param dateStr
     * @return
     */
    Date parser(String dateStr);


    /**
     * 设置时间格式
     *
     * @param dautformat
     */
    void setDefault(String dautformat);

}
