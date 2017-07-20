package com.jen.easy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jen.easy.demo.Student;
import com.jen.easy.log.Logcat;
import com.jen.easy.sqlite.DBHelper;

import java.lang.reflect.Field;

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

        Logcat.d("onStart----");
        try {
            Student student = new Student();
            Field fs = Student.class.getDeclaredField("id");
            fs.setAccessible(true);
            fs.set(student, "aaaaaaaaaa");
//            fs.setShort(student,);
            Logcat.d("student id =" + student.getId());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Logcat.d("NoSuchFieldException----");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logcat.d("IllegalAccessException----");
        }
    }

}
