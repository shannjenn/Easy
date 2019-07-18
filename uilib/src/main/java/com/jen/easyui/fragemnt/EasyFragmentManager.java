package com.jen.easyui.fragemnt;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jen.easy.log.EasyLog;

import java.util.ArrayList;
import java.util.List;

public class EasyFragmentManager {
    private FragmentManager mManager;
    private Fragment mCurrentFragment;
    private List<Fragment> mFragments;

    public EasyFragmentManager(AppCompatActivity activity) {
        mManager = activity.getSupportFragmentManager();
        mFragments = new ArrayList<>();
    }

    /**
     * 增加Fragment
     *
     * @param layoutId  布局容器ID
     * @param fragments .
     */
    public void addFragment(int layoutId, Fragment... fragments) {
        FragmentTransaction transition = mManager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            Fragment fragment = fragments[i];
            transition.add(layoutId, fragment, fragment.getClass().getName());
            if (i == 0) {
                transition.show(fragments[i]);//默认显示第一个
                mCurrentFragment = fragments[i];
            } else {
                transition.hide(fragments[i]);
            }
            mFragments.add(fragment);
        }
        transition.commit();
    }

    /**
     * 动态显示Fragment
     */
    public void showFragment(Class cls) {
        Fragment fragment = mManager.findFragmentByTag(cls.getName());
        if (fragment == null) {
            EasyLog.w(cls.getName() + " 未曾增加");
            return;
        }
        FragmentTransaction transition = mManager.beginTransaction();
        transition.show(fragment);
        if (mCurrentFragment != null) {
            transition.hide(mCurrentFragment);
        }
        transition.commit();
        mCurrentFragment = fragment;
    }

    /**
     * 显示第几个Fragment
     *
     * @param position .
     */
    public void showFragment(int position) {
        if (position < 0 || position >= mFragments.size()) {
            EasyLog.w("showFragment error position = " + position);
            return;
        }
        showFragment(mFragments.get(position).getClass());
    }

    /**
     * 获取fragment
     *
     * @param cls .
     * @return .
     */
    public Fragment findFragment(Class cls) {
        Fragment fragment = mManager.findFragmentByTag(cls.getName());
        if (fragment == null) {
            throw new RuntimeException(cls.getName() + " 该Fragment还没增加");
        }
        return fragment;
    }

    /**
     * 获取Fragment集合
     *
     * @return .
     */
    public List<Fragment> getmFragments() {
        if (mFragments == null) {
            return new ArrayList<>();
        }
        return mFragments;
    }

    /**
     * 获取当前Fragment
     *
     * @return .
     */
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
