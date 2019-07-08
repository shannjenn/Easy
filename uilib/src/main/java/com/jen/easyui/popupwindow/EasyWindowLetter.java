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

public class EasyWindowLetter extends EasyWindow implements EasyLetterView.TouchListener {
    private RecyclerView recyclerView;
    private EasyAdapterFactory adapter;
    private EasyLetterView lt_letter;
    private EasyLetterDecoration letterDecoration;

    EasyWindowLetter(Build build, EasyAdapterFactory adapter, EasyLetterDecoration letterDecoration) {
        super(build);
        this.adapter = adapter;
        this.letterDecoration = letterDecoration;
        initView();
    }

    @Override
    View bindView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_letter, null);
        lt_letter = popView.findViewById(R.id.lt_letter);
        recyclerView = popView.findViewById(R.id.recycler);
        return popView;
    }

    @SuppressWarnings("unchecked")
    private void initView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(build.context));
        lt_letter.setTouchListener(this);
        recyclerView.setAdapter(adapter);
        if (adapter.getData() != null && adapter.getData().size() > 0 && !(adapter.getData().get(0) instanceof EasyLetterItem)) {
            letterDecoration.setData(adapter.getData());
            recyclerView.removeItemDecoration(letterDecoration);
            recyclerView.addItemDecoration(letterDecoration);
        }
    }

    /**
     * 数据改变时需要调该方法刷新Letter
     * @param data .
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setData(List data) {
        if (data == null || data.size() == 0) {
            return;
        } else if (!(data.get(0) instanceof EasyLetterItem)) {
            EasyLog.e("setData错误,请设置EasyLetterItem集合");
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
            Object object = adapter.getData().get(i);
            if (object instanceof EasyLetterItem) {
                EasyLetterItem letterItem = (EasyLetterItem) object;
                if (letterItem.getLetter().equals(letter)) {
                    EasyLog.d("touch = " + letter);
                    recyclerView.scrollToPosition(i);
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(i, 0);
                    break;
                }
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
}
