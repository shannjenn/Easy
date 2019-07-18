package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easyui.R;
import com.jen.easyui.recycler.EasyAdapterFactory;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyHolderWaterfallAdapter;
import com.jen.easyui.recycler.LayoutType;

import java.util.List;

/**
 * 说明：Object类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowObject<T> extends EasyWindow<T> {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private EasyWindowAdapter<T> adapterImp;

    EasyWindowObject(Build<T> build, EasyWindowAdapter<T> adapterImp) {
        super(build);
        this.adapterImp = adapterImp;
        initView();
    }

    @Override
    View bindView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_object, null);
        recyclerView = popView.findViewById(R.id.recycler);
        return popView;
    }

    private void initView() {
        if (adapterImp.bindRecycleLayout() == null) {
            adapter = new MyAdapter(build.context, recyclerView);
        } else {
            adapter = new MyAdapter(build.context);
            recyclerView.setAdapter(adapter);
            LayoutType layoutType = adapterImp.bindRecycleLayout().getKey();
            int size = adapterImp.bindRecycleLayout().getValue();
            recyclerView.setLayoutManager(LayoutType.getLayout(build.context, layoutType, size));
        }
        adapter.setItemListener(adapterImp.itemListener());
        adapter.setDataAndNotify(build.data);
    }

    public void setData(List<T> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (adapter != null) {
            adapter.setDataAndNotify(data);
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public EasyAdapterFactory getAdapter() {
        return adapter;
    }

    private class MyAdapter extends EasyHolderWaterfallAdapter<T> {
        MyAdapter(Context context) {
            super(context);
        }

        MyAdapter(Context context, RecyclerView recyclerView) {
            super(context, recyclerView);
        }

        @Override
        protected int[] onBindLayout() {
            return adapterImp.onBindLayout();
        }

        @Override
        protected int getViewType(int position) {
            return adapterImp.getViewType(mData.get(position), position);
        }

        @Override
        protected void onBindHolderData(EasyHolder easyHolder, View view, int viewType, int position) {
            adapterImp.onBindHolderData(easyHolder, view, viewType, mData.get(position), position);
        }

        @Override
        public RecyclerView.ItemDecoration onDecoration() {
            return adapterImp.onDecoration();
        }
    }
}
