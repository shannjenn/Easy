package com.jen.easyui.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jen.easyui.R;
import com.jen.easyui.dialog.imp.EasyDialogListener;

/**
 * 作者：ShannJenn
 * 时间：2018/1/15.
 */

abstract class EasyDialogBuilder {
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

    public EasyDialogBuilder(Context context) {
        this.context = context;
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

    public EasyDialogBuilder setIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }

    public EasyDialogBuilder setTitle(String txt) {
        txtTitle = txt;
        return this;
    }

    public EasyDialogBuilder setContent(String txt) {
        txtContent = txt;
        return this;
    }

    public EasyDialogBuilder setLeftButton(String txt) {
        txtLeft = txt;
        return this;
    }

    public EasyDialogBuilder setMiddleButton(String txt) {
        txtMiddle = txt;
        return this;
    }

    public EasyDialogBuilder setRightButton(String txt) {
        txtRight = txt;
        return this;
    }

    public EasyDialogBuilder setEasyDialogListener(EasyDialogListener easyDialogListener) {
        this.easyDialogListener = easyDialogListener;
        return this;
    }

    public EasyDialogBuilder setWidth(float width) {
        this.width = width;
        return this;
    }

    public EasyDialogBuilder setHeight(float height) {
        this.height = height;
        return this;
    }

}
