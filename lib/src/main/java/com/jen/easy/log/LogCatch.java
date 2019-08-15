package com.jen.easy.log;

import com.jen.easy.constant.Unicode;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.LogcatLog;
import com.jen.easy.log.imp.LogcatListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

class LogCatch extends Thread {
    private static LogCatch instance;
    private LogcatListener mListener;
    private Process logcatProc;
    private BufferedReader reader;
    private boolean running = true;
    private String cmds;
    private String PID;
    final String prefix = "LogCatch-";//文件前缀
    private String suffix = ".txt";//默认后缀名

    private LogCatch() {
        PID = String.valueOf(android.os.Process.myPid());
        cmds = "logcat *:e *:d | grep \"(" + PID + ")\"";// d到e级别
    }

    /**
     * 获取CrashHandler实例
     *
     * @return CrashHandler
     */
    static LogCatch getInstance() {
        if (instance == null) {
            synchronized (LogCatch.class) {
                if (instance == null) {
                    instance = new LogCatch();
                }
            }
        }
        return instance;
    }

    void setPID(int PID) {
        this.PID = String.valueOf(PID);
    }

    void setLogLevel(LogcatLevel level) {
        switch (level) {
            case E:
                cmds = "logcat *:e | grep \"(" + PID + ")\"";// e级别
                break;
            case W:
                cmds = "logcat *:e *:w | grep \"(" + PID + ")\"";// w到e级别
                break;
            case I:
                cmds = "logcat *:e *:i | grep \"(" + PID + ")\"";// i到e级别
                break;
            case D:
                cmds = "logcat *:e *:d | grep \"(" + PID + ")\"";// d到e级别
                break;
        }
    }

    private String getFileName() {
        return getFileName(new Date(System.currentTimeMillis()));
    }

    String getFileName(Date date) {
        return prefix + LogcatDate.getFileName(date) + suffix;
    }

    void startLogs() {
        if (LogcatPath.getInstance().getPath() == null) {
            return;
        }
        running = true;
        instance.start();
    }

    void stopLogs() {
        running = false;
//        instance = null;
    }

    @Override
    public void run() {
        try {
            logcatProc = Runtime.getRuntime().exec(cmds);
            reader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream(), Unicode.DEFAULT), 1024);
            String line;
            File file = new File(LogcatPath.getInstance().getPath(), getFileName());
            boolean isCreated = file.exists();//已经创建过文件
            String headStr = null;
            if (mListener != null) {
                headStr = mListener.addFileHeader();
            }
            while (running && (line = reader.readLine()) != null) {
                if (!running) {
                    break;
                }
                if (line.length() == 0) {
                    EasyLog.d("LogCatch continue----------- ");
                    continue;
                }
                if (line.contains(PID)) {
                    try {
                        FileOutputStream out = new FileOutputStream(file, true);
                        if (!isCreated && headStr != null) {
                            isCreated = true;
                            out.write((headStr + "\n").getBytes(Unicode.DEFAULT));
                        }
                        out.write((LogcatDate.getDateEN() + "  " + line + "\n").getBytes(Unicode.DEFAULT));
                        out.flush();
                        out.close();
                        if (mListener != null && file.exists()) {
                            mListener.onLogPrint(file);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (logcatProc != null) {
                logcatProc.destroy();
                logcatProc = null;
            }
            if (reader != null) {
                try {
                    reader.close();
                    reader = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    void setListener(LogcatListener listener) {
        this.mListener = listener;
    }
}
