package com.jen.easytest.activity.recyclerView;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easy.EasyBindId;
import com.jen.easytest.R;
import com.jen.easytest.adapter.RecyclerViewHScrollAdapter;
import com.jen.easytest.model.RecyclerViewModel;

import easybase.EasyActivity;

import com.jen.easyui.recycler.HScroll.EasyHScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerHScrollActivity extends EasyActivity {

    @EasyBindId(R.id.easyRecyclerScrollView)
    EasyHScrollRecyclerView recyclerView;

    RecyclerViewHScrollAdapter mAdapter;
    List<RecyclerViewModel> mData = new ArrayList<>();

    @Override
    public int bindView() {
        return R.layout.activity_recycler_scroll;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mData.clear();
        for (int i = 0; i < 36; i++) {
            RecyclerViewModel model = new RecyclerViewModel();
            mData.add(model);
        }
        mData.get(0).setLetter("A");
        mData.get(1).setLetter("A");
        mData.get(2).setLetter("B");
        mData.get(3).setLetter("B");
        mData.get(4).setLetter("C");
        mData.get(5).setLetter("C");
        mData.get(6).setLetter("D");
        mData.get(7).setLetter("D");
        mData.get(8).setLetter("E");
        mData.get(9).setLetter("E");
        mData.get(10).setLetter("F");
        mData.get(11).setLetter("F");
        mData.get(12).setLetter("G");
        mData.get(13).setLetter("G");
        mData.get(14).setLetter("H");
        mData.get(15).setLetter("H");
        mData.get(16).setLetter("I");
        mData.get(17).setLetter("I");
        mData.get(18).setLetter("J");
        mData.get(19).setLetter("J");
        mData.get(20).setLetter("K");
        mData.get(21).setLetter("K");
        mData.get(22).setLetter("L");
        mData.get(23).setLetter("L");
        mData.get(24).setLetter("M");
        mData.get(25).setLetter("M");
        mData.get(26).setLetter("N");
        mData.get(27).setLetter("N");
        mData.get(28).setLetter("O");
        mData.get(29).setLetter("O");
        mData.get(30).setLetter("P");
        mData.get(31).setLetter("P");
        mData.get(32).setLetter("Q");
        mData.get(33).setLetter("Q");
        mData.get(34).setLetter("R");

        mAdapter = new RecyclerViewHScrollAdapter<>(this, recyclerView);
    }


    @EasyBindClick({})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.tabBar: {
                break;
            }
            default: {

                break;
            }
        }
    }


}
