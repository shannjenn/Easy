package com.jen.easy.imageLoader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * 图片加载工具
 * 作者：ShannJenn
 * 时间：2017/11/08.
 */

public class ImageLoader extends ImageLoaderManager {
//    private static ImageLoader mInstance;

    /**
     * 多例
     */
    public ImageLoader(Context context, int httpMaxThread) {
        super(context, httpMaxThread);
    }

    /**
     * 单例
     */
    /*public static ImageLoader getInstance(Context context) {
        synchronized (ImageLoader.class) {
            if (mInstance == null) {
                mInstance = new ImageLoader(context, -1);//-1值默认线程数
            }
        }
        return mInstance;
    }*/

    @Override
    public void setImage(String imageUrl, ImageView imageView) {
        synchronized (ImageLoader.class) {
            super.setImage(imageUrl, imageView);
        }
    }

    @Override
    public void setImage(String imageUrl, ImageView imageView, int width, int height) {
        super.setImage(imageUrl, imageView, width, height);
    }

    @Override
    public ImageLoader setDefaultImage(Drawable defaultImage) {
        super.setDefaultImage(defaultImage);
        return this;
    }

    @Override
    public ImageLoader setDefaultHeight(int defaultHeight) {
        super.setDefaultHeight(defaultHeight);
        return this;
    }

    @Override
    public ImageLoader setDefaultWidth(int defaultWidth) {
        super.setDefaultWidth(defaultWidth);
        return this;
    }

    @Override
    public void setTimeOut(int timeOut) {
        super.setTimeOut(timeOut);
    }
}
