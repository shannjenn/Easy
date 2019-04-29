package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easyui.R;
import com.jen.easyui.popupwindow.listener.WindowCancelSureListener;
import com.jen.easyui.popupwindow.listener.WindowLeftRightListener;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyHolderRecyclerWaterfallAdapter;
import com.jen.easyui.recycler.listener.EasyItemListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：Object类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

class EasyWindowObject extends EasyWindow implements EasyItemListener {
    private WindowBind windowBind;
    private MyAdapter<Object> adapter;
    private List<Object> data;
    private RecyclerView.LayoutManager layoutManager;

    EasyWindowObject(Build build, WindowBind windowBind, RecyclerView.LayoutManager layoutManager) {
        super(build);
        this.windowBind = windowBind;
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
        adapter = new MyAdapter<>(build.context, data);
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
        if (build.itemClickListener != null) {
            build.itemClickListener.itemClick(build.flagCode, showView, selectPosition, data.get(selectPosition));
        }
    }

    @Override
    void clickLeftCallBack() {
        if (build.listener instanceof WindowLeftRightListener) {
            ((WindowLeftRightListener) build.listener).windowLeft(build.flagCode, showView, selectPosition);
        } else if (build.listener instanceof WindowCancelSureListener) {
            ((WindowCancelSureListener) build.listener).windowCancel(build.flagCode, showView);
        }
    }

    @Override
    void clickRightCallBack() {
        if (build.listener instanceof WindowLeftRightListener) {
            ((WindowLeftRightListener) build.listener).windowRight(build.flagCode, showView, selectPosition);
        } else if (build.listener instanceof WindowCancelSureListener) {
            if (selectPosition >= 0 && selectPosition < data.size()) {
                ((WindowCancelSureListener) build.listener).windowSure(build.flagCode, showView, selectPosition, data.get(selectPosition));
            }
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
