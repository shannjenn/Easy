package com.jen.easytest.activity.recyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyViewMethod;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;


/**
 * Created by Administrator on 2017/12/25.
 */

public class RecyclerViewMainActivity extends EasyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_main);
    }

    @Override
    protected void intDataBeforeView() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadDataAfterView() {

    }


    @EasyViewMethod({R.id.recycleHScroll, R.id.recycleViewLetter})
    @Override
    protected void onBindClick(View view) {
        Class clazz = null;
        switch (view.getId()) {
            case R.id.recycleHScroll: {
                clazz = RecyclerHScrollActivity.class;
                break;
            }
            case R.id.recycleViewLetter: {
                clazz = RecyclerViewLetterActivity.class;
                break;
            }
            default: {

                break;
            }
        }
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
