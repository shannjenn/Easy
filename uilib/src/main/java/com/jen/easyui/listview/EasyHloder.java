package com.jen.easyui.listview;

import android.view.View;

/**
 * 作者：ShannJenn
 * 时间：2017/09/30.
 */

public abstract class EasyHloder extends EasyHloderImp {

    public EasyHloder(View itemView) {
        super(itemView);
    }

    /**
     * 初始化item数据
     *
     * @return
     */
    public abstract boolean onBindEasyHolder(int pos);

    /**
     * 控件点击事件
     *
     * @return
     */
    public abstract int[] getOnClick();

    /**
     * 控件长按事件
     *
     * @return
     */
    public abstract int[] getOnLongClick();

    /**
     * item点击事件
     *
     * @return
     */
    public abstract boolean getItemClick();

}
