package com.jen.easy.log;

import com.jen.easy.constant.TAG;
import com.jen.easy.log.imp.LogcatCrashListener;

/**
 * ClassName:LogcatHelperManager Function: log日志统计保存
 *
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：日志抓取
 */
abstract class LogcatHelperManager {

    LogcatHelperManager() {
    }

    /**
     * 设置Log路径
     *
     * @param path .
     */
    protected void setLogPath(String path) {
        LogcatPath.getInstance().setPath(path);
    }

    /**
     * 设置日志输出等级
     *
     * @param level :'d','i','w','e'
     */
    protected void setLevel(@LogcatLevel int level) {
        LogDumper.getInstance().setLogLevel(level);
    }

    /**
     * 开始日志记录
     */
    protected void start() {
        if (LogcatPath.getInstance().getPath() == null) {
            EasyLog.w(TAG.EasyLogcat, "日志路径为空，LogcatHelper日志未能启动--------------------");
            return;
        }
        EasyLog.w(TAG.EasyLogcat, "日志路径为:" + LogcatPath.getInstance().getPath());
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
    protected void setListener(LogcatCrashListener listener) {
        LogcatCrash.getInstance().setListener(listener);
    }
}