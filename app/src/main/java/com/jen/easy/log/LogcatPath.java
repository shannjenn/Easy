package com.jen.easy.log;

import android.os.Environment;

import java.io.File;

class LogcatPath {
    private static String logPath;

    public static String getDefaultPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            logPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "_LogcatHelper";
        }/*
          else {// 如果SD卡不存在，就保存到本应用的目录下 logPath =
		  context.getFilesDir().getAbsolutePath() + File.separator +
		  "WeCareLoveLog"; }
		 */
        if (logPath == null)
            return null;
        File file = new File(logPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return logPath;
    }

    public static String getLogPath() {
        return logPath;
    }

    public static void setLogPath(String logPath) {
        LogcatPath.logPath = logPath;
    }

}
