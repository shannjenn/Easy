package easybase;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jen.easy.sqlite.imp.DatabaseListener;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class EasyApplication extends Application {
    private final String TAG = EasyApplication.class.getSimpleName();
    private static EasyApplication mApp;
    private final int DB_VERSION = 1;//数据库版本号

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        createTB();

    }


    /**
     * 创建表
     */
    private void createTB() {
//        if (EasyMain.mDBHelper.getVersion() == 1) {//第一版开始全部执行创建,发版后使用升级操作
//            Log.d(TAG, "创建表------------");
////            EasyMain.mDBHelper.createTB(TablesTatus.class);
//
//        }
    }

    /**
     * 数据库监听
     */
    private DatabaseListener databaseListener = new DatabaseListener() {
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {//升级数据库
            Log.d(TAG, "升级数据库------------");
        }
    };


    public static Application getAppContext() {
        return mApp;
    }

}
