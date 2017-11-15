package com.jen.easyui.image;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import com.jen.easy.EasyMain;
import com.jen.easy.app.EasyApplication;
import com.jen.easy.http.HttpDownloadPRequest;
import com.jen.easy.http.imp.HtppDownloadListener;
import com.jen.easy.log.EasyUILog;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片加载工具
 * 作者：ShannJenn
 * 时间：2017/11/08.
 */

abstract class ImageLoaderManager {
    /*本地缓存目录*/
    private String LOCAL_PATH;
    /*图片缓存*/
    private final Map<String, SoftReference<Drawable>> mImageCache = new HashMap<>();
    /*网络获取图片请求参数*/
    private HttpDownloadPRequest mHttpRequest = new HttpDownloadPRequest();
    /*ImageView缓存*/
    private final Map<String, ImageView> mViewCache = new HashMap<>();


    private final int SUCCESS = 100;
    private final int FAIL = 101;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS: {
                    String imageUrl = (String) msg.obj;
                    ImageView imageView = mViewCache.get(imageUrl);
                    if (imageView != null) {
                        SoftReference<Drawable> softReference = mImageCache.get(imageUrl);
                        Drawable drawable = softReference.get();
                        if (drawable != null) {
                            imageView.setImageDrawable(drawable);
                        } else {
                            EasyUILog.e("mHandler drawable is null");
                        }
                    } else {
                        EasyUILog.e("mHandler imageView is null");
                    }
                    break;
                }
                case FAIL: {

                    break;
                }
                default: {

                    break;
                }
            }
        }
    };

    protected ImageLoaderManager() {
        init();
    }

    private void init() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ".EasyImageLoaderCache";
            File file = new File(LOCAL_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else if (LOCAL_PATH == null) {
            LOCAL_PATH = EasyApplication.getAppContext().getFilesDir().getAbsolutePath() + File.separator + "EasyImageLoaderCache";
            File file = new File(LOCAL_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    public void setImage(String imageUrl, ImageView imageView) {
        Drawable drawable;
        if (mImageCache.containsKey(imageUrl)) {
            SoftReference<Drawable> softReference = mImageCache.get(imageUrl);
            drawable = softReference.get();
            if (drawable != null) {
                imageView.setImageDrawable(drawable);
                return;
            }
        }

        drawable = getFromFile(imageUrl);
        if (drawable != null) {
            SoftReference<Drawable> softReference = new SoftReference<>(drawable);
            mImageCache.put(imageUrl, softReference);
            imageView.setImageDrawable(drawable);
            return;
        }

        getFromHttp(imageUrl);
        mViewCache.put(imageUrl, imageView);
    }

    private Drawable getFromFile(String imageUrl) {
        String name = urlChangeToName(imageUrl);
        Drawable drawable = Drawable.createFromPath(LOCAL_PATH + File.separator + name);
        if (drawable != null) {
            return drawable;
        }
        return null;
    }

    private void getFromHttp(String imageUrl) {
        String name = urlChangeToName(imageUrl);
        String filePath = LOCAL_PATH + File.separator + name;
        mHttpRequest.http.url = imageUrl;
        mHttpRequest.request.filePath = filePath;
        mHttpRequest.request.flag = imageUrl;
        mHttpRequest.setDownloadListener(mHttpListener);
        EasyMain.mHttp.start(mHttpRequest);
    }

    private String urlChangeToName(String imageUrl) {
        String name = imageUrl;
        if (name.length() > 20) {
            name = name.substring(name.length() - 20, name.length());
        }
        name.replaceAll(":", "_");
        name.replaceAll("/", "_");
        return name;
    }

    private HtppDownloadListener mHttpListener = new HtppDownloadListener() {
        @Override
        public void success(int flagCode, String flag, String filePath) {
            boolean success = true;
            Drawable drawable = Drawable.createFromPath(filePath);
            if (drawable == null) {
                EasyUILog.e("mHttpListener success error");
                success = false;
            } else {
                SoftReference<Drawable> softReference = new SoftReference<>(drawable);
                mImageCache.put(flag, softReference);
            }

            Message message = mHandler.obtainMessage();
            message.what = success ? SUCCESS : FAIL;
            message.obj = flag;
            mHandler.sendMessage(message);
        }

        @Override
        public void fail(int flagCode, String flag, String msg) {
            EasyUILog.e("mHttpListener fail");
        }

        @Override
        public void progress(int flagCode, String flag, long currentPoint, long endPoint) {

        }
    };
}