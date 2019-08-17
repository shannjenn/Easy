package com.jen.easy.imageLoader;

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

    /**
     * 单例
     */
    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                }
            }
        }
        return mInstance;
    }


    @Override
    public void init(ImageLoaderConfig config) {
        super.init(config);
    }

    @Override
    protected void setImage(String imageUrl, boolean useCache, ImageView imageView, boolean showDefaultImg, int width, int height) {
        super.setImage(imageUrl, useCache, imageView, showDefaultImg, width, height);
    }

    @Override
    public void setImage(String imageUrl, ImageView imageView, int width, int height) {
        super.setImage(imageUrl, imageView, width, height);
    }

    @Override
    public void setImage(String imageUrl, ImageView imageView) {
        super.setImage(imageUrl, imageView);
    }

    @Override
    public void setImage(String imageUrl, ImageView imageView, boolean showDefaultImg) {
        super.setImage(imageUrl, imageView, showDefaultImg);
    }

    @Override
    public void setImage(String imageUrl, boolean useCache, ImageView imageView, int width, int height) {
        super.setImage(imageUrl, useCache, imageView, width, height);
    }

    @Override
    public void setImage(String imageUrl, boolean useCache, ImageView imageView) {
        super.setImage(imageUrl, useCache, imageView);
    }

    @Override
    public void setImage(String imageUrl, boolean useCache, ImageView imageView, boolean showDefaultImg) {
        super.setImage(imageUrl, useCache, imageView, showDefaultImg);
    }

    @Override
    public void clearCache() {
        super.clearCache();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
