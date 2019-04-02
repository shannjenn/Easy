package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

class EasyWindowObject extends EasyWindow implements EasyItemListener, View.OnClickListener {
    private WindowBind windowBind;
    private MyAdapter<Object> adapter;

    EasyWindowObject(Build build, WindowBind windowBind) {
        super(build);
        this.windowBind = windowBind;
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
        View rl_top_bar = popView.findViewById(R.id.rl_top_bar);
        ImageView iv_left = popView.findViewById(R.id.iv_left);
        TextView tv_title = popView.findViewById(R.id.tv_title);
        TextView tv_right = popView.findViewById(R.id.tv_right);

        rl_top_bar.setVisibility(build.showTopBar ? View.VISIBLE : View.GONE);
        tv_title.setText(build.topBarTitleText);
        tv_right.setText(build.topBarRightText);

        iv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);

        adapter = new MyAdapter<>(build.context, build.data);
        adapter.setItemListener(this);
        RecyclerView recycler = popView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(build.context));
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
    public void onClick(View v) {
        WindowOkListener okListener;
        if (!(build.listener instanceof WindowOkListener) || build.data.size() == 0) {
            return;
        }
        okListener = (WindowOkListener) build.listener;
        int i = v.getId();
        if (i == R.id.tv_right) {
            okListener.sure(build.flagCode, showView, selectPosition, build.data.get(selectPosition));
        } else if (i == R.id.iv_left) {
            okListener.cancel(build.flagCode, showView);
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
