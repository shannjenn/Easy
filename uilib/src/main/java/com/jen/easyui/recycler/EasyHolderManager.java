package com.jen.easyui.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * baseAdapter
 * 作者：ShannJenn
 * 时间：2018/05/28.
 */

abstract class EasyHolderManager extends RecyclerView.ViewHolder {
    private final String TAG = EasyHolderManager.class.getSimpleName();

    public EasyHolderManager(View itemView) {
        super(itemView);
    }

    /**
     * 绑定点击事件
     *
     * @return
     */
    protected abstract EasyAdapterOnClickListener bindEasyAdapterOnClickListener();

    /**
     * 绑定头部数据
     *
     * @return
     */
    protected abstract void onBindHeadData(View view);

    /**
     * 绑定底部数据
     *
     * @return
     */
    protected abstract void onBindFootData(View view);

    /**
     * 绑定item数据
     *
     * @return
     */
    protected abstract void onBindData(View view, int viewType, int position);


    public void addOnClickEvent(View view, final int position) {
        if (bindEasyAdapterOnClickListener() == null) {
            Log.w(TAG,"继承的" + EasyHolder.class.getSimpleName() + "未绑定bindEasyAdapterOnClickListener事件");
            return;
        }
        if (view == null) {
            Log.w(TAG,"点击设置事件失败，请检查view是否不为空");
            return;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindEasyAdapterOnClickListener().onClick(v, position);
            }
        });
    }

    public void addOnLongClickEvent(View view, final int position) {
        if (bindEasyAdapterOnClickListener() == null) {
            Log.w(TAG,"继承的" + EasyHolder.class.getSimpleName() + "未绑定bindEasyAdapterOnClickListener事件");
            return;
        }
        if (view == null) {
            Log.w(TAG,"点击设置事件失败，请检查view是否不为空");
            return;
        }
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return bindEasyAdapterOnClickListener().onLongClick(v, position);
            }
        });
    }

}
