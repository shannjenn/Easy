package com.jen.easyui.popupwindow;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jen.easyui.R;
import com.jen.easyui.recycler.EasyRecyclerAdapterFactory;

import java.util.List;

/**
 * 说明：Object类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowObject extends EasyWindow {
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private EasyRecyclerAdapterFactory adapter;

    EasyWindowObject(Build build, EasyRecyclerAdapterFactory adapter, RecyclerView.LayoutManager layoutManager) {
        super(build);
        this.adapter = adapter;
        this.layoutManager = layoutManager;
        initView();
    }

    @Override
    View bindView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_object, null);
        recyclerView = popView.findViewById(R.id.recycler);
        return popView;
    }

    private void initView(){
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(build.context);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setData(List data) {
        if (data == null || data.size() == 0) {
            return;
        }
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    public void setRecyclerViewMargins(int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        params.setMargins(left, top, right, bottom);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public EasyRecyclerAdapterFactory getAdapter() {
        return adapter;
    }
}
