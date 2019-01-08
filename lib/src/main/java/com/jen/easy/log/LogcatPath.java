package com.jen.easy.log;

import android.os.Environment;

import com.jen.easy.constant.TAG;

import java.io.File;

class LogcatPath {
    private static LogcatPath me;
    private String path;

    static LogcatPath getInstance() {
        if (me == null) {
            synchronized (LogcatPath.class) {
                if (me == null) {
                    me = new LogcatPath();
                }
            }
        }
        return me;
    }

    private LogcatPath() {
        initPath();
    }

    private void initPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "_LogcatHelper";
        }
        File file = new File(path);
        if (!file.exists()) {
            boolean ret = file.mkdirs();
            if (!ret) {
                path = null;
            }
        }
    }

//    static void setDefaultPath(Context context) {
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
//            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "_LogcatHelper";
//        } else {// 如果SD卡不存在，就保存到本应用的目录下
//            path = context.getFilesDir().getAbsolutePath() + File.separator + "_LogcatHelper";
//        }
//        /*if (path == null)
//            return;*/
//        File file = new File(path);
//        if (!file.exists()) {
//            boolean ret = file.mkdirs();
//            if (!ret) {
//                path = null;
//            }
//        }
//    }

    String getPath() {
        return path;
    }

    void setPath(String path) {
        if (path == null) {
            EasyLog.w(TAG.EasyLogcat, "设置的日志路径不能为空：");
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            boolean ret = file.mkdirs();
            if (!ret) {
                EasyLog.w(TAG.EasyLogcat, "设置的日志路径不正确：" + path);
                return;
            }
        }
        this.path = path;
    }

}
