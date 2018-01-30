package com.jen.easyui.util;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:22:29
 * 说明：
 */

public class StringToList extends StringToListManager {

    /**
     * 设置默认编码
     * @param unicode
     */
    @Override
    protected void setUnicode(String unicode) {
        super.setUnicode(unicode);
    }

    /**
     * 列表转字符串
     *
     * @param list 列表
     * @param <T>  对象
     * @return 字符串
     */
    @Override
    public <T> String list2String(List<T> list) {
        return super.list2String(list);
    }

    /**
     * 字符串转列表
     *
     * @param str 字符串
     * @param <T> 对象
     * @return 对象
     */
    @Override
    public <T> List<T> string2List(String str) {
        return super.string2List(str);
    }

    /**
     * 对象转字符串
     *
     * @param <T> 对象
     * @return 字符串
     */
    @Override
    public <T> String object2String(T obj) {
        return super.object2String(obj);
    }

    /**
     * 字符串转对象
     *
     * @param str 字符串
     * @return 对象
     */
    @Override
    public Object string2Object(String str) {
        return super.string2Object(str);
    }
}
