package com.jen.easyui.popupwindow;

import android.content.Context;

import com.jen.easyui.popupwindow.listener.WindowListener;

import java.util.ArrayList;
import java.util.List;


public class Build {
    Context context;
    int height, width;
    boolean showTopBar = true;
    String topBarRightText = "确定";
    String topBarTitleText = "标题";

    int flagCode;
    WindowListener listener;
    final List<Object> data = new ArrayList<>();

    Build(Context context) {
        this.context = context;
        data.add("");//默认一条数据
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
        return new EasyWindowObject(this, windowBind);
    }

    /**
     * 滚动
     *
     * @return .
     */
    public EasyWindow createScroll() {
        return new EasyWindowScroll(this);
    }

    public Build setData(List data) {
        this.data.clear();
        if (data != null && data.size() > 0) {
            this.data.addAll(data);
        }
        return this;
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

    public Build setTopBarRightText(String topBarRightText) {
        this.topBarRightText = topBarRightText;
        return this;
    }

    public Build setTopBarTitleText(String topBarTitleText) {
        this.topBarTitleText = topBarTitleText;
        return this;
    }
}
