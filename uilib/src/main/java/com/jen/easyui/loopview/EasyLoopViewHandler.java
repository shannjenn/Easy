package com.jen.easyui.loopview;

import android.os.Handler;
import android.os.Message;


/**
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
final class EasyLoopViewHandler extends Handler {
    static final int H_1000 = 1000;
    static final int H_2000 = 2000;
    static final int H_3000 = 3000;
    final EasyLoopView loopview;

    EasyLoopViewHandler(EasyLoopView loopview) {
        super();
        this.loopview = loopview;
    }

    @Override
    public final void handleMessage(Message msg) {
        if (msg.what == H_1000)
            this.loopview.invalidate();
        while (true) {
            if (msg.what == H_2000)
                loopview.smoothScroll();
            else if (msg.what == H_3000)
                this.loopview.itemSelected();
            super.handleMessage(msg);
            return;
        }
    }

}
