package com.jen.easy.log;

import com.jen.easy.EasyListener;
import com.jen.easy.log.imp.LogcatHelperImp;

/**
 * ClassName:LogcatHelperManager Function: log日志统计保存
 *
 * @author Jen
 * @Date 2017-7-13 下午6:13:54
 * @see
 * @since Ver 1.1
 */
public class LogcatHelperManager implements LogcatHelperImp {

    public LogcatHelperManager() {
        init();
    }

    private void init() {
        EasyLog.d("LogcatHelperManager init");
        LogcatPath.setDefaultPath();
    }

    /**
     * 设置Log路径
     *
     * @param path
     */
    @Override
    public void setLogPath(String path) {
        LogcatPath.setLogPath(path);
    }

    /**
     * 设置日志输出等级
     *
     * @param level :'d','i','w','e'
     */
    @Override
    public void setLevel(char level) {
        LogDumper.getInstance().setLogLevel(level);
    }

    /**
     * 开始日志记录
     */
    @Override
    public void start() {
        if (LogcatPath.getLogPath() == null) {
            EasyLog.w("日志路径为空，LogcatHelper日志未能启动--------------------");
            return;
        }
        EasyLog.w("日志路径为:" + LogcatPath.getLogPath());
        LogDumper.getInstance().startLogs();
        LogcatCrash.getInstance().start();
    }

    /**
     * 停止日志记录
     */
    @Override
    public void stop() {
        LogDumper.getInstance().stopLogs();
        LogcatCrash.getInstance().stop();
    }

    /**
     * 未抓取崩溃监听
     */
    @Override
    public void setListener(EasyListener.LOG.CrashListener listener) {
        LogcatCrash.getInstance().setListener(listener);
    }
}