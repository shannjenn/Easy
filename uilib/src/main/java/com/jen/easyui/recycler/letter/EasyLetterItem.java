package com.jen.easyui.recycler.letter;

import android.support.annotation.NonNull;

/**
 * 0为最高级
 * 作者：ShannJenn
 * 时间：2018/03/21.
 */

public abstract class EasyLetterItem implements Comparable<EasyLetterItem> {
    private String letter;
    private boolean hidden;

    public String getLetter() {
        if (letter == null) {
            return "";
        }
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

    @Override
    public int compareTo(@NonNull EasyLetterItem another) {
        return this.getLetter().compareTo(another.getLetter());
    }
}
