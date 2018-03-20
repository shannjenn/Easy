package com.jen.easytest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jen.easy.EasyMouse;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.recyclerview.EasyLetterView;
import com.jen.easyui.recyclerview.EasyRecyclerAdapter;
import com.jen.easyui.recyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/12/25.
 */

public class RecyclerViewActivity extends EasyActivity {

    @EasyMouse.BIND.ID(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    @EasyMouse.BIND.ID(R.id.lt_letter)
    EasyLetterView lt_letter;

    @EasyMouse.BIND.ID(R.id.tv_letter_show)
    TextView tv_letter_show;

    List<String> mData = new ArrayList<>();

    EasyAdapter1<String> easyAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
    }

    @Override
    protected void intDataBeforeView() {
        mData.clear();
        for (int i = 0; i < 100; i++) {
            mData.add("第" + i + "个数据 --- ");
        }
        easyAdapter1 = new EasyAdapter1<>(this, mData);

        recyclerView.setLinearLayoutManager(RecyclerView.VERTICAL);
        recyclerView.setShowHeader(true);
        recyclerView.setShowFoot(true);
        recyclerView.setRefreshListener(refreshListener);
        recyclerView.setLoadMoreListener(loadMoreListener);

        recyclerView.setAdapter(easyAdapter1);
    }

    @Override
    protected void initViews() {
//        lt_letter.setTextViewDialog(tv_letter_show);
    }

    @Override
    protected void loadDataAfterView() {

    }

    @EasyMouse.BIND.Method({R.id.EasyRecyclerAdapter, R.id.EasyRecyclerWaterfallAdapter, R.id.EasyTreeRecyclerAdapter})
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

    private class EasyAdapter1<T extends String> extends EasyRecyclerAdapter<T> {

        /**
         * @param context
         * @param data    数据
         */
        protected EasyAdapter1(Context context, List<T> data) {
            super(context, data);
        }

        @Override
        protected int onBindLayout() {
            return R.layout.item_recycler_view;
        }

        @Override
        protected void onBindView(View view, int viewType, T data, int pos) {
            super.onBindView(view, viewType, data, pos);
            TextView tv_text = view.findViewById(R.id.tv_text);
            tv_text.setText(data);
        }
    }
}
