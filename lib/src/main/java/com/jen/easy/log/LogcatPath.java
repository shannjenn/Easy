package com.jen.easy.log;

import android.os.Environment;

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
    }

    /**
     * 默认地址
     */
    private void setDefaultPath() {
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

    String getPath() {
        if (path == null) {
            setDefaultPath();
        }
        return path;
    }

    void setPath(String path) {
        if (path == null) {
            EasyLog.e("设置的日志路径不能为空：");
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            boolean ret = file.mkdirs();
            if (!ret) {
                EasyLog.e("设置的日志路径不正确：" + path);
                return;
            }
        }
        this.path = path;
    }

}
