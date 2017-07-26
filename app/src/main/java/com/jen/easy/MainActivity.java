package com.jen.easy;

import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
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
        downloadTest();
        doHttpTest();
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

    private void downloadTest(){
        Student student = new Student();
        student.httpBase.url = "http://mdm.zte.com.cn/PositionEnglishTest/AppUploadFolder/Audio/609e507c-05cd-4a9a-a52f-454209aa58f3_1.zip";
        student.fileParam.filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + "_Easy" + File.separator + "abc.zip";
        EasyHttp.getInstance().start(student);
    }

    private void doHttpTest(){
        String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=%E6%88%91%E4%BB%AC&rsv_pq=d8f04bcf0001c5af&rsv_t=859bmrPQY0BJ%2FPDl6W8qPBW4qLToGicBk9fsbqb5aLWtb4%2FobFUYZNN6Y5o&rqlang=cn&rsv_enter=1&rsv_sug3=6&rsv_sug1=9&rsv_sug7=100&rsv_sug2=0&inputT=3945&rsv_sug4=4966";
        School school = new School();
        school.httpBase.url = url;
        EasyHttp.getInstance().start(school);
    }

}
