package com.jen.easytest.activity.recyclerView;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jen.easy.EasyBindId;
import com.jen.easytest.R;
import com.jen.easytest.adapter.decoration.MyItemDecoration;
import com.jen.easytest.model.RecyclerViewModel;
import com.jen.easyui.recycler.EasyBaseAdapter;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.letter.EasyLetterDecoration;
import com.jen.easyui.recycler.listener.EasyItemListener;

import java.util.ArrayList;
import java.util.List;

import easybase.EasyActivity;


/**
 * Created by Administrator on 2017/12/25.
 */

public class RecyclerSlideDeleteActivity extends EasyActivity {
    @EasyBindId(R.id.recycle)
    RecyclerView recycle;

    List<RecyclerViewModel> mData = new ArrayList<>();
    EasyAdapter1<RecyclerViewModel> easyAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_slide);
    }

    @Override
    public int bindView() {
        return 0;
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


        easyAdapter1 = new EasyAdapter1<>(this, mData);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        easyAdapter1.setItemTouchSortEvent(recycle);
        recycle.setAdapter(easyAdapter1);

        EasyLetterDecoration itemDecoration = new EasyLetterDecoration(mData);
        itemDecoration.setLetterTextColor(0xffff0000);
        recycle.addItemDecoration(itemDecoration);

        easyAdapter1.setItemListener(new EasyItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, position + "", Toast.LENGTH_LONG).show();
//                EasyToast.toast(position + "");
            }

            @Override
            public void onViewClick(View view, int position) {

            }

            @Override
            public boolean onViewLongClick(View view, int position) {
                return false;
            }
        });
    }


    @Override
    protected void onBindClick(View view) {

    }

    private class EasyAdapter1<T extends RecyclerViewModel> extends EasyBaseAdapter<T> {

        public EasyAdapter1(Context context) {
            super(context);
        }

        public EasyAdapter1(Context context, List<T> list) {
            super(context);
        }

        public EasyAdapter1(Context context, RecyclerView recyclerView) {
            super(context, recyclerView);
        }

        @Override
        public RecyclerView.ItemDecoration onDecoration() {
            return MyItemDecoration.newInstance(mContext);
        }

        @Override
        protected int gridLayoutItemRows(int position) {
            return 0;
        }

        @Override
        protected EasyHolder bindHolder(View view) {
            return new MyHolder(this, view);
        }

        @Override
        protected int onBindLayout() {
            return R.layout.item_recycler_view_slide;
        }

        class MyHolder extends EasyHolder {

            public MyHolder(EasyBaseAdapter adapter, View itemView) {
                super(adapter, itemView);
            }

            @Override
            protected void onBindData(View view, int viewType, int position) {
                TextView tv_text = view.findViewById(R.id.content);
                tv_text.setText(mData.get(position).getLetter());
            }

        }
    }
}
