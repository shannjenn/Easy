package com.jen.easyui.recyclerview;

import android.view.View;

/**
 * baseAdapter
 * 作者：ShannJenn
 * 时间：2018/05/28.
 */

public abstract class EasyHolder extends EasyHolderManager {

    public EasyHolder(View itemView, EasyItemType easyItemType) {
        super(itemView, easyItemType);
    }

    @Override
    protected void onBindHeadData(View view) {

    }

    @Override
    protected void onBindFootData(View view) {

    }
}
