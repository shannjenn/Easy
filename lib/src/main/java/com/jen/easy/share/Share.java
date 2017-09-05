package com.jen.easy.share;

import android.content.Context;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：SharedPreferences实现类
 */
public class Share extends ShareManager {

    /**
     * 新建实例
     */
    public Share(Context context) {
        super(context);
    }

    /**
     * 获取string
     *
     * @param name         参数
     * @param defaultValue 默认值
     * @return 值
     */
    @Override
    public String getString(String name, String defaultValue) {
        return super.getString(name, defaultValue);
    }

    /**
     * 保存string
     *
     * @param name  参数
     * @param value 值
     */
    @Override
    public void setString(String name, String value) {
        super.setString(name, value);
    }

    /**
     * 获取boolean
     *
     * @param name 参数
     * @return 值
     */
    @Override
    public boolean getBoolean(String name) {
        return super.getBoolean(name);
    }

    /**
     * 保存boolean
     *
     * @param name  参数
     * @param value 值
     */
    @Override
    public void setBoolean(String name, boolean value) {
        super.setBoolean(name, value);
    }

    /**
     * 获取int
     *
     * @param name  参数
     * @param defaut 默认值
     * @return 值
     */
    @Override
    public int getInt(String name, int defaut) {
        return super.getInt(name, defaut);
    }

    /**
     * 保存int
     *
     * @param name 参数
     * @param value 值
     */
    @Override
    public void setInt(String name, int value) {
        super.setInt(name, value);
    }

    /**
     * 保存list
     *
     * @param name 参数
     * @param list 值
     * @return 是否成功
     */
    @Override
    public <T> boolean setList(String name, List<T> list) {
        return super.setList(name, list);
    }

    /**
     * 获取list
     *
     * @param name 参数
     * @return 值
     */
    @Override
    public <T> List<T> getList(String name) {
        return super.getList(name);
    }

    /**
     * 获取object
     *
     * @param name 参数
     * @return 值
     */
    @Override
    public Object getObject(String name) {
        return super.getObject(name);
    }

    /**
     * 设置object
     *
     * @param name 参数
     * @param obj  值
     * @return 是否成功
     */
    @Override
    public boolean setObject(String name, Object obj) {
        return super.setObject(name, obj);
    }

    /**
     * 删除
     *
     * @param name 参数
     */
    @Override
    public void removeValue(String name) {
        super.removeValue(name);
    }
}
