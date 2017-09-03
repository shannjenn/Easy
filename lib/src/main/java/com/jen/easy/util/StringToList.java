package com.jen.easy.util;

import android.util.Base64;

import com.jen.easy.constant.Constant;
import com.jen.easy.log.EasyLog;
import com.jen.easy.util.imp.StringToListImp;

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

public class StringToList implements StringToListImp {
    private final String TAG = "StringToList : ";

    public <T> String list2String(List<T> list) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(list);
            String str = new String(Base64.encode(byteArrayOutputStream.toByteArray(), 0), Constant.Unicode.DEFAULT);
            objectOutputStream.close();
            return str;
        } catch (IOException e) {
            EasyLog.e(TAG + "list2String IOException");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> string2List(String str) {
        List list = null;
        try {
            byte[] mobileBytes = Base64.decode(str.getBytes(Constant.Unicode.DEFAULT), 0);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            list = (List) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "string2List IOException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "string2List ClassNotFoundException");
        }
        return list;
    }

    @Override
    public <T> String object2String(T obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            String str = new String(Base64.encode(byteArrayOutputStream.toByteArray(), 0), Constant.Unicode.DEFAULT);
            objectOutputStream.close();
            return str;
        } catch (IOException e) {
            EasyLog.e(TAG + "object2String IOException");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object string2Object(String str) {
        try {
            byte[] mobileBytes = Base64.decode(str.getBytes(Constant.Unicode.DEFAULT), 0);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            Object obj = objectInputStream.readObject();
            objectInputStream.close();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "string2Object IOException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            EasyLog.e(TAG + "string2Object ClassNotFoundException");
        }
        return null;
    }
}
