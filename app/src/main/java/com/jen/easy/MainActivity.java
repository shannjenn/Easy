package com.jen.easy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jen.easy.sqlite.DBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        DBHelper dbHelper = DBHelper.getInstance(getApplication());
    }
}
