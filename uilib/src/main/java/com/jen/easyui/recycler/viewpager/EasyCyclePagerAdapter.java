package com.jen.easyui.recycler.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyLog;

import java.util.ArrayList;
import java.util.List;

public abstract class EasyCyclePagerAdapter<T> extends PagerAdapter {
    protected Context context;
    private final List<T> mData = new ArrayList<>();
    private final List<View> views = new ArrayList<>();
    private EasyCycleViewPager viewPager;
    private boolean isCycle;

    /**
     * 不循环
     *
     * @param context .
     * @param data    .
     */
    public EasyCyclePagerAdapter(Context context, List<T> data) {
        this.context = context;
        initData(data);
    }

    /**
     * 循环播放、滑动
     *
     * @param viewPager .
     * @param data      .
     */
    public EasyCyclePagerAdapter(final EasyCycleViewPager viewPager, List<T> data) {
        this.context = viewPager.getContext();
        this.viewPager = viewPager;
        isCycle = true;
        initData(data);
        viewPager.setCycle(true);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
//                EasyLog.d("onPageSelected position = " + position);
                if (viewPager.getSuperCurrentItem() == mData.size() - 1) {
                    viewPager.setCurrentItem(1, false);
                } else if (viewPager.getSuperCurrentItem() == 0) {
                    viewPager.setCurrentItem(mData.size() - 2, false);
                }
                super.onPageSelected(position);
            }
        });
    }

    private void initData(List<T> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        mData.clear();
        views.clear();
        if (isCycle) {
            mData.add(data.get(0));
            mData.addAll(data);
            mData.add(data.get(data.size() - 1));
        } else {
            mData.addAll(data);
        }
        for (int i = 0; i < mData.size(); i++) {
            views.add(bindImageView());
        }
    }

    public void setData(List<T> data) {
        initData(data);
    }

    public void setDataAndNotify(List<T> data) {
        initData(data);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (isCycle) {
            viewPager.setCurrentItem(1);
        }
    }

    /**
     * @return 不可为空
     */
    public abstract View bindImageView();

    public abstract void bindData(T item, int position, View view);

    @Override
    public int getCount() {
//        return Integer.MAX_VALUE;//返回一个无限大的值，可以 无限循环
        return mData.size();
    }

    /**
     * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * 初始化一个条目
     *
     * @param container .
     * @param position  当前需要加载条目的索引
     * @return .
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int index = position % views.size();
        View view = views.get(index);
//        viewPager.addView(iv);// 把position对应位置的ImageView添加到ViewPager中
        container.addView(view);
        T item = mData.get(index);
        int myIndex;
        if (index == 0) {
            myIndex = mData.size() - 2;
        } else if (index == mData.size() - 1) {
            myIndex = 1;
        } else {
            myIndex = index;
        }
        bindData(item, myIndex - 1, view);
        return view;// 把当前添加ImageView返回回去.
    }

    /**
     * 销毁一个条目
     * position 就是当前需要被销毁的条目的索引
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        viewPager.removeView(views.get(position % views.size())); // 把ImageView从ViewPager中移除掉
        container.removeView(views.get(position % views.size())); // 把ImageView从ViewPager中移除掉
    }
}