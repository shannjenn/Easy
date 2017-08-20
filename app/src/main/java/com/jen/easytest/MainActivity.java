package com.jen.easytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.jen.easy.EasyMain;
import com.jen.easy.EasyMouse;
import com.jen.easy.log.Logcat;
import com.jen.easytest.demo.Student;

public class MainActivity extends AppCompatActivity {

    @EasyMouse.BIND.ID(ID = R.id.listview_footer_content1)
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyMain.BIND.unbind(this);
    }
}
