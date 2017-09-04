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
     * @param name
     * @param defaultValue
     * @return
     */
    @Override
    public String getString(String name, String defaultValue) {
        return super.getString(name, defaultValue);
    }

    /*
     * 保存string
     *
     * @param name
     * @param value
     */
    @Override
    public void setString(String name, String value) {
        super.setString(name, value);
    }

    /**
     * 获取boolean
     *
     * @param name
     * @return
     */
    @Override
    public boolean getBoolean(String name) {
        return super.getBoolean(name);
    }

    /**
     * 保存boolean
     *
     * @param name
     * @param value
     */
    @Override
    public void setBoolean(String name, boolean value) {
        super.setBoolean(name, value);
    }

    /**
     * 获取int
     *
     * @param name
     * @param defau
     * @return
     */
    @Override
    public int getInt(String name, int defau) {
        return super.getInt(name, defau);
    }

    /**
     * 保存int
     *
     * @param name
     * @param value
     */
    @Override
    public void setInt(String name, int value) {
        super.setInt(name, value);
    }

    /**
     * 保存list
     *
     * @param name
     * @param list
     * @return
     */
    @Override
    public <T> boolean setList(String name, List<T> list) {
        return super.setList(name, list);
    }

    /**
     * 获取list
     *
     * @param name
     * @return
     */
    @Override
    public <T> List<T> getList(String name) {
        return super.getList(name);
    }

    /**
     * 获取object
     *
     * @param name
     * @return
     */
    @Override
    public Object getObject(String name) {
        return super.getObject(name);
    }

    /**
     * 设置object
     *
     * @param name
     * @param obj
     * @return
     */
    @Override
    public boolean setObject(String name, Object obj) {
        return super.setObject(name, obj);
    }

    /**
     * 删除
     *
     * @param name
     * @return
     */
    @Override
    public void removeValue(String name) {
        super.removeValue(name);
    }
}
