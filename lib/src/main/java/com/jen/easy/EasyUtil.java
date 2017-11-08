package com.jen.easy;

import com.jen.easy.util.DataFormat;
import com.jen.easy.util.StringToList;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/12.
 */
public final class EasyUtil {
    private EasyUtil() {
    }

    /**
     * 时间格式化
     */
    public static final DataFormat mDateFormat;
    /**
     * 字符串与List转换
     */
    public static final StringToList mStrList;


    static {
        mDateFormat = new DataFormat();
        mStrList = new StringToList();
    }

}
