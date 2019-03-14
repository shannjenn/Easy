package com.jen.easyui.view.baseview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

public class EasyMarqueeTextView extends android.support.v7.widget.AppCompatTextView {

    public EasyMarqueeTextView(Context context) {
        super(context);
        init();
    }

    public EasyMarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EasyMarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //设置单行
        setSingleLine();
        //设置Ellipsize
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        //获取焦点
        setFocusable(true);
        //走马灯的重复次数，-1代表无限重复
        setMarqueeRepeatLimit(-1);
        //强制获得焦点
        setFocusableInTouchMode(true);
    }

    /*
     *这个属性这个View得到焦点,在这里我们设置为true,这个View就永远是有焦点的
     */
    @Override
    public boolean isFocused() {
        return true;
    }
}
