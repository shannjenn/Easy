package com.jen.easytest.activity.popupWindow;

import android.view.View;

import com.jen.easytest.R;
import com.jen.easyui.popupwindow.WindowBind;
import com.jen.easyui.recycler.EasyHolder;

import java.util.List;

public class WindowBindEdit implements WindowBind, View.OnClickListener {

    private Listener listener;

    public static WindowBindEdit bind() {
        return new WindowBindEdit();
    }


    @Override
    public int[] onBindItemLayout() {
        return new int[]{R.layout.window_order_edit};
    }

    @Override
    public int onBindViewType() {
        return 0;
    }

    @Override
    public void onBindItemData(EasyHolder easyHolder, View view, List data, int position) {
        view.findViewById(R.id.tv_rename).setOnClickListener(this);
        view.findViewById(R.id.tv_edit).setOnClickListener(this);
        view.findViewById(R.id.tv_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rename: {
                listener.callBack(0);
                break;
            }
            case R.id.tv_edit: {
                listener.callBack(1);
                break;
            }
            case R.id.tv_delete: {
                listener.callBack(2);
                break;
            }
        }
    }

   public interface Listener {
        void callBack(int type);//0,1,2
    }

    public WindowBindEdit setListener(Listener listener) {
        this.listener = listener;
        return this;
    }
}
