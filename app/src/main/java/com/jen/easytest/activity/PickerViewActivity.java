package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindId;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.view.loopview.EasyLoopView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class PickerViewActivity extends EasyActivity {

    @EasyBindId(R.id.pick_1)
    EasyLoopView pick_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_view);
    }

    @Override
    protected void initViews() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            data.add(i < 10 ? "0" + i : "" + i);
        }
//        pick_1.setPaintTextSizeDivide(140,140,2.8f);
//        pick_1.setData(data);
//        pick_1.setSelected(0);

        pick_1.setData((ArrayList) data);
        pick_1.setInitPosition(0);
    }

    @Override
    protected void onBindClick(View view) {

    }


}
