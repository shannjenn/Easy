package com.jen.easy.imageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
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
import java.util.HashMap;
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
    /*本地缓存目录*/
    private String LOCAL_PATH;
    /*保存文件名最长长度*/
    private final int NAME_LENG = 100;
    /*图片缓存*/
//    private final Map<String, SoftReference<Drawable>> mImageCache = new HashMap<>();
    private LruCache<String, Bitmap> mImageCache;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(2);
    /*网络获取图片请求参数*/
    private HttpDownloadRequest mHttpRequest = new HttpDownloadRequest();
    /*ImageView缓存*/
    private final Map<String, ImageView> mViewCache = new HashMap<>();
    /*默认图片*/
    private Drawable mDefaultImage;
    /*默认高宽*/
    private int mDefaultHeight = 300, mDefaultWidth = 300;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private int mHttpMaxThread = 3;//默认三个线程
    private int timeOut = 10000;//默认超时
    private Http mHttp;

    ImageLoaderManager(Context context,int httpMaxThread) {
        if (httpMaxThread > 0) {
            mHttpMaxThread = httpMaxThread;
        }
        init(context);
    }

    private void init(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ".EasyImageLoaderCache";
            File file = new File(LOCAL_PATH);
            if (!file.exists()) {
                boolean result = file.mkdirs();
                if (!result) {
                    EasyUILog.e(TAG + "创建图片缓存目录失败1");
                }
            }
        } else if (LOCAL_PATH == null) {
            LOCAL_PATH = context.getFilesDir().getAbsolutePath() + File.separator + "EasyImageLoaderCache";
            File file = new File(LOCAL_PATH);
            if (!file.exists()) {
                boolean result = file.mkdirs();
                if (!result) {
                    EasyUILog.e(TAG + "创建图片缓存目录失败2");
                }
            }
        }

        mHttp = new Http(mHttpMaxThread);
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        EasyUILog.d("cacheSize=" + cacheSize);
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
        setImage(imageUrl, imageView, mDefaultWidth, mDefaultHeight);
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
        final String filePath = LOCAL_PATH + File.separator + name;
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
        mViewCache.put(imageUrl, imageView);
        String name = urlChangeToName(imageUrl);
        String filePath = LOCAL_PATH + File.separator + name;
        mHttpRequest.httpParam.url = imageUrl;
        mHttpRequest.httpParam.timeout = timeOut;
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
        if (name.length() > NAME_LENG) {
            name = name.substring(name.length() - NAME_LENG, name.length());
        }
        name = name.replaceAll(":", "_");
        name = name.replaceAll("/", "_");
        return name;
    }

    private HttpDownloadListener mHttpListener = new HttpDownloadListener() {
        @Override
        public void success(int flagCode, final String imageUrl, String filePath) {
            final ImageView imageView = mViewCache.remove(imageUrl);
            if (imageView == null) {
                EasyUILog.e(TAG + "mHttpListener success imageView == null imageUrl=" + imageUrl);
                return;
            }
            boolean SDCardResult = getFromSDCard(mDefaultWidth, mDefaultHeight, imageUrl, imageView);
            if (!SDCardResult) {
                setImageByHandler(null, imageView);
            }
        }

        @Override
        public void fail(int flagCode, String imageUrl, String msg) {
            ImageView imageView = mViewCache.remove(imageUrl);
            if (imageView == null) {
                EasyUILog.e(TAG + "mHttpListener fail imageView == null imageUrl=" + imageUrl);
                return;
            }
            setImageByHandler(null, imageView);
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
                    imageView.setImageDrawable(mDefaultImage);
                }
            }
        });
    }

    protected ImageLoaderManager setDefaultImage(Drawable defaultImage) {
        mDefaultImage = defaultImage;
        return this;
    }

    protected ImageLoaderManager setDefaultHeight(int defautHeight) {
        this.mDefaultHeight = defautHeight;
        return this;
    }

    protected ImageLoaderManager setDefaultWidth(int defautWidth) {
        this.mDefaultWidth = defautWidth;
        return this;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
