package com.jen.easy;

import com.jen.easy.aop.DynamicProxyManager;
import com.jen.easy.aop.imp.DynamicProxyImp;
import com.jen.easy.app.EasyApplication;
import com.jen.easy.bind.BindManager;
import com.jen.easy.bind.imp.BindImp;
import com.jen.easy.http.HttpManager;
import com.jen.easy.http.HttpParseManager;
import com.jen.easy.http.imp.HttpImp;
import com.jen.easy.http.imp.HttpParseImp;
import com.jen.easy.log.Logcat;
import com.jen.easy.log.LogcatHelperManager;
import com.jen.easy.log.imp.LogcatHelperImp;
import com.jen.easy.sqlite.DBDaoManager;
import com.jen.easy.sqlite.DBHelperManager;
import com.jen.easy.sqlite.imp.DBDaoImp;
import com.jen.easy.sqlite.imp.DBHelperImp;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyMain {
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
     * 切入编程
     */
    public static final DynamicProxyImp PROXY;

    static {
        PROXY = new DynamicProxyManager();

        BIND = new BindManager();
        HTTP = new HttpManager();
        HPARSE = new HttpParseManager();
        if (EasyApplication.getAppContext() != null) {
            DB = new DBHelperManager(EasyApplication.getAppContext());
            DBD = new DBDaoManager(DB);
        } else {
            DB = null;
            DBD = null;
            Logcat.e("请继承:" + EasyApplication.class.getSimpleName());
        }
        LOG = new LogcatHelperManager();
    }
}
