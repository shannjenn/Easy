package com.jen.easy.log;

import com.jen.easy.log.imp.LogCrashListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;

class LogcatCrash implements UncaughtExceptionHandler {
    private static LogcatCrash instance; // 单例模式
    private LogCrashListener mListener;
    private UncaughtExceptionHandler defalutHandler; // 系统默认的UncaughtException处理类

    private LogcatCrash() {
    }

    /**
     * 获取CrashHandler实例
     *
     * @return CrashHandler
     */
    static LogcatCrash getInstance() {
        if (instance == null) {
            instance = new LogcatCrash();
        }
        return instance;
    }

    /**
     * 异常处理初始化
     */
    void start() {
        if (LogcatPath.getLogPath() == null) {
            return;
        }
        // 获取系统默认的UncaughtException处理器
        defalutHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    void stop() {
        if (defalutHandler != null)
            Thread.setDefaultUncaughtExceptionHandler(defalutHandler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        boolean userCatch = false;
        if (mListener != null) {
            File file = new File(LogcatPath.getLogPath(), "LogcatCrash-" + LogcatDate.getFileName() + ".txt");
            try {
                FileOutputStream outputStream = new FileOutputStream(file, true);
                PrintWriter p = new PrintWriter(outputStream);
                p.println(LogcatDate.getDateEN() + " " + ex.toString() + "\n");
                p.println(LogcatDate.getDateEN() + " " + ex.getLocalizedMessage());
                p.println(LogcatDate.getDateEN() + " " + ex.getMessage());
                ex.printStackTrace(p);
                p.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            userCatch = mListener.onBeforeHandleException(ex);
        }
        if (!userCatch && defalutHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            defalutHandler.uncaughtException(thread, ex);
        } else {
            EasyLog.w("用户来处理异常");
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                EasyLog.e("LogcatCrash InterruptedException");
            }*/
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(1);
        }
    }

    void setListener(LogCrashListener listener) {
        this.mListener = listener;
    }

}
