package com.jen.easyui.popupwindow.timepick;

import java.util.Calendar;

/**
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
public interface EasyTimePickListener {
    /**
     * Listener when date has been checked
     */
    void onPick(Calendar calendar);
}
