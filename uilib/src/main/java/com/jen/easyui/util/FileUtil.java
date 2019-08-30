package com.jen.easyui.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/12/14:19:53
 * 说明：
 */

public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();

    /**
     * 获取Raw文件夹下文件路径
     *
     * @param context .
     * @param rawId   .
     * @return .
     */
    public static String getFilePathFromRaw(Context context, int rawId) {
        return "android.resource://" + context.getPackageName() + "/" + rawId;
    }

    /**
     * 读取raw文件夹下的文件(内容)
     *
     * @param resourceId raw文件夹下的文件资源ID
     * @return 文件内容
     */
    public static String getFileFromRaw(Context context, int resourceId) {
        if (null == context || resourceId < 0) {
            return null;
        }
        String result = null;
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            int length = inputStream.available();// 获取文件的字节数
            byte[] buffer = new byte[length];// 创建byte数组
            inputStream.read(buffer);// 将文件中的数据读到byte数组中
            result = bytesToString(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 列出Asserts文件夹下的所有文件
     *
     * @return asserts目录下的文件名列表
     */
    public static List<String> getFilesFromAsserts(Context context) {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (null == files) ? null : Arrays.asList(files);
    }

    public static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 删除文件
     *
     * @param path 路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        deleteFile(file);
    }

    public static void deleteFile(File file) {
        if (file == null) {
            Log.w(TAG, "file ==  null");
        } else if (!file.exists()) {
            Log.w(TAG, "除文件不存在" + file.getPath());
        } else if (file.isFile()) {
            boolean result = file.delete();
            Log.d(TAG, "删除文件" + file.getPath() + (result ? "成功" : "失败"));
        } else {
            Log.w(TAG, "文件是文件夹无法删除,请用删除文件夹方法删除" + file.getPath());
        }
    }


    /**
     * 删除文件夹
     *
     * @param path 路径
     */
    public static void deleteDir(String path) {
        File dir = new File(path);
        deleteDir(dir);
    }

    /**
     * 删除文件夹
     *
     * @param dir 文件夹
     */
    public static void deleteDir(File dir) {
        if (dir == null) {
            Log.w(TAG, "传入为空");
        } else if (!dir.exists()) {
            Log.w(TAG, "文件不存在");
        } else if (!dir.isDirectory()) {
            Log.w(TAG, "该文件不是目录");
        } else {
            String filePath = dir.getPath();
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    String path = file.getPath();
                    boolean result = file.delete(); // 删除所有文件
                    Log.d(TAG, "删除文件" + path + (result ? "成功" : "失败"));
                } else if (file.isDirectory()) {
                    deleteDir(file); // 递规的方式删除文件夹
                }
            }
            boolean result = dir.delete();// 删除目录本身
            Log.d(TAG, "删除文件" + filePath + (result ? "成功" : "失败"));
        }
    }


    /**
     * 将流转换成字符串
     */
    public static String fileToString(File file) {
        String fileStr = null;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n;
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            byte[] result = out.toByteArray();
            inputStream.close();
            fileStr = Base64.encodeToString(result, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileStr;
    }

    /**
     * 将流转换成字符串
     */
    public static String fileToString(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return fileToString(file);
        }
        return null;
    }

}
