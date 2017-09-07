package com.jen.easyui.listview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

import static com.jen.easyui.listview.ListStyle.LIST_VERTICAL;

/**
 * Created by Jen on 2017/8/24.
 */

public class EasyListView extends RecyclerView {
    private EasyRecyclerAdapter adapter;
    private ListStyle listStyle;
    private int layout;

    public EasyListView(Context context) {
        super(context);
    }

    public EasyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
    }

    public void setAdaper(List<?> datas, int layout) {
        if (listStyle == null) {
            setListStype(LIST_VERTICAL);
        }
        adapter = new EasyRecyclerAdapter(datas, layout);
        setAdapter(adapter);
    }

    public void setListStype(ListStyle listStyle) {
        setListStype(listStyle, 1);
    }

    public void setListStype(ListStyle listStyle, int size) {
        this.listStyle = listStyle;
        LayoutManager layoutManager;
        switch (listStyle) {
            case LIST_HORIZONTAL: {
                layoutManager = new LinearLayoutManager(getContext());
                ((LinearLayoutManager) layoutManager).setOrientation(HORIZONTAL);
                super.setLayoutManager(layoutManager);
                break;
            }
            case LIST_VERTICAL: {
                layoutManager = new LinearLayoutManager(getContext());
                ((LinearLayoutManager) layoutManager).setOrientation(VERTICAL);
                super.setLayoutManager(layoutManager);
                break;
            }
            case GRID_HORIZONTAL: {
                layoutManager = new GridLayoutManager(getContext(), size);
                ((GridLayoutManager) layoutManager).setOrientation(HORIZONTAL);
                super.setLayoutManager(layoutManager);
                break;
            }
            case GRID_VERTICAL: {
                layoutManager = new GridLayoutManager(getContext(), size);
                ((GridLayoutManager) layoutManager).setOrientation(VERTICAL);
                super.setLayoutManager(layoutManager);
                break;
            }
            default: {

                break;
            }
        }
    }
}
