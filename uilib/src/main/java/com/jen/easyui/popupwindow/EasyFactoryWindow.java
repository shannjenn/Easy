package com.jen.easyui.popupwindow;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.jen.easyui.util.EasyDisplayUtil;

/**
 * 说明：
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

abstract class EasyFactoryWindow extends PopupWindow {
    private Context context;
    private float showAlpha = 0.5f;
    private Drawable background;

    EasyFactoryWindow(Context context) {
        this.context = context;
        initWindow();
    }

    @Override
    public void setContentView(View contentView) {
        super.setContentView(contentView);
    }

    /**
     * 初始化信息
     */
    private void initWindow() {
        setOutsideTouchable(true);  //默认设置outside点击无响应
        setFocusable(true);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    protected abstract int animation();

    private void showWindow() {
        setAnimationStyle(animation());
        showAnimator().start();
    }

    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(touchable);
        if (touchable) {
            if (background == null) {
                background = new ColorDrawable(0x00000000);
            }
            super.setBackgroundDrawable(background);
        } else {
            super.setBackgroundDrawable(null);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        this.background = background;
        setOutsideTouchable(isOutsideTouchable());
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        showWindow();
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void showAsDropDown(View anchor) {
        showWindow();
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        showWindow();
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        showWindow();
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        dismissAnimator().start();
    }

    /**
     * 窗口显示时，窗口背景透明度渐变动画
     */
    private ValueAnimator showAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, showAlpha);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                setWindowBackgroundAlpha(alpha);
            }
        });
        animator.setDuration(300);//要与动画 easy_popup_window_anim_style 时间一致
        return animator;
    }

    /**
     * 窗口隐藏时，窗口背景透明度渐变动画
     */
    private ValueAnimator dismissAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(showAlpha, 1.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                setWindowBackgroundAlpha(alpha);
            }
        });
        animator.setDuration(200);//要与动画 easy_popup_window_anim_style 时间一致
        return animator;
    }

    /**
     * 控制窗口背景的不透明度
     */
    private void setWindowBackgroundAlpha(float alpha) {
        EasyDisplayUtil.setBackgroundAlpha((Activity) context, alpha);
    }

    public void setShowAlpha(float alpha) {
        showAlpha = alpha;
    }
}
