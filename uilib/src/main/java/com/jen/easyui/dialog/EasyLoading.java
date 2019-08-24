package com.jen.easyui.dialog;

import android.app.Application;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.jen.easyui.R;


/**
 * 作者：ShannJenn
 * 时间：2018/1/15.
 */
public abstract class EasyLoading extends Dialog {

    public EasyLoading(Application context) {
        super(context, R.style._easy_dialog_loading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = bindLayout();
        if (layoutId == 0) {
            setContentView(R.layout._easy_dialog_loading);
        } else {
            setContentView(layoutId);
        }
        Window window = getWindow();
        if (window != null) {
            //全局，需要增加权限<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
    }

    protected abstract int bindLayout();

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }

    @Override
    public void show() {
        if (!isShowing())
            super.show();
    }

    @Override
    public void dismiss() {
        if (isShowing())
            super.dismiss();
    }

}
