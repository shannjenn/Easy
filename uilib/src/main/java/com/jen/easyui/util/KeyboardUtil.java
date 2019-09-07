package com.jen.easyui.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jen.easy.log.EasyLog;

import java.util.Objects;

public class KeyboardUtil {

    public KeyboardUtil() {

    }

    /**
     * 判定当前是否需要隐藏(点击EditText外面时候隐藏)
     */
    public static boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(Activity activity) {
        Object imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm instanceof InputMethodManager && ((InputMethodManager) imm).isActive()) {
            ((InputMethodManager) imm).hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(Dialog dialog) {
        Object imm = dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm instanceof InputMethodManager && ((InputMethodManager) imm).isActive()) {
            ((InputMethodManager) imm).hideSoftInputFromWindow(Objects.requireNonNull(dialog.getWindow()).getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * @param listener .
     */
    public static void setOnKeyboardChangeListener(Activity activity, final KeyboardChangeListener listener) {
        final int[] virtualKeyboardHeight = new int[1];//获取屏幕的高度,该方式的获取不包含虚拟键盘
        final int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        final int screenHeight6 = screenHeight / 6;//屏幕6分之一的高度，作用是防止获取到虚拟键盘的高度
        final View rootView = activity.getWindow().getDecorView();

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
                            virtualKeyboardHeight[0] = heightDifference;
                            if (listener != null) {
                                listener.onKeyboardHide();
                            }
                        } else {
                            if (listener != null) {
                                listener.onKeyboardShow(heightDifference - virtualKeyboardHeight[0]);
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * @param listener .
     */
    public static void setOnKeyboardChangeListener(Dialog dialog, final KeyboardChangeListener listener) {
        final int[] virtualKeyboardHeight = new int[1];//获取屏幕的高度,该方式的获取不包含虚拟键盘
        final int screenHeight = dialog.getContext().getResources().getDisplayMetrics().heightPixels;
        final int screenHeight6 = screenHeight / 6;//屏幕6分之一的高度，作用是防止获取到虚拟键盘的高度
        Window window = dialog.getWindow();
        if (window == null) {
            EasyLog.e("KeyboardUtil setOnKeyboardChangeListener window == null");
            return;
        }
        final View rootView = window.getDecorView();

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
                            virtualKeyboardHeight[0] = heightDifference;
                            if (listener != null) {
                                listener.onKeyboardHide();
                            }
                        } else {
                            if (listener != null) {
                                listener.onKeyboardShow(heightDifference - virtualKeyboardHeight[0]);
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
    public abstract static class KeyboardChangeListener {
        /**
         * 键盘弹出
         *
         * @param keyboardHeight 键盘高度
         */
        protected void onKeyboardShow(int keyboardHeight) {
        }

        /**
         * 键盘隐藏
         */
        protected void onKeyboardHide() {
        }
    }
}
