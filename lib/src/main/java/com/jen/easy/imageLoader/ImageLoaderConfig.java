package com.jen.easy.imageLoader;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.jen.easy.exception.ExceptionType;
import com.jen.easy.exception.ImageLoaderLog;

import java.io.File;

/**
 * 作者：ShannJenn
 * 时间：2018/2/8:0:19
 * 说明：
 */

public class ImageLoaderConfig {
    private Context context;

    private String localPath;//本地缓存目录
    private final int nameMaxLen = 100;//保存文件名最长长度
    private Drawable defaultImage;//默认图片
    private int imgHeight = 300, imgWidth = 300;//默认高宽
    private final int httpMaxThread = 5;//默认三个线程
    private int timeOut = 5000;//默认超时毫秒
    private Bitmap.Config bitmapConfig;//图片配置

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
                    ImageLoaderLog.exception(ExceptionType.IllegalArgumentException, "创建图片缓存目录失败1");
                }
            }
        } else if (localPath == null) {
            localPath = context.getFilesDir().getAbsolutePath() + File.separator + ".EasyImageLoaderCache";
            File file = new File(localPath);
            if (!file.exists()) {
                boolean result = file.mkdirs();
                if (!result) {
                    ImageLoaderLog.exception(ExceptionType.IllegalArgumentException, "创建图片缓存目录失败2");
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

    public ImageLoaderConfig imgHeight(int defaultHeight) {
        this.imgHeight = defaultHeight;
        return this;
    }

    public ImageLoaderConfig imgWidth(int defaultWidth) {
        this.imgWidth = defaultWidth;
        return this;
    }

    public ImageLoaderConfig timeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    /*public ImageLoaderConfig httpMaxThread(int httpMaxThread) {
        if (httpMaxThread > 0) {
            this.httpMaxThread = httpMaxThread;
        }
        return this;
    }*/

    public ImageLoaderConfig bitmapConfig(Bitmap.Config bitmapConfig) {
        this.bitmapConfig = bitmapConfig;
        return this;
    }


    String getLocalPath() {
        return localPath;
    }

    int getNameMaxLen() {
        return nameMaxLen;
    }

    Drawable getDefaultImage() {
        return defaultImage;
    }

    int getImgHeight() {
        return imgHeight;
    }

    int getImgWidth() {
        return imgWidth;
    }

    int getHttpMaxThread() {
        return httpMaxThread;
    }

    int getTimeOut() {
        return timeOut;
    }

    Bitmap.Config getBitmapConfig() {
        return bitmapConfig;
    }
}
