package com.jen.easy.log;

import android.content.Context;
import android.os.Environment;

import com.jen.easy.constant.TAG;

import java.io.File;

class LogcatPath {
    private static String logPath;

    static void setDefaultPath(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            logPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "_LogcatHelper";
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            logPath = context.getFilesDir().getAbsolutePath() + File.separator + "_LogcatHelper";
        }
        /*if (logPath == null)
            return;*/
        File file = new File(logPath);
        if (!file.exists()) {
            boolean ret = file.mkdirs();
            if (!ret) {
                logPath = null;
            }
        }
    }

    static String getLogPath() {
        return logPath;
    }

    static void setLogPath(String logPath) {
        if (logPath == null) {
            EasyLog.w(TAG.EasyLogcat,"设置的日志路径不能为空：");
            return;
        }
        File file = new File(logPath);
        if (!file.exists()) {
            boolean ret = file.mkdirs();
            if (!ret) {
                EasyLog.w(TAG.EasyLogcat,"设置的日志路径不正确：" + logPath);
                return;
            }
        }
        LogcatPath.logPath = logPath;
    }

}
