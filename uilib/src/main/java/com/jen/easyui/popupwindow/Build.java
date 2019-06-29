package com.jen.easyui.popupwindow;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

import com.jen.easyui.popupwindow.listener.WindowDismissListener;
import com.jen.easyui.popupwindow.listener.WindowTopBarListener;
import com.jen.easyui.recycler.EasyRecyclerAdapterFactory;
import com.jen.easyui.recycler.letter.EasyLetterDecoration;


public class Build {
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

    Build(Context context) {
        this.context = context;
    }

    /**
     * 对象列表
     *
     * @param adapter 绑定item数据
     * @return .
     */
    public EasyWindowObject createObject(EasyRecyclerAdapterFactory adapter) {
        return createObject(adapter, null);
    }

    /**
     * 对象列表
     *
     * @param adapter       绑定item数据
     * @param layoutManager .
     * @return .
     */
    public EasyWindowObject createObject(EasyRecyclerAdapterFactory adapter, RecyclerView.LayoutManager layoutManager) {
        return new EasyWindowObject(this, adapter, layoutManager);
    }

    /**
     * 字母列表
     *
     * @return .
     */
    public EasyWindowLetter createLetter(EasyRecyclerAdapterFactory adapter, EasyLetterDecoration letterDecoration) {
        return new EasyWindowLetter(this, adapter, letterDecoration);
    }

    /**
     * String
     *
     * @return .
     */
    public EasyWindowString createString() {
        return new EasyWindowString(this);
    }

    /**
     * 滚动
     *
     * @return .
     */
    public EasyWindowScroll createScroll() {
        return new EasyWindowScroll(this);
    }

    public Build setFlagCode(int flagCode) {
        this.flagCode = flagCode;
        return this;
    }

    public Build setShowTopBar(boolean showTopBar) {
        this.showTopBar = showTopBar;
        return this;
    }

    public Build setHeight(int height) {
        this.height = height;
        return this;
    }

    public Build setWidth(int width) {
        this.width = width;
        return this;
    }

    public Build setTopBarListener(WindowTopBarListener topBarListener) {
        this.topBarListener = topBarListener;
        return this;
    }

    public Build setDismissListener(WindowDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    public Build setStyleTopBar(StyleTopBar styleTopBar) {
        this.styleTopBar = styleTopBar;
        return this;
    }

    public Build setStyleAnim(StyleAnim animStyle) {
        this.styleAnim = animStyle;
        return this;
    }

    public Build setShowAlpha(float showAlpha) {
        this.showAlpha = showAlpha;
        return this;
    }

    public Build setBackground(Drawable background) {
        this.background = background;
        return this;
    }
}
