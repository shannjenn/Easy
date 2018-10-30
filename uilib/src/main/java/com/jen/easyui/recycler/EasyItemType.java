package com.jen.easyui.recycler;

/**
 * 作者：ShannJenn
 * 时间：2018/5/28:23:31
 * 说明：ItemType 设置值小于0，adapter中ItemViewType值大于0，以此做区分
 */

public enum EasyItemType {
    HEAD(-109050301),
    FOOT(-109050302),
    BODY(-109050303);

    private int mType = -1;

    private EasyItemType(int value) {
        mType = value;
    }

    public int getType() {
        return mType;
    }

}
