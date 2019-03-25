package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jen.easyui.R;
import com.jen.easyui.popupwindow.listener.WindowItemListener;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyHolderRecyclerWaterfallAdapter;
import com.jen.easyui.recycler.listener.EasyItemListener;
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
        adapter.setItemListener(itemListener);
        RecyclerView recycler = popView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(build.context));
        recycler.setAdapter(adapter);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.top_bar_tv_right) {

            } else if (id == R.id.top_bar_iv_close) {

            }
            dismiss();
        }
    };

    private EasyItemListener itemListener = new EasyItemListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (build.listener instanceof WindowItemListener) {
                ((WindowItemListener) build.listener).onClickItem(build.flagCode, showView, build.data.get(position), position);
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
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        super.showAsDropDown(anchor);
    }

    /**
     * 显示在底部
     */
    public void showAsBottom(View showView) {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
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
}
