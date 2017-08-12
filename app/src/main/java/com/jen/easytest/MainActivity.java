package com.jen.easytest;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.jen.easy.Easy;
import com.jen.easy.EasyF;

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
        Easy.BIND.bind(this);

        File file = getDatabasePath("Easy.db");
        SQLiteDatabase.create(null);
        if(EasyF.HTTP.Code.FAIL == 0){

        };
    }
}
