package com.jen.easy;

import android.text.TextUtils;

import com.jen.easy.aop.DynamicProxy;
import com.jen.easy.app.EasyApplication;
import com.jen.easy.bind.BindView;
import com.jen.easy.constant.Constant;
import com.jen.easy.decrypt.FileDecrypt;
import com.jen.easy.http.Http;
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
    public static final BindView mBindView;
    /**
     * 网络请求（结合注释@EasyMouse.HTTP使用）
     */
    public static final Http mHttp;
    /**
     * 数据库操作（结合注释@EasyMouse.DB使用）
     */
    public static final DBHelper mDBHelper;
    /**
     * 数据表操作（结合注释@EasyMouse.DB使用）
     */
    public static final DBDao mDao;
    /**
     * 日志取
     */
    public static final LogcatHelper mLog;
    /**
     * 数据存储SharedPreferences
     */
    public static final Shared mShared;

    static {
        EasyLibLog.d("init EasyMain -------");

        mBindView = new BindView();
        mHttp = new Http();
//        Parse = new HttpParseManager();
        mLog = new LogcatHelper();

        if (EasyApplication.getAppContext() != null) {
            mShared = new Shared(EasyApplication.getAppContext());

            if (TextUtils.isEmpty(Constant.DB.PASSWORD)) {
                mDBHelper = new DBHelper(EasyApplication.getAppContext());
                mDao = new DBDao(EasyApplication.getAppContext());
            } else {
                DBHelper DBtemp = new DBHelper(EasyApplication.getAppContext());
                DBDao DBDtemp = new DBDao(EasyApplication.getAppContext());

                DynamicProxy proxyDB = new DynamicProxy();
                mDBHelper = (DBHelper) proxyDB.bind(DBtemp);
                String path = EasyApplication.getAppContext().getDatabasePath(mDBHelper.getDBName()).getPath();

                proxyDB.setBeforeMethod(FileDecrypt.class, path, Constant.DB.PASSWORD);
                proxyDB.setAfterMethod(FileDecrypt.class, path, Constant.DB.PASSWORD);

                DynamicProxy proxyDBD = new DynamicProxy();
                mDao = (DBDao) proxyDBD.bind(DBDtemp);
                proxyDBD.setBeforeMethod(FileDecrypt.class, path, Constant.DB.PASSWORD);
                proxyDBD.setAfterMethod(FileDecrypt.class, path, Constant.DB.PASSWORD);
            }
        } else {
            mShared = null;
            mDBHelper = null;
            mDao = null;
            EasyLibLog.e("请继承:" + EasyApplication.class.getSimpleName());
        }
    }
}
