package com.jen.easyui.dialog;

import android.view.Gravity;

import com.jen.easyui.util.EasyDensityUtil;

public class StyleContent {
    private int gravity;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;

    public StyleContent() {
        gravity = Gravity.START;
        paddingLeft = EasyDensityUtil.dp2pxInt(20);
        paddingRight = EasyDensityUtil.dp2pxInt(20);
        paddingTop = EasyDensityUtil.dp2pxInt(20);
    }

    public int getGravity() {
        return gravity;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }


    //setter=========================================================
    public StyleContent setGravity(int gravity) {
        this.gravity = gravity;
        return this;

    }

    public StyleContent setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;

    }

    public StyleContent setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;

    }

    public StyleContent setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        return this;

    }

    public StyleContent setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;

    }
}
