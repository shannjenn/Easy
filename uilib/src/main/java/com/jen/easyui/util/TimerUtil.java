package com.jen.easyui.util;

import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TimerUtil {
    private Timer timer;
    private TimerTask timerTask;
    private boolean isRunning;
    private int timers;//-1关闭
    private ThreadType threadType;
    private Handler handler;

    public enum ThreadType {
        UIThread,//不能再子线程新建（未实现）
        Thread
    }

    public TimerUtil(ThreadType threadType) {
        this.threadType = threadType;
        switch (threadType) {
            case UIThread:
                handler = new Handler(Looper.myLooper());
                break;
            case Thread:
                break;
        }
    }

    public abstract void listener(int timers);

    public TimerUtil start(long delay) {
        return start(delay, -1, -1);
    }

    public TimerUtil start(long delay, long period) {
        return start(delay, period, -1);
    }

    /**
     * @param delay     .
     * @param period    .
     * @param topTimers 运行几次停止
     */
    public TimerUtil start(long delay, long period, final int topTimers) {
        if (isRunning) {
            return this;
        }
        if (timer != null || timerTask != null) {
            stop();
        }
        isRunning = true;
        timer = new Timer();
        timers = topTimers;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                switch (threadType) {
                    case UIThread:
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener(timers);
                            }
                        });
                        break;
                    case Thread:
                        listener(timers);
                        break;
                }
                if (topTimers > 0) {
                    if (timers <= -1) {
                        cancel();
                    } else {
                        timers--;
                    }
                }
            }
        };
        if (period > 0) {
            timer.schedule(timerTask, delay, period);
        } else {
            timer.schedule(timerTask, delay);
        }
        return this;
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        isRunning = false;
    }
}
