package com.jen.easy.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.LruCache;
import android.widget.ImageView;

import com.jen.easy.http.Http;
import com.jen.easy.http.HttpDownloadRequest;
import com.jen.easy.http.imp.HttpDownloadListener;
import com.jen.easy.log.EasyUILog;

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
    private final String TAG = "ImageLoaderManager ";
    private LruCache<String, Bitmap> mImageCache;//图片缓存
    private final Map<String, List<ImageView>> mViewCache = new HashMap<>();//key:imageUrl,value:ImageView缓存
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(2);
    private Http mHttp;
    private HttpDownloadRequest mHttpRequest = new HttpDownloadRequest();//网络获取图片请求参数
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ImageLoaderConfig config;

    protected ImageLoaderManager() {
    }

    protected void init(ImageLoaderConfig builder) {
        this.config = builder;
        mHttp = new Http(builder.getHttpMaxThread());
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    public void setImage(final String imageUrl, final ImageView imageView, final int width, final int height) {
        mExecutorService.submit(new Runnable() {
            public void run() {
                if (imageView == null) {
                    EasyUILog.w("ImageView is null");
                    return;
                }
                if (TextUtils.isEmpty(imageUrl)) {
                    setImageByHandler(null, imageView);
                    return;
                }

                if (mViewCache.containsKey(imageUrl)) {//正在下载
                    mViewCache.get(imageUrl).add(imageView);
                    return;
                }

                boolean cacheResult = getFromCache(imageUrl, imageView);
                if (cacheResult)
                    return;

                boolean SDCardResult = getFromSDCard(width, height, imageUrl, imageView);
                if (SDCardResult)
                    return;

                getFromHttp(imageUrl, imageView);
            }
        });
    }

    public void setImage(String imageUrl, ImageView imageView) {
        setImage(imageUrl, imageView, config.getImgWidth(), config.getImgHeight());
    }

    private boolean getFromCache(String imageUrl, final ImageView imageView) {
        final Bitmap bitmap = mImageCache.get(imageUrl);
        setImageByHandler(bitmap, imageView);
        return bitmap != null;

    }

    /**
     * 从SD卡获取
     *
     * @param imageUrl
     * @return
     * @smallRate 压缩比例
     */
    private boolean getFromSDCard(int picWidth, int picHeight, String imageUrl, ImageView imageView) {
        String name = urlChangeToName(imageUrl);
        final String filePath = config.getLocalPath() + File.separator + name;
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }

        try {
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
                if (width > picWidth)
                    opt.inSampleSize *= width / picWidth;
            } else {
                if (height > picHeight)
                    opt.inSampleSize *= height / picHeight;
            }

            //这次再真正地生成一个有像素的，经过缩放了的bitmap
            opt.inJustDecodeBounds = false;
            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, opt);
            if (bitmap == null) {
                return false;
            } else {
                mImageCache.put(imageUrl, bitmap);
                setImageByHandler(bitmap, imageView);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 从网络获取
     *
     * @param imageUrl 图片地址
     */
    private void getFromHttp(String imageUrl, ImageView imageView) {
        List<ImageView> imageViews = new ArrayList<>();
        imageViews.add(imageView);
        mViewCache.put(imageUrl, imageViews);
        String name = urlChangeToName(imageUrl);
        String filePath = config.getLocalPath() + File.separator + name;
        mHttpRequest.httpParam.url = imageUrl;
        mHttpRequest.httpParam.timeout = config.getTimeOut();
        mHttpRequest.flag.filePath = filePath;
        mHttpRequest.flag.str = imageUrl;
        mHttpRequest.setDownloadListener(mHttpListener);
        mHttp.start(mHttpRequest);
    }

    /**
     * 图片地址转保存到SD卡的名称（包括后缀）
     *
     * @param imageUrl 图片地址
     * @return
     */
    private String urlChangeToName(String imageUrl) {
        String name = imageUrl;
        if (name.length() > config.getNameMaxLen()) {
            name = name.substring(name.length() - config.getNameMaxLen(), name.length());
        }
        name = name.replaceAll(":", "_");
        name = name.replaceAll("/", "_");
        return name;
    }

    private HttpDownloadListener mHttpListener = new HttpDownloadListener() {
        @Override
        public void success(int flagCode, final String imageUrl, String filePath) {
            List<ImageView> imageViews = mViewCache.remove(imageUrl);
            if (imageViews == null || imageViews.size() == 0) {
                EasyUILog.e(TAG + "mHttpListener success imageView == null imageUrl=" + imageUrl);
                return;
            }
            int size = imageViews.size();
            for (int i = 0; i < size; i++) {
                ImageView imageView = imageViews.get(i);
                boolean SDCardResult = getFromSDCard(config.getImgWidth(), config.getImgHeight(), imageUrl, imageView);
                if (!SDCardResult) {
                    setImageByHandler(null, imageView);
                }
            }
        }

        @Override
        public void fail(int flagCode, String imageUrl, String msg) {
            List<ImageView> imageViews = mViewCache.remove(imageUrl);
            if (imageViews == null || imageViews.size() == 0) {
                EasyUILog.e(TAG + "mHttpListener fail imageView == null imageUrl=" + imageUrl);
                return;
            }
            int size = imageViews.size();
            for (int i = 0; i < size; i++) {
                ImageView imageView = imageViews.get(i);
                setImageByHandler(null, imageView);
            }
        }

        @Override
        public void progress(int flagCode, String flag, long currentPoint, long endPoint) {

        }
    };

    private void setImageByHandler(final Bitmap bitmap, final ImageView imageView) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageDrawable(config.getDefaultImage());
                }
            }
        });
    }

}
