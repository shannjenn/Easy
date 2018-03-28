package com.jen.easy.log.imp;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:39
 * 说明：日志抓取监听
 */
public interface LogCrashListener {

    /**
     * 崩溃信息抓取
     *
     * @param ex 异常
     * @return true用户处理，false系统处理并抛出异常
     */
    boolean onBeforeHandleException(Throwable ex);
}
