package com.jen.easyui.viewpager;

import android.content.Context;
import android.widget.ImageView;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/9/11.
 */

public abstract class EasyImagePagerAdapter<T> extends EasyImagePagerAdapterManager<T> {


    public EasyImagePagerAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    protected abstract void setItemImage(ImageView itemImage, T item);


}
