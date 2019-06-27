package com.jen.easytest.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jen.easy.EasyBindId;
import com.jen.easytest.R;
import com.jen.easytest.adapter.ImageLoaderAdapter;
import com.jen.easytest.http.response.ImageLoaderResponse;
import com.jen.easytest.model.ImageLoaderModel;
import easybase.EasyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class ImageLoaderActivity<T extends ImageLoaderResponse> extends EasyActivity<T> {

    @EasyBindId(R.id.iv_pic)
    ImageView iv_pic;
    @EasyBindId(R.id.recycle)
    RecyclerView recycle;

    ImageLoaderAdapter adapter;
    List<ImageLoaderModel> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
    }



    @Override
    protected void initViews() {
//        List<ImageLoaderModel> list = EasyMain.mDao.searchAll(ImageLoaderModel.class);
//        mData.addAll(list);
//        mData.addAll(list);
        adapter = new ImageLoaderAdapter<>(this, mData);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        recycle.setLayoutManager(manager);
        recycle.setAdapter(adapter);
    }



    @Override
    protected void onBindClick(View view) {

    }

}
