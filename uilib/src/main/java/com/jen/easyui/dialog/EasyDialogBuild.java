package com.jen.easyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public class EasyDialogBuild extends EasyDialogBuilderImp {


    public EasyDialogBuild(Context context) {
        super(context);
    }

    @Override
    public EasyDialogBuilderImp setContent(String txt) {
        return super.setContent(txt);
    }

    @Override
    public EasyDialogBuilderImp setPositiveButton(String txt) {
        return super.setPositiveButton(txt);
    }

    @Override
    public EasyDialogBuilderImp setNegativeButton(String txt) {
        return super.setNegativeButton(txt);
    }

    @Override
    public EasyDialogBuilderImp setFlagCode(int flagCode) {
        return super.setFlagCode(flagCode);
    }

    @Override
    public EasyDialogBuilderImp setFlag(String flag) {
        return super.setFlag(flag);
    }

    @Override
    public void setDialogOnclick(DialogOnclick dialogOnclick) {
        super.setDialogOnclick(dialogOnclick);
    }

    @Override
    public Dialog create() {
        return super.create();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }
}
