package com.jen.easy.log.imp;

import com.jen.easy.EasyL;

/**
 * ClassName:LogcatHelperManager Function: log日志统计保存
 *
 * @author Jen
 * @Date 2017-7-13 下午6:13:54
 * @see
 * @since Ver 1.1
 */
public interface LogcatHelperImp {


    /**
     * 设置Log路径
     *
     * @param path
     */
    void setLogPath(String path);

    /**
     * 设置日志输出等级
     *
     * @param level :'d','i','w','e'
     */
    void setLevel(char level);

    /**
     * 开始日志记录
     */
    void start();

    /**
     * 停止日志记录
     */
    void stop();

    /**
     * 未抓取崩溃监听
     */
    void setListener(EasyL.LOG.CrashListener listener);
}