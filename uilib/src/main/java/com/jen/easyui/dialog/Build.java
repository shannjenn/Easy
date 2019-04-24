package com.jen.easyui.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.jen.easyui.R;


public class Build {
    Context context;

    Drawable iconLeft;
    Drawable iconRight;
    CharSequence txtTitle;
    CharSequence txtContent;
    String txtLeft;
    String txtRight;

    StyleTitle styleTitle;
    StyleContent styleContent;
    StyleButtons styleButtons;

    int flagCode;
    DialogListener listener;

    Build(Context context) {
        this.context = context;
    }

    public EasyDialog create() {
        return new EasyDialog(context, this, R.style._easy_dialog);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    static int dp2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public Build setIconLeft(Drawable iconLeft) {
        this.iconLeft = iconLeft;
        return this;
    }

    public Build setIconRight(Drawable iconRight) {
        this.iconRight = iconRight;
        return this;
    }

    public Build setTitle(CharSequence txt) {
        txtTitle = txt;
        return this;
    }

    public Build setContent(CharSequence txt) {
        txtContent = txt;
        return this;
    }

    public Build setLeftButton(String txt) {
        txtLeft = txt;
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


    public Build setStyleTitle(StyleTitle styleTitle) {
        this.styleTitle = styleTitle;
        return this;
    }

    public Build setStyleContent(StyleContent styleContent) {
        this.styleContent = styleContent;
        return this;
    }

    public Build setStyleButtons(StyleButtons styleButtons) {
        this.styleButtons = styleButtons;
        return this;
    }


    public Build setFlagCode(int flagCode) {
        this.flagCode = flagCode;
        return this;
    }
}
