package com.jen.easyui.recyclerview;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * list或者grid模式
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

    /**
     * @return item布局
     */
    @Override
    protected abstract int onBindLayout();

    /**
     * @param view itemView
     * @return 返回：new EasyHloder(view)
     */
    @Override
    protected abstract EasyHloder onCreateEasyHolder(View view);
}
