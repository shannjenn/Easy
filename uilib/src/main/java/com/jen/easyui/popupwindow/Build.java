package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.jen.easyui.popupwindow.listener.WindowListener;
import com.jen.easyui.recycler.letter.EasyLetterDecoration;

import java.util.ArrayList;
import java.util.List;


public class Build {
    Context context;
    int height, width;
    boolean showTopBar = true;
    StyleTopBar styleTopBar;
    StyleAnim styleAnim = StyleAnim.BOTTOM;

    int flagCode;
    WindowListener listener;

    Build(Context context) {
        this.context = context;
    }

    /**
     * @return 字符串列表
     */
    public EasyWindow createString() {
        return new EasyWindowString(this);
    }

    /**
     * 对象列表
     *
     * @param windowBind 绑定item数据
     * @return .
     */
    public EasyWindow createObject(WindowBind windowBind) {
        return createObject(windowBind, null);
    }

    /**
     * 对象列表
     *
     * @param windowBind    绑定item数据
     * @param layoutManager .
     * @return .
     */
    public EasyWindow createObject(WindowBind windowBind, RecyclerView.LayoutManager layoutManager) {
        return new EasyWindowObject(this, windowBind, layoutManager);
    }

    /**
     * 滚动
     *
     * @return .
     */
    public EasyWindow createScroll() {
        return new EasyWindowScroll(this);
    }

    /**
     * 字母列表
     *
     * @return .
     */
    public EasyWindow createLetter(WindowBind windowBind, EasyLetterDecoration letterDecoration) {
        return new EasyWindowLetter(this, windowBind, letterDecoration);
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

    public Build setListener(WindowListener listener) {
        this.listener = listener;
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
}
