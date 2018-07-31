package com.jen.easyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ProgressBar;

import com.jen.easyui.R;


/**
 * 作者：ShannJenn
 * 时间：2018/1/15.
 */
public class EasyLoading extends Dialog {
    private Context context;
    private ProgressBar progressBar;//预留后期改进

    public EasyLoading(Context context) {
        super(context, R.style._easy_dialog_loading);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._easy_dialog_loading);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            dismiss();
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
