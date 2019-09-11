package com.jen.easytest.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.jen.easytest.R;
import com.jen.easyui.recycler.viewpager.EasyCyclePagerAdapter;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2018/11/21.
 * 说明：
 */
public class ImageViewPagerAdapter extends EasyCyclePagerAdapter {

    public ImageViewPagerAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public View bindImageView() {
        return new ImageView(context);
    }

    @Override
    public void bindData(Object item, int position, View view) {

    }

}
