package com.jen.easytest;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jen.easy.EasyMain;
import com.jen.easy.EasyMouse;
import com.jen.easy.EasyUtil;
import com.jen.easy.log.Logcat;
import com.jen.easytest.demo.Student;
import com.jen.easyui.listview.EasyListView;
import com.jen.easyui.listview.ItemSource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @ItemSource(text = R.id.tv_item)
    TextView tv_View;

    @EasyMouse.BIND.ID(R.id.list)
    EasyListView listView;

    @EasyMouse.BIND.ID(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EasyMain.BIND.bind(this);

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.id = i + "";
            student.name = "测试";
            list.add(student);
        }
        listView.setAdaper(list,R.layout.item);

        EasyUtil.dateFormat.getTime("2017-09-06 21:50:43");
        EasyUtil.dateFormat.getTime("0");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyMain.BIND.unbind(this);
    }

    @Override
    public void onRefresh() {
        Logcat.d(" onRefresh --- ");
    }
}
