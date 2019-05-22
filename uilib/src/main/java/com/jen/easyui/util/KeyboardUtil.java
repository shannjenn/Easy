package com.jen.easyui.util;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

public class KeyboardUtil {
    /**
     * 虚拟键盘高度
     */
    private int virtualKeyboardHeight;
    /**
     * 屏幕高度
     */
    private int screenHeight;
    /**
     * 屏幕6分之一的高度，作用是防止获取到虚拟键盘的高度
     */
    private int screenHeight6;
    private View rootView;

    public KeyboardUtil(Activity context) {
        //获取屏幕的高度,该方式的获取不包含虚拟键盘
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        screenHeight6 = screenHeight / 6;
        rootView = context.getWindow().getDecorView();
    }

    /**
     * @param listener .
     */
    public void setOnKeyboardChangeListener(final KeyboardChangeListener listener) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                //回调该方法时rootView还未绘制，需要设置绘制完成监听
                rootView.post(new Runnable() {
                    @Override
                    public void run() {
                        Rect rect = new Rect();
                        //获取屏幕底部坐标
                        rootView.getWindowVisibleDisplayFrame(rect);
                        //获取键盘的高度
                        int heightDifference = screenHeight - rect.bottom;
                        if (heightDifference < screenHeight6) {
                            virtualKeyboardHeight = heightDifference;
                            if (listener != null) {
                                listener.onKeyboardHide();
                            }
                        } else {
                            if (listener != null) {
                                listener.onKeyboardShow(heightDifference - virtualKeyboardHeight);
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 软键盘状态切换监听
     */
    public interface KeyboardChangeListener {
        /**
         * 键盘弹出
         *
         * @param keyboardHeight 键盘高度
         */
        void onKeyboardShow(int keyboardHeight);

        /**
         * 键盘隐藏
         */
        void onKeyboardHide();
    }
}
