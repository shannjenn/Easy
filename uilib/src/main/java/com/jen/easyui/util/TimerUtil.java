package com.jen.easyui.util;

import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TimerUtil {
    private Timer timer;
    private boolean isRunning;
    private int timers;
    private ThreadType threadType;
    private Handler handler;

    public enum ThreadType {
        UIThread,//不能再子线程新建（未实现）
        Thread
    }

    public TimerUtil(ThreadType threadType) {
        timer = new Timer();
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

    public void start(long delay) {
        start(delay, -1, -1);
    }

    public void start(long delay, long period) {
        start(delay, period, -1);
    }

    /**
     * @param delay     .
     * @param period    .
     * @param topTimers 运行几次停止
     */
    public void start(long delay, long period, final long topTimers) {
        if (isRunning) {
            return;
        }
        isRunning = true;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                timers++;
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
                if (timers == topTimers) {
                    cancel();
                }
            }
        };
        if (period > 0) {
            timer.schedule(timerTask, delay, period);
        } else {
            timer.schedule(timerTask, delay);
        }
    }

    public void stop() {
        timer.cancel();
    }
}
