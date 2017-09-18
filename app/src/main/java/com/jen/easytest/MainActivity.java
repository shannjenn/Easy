package com.jen.easytest;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jen.easy.EasyMain;
import com.jen.easy.EasyMouse;
import com.jen.easy.EasyUtil;
import com.jen.easy.log.Logcat;
import com.jen.easytest.demo.Student;
import com.jen.easyui.listview.EasyListView;
import com.jen.easyui.listview.ItemSource;
import com.jen.easyui.dialog.EasyLoading;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @ItemSource(text = R.id.tv_item)
    TextView tv_View;

    @EasyMouse.BIND.ID(R.id.list)
    EasyListView listView;

    @EasyMouse.BIND.ID(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @EasyMouse.BIND.ID(R.id.pb_bar)
    ProgressBar pb_bar;

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
            String cl = list.getClass().toString();
            String cl2 = student.getClass().toString();
//            String cl3 = List<Student>

            List<Student> unmodifiedList = Collections.unmodifiableList(list);
            list.add(student);

            try {
                Method m = MainActivity.class.getMethod("test01", Map.class, List.class);
                Type[] t = m.getGenericParameterTypes();//获取参数泛型
                for (Type paramType : t) {
                    System.out.println("#" + paramType);
                    if (paramType instanceof ParameterizedType) {
                        Type[] genericTypes = ((ParameterizedType) paramType).getActualTypeArguments();
                        for (Type genericType : genericTypes) {
                            System.out.println("泛型类型" + genericType);
                            System.out.println("泛型类型" + genericType);
                        }
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
        listView.setAdaper(list, R.layout.item);

        EasyUtil.dateFormat.getTime("2017-09-06 21:50:43");
        EasyUtil.dateFormat.getTime("0");

        Student student = new Student();

        EasyLoading loading = new EasyLoading(this);
        loading.show();

        Logcat.d("a=");
    }

    public static void test01(Map<String, Student> map, List<Student> list) {
        System.out.println("Generic.test01()");
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
