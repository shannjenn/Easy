package com.jen.easyui.listview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

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

    public void setAdaper(ListStyle listStyle, int layout) {
        this.listStyle = listStyle;
    }
}
