package com.jen.easytest.adapter;

import android.content.Context;
import android.view.View;

import com.jen.easytest.R;
import com.jen.easytest.model.RecyclerViewModel;
import com.jen.easyui.recycler.EasyHScrollRecyclerViewAdapter;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyRecyclerBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/3/2.
 */

public class RecyclerViewHScrollAdapter<T extends RecyclerViewModel> extends EasyHScrollRecyclerViewAdapter<T> {
    /**
     * @param context
     * @param data    数据
     */
    public RecyclerViewHScrollAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    protected EasyHolder bindHolder(View view) {
        return new myEasyHolder(this,view);
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

        public myEasyHolder(EasyRecyclerBaseAdapter adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        protected void onBindData(View view, int viewType, int position) {

        }
    }
}
