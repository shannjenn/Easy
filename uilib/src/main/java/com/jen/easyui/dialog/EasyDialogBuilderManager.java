package com.jen.easyui.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jen.easyui.R;
import com.jen.easyui.dialog.imp.EasyDialogListener;

/**
 * 作者：ShannJenn
 * 时间：2018/1/15.
 */

abstract class EasyDialogBuilderManager {
    private Context context;

    private float width;//宽度(db)
    private float height;//高度(db)

    private Drawable icon;
    private String txtTitle;
    private String txtContent;
    private String txtLeft;
    private String txtMiddle;
    private String txtRight;

    private EasyDialogListener easyDialogListener;

    public EasyDialogBuilderManager(Context context) {
        this.context = context;
    }

    public EasyDialogBuilderManager setIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }

    public EasyDialogBuilderManager setTitle(String txt) {
        txtTitle = txt;
        return this;
    }

    public EasyDialogBuilderManager setContent(String txt) {
        txtContent = txt;
        return this;
    }

    public EasyDialogBuilderManager setLeftButton(String txt) {
        txtLeft = txt;
        return this;
    }

    public EasyDialogBuilderManager setMiddleButton(String txt) {
        txtMiddle = txt;
        return this;
    }

    public EasyDialogBuilderManager setRightButton(String txt) {
        txtRight = txt;
        return this;
    }

    public EasyDialogBuilderManager setEasyDialogListener(EasyDialogListener easyDialogListener) {
        this.easyDialogListener = easyDialogListener;
        return this;
    }

    public EasyDialogBuilderManager setWidth(float width) {
        this.width = width;
        return this;
    }

    public EasyDialogBuilderManager setHeight(float height) {
        this.height = height;
        return this;
    }

    public EasyDialog create() {
        EasyDialog dialog = new EasyDialog(context, R.style._easy_dialog);
        dialog.setIcon(icon);
        dialog.setTxtTile(txtTitle);
        dialog.setTxtContent(txtContent);
        dialog.setTxtLeft(txtLeft);
        dialog.setTxtMiddle(txtMiddle);
        dialog.setTxtRight(txtRight);
        dialog.setEasyDialogListener(easyDialogListener);

        dialog.setWidth(width);
        dialog.setHeight(height);

        dialog.initViews();
        return dialog;
    }

}
