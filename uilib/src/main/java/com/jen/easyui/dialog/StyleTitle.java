package com.jen.easyui.dialog;


import android.view.Gravity;

import com.jen.easyui.util.DensityUtil;

public class StyleTitle {
    private int gravity;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;

    public StyleTitle() {
        gravity = Gravity.CENTER;
        paddingLeft = DensityUtil.dp2pxInt(40);
        paddingRight = DensityUtil.dp2pxInt(40);
        paddingTop = DensityUtil.dp2pxInt(20);
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
    public StyleTitle setGravity(int gravity) {
        this.gravity = gravity;
        return this;

    }

    public StyleTitle setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;

    }

    public StyleTitle setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;

    }

    public StyleTitle setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        return this;

    }

    public StyleTitle setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        return this;

    }
}
