package com.jen.easyui.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 瀑布布局（多种Item） 带holder
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyHolderWaterfallAdapter<T> extends EasyAdapterFactory<T> {
    private final String TAG = EasyHolderWaterfallAdapter.class.getSimpleName();

    public EasyHolderWaterfallAdapter(Context context) {
        super(context);
    }

    public EasyHolderWaterfallAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
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
    protected int gridLayoutItemRows(int position) {
        return 0;
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int[] layouts = onBindLayout();
        if (layouts == null) {
            Log.e(TAG, "布局为空");
            return super.onCreateViewHolder(parent, viewType);
        }
        if (viewType < 0 || layouts.length == 0 || layouts.length < viewType) {
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

    @Override
    protected EasyHolder bindHolder(View view) {
        return new MyHolder(this, view);
    }

    class MyHolder extends EasyHolder {
        public MyHolder(EasyAdapterFactory adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        protected void onBindData(View view, int viewType, int position) {
            onBindHolderData(this, view, viewType, position);
        }
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


    /**
     * 绑定数据
     *
     * @param easyHolder 。
     * @param view       。
     * @param viewType   。
     * @param position   。
     */
    protected abstract void onBindHolderData(EasyHolder easyHolder, View view, int viewType, int position);
}

