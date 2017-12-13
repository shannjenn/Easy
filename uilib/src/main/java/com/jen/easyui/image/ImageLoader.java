package com.jen.easyui.image;

import android.widget.ImageView;

/**
 * 图片加载工具
 * 作者：ShannJenn
 * 时间：2017/11/08.
 */

public class ImageLoader extends ImageLoaderManager {
    private static ImageLoader mInstance;

    private ImageLoader() {
        super();
    }

    public static ImageLoader getInstance() {
        synchronized (ImageLoader.class) {
            if (mInstance == null) {
                mInstance = new ImageLoader();
            }
        }
        return mInstance;
    }

    @Override
    public void setImage(String imageUrl, ImageView imageView) {
        synchronized (ImageLoader.class) {
            super.setImage(imageUrl, imageView);
        }
    }
}
