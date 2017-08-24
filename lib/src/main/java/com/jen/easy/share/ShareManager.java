package com.jen.easy.share;

import android.content.Context;
import android.content.SharedPreferences;

import com.jen.easy.EasyUtil;
import com.jen.easy.share.imp.ShareImp;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */

public class ShareManager implements ShareImp {
    private static final String xmlFileName = "easyShare";
    private SharedPreferences config;
    private SharedPreferences.Editor editor;


    public ShareManager(Context context) {
        config = context.getSharedPreferences(xmlFileName, Context.MODE_PRIVATE);
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
        return config.getString(name, defaultValue);
    }

    /*
     * 保存string
     *
     * @param name
     * @param value
     */
    @Override
    public void setString(String name, String value) {
        editor = config.edit();
        editor.putString(name, value);
        editor.apply();
//        editor.commit();
    }

    /**
     * 获取boolean
     *
     * @param name
     * @return
     */
    @Override
    public boolean getBoolean(String name) {
        return config.getBoolean(name, false);
    }

    /**
     * 保存boolean
     *
     * @param name
     * @param value
     */
    @Override
    public void setBoolean(String name, boolean value) {
        editor = config.edit();
        editor.putBoolean(name, value);
        editor.apply();
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
        return config.getInt(name, defau);
    }

    /**
     * 保存int
     *
     * @param name
     * @param value
     */
    @Override
    public void setInt(String name, int value) {
        editor = config.edit();
        editor.putInt(name, value);
        editor.apply();
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
        String value = EasyUtil.STRING.list2String(list);
        if (value == null) {
            return false;
        }
        setString(name, value);
        return true;
    }

    /**
     * 获取list
     *
     * @param name
     * @return
     */
    @Override
    public <T> List<T> getList(String name) {
        List<T> valueLlist = new ArrayList<T>();
        String value = getString(name, null);
        if (null == value) {
            return valueLlist;
        }
        List<T> list = EasyUtil.STRING.string2List(value);
        valueLlist.addAll(list);
        return valueLlist;
    }

    /**
     * 获取object
     *
     * @param name
     * @return
     */
    @Override
    public Object getObject(String name) {
        String value = getString(name, null);
        if (null == value) {
            return null;
        }
        Object obj = EasyUtil.STRING.string2Object(value);
        return obj;
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
        String value = EasyUtil.STRING.object2String(obj);
        if (null == value) {
            return false;
        }
        setString(name, value);
        return true;
    }

    @Override
    public void removeValue(String name) {
        editor = config.edit();
        editor.remove(name);
        editor.apply();
    }
}
