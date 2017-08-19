package com.jen.easytest;

import android.app.Activity;
import android.os.Bundle;

import com.jen.easy.EasyMain;

import java.io.File;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        File file = getDatabasePath("easy.db");
        File parent  =file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

//        EasyMain.BIND.bind(this);
//        EasyMain.HTTP.start(null);
        EasyMain.DB.create();

//        EasyMain.LOG.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyMain.BIND.unbind(this);
    }

}
