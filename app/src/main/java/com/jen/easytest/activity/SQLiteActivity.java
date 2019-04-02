package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easy.sqlite.EasyTBDao;
import com.jen.easytest.R;
import com.jen.easytest.sqlite.Student;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.util.EasyToast;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class SQLiteActivity extends EasyActivity {
    private EasyTBDao dbDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
    }


    @Override
    protected void initViews() {
//        easyButon.setBackgroundColor(0xffff0000);
    }


    @EasyBindClick({R.id.replace, R.id.search})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.replace: {
                dbDao = new EasyTBDao(SQLiteActivity.this);
                Student student = new Student();
                student.setId(1);
                student.setName("张三");
                student.setAge(30);

                boolean insertResult = dbDao.insert(student);
                boolean replaceResult = dbDao.replace(student);
//                List<Student> list = EasyMain.mDao.searchAll(Student.class);

                EasyToast.show("结果：" + replaceResult);
                break;
            }
            case R.id.search: {
                List<Student> list = dbDao.searchAll(Student.class);
                EasyToast.show("结果：" + list.size());
                break;
            }
            default: {

                break;
            }
        }
    }

}
