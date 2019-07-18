package com.jen.easyui.popupwindow;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
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

abstract class EasyWindowFactory<T> extends PopupWindow {
    protected Build<T> build;
    private float windowHeight;
    private float windowWidth;
    private int showDurationTime = 300;
    private int hideDurationTime = 200;

    EasyWindowFactory(Build<T> build) {
        this.build = build;
        initWindow();
    }

    @Override
    public void setContentView(View contentView) {
        super.setContentView(contentView);
        windowHeight = 0;//重置宽高
        windowWidth = 0;
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

    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(touchable);
        if (touchable) {
            if (build.background == null) {
                build.background = new ColorDrawable(0x00000000);
            }
            super.setBackgroundDrawable(build.background);
        } else {
            super.setBackgroundDrawable(null);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        build.background = background;
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

    private void showWindow() {
        propertyAnimator();
        alphaAnimator(true, showDurationTime).start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        alphaAnimator(false, hideDurationTime).start();
    }


    /**
     * 属性动画
     */
    private void propertyAnimator() {
        if (windowHeight <= 0) {
            getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            if (build.height > 0) {
                windowHeight = build.height;
            } else {
                windowHeight = getContentView().getMeasuredHeight();
            }
            if (build.width > 0) {
                windowWidth = build.width;
            } else {
                windowWidth = getContentView().getMeasuredWidth();
            }
        }
        PropertyValuesHolder propertyValuesHolder;
        switch (build.styleAnim) {
            case BOTTOM:
                propertyValuesHolder = PropertyValuesHolder.ofFloat("translationY", windowHeight, 0);
                break;
            case DROP:
                propertyValuesHolder = PropertyValuesHolder.ofFloat("translationY", -windowHeight, 0);
                break;
            case RIGHT:
                propertyValuesHolder = PropertyValuesHolder.ofFloat("translationX", windowWidth, 0);
                break;
            default:
                propertyValuesHolder = PropertyValuesHolder.ofFloat("translationY", 0, windowHeight);
                break;
        }
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(getContentView(), propertyValuesHolder);
        animator.setDuration(showDurationTime);
        animator.start();
    }

    /**
     * 窗口显示时，窗口背景透明度渐变动画
     */
    private ValueAnimator alphaAnimator(boolean isShow, int time) {
        ValueAnimator animator = ValueAnimator.ofFloat(isShow ? 1.0f : build.showAlpha, isShow ? build.showAlpha : 1.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setWindowBackgroundAlpha((float) animation.getAnimatedValue());
            }
        });
        animator.setDuration(time);//要与属性动画时间一致
        return animator;
    }

    /**
     * 控制窗口背景的不透明度
     */
    private void setWindowBackgroundAlpha(float alpha) {
        EasyDisplayUtil.setBackgroundAlpha((Activity) build.context, alpha);
    }

    public void setShowAlpha(float alpha) {
        build.showAlpha = alpha;
    }

    public void setShowDurationTime(int showDurationTime) {
        this.showDurationTime = showDurationTime;
    }

    public void setHideDurationTime(int hideDurationTime) {
        this.hideDurationTime = hideDurationTime;
    }
}
