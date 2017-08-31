package com.jen.easy;

import android.text.TextUtils;

import com.jen.easy.app.EasyApplication;
import com.jen.easy.bind.BindManager;
import com.jen.easy.bind.imp.BindImp;
import com.jen.easy.constant.Constant;
import com.jen.easy.http.HttpManager;
import com.jen.easy.parse.HttpParseManager;
import com.jen.easy.http.imp.HttpImp;
import com.jen.easy.parse.imp.HttpParseImp;
import com.jen.easy.log.EasyLog;
import com.jen.easy.log.LogcatHelperManager;
import com.jen.easy.log.imp.LogcatHelperImp;
import com.jen.easy.share.ShareManager;
import com.jen.easy.share.imp.ShareImp;
import com.jen.easy.sqlite.DBDaoManager;
import com.jen.easy.sqlite.DBHelperManager;
import com.jen.easy.sqlite.imp.DBDaoImp;
import com.jen.easy.sqlite.imp.DBHelperImp;

/**
 * 核心框架模块
 * 创建人：ShannJenn
 * 时间：2017/8/12.
 */

public final class EasyMain {
    private EasyMain() {
    }

    /**
     * ID绑定
     */
    public static final BindImp Bing;
    /**
     * 网络请求
     */
    public static final HttpImp Http;
    /**
     * 网络数据解析
     */
    public static final HttpParseImp Parse;
    /**
     * 数据库操作
     */
    public static final DBHelperImp DB;
    /**
     * 数据表操作
     */
    public static final DBDaoImp Dao;
    /**
     * 日志取
     */
    public static final LogcatHelperImp Log;
    /**
     * 数据存储SharedPreferences
     */
    public static final ShareImp Share;

    static {
        EasyLog.d("init EasyMain -------");

        Bing = new BindManager();
        Http = new HttpManager();
        Parse = new HttpParseManager();
        Log = new LogcatHelperManager();

        if (EasyApplication.getAppContext() != null) {
            Share = new ShareManager(EasyApplication.getAppContext());

            if (TextUtils.isEmpty(Constant.DB.PASSWORD)) {
                DB = new DBHelperManager(EasyApplication.getAppContext());
                Dao = new DBDaoManager(EasyApplication.getAppContext());
            } else {
                DBHelperImp DBtemp = new DBHelperManager(EasyApplication.getAppContext());
                DBDaoImp DBDtemp = new DBDaoManager(EasyApplication.getAppContext());

                EasyClass.DynamicProxy proxyDB = new EasyClass.DynamicProxy();
                DB = (DBHelperImp) proxyDB.bind(DBtemp);
                String path = EasyApplication.getAppContext().getDatabasePath(DB.getDBName()).getPath();

                proxyDB.setBeforeMethod(EasyClass.FileDecrypt.class, path, Constant.DB.PASSWORD)
                        .setAfterMethod(EasyClass.FileDecrypt.class, path, Constant.DB.PASSWORD)
                        .bind(DBtemp);

                EasyClass.DynamicProxy proxyDBD = new EasyClass.DynamicProxy();
                Dao = (DBDaoImp) proxyDBD.bind(DBDtemp);
                proxyDBD.setBeforeMethod(EasyClass.FileDecrypt.class, path, Constant.DB.PASSWORD);
                proxyDBD.setAfterMethod(EasyClass.FileDecrypt.class, path, Constant.DB.PASSWORD);
            }
        } else {
            Share = null;
            DB = null;
            Dao = null;
            EasyLog.e("请继承:" + EasyApplication.class.getSimpleName());
        }
    }
}
