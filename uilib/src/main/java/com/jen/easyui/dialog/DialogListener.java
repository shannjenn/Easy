package com.jen.easyui.dialog;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public abstract class DialogListener {
    public abstract void rightButton(int flagCode);

    public void leftButton(int flagCode) {
    }

    public void dismiss(int flagCode) {
    }
}
