package com.jen.easytest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jen.easytest.R;
import com.jen.easytest.adapter.decoration.MyItemDecoration;
import com.jen.easytest.model.RecyclerViewModel;
import com.jen.easyui.recycler.HScroll.EasyHScrollAdapter;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyAdapterFactory;

import java.util.List;

/**
 * Created by Administrator on 2018/3/2.
 */

public class RecyclerViewHScrollAdapter<T extends RecyclerViewModel> extends EasyHScrollAdapter<T> {
    public RecyclerViewHScrollAdapter(Context context) {
        super(context);
    }

    public RecyclerViewHScrollAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
    }

    @Override
    public RecyclerView.ItemDecoration onDecoration() {
        return MyItemDecoration.newInstance(mContext);
    }

    @Override
    protected EasyHolder bindHolder(View view) {
        return new myEasyHolder(this, view);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.item_recycler_scroll_view;
    }

    @Override
    protected int onBindEasyHScrollViewId() {
        return R.id.scrollView;
    }

    private class myEasyHolder extends EasyHolder {

        public myEasyHolder(EasyAdapterFactory adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        protected void onBindData(View view, int viewType, int position) {

        }
    }
}
