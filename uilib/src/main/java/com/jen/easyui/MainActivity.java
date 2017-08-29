package com.jen.easyui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jen.easyui.constant.FieldType;
import com.jen.easyui.listview.EasyListView;
import com.jen.easyui.listview.ListStyle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EasyListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (EasyListView) findViewById(R.id.list);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Student student = new Student();
//        LayoutReflectmanager.bindItemLayout(student);


        List<Student> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(student);
        }
        list.setAdaper(datas, ListStyle.BASIC, R.layout.item_list);

        new FieldType<Student>();
    }
}
