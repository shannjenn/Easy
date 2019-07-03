package com.jen.easyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jen.easy.bind.EasyBind;
import com.jen.easyui.R;


/**
 * 作者：ShannJenn
 * 时间：2018/1/15.
 */
public abstract class EasyDialogCustom extends Dialog {
    public Context context;

    public EasyDialogCustom(Context context) {
        super(context, R.style._easy_dialog);
        this.context = context;
        init();
    }

    private void init() {
        View layout = LayoutInflater.from(context).inflate(bindLayout(), null);
        EasyBind easyBind = new EasyBind();
        easyBind.inject(this, layout);
        initView(layout);
        setContentView(layout);
    }

    protected abstract int bindLayout();

    protected abstract void initView(View layout);

    @Override
    public void show() {
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void hide() {
        super.hide();
    }

}