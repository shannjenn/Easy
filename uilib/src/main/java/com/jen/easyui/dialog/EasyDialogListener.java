package com.jen.easyui.dialog;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public interface EasyDialogListener {
    void leftButton(int flagCode);

    void middleButton(int flagCode);

    void rightButton(int flagCode);

    void dismiss(int flagCode);
}
