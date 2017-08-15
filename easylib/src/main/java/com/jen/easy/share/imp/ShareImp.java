package com.jen.easy.share.imp;

import java.util.List;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */

public interface ShareImp {

    /**
     * 获取string
     *
     * @param name
     * @param defaultValue
     * @return
     */
    String getString(String name, String defaultValue);

    /*
     * 保存string
     *
     * @param name
     * @param value
     */
    void setString(String name, String value);

    /**
     * 获取boolean
     *
     * @param name
     * @return
     */
    boolean getBoolean(String name);

    /**
     * 保存boolean
     *
     * @param name
     * @param value
     */
    void setBoolean(String name, boolean value);

    /**
     * 获取int
     *
     * @param name
     * @param defau
     * @return
     */
    int getInt(String name, int defau);

    /**
     * 保存int
     *
     * @param name
     * @param value
     */
    void setInt(String name, int value);

    /**
     * 保存list
     *
     * @param name
     * @param list
     * @return
     */
    <T> boolean setList(String name, List<T> list);

    /**
     * 获取list
     *
     * @param name
     * @return
     */
    <T> List<T> getList(String name);

    /**
     * 获取object
     *
     * @param name
     * @return
     */
    Object getObject(String name);

    /**
     * 设置object
     *
     * @param name
     * @param obj
     * @return
     */
    boolean setObject(String name, Object obj);

    /**
     * 移除值
     *
     * @param name
     */
    void removeValue(String name);
}
