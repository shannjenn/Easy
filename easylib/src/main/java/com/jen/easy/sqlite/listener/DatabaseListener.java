package com.jen.easy.sqlite.listener;

import android.database.sqlite.SQLiteDatabase;

/**
 * 数据监听
 * Created by Jen on 2017/7/31.
 */

public interface DatabaseListener {

    void onCreate(SQLiteDatabase db);

    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
