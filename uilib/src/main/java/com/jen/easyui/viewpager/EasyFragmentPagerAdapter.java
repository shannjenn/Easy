package com.jen.easyui.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/9/11.
 */

public abstract class EasyFragmentPagerAdapter extends EasyFragmentPagerAdapterImp {

    protected EasyFragmentPagerAdapter(FragmentManager fm, List<String> title, List<Fragment> fragments) {
        super(fm, title, fragments);
    }

    /**
     * 用此方法更新数据，不要使用notifyDataSetChanged();
     * @param titles
     * @param fragments
     */
    @Override
    public void upDatas(List<String> titles, List<Fragment> fragments) {
        super.upDatas(titles, fragments);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
