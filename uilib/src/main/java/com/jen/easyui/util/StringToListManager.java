package com.jen.easyui.util;

import android.util.Base64;

import com.jen.easy.log.EasyLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/14.
 */

abstract class StringToListManager {
    private final String TAG = "StringToListManager";
    private String unicode = "utf-8";

    protected void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    protected <T> String list2String(List<T> list) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(list);
            String str = new String(Base64.encode(byteArrayOutputStream.toByteArray(), 0), unicode);
            objectOutputStream.close();
            return str;
        } catch (IOException e) {
            EasyLog.w(TAG, "list2String IOException");
            e.printStackTrace();
        }
        return null;
    }

    protected <T> List<T> string2List(String str) {
        List list = null;
        try {
            byte[] mobileBytes = Base64.decode(str.getBytes(unicode), 0);
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

    protected <T> String object2String(T obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            String str = new String(Base64.encode(byteArrayOutputStream.toByteArray(), 0), unicode);
            objectOutputStream.close();
            return str;
        } catch (IOException e) {
            EasyLog.w(TAG, "object2String IOException");
            e.printStackTrace();
        }
        return null;
    }

    protected Object string2Object(String str) {
        try {
            byte[] mobileBytes = Base64.decode(str.getBytes(unicode), 0);
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
