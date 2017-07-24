package com.jen.easy;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jen.easy.demo.Student;
import com.jen.easy.http.HttpReflectMan;
import com.jen.easy.log.Logcat;
import com.jen.easy.sqlite.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        json(student);

        Logcat.d("");

//        HttpReflectMan.parseJsonObject(School.class,"");
    }

    private void json(Student student) {
        try {
            JSONArray array = new JSONArray();
            for (int i = 0; i < 3; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", i);
                jsonObject.put("name", student.getName());
                jsonObject.put("yes", student.isYes());
                jsonObject.put("age", student.getAge());
                array.put(jsonObject);
//                HttpReflectMan.parseJsonObject(Student.class,jsonObject);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("student", array);
            Logcat.d(jsonObject.toString());

            /*String jsonStr = jsonObject.toString();
            JSONObject jsonObject1 = new JSONObject(jsonStr);
            JSONArray array1 = (JSONArray) jsonObject1.get("student");
            int id = array1.getJSONObject(0).getInt("id");
            Logcat.d("id=" + id);*/

        } catch (JSONException e) {
            e.printStackTrace();
            Logcat.e("error-------");
        }
    }


}
