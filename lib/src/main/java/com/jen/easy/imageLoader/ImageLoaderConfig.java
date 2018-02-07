package com.jen.easy.imageLoader;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.jen.easy.log.EasyUILog;

import java.io.File;

/**
 * 作者：ShannJenn
 * 时间：2018/2/8:0:19
 * 说明：
 */

public class ImageLoaderConfig {
    private final String TAG = "ImageLoaderConfig";
    private Context context;

    private String localPath;//本地缓存目录
    private final int nameMaxLen = 100;//保存文件名最长长度
    private Drawable defaultImage;//默认图片
    private int imgHeight = 300, imgWidth = 300;//默认高宽
    private int httpMaxThread = 3;//默认三个线程
    private int timeOut = 10000;//默认超时毫秒

    public ImageLoaderConfig(Application context) {
        this.context = context;
    }

    public ImageLoaderConfig build() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            localPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ".EasyImageLoaderCache";
            File file = new File(localPath);
            if (!file.exists()) {
                boolean result = file.mkdirs();
                if (!result) {
                    EasyUILog.e(TAG + "创建图片缓存目录失败1");
                }
            }
        } else if (localPath == null) {
            localPath = context.getFilesDir().getAbsolutePath() + File.separator + "EasyImageLoaderCache";
            File file = new File(localPath);
            if (!file.exists()) {
                boolean result = file.mkdirs();
                if (!result) {
                    EasyUILog.e(TAG + "创建图片缓存目录失败2");
                }
            }
        }
        return this;
//            return new ImageLoaderManager(this);
    }

    public ImageLoaderConfig defaultImage(Drawable defaultImage) {
        this.defaultImage = defaultImage;
        return this;
    }

    public ImageLoaderConfig imgHeight(int defautHeight) {
        this.imgHeight = defautHeight;
        return this;
    }

    public ImageLoaderConfig imgWidth(int defautWidth) {
        this.imgWidth = defautWidth;
        return this;
    }

    public ImageLoaderConfig timeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public ImageLoaderConfig httpMaxThread(int httpMaxThread) {
        if (httpMaxThread > 0) {
            this.httpMaxThread = httpMaxThread;
        }
        return this;
    }

    public String getLocalPath() {
        return localPath;
    }

    public int getNameMaxLen() {
        return nameMaxLen;
    }

    public Drawable getDefaultImage() {
        return defaultImage;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public int getHttpMaxThread() {
        return httpMaxThread;
    }

    public int getTimeOut() {
        return timeOut;
    }
}
