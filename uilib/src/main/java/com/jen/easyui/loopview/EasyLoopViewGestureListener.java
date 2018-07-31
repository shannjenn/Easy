package com.jen.easyui.loopview;

import android.view.MotionEvent;

/**
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
final class EasyLoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

    final EasyLoopView loopView;

    EasyLoopViewGestureListener(EasyLoopView loopview) {
        super();
        loopView = loopview;
    }

    @Override
    public final boolean onDown(MotionEvent motionevent) {
        loopView.cancelFuture();
        return true;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        loopView.smoothScroll(velocityY * 1.2f);
        return true;
    }
}
