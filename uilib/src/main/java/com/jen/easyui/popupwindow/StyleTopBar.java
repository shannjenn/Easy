package com.jen.easyui.popupwindow;

public class StyleTopBar {

    private ShowLeft showLeft;
    private ShowRight showRight;
    private ShowTitle showTitle;

    private String leftText;
    private String TitleText;
    private String RightText;

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
        showLeft = ShowLeft.IMAGE;
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
        return TitleText == null ? "" : TitleText;
    }

    public String getRightText() {
        return RightText == null ? "" : RightText;
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


    //setter========================================================================================
    public StyleTopBar setLeftText(String leftText) {
        this.leftText = leftText;
        return this;
    }

    public StyleTopBar setTitleText(String titleText) {
        TitleText = titleText;
        return this;
    }

    public StyleTopBar setRightText(String rightText) {
        RightText = rightText;
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
}
