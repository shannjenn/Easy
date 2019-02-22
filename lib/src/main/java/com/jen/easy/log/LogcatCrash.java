package com.jen.easy.log;

import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.LogcatLog;
import com.jen.easy.log.imp.LogcatCrashListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：日志抓取
 */
class LogcatCrash implements UncaughtExceptionHandler {
    private static LogcatCrash instance; // 单例模式
    private LogcatCrashListener mListener;
    private UncaughtExceptionHandler exceptionHandler; // 系统默认的UncaughtException处理类

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
        if (LogcatPath.getInstance().getPath() == null) {
            return;
        }
        // 获取系统默认的UncaughtException处理器
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    void stop() {
        if (exceptionHandler != null)
            Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        boolean userCatch = false;
        if (mListener != null) {
            File file = new File(LogcatPath.getInstance().getPath(), "LogcatCrash-" + LogcatDate.getFileName() + ".txt");
            try {
                FileOutputStream outputStream = new FileOutputStream(file, true);
                PrintWriter p = new PrintWriter(outputStream);
                p.println(LogcatDate.getDateEN() + " " + ex.toString() + "\n");
                p.println(LogcatDate.getDateEN() + " " + ex.getLocalizedMessage());
                p.println(LogcatDate.getDateEN() + " " + ex.getMessage());
                ex.printStackTrace(p);
                p.flush();
            } catch (FileNotFoundException e) {
                LogcatLog.exception(ExceptionType.FileNotFoundException, "文件不存在");
            }

            userCatch = mListener.onBeforeHandleException(ex);
        }
        if (!userCatch && exceptionHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            exceptionHandler.uncaughtException(thread, ex);
        } else {
            LogcatLog.w("用户来处理异常");
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(1);
        }
    }

    void setListener(LogcatCrashListener listener) {
        this.mListener = listener;
    }

}
