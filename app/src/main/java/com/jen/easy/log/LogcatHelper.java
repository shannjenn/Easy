package com.jen.easy.log;

/**
 * ClassName:LogcatHelper Function: log日志统计保存
 * 
 * @author Jen
 * @version
 * @since Ver 1.1
 * @Date 2017-7-13 下午6:13:54
 * @see
 */
public class LogcatHelper {
	private static LogcatHelper mLogcatHelper;

	private LogcatHelper() {
		init();
	}

	/**
	 * 记得加上权限WRITE_EXTERNAL_STORAGE、READ_LOGS
	 */
	public static LogcatHelper getInstance() {
		if (mLogcatHelper == null) {
			mLogcatHelper = new LogcatHelper();
		}
		return mLogcatHelper;
	}

	private void init() {
		LogcatCrash.getInstance();
		LogDumper.getInstance();
	}

	/**
	 * 设置Log路径
	 * 
	 * @param path
	 */
	public void setLogPath(String path) {
		LogcatPath.setLogPath(path);
	}

	/**
	 * 设置日志输出等级
	 * 
	 * @param level
	 *            :'d','i','w','e'
	 */
	public void setLevel(char level) {
		LogDumper.getInstance().setLogLevel(level);
	}

	/**
	 * 开始日志记录
	 */
	public void start() {
		LogDumper.getInstance().startLogs();
		LogcatCrash.getInstance().start();
	}

	/**
	 * 停止日志记录
	 */
	public void stop() {
		LogDumper.getInstance().stopLogs();
		LogcatCrash.getInstance().stop();
	}

	/**
	 * 未抓取崩溃监听
	 */
	public void setListener(LogcatCrash.LogcatCrashListener listener) {
		LogcatCrash.getInstance().setListener(listener);
	}
}