package com.jen.easyui.dialog;

import android.widget.CompoundButton;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public interface DialogListenerC extends DialogListenerB {
//    void leftButton(int flagCode);

//    void middleButton(int flagCode);

//    void rightButton(int flagCode);

//    void dismiss(int flagCode);

    void check(int flagCode, CompoundButton buttonView, boolean isCheck);
}
