package com.jen.easyui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easy.bind.EasyBind;


/**
 * 用Build创建
 * 只有一个按钮时，用左边按钮
 * 没有值时控件会隐藏，比如icon==null则隐藏图标
 * 作者：ShannJenn
 * 时间：2018/1/15.
 */
public abstract class EasyDialogCustom extends EasyDialogFactory {
    public Context context;

    public EasyDialogCustom(Context context) {
        super(context);
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

    @Override
    protected void onTouchOutside() {
        if(touchOutsideHideInputMethod){

        }
    }

    protected abstract int bindLayout();

    protected abstract void initView(View layout);

    @Override
    public void show() {
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