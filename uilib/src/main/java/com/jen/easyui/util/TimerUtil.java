package com.jen.easyui.util;

import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TimerUtil {
    private Timer timer;
    private TimerTask timerTask;
    private boolean isRunning;
    private int second;//-1关闭
    private ThreadType threadType;
    private Handler handler;
    private boolean pause;//暂停（单次无效）
    private int topSecond;//运行几次停止

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
     * @param topSecond 运行几次停止
     */
    public TimerUtil start(long delay, long period, final int topSecond) {
        if (isRunning) {
            return this;
        }
        if (timer != null || timerTask != null) {
            stop();
        }
        this.topSecond = topSecond;
        second = topSecond;
        isRunning = true;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (pause) {
                    return;
                }
                switch (threadType) {
                    case UIThread:
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener(second);
                                if (topSecond > 0) {
                                    if (second <= -1) {
                                        if (timerTask != null)
                                            timerTask.cancel();
                                    } else {
                                        second--;
                                    }
                                }
                            }
                        });
                        break;
                    case Thread:
                        listener(second);
                        if (topSecond > 0) {
                            if (second <= -1) {
                                if (timerTask != null)
                                    timerTask.cancel();
                            } else {
                                second--;
                            }
                        }
                        break;
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

    public void pause() {
        pause = true;
    }

    public void restart() {
        pause = false;
        second = topSecond;
    }

    public void stop() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        isRunning = false;
    }
}
