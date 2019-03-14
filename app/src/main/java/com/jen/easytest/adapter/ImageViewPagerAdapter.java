package com.jen.easytest.adapter;

import android.content.Context;
import android.view.View;

import com.jen.easytest.R;
import com.jen.easyui.recycler.viewpager.EasyViewPagerAdapter;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2018/11/21.
 * 说明：
 */
public class ImageViewPagerAdapter extends EasyViewPagerAdapter {

    public ImageViewPagerAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setData(View view, Object item) {

    }

}
