package com.jen.easyui.popupwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyLog;
import com.jen.easyui.R;
import com.jen.easyui.popupwindow.listener.WindowCancelSureListener;
import com.jen.easyui.popupwindow.listener.WindowItemListener;
import com.jen.easyui.popupwindow.listener.WindowLeftRightListener;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.EasyHolderRecyclerBaseAdapter;
import com.jen.easyui.recycler.listener.EasyItemListener;
import com.jen.easyui.util.EasyDensityUtil;
import com.jen.easyui.util.EasyDisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：String类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

class EasyWindowString extends EasyWindow implements EasyItemListener {
    private MyAdapter<String> adapter;
    private List<String> data;
    private RecyclerView recycler;

    EasyWindowString(Build build) {
        super(build);
    }

    @Override
    View bindContentView() {
        View popView = LayoutInflater.from(build.context).inflate(R.layout._easy_popup_window_string, null);
        data = new ArrayList<>();
        data.add("");//默认有一个
        adapter = new MyAdapter<>(build.context, data);
        adapter.setItemListener(this);
        recycler = popView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(build.context));
        recycler.setAdapter(adapter);
        updateHeight();
        return popView;
    }

    private void updateHeight() {
        if (build.height != 0 && build.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            return;
        }
        ViewGroup.LayoutParams lp = recycler.getLayoutParams();
        int height = EasyDensityUtil.dp2pxInt(50 * data.size());
        int maxHeight = EasyDisplayUtil.getScreenHeight(build.context);
        int titleHeight;
        if (build.showTopBar) {
            titleHeight = EasyDensityUtil.dp2pxInt(46 + 10);//46是标题高度
        } else {
            titleHeight = EasyDensityUtil.dp2pxInt(10);//46是标题高度
        }
        if (height >= maxHeight - titleHeight) {
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            lp.height = height;
        }
//        recycler.setLayoutParams(lp);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setData(List data) {
        if (data == null || data.size() == 0) {
            return;
        } else if (!(data.get(0) instanceof String)) {
            EasyLog.e("setData错误,请设置String集合");
            return;
        }
        this.data.clear();
        this.data.addAll(data);
        updateHeight();
        adapter.notifyDataSetChanged();
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
        if (build.listener instanceof WindowLeftRightListener) {
            ((WindowLeftRightListener) build.listener).windowLeft(build.flagCode, showView, selectPosition);
        } else if (build.listener instanceof WindowCancelSureListener) {
            ((WindowCancelSureListener) build.listener).windowCancel(build.flagCode, showView);
        }
    }

    @Override
    void clickRightCallBack() {
        if (build.listener instanceof WindowLeftRightListener) {
            ((WindowLeftRightListener) build.listener).windowRight(build.flagCode, showView, selectPosition);
        } else if (build.listener instanceof WindowCancelSureListener) {
            if (selectPosition >= 0 && selectPosition < data.size()) {
                ((WindowCancelSureListener) build.listener).windowSure(build.flagCode, showView, selectPosition, data.get(selectPosition));
            }
        }
    }

    class MyAdapter<T> extends EasyHolderRecyclerBaseAdapter<T> {
        /**
         * @param context .
         * @param data    数据
         */
        MyAdapter(Context context, List<T> data) {
            super(context, data);
        }

        @Override
        protected int onBindLayout() {
            return R.layout._easy_popup_window_string_item;
        }

        @Override
        protected void onBindHolderData(EasyHolder easyHolder, View view, int viewType, int position) {
            Object object = mData.get(position);
            if (object instanceof String) {
                easyHolder.setTextView(R.id.shape_name, (String) object);
            }
        }
    }

}
