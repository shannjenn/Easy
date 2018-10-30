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

public abstract class EasyRecyclerWaterfallAdapter<T> extends EasyRecyclerBaseAdapter<T> {
    private final String TAG = EasyRecyclerWaterfallAdapter.class.getSimpleName();
    /**
     * @param data 数据
     */
    EasyRecyclerWaterfallAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public int getItemViewType(int position) {
//        int viewType = super.getItemViewType(position);
        return getViewType(position);
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int[] layouts = onBindLayout();
        if (layouts == null) {
            Log.w(TAG,"布局为空");
            return null;
        }
        if (viewType < 0 || layouts.length < viewType) {
            Log.w(TAG,"viewType：" + viewType + "错误");
            return null;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layouts[viewType], parent, false);
        if (view == null) {
            Log.w(TAG,"找不到该值对应item布局R.layout.id：" + layouts[viewType]);
            return null;
        }
        return bindHolder(view);
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
     * @return 设置值大于0，以此做区分EasyItemType的值小于0
     */
    protected abstract int getViewType(int position);
}

