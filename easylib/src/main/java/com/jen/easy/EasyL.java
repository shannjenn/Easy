package com.jen.easy;

import android.database.sqlite.SQLiteDatabase;

/**
 * 监听类
 * 创建人：ShannJenn
 * 时间：2017/8/12.
 */

public abstract class EasyL {


    /**
     * 数据库********************
     */
    public abstract static class DB {
        /**
         * 数据监听
         * Created by Jen on 2017/7/31.
         */
        public interface DatabaseListener {

            /**
             * 数据库升级
             *
             * @param db
             * @param oldVersion
             * @param newVersion
             */
            void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
        }

    }

    /**
     * LOG日记********************
     */
    public abstract static class LOG {
        /**
         * 日志抓取监听
         */
        public interface CrashListener {
            /**
             * @param ex
             * @return ture用户处理，false系统处理并抛出异常
             */
            boolean onBeforeHandleException(Throwable ex);
        }
    }

    /**
     * 网络请求********************
     */
    public abstract static class HTTP {
        /**
         * 基本数据网络请求监听
         */

        public interface BaseListener {

            void success(int flagCode, String flag, Object msg);

            void fail(int flagCode, String flag, int easyHttpCode, String msg);
        }

        /**
         * 下载监听
         */
        public interface DownloadListener {

            void success(int flagCode, String flag);

            void fail(int flagCode, String flag, int easyHttpCode, String msg);

            void progress(int flagCode, String flag, long currentPoint, long endPoint);
        }

        /**
         * 上传监听
         */

        public interface UploadListener {

            void success(int flagCode, String flag, Object result);

            void fail(int flagCode, String flag, int easyHttpCode, String msg);

            void progress(int flagCode, String flag, long currentPoint, long endPoint);
        }

    }

}
