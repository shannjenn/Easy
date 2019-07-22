package com.jen.easy.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.LruCache;
import android.widget.ImageView;

import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.ImageLoaderLog;
import com.jen.easy.http.EasyHttp;
import com.jen.easy.http.imp.EasyHttpListener;

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
    private LruCache<String, Bitmap> mImageCache;//图片缓存
    private List<String> mDownloadingUrl = new ArrayList<>();
    private Map<ImageView, String> mImageViewCache = new HashMap<>();//key:,ImageView:imageUrl缓存
    private ExecutorService mExecutorService;
    private EasyHttp mHttp;
    private final int H_IMAGE = 100;
    private final int H_IMAGE_EMPTY = 101;
    private ImageLoaderConfig config;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            runHandler(msg);
        }
    };

    ImageLoaderManager() {

    }

    private void setImageView(final ImageView imageView) {
        Message message = new Message();
        message.what = H_IMAGE;
        message.obj = imageView;
        mHandler.sendMessage(message);
    }

    private void setImageViewEmpty(ImageView imageView) {
        Message message = new Message();
        message.what = H_IMAGE_EMPTY;
        message.obj = imageView;
        mHandler.sendMessage(message);
    }

    private void runHandler(Message msg) {
        synchronized (this) {
            switch (msg.what) {
                case H_IMAGE: {
                    ImageView imageView = (ImageView) msg.obj;
                    if (imageView == null) {
                        mImageViewCache.remove(null);
                        ImageLoaderLog.exception(ExceptionType.NullPointerException, "Handler imageView 为空---");
                        return;
                    }
                    String imageUrl = mImageViewCache.remove(imageView);
                    if (imageUrl == null) {
                        ImageLoaderLog.exception(ExceptionType.NullPointerException, "Handler imageUrl 为空---");
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
                        ImageLoaderLog.exception(ExceptionType.NullPointerException, "imageView 为空---");
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

    protected void setImage(final String imageUrl, final ImageView imageView, final int width, final int height) {
        mExecutorService.submit(new Runnable() {
            public void run() {
                if (imageView == null) {
                    ImageLoaderLog.exception(ExceptionType.NullPointerException, "setImage ImageView is null");
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

    protected void setImage(String imageUrl, ImageView imageView) {
        setImage(imageUrl, imageView, config.getImgWidth(), config.getImgHeight());
    }

    private boolean getFromCache(String imageUrl, ImageView imageView) {
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
     * @param picWidth  宽
     * @param picHeight 高
     * @param imageUrl  url
     * @param imageView 。
     * @return 。
     */
    private boolean getFromSDCard(int picWidth, int picHeight, String imageUrl, ImageView imageView) {
        ImageLoaderLog.d("getFromSDCard-----");
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
    private void getFromHttp(String imageUrl) {
        ImageLoaderLog.d("getFromHttp-----");
        mDownloadingUrl.add(imageUrl);
        String name = urlChangeToName(imageUrl);
        String filePath = config.getLocalPath() + File.separator + name;
        ImagLoaderRequest request = new ImagLoaderRequest();//网络获取图片请求参数
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
        public void success(int flagCode, String flag, Object response, Map<String, List<String>> headMap) {
            ImageLoaderLog.d("图片下载成功-----");
            mImageViewCache.remove(null);
            if (response instanceof ImagLoaderResponse) {
                ImagLoaderResponse imgResponse = (ImagLoaderResponse) response;
                Set<ImageView> set = mImageViewCache.keySet();
                for (ImageView imageView : set) {
                    String value = mImageViewCache.get(imageView);
                    if (response.equals(value)) {
                        getFromSDCard(config.getImgWidth(), config.getImgHeight(), imgResponse.getFilePath(), imageView);
                        break;
                    }
                }
            }
        }

        @Override
        public void fail(int flagCode, String imageUrl, Object msg) {
            ImageLoaderLog.d("图片下载失败-----");
            mDownloadingUrl.remove(imageUrl);
        }

        @Override
        public void progress(int flagCode, String flag, Object response, long currentPoint, long endPoint) {
            ImageLoaderLog.d("flag = " + flag + " currentPoint = " + currentPoint + " endPoint=" + endPoint);
        }
    };

}
