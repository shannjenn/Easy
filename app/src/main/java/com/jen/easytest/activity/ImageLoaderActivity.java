package com.jen.easytest.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jen.easy.EasyMouse;
import com.jen.easytest.R;
import com.jen.easytest.adapter.ImageLoaderAdapter;
import com.jen.easytest.http.request.ImageLoaderRequest;
import com.jen.easytest.http.response.ImageLoaderResponse;
import com.jen.easytest.model.ImageLoaderModel;
import com.jen.easyui.EasyMain;
import com.jen.easyui.base.EasyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class ImageLoaderActivity<T extends ImageLoaderResponse> extends EasyActivity<T> {

    @EasyMouse.BIND.ID(R.id.iv_pic)
    ImageView iv_pic;
    @EasyMouse.BIND.ID(R.id.recycle)
    RecyclerView recycle;

    ImageLoaderAdapter adapter;
    List<ImageLoaderModel> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
    }

    @Override
    protected void intDataBeforeView() {
//        checkFilePermission();
        EasyMain.mImageLoader.setImage("http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg", iv_pic);

    }

    @Override
    protected void initViews() {
        List<ImageLoaderModel> list = EasyMain.mDao.searchAll(ImageLoaderModel.class);
        mData.addAll(list);
        mData.addAll(list);
        adapter = new ImageLoaderAdapter(this, mData);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        recycle.setLayoutManager(manager);
        recycle.setAdapter(adapter);
    }

    @Override
    protected void loadDataAfterView() {
        if (mData.size() == 0) {
            ImageLoaderRequest request = new ImageLoaderRequest();
            request.setBseListener(this);
            EasyMain.mHttp.start(request);
        }
    }

    @Override
    protected void onBindClick(View view) {

    }

    @Override
    public void success(int flagCode, String flag, T response) {
        super.success(flagCode, flag, response);
        mData.clear();
        mData.addAll(response.getData().getData2());
        EasyMain.mDao.replace(mData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void fail(int flagCode, String flag, String msg) {
        super.fail(flagCode, flag, msg);
    }
}
