package com.jen.easyui.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyLog;

import java.util.List;

/**
 * 瀑布布局（多种Item）
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyRecyclerWaterfallAdapter<T> extends EasyRecyclerBaseAdapter<T> {
    /**
     * @param data 数据
     */
    EasyRecyclerWaterfallAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = super.getItemViewType(position);
        int headType = EasyItemType.HEAD.getType();
        int footType = EasyItemType.FOOT.getType();
        if (viewType == headType || viewType == footType) {
            return viewType;
        }
        int type = getViewType(position - mHeadItemCount);
        if (type == headType || type == footType) {
            EasyLog.w("getViewType 值不能和EasyItemType值相同");
            return 0;
        } else {
            return type;
        }
    }

    @Override
    public EasyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EasyItemType.HEAD.getType() || viewType == EasyItemType.FOOT.getType()) {
            return super.onCreateViewHolder(parent, viewType);
        }
        int[] layouts = onBindLayout();
        if (layouts == null) {
            EasyLog.w("布局为空");
            return null;
        }
        if (viewType < 0 || layouts.length < viewType) {
            EasyLog.w("viewType：" + viewType + "错误");
            return null;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layouts[viewType], parent, false);
        if (view == null) {
            EasyLog.w("找不到该值对应item布局R.layout.id：" + layouts[viewType]);
            return null;
        }
        return bindHolder(view, EasyItemType.BODY);
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

