package com.jen.easytest.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.jen.easy.EasyMouse;
import com.jen.easytest.BaseActivity;
import com.jen.easytest.R;
import com.jen.easytest.fragment.EmptyFragmentBlue;
import com.jen.easytest.fragment.EmptyFragmentRed;
import com.jen.easyui.tabbar.EasyTabBarTxtImg;
import com.jen.easyui.tabbar.EasyTabBarTxtScroll;
import com.jen.easyui.viewpager.EasyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class TabBarActivity extends BaseActivity {

    @EasyMouse.BIND.ID(R.id.easy_tabbar_txtimg)
    EasyTabBarTxtImg easy_tabbar_txtimg;

    @EasyMouse.BIND.ID(R.id.easy_tabar_scroll)
    EasyTabBarTxtScroll easy_tabar_scroll;

    @EasyMouse.BIND.ID(R.id.viewpager_scroll)
    ViewPager viewpager_scroll;

    ScrollFragmentPagerAdapter scrollFragmentPagerAdapter;
    List<String> mScrollTitles = new ArrayList<>();
    List<Fragment> mScrollFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbar);
    }

    @Override
    protected void initViews() {
        mScrollTitles.add("标题1");
        mScrollTitles.add("标题2");
        mScrollTitles.add("标题3");
        mScrollTitles.add("标题4");
        mScrollTitles.add("标题5");

        Fragment fragment1 = new EmptyFragmentBlue();
        Fragment fragment2 = new EmptyFragmentRed();
        Fragment fragment3 = new EmptyFragmentBlue();
        Fragment fragment4 = new EmptyFragmentRed();
        Fragment fragment5 = new EmptyFragmentBlue();
        mScrollFragments.add(fragment1);
        mScrollFragments.add(fragment2);
        mScrollFragments.add(fragment3);
        mScrollFragments.add(fragment4);
        mScrollFragments.add(fragment5);

        scrollFragmentPagerAdapter = new ScrollFragmentPagerAdapter(getSupportFragmentManager(),mScrollTitles,mScrollFragments);
        viewpager_scroll.setAdapter(scrollFragmentPagerAdapter);
        easy_tabar_scroll.setViewPager(viewpager_scroll,mScrollTitles);
    }

    @Override
    protected void initDatas() {

    }


    class ScrollFragmentPagerAdapter extends EasyFragmentPagerAdapter {

        protected ScrollFragmentPagerAdapter(FragmentManager fm, List<String> title, List<Fragment> fragments) {
            super(fm, title, fragments);
        }
    }

}
