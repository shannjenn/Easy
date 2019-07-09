package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jen.easyui.R;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyHolderBaseAdapter;
import com.jen.easyui.recycler.EasyAdapterFactory;

import java.util.List;

/**
 * 说明：String类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowString extends EasyWindow {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ItemBuild itemBuild;

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
        adapter = new MyAdapter(build.context, recyclerView);
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

    public EasyAdapterFactory getAdapter() {
        return adapter;
    }

    private class MyAdapter extends EasyHolderBaseAdapter {

        public MyAdapter(Context context, RecyclerView recyclerView) {
            super(context, recyclerView);
        }

        @Override
        protected int onBindLayout() {
            return R.layout._easy_popup_window_string_item;
        }

        @Override
        protected void onBindHolderData(EasyHolder easyHolder, View view, int viewType, int position) {
            String name;
            if (itemBuild != null) {
                TextView shape_name = view.findViewById(R.id.shape_name);
                name = itemBuild.item(position, shape_name);
            } else {
                name = mData.get(position).toString();
            }
            easyHolder.setTextView(R.id.shape_name, name);
        }

        @Override
        public RecyclerView.ItemDecoration onDecoration() {
            return null;
        }
    }

    public interface ItemBuild {
        String item(int position, TextView itemView);
    }

    public void setItemBuild(ItemBuild itemBuild) {
        this.itemBuild = itemBuild;
    }
}
