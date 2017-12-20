package com.jen.easy.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 作者：ShannJenn
 * 时间：2017/12/14:22:59
 * 说明：
 */
public class ImageTools {

    /**
     * 图片压缩 质量压缩
     *
     * @param image
     * @return Bitmap
     * @author Jenn 2014年11月25日 上午11:58:52
     * @since 1.0.0
     */
    public static Bitmap Quality(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {// 循环判断如果压缩后图片是否大于100kb,大于继续压缩

            baos.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(bais, null, null);
        return bitmap;
    }

    /**
     * 图片压缩 根据路径按比例大小压缩
     *
     * @param srcPath
     * @return Bitmap
     * @author Jenn 2014年11月25日 上午11:59:14
     * @since 1.0.0
     */
    public static Bitmap SizeByPath(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了

        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空


        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为

        float hh = 800f;// 这里设置高度为800f

        float ww = 480f;// 这里设置宽度为480f

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可

        int be = 1;// be=1表示不缩放

        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放

            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放

            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例

        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return Quality(bitmap);// 压缩好比例大小后再进行质量压缩

    }

    /**
     * 图片压缩 根据Bitmap图片 按比例大小压缩方法
     *
     * @param image
     * @return Bitmap
     * @author Jenn 2014年11月25日 下午12:00:23
     * @since 1.0.0
     */
    public static Bitmap SizeByBitmap(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出

            baos.reset();// 重置baos即清空baos

            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了

        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为

        float hh = 8f;// 这里设置高度为800f

        float ww = 4f;// 这里设置宽度为480f

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可

        int be = 0;// be=1表示不缩放

        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放

            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放

            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例

        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了

        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return Quality(bitmap);// 压缩好比例大小后再进行质量压缩

    }

    /**
     * 更改图片大小显示 按bitmap
     *
     * @param bitmap
     * @param w
     * @param h
     * @return Bitmap
     * @author Jenn 2014年11月25日 下午12:00:50
     * @since 1.0.0
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            // int newWidth = w;

            // int newHeight = h;

            float scaleWidth = ((float) w) / width;
            float scaleHeight = ((float) h) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            return resizedBitmap;
        } else {
            return null;
        }
    }

    /**
     * 更改图片大小显示 按路径
     *
     * @param path
     * @param width
     * @param height
     * @return Bitmap
     * @author Jenn 2014年11月25日 下午12:01:22
     * @since 1.0.0
     */
    public static Bitmap resizeBitmap(String path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.outWidth = width;
        options.outHeight = height;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        options.inSampleSize = options.outWidth / height;
        options.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(path, options);
        return bmp;
    }

    /**
     * bitmaptoString:将图片Bitmap转换成字符串
     *
     * @param bitmap
     * @return String
     * @author Jenn 2015-6-30 下午5:01:06
     * @since 1.0.0
     */
    public String bitmaptoString(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * stringtoBitmap:将字符串转换成图片Bitmap类型
     *
     * @param string
     * @return Bitmap
     * @author Jenn 2015-6-30 下午5:01:35
     * @since 1.0.0
     */
    public Bitmap stringtoBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}