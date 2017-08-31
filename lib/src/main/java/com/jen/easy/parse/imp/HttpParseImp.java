package com.jen.easy.parse.imp;

/**
 * Created by Jen on 2017/7/21.
 */

public interface HttpParseImp {

    /**
     * json解析
     *
     * @param clazz
     * @param obj
     * @return
     */
    Object parseJson(Class clazz, Object obj);
}
