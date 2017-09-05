package com.jen.easy.share;

import android.content.Context;
import android.content.SharedPreferences;

import com.jen.easy.EasyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */

abstract class ShareManager {
    private static final String xmlFileName = "easyShare";
    private SharedPreferences config;
    private SharedPreferences.Editor editor;


    protected ShareManager(Context context) {
        config = context.getSharedPreferences(xmlFileName, Context.MODE_PRIVATE);
    }

    /**
     * 获取string
     *
     * @param name         参数
     * @param defaultValue 默认值
     * @return 值
     */
    protected String getString(String name, String defaultValue) {
        return config.getString(name, defaultValue);
    }

    /**
     * 保存string
     *
     * @param name  参数
     * @param value 值
     */
    protected void setString(String name, String value) {
        editor = config.edit();
        editor.putString(name, value);
        editor.apply();
//        editor.commit();
    }

    /**
     * 获取boolean
     *
     * @param name 参数
     * @return 值
     */
    protected boolean getBoolean(String name) {
        return config.getBoolean(name, false);
    }

    /**
     * 保存boolean
     *
     * @param name  参数
     * @param value 值
     */
    protected void setBoolean(String name, boolean value) {
        editor = config.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    /**
     * 获取int
     *
     * @param name   参数
     * @param defaut 默认值
     * @return 值
     */
    protected int getInt(String name, int defaut) {
        return config.getInt(name, defaut);
    }

    /**
     * 保存int
     *
     * @param name  参数
     * @param value 值
     */
    protected void setInt(String name, int value) {
        editor = config.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    /**
     * 保存list
     *
     * @param name 参数
     * @param list 值
     * @return 是否成功
     */
    protected <T> boolean setList(String name, List<T> list) {
        String value = EasyUtil.StrList.list2String(list);
        if (value == null) {
            return false;
        }
        setString(name, value);
        return true;
    }

    /**
     * 获取list
     *
     * @param name 参数
     * @return 值
     */
    protected <T> List<T> getList(String name) {
        List<T> valueLlist = new ArrayList<T>();
        String value = getString(name, null);
        if (null == value) {
            return valueLlist;
        }
        List<T> list = EasyUtil.StrList.string2List(value);
        valueLlist.addAll(list);
        return valueLlist;
    }

    /**
     * 获取object
     *
     * @param name 参数
     * @return 值
     */
    protected Object getObject(String name) {
        String value = getString(name, null);
        if (null == value) {
            return null;
        }
        Object obj = EasyUtil.StrList.string2Object(value);
        return obj;
    }

    /**
     * 设置object
     *
     * @param name 参数
     * @param obj  值
     * @return 是否成功
     */
    protected boolean setObject(String name, Object obj) {
        String value = EasyUtil.StrList.object2String(obj);
        if (null == value) {
            return false;
        }
        setString(name, value);
        return true;
    }

    /**
     * 删除
     *
     * @param name 参数
     */
    protected void removeValue(String name) {
        editor = config.edit();
        editor.remove(name);
        editor.apply();
    }
}
