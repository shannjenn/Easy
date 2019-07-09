package com.jen.easytest.adapter.decoration;

import android.content.Context;
import android.widget.LinearLayout;

import com.jen.easyui.recycler.itemDecoration.EasyItemDecoration;

/**
 * 默认分割线
 */
public class MyItemDecoration extends EasyItemDecoration {

    public MyItemDecoration(Context context, int orientation) {
        super(context, orientation);
    }

    public MyItemDecoration(Context context, int orientation, int drawableId) {
        super(context, orientation, drawableId);
    }

    public MyItemDecoration(Context context, int orientation, int dividerHeight, int dividerColor) {
        super(context, orientation, dividerHeight, dividerColor);
    }

    public static MyItemDecoration newInstance(Context context) {
        return new MyItemDecoration(context, LinearLayout.HORIZONTAL, 1,
                0xffdddddd);
    }
}
