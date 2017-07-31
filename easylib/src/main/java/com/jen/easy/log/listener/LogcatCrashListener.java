package com.jen.easy.log.listener;

/**
 * Created by Jen on 2017/7/31.
 */

public interface LogcatCrashListener {
    /**
     * @param ex
     * @return ture用户处理，false系统处理并抛出异常
     */
    boolean onBeforeHandleException(Throwable ex);
}
