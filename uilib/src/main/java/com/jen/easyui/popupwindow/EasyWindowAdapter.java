package com.jen.easyui.popupwindow;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.LayoutType;
import com.jen.easyui.recycler.listener.EasyItemListener;

import java.util.Map;

public abstract class EasyWindowAdapter<T> {

    public abstract int[] onBindLayout();

    public abstract void onBindHolderData(EasyHolder easyHolder, View view, int viewType, T item, int position);

    public int getViewType(T item, int position) {
        return 0;
    }

    public RecyclerView.ItemDecoration onDecoration() {
        return null;
    }

    /**
     * Map.Entry<LayoutType, Integer> entry = new AbstractMap.SimpleEntry<>(LayoutType.LinearVertical, 2);
     *
     * @return .
     */
    public Map.Entry<LayoutType, Integer> bindRecycleLayout() {
        return null;
    }

    public EasyItemListener itemListener() {
        return null;
    }
}
