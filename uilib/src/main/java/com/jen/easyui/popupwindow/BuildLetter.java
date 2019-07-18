package com.jen.easyui.popupwindow;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.jen.easyui.popupwindow.listener.WindowDismissListener;
import com.jen.easyui.popupwindow.listener.WindowTopBarListener;
import com.jen.easyui.recycler.letter.EasyLetterItem;

import java.util.List;


/**
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
public class BuildLetter<T extends EasyLetterItem> extends Build<T> {
    List<T> data;

    public BuildLetter(Context context) {
        super(context);
    }

    /**
     * 字母列表
     *
     * @return .
     */
    public EasyWindowLetter<T> createLetter(EasyWindowAdapter<T> adapter) {
        return new EasyWindowLetter<>(this, adapter);
    }

    @Override
    public BuildLetter<T> setFlagCode(int flagCode) {
        super.setFlagCode(flagCode);
        return this;
    }

    @Override
    public BuildLetter<T> setShowTopBar(boolean showTopBar) {
        super.setShowTopBar(showTopBar);
        return this;
    }

    @Override
    public BuildLetter<T> setHeight(int height) {
        super.setHeight(height);
        return this;
    }

    @Override
    public BuildLetter<T> setWidth(int width) {
        super.setWidth(width);
        return this;
    }

    @Override
    public BuildLetter<T> setTopBarListener(WindowTopBarListener topBarListener) {
        super.setTopBarListener(topBarListener);
        return this;
    }

    @Override
    public BuildLetter<T> setDismissListener(WindowDismissListener dismissListener) {
        super.setDismissListener(dismissListener);
        return this;
    }

    @Override
    public BuildLetter<T> setStyleTopBar(StyleTopBar styleTopBar) {
        super.setStyleTopBar(styleTopBar);
        return this;
    }

    @Override
    public BuildLetter<T> setStyleAnim(StyleAnim animStyle) {
        super.setStyleAnim(animStyle);
        return this;
    }

    @Override
    public BuildLetter<T> setShowAlpha(float showAlpha) {
        super.setShowAlpha(showAlpha);
        return this;
    }

    @Override
    public BuildLetter<T> setBackground(Drawable background) {
        super.setBackground(background);
        return this;
    }

    @Override
    public BuildLetter<T> setData(List<T> data) {
        super.setData(data);
        return this;
    }
}
