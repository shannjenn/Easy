package com.jen.easytest.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jen.easy.EasyBindId;
import com.jen.easy.imageLoader.ImageLoader;
import com.jen.easy.imageLoader.ImageLoaderConfig;
import com.jen.easytest.MyApplication;
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

    ImageLoaderAdapter<ImageLoaderModel> adapter;

    @Override
    public int bindView() {
        return R.layout.activity_image_loader;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ImageLoader.getInstance().init(ImageLoaderConfig.build(MyApplication.getAppContext()));
        List<ImageLoaderModel> loaderModels = new ArrayList<>();
        loaderModels.add(new ImageLoaderModel());
        loaderModels.add(new ImageLoaderModel());
        loaderModels.add(new ImageLoaderModel());
        loaderModels.add(new ImageLoaderModel());
        loaderModels.add(new ImageLoaderModel());
        loaderModels.add(new ImageLoaderModel());
        loaderModels.add(new ImageLoaderModel());
        loaderModels.add(new ImageLoaderModel());
        adapter = new ImageLoaderAdapter<>(this, recycle);
        adapter.setDataAndNotify(loaderModels);
    }

    @Override
    protected void onBindClick(View view) {

    }

}
