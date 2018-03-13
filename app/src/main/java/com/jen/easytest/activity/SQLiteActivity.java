package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyMouse;
import com.jen.easytest.R;
import com.jen.easytest.sqlite.Student;
import com.jen.easyui.EasyMain;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.base.EasyToast;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class SQLiteActivity extends EasyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
    }

    @Override
    protected void intDataBeforeView() {

    }

    @Override
    protected void initViews() {
//        easyButon.setBackgroundColor(0xffff0000);
    }

    @Override
    protected void loadDataAfterView() {

    }

    @EasyMouse.BIND.Method({R.id.replace, R.id.search})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.replace: {
                Student student = new Student();
                student.setId(1);
                student.setName("张三");
                student.setAge(30);
                boolean result = EasyMain.mDao.replace(student);
                EasyToast.toast(this, "结果：" + result);
                break;
            }
            case R.id.search: {
                List<Student> list = EasyMain.mDao.searchAll(Student.class);
                EasyToast.toast(this, "结果：" + list.size());
                break;
            }
            default: {

                break;
            }
        }
    }

}
