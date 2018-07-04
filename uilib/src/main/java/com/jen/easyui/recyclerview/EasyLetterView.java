package com.jen.easyui.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 说明：
 * 1.自定义字符串请在布局实现lettersByComma属性（值用逗号隔开）
 * 2.顶部和底部有遮挡，设置paddingTop和paddingBottom
 * 3.点击时该View宽度全屏，注意设置布局位置
 * 作者：ShannJenn
 * 时间：2018/03/14.
 */

public class EasyLetterView extends EasyLetterViewManager {

    public EasyLetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyLetterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}