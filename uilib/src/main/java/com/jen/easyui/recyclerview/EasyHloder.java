package com.jen.easyui.recyclerview;

import android.view.View;

/**
 * 作者：ShannJenn
 * 时间：2017/09/30.
 */

public abstract class EasyHloder extends EasyHloderImp {

    /**
     * bind ID在这处理
     * @param itemView
     */
    public EasyHloder(View itemView) {
        super(itemView);
    }

    /**
     * 初始化item数据在这里处理
     *
     * @return
     */
    public abstract void onBindEasyHolder(int pos);

    /**
     * 控件点击事件
     *
     * @return 控件IDs
     */
    public abstract int[] getOnClick();

    /**
     * 控件长按事件
     *
     * @return 控件IDs
     */
    public abstract int[] getOnLongClick();

    /**
     * item点击事件
     *
     * @return 是否设置item点击事件
     */
    public abstract boolean getItemClick();

}
