package com.jen.easyui.popupwindow;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easyui.R;
import com.jen.easyui.recycler.EasyAdapterFactory;

import java.util.List;

/**
 * 说明：Object类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowObject<T> extends EasyWindow<T> {
    private RecyclerView recyclerView;
    private EasyAdapterFactory<T> adapter;

    EasyWindowObject(Build<T> build, EasyAdapterFactory<T> adapter) {
        super(build);
        this.adapter = adapter;
    }

    @Override
    View bindView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_object, null);
        recyclerView = popView.findViewById(R.id.recycler);
        return popView;
    }

    public void setData(List<T> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (adapter != null) {
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public EasyAdapterFactory getAdapter() {
        return adapter;
    }
}
