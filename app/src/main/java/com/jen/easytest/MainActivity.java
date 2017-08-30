package com.jen.easytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.RelativeLayout;

import com.jen.easy.EasyMain;
import com.jen.easy.EasyMouse;
import com.jen.easy.log.Logcat;
import com.jen.easytest.demo.Student;
import com.jen.easyui.listview.ItemLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @ItemLayout(text = R.id.tv_item)
    @EasyMouse.BIND.ID(R.id.listview_footer_content1)
    RelativeLayout listview_footer_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_footer);
        EasyMain.BIND.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Object[] list = new Object[3];
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            list[i] = student;
        }

        Object obj = list;

        Object[] list2 = (Object[]) obj;
        Logcat.d(list2.length + "");
        Logcat.d(list2.length + "");

        Student student = new Student();

        Field[] fields = student.getClass().getDeclaredFields();
        String a = fields[0].getGenericType().toString();
        String b = fields[1].getGenericType().toString();
        String c = fields[2].getGenericType().toString();
        String d = fields[3].getGenericType().toString();
        String e = fields[4].getGenericType().toString();

        List<Student> list1 = new ArrayList<>();

        Logcat.d(TextUtils.isEmpty(" ") + "");
        Logcat.d(TextUtils.isEmpty(" ") + "");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyMain.BIND.unbind(this);
    }
}
