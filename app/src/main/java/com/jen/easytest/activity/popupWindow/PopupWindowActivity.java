package com.jen.easytest.activity.popupWindow;

import android.os.Bundle;
import android.view.View;

import com.jen.easy.EasyBindClick;
import com.jen.easy.EasyBindId;
import com.jen.easytest.R;
import com.jen.easytest.model.RecyclerViewModel;
import com.jen.easyui.base.EasyActivity;
import com.jen.easyui.popupwindow.EasyWindow;
import com.jen.easyui.popupwindow.StyleTopBar;
import com.jen.easyui.popupwindow.WindowBind;
import com.jen.easyui.recycler.EasyHolder;
import com.jen.easyui.recycler.letter.EasyLetterDecoration;
import com.jen.easyui.util.EasyDensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class PopupWindowActivity extends EasyActivity {

    EasyWindow easyWindowStr;
    EasyWindow easyWindowObject;
    EasyWindow easyWindowLetter;

    StyleTopBar styleTopBar;

    @EasyBindId(R.id.popup_window_object)
    View popup_window_object;
    @EasyBindId(R.id.popup_line)
    View popup_line;
    @EasyBindId(R.id.popup_window_str)
    View popup_window;
    @EasyBindId(R.id.tv_right)
    View tv_right;

    List<RecyclerViewModel> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
    }

    @Override
    protected void initViews() {
        final List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("13");
        list.add("1");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("2");
        list.add("1");

        styleTopBar = new StyleTopBar();

        easyWindowStr = EasyWindow.build(this)
//                .setData(list)
//                .setWidth(500)
                .setShowTopBar(true)
                .createString();
        easyWindowStr.setData(list);

        easyWindowObject = EasyWindow.build(this)
                .setStyleTopBar(styleTopBar)
                .createObject(new WindowBind() {
                    @Override
                    public int[] onBindItemLayout() {
                        return new int[]{R.layout._easy_recycler_foot};
                    }

                    @Override
                    public int onBindViewType() {
                        return 0;
                    }

                    @Override
                    public void onBindItemData(EasyHolder easyHolder, View view, List data, int position) {
                        easyHolder.setTextView(R.id.tv_text, list.get(position));
                    }
                });
        easyWindowObject.setData(list);

        easyWindowLetter = EasyWindow.build(this)
                .createLetter(new WindowBind() {
                    @Override
                    public int onBindViewType() {
                        return 0;
                    }

                    @Override
                    public int[] onBindItemLayout() {
                        return new int[]{R.layout.item_test};
                    }

                    @Override
                    public void onBindItemData(EasyHolder easyHolder, View view, List data, int position) {
                        RecyclerViewModel model = (RecyclerViewModel) data.get(position);

                        easyHolder.setTextView(R.id.tv_rename,model.getName());
                    }
                }, new EasyLetterDecoration());

        mData.clear();
        for (int i = 0; i < 36; i++) {
            RecyclerViewModel model = new RecyclerViewModel();
            model.setName("字母" + i);
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

    }

    @EasyBindClick({R.id.popup_window_str, R.id.popup_window_object, R.id.popup_window_right, R.id.popup_window_letter})
    @Override
    protected void onBindClick(View view) {
        switch (view.getId()) {
            case R.id.popup_window_str: {
//                easyWindowStr.showDropDown(popup_window);
                easyWindowStr.showBottom(popup_window);
                break;
            }
            case R.id.popup_window_object: {
                styleTopBar.setTitleText("topBar测试");
                easyWindowObject.updateTopBar();
                easyWindowObject.showBottom(popup_window);
                break;
            }
            case R.id.popup_window_right: {
                int x = -EasyDensityUtil.dp2pxInt(195);
                int y = EasyDensityUtil.dp2pxInt(23 + 10);
                EasyWindow.build(this)
                        .setShowTopBar(false)
                        .setWidth(EasyDensityUtil.dp2pxInt(195))
                        .setHeight(EasyDensityUtil.dp2pxInt(45))
                        .createObject(WindowBindEdit.bind()).showRight(tv_right, x, y);
                break;
            }
            case R.id.popup_window_letter: {
                easyWindowLetter.setData(mData);
                easyWindowLetter.showBottom(view);
                break;
            }
        }
    }

}
