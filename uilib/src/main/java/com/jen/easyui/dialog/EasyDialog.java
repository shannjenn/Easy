package com.jen.easyui.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import com.jen.easyui.dialog.imp.EasyDialogListener;

/**
 * 用Build创建
 * 只有一个按钮时，用左边按钮
 * 没有值时控件会隐藏，比如icon==null则隐藏图标
 * 作者：ShannJenn
 * 时间：2018/1/15.
 */
public class EasyDialog extends EasyDialogManager {
    EasyDialog(@NonNull Context context) {
        super(context);
    }

    EasyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    EasyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void setEasyDialogListener(EasyDialogListener easyDialogListener) {
        super.setEasyDialogListener(easyDialogListener);
    }


    /**
     * 用与创建EasyDialog
     * 作者：ShannJenn
     * 时间：2018/1/15.
     */
    public static class Build extends EasyDialogBuilderManager {

        public Build(Context context) {
            super(context);
        }

        @Override
        public Build setIcon(Drawable icon) {
            super.setIcon(icon);
            return this;
        }

        @Override
        public Build setTitle(String txt) {
            super.setTitle(txt);
            return this;
        }

        @Override
        public Build setContent(String txt) {
            super.setContent(txt);
            return this;
        }

        @Override
        public Build setLeftButton(String txt) {
            super.setLeftButton(txt);
            return this;
        }

        @Override
        public Build setMiddleButton(String txt) {
            super.setMiddleButton(txt);
            return this;
        }

        @Override
        public Build setRightButton(String txt) {
            super.setRightButton(txt);
            return this;
        }

        @Override
        public Build setEasyDialogListener(EasyDialogListener easyDialogListener) {
            super.setEasyDialogListener(easyDialogListener);
            return this;
        }

        @Override
        public EasyDialog create() {
            return super.create();
        }
    }
}