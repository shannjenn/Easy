package com.jen.easy.log;

import com.jen.easy.log.imp.LogcatListener;

import java.io.File;
import java.util.Date;

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
     * @param date 那一天时间
     * @return 文件，注意：可能不存在
     */
    public File getLogFile(Date date) {
        String fileName = LogCatch.getInstance().getFileName(date);
        return new File(LogcatPath.getInstance().getPath(), fileName);
    }

    /**
     * @param date 那一天时间
     * @return 文件，注意：可能不存在
     */
    public File getCrashFile(Date date) {
        String fileName = CrashCatch.getInstance().getFileName(date);
        return new File(LogcatPath.getInstance().getPath(), fileName);
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
            EasyLog.e("日志路径为空，LogcatHelper日志未能启动--------------------");
            return;
        }
        EasyLog.i("日志路径为:" + LogcatPath.getInstance().getPath());
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

    /**
     * 增加Log文件内容头部信息
     *
     * @param header 文件内容头部信息
     */
    public void setFileHeader(String header) {
        LogCatch.getInstance().setFileHeader(header);
        CrashCatch.getInstance().setFileHeader(header);
    }
}