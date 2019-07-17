package com.jen.easyui.util;

import android.content.Context;
import android.content.SharedPreferences;
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
 * 请继承使用
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */
public abstract class EasySharedUtil {
    private final String TAG = "EasySharedUtil";
    private static final String xmlFileName = "easyShare";
    private final String Unicode = "UTF-8";
    private SharedPreferences config;
    private SharedPreferences.Editor editor;
//    protected static EasySharedUtil me;

//    public static EasySharedUtil getIns() {
//        if (me == null) {
//            synchronized (EasySharedUtil.class) {
//                if (me == null) {
//                    me = new EasySharedUtil(XGApplication.getApplication());
//                }
//            }
//        }
//        return me;
//    }


    protected EasySharedUtil(Context context) {
        config = context.getSharedPreferences(xmlFileName, Context.MODE_PRIVATE);
    }

    /**
     * 获取string
     *
     * @param name         参数
     * @param defaultValue 默认值
     * @return 值
     */
    public String getString(String name, String defaultValue) {
        return config.getString(name, defaultValue);
    }

    /**
     * 保存string
     *
     * @param name  参数
     * @param value 值
     */
    public void setString(String name, String value) {
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
    public boolean getBoolean(String name, boolean defaultValue) {
        return config.getBoolean(name, defaultValue);
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
     * 保存list(需要序列化)
     *
     * @param name             参数
     * @param serializableList 值(需要序列化)
     * @return 是否成功
     */
    public <T> boolean setList(String name, List<T> serializableList) {
        String value = list2String(serializableList);
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
    public <T> List<T> getList(String name, Class<T> cls) {
        List<T> valueList = new ArrayList<>();
        String value = getString(name, "");
        if (value.length() == 0) {
            return valueList;
        }
        List<T> list = string2List(value, cls);
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
    public boolean setObject(String name, Object obj) {
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


    /**
     * T对象需要序列化
     *
     * @param list .
     * @param <T>  .
     * @return .
     */
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

    private <T> List<T> string2List(String str, Class<T> cls) {
        List<T> list = new ArrayList<>();
        try {
            byte[] mobileBytes = Base64.decode(str.getBytes(Unicode), 0);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            if (object instanceof List) {
                List<?> objectList = (List<?>) object;
                if (objectList.size() > 0 && objectList.get(0).getClass().getName().equals(cls.getName())) {
                    list = (List<T>) objectList;
                }
            }
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
