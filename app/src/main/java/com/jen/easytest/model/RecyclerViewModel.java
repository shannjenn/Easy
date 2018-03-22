package com.jen.easytest.model;

import com.jen.easyui.recyclerview.EasyLetterItem;

/**
 * Created by Administrator on 2017/12/25.
 */

public class RecyclerViewModel extends EasyLetterItem {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
