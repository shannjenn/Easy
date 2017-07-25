package com.jen.easy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jen.easy.demo.School;
import com.jen.easy.demo.Student;
import com.jen.easy.http.EasyHttp;
import com.jen.easy.http.HttpParse;
import com.jen.easy.log.Logcat;
import com.jen.easy.sqlite.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

        json();

        EasyHttp.getInstance().start(student);

        Logcat.d("");

        String url = BuildConfig.BASE_URL;
    }

    private void json() {
        try {
            School school = new School();
            school.setId("1000");
            school.setName("华工");
            Student student = new Student();
            student.setId("100");
            student.setName("jen");
            student.setYes(true);
            student.setAge(30);
            school.setStudent(student);
            List<Student> list = new ArrayList<>();
            school.setStudents(list);
            for (int i = 10; i < 15; i++) {
                Student student1 = new Student();
                student1.setId(i + "");
                student1.setName("jen");
                student1.setYes(true);
                student1.setAge(30);
                list.add(student1);
            }


            JSONObject jsonObject = new JSONObject();

            JSONObject jsonObject0 = new JSONObject();
            jsonObject0.put("id", "1000");
            jsonObject0.put("name", "华工");
            jsonObject.put("school", jsonObject0);

            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("id", "100");
            jsonObject1.put("name", "jen");
            jsonObject1.put("yes", true);
            jsonObject1.put("age", 30);
            jsonObject0.put("student", jsonObject1);

            JSONArray array = new JSONArray();
            for (int i = 10; i < 15; i++) {
                JSONObject object = new JSONObject();
                object.put("id", i + "");
                object.put("name", "jen");
                object.put("yes", true);
                object.put("age", 30);
                array.put(object);
            }
            jsonObject0.put("students", array);

            Logcat.d("jsonObject=" + jsonObject.toString());
            Object object = HttpParse.parseJson(School.class, jsonObject);
            Logcat.d("object=" + object.toString());


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
