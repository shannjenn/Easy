package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easyui.R;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyHolderRecyclerWaterfallAdapter;
import com.jen.easyui.view.baseview.EasyTopBar;

import java.util.List;

/**
 * 说明：
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public abstract class EasyWindow extends EasyFactoryWindow {
    private MyAdapter<Object> adapter;
    protected Build build;
    private EasyTopBar topBar;

    public static Build build(Context context) {
        return new Build(context);
    }

    EasyWindow(Build build) {
        super(build.context);
        this.build = build;
        init();
    }

    private void init() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window, null);
        setContentView(popView);

        topBar = popView.findViewById(R.id.topBar);
        topBar.setVisibility(build.showTopBar ? View.VISIBLE : View.GONE);
        topBar.getRightText().setOnClickListener(clickListener);
        topBar.getLeftImageView().setOnClickListener(clickListener);

        adapter = new MyAdapter<>(build.context, build.data);
        adapter.setItemListener(build.itemListener);
        RecyclerView recycler = popView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(build.context));
        recycler.setAdapter(adapter);

        setHeight(build.height == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : build.height);
        setWidth(build.width == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : build.width);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (build.listener != null) {
                int id = view.getId();
                if (id == R.id.top_bar_tv_right) {
                    build.listener.ok(build.flagCode, showView);
                } else if (id == R.id.top_bar_iv_close) {
                    build.listener.cancel(build.flagCode, showView);
                }
            }
            dismiss();
        }
    };

    public void setData(List data) {
        build.data.clear();
        if (data != null && data.size() > 0) {
            build.data.addAll(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
    }

    /**
     * 显示在底部
     */
    public void showAsBottom(View showView) {
        showAtLocation(showView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
            return windowBindFactory().onBindItemLayout();
        }

        @Override
        protected int getViewType(int position) {
            return windowBindFactory().onBindViewType();
        }

        @Override
        protected void onBindHolderData(EasyHolder easyHolder, View view, int viewType, int position) {
            windowBindFactory().onBindItemData(easyHolder, view, mData, position);
        }
    }

    protected abstract WindowBind windowBindFactory();

    public EasyTopBar getTopBar() {
        return topBar;
    }
}
