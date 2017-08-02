package com.jen.easy.log;

import com.jen.easy.log.listener.LogcatCrashListener;

import java.io.File;

/**
 * ClassName:LogcatHelper Function: log日志统计保存
 *
 * @author Jen
 * @Date 2017-7-13 下午6:13:54
 * @see
 * @since Ver 1.1
 */
public class LogcatHelper {
    private static LogcatHelper mLogcatHelper;

    private LogcatHelper() {
        init();
    }

    /**
     * 记得加上权限WRITE_EXTERNAL_STORAGE、READ_LOGS
     */
    public static LogcatHelper getInstance() {
        if (mLogcatHelper == null) {
            mLogcatHelper = new LogcatHelper();
        }
        return mLogcatHelper;
    }

    private void init() {
        LogcatPath.setDefaultPath();
        LogcatCrash.getInstance();
        LogDumper.getInstance();
    }

    /**
     * 设置Log路径
     *
     * @param path
     */
    public void setLogPath(String path) {
        LogcatPath.setLogPath(path);
    }

    /**
     * 设置日志输出等级
     *
     * @param level :'d','i','w','e'
     */
    public void setLevel(char level) {
        LogDumper.getInstance().setLogLevel(level);
    }

    /**
     * 开始日志记录
     */
    public void start() {
        if (LogcatPath.getLogPath() == null) {
            Logcat.w("日志路径为空，LogcatHelper日志未能启动--------------------");
            return;
        }
        LogDumper.getInstance().startLogs();
        LogcatCrash.getInstance().start();
    }

    /**
     * 停止日志记录
     */
    public void stop() {
        LogDumper.getInstance().stopLogs();
        LogcatCrash.getInstance().stop();
    }

    /**
     * 未抓取崩溃监听
     */
    public void setListener(LogcatCrashListener listener) {
        LogcatCrash.getInstance().setListener(listener);
    }
}