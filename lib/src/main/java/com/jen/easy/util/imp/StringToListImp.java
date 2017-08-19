package com.jen.easy.util.imp;

import java.util.List;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */

public interface StringToListImp {

    <T> String list2String(List<T> list);

    <T> List<T> string2List(String str);

    <T> String object2String(T obj);

    Object string2Object(String str);
}
