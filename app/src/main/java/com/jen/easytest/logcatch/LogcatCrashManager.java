package com.jen.easytest.logcatch;

import android.content.Intent;

import com.jen.easy.log.EasyLog;
import com.jen.easy.log.LogcatHelper;
import com.jen.easy.log.LogcatLevel;
import com.jen.easy.log.imp.LogcatListener;
import com.jen.easytest.MainActivity;
import com.jen.easytest.MyApplication;

import java.io.File;

public class LogcatCrashManager {
    private final String TAG = "LogcatCrashManager";
    private static LogcatCrashManager me;
    private LogcatHelper logcatHelper;
//    private String path;

    public static LogcatCrashManager getIns() {
        if (me == null) {
            synchronized (LogcatCrashManager.class) {
                me = new LogcatCrashManager();
            }
        }
        return me;
    }

    private LogcatCrashManager() {
        logcatHelper = new LogcatHelper();
        init();
    }

    private void init() {
        logcatHelper.setLevel(LogcatLevel.W);
        logcatHelper.setListener(logcatCrashListener);
    }

    /**
     * 错误日志抓取
     */
    private LogcatListener logcatCrashListener = new LogcatListener() {
        /**
         * 崩溃信息抓取
         *
         * @param throwable 异常
         * @return true用户处理，false系统处理并抛出异常
         */
        @Override
        public boolean onBeforeHandleException(Throwable throwable, File file) {
            EasyLog.d("onBeforeHandleException ------------------" + file.getPath());
//            LogcatCrashActivity.actStart(XGApplication.getApplication());
            MyApplication.getAppContext().startActivity(new Intent(MyApplication.getAppContext(), MainActivity.class));
            return false;
        }

        /**
         * log打印级别抓取返回
         * @param file 文件位置
         */
        @Override
        public void onLogPrint(File file) {
            EasyLog.d("onLogPrint ------------------" + file.getPath());

        }

        @Override
        public String addFileHeader() {
            return "这个是文件头部信息===================================";
        }
    };

    public void start() {
        logcatHelper.start();
    }

    public void stop() {
        logcatHelper.stop();
    }
}
