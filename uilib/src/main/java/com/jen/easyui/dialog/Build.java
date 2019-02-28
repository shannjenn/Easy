package com.jen.easyui.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jen.easyui.R;


public class Build {
    private Context context;

    private Drawable iconLeft;
    private Drawable iconRight;
    private String txtTitle;
    private GravityTitle titleGravity = GravityTitle.Left;
    private CharSequence txtContent;
    private String txtCheckBox;
    private GravityContent contentGravity = GravityContent.Center;
    private String txtLeft;
    private String txtMiddle;
    private String txtRight;

    private int flagCode;
    private DialogListener listener;

    Build(Context context) {
        this.context = context;
    }

    public EasyDialog create() {
        EasyDialog dialog = new EasyDialog(context, R.style._easy_dialog);
        dialog.setIconLeft(iconLeft);
        dialog.setIconRight(iconRight);
        dialog.setTxtTile(txtTitle);
        dialog.setTxtContent(txtContent);
        dialog.setTxtCheckBox(txtCheckBox);
        dialog.setTxtLeft(txtLeft);
        dialog.setTxtMiddle(txtMiddle);
        dialog.setTxtRight(txtRight);
        dialog.setListener(listener);
        dialog.setFlagCode(flagCode);
        dialog.setTitleGravity(titleGravity);
        dialog.setContentGravity(contentGravity);

        dialog.initViews();
        return dialog;
    }

    public Build setIconLeft(Drawable iconLeft) {
        this.iconLeft = iconLeft;
        return this;
    }

    public Build setIconRight(Drawable iconRight) {
        this.iconRight = iconRight;
        return this;
    }

    public Build setTitle(String txt) {
        txtTitle = txt;
        return this;
    }

    public Build setTitleGravity(GravityTitle titleGravity) {
        this.titleGravity = titleGravity;
        return this;
    }

    public Build setContent(CharSequence txt) {
        txtContent = txt;
        return this;
    }

    public Build setTxtCheckBox(String txtCheckBox) {
        this.txtCheckBox = txtCheckBox;
        return this;
    }

    public Build setContentGravity(GravityContent contentGravity) {
        this.contentGravity = contentGravity;
        return this;
    }

    public Build setLeftButton(String txt) {
        txtLeft = txt;
        return this;
    }

    public Build setMiddleButton(String txt) {
        txtMiddle = txt;
        return this;
    }

    public Build setRightButton(String txt) {
        txtRight = txt;
        return this;
    }

    public Build setListener(DialogListener listener) {
        this.listener = listener;
        return this;
    }

    public Build setFlagCode(int flagCode) {
        this.flagCode = flagCode;
        return this;
    }
}
