package com.jen.easyui.tabbar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 导航布局,说明：图片文字导航栏，不带滑动游标效果
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public class EasyTabBarBottom extends EasyTabBarBottomImp {

    public EasyTabBarBottom(Context context) {
        super(context);
    }

    public EasyTabBarBottom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyTabBarBottom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        super.setViewPager(viewPager);
    }
}
