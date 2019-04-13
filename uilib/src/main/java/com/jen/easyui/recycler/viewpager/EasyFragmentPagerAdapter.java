package com.jen.easyui.recycler.viewpager;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class EasyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private FragmentManager mFragmentManager;
    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();

    public EasyFragmentPagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        this.mFragmentManager = fm;
        if (titles != null && titles.size() > 0) {
            mTitles.addAll(titles);
        }
        if (fragments != null && fragments.size() > 0) {
            this.mFragments.addAll(fragments);
        }
    }

    public EasyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragmentManager = fm;
        if (fragments != null && fragments.size() > 0) {
            this.mFragments.addAll(fragments);
        }
    }

    /**
     * 禁止用,使用updateData
     */
    @Override
    public void notifyDataSetChanged() {
//        super.notifyDataSetChanged();
    }

    private void setDataSetChanged() {
        super.notifyDataSetChanged();
    }

    /**
     * 刷新使用该方法
     */
    public void updateData(List<Fragment> mlist) {
        if (mlist == null) return;
        this.mFragments.clear();
        this.mFragments.addAll(mlist);
        setDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles.size() < position) {
            return "";
        }
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragments.get(arg0);//
    }

    @Override
    public int getCount() {
        return mFragments.size();//
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        if (!((Fragment) object).isAdded() && mFragments.contains(object)) {//新增
            return PagerAdapter.POSITION_NONE;
        } else if (((Fragment) object).isAdded() && !mFragments.contains(object)) {//删除
            return PagerAdapter.POSITION_NONE;
        }
        return mFragments.indexOf(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment instantiateItem = ((Fragment) super.instantiateItem(container, position));
        Fragment item = mFragments.get(position);
        if (instantiateItem == item) {
            return instantiateItem;
        } else {
            //如果集合中对应下标的fragment和fragmentManager中的对应下标的fragment对象不一致，那么就是新添加的，所以自己add进入；这里为什么不直接调用super方法呢，因为fragment的mIndex搞的鬼，以后有机会再补一补。
            mFragmentManager.beginTransaction().add(container.getId(), item).commitNowAllowingStateLoss();
            return item;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        //如果getItemPosition中的值为PagerAdapter.POSITION_NONE，就执行该方法。
        if (mFragments.contains(fragment)) {
            super.destroyItem(container, position, fragment);
            return;
        }
        //自己执行移除。因为mFragments在删除的时候就把某个fragment对象移除了，所以一般都得自己移除在fragmentManager中的该对象。
        mFragmentManager.beginTransaction().remove(fragment).commitNowAllowingStateLoss();

    }
}