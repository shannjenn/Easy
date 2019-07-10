package com.jen.easyui.popupwindow;

public class StyleTopBar {

    private ShowLeft showLeft;
    private ShowRight showRight;
    private ShowTitle showTitle;

    private String leftText = "取消";
    private String titleText = "标题";
    private String rightText = "确定";

    private int leftTextSize = 22;
    private int titleTextSize = 24;
    private int rightTextSize = 22;

    private int leftTextColor = 0xff666666;
    private int titleTextColor = 0xff000000;
    private int rightTextColor = 0xff666666;

    private int backgroundColor = 0xFFF4F4F4;

    public enum ShowLeft {
        IMAGE, TEXT, NON//NON为不显示
    }

    public enum ShowRight {
        IMAGE, TEXT, NON
    }

    public enum ShowTitle {
        TEXT, NON
    }

    public StyleTopBar() {
        showLeft = ShowLeft.TEXT;
        showRight = ShowRight.TEXT;
        showTitle = ShowTitle.TEXT;
    }

    public StyleTopBar(ShowLeft showLeft, ShowTitle showTitle, ShowRight showRight) {
        this.showLeft = showLeft;
        this.showTitle = showTitle;
        this.showRight = showRight;
    }

    public String getLeftText() {
        return leftText == null ? "" : leftText;
    }

    public String getTitleText() {
        return titleText == null ? "" : titleText;
    }

    public String getRightText() {
        return rightText == null ? "" : rightText;
    }

    public ShowLeft getShowLeft() {
        return showLeft;
    }

    public ShowRight getShowRight() {
        return showRight;
    }

    public ShowTitle getShowTitle() {
        return showTitle;
    }

    public int getLeftTextSize() {
        return leftTextSize;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public int getRightTextSize() {
        return rightTextSize;
    }

    public int getLeftTextColor() {
        return leftTextColor;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public int getRightTextColor() {
        return rightTextColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    //setter========================================================================================
    public StyleTopBar setLeftText(String leftText) {
        this.leftText = leftText;
        return this;
    }

    public StyleTopBar setTitleText(String titleText) {
        this.titleText = titleText;
        return this;
    }

    public StyleTopBar setRightText(String rightText) {
        this.rightText = rightText;
        return this;
    }

    public StyleTopBar setShowLeft(ShowLeft showLeft) {
        this.showLeft = showLeft;
        return this;
    }

    public StyleTopBar setShowRight(ShowRight showRight) {
        this.showRight = showRight;
        return this;
    }

    public StyleTopBar setShowTitle(ShowTitle showTitle) {
        this.showTitle = showTitle;
        return this;
    }

    public StyleTopBar setLeftTextSize(int leftTextSize) {
        this.leftTextSize = leftTextSize;
        return this;
    }

    public StyleTopBar setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        return this;
    }

    public StyleTopBar setRightTextSize(int rightTextSize) {
        this.rightTextSize = rightTextSize;
        return this;
    }

    public StyleTopBar setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
        return this;
    }

    public StyleTopBar setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        return this;
    }

    public StyleTopBar setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
        return this;
    }

    public StyleTopBar setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
}
