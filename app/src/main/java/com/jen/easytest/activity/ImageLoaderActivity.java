package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jen.easy.EasyMouse;
import com.jen.easytest.R;
import com.jen.easyui.activity.EasyBaseActivity;
import com.jen.easyui.image.ImageLoader;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class ImageLoaderActivity extends EasyBaseActivity {

    @EasyMouse.BIND.ID(R.id.iv_pic)
    ImageView iv_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
    }

    @Override
    protected void intDataBeforeView() {
        for (int i = 0; i < 10; i++) {//多线程加载图片
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    ImageLoader.getInstance().setImage("http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg", iv_pic);
                }
            }.start();
        }

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadDataAfterView() {

    }

    @Override
    protected void onBindClick(View view) {

    }

}
