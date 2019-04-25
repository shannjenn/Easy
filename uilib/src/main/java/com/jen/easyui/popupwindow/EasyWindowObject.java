package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easyui.R;
import com.jen.easyui.popupwindow.listener.WindowItemListener;
import com.jen.easyui.popupwindow.listener.WindowOkListener;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyHolderRecyclerWaterfallAdapter;
import com.jen.easyui.recycler.listener.EasyItemListener;

import java.util.List;

/**
 * 说明：Object类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

class EasyWindowObject extends EasyWindow implements EasyItemListener {
    private WindowBind windowBind;
    private MyAdapter<Object> adapter;
    private RecyclerView.LayoutManager layoutManager;

    EasyWindowObject(Build build, WindowBind windowBind, RecyclerView.LayoutManager layoutManager) {
        super(build);
        this.windowBind = windowBind;
        this.layoutManager = layoutManager;
    }

    @Override
    public void setData(List data) {
        build.data.clear();
        if (data != null && data.size() > 0) {
            build.data.addAll(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    View bindContentView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_object, null);
        adapter = new MyAdapter<>(build.context, build.data);
        adapter.setItemListener(this);
        RecyclerView recycler = popView.findViewById(R.id.recycler);
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(build.context);
        }
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
        return popView;
    }

    @Override
    public void onItemClick(View view, int position) {
        selectPosition = position;
        WindowItemListener itemListener;
        if (!(build.listener instanceof WindowItemListener)) {
            return;
        }
        itemListener = (WindowItemListener) build.listener;
        itemListener.itemClick(build.flagCode, showView, selectPosition, build.data.get(selectPosition));
    }

    @Override
    void clickLeftCallBack() {
        if (build.listener instanceof WindowOkListener && build.data.size() > 0) {
            ((WindowOkListener) build.listener).windowLeft(build.flagCode, showView, selectPosition, build.data.get(selectPosition));
        }
    }

    @Override
    void clickRightCallBack() {
        if (build.listener instanceof WindowOkListener && build.data.size() > 0) {
            ((WindowOkListener) build.listener).windowRight(build.flagCode, showView, selectPosition, build.data.get(selectPosition));
        }
    }

    class MyAdapter<T> extends EasyHolderRecyclerWaterfallAdapter<T> {
        /**
         * @param context .
         * @param data    数据
         */
        MyAdapter(Context context, List<T> data) {
            super(context, data);
        }

        @Override
        protected int[] onBindLayout() {
            return windowBind.onBindItemLayout();
        }

        @Override
        protected int getViewType(int position) {
            return windowBind.onBindViewType();
        }

        @Override
        protected void onBindHolderData(EasyHolder easyHolder, View view, int viewType, int position) {
            windowBind.onBindItemData(easyHolder, view, mData, position);
        }
    }

}
