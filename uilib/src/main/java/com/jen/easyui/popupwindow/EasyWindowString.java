package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jen.easyui.R;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyHolderBaseAdapter;

import java.util.List;

/**
 * 说明：String类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowString<T> extends EasyWindow<T> {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ItemBuild<T> itemBuild;

    EasyWindowString(Build<T> build) {
        super(build);
        initView();
    }

    @Override
    View bindView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_string, null);
        recyclerView = popView.findViewById(R.id.recycler);
        return popView;
    }

    private void initView() {
        adapter = new MyAdapter(build.context, recyclerView);
        adapter.setDataAndNotify(build.data);
    }

    public void setData(List<T> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        adapter.setDataAndNotify(data);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public MyAdapter getAdapter() {
        return adapter;
    }

    public class MyAdapter extends EasyHolderBaseAdapter<T> {

        MyAdapter(Context context, RecyclerView recyclerView) {
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
                name = itemBuild.item(mData.get(position), position, shape_name);
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

    public interface ItemBuild<T> {
        String item(T item, int position, TextView itemView);
    }

    public void setItemBuild(ItemBuild<T> itemBuild) {
        this.itemBuild = itemBuild;
    }

}
