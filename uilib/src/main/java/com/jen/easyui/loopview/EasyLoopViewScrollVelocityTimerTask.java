package com.jen.easyui.loopview;

import java.util.TimerTask;


/**
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
 final class EasyLoopViewScrollVelocityTimerTask extends TimerTask {
    float a;
    final float velocityY;
    final EasyLoopView loopView;

    EasyLoopViewScrollVelocityTimerTask(EasyLoopView loopview, float velocityY) {
        super();
        loopView = loopview;
        this.velocityY = velocityY;
        a = Integer.MAX_VALUE;
    }

    @Override
    public final void run() {
        if (a == Integer.MAX_VALUE) {
            if (Math.abs(velocityY) > 2000F) {
                if (velocityY > 0.0F) {
                    a = 2000F;
                } else {
                    a = -2000F;
                }
            } else {
                a = velocityY;
            }
        }
        if (Math.abs(a) >= 0.0F && Math.abs(a) <= 20F) {
            loopView.cancelFuture();
            loopView.loopViewHandler.sendMessage(loopView.loopViewHandler.obtainMessage(EasyLoopViewHandler.H_2000));
            return;
        }
        int i = (int) ((a * 10F) / 1000F);
        EasyLoopView loopview = loopView;
        loopview.totalScrollY = loopview.totalScrollY - i;
        /*if (!loopView.isLoop) {
            float itemHeight = loopView.lineSpacingMultiplier * loopView.maxTextHeight;
            if (loopView.totalScrollY <= (int) ((float) (-loopView.initPosition) * itemHeight)) {
                a = 40F;
                loopView.totalScrollY = (int) ((float) (-loopView.initPosition) * itemHeight);
            } else if (loopView.totalScrollY >= (int) ((float) (loopView.arrayList.size() - 1 - loopView.initPosition) * itemHeight)) {
                loopView.totalScrollY = (int) ((float) (loopView.arrayList.size() - 1 - loopView.initPosition) * itemHeight);
                a = -40F;
            }
        }*/
        if (a < 0.0F) {
            a = a + 20F;
        } else {
            a = a - 20F;
        }
        loopView.loopViewHandler.sendMessage(loopView.loopViewHandler.obtainMessage(EasyLoopViewHandler.H_1000));
    }
}
