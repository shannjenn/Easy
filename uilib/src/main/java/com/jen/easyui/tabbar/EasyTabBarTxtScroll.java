package com.jen.easyui.tabbar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.List;

/**
 * 导航布局,说明：文字导航栏，滑动游标效果、标题可以滑动
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * 作者：ShannJenn
 * 时间：2017/10/31.
 */
public class EasyTabBarTxtScroll extends EasyTabBarTxtScrollImp {
    public EasyTabBarTxtScroll(Context context) {
        super(context);
    }

    public EasyTabBarTxtScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyTabBarTxtScroll(Context context, AttributeSet attrs, int defStyleAttr) {
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