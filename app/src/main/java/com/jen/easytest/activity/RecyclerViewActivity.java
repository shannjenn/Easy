package com.jen.easytest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jen.easy.Easy;
import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;
import com.jen.easytest.model.RecyclerViewModel;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.recyclerview.EasyAdapterOnClickListener;
import com.jen.easyui.recyclerview.EasyHolder;
import com.jen.easyui.recyclerview.EasyItemType;
import com.jen.easyui.recyclerview.EasyLetterDecoration;
import com.jen.easyui.recyclerview.EasyLetterView;
import com.jen.easyui.recyclerview.EasyLetterViewManager;
import com.jen.easyui.recyclerview.EasyRecyclerAdapter;
import com.jen.easyui.recyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/12/25.
 */

public class RecyclerViewActivity extends EasyActivity {

    @Easy.BIND.ID(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    @Easy.BIND.ID(R.id.lt_letter)
    EasyLetterView lt_letter;

    @Easy.BIND.ID(R.id.tv_letter_show)
    TextView tv_letter_show;

    List<RecyclerViewModel> mData = new ArrayList<>();

    EasyAdapter1<RecyclerViewModel> easyAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
    }

    @Override
    protected void intDataBeforeView() {
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
        recyclerView.setLinearLayoutManager(RecyclerView.VERTICAL);
        easyAdapter1.setItemTouchSortEvent(recyclerView);
        recyclerView.setAdapter(easyAdapter1);

        recyclerView.showHeader(true);
        recyclerView.showFooter(true);
        recyclerView.setRefreshListener(refreshListener);
        recyclerView.setLoadMoreListener(loadMoreListener);
        EasyLetterDecoration itemDecoration = new EasyLetterDecoration(mData);
        itemDecoration.setLetterTextColor(0xffff0000);
        recyclerView.addItemDecoration(itemDecoration);


        lt_letter.setTouchListener(new EasyLetterViewManager.TouchListener() {
            @Override
            public void onTouch(String letter) {
//                int pos = -1;
                for (int i = 1; i < mData.size(); i++) {
                    if (mData.get(i).getLetter().equals(letter)) {
//                        pos = i;
                        EasyLog.d("touch = " + letter);
                        recyclerView.scrollPositionToHeader(i);
                        break;
                    }
                }
            }
        });
        easyAdapter1.setEasyAdapterOnClickListener(new EasyAdapterOnClickListener() {
            @Override
            public void onClick(View view, int pos) {

            }

            @Override
            public boolean onLongClick(View view, int pos) {
                return false;
            }

        });
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadDataAfterView() {

    }

    @Easy.BIND.Method({R.id.EasyRecyclerAdapter, R.id.EasyRecyclerWaterfallAdapter, R.id.EasyTreeRecyclerAdapter})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.EasyRecyclerAdapter: {
                recyclerView.setAdapter(easyAdapter1);
                break;
            }
            case R.id.EasyRecyclerWaterfallAdapter: {

                break;
            }
            case R.id.EasyTreeRecyclerAdapter: {

                break;
            }
            default: {

                break;
            }
        }
    }

    EasyRecyclerView.RefreshListener refreshListener = new EasyRecyclerView.RefreshListener() {
        @Override
        public void onRefresh() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.refreshFinish();
                }
            }, 2000);
        }
    };

    EasyRecyclerView.LoadMoreListener loadMoreListener = new EasyRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.loadMoreFinish();
                }
            }, 2000);
        }
    };

    private class EasyAdapter1<T extends RecyclerViewModel> extends EasyRecyclerAdapter<T> {

        /**
         * @param context
         * @param data    数据
         */
        protected EasyAdapter1(Context context, List<T> data) {
            super(context, data);
        }

        @Override
        protected EasyHolder bindHolder(View view, EasyItemType viwType) {
            return new MyHolder(view, viwType);
        }

        @Override
        protected int onBindLayout() {
            return R.layout.item_recycler_view;
        }

        class MyHolder extends EasyHolder {

            /*public MyHolder(View itemView) {
                super(itemView);
            }*/
            public MyHolder(View itemView, EasyItemType viewType) {
                super(itemView, viewType);
            }

            @Override
            protected EasyAdapterOnClickListener bindEasyAdapterOnClickListener() {
                return null;
            }

            @Override
            protected void onBindData(View view, int viewType, int position) {
                TextView tv_text = view.findViewById(R.id.tv_text);
                tv_text.setText(mData.get(position).getLetter());
            }

        }
    }
}
