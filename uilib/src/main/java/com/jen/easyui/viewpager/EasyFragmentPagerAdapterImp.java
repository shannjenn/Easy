package com.jen.easyui.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jen.easyui.EasyUILog;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/9/11.
 */

abstract class EasyFragmentPagerAdapterImp extends FragmentStatePagerAdapter {
    private final String TAG = EasyFragmentPagerAdapterImp.class.getSimpleName() + " ";
    protected List<String> title;
    protected List<Fragment> fragments;

    protected EasyFragmentPagerAdapterImp(FragmentManager fm, List<String> title, List<Fragment> fragments) {
        super(fm);
        this.title = title;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= fragments.size()) {
            EasyUILog.e(TAG + "position >= fragments.size()");
            return null;
        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        int count = 0;
        if (fragments == null) {
            EasyUILog.e(TAG + "fragments is null");
            return count;
        }
        if (title != null) {
            count = title.size();
        }
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
