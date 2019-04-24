package com.jen.easyui.dialog;

import com.jen.easyui.util.EasyDensityUtil;

public class StyleButtons {
    private int buttonsMarginTop;
    private int buttonsMarginBottom;

    private int leftButtonMarginLeft;
    private int leftButtonMarginRight;

    private int rightButtonMarginLeft;
    private int rightButtonMarginRight;

    private int leftButtonCorners;//左边按钮 所有角(优先判断:为0时再设置其他角)
    private int leftButtonCornerLeftTop;//左边按钮 左上角
    private int leftButtonCornerLeftBottom;//左边按钮 左下角
    private int leftButtonCornerRightTop;//左边按钮
    private int leftButtonCornerRightBottom;//左边按钮

    private int rightButtonCorners;//右边按钮 所有角(优先判断:为0时再设置其他角)
    private int rightButtonCornerLeftTop;//右边按钮 左上角
    private int rightButtonCornerLeftBottom;//右边按钮 左下角
    private int rightButtonCornerRightTop;//右边按钮
    private int rightButtonCornerRightBottom;//右边按钮

    public StyleButtons() {
        //设置默认值
        buttonsMarginTop = EasyDensityUtil.dp2pxInt(20);
        buttonsMarginBottom = EasyDensityUtil.dp2pxInt(20);
        leftButtonMarginLeft = EasyDensityUtil.dp2pxInt(20);
        rightButtonMarginRight = EasyDensityUtil.dp2pxInt(20);
        leftButtonMarginRight = EasyDensityUtil.dp2pxInt(5);
        rightButtonMarginLeft = EasyDensityUtil.dp2pxInt(5);
        leftButtonCorners = 4;
        rightButtonCorners = 4;
    }

    private int db2px(int db) {
        return EasyDensityUtil.dp2pxInt(db);
    }

    //getter================================================================================================
    public int getButtonsMarginTop() {
        return buttonsMarginTop;
    }

    public int getButtonsMarginBottom() {
        return buttonsMarginBottom;
    }

    public int getLeftButtonMarginLeft() {
        return leftButtonMarginLeft;
    }

    public int getLeftButtonMarginRight() {
        return leftButtonMarginRight;
    }

    public int getRightButtonMarginLeft() {
        return rightButtonMarginLeft;
    }

    public int getRightButtonMarginRight() {
        return rightButtonMarginRight;
    }

    public int getLeftButtonCorners() {
        return leftButtonCorners;
    }

    public int getLeftButtonCornerLeftTop() {
        return leftButtonCornerLeftTop;
    }

    public int getLeftButtonCornerLeftBottom() {
        return leftButtonCornerLeftBottom;
    }

    public int getLeftButtonCornerRightTop() {
        return leftButtonCornerRightTop;
    }

    public int getLeftButtonCornerRightBottom() {
        return leftButtonCornerRightBottom;
    }

    public int getRightButtonCorners() {
        return rightButtonCorners;
    }

    public int getRightButtonCornerLeftTop() {
        return rightButtonCornerLeftTop;
    }

    public int getRightButtonCornerLeftBottom() {
        return rightButtonCornerLeftBottom;
    }

    public int getRightButtonCornerRightTop() {
        return rightButtonCornerRightTop;
    }

    public int getRightButtonCornerRightBottom() {
        return rightButtonCornerRightBottom;
    }

    //setter  margin================================================================================================

    /**
     * @param buttonsMarginTop db值
     */
    public StyleButtons setButtonsMarginTop(int buttonsMarginTop) {
        this.buttonsMarginTop = db2px(buttonsMarginTop);
        return this;
    }

    /**
     * @param buttonsMarginBottom db值
     */
    public StyleButtons setButtonsMarginBottom(int buttonsMarginBottom) {
        this.buttonsMarginBottom = db2px(buttonsMarginBottom);
        return this;
    }

    /**
     * @param leftButtonMarginLeft db值
     */
    public StyleButtons setLeftButtonMarginLeft(int leftButtonMarginLeft) {
        this.leftButtonMarginLeft = db2px(leftButtonMarginLeft);
        return this;
    }

    /**
     * @param leftButtonMarginRight db值
     */
    public StyleButtons setLeftButtonMarginRight(int leftButtonMarginRight) {
        this.leftButtonMarginRight = db2px(leftButtonMarginRight);
        return this;
    }

    /**
     * @param rightButtonMarginLeft db值
     */
    public StyleButtons setRightButtonMarginLeft(int rightButtonMarginLeft) {
        this.rightButtonMarginLeft = db2px(rightButtonMarginLeft);
        return this;
    }

    /**
     * @param rightButtonMarginRight db值
     */
    public StyleButtons setRightButtonMarginRight(int rightButtonMarginRight) {
        this.rightButtonMarginRight = db2px(rightButtonMarginRight);
        return this;
    }

    //setter  corners================================================================================================


    public StyleButtons setLeftButtonCorners(int leftButtonCorners) {
        this.leftButtonCorners = leftButtonCorners;
        return this;
    }

    public StyleButtons setLeftButtonCornerLeftTop(int leftButtonCornerLeftTop) {
        this.leftButtonCornerLeftTop = leftButtonCornerLeftTop;
        return this;
    }

    public StyleButtons setLeftButtonCornerLeftBottom(int leftButtonCornerLeftBottom) {
        this.leftButtonCornerLeftBottom = leftButtonCornerLeftBottom;
        return this;
    }

    public StyleButtons setLeftButtonCornerRightTop(int leftButtonCornerRightTop) {
        this.leftButtonCornerRightTop = leftButtonCornerRightTop;
        return this;
    }

    public StyleButtons setLeftButtonCornerRightBottom(int leftButtonCornerRightBottom) {
        this.leftButtonCornerRightBottom = leftButtonCornerRightBottom;
        return this;
    }

    public StyleButtons setRightButtonCorners(int rightButtonCorners) {
        this.rightButtonCorners = rightButtonCorners;
        return this;
    }

    public StyleButtons setRightButtonCornerLeftTop(int rightButtonCornerLeftTop) {
        this.rightButtonCornerLeftTop = rightButtonCornerLeftTop;
        return this;
    }

    public StyleButtons setRightButtonCornerLeftBottom(int rightButtonCornerLeftBottom) {
        this.rightButtonCornerLeftBottom = rightButtonCornerLeftBottom;
        return this;
    }

    public StyleButtons setRightButtonCornerRightTop(int rightButtonCornerRightTop) {
        this.rightButtonCornerRightTop = rightButtonCornerRightTop;
        return this;
    }

    public StyleButtons setRightButtonCornerRightBottom(int rightButtonCornerRightBottom) {
        this.rightButtonCornerRightBottom = rightButtonCornerRightBottom;
        return this;
    }
}
