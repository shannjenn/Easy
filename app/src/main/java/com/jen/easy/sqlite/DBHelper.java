package com.jen.easy.sqlite;

import android.app.Application;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
    private static DBHelper dbHelper;
    private static Database database;

    private DBHelper() {

    }

    /**
     * 获取实例
     *
     * @return
     */
    public static DBHelper getInstance(Application appContext) {
        if (dbHelper == null) {
            dbHelper = new DBHelper();
            database = new Database(appContext);
        }
        return dbHelper;
    }

    /**
     * 读取数据库
     *
     * @return
     */
    public SQLiteDatabase getReadDatabse() {
        return database.getReadableDatabase();
    }

    /**
     * 写入数据库
     *
     * @return
     */
    public SQLiteDatabase getWtriteDatabse() {
        return database.getWritableDatabase();
    }

    /**
     * 获取数据库名称
     */
    public String getDBName() {
        return DB.DB_NAME;
    }

    /**
     * 获取数据库版本号
     */
    public int getDBVersion() {
        return DB.DB_VERSION;
    }

    /**
     * 设置数据库版本号
     */
    public void setDBVersion(int version) {
        DB.DB_VERSION = version;
    }

    /**
     * 创建表
     */
    public static void createTB() {
        DBLog.d("createTB");
        if (dbHelper == null) {
            DBLog.d("dbHelper is null");
            return;
        }
        SQLiteDatabase db = dbHelper.getWtriteDatabse();
        try {
            db.beginTransaction();

            db.execSQL(TB.CTREATE_TB_COURSEFILEINFO);//课件下载表
            DBLog.d("CTREATE_TB_COURSEFILEINFO SUCCESS");
            db.execSQL(TB.CTREATE_TB_STUDYPROGRESS);//学习进度表
            DBLog.d("CTREATE_TB_STUDYPROGRESS SUCCESS");

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            DBLog.d("createTB error");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    private void TBSql() {

		/*final String CTREATE_TB_COURSEFILEINFO = "create table if not exists " + TB_CourseFileInfo +
                "(" +
				CourseFIleInfoClounms.CID.toString() + " varchar," +
				CourseFIleInfoClounms.UID.toString() + " varchar," +
				CourseFIleInfoClounms.N.toString() + " varchar," +
				CourseFIleInfoClounms.M.toString() + " varchar," +
				CourseFIleInfoClounms.P.toString() + " varchar," +
				CourseFIleInfoClounms.CNO.toString() + " varchar," +
				CourseFIleInfoClounms.SNO.toString() + " varchar," +
				CourseFIleInfoClounms.VER.toString() + " varchar," +
				CourseFIleInfoClounms.L.toString() + " varchar," +
				CourseFIleInfoClounms.E.toString() + " varchar," +
				CourseFIleInfoClounms.folder.toString() + " varchar," +
				"primary key ("+CourseFIleInfoClounms.CID.toString()+","+CourseFIleInfoClounms.UID.toString()+")"+
				")";*/
    }

}
