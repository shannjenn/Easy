package com.jen.easyui.view.baseview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.View;

/**
 * 密码输入框 ●●●●●
 */
public abstract class EasyPasswordEditText extends android.support.v7.widget.AppCompatEditText {

    public EasyPasswordEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EasyPasswordEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setTransformationMethod(new TransformationMethod() {
            @Override
            public CharSequence getTransformation(CharSequence source, View view) {
                return new PasswordCharSequence(source);
            }

            @Override
            public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {

            }
        });
    }

    /**
     * 将密码转换成*显示
     */
    private class PasswordCharSequence implements CharSequence {
        private CharSequence mSource;

        PasswordCharSequence(CharSequence source) {
            mSource = source; // Store char sequence
        }

        public char charAt(int index) {
//            return '●';
            return password();
        }

        public int length() {
            return mSource.length();
        }

        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end); // Return default
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    protected abstract char password();
}
