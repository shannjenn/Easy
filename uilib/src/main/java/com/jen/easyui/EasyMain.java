package com.jen.easyui;

import android.app.Application;
import android.text.TextUtils;

import com.jen.easy.aop.DynamicProxy;
import com.jen.easy.bind.BindView;
import com.jen.easy.constant.Constant;
import com.jen.easy.decrypt.FileDecrypt;
import com.jen.easy.http.Http;
import com.jen.easy.imageLoader.ImageLoader;
import com.jen.easy.log.EasyLibLog;
import com.jen.easy.log.LogcatHelper;
import com.jen.easy.share.Shared;
import com.jen.easy.sqlite.DBDao;
import com.jen.easy.sqlite.DBHelper;

/**
 * 核心框架模块
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public final class EasyMain {
    private EasyMain() {
    }

    /**
     * ID绑定（结合注释@EasyMouse.BIND使用）
     */
    public static BindView mBindView;
    /**
     * 网络请求（结合注释@EasyMouse.HTTP使用）
     */
    public static Http mHttp;
    /**
     * 数据库操作（结合注释@EasyMouse.DB使用）
     */
    public static DBHelper mDBHelper;
    /**
     * 数据表操作（结合注释@EasyMouse.DB使用）
     */
    public static DBDao mDao;
    /**
     * 日志取
     */
    public static LogcatHelper mLog;
    /**
     * 数据存储SharedPreferences
     */
    public static Shared mShared;
    /**
     * 图片加载工具
     */
    public static ImageLoader mImageLoader;

    public static void init(Application application) {
        EasyLibLog.d("init EasyMain -------");

        mBindView = new BindView();
        mHttp = new Http(10);//默认最大10个线程
        mLog = new LogcatHelper(application);
        mImageLoader = new ImageLoader(application, -1);

        mShared = new Shared(application);

        if (TextUtils.isEmpty(Constant.DB.PASSWORD)) {
            mDBHelper = new DBHelper(application);
            mDao = new DBDao(application);
        } else {
            DBHelper DBtemp = new DBHelper(application);
            DBDao DBDtemp = new DBDao(application);

            DynamicProxy proxyDB = new DynamicProxy();
            mDBHelper = (DBHelper) proxyDB.bind(DBtemp);
            String path = application.getDatabasePath(mDBHelper.getDBName()).getPath();

            proxyDB.setBeforeMethod(FileDecrypt.class, path, Constant.DB.PASSWORD);
            proxyDB.setAfterMethod(FileDecrypt.class, path, Constant.DB.PASSWORD);

            DynamicProxy proxyDBD = new DynamicProxy();
            mDao = (DBDao) proxyDBD.bind(DBDtemp);
            proxyDBD.setBeforeMethod(FileDecrypt.class, path, Constant.DB.PASSWORD);
            proxyDBD.setAfterMethod(FileDecrypt.class, path, Constant.DB.PASSWORD);
        }
    }
}
