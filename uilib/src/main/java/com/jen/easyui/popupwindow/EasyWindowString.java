package com.jen.easyui.popupwindow;

import android.view.View;

import com.jen.easyui.R;
import com.jen.easyui.recycler.EasyHolder;

import java.util.List;

/**
 * 说明：String类型
 * 作者：ShannJenn
 * 时间：2017/09/09.
 */

public class EasyWindowString extends EasyWindow {

    EasyWindowString(Build build) {
        super(build);
    }

    private WindowBind windowBind = new WindowBind() {
        @Override
        public int[] onBindItemLayout() {
            return new int[]{R.layout._easy_popup_window_string_item};
        }

        @Override
        public int onBindViewType() {
            return 0;
        }

        @Override
        public void onBindItemData(EasyHolder easyHolder, View view, List data, int position) {
            Object object = data.get(position);
            if (object instanceof String) {
                easyHolder.setTextView(R.id.shape_name, (String) object);
            }
        }
    };

    @Override
    protected WindowBind windowBindFactory() {
        return windowBind;
    }
}
