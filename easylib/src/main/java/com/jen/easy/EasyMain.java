package com.jen.easy;

import android.text.TextUtils;

import com.jen.easy.app.EasyApplication;
import com.jen.easy.bind.BindManager;
import com.jen.easy.bind.imp.BindImp;
import com.jen.easy.http.HttpManager;
import com.jen.easy.http.HttpParseManager;
import com.jen.easy.http.imp.HttpImp;
import com.jen.easy.http.imp.HttpParseImp;
import com.jen.easy.log.EasyLog;
import com.jen.easy.log.LogcatHelperManager;
import com.jen.easy.log.imp.LogcatHelperImp;
import com.jen.easy.share.ShareManager;
import com.jen.easy.share.imp.ShareImp;
import com.jen.easy.sqlite.DBConstant;
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
    public static final BindImp BIND;
    /**
     * 网络请求
     */
    public static final HttpImp HTTP;
    /**
     * 网络数据解析
     */
    public static final HttpParseImp HPARSE;
    /**
     * 数据库操作
     */
    public static final DBHelperImp DB;
    /**
     * 数据表操作
     */
    public static final DBDaoImp DBD;
    /**
     * 日志取
     */
    public static final LogcatHelperImp LOG;
    /**
     * 数据存储SharedPreferences
     */
    public static final ShareImp SHARE;

    static {
        EasyLog.d("init EasyMain -------");

        BIND = new BindManager();
        HTTP = new HttpManager();
        HPARSE = new HttpParseManager();
        LOG = new LogcatHelperManager();

        if (EasyApplication.getAppContext() != null) {
            SHARE = new ShareManager(EasyApplication.getAppContext());

            if (TextUtils.isEmpty(DBConstant.PASSWORD)) {
                DB = new DBHelperManager(EasyApplication.getAppContext());
                DBD = new DBDaoManager(EasyApplication.getAppContext());
            } else {
                DBHelperImp DBtemp = new DBHelperManager(EasyApplication.getAppContext());
                DBDaoImp DBDtemp = new DBDaoManager(EasyApplication.getAppContext());

                EasyClass.DynamicProxy proxyDB = new EasyClass.DynamicProxy();
                DB = (DBHelperImp) proxyDB.bind(DBtemp);
                String path = EasyApplication.getAppContext().getDatabasePath(DB.getName()).getPath();

                proxyDB.setBeforeMethod(EasyClass.FileDecrypt.class, path, DBConstant.PASSWORD)
                        .setAfterMethod(EasyClass.FileDecrypt.class, path, DBConstant.PASSWORD)
                        .bind(DBtemp);

                EasyClass.DynamicProxy proxyDBD = new EasyClass.DynamicProxy();
                DBD = (DBDaoImp) proxyDBD.bind(DBDtemp);
                proxyDBD.setBeforeMethod(EasyClass.FileDecrypt.class, path, DBConstant.PASSWORD);
                proxyDBD.setAfterMethod(EasyClass.FileDecrypt.class, path, DBConstant.PASSWORD);
            }
        } else {
            SHARE = null;
            DB = null;
            DBD = null;
            EasyLog.e("请继承:" + EasyApplication.class.getSimpleName());
        }
    }
}
