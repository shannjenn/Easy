package com.jen.easy.sqlite.imp;

import android.database.sqlite.SQLiteDatabase;

/**
 * 作者：ShannJenn
 * 时间：2017/9/4:21:35
 * 说明：数据库监听
 */

public interface DatabaseListener {
    /**
     * 数据库升级
     *
     * @param db         数据库
     * @param oldVersion 旧版本号
     * @param newVersion 升级版本号
     */
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
