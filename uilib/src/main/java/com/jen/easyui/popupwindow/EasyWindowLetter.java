package com.jen.easyui.popupwindow;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easy.log.EasyLog;
import com.jen.easyui.R;
import com.jen.easyui.recycler.EasyAdapterFactory;
import com.jen.easyui.recycler.letter.EasyLetterDecoration;
import com.jen.easyui.recycler.letter.EasyLetterItem;
import com.jen.easyui.recycler.letter.EasyLetterView;

import java.util.List;

/**
 * 说明：Object类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowLetter<T> extends EasyWindow<T> implements EasyLetterView.TouchListener {
    private RecyclerView recyclerView;
    private EasyAdapterFactory<EasyLetterItem> adapter;
    private EasyLetterView lt_letter;
    private EasyLetterDecoration<EasyLetterItem> letterDecoration;

    EasyWindowLetter(Build<T> build, EasyAdapterFactory<EasyLetterItem> adapter) {
        super(build);
        this.adapter = adapter;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(build.context));
        lt_letter.setTouchListener(this);
        recyclerView.setAdapter(adapter);
        if (letterDecoration == null) {
            letterDecoration = new EasyLetterDecoration<>();
        }

        if (adapter.getData() != null && adapter.getData().size() > 0) {
            letterDecoration.setData(adapter.getData());
            recyclerView.removeItemDecoration(letterDecoration);
            recyclerView.addItemDecoration(letterDecoration);
        }
    }

    /**
     * 数据改变时需要调该方法刷新Letter
     *
     * @param data .
     */
    public void setData(List<EasyLetterItem> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        recyclerView.removeItemDecoration(letterDecoration);
        letterDecoration.setData(data);
        recyclerView.addItemDecoration(letterDecoration);
        adapter.setData(data);
        adapter.notifyDataSetChanged();
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

    public EasyAdapterFactory getAdapter() {
        return adapter;
    }

    /**
     * 字母控件
     */
    public EasyLetterView getLetterView() {
        return lt_letter;
    }

    public EasyLetterDecoration<EasyLetterItem> getLetterDecoration() {
        return letterDecoration;
    }
}
