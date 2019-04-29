package com.jen.easyui.popupwindow;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easyui.R;
import com.jen.easyui.recycler.EasyRecyclerAdapterFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：Object类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

class EasyWindowObject extends EasyWindow {
    private List<Object> data;
    private RecyclerView.LayoutManager layoutManager;

    EasyWindowObject(Build build, EasyRecyclerAdapterFactory adapter, RecyclerView.LayoutManager layoutManager) {
        super(build, adapter);
        this.layoutManager = layoutManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setData(List data) {
        if (data == null || data.size() == 0) {
            return;
        }
        this.data.clear();
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    View bindContentView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_object, null);
        data = new ArrayList<>();
        data.add("");//默认有一个
        RecyclerView recycler = popView.findViewById(R.id.recycler);
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(build.context);
        }
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
        return popView;
    }

}
