package com.jen.easyui.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 瀑布布局（多种Item）
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyRecyclerWaterfallAdapter<T> extends EasyRecyclerAdapterFactory<T> {
    private final String TAG = EasyRecyclerWaterfallAdapter.class.getSimpleName();

    public EasyRecyclerWaterfallAdapter(Context context) {
        super(context);
    }

    /**
     * @param data 数据
     */
    public EasyRecyclerWaterfallAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
//        int viewType = super.getItemViewType(position);
        return getViewType(position);
    }

    @Override
    protected int setGridLayoutItemRows(int position) {
        return 0;
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int[] layouts = onBindLayout();
        if (layouts == null) {
            Log.e(TAG, "布局为空");
            return super.onCreateViewHolder(parent, viewType);
        }
        if (viewType < 0 || layouts.length < viewType) {
            Log.e(TAG, "viewType：" + viewType + "错误");
            return super.onCreateViewHolder(parent, viewType);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layouts[viewType], parent, false);
        if (view == null) {
            Log.e(TAG, "找不到该值对应item布局R.layout.id：" + layouts[viewType]);
            return super.onCreateViewHolder(parent, viewType);
        }
        return bindHolder(view);
    }

    @Override
    public void onBindViewHolder(EasyHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    /**
     * 返回值对应viewType
     *
     * @return viewType
     */
    protected abstract int[] onBindLayout();

    /**
     * 获取布局类型
     *
     * @param position 下标
     * @return 设置值大于0，以此做区分EasyItemType小于0的值
     */
    protected abstract int getViewType(int position);
}

