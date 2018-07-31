package com.jen.easyui.timepick;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.PopupWindow;

/**
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
class EasyTimePickAnimationListener implements AnimationListener {
    private PopupWindow popupWindow;

    EasyTimePickAnimationListener(PopupWindow popupWindow) {
        this.popupWindow = popupWindow;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        popupWindow.dismiss();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

}
