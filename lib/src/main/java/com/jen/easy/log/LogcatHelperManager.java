package com.jen.easy.log;

import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.LogcatLog;
import com.jen.easy.log.imp.LogcatListener;

/**
 * ClassName:LogcatHelperManager Function: log日志统计保存
 * <p>
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
    protected void setLevel(LogcatLevel level) {
        LogCatch.getInstance().setLogLevel(level);
    }

    /**
     * 开始日志记录
     */
    protected void start() {
        if (LogcatPath.getInstance().getPath() == null) {
            LogcatLog.exception(ExceptionType.NullPointerException, "日志路径为空，LogcatHelper日志未能启动--------------------");
            return;
        }
        LogcatLog.i("日志路径为:" + LogcatPath.getInstance().getPath());
        LogCatch.getInstance().startLogs();
        CrashCatch.getInstance().start();
    }

    /**
     * 停止日志记录
     */
    protected void stop() {
        LogCatch.getInstance().stopLogs();
        CrashCatch.getInstance().stop();
    }

    /**
     * 抓取崩溃监听
     */
    protected void setListener(LogcatListener listener) {
        LogCatch.getInstance().setListener(listener);
        CrashCatch.getInstance().setListener(listener);
    }

    /**
     * 设置后缀名
     *
     * @param suffix 后缀名
     */
    public void setSuffix(String suffix) {
        if (suffix == null)
            return;
        LogCatch.getInstance().setSuffix(suffix);
        CrashCatch.getInstance().setSuffix(suffix);
    }
}