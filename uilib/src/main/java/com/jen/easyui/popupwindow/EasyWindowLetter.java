package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easy.log.EasyLog;
import com.jen.easyui.R;
import com.jen.easyui.popupwindow.listener.WindowItemListener;
import com.jen.easyui.popupwindow.listener.WindowOkListener;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyHolderRecyclerWaterfallAdapter;
import com.jen.easyui.recycler.letter.EasyLetterDecoration;
import com.jen.easyui.recycler.letter.EasyLetterItem;
import com.jen.easyui.recycler.letter.EasyLetterView;
import com.jen.easyui.recycler.listener.EasyItemListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：Object类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowLetter extends EasyWindow implements EasyItemListener {
    private WindowBind windowBind;
    private RecyclerView recycler;
    private EasyLetterView lt_letter;
    private MyAdapter<Object> adapter;
    private List<Object> data;
    private EasyLetterDecoration letterDecoration;

    EasyWindowLetter(Build build, WindowBind windowBind, EasyLetterDecoration letterDecoration) {
        super(build);
        this.windowBind = windowBind;
        this.letterDecoration = letterDecoration;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setData(List data) {
        if (data == null || data.size() == 0) {
            return;
        } else if (!(data.get(0) instanceof EasyLetterItem)) {
            EasyLog.e("setData错误,请设置EasyLetterItem集合");
            return;
        }
        this.data.clear();
        this.data.addAll(data);
        letterDecoration.setData(this.data);
        recycler.removeItemDecoration(letterDecoration);
        recycler.addItemDecoration(letterDecoration);
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置字母
     *
     * @param letters .
     */
    public void setLetters(String[] letters) {
        lt_letter.setLetters(letters);
    }

    /**
     * 字母控件
     */
    public EasyLetterView getLetterView() {
        return lt_letter;
    }

    @Override
    View bindContentView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_letter, null);
        lt_letter = popView.findViewById(R.id.lt_letter);
        data = new ArrayList<>();
        data.add("");//默认有一个
        adapter = new MyAdapter<>(build.context, data);
        adapter.setItemListener(this);
        recycler = popView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(build.context));
        recycler.setAdapter(adapter);
        lt_letter.setTouchListener(new EasyLetterView.TouchListener() {
            @Override
            public void onTouch(String letter) {
                for (int i = 0; i < data.size(); i++) {
                    Object object = data.get(i);
                    if (object instanceof EasyLetterItem) {
                        EasyLetterItem letterItem = (EasyLetterItem) object;
                        if (letterItem.getLetter().equals(letter)) {
                            EasyLog.d("touch = " + letter);
                            recycler.scrollToPosition(i);
                            LinearLayoutManager mLayoutManager = (LinearLayoutManager) recycler.getLayoutManager();
                            mLayoutManager.scrollToPositionWithOffset(i, 0);
                            break;
                        }
                    }
                }
            }
        });
        return popView;
    }

    @Override
    public void onItemClick(View view, int position) {
        selectPosition = position;
        WindowItemListener itemListener;
        if (!(build.listener instanceof WindowItemListener)) {
            return;
        }
        itemListener = (WindowItemListener) build.listener;
        itemListener.itemClick(build.flagCode, showView, selectPosition, data.get(selectPosition));
    }

    @Override
    void clickLeftCallBack() {
        if (build.listener instanceof WindowOkListener && data.size() > 0) {
            ((WindowOkListener) build.listener).windowLeft(build.flagCode, showView, selectPosition, data.get(selectPosition));
        }
    }

    @Override
    void clickRightCallBack() {
        if (build.listener instanceof WindowOkListener && data.size() > 0) {
            ((WindowOkListener) build.listener).windowRight(build.flagCode, showView, selectPosition, data.get(selectPosition));
        }
    }

    class MyAdapter<E> extends EasyHolderRecyclerWaterfallAdapter<E> {
        /**
         * @param context .
         * @param data    数据
         */
        MyAdapter(Context context, List<E> data) {
            super(context, data);
        }

        @Override
        protected int[] onBindLayout() {
            return windowBind.onBindItemLayout();
        }

        @Override
        protected int getViewType(int position) {
            return windowBind.onBindViewType();
        }

        @Override
        protected void onBindHolderData(EasyHolder easyHolder, View view, int viewType, int position) {
            windowBind.onBindItemData(easyHolder, view, mData, position);
        }
    }

}
