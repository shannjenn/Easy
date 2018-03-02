package com.jen.easyui.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.jen.easy.log.EasyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/9/11.
 */

abstract class EasyFragmentPagerAdapterManager extends FragmentStatePagerAdapter {
    private final String TAG = EasyFragmentPagerAdapterManager.class.getSimpleName() + " ";
    protected FragmentManager fm;
    protected final List<String> mTitles = new ArrayList<>();
    protected final List<Fragment> mFragments = new ArrayList<>();

    protected EasyFragmentPagerAdapterManager(FragmentManager fm, List<String> title, List<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        mTitles.clear();
        mTitles.addAll(title);
        mFragments.clear();
        mFragments.addAll(fragments);
    }

    protected EasyFragmentPagerAdapterManager(FragmentManager fm, List<Fragment> fragments) {
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
        if (mFragments != null) {
            count = mFragments.size();
        }
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles.size() <= position) {
            EasyLog.d(TAG + "getPageTitle mTitles.size() <= position");
            return "";
        }
        return mTitles.get(position);
    }

    /*@Override
    public void finishUpdate(ViewGroup container) {
        try {
            super.finishUpdate(container);
        } catch (NullPointerException nullPointerException) {
            EasyLog.d("Catch the NullPointerException in EasyFragmentPagerAdapterManager.finishUpdate");
        }
    }*/

    public void upDatas(List<String> titles, List<Fragment> fragments) {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment f : mFragments) {
            ft.remove(f);
        }
//        ft.commit();
        ft.commitAllowingStateLoss();
        ft = null;
        fm.executePendingTransactions();

        mTitles.clear();
        mTitles.addAll(titles);
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
