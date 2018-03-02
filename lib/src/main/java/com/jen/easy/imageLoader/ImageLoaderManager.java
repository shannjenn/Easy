package com.jen.easy.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.LruCache;
import android.widget.ImageView;

import com.jen.easy.http.Http;
import com.jen.easy.http.HttpDownloadRequest;
import com.jen.easy.http.imp.HttpDownloadListener;
import com.jen.easy.log.EasyLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片加载工具
 * 作者：ShannJenn
 * 时间：2017/11/08.
 */

abstract class ImageLoaderManager {
    private static final String TAG = "ImageLoaderManager";
    private static LruCache<String, Bitmap> mImageCache;//图片缓存
    //    private static Map<String, Bitmap> mImageCache;
    private static List<String> mDownloadingUrl = new ArrayList<>();
    private static Map<ImageView, String> mImageViewCache = new HashMap<>();//key:,ImageView:imageUrl缓存
    private static ExecutorService mExecutorService;
    private static Http mHttp;
    private static HttpDownloadRequest mHttpRequest = new HttpDownloadRequest();//网络获取图片请求参数
    private static final int H_IMAGE = 100;
    private static final int H_IMAGE_EMPTY = 101;
    private static ImageLoaderConfig config;
    private static Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case H_IMAGE: {
                    ImageView imageView = (ImageView) msg.obj;
                    if (imageView == null) {
                        mImageViewCache.remove(null);
                        EasyLog.w("Handler imageView 为空---");
                        return;
                    }
                    String imageUrl = mImageViewCache.remove(imageView);
                    if (imageUrl == null) {
                        EasyLog.w("Handler imageUrl 为空---");
                        return;
                    }
                    Bitmap bitmap = mImageCache.get(imageUrl);
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        setImage(imageUrl, imageView);
                    }
                    break;
                }
                case H_IMAGE_EMPTY: {
                    ImageView imageView = (ImageView) msg.obj;
                    if (imageView == null) {
                        EasyLog.w("imageView 为空---");
                        return;
                    }
                    imageView.setImageDrawable(config.getDefaultImage());
                    break;
                }
                default: {

                    break;
                }
            }
        }
    };

    private synchronized static void setImageView(final ImageView imageView) {
        Message message = new Message();
        message.what = H_IMAGE;
        message.obj = imageView;
        mHandler.sendMessage(message);

        /*mHandler.post(new Runnable() {
            @Override
            public void run() {
                String imageUrl = mImageViewCache.remove(imageView);
                if (imageUrl == null) {
                    EasyLog.w("Handler imageUrl 为空---");
                    return;
                }
                Bitmap bitmap = mImageCache.get(imageUrl);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    setImage(imageUrl, imageView);
                }
            }
        });*/
    }

    private synchronized static void setImageViewEmpty(ImageView imageView) {
        Message message = new Message();
        message.what = H_IMAGE_EMPTY;
        message.obj = imageView;
        mHandler.sendMessage(message);
    }

    protected ImageLoaderManager() {
    }

    public synchronized static void init(ImageLoaderConfig builder) {
        config = builder;
        int httThreadSize = 3;
        mHttp = new Http(httThreadSize);
//        mExecutorService = Executors.newFixedThreadPool(builder.getHttpMaxThread());
        mExecutorService = Executors.newFixedThreadPool(1);
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return 1;
            }
        };
    }

    public synchronized static void setImage(final String imageUrl, final ImageView imageView, final int width, final int height) {
        mExecutorService.submit(new Runnable() {
            public void run() {
                if (imageView == null) {
                    EasyLog.w(TAG, "setImage ImageView is null");
                    return;
                }
                if (TextUtils.isEmpty(imageUrl)) {
                    setImageViewEmpty(imageView);
                    return;
                }

                if (mDownloadingUrl.contains(imageUrl)) {//正在下载
                    return;
                }
                if (mImageViewCache.containsKey(imageView)) {
                    return;
                }
                mImageViewCache.put(imageView, imageUrl);

                boolean cacheResult = getFromCache(imageUrl, imageView);
                EasyLog.w(TAG, "setImage cacheResult = " + cacheResult);
                if (cacheResult)
                    return;

                boolean SDCardResult = getFromSDCard(width, height, imageUrl, imageView);
                if (SDCardResult)
                    return;

                setImageViewEmpty(imageView);
                getFromHttp(imageUrl);
            }
        });
    }

    public synchronized static void setImage(String imageUrl, ImageView imageView) {
        setImage(imageUrl, imageView, config.getImgWidth(), config.getImgHeight());
    }

    private synchronized static boolean getFromCache(String imageUrl, ImageView imageView) {
        EasyLog.d(TAG, "getFromCache-----");
        Bitmap bitmap = mImageCache.get(imageUrl);
        if (bitmap == null) {
            return false;
        } else {
            setImageView(imageView);
            return true;
        }

    }

    /**
     * 从SD卡获取
     *
     * @param imageUrl
     * @return
     * @smallRate 压缩比例
     */
    private synchronized static boolean getFromSDCard(int picWidth, int picHeight, String imageUrl, ImageView imageView) {
        EasyLog.d(TAG, "getFromSDCard-----");
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
            if (width > picWidth)
                opt.inSampleSize *= width / picWidth;
        } else {
            if (height > picHeight)
                opt.inSampleSize *= height / picHeight;
        }

        //这次再真正地生成一个有像素的，经过缩放了的bitmap
        opt.inJustDecodeBounds = false;
        opt.inPreferredConfig = config.getBitmapConfig();
        final Bitmap bitmap = BitmapFactory.decodeFile(filePath, opt);
        if (bitmap == null) {
            return false;
        } else {
            mImageCache.put(imageUrl, bitmap);
            setImageView(imageView);
            return true;
        }
    }

    /**
     * 从网络获取
     *
     * @param imageUrl 图片地址
     */
    private synchronized static void getFromHttp(String imageUrl) {
        EasyLog.d(TAG, "getFromHttp-----");
        mDownloadingUrl.add(imageUrl);
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
    private synchronized static String urlChangeToName(String imageUrl) {
        String name = imageUrl;
        if (name.length() > config.getNameMaxLen()) {
            name = name.substring(name.length() - config.getNameMaxLen(), name.length());
        }
        name = name.replaceAll(":", "_");
        name = name.replaceAll("/", "_");
        return name;
    }

    private static HttpDownloadListener mHttpListener = new HttpDownloadListener() {
        @Override
        public void success(int flagCode, final String imageUrl, String filePath) {
            EasyLog.d(TAG, "图片下载成功-----");
            mImageViewCache.remove(null);
            Set<ImageView> set = mImageViewCache.keySet();
            for (ImageView imageView : set) {
                String value = mImageViewCache.get(imageView);
                if (imageUrl.equals(value)) {
                    if (imageView != null) {
                        getFromSDCard(config.getImgWidth(), config.getImgHeight(), imageUrl, imageView);
                    }
                    break;
                }
            }
        }

        @Override
        public void fail(int flagCode, String imageUrl, String msg) {
            EasyLog.d(TAG, "图片下载失败-----");
            mDownloadingUrl.remove(imageUrl);
        }

        @Override
        public void progress(int flagCode, String flag, long currentPoint, long endPoint) {

        }
    };

}
