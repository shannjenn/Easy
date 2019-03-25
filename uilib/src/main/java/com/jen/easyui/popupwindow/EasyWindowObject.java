package com.jen.easyui.popupwindow;

/**
 * 说明：Object类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowObject extends EasyWindow {
    private WindowBind windowBind;

    EasyWindowObject(Build build, WindowBind windowBind) {
        super(build);
        this.windowBind = windowBind;
    }

    @Override
    protected WindowBind windowBindFactory() {
        return windowBind;
    }
}
