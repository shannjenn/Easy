package com.jen.easyui.recycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridLayout;

public enum LayoutType {
    LinearVertical,
    LinearHorizontal,
    GridVertical,
    GridHorizontal;

    /**
     * @param context .
     * @param type    .
     * @param size    .LinearLayout随意传
     * @return .
     */
    public static RecyclerView.LayoutManager getLayout(Context context, LayoutType type, int size) {
        switch (type) {
            case LinearVertical: {
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                return manager;
            }
            case LinearHorizontal: {
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                return manager;
            }
            case GridVertical: {
                GridLayoutManager manager = new GridLayoutManager(context, size);
                manager.setOrientation(GridLayout.VERTICAL);
                return manager;
            }
            case GridHorizontal:
                GridLayoutManager manager = new GridLayoutManager(context, size);
                manager.setOrientation(GridLayout.HORIZONTAL);
                return manager;
        }
        return null;
    }
}
