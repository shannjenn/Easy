package com.jen.easytest;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
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
        EasyMain.BIND.bind(this);

        File file = getDatabasePath("EasyMain.db");
        SQLiteDatabase.create(null);
    }


}
