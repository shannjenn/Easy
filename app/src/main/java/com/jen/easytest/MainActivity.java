package com.jen.easytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jen.easy.EasyMain;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EasyMain.BIND.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyMain.BIND.unbind(this);
    }
}
