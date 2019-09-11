package com.jen.easyui.recycler.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.jen.easy.log.EasyLog;

public class EasyCycleViewPager extends ViewPager {
    private boolean isCycle;

    public EasyCycleViewPager(@NonNull Context context) {
        super(context);
    }

    public EasyCycleViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getCurrentItem() {
        PagerAdapter adapter = getAdapter();
        if (adapter == null) {
            return -1;
        } else if (!(adapter instanceof EasyCyclePagerAdapter)) {
            EasyLog.e("必须使用EasyCyclePagerAdapter");
            return -1;
        }
        int count = adapter.getCount();
        int position = super.getCurrentItem();
        if (isCycle) {
            if (position == 0) {
                position = count - 2;
            } else if (position == count - 1) {
                position = 0;
            } else {
                position = position - 1;
            }
        }
        return position;
    }

    int getSuperCurrentItem() {
        return super.getCurrentItem();
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item + 1);
    }

    void setCycle(boolean cycle) {
        isCycle = cycle;
    }
}
