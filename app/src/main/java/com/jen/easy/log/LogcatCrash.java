package com.jen.easy.log;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;

class LogcatCrash implements UncaughtExceptionHandler {
    private static LogcatCrash instance; // 单例模式
    private LogcatCrashListener mListener;
    private UncaughtExceptionHandler defalutHandler; // 系统默认的UncaughtException处理类
    private String Dir;

    private LogcatCrash() {
        Dir = LogcatPath.getDefaultPath();
    }

    /**
     * 获取CrashHandler实例
     *
     * @return CrashHandler
     */
    public static LogcatCrash getInstance() {
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
    public void start() {
        if (Dir == null) {
            Logcat.w("日志路径为空，LogcatHelper日志未能启动--------------------");
            return;
        }
        // 获取系统默认的UncaughtException处理器
        defalutHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void stop() {
        if (defalutHandler != null)
            Thread.setDefaultUncaughtExceptionHandler(defalutHandler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        boolean userCatch = false;
        if (mListener != null) {
            try {
                PrintWriter p = new PrintWriter(new FileOutputStream(new File(Dir, "LogcatCrash-" + LogcatDate.getFileName() + ".txt"), true));
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
                Log.e(Logcat.TAG, "error : ", e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    public interface LogcatCrashListener {
        boolean onBeforeHandleException(Throwable ex);
    }

    public void setListener(LogcatCrashListener listener) {
        this.mListener = listener;
    }

    public void setDir(String dir) {
        Dir = dir;
    }
}
