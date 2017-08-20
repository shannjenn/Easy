package com.jen.easyui.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 创建人：ShannJenn
 * 时间：2017/8/20.
 */

public class Listview_footer extends android.widget.LinearLayout {

    public Listview_footer(Context context) {
        super(context);
    }

    public Listview_footer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Listview_footer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context){
        RelativeLayout relativeLayout = new RelativeLayout(context);
    }

}
