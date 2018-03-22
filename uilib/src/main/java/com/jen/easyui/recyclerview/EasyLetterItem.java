package com.jen.easyui.recyclerview;

/**
 * 0为最高级
 * 作者：ShannJenn
 * 时间：2018/03/21.
 */

public abstract class EasyLetterItem {
    private String letter = "";
    private boolean hidden;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        if (letter == null)
            return;
        this.letter = letter;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
