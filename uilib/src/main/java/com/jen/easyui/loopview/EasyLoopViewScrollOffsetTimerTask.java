package com.jen.easyui.loopview;

import java.util.TimerTask;

/**
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
final class EasyLoopViewScrollOffsetTimerTask extends TimerTask {

    int realTotalOffset;
    int realOffset;
    int offset;
    final EasyLoopView loopView;

    EasyLoopViewScrollOffsetTimerTask(EasyLoopView loopview, int offset) {
        super();
        this.loopView = loopview;
        this.offset = offset;
        realTotalOffset = Integer.MAX_VALUE;
        realOffset = 0;
    }

    @Override
    public final void run() {
        if (realTotalOffset == Integer.MAX_VALUE) {
            float itemHeight = loopView.textVerticalMargin * loopView.maxTextHeight;
            offset = (int) ((offset + itemHeight) % itemHeight);
            if ((float) offset > itemHeight / 2.0F) {
                realTotalOffset = (int) (itemHeight - (float) offset);
            } else {
                realTotalOffset = -offset;
            }
        }
        realOffset = (int) ((float) realTotalOffset * 0.4F);
        if (realOffset == 0) {
            if (realTotalOffset < 0) {
                realOffset = -1;
            } else {
                realOffset = 1;
            }
        }
        if (Math.abs(realTotalOffset) <= 0) {
            loopView.cancelFuture();
            loopView.loopViewHandler.sendMessage(loopView.loopViewHandler.obtainMessage(EasyLoopViewHandler.H_3000));
        } else {
            loopView.totalScrollY = loopView.totalScrollY + realOffset;
            loopView.loopViewHandler.sendMessage(loopView.loopViewHandler.obtainMessage(EasyLoopViewHandler.H_1000));
            realTotalOffset = realTotalOffset - realOffset;
        }
    }
}
