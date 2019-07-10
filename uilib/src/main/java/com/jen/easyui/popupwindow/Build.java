package com.jen.easyui.popupwindow;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

import com.jen.easyui.popupwindow.listener.WindowDismissListener;
import com.jen.easyui.popupwindow.listener.WindowTopBarListener;
import com.jen.easyui.recycler.EasyAdapterFactory;
import com.jen.easyui.recycler.letter.EasyLetterDecoration;
import com.jen.easyui.recycler.letter.EasyLetterItem;


public class Build<T> {
    Context context;
    int height, width;
    float showAlpha = 0.5f;
    Drawable background;
    boolean showTopBar = true;
    StyleTopBar styleTopBar = new StyleTopBar();//默认值
    StyleAnim styleAnim = StyleAnim.BOTTOM;

    int flagCode;
    WindowTopBarListener topBarListener;
    WindowDismissListener dismissListener;

    public Build(Context context) {
        this.context = context;
    }

    /**
     * 对象列表
     *
     * @param adapter 绑定item数据
     * @return .
     */
    public EasyWindowObject<T> createObject(EasyAdapterFactory<T> adapter) {
        return new EasyWindowObject<>(this, adapter);
    }

    /**
     * 字母列表
     *
     * @return .
     */
    public EasyWindowLetter<T> createLetter(EasyAdapterFactory<EasyLetterItem> adapter) {
        return new EasyWindowLetter<>(this, adapter);
    }

    /**
     * String
     *
     * @return .
     */
    public EasyWindowString<T> createString() {
        return new EasyWindowString<>(this);
    }

    /**
     * 滚动
     *
     * @return .
     */
    public EasyWindowScroll<T> createScroll() {
        return new EasyWindowScroll<>(this);
    }


    public Build<T> setFlagCode(int flagCode) {
        this.flagCode = flagCode;
        return this;
    }

    public Build<T> setShowTopBar(boolean showTopBar) {
        this.showTopBar = showTopBar;
        return this;
    }

    public Build<T> setHeight(int height) {
        this.height = height;
        return this;
    }

    public Build<T> setWidth(int width) {
        this.width = width;
        return this;
    }

    public Build<T> setTopBarListener(WindowTopBarListener topBarListener) {
        this.topBarListener = topBarListener;
        return this;
    }

    public Build<T> setDismissListener(WindowDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    public Build<T> setStyleTopBar(StyleTopBar styleTopBar) {
        this.styleTopBar = styleTopBar;
        return this;
    }

    public Build<T> setStyleAnim(StyleAnim animStyle) {
        this.styleAnim = animStyle;
        return this;
    }

    public Build<T> setShowAlpha(float showAlpha) {
        this.showAlpha = showAlpha;
        return this;
    }

    public Build<T> setBackground(Drawable background) {
        this.background = background;
        return this;
    }
}
