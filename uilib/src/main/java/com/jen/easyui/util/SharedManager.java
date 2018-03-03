package com.jen.easyui.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.jen.easy.log.EasyLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */

abstract class SharedManager {
    private final String TAG = "SharedManager";
    private static final String xmlFileName = "easyShare";
    private final String Unicode = "UTF-8";
    private SharedPreferences config;
    private SharedPreferences.Editor editor;


    SharedManager(Context context) {
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
        String value = list2String(list);
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
        List<T> valueLlist = new ArrayList<>();
        String value = getString(name, null);
        if (null == value) {
            return valueLlist;
        }
        List<T> list = string2List(value);
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
        return string2Object(value);
    }

    /**
     * 设置object
     *
     * @param name 参数
     * @param obj  值
     * @return 是否成功
     */
    protected boolean setObject(String name, Object obj) {
        String value = object2String(obj);
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


    private <T> String list2String(List<T> list) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(list);
            String str = new String(Base64.encode(byteArrayOutputStream.toByteArray(), 0), Unicode);
            objectOutputStream.close();
            return str;
        } catch (IOException e) {
            EasyLog.w(TAG, "list2String IOException");
            e.printStackTrace();
        }
        return null;
    }

    private <T> List<T> string2List(String str) {
        List list = null;
        try {
            byte[] mobileBytes = Base64.decode(str.getBytes(Unicode), 0);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            list = (List) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            EasyLog.w(TAG, "string2List IOException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            EasyLog.w(TAG, "string2List ClassNotFoundException");
        }
        return list;
    }

    private <T> String object2String(T obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            String str = new String(Base64.encode(byteArrayOutputStream.toByteArray(), 0), Unicode);
            objectOutputStream.close();
            return str;
        } catch (IOException e) {
            EasyLog.w(TAG, "object2String IOException");
            e.printStackTrace();
        }
        return null;
    }

    private Object string2Object(String str) {
        try {
            byte[] mobileBytes = Base64.decode(str.getBytes(Unicode), 0);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            Object obj = objectInputStream.readObject();
            objectInputStream.close();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
            EasyLog.w(TAG, "string2Object IOException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            EasyLog.w(TAG, "string2Object ClassNotFoundException");
        }
        return null;
    }
}
