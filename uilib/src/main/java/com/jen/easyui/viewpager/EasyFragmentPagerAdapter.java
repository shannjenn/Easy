package com.jen.easyui.viewpager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/9/11.
 */

public class EasyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    protected FragmentManager fm;
    protected final List<String> mTitles = new ArrayList<>();
    protected final List<Fragment> mFragments = new ArrayList<>();

    public EasyFragmentPagerAdapter(FragmentManager fm, List<String> title, List<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        mTitles.clear();
        if (title != null && title.size() > 0) {
            mTitles.addAll(title);
        }
        mFragments.clear();
        mFragments.addAll(fragments);
    }

    public EasyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        mFragments.clear();
        mFragments.addAll(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        int count = 0;
        count = mFragments.size();
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles.size() <= position) {
            return "";
        }
        return mTitles.get(position);
    }

    /*@Override
    public void finishUpdate(ViewGroup container) {
        try {
            super.finishUpdate(container);
        } catch (NullPointerException nullPointerException) {
            Log.d("Catch the NullPointerException in EasyFragmentPagerAdapterManager.finishUpdate");
        }
    }*/

    public void upDatas(List<String> title, List<Fragment> fragments) {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment f : mFragments) {
            ft.remove(f);
        }
//        ft.commit();
        ft.commitAllowingStateLoss();
        ft = null;
        fm.executePendingTransactions();

        mTitles.clear();
        if (title != null && title.size() > 0) {
            mTitles.addAll(title);
        }
        mFragments.clear();
        mFragments.addAll(fragments);
        notifyDataSetChanged();
    }

    public void upDatas(List<Fragment> fragments) {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment f : mFragments) {
            ft.remove(f);
        }
//        ft.commit();
        ft.commitAllowingStateLoss();
        ft = null;
        fm.executePendingTransactions();

        mFragments.clear();
        mFragments.addAll(fragments);
        notifyDataSetChanged();
    }

    /*@Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }*/
}
