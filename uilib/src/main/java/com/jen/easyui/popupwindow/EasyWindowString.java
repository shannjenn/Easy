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
import com.jen.easyui.recycler.EasyHolderRecyclerBaseAdapter;
import com.jen.easyui.recycler.listener.EasyItemListener;
import com.jen.easyui.view.baseview.EasyTopBar;

import java.util.List;

/**
 * 说明：String类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

class EasyWindowString extends EasyWindow implements EasyItemListener, View.OnClickListener {
    private MyAdapter<Object> adapter;
    private int checkPosition;

    EasyWindowString(Build build) {
        super(build);
    }

    @Override
    View bindContentView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_string, null);
        EasyTopBar topBar = popView.findViewById(R.id.topBar);
        topBar.setVisibility(build.showTopBar ? View.VISIBLE : View.GONE);
        topBar.getRightText().setOnClickListener(this);
        topBar.getLeftImageView().setOnClickListener(this);

        adapter = new MyAdapter<>(build.context, build.data);
        adapter.setItemListener(this);
        RecyclerView recycler = popView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(build.context));
        recycler.setAdapter(adapter);
        return popView;
    }

    @Override
    void setData(List data) {
        build.data.clear();
        if (data != null && data.size() > 0) {
            build.data.addAll(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        checkPosition = position;
        WindowItemListener itemListener;
        if (!(build.listener instanceof WindowItemListener)) {
            return;
        }
        itemListener = (WindowItemListener) build.listener;
        itemListener.itemClick(build.flagCode, showView, checkPosition, build.data.get(checkPosition));
    }

    @Override
    public void onClick(View v) {
        WindowOkListener okListener;
        if (!(build.listener instanceof WindowOkListener)) {
            return;
        }
        okListener = (WindowOkListener) build.listener;
        int i = v.getId();
        if (i == R.id.top_bar_tv_right) {
            if (checkPosition >= 0) {
                okListener.ok(build.flagCode, showView, checkPosition, build.data.get(checkPosition));
            }
        } else if (i == R.id.top_bar_iv_close) {
            okListener.cancel(build.flagCode, showView);
        }
    }

    class MyAdapter<T> extends EasyHolderRecyclerBaseAdapter<T> {
        /**
         * @param context .
         * @param data    数据
         */
        MyAdapter(Context context, List<T> data) {
            super(context, data);
        }

        @Override
        protected int onBindLayout() {
            return R.layout._easy_popup_window_string_item;
        }

        @Override
        protected void onBindHolderData(EasyHolder easyHolder, View view, int viewType, int position) {
            Object object = mData.get(position);
            if (object instanceof String) {
                easyHolder.setTextView(R.id.shape_name, (String) object);
            }
        }
    }

}
