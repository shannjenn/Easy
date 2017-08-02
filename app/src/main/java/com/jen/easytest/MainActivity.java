package com.jen.easytest;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easy.bind.EasyBind;
import com.jen.easy.sqlite.DBHelper;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyBind.bind(this);
        DBHelper.getInstance().getDBName();
    }
}
