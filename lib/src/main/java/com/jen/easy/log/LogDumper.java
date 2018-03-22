package com.jen.easy.log;

import com.jen.easy.constant.TAG;
import com.jen.easy.constant.Unicode;

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
            instance = new LogDumper();
        }
        return instance;
    }

    public void setPID(int PID) {
        this.PID = String.valueOf(PID);
    }

    void setLogLevel(char level) {
        switch (level) {
            case 'd':
                cmds = "logcat *:e *:d | grep \"(" + PID + ")\"";// d到e级别
                break;
            case 'i':
                cmds = "logcat *:e *:i | grep \"(" + PID + ")\"";// i到e级别
                break;
            case 'w':
                cmds = "logcat *:e *:w | grep \"(" + PID + ")\"";// w到e级别
                break;
            case 'e':
                cmds = "logcat *:e | grep \"(" + PID + ")\"";// e级别
                break;
            default:
                break;
        }
    }

    void startLogs() {
        if (LogcatPath.getLogPath() == null) {
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
            String line = null;
            while (running && (line = reader.readLine()) != null) {
                if (!running) {
                    break;
                }
                if (line.length() == 0) {
                    continue;
                }
                if (line.contains(PID)) {
                    try {
                        File file = new File(LogcatPath.getLogPath(), "LogcatHelper-" + LogcatDate.getFileName() + ".txt");
                        FileOutputStream out = new FileOutputStream(file, true);
                        out.write((LogcatDate.getDateEN() + "  " + line + "\n").getBytes(Unicode.DEFAULT));
                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            EasyLog.w(TAG.EasyLogcat, "IOException");
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
}
