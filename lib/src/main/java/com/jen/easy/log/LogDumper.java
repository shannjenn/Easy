package com.jen.easy.log;

import com.jen.easy.constant.Unicode;
import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.LogcatLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class LogDumper extends Thread {
    private static LogDumper instance;
    private Process logcatProc;
    private BufferedReader reader;
    private boolean running = true;
    private String cmds;
    private String PID;

    private LogDumper() {
        PID = String.valueOf(android.os.Process.myPid());
        cmds = "logcat *:e *:d | grep \"(" + PID + ")\"";// d到e级别
    }

    /**
     * 获取CrashHandler实例
     *
     * @return CrashHandler
     */
    static LogDumper getInstance() {
        if (instance == null) {
            synchronized (LogDumper.class) {
                if (instance == null) {
                    instance = new LogDumper();
                }
            }
        }
        return instance;
    }

    void setPID(int PID) {
        this.PID = String.valueOf(PID);
    }

    void setLogLevel(@LogcatLevel int level) {
        switch (level) {
            case LogcatLevel.E:
                cmds = "logcat *:e | grep \"(" + PID + ")\"";// e级别
                break;
            case LogcatLevel.W:
                cmds = "logcat *:e *:w | grep \"(" + PID + ")\"";// w到e级别
                break;
            case LogcatLevel.I:
                cmds = "logcat *:e *:i | grep \"(" + PID + ")\"";// i到e级别
                break;
            case LogcatLevel.D:
                cmds = "logcat *:e *:d | grep \"(" + PID + ")\"";// d到e级别
                break;
        }
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
            while (running && (line = reader.readLine()) != null) {
                if (!running) {
                    break;
                }
                if (line.length() == 0) {
                    continue;
                }
                if (line.contains(PID)) {
                    try {
                        File file = new File(LogcatPath.getInstance().getPath(), "LogcatHelper-" + LogcatDate.getFileName() + ".txt");
                        FileOutputStream out = new FileOutputStream(file, true);
                        out.write((LogcatDate.getDateEN() + "  " + line + "\n").getBytes(Unicode.DEFAULT));
                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e) {
                        LogcatLog.exception(ExceptionType.FileNotFoundException, "FileNotFoundException");
                    }
                }
            }

        } catch (IOException e) {
            LogcatLog.exception(ExceptionType.IOException, "IOException");
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
                    LogcatLog.exception(ExceptionType.IOException, "IOException");
                }
            }
        }
    }
}
