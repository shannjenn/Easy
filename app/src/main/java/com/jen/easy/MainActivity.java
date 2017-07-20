package com.jen.easy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jen.easy.demo.Student;
import com.jen.easy.log.Logcat;
import com.jen.easy.sqlite.DBHelper;
import com.jen.easy.sqlite.EasyDBDao;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        DBHelper dbHelper = DBHelper.getInstance();
//        dbHelper.createTB(Student.class);
        dbHelper.rebuildTB(Student.class);
        Student student = new Student();
        student.setId("100");
        student.setName("jen");
        student.setYes(true);
        student.setAge(30);
        long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        Date d1=new Date(time);
        student.setDate(d1);
        EasyDBDao.insert(student);
        Object object = EasyDBDao.searchById(Student.class, "100");

        Logcat.d("");
    }

}
