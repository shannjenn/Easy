package com.jen.easytest.activity.recyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easytest.R;
import easybase.EasyActivity;


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
    protected void initViews() {

    }



    @EasyBindClick({R.id.recycleHScroll, R.id.recycleViewLetter, R.id.SlideDelete})
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
            case R.id.SlideDelete: {
                clazz = RecyclerSlideDeleteActivity.class;
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
