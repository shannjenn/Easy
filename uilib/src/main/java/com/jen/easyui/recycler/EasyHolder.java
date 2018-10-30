package com.jen.easyui.recycler;

import android.view.View;

/**
 * baseAdapter
 * 作者：ShannJenn
 * 时间：2018/05/28.
 */

public abstract class EasyHolder extends EasyHolderManager {

    public EasyHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBindHeadData(View view) {

    }

    @Override
    protected void onBindFootData(View view) {

    }
}
