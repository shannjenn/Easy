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
    public static final DataFormat dateFormat;
    /**
     * 字符串与List转换
     */
    public static final StringToList StrList;


    static {
        dateFormat = new DataFormat();
        StrList = new StringToList();
    }

}
