package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindId;
import com.jen.easytest.R;
import com.jen.easytest.adapter.ImageViewPagerAdapter;
import easybase.EasyActivity;
import com.jen.easyui.recycler.viewpager.EasyViewPagerPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class ImageViewPagerActivity extends EasyActivity {

    @EasyBindId(R.id.image_view_pager)
    EasyViewPagerPoint image_view_pager;

    ImageViewPagerAdapter mAdapter;

    @Override
    public int bindView() {
        return R.layout.activity_image_view_pager;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<String> list = new ArrayList<>();
        list.add("aaaa");
        list.add("bbbb");
        list.add("ccc");
        list.add("ddd");
        mAdapter = new ImageViewPagerAdapter(this, list);
        image_view_pager.setAdapter(mAdapter);
        image_view_pager.setNumCount(4);
    }

    @Override
    protected void onBindClick(View view) {

    }

}
