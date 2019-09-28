package com.jen.easyui.fragemnt;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jen.easy.log.EasyLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EasyFragmentManager {
    private FragmentManager mManager;
    private Fragment mCurrentFragment;
    private List<Fragment> mFragments;

    public EasyFragmentManager(FragmentManager fragmentManager) {
        mManager = fragmentManager;
        mFragments = new ArrayList<>();
    }

    /**
     * 初始化
     *
     * @param onSaveInstanceState Activity onSaveInstanceState
     * @param layoutId            .
     */
    private void init(boolean onSaveInstanceState, int layoutId) {
        FragmentTransaction transition = mManager.beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment fragment = mFragments.get(i);
            if (!onSaveInstanceState) {
                transition.add(layoutId, fragment, fragment.getClass().getName());
            }
            if (i == 0) {
                transition.show(fragment);//默认显示第一个
                mCurrentFragment = fragment;
            } else {
                transition.hide(fragment);
            }
        }
        transition.commit();
    }

    /**
     * 增加Fragment
     *
     * @param layoutId  布局容器ID
     * @param fragments .
     */
    public void addFragment(int layoutId, Fragment... fragments) {
        Collections.addAll(mFragments, fragments);
        init(false, layoutId);
    }


    /**
     * @param cls Activity onSaveInstanceState 重建后调用
     */
    public void onSaveInstanceState(Class... cls) {
        mFragments.clear();
        for (Class cl : cls) {
            Fragment fragment = mManager.findFragmentByTag(cl.getName());
            if (fragment == null) {
                EasyLog.e(cl.getName() + " 未曾增加或者没有保留缓存");
            } else {
                mFragments.add(fragment);
            }
        }
        init(true, 0);
    }

    /**
     * @param fragment Activity onSaveInstanceState 重建后调用
     */
    public void onSaveInstanceState(Fragment... fragment) {
        mFragments.clear();
        Collections.addAll(mFragments, fragment);
        init(true, 0);
    }

    /**
     * 动态显示Fragment
     */
    public void showFragment(Class cls) {
        Fragment fragment = mManager.findFragmentByTag(cls.getName());
        if (fragment == null) {
            EasyLog.e(cls.getName() + " 未曾增加");
            return;
        }
        if (mCurrentFragment != null && mCurrentFragment.getClass().getName().equals(cls.getName())) {
            return;
        }
        FragmentTransaction transition = mManager.beginTransaction();
        transition.show(fragment);
        if (mCurrentFragment != null) {
            transition.hide(mCurrentFragment);
        }
//        transition.commit();
        transition.commitAllowingStateLoss();
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
            EasyLog.e(cls.getName() + " 该Fragment还没增加");
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
     * 移除所有fragment
     */
    public void removeAllFragments() {
        mFragments.clear();
    }

    /**
     * 获取当前Fragment
     *
     * @return .
     */
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    /**
     * 设置默认当前Fragment
     *
     * @param mCurrentFragment .
     */
    public void setCurrentFragment(Fragment mCurrentFragment) {
        this.mCurrentFragment = mCurrentFragment;
    }

    public boolean isFragmentShow(Class clazz) {
        return mCurrentFragment != null && mCurrentFragment.getClass().getName().equals(clazz.getName());
    }
}
