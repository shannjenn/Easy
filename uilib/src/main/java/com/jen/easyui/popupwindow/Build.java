package com.jen.easyui.popupwindow;

import android.content.Context;
import android.content.res.Resources;

import com.jen.easyui.dialog.StyleContent;
import com.jen.easyui.popupwindow.listener.WindowListener;

import java.util.ArrayList;
import java.util.List;


public class Build {
    Context context;
    boolean showTopBar = true;
    final List<Object> data = new ArrayList<>();
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
        return new EasyWindowObject(this, windowBind);
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

    public Build setListener(WindowListener listener) {
        this.listener = listener;
        return this;
    }
}
