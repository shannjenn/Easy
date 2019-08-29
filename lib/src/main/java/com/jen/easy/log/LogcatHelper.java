package com.jen.easy.log;

import com.jen.easy.log.imp.LogcatListener;

import java.io.File;
import java.util.Date;

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
    public void setLevel(LogcatLevel level) {
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
    public void setListener(LogcatListener listener) {
        super.setListener(listener);
    }

    /**
     * @param date 那一天时间
     * @return 文件，注意：可能不存在
     */
    @Override
    public File getLogFile(Date date) {
        return super.getLogFile(date);
    }

    /**
     * @param date 那一天时间
     * @return 文件，注意：可能不存在
     */
    @Override
    public File getCrashFile(Date date) {
        return super.getCrashFile(date);
    }

    /**
     * 设置后缀名
     *
     * @param suffix 后缀名
     */
    @Override
    public void setSuffix(String suffix) {
        super.setSuffix(suffix);
    }

    /**
     * 增加Log文件内容头部信息
     *
     * @param header 文件内容头部信息
     */
    @Override
    public void setFileHeader(String header) {
        super.setFileHeader(header);
    }
}
