package com.jen.easytest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jen.easy.EasyMouse;
import com.jen.easytest.R;
import com.jen.easytest.fragment.EmptyFragmentBlue;
import com.jen.easytest.fragment.EmptyFragmentRed;
import com.jen.easyui.activity.EasyBaseActivity;
import com.jen.easyui.tabbar.EasyTabBarBottom;
import com.jen.easyui.tabbar.EasyTabBarTop;
import com.jen.easyui.viewpager.EasyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/10/26.
 */

public class TabBarActivity extends EasyBaseActivity {

    @EasyMouse.BIND.ID(R.id.easy_tabbar_txtimg)
    EasyTabBarBottom easy_tabbar_txtimg;

    @EasyMouse.BIND.ID(R.id.easy_tabar_scroll)
    EasyTabBarTop easy_tabar_scroll;

    @EasyMouse.BIND.ID(R.id.viewpager_scroll)
    ViewPager viewpager_scroll;

    Handler handler = new Handler();
    CenterFragmentPagerAdapter centerFragmentPagerAdapter;
    ScrollFragmentPagerAdapter scrollFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbar);
    }

    @Override
    protected void intDataBeforeView() {

    }

    @Override
    protected void initViews() {
        initScroll();
    }

    @Override
    protected void loadDataAfterView() {

    }

    @Override
    protected void onBindClick(View view) {

    }

    private void initScroll(){
        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();

        titles.add("标题1");
        titles.add("标题2222");
        titles.add("标题3");
        titles.add("标题4");
        titles.add("标题5");
        titles.add("标题6");
        titles.add("标题7");
        titles.add("标题8");
        titles.add("标题9");
        titles.add("标题10");
        titles.add("标题11");

        Fragment fragment1 = new EmptyFragmentBlue();
        Fragment fragment2 = new EmptyFragmentRed();
        Fragment fragment3 = new EmptyFragmentBlue();
        Fragment fragment4 = new EmptyFragmentRed();
        Fragment fragment5 = new EmptyFragmentBlue();
        Fragment fragment6 = new EmptyFragmentBlue();
        Fragment fragment7 = new EmptyFragmentBlue();
        Fragment fragment8 = new EmptyFragmentBlue();
        Fragment fragment9 = new EmptyFragmentBlue();
        Fragment fragment10 = new EmptyFragmentBlue();
        Fragment fragment11 = new EmptyFragmentBlue();

        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);
        fragments.add(fragment6);
        fragments.add(fragment7);
        fragments.add(fragment8);
        fragments.add(fragment9);
        fragments.add(fragment10);
        fragments.add(fragment11);

        scrollFragmentPagerAdapter = new ScrollFragmentPagerAdapter(getSupportFragmentManager(),titles,fragments);
        viewpager_scroll.setAdapter(scrollFragmentPagerAdapter);
        easy_tabar_scroll.setViewPager(viewpager_scroll,titles);
    }


    class CenterFragmentPagerAdapter extends EasyFragmentPagerAdapter {

        protected CenterFragmentPagerAdapter(FragmentManager fm, List<String> title, List<Fragment> fragments) {
            super(fm, title, fragments);
        }
    }

    class ScrollFragmentPagerAdapter extends EasyFragmentPagerAdapter {

        protected ScrollFragmentPagerAdapter(FragmentManager fm, List<String> title, List<Fragment> fragments) {
            super(fm, title, fragments);
        }
    }

}
