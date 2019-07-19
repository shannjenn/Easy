package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easy.log.EasyLog;
import com.jen.easyui.R;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyHolderWaterfallAdapter;
import com.jen.easyui.recycler.LayoutType;
import com.jen.easyui.recycler.letter.EasyLetterDecoration;
import com.jen.easyui.recycler.letter.EasyLetterItem;
import com.jen.easyui.recycler.letter.EasyLetterView;

import java.util.List;

/**
 * 说明：Object类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowLetter<T extends EasyLetterItem> extends EasyWindow<T> implements EasyLetterView.TouchListener {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private EasyWindowAdapter<T> adapterImp;
    private EasyLetterView lt_letter;
    private EasyLetterDecoration<T> letterDecoration = new EasyLetterDecoration<>();

    EasyWindowLetter(BuildLetter<T> build, EasyWindowAdapter<T> adapterImp) {
        super(build);
        this.build = build;
        this.adapterImp = adapterImp;
        initView();
    }

    @Override
    View bindView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_letter, null);
        lt_letter = popView.findViewById(R.id.lt_letter);
        recyclerView = popView.findViewById(R.id.recycler);
        return popView;
    }

    private void initView() {
        if (adapterImp.bindRecycleLayout() == null) {
            adapter = new MyAdapter(build.context, recyclerView);
        } else {
            adapter = new MyAdapter(build.context);
            recyclerView.setAdapter(adapter);
            LayoutType layoutType = adapterImp.bindRecycleLayout().getKey();
            int size = adapterImp.bindRecycleLayout().getValue();
            recyclerView.setLayoutManager(LayoutType.getLayout(build.context, layoutType, size));
        }
        recyclerView.removeItemDecoration(letterDecoration);
        letterDecoration.setData(adapter.getData());
        recyclerView.addItemDecoration(letterDecoration);

        lt_letter.setTouchListener(this);
        adapter.setItemListener(adapterImp.itemListener());
        adapter.setDataAndNotify(build.data);
    }

    /**
     * 数据改变时需要调该方法刷新Letter
     *
     * @param data .
     */
    public void setData(List<T> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        recyclerView.removeItemDecoration(letterDecoration);
        letterDecoration.setData(data);
        recyclerView.addItemDecoration(letterDecoration);
        adapter.setDataAndNotify(data);
    }

    @Override
    public void onTouch(String letter) {
        for (int i = 0; i < adapter.getData().size(); i++) {
            EasyLetterItem letterItem = adapter.getData().get(i);
            if (letterItem.getLetter().equals(letter)) {
                EasyLog.d("touch = " + letter);
                recyclerView.scrollToPosition(i);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                mLayoutManager.scrollToPositionWithOffset(i, 0);
                break;
            }
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public MyAdapter getAdapter() {
        return adapter;
    }

    /**
     * 字母控件
     */
    public EasyLetterView getLetterView() {
        return lt_letter;
    }

    public EasyLetterDecoration<T> getLetterDecoration() {
        return letterDecoration;
    }

    public class MyAdapter extends EasyHolderWaterfallAdapter<T> {
        MyAdapter(Context context) {
            super(context);
        }

        MyAdapter(Context context, RecyclerView recyclerView) {
            super(context, recyclerView);
        }

        @Override
        protected int[] onBindLayout() {
            return adapterImp.onBindLayout();
        }

        @Override
        protected int getViewType(int position) {
            return adapterImp.getViewType(mData.get(position), position);
        }

        @Override
        protected void onBindHolderData(EasyHolder easyHolder, View view, int viewType, int position) {
            adapterImp.onBindHolderData(easyHolder, view, viewType, mData.get(position), position);
        }

        @Override
        public RecyclerView.ItemDecoration onDecoration() {
            return letterDecoration;
        }
    }
}
