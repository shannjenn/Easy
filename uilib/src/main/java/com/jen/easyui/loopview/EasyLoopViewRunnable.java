package com.jen.easyui.loopview;

/**
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
final class EasyLoopViewRunnable implements Runnable {
    private final EasyLoopView loopView;

    EasyLoopViewRunnable(EasyLoopView loopview) {
        super();
        loopView = loopview;

    }

    @Override
    public final void run() {
        EasyLoopViewListener listener = loopView.loopListener;
        int selectedItem = loopView.getSelectedItem();
        loopView.arrayList.get(selectedItem);
        listener.onItemSelect(loopView, selectedItem);
    }
}
