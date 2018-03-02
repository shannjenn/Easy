package com.jen.easy.imageLoader;

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
        synchronized (ImageLoader.class) {
            if (mInstance == null) {
                mInstance = new ImageLoader();
            }
        }
        return mInstance;
    }



    /*@Override
    public synchronized static void init(ImageLoaderConfig config) {
        super.init(config);
    }

    @Override
    public void setImage(String imageUrl, ImageView imageView, int width, int height) {
        super.setImage(imageUrl, imageView, width, height);
    }

    @Override
    public void setImage(String imageUrl, ImageView imageView) {
        super.setImage(imageUrl, imageView);
    }*/
}
