package com.jen.easyui.util;

import com.jen.easy.log.EasyLog;

import java.io.File;

/**
 * 作者：ShannJenn
 * 时间：2017/12/14:19:53
 * 说明：
 */

public class EasyFileUtil {
    private final String TAG = "EasyFileUtil";

    /**
     * 删除文件
     *
     * @param path 路径
     */
    public void deleteFile(String path) {
        File file = new File(path);
        deletFile(file);
    }

    public void deletFile(File file) {
        if (file == null) {
            EasyLog.w(TAG, "file ==  null");
        } else if (!file.exists()) {
            EasyLog.w(TAG, "除文件不存在" + file.getPath());
        } else if (file.isFile()) {
            boolean result = file.delete();
            EasyLog.d(TAG, "删除文件" + file.getPath() + (result ? "成功" : "失败"));
        } else {
            EasyLog.w(TAG, "文件是文件夹无法删除,请用删除文件夹方法删除" + file.getPath());
        }
    }


    /**
     * 删除文件夹
     *
     * @param path 路径
     */
    public void deleteDir(String path) {
        File dir = new File(path);
        deleteDir(dir);
    }

    /**
     * 删除文件夹
     *
     * @param dir 文件夹
     */
    public void deleteDir(File dir) {
        if (dir == null) {
            EasyLog.w(TAG, "传入为空");
        } else if (!dir.exists()) {
            EasyLog.w(TAG, "文件不存在");
        } else if (!dir.isDirectory()) {
            EasyLog.w(TAG, "该文件不是目录");
        } else {
            String filePath = dir.getPath();
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    String path = file.getPath();
                    boolean result = file.delete(); // 删除所有文件
                    EasyLog.d(TAG, "删除文件" + path + (result ? "成功" : "失败"));
                } else if (file.isDirectory()) {
                    deleteDir(file); // 递规的方式删除文件夹
                }
            }
            boolean result = dir.delete();// 删除目录本身
            EasyLog.d(TAG, "删除文件" + filePath + (result ? "成功" : "失败"));
        }

    }
}
