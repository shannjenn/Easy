package com.jen.easy;

import com.jen.easy.util.DataFormatManager;
import com.jen.easy.util.StringToList;
import com.jen.easy.util.imp.DataFormatImp;
import com.jen.easy.util.imp.StringToListImp;

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
    public static final DataFormatImp DateFormat;
    /**
     * 字符串与List转换
     */
    public static final StringToListImp StrList;


    static {
        DateFormat = new DataFormatManager();
        StrList = new StringToList();
    }

}
