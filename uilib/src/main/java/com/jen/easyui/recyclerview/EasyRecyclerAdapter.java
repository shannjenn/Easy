package com.jen.easyui.recyclerview;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyRecyclerAdapter<T> extends EasyRecyclerAdapterImp<T> {

    /**
     * @param data 数据
     */
    protected EasyRecyclerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    protected abstract int onBindLayout();

    /**
     * Holder
     *
     * @return
     */
    @Override
    protected abstract EasyHloder onCreateEasyHolder(View view);
}
