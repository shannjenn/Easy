package com.jen.easyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public class EasyDialogBuild extends EasyDialogBuilderManager {


    public EasyDialogBuild(Context context) {
        super(context);
    }

    @Override
    public EasyDialogBuilderManager setContent(String txt) {
        return super.setContent(txt);
    }

    @Override
    public EasyDialogBuilderManager setPositiveButton(String txt) {
        return super.setPositiveButton(txt);
    }

    @Override
    public EasyDialogBuilderManager setNegativeButton(String txt) {
        return super.setNegativeButton(txt);
    }

    @Override
    public EasyDialogBuilderManager setFlagCode(int flagCode) {
        return super.setFlagCode(flagCode);
    }

    @Override
    public EasyDialogBuilderManager setFlag(String flag) {
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
