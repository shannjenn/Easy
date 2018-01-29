package com.jen.easy.log;

import android.content.Context;

import com.jen.easy.log.imp.LogCrashListener;

/**
 * ClassName:LogcatHelperManager Function: log日志统计保存
 *
 * @author Jen
 * @Date 2017-7-13 下午6:13:54
 * @see
 * @since Ver 1.1
 */
abstract class LogcatHelperManager {

    protected LogcatHelperManager(Context context) {
        init(context);
    }

    private void init(Context context) {
        EasyLibLog.d("LogcatHelperManager init");
        LogcatPath.setDefaultPath(context);
    }

    /**
     * 设置Log路径
     *
     * @param path
     */
    protected void setLogPath(String path) {
        LogcatPath.setLogPath(path);
    }

    /**
     * 设置日志输出等级
     *
     * @param level :'d','i','w','e'
     */
    protected void setLevel(char level) {
        LogDumper.getInstance().setLogLevel(level);
    }

    /**
     * 开始日志记录
     */
    protected void start() {
        if (LogcatPath.getLogPath() == null) {
            EasyLibLog.w("日志路径为空，LogcatHelper日志未能启动--------------------");
            return;
        }
        EasyLibLog.w("日志路径为:" + LogcatPath.getLogPath());
        LogDumper.getInstance().startLogs();
        LogcatCrash.getInstance().start();
    }

    /**
     * 停止日志记录
     */
    protected void stop() {
        LogDumper.getInstance().stopLogs();
        LogcatCrash.getInstance().stop();
    }

    /**
     * 抓取崩溃监听
     */
    protected void setListener(LogCrashListener listener) {
        LogcatCrash.getInstance().setListener(listener);
    }
}