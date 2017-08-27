package com.jen.easyui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jen.easyui.listview.EasyListView;

public class MainActivity extends AppCompatActivity {

    EasyListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
