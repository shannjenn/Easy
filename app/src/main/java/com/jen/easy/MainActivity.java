package com.jen.easy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jen.easy.demo.School;
import com.jen.easy.demo.Student;
import com.jen.easy.http.HttpReflectMan;
import com.jen.easy.log.Logcat;

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
        /*DBHelper dbHelper = DBHelper.getInstance();
//        dbHelper.createTB(Student.class);
        dbHelper.rebuildTB(Student.class);

        Student student = new Student();
        student.setId("100");
        student.setName("jen");
        student.setYes(true);
        student.setAge(30);
        long time = System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        Date d1 = new Date(time);
        student.setDate(d1);
        EasyDBDao.replace(student);

        Student student1 = (Student) searchById(Student.class, "100");
        json(student);

        Logcat.d("");*/

        HttpReflectMan.parseHttpResult(School.class,"");
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
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("student", array);

            String jsonStr = jsonObject.toString();
            JSONObject jsonObject1 = new JSONObject(jsonStr);
            JSONArray array1 = (JSONArray) jsonObject1.get("student");
            int id = array1.getJSONObject(0).getInt("id");
            Logcat.d("id=" + id);

        } catch (JSONException e) {
            e.printStackTrace();
            Logcat.e("error-------");
        }
    }


}
