package com.jen.easy.log;

import com.jen.easy.log.imp.LogcatCrashListener;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 * 说明：日志抓取
 */
public class LogcatHelper extends LogcatHelperManager {

    /**
     * 新建实例
     */
    public LogcatHelper() {
        super();
    }

    /**
     * 设置Log路径
     *
     * @param path .
     */
    @Override
    public void setLogPath(String path) {
        super.setLogPath(path);
    }

    /**
     * 设置日志输出等级
     *
     * @param level :'d','i','w','e'
     */
    @Override
    public void setLevel(@LogcatLevel int level) {
        super.setLevel(level);
    }

    /**
     * 开始日志记录
     */
    @Override
    public void start() {
        super.start();
    }

    /**
     * 停止日志记录
     */
    @Override
    public void stop() {
        super.stop();
    }

    /**
     * 抓取崩溃监听
     */
    @Override
    public void setListener(LogcatCrashListener listener) {
        super.setListener(listener);
    }
}
