package com.jen.easytest;

import android.app.Activity;
import android.os.Bundle;

import com.jen.easy.EasyMain;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyMain.BIND.bind(this);
        EasyMain.DB.create();
//        String path = EasyApplication.getAppContext().getDatabasePath(EasyMain.DB.getName()).getPath();
//        FileDecryptFactory.getFileDecrypt().encrypt(path, "123");

        /*DBHelperImp DBtemp = new DBHelperManager(EasyApplication.getAppContext());
        DBDaoImp DBDtemp = new DBDaoManager(EasyApplication.getAppContext());

        DBHelperImp DB = (DBHelperImp) new DynamicProxyManager().bind(DBtemp);
        DB.create();
        Logcat.d("DB="+DB);*/
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyMain.BIND.unbind(this);
    }

}
