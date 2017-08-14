package com.jen.easy;

import com.jen.easy.util.DataFormatManager;
import com.jen.easy.util.imp.DataFormatImp;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyUtil {
    public static final DataFormatImp DATAFORMAT;

    static {
        DATAFORMAT = new DataFormatManager();
    }

}
