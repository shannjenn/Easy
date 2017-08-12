package com.jen.easy;

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
import com.jen.easy.sqlite.DBHelperManager;
import com.jen.easy.sqlite.DBDaoManager;
import com.jen.easy.sqlite.imp.DBHelperImp;
import com.jen.easy.sqlite.imp.DBDaoImp;
import com.jen.easy.util.DataFormatManager;
import com.jen.easy.util.imp.DataFormatImp;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/12.
 */

public class Easy {
    /**
     * ID绑定
     */
    public static BindImp BIND;
    /**
     * 网络请求
     */
    public static HttpImp HTTP;
    /**
     * 网络数据解析
     */
    public static HttpParseImp HPARSE;
    /**
     * 数据库操作
     */
    public static DBHelperImp DB;
    /**
     * 数据表操作
     */
    public static DBDaoImp DBD;
    /**
     * 日志取
     */
    public static LogcatHelperImp LOG;
    /**
     * 时间格式
     */
    public static DataFormatImp FORMAT;

    static {
        BIND = new BindManager();
        HTTP = new HttpManager();
        HPARSE = new HttpParseManager();
        if (EasyApplication.getAppContext() != null) {
            DB = new DBHelperManager(EasyApplication.getAppContext());
            DBD = new DBDaoManager(DB);
        } else {
            Logcat.e("请继承:" + EasyApplication.class.getSimpleName());
        }
        LOG = new LogcatHelperManager();
        FORMAT = new DataFormatManager();
    }


    private Easy() {
    }
}
