package com.jen.easyui.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

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

public class EasySharedUtil {
    private final String TAG = "EasySharedUtil";
    private static final String xmlFileName = "easyShare";
    private final String Unicode = "UTF-8";
    private SharedPreferences config;
    private SharedPreferences.Editor editor;


    public EasySharedUtil(Context context) {
        config = context.getSharedPreferences(xmlFileName, Context.MODE_PRIVATE);
    }

    /**
     * 获取string
     *
     * @param name         参数
     * @param defaultValue 默认值
     * @return 值
     */
    public String getString(String name, @NonNull String defaultValue) {
        return config.getString(name, defaultValue);
    }

    /**
     * 保存string
     *
     * @param name  参数
     * @param value 值
     */
    public void setString(String name, @NonNull String value) {
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
    public boolean getBoolean(String name) {
        return config.getBoolean(name, false);
    }

    /**
     * 保存boolean
     *
     * @param name  参数
     * @param value 值
     */
    public void setBoolean(String name, boolean value) {
        editor = config.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    /**
     * 获取int
     *
     * @param name         参数
     * @param defaultValue 默认值
     * @return 值
     */
    public int getInt(String name, int defaultValue) {
        return config.getInt(name, defaultValue);
    }

    /**
     * 保存int
     *
     * @param name  参数
     * @param value 值
     */
    public void setInt(String name, int value) {
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
    public <T> boolean setList(String name, List<T> list) {
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
    public <T> List<T> getList(@NonNull String name) {
        List<T> valueList = new ArrayList<>();
        String value = getString(name, "");
        if (value.length() == 0) {
            return valueList;
        }
        List<T> list = string2List(value);
        valueList.addAll(list);
        return valueList;
    }

    /**
     * 获取object
     *
     * @param name 参数
     * @return 值
     */
    public Object getObject(String name) {
        String value = getString(name, "");
        if (value.length() == 0) {
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
    public boolean setObject(String name, @NonNull Object obj) {
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
    public void removeValue(String name) {
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
            Log.w(TAG, "list2String IOException");
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
            Log.w(TAG, "string2List IOException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.w(TAG, "string2List ClassNotFoundException");
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
            Log.w(TAG, "object2String IOException");
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
            Log.w(TAG, "string2Object IOException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.w(TAG, "string2Object ClassNotFoundException");
        }
        return null;
    }
}
