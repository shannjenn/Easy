package com.jen.easyui.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * baseAdapter
 * 作者：ShannJenn
 * 时间：2018/05/28.
 */

public abstract class EasyHolder extends RecyclerView.ViewHolder {
    private final String TAG = EasyHolder.class.getSimpleName();
    protected EasyRecyclerBaseAdapter mAdapter;
    private View mView;

    public EasyHolder(EasyRecyclerBaseAdapter adapter, View itemView) {
        super(itemView);
        this.mAdapter = adapter;
        this.mView = itemView;
    }

    /**
     * 绑定item数据
     *
     * @return
     */
    protected abstract void onBindData(View view, int viewType, int position);


    public void addOnClickEvent(int id, final int position) {
        if (mAdapter == null) {
            Log.e(TAG, "mAdapter 为空，点击事件不能生效" + EasyHolder.class.getSimpleName());
            return;
        }
        if (mAdapter.easyAdapterOnClickListener == null) {
            return;
        }
        if (mView == null) {
            return;
        }
        View view = mView.findViewById(id);
        if (view == null) {
            Log.w(TAG, "点击设置事件失败，请检查view是否不为空");
            return;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.easyAdapterOnClickListener.onClick(v, position);
            }
        });
    }

    public void addOnLongClickEvent(int id, final int position) {
        if (mAdapter == null) {
            Log.e(TAG, "mAdapter 为空，点击事件不能生效" + EasyHolder.class.getSimpleName());
            return;
        }
        if (mAdapter.easyAdapterOnClickListener == null) {
            return;
        }
        if (mView == null) {
            return;
        }
        View view = mView.findViewById(id);
        if (view == null) {
            Log.w(TAG, "点击设置事件失败，请检查view是否不为空");
            return;
        }
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mAdapter.easyAdapterOnClickListener.onLongClick(v, position);
            }
        });
    }
}
