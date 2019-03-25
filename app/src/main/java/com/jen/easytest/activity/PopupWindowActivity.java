package com.jen.easytest.activity;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easy.EasyBindId;
import com.jen.easytest.R;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.popupwindow.EasyWindow;
import com.jen.easyui.popupwindow.WindowBind;
import com.jen.easyui.popupwindow.listener.WindowItemListener;
import com.jen.easyui.recycler.EasyHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class PopupWindowActivity extends EasyActivity {

    EasyWindow easyWindowStr;
    EasyWindow easyWindowObject;

    @EasyBindId(R.id.popup_line)
    View popup_line;
    @EasyBindId(R.id.popup_window_str)
    View popup_window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
    }

    @Override
    protected void intDataBeforeView() {

    }

    @Override
    protected void initViews() {
        final List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("13");
        list.add("1");
        list.add("2");
        list.add("1");

        easyWindowStr = EasyWindow.build(this)
                .setData(list)
                .setShowTopBar(false)
                .createString();

        easyWindowObject = EasyWindow.build(this)
                .setData(list)
                .setListener(new WindowItemListener() {
                    @Override
                    public void onClickItem(int flag, View view, Object item, int position) {

                    }
                })
                .createObject(new WindowBind() {
                    @Override
                    public int onBindItemLayout() {
                        return R.layout._easy_recycler_foot;
                    }

                    @Override
                    public void onBindItemData(EasyHolder easyHolder, View view, List data, int position) {
                        easyHolder.setTextView(R.id.tv_text, list.get(position));
                    }
                });

    }

    @Override
    protected void loadDataAfterView() {

    }

    @EasyBindClick({R.id.popup_window_str, R.id.popup_window_object})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.popup_window_str: {
                easyWindowStr.showAsDropDown(popup_window);
                break;
            }
            case R.id.popup_window_object: {
                easyWindowObject.showAsBottom(popup_window);
                break;
            }
            default: {

                break;
            }
        }
    }

}
