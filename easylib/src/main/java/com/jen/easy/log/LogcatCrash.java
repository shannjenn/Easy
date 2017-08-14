package com.jen.easy.log;

import com.jen.easy.EasyListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;

class LogcatCrash implements UncaughtExceptionHandler {
    private static LogcatCrash instance; // 单例模式
    private EasyListener.LOG.CrashListener mListener;
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
            synchronized (LogcatCrash.class) {
                if (instance == null) {
                    instance = new LogcatCrash();
                }
            }
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
            try {
                PrintWriter p = new PrintWriter(new FileOutputStream(new File(LogcatPath.getLogPath(), "LogcatCrash-" + LogcatDate.getFileName() + ".txt"), true));
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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Logcat.e("LogcatCrash InterruptedException");
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    void setListener(EasyListener.LOG.CrashListener listener) {
        this.mListener = listener;
    }

}
