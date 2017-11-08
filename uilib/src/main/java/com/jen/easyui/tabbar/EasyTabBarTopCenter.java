package com.jen.easyui.tabbar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.List;

/**
 * 导航布局,说明：文字居中导航栏
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * 作者：ShannJenn
 * 时间：2017/10/31.
 */
public class EasyTabBarTopCenter extends EasyTabBarTopCenterImp {
    public EasyTabBarTopCenter(Context context) {
        super(context);
    }

    public EasyTabBarTopCenter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyTabBarTopCenter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setViewPager(ViewPager viewPager) {
        super.setViewPager(viewPager);
    }

    @Override
    public void setViewPager(ViewPager viewPager, List<String> titles) {
        super.setViewPager(viewPager, titles);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}