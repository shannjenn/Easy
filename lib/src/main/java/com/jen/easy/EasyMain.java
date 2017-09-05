package com.jen.easy;

import android.text.TextUtils;

import com.jen.easy.aop.DynamicProxy;
import com.jen.easy.app.EasyApplication;
import com.jen.easy.bind.Bind;
import com.jen.easy.constant.Constant;
import com.jen.easy.decrypt.FileDecrypt;
import com.jen.easy.http.Http;
import com.jen.easy.log.EasyLog;
import com.jen.easy.log.LogcatHelper;
import com.jen.easy.share.Share;
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
    public static final Bind BIND;
    /**
     * 网络请求（结合注释@EasyMouse.HTTP使用）
     */
    public static final Http HTTP;
    /**
     * 数据库操作（结合注释@EasyMouse.DB使用）
     */
    public static final DBHelper DB;
    /**
     * 数据表操作（结合注释@EasyMouse.DB使用）
     */
    public static final DBDao Dao;
    /**
     * 日志取
     */
    public static final LogcatHelper Log;
    /**
     * 数据存储SharedPreferences
     */
    public static final Share SHARE;

    static {
        EasyLog.d("init EasyMain -------");

        BIND = new Bind();
        HTTP = new Http();
//        Parse = new HttpParseManager();
        Log = new LogcatHelper();

        if (EasyApplication.getAppContext() != null) {
            SHARE = new Share(EasyApplication.getAppContext());

            if (TextUtils.isEmpty(Constant.DB.PASSWORD)) {
                DB = new DBHelper(EasyApplication.getAppContext());
                Dao = new DBDao(EasyApplication.getAppContext());
            } else {
                DBHelper DBtemp = new DBHelper(EasyApplication.getAppContext());
                DBDao DBDtemp = new DBDao(EasyApplication.getAppContext());

                DynamicProxy proxyDB = new DynamicProxy();
                DB = (DBHelper) proxyDB.bind(DBtemp);
                String path = EasyApplication.getAppContext().getDatabasePath(DB.getDBName()).getPath();

                proxyDB.setBeforeMethod(FileDecrypt.class, path, Constant.DB.PASSWORD);
                proxyDB.setAfterMethod(FileDecrypt.class, path, Constant.DB.PASSWORD);

                DynamicProxy proxyDBD = new DynamicProxy();
                Dao = (DBDao) proxyDBD.bind(DBDtemp);
                proxyDBD.setBeforeMethod(FileDecrypt.class, path, Constant.DB.PASSWORD);
                proxyDBD.setAfterMethod(FileDecrypt.class, path, Constant.DB.PASSWORD);
            }
        } else {
            SHARE = null;
            DB = null;
            Dao = null;
            EasyLog.e("请继承:" + EasyApplication.class.getSimpleName());
        }
    }
}
