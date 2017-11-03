package com.jen.easyui.recyclerview;

import android.content.Context;
import android.view.View;

/**
 * 树形模式
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyTreeRecyclerAdapter<T extends EasyTreeItem> extends EasyTreeRecyclerAdapterImp<T> {

    /**
     * @param context
     * @param tree    数据
     */
    protected EasyTreeRecyclerAdapter(Context context, T tree) {
        super(context, tree);
    }

    @Override
    protected abstract boolean showTopLevel();

    @Override
    protected abstract int[] onBindLevelLayout();

    @Override
    protected abstract float itemSpaceSize();

    @Override
    protected abstract EasyHloderImp onCreateEasyHolder(View view);
}