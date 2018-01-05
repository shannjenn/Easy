package com.jen.easyui.flowlayout;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 自动换行布局
 * 作者：ShannJenn
 * 时间：2017/11/16.
 */
abstract class EasyFlowLayout extends EasyFlowLayoutManager {

    public EasyFlowLayout(Context context) {
        super(context);
    }

    public EasyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}