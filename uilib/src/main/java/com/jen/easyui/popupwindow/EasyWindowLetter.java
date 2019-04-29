package com.jen.easyui.popupwindow;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jen.easy.log.EasyLog;
import com.jen.easyui.R;
import com.jen.easyui.recycler.EasyRecyclerAdapterFactory;
import com.jen.easyui.recycler.letter.EasyLetterDecoration;
import com.jen.easyui.recycler.letter.EasyLetterItem;
import com.jen.easyui.recycler.letter.EasyLetterView;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：Object类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowLetter extends EasyWindow  {
    private RecyclerView recycler;
    private EasyLetterView lt_letter;
    private List<Object> data;
    private EasyLetterDecoration letterDecoration;

    EasyWindowLetter(Build build, EasyRecyclerAdapterFactory adapter, EasyLetterDecoration letterDecoration) {
        super(build,adapter);
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

}
