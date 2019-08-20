package com.jen.easy.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.LruCache;
import android.widget.ImageView;

import com.jen.easy.http.EasyHttp;
import com.jen.easy.http.imp.EasyHttpListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片加载工具
 * 作者：ShannJenn
 * 时间：2017/11/08.
 */

abstract class ImageLoaderManager {
    private LruCache<String, Bitmap> mImageCache;//图片缓存
    private Map<String, List<Image>> mImageViewCache = new HashMap<>();
    private ExecutorService mExecutorService;
    private EasyHttp mHttp;
    private final int H_IMAGE = 100;
    private final int H_IMAGE_EMPTY = 101;
    private ImageLoaderConfig config;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            synchronized (this) {
                switch (msg.what) {
                    case H_IMAGE: {
                        String imageUrl = (String) msg.obj;
                        List<Image> images = mImageViewCache.remove(imageUrl);
                        Bitmap bitmap = mImageCache.get(imageUrl);
                        if (images == null) {
                            ImageLoaderLog.w("handler H_IMAGE imageViews List is null imageUrl = " + imageUrl);
                            return;
                        }
                        for (int i = 0; i < images.size(); i++) {
                            if (images.get(i).imageView != null) {
                                images.get(i).imageView.setImageBitmap(bitmap);
                                ImageLoaderLog.d("handler H_IMAGE ImageView success imageUrl = " + imageUrl);
                            } else {
                                ImageLoaderLog.w("handler H_IMAGE ImageView is null imageUrl = " + imageUrl);
                            }
                        }
                        break;
                    }
                    case H_IMAGE_EMPTY: {
                        String imageUrl = (String) msg.obj;
                        List<Image> images = mImageViewCache.get(imageUrl);
                        if (images == null) {
                            ImageLoaderLog.w("handler H_IMAGE imageViews List is null imageUrl = " + imageUrl);
                            return;
                        }
                        for (int i = 0; i < images.size(); i++) {
                            if (images.get(i).imageView != null) {
                                images.get(i).imageView.setImageDrawable(config.getDefaultImage());
                            } else {
                                ImageLoaderLog.w("handler H_IMAGE_EMPTY ImageView is null imageUrl = " + imageUrl);
                            }
                        }
                        break;
                    }
                    default: {

                        break;
                    }
                }
            }
        }
    };

    class Image {
        String url;
        int width;
        int height;
        ImageView imageView;

        Image(String url, ImageView imageView, int width, int height) {
            this.url = url;
            this.width = width;
            this.height = height;
            this.imageView = imageView;
        }
    }

    private void setImageViewByHandler(String imageUrl) {
        Message message = new Message();
        message.what = H_IMAGE;
        message.obj = imageUrl;
        mHandler.sendMessage(message);
    }

    private void setImageViewEmptyByHandler(String imageUrl) {
        Message message = new Message();
        message.what = H_IMAGE_EMPTY;
        message.obj = imageUrl;
        mHandler.sendMessage(message);
    }

    protected void init(ImageLoaderConfig builder) {
        config = builder;
        mHttp = new EasyHttp(builder.getHttpMaxThread());
        mHttp.setListener(mHttpListener);
        mExecutorService = Executors.newFixedThreadPool(builder.getHttpMaxThread());
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    /**
     * @param imageUrl       图片地址
     * @param useCache       是否使用缓存
     * @param imageView      .
     * @param showDefaultImg 是否显示默认图片
     * @param width          宽
     * @param height         高
     */
    protected void setImage(final String imageUrl, final boolean useCache, final ImageView imageView, final boolean showDefaultImg, final int width, final int height) {
        mExecutorService.submit(new Runnable() {
            public void run() {
                if (TextUtils.isEmpty(imageUrl)) {
                    ImageLoaderLog.w("加载图片错误：setImage error imageUrl图片地址为空");
                    return;
                }
                if (imageView == null) {
                    ImageLoaderLog.w("加载图片错误：setImage error imageView为空 imageUrl = " + imageUrl);
                    return;
                }
                List<Image> imageViews = new ArrayList<>();
                if (mImageViewCache.containsKey(imageUrl)) {//正在下载
                    imageViews = mImageViewCache.get(imageUrl);
                    imageViews.add(new Image(imageUrl, imageView, width, height));
                    mImageViewCache.put(imageUrl, imageViews);
                    ImageLoaderLog.d("正在下载 imageUrl = " + imageUrl);
                    return;
                }
                imageViews.add(new Image(imageUrl, imageView, width, height));
                mImageViewCache.put(imageUrl, imageViews);
                if (useCache) {
                    boolean cacheResult = getFromCache(imageUrl);//从内存获取
                    if (cacheResult)
                        return;

                    boolean SDCardResult = createBitmapByUrl(width, height, imageUrl);//从SD卡/文件获取
                    if (SDCardResult) {
                        setImageViewByHandler(imageUrl);
                        return;
                    }
                }
                if (showDefaultImg)
                    setImageViewEmptyByHandler(imageUrl);
                getFromHttp(imageUrl);
            }
        });
    }

    public void setImage(final String imageUrl, final ImageView imageView, final int width, final int height) {
        setImage(imageUrl, true, imageView, false, width, height);
    }

    public void setImage(String imageUrl, ImageView imageView) {
        setImage(imageUrl, true, imageView, false, 0, 0);
    }

    public void setImage(String imageUrl, ImageView imageView, boolean showDefaultImg) {
        setImage(imageUrl, true, imageView, showDefaultImg, 0, 0);
    }

    public void setImage(final String imageUrl, boolean useCache, final ImageView imageView, final int width, final int height) {
        setImage(imageUrl, useCache, imageView, false, width, height);
    }

    public void setImage(String imageUrl, boolean useCache, ImageView imageView) {
        setImage(imageUrl, useCache, imageView, false, 0, 0);
    }

    public void setImage(String imageUrl, boolean useCache, ImageView imageView, boolean showDefaultImg) {
        setImage(imageUrl, useCache, imageView, showDefaultImg, 0, 0);
    }

    private boolean getFromCache(String imageUrl) {
        Bitmap bitmap = mImageCache.get(imageUrl);
        if (bitmap == null) {
            return false;
        } else {
            setImageViewByHandler(imageUrl);
            return true;
        }

    }

    /**
     * 从SD卡获取
     *
     * @param picWidth  宽
     * @param picHeight 高
     * @param imageUrl  url
     * @return 。
     */
    private boolean createBitmapByUrl(int picWidth, int picHeight, String imageUrl) {
        ImageLoaderLog.d("createBitmapByUrl imageUrl = " + imageUrl);
        String name = urlChangeToName(imageUrl);
        final String filePath = config.getLocalPath() + File.separator + name;
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opt);

        int width = opt.outWidth;
        int height = opt.outHeight;
        if (width == 0 || height == 0) {
            return false;
        }
        opt.inSampleSize = 1;
        if (width > height) {
            if (picWidth > 0) {
                if (width > picWidth) {
                    opt.inSampleSize *= width / picWidth;
                }
            } else {
                if (width > config.getImgMaxWidth()) {
                    opt.inSampleSize *= width / config.getImgMaxWidth();
                }
            }
        } else {
            if (picHeight > 0) {
                if (height > picHeight) {
                    opt.inSampleSize *= height / picHeight;
                }
            } else {
                if (height > config.getImgMaxHeight()) {
                    opt.inSampleSize *= height / config.getImgMaxHeight();
                }
            }
        }

        //这次再真正地生成一个有像素的，经过缩放了的bitmap
        opt.inJustDecodeBounds = false;
        opt.inPreferredConfig = config.getBitmapConfig();
        final Bitmap bitmap = BitmapFactory.decodeFile(filePath, opt);
        if (bitmap == null) {
            ImageLoaderLog.w("createBitmapByUrl bitmap is null imageUrl = " + imageUrl);
            return false;
        } else {
            mImageCache.put(imageUrl, bitmap);
            return true;
        }
    }

    /**
     * 从网络获取
     *
     * @param imageUrl 图片地址
     */
    private void getFromHttp(String imageUrl) {
        ImageLoaderLog.d("getFromHttp-----");
        String name = urlChangeToName(imageUrl);
        String filePath = config.getLocalPath() + File.separator + name;
        ImageLoaderRequest request = new ImageLoaderRequest();//网络获取图片请求参数
        request.urlBase = imageUrl;
        request.timeout = config.getTimeOut();
        request.filePath = filePath;
        mHttp.start(request, imageUrl);
    }

    /**
     * 图片地址转保存到SD卡的名称（包括后缀）
     *
     * @param imageUrl 图片地址
     * @return .
     */
    private String urlChangeToName(String imageUrl) {
        String name = imageUrl;
        if (name.length() > config.getNameMaxLen()) {
            name = name.substring(name.length() - config.getNameMaxLen());
        }
        name = name.replace(":", "_");
        name = name.replace("/", "_");
        return name;
    }

    private EasyHttpListener mHttpListener = new EasyHttpListener() {

        @Override
        public void success(int flagCode, String flagStr, Object response, Map<String, List<String>> headMap) {
            ImageLoaderLog.d("图片下载成功 imageUrl = " + flagStr);
            List<Image> images = mImageViewCache.get(flagStr);
            if (images != null && images.size() > 0) {
                boolean result = createBitmapByUrl(images.get(0).width, images.get(0).height, flagStr);
                if (result) {
                    setImageViewByHandler(flagStr);
                } else {
                    mImageViewCache.remove(flagStr);
                }
            }
        }

        @Override
        public void fail(int flagCode, String flagStr, Object response) {
            ImageLoaderLog.e("图片下载失败 imageUrl = " + flagStr);
            mImageViewCache.remove(flagStr);
        }

        @Override
        public void progress(int flagCode, String flag, Object response, long currentPoint, long endPoint) {
            ImageLoaderLog.d("flag = " + flag + " currentPoint = " + currentPoint + " endPoint=" + endPoint);
        }
    };

    public void clearCache() {
        mHandler.removeMessages(H_IMAGE);
        mHandler.removeMessages(H_IMAGE_EMPTY);
        if (mImageCache != null) {
            mImageCache.evictAll();
        }
        mImageViewCache.clear();
    }

    public void destroy() {
        mHttp.shutdown();
    }

}
