package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easyui.R;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyHolderRecyclerBaseAdapter;
import com.jen.easyui.recycler.EasyRecyclerAdapterFactory;

import java.util.List;

/**
 * 说明：String类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowString extends EasyWindow {
    private RecyclerView recyclerView;
    private MyAdapter<String> adapter;

    EasyWindowString(Build build) {
        super(build);
        initView();
    }

    @Override
    View bindView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_object, null);
        recyclerView = popView.findViewById(R.id.recycler);
        return popView;
    }

    private void initView() {
        adapter = new MyAdapter<>(build.context);
        adapter.bindRecycleLinearVertical(recyclerView);
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

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public EasyRecyclerAdapterFactory getAdapter() {
        return adapter;
    }

    private class MyAdapter<T extends String> extends EasyHolderRecyclerBaseAdapter<T> {

        public MyAdapter(Context context) {
            super(context);
        }

        @Override
        protected int onBindLayout() {
            return R.layout._easy_popup_window_string_item;
        }

        @Override
        protected void onBindHolderData(EasyHolder easyHolder, View view, int viewType, int position) {
            String name = mData.get(position);
            easyHolder.setTextView(R.id.shape_name, name);
        }
    }
}
