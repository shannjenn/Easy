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
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
