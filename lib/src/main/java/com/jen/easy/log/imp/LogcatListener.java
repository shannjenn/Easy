package com.jen.easy.log.imp;

import java.io.File;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:39
 * 说明：日志抓取监听
 */
public abstract class LogcatListener {

    /**
     * 崩溃信息抓取
     *
     * @param ex   异常
     * @param file 保存的文件
     * @return true用户处理，false系统处理并抛出异常
     */
    public abstract boolean onBeforeHandleException(Throwable ex, File file);

    /**
     * Log打印日志
     *
     * @param file 保存的文件
     */
    public abstract void onLogPrint(File file);

    /**
     * Log信息过滤，true过滤不打印
     *
     * @param log .
     * @return .
     */
    public boolean onLogFilter(String log) {
        return false;
    }

    /**
     * @return 文件头部信息
     */
    public String addFileHeader() {
        return null;
    }
}
