package com.jen.easy.log;

import com.jen.easy.constant.Unicode;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.LogcatLog;
import com.jen.easy.log.imp.LogcatListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：日志抓取
 */
class CrashCatch implements UncaughtExceptionHandler {
    private static CrashCatch instance; // 单例模式
    private LogcatListener mListener;
    private UncaughtExceptionHandler exceptionHandler; // 系统默认的UncaughtException处理类
    private String suffix = ".txt";//默认后缀名

    private CrashCatch() {
    }

    /**
     * 获取CrashHandler实例
     *
     * @return CrashHandler
     */
    static CrashCatch getInstance() {
        if (instance == null) {
            synchronized (CrashCatch.class) {
                if (instance == null) {
                    instance = new CrashCatch();
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
            File file = new File(LogcatPath.getInstance().getPath(), "CrashCatch-" + LogcatDate.getFileName() + suffix);
            boolean isCreated = file.exists();
            try {
                FileOutputStream outputStream = new FileOutputStream(file, true);
                if (!isCreated) {
                    String addFileHeadStr = mListener.addFileHeader();
                    if (addFileHeadStr != null) {
                        outputStream.write((addFileHeadStr + "\n").getBytes(Unicode.DEFAULT));
                    }
                }
                PrintWriter p = new PrintWriter(outputStream);
                p.println(LogcatDate.getDateEN() + " " + ex.toString() + "\n");
                p.println(LogcatDate.getDateEN() + " " + ex.getLocalizedMessage());
                p.println(LogcatDate.getDateEN() + " " + ex.getMessage());
                ex.printStackTrace(p);
                p.flush();
            } catch (FileNotFoundException e) {
                LogcatLog.exception(ExceptionType.FileNotFoundException, "文件不存在");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            userCatch = mListener.onBeforeHandleException(ex, file);
        }
        EasyLog.d("uncaughtException----------- ");
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

    void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    void setListener(LogcatListener listener) {
        this.mListener = listener;
    }
}