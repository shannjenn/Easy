package com.jen.easyui.viewpager;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2017/9/11.
 */

abstract class EasyImagePagerAdapterManager<T> extends PagerAdapter {
    protected Context context;
    protected List<T> data;

    EasyImagePagerAdapterManager(Context context, List<T> data) {
        super();
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public int getItemPosition(Object object) {
        if (object != null && data != null) {
            Integer resId = (Integer) ((ImageView) object).getTag();
            if (resId != null) {
                for (int i = 0; i < data.size(); i++) {
                    if (resId.equals(data.get(i))) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        if (data != null && position < data.size()) {
            T item = data.get(position);
            if (item != null) {
                ImageView itemImage = new ImageView(context);
                setItemImage(itemImage, item);
                //此处假设所有的照片都不同，用resId唯一标识一个itemView；也可用其它Object来标识，只要保证唯一即可
                itemImage.setTag(item);
                ((ViewPager) container).addView(itemImage);
                return itemImage;
            }
        }
        return null;
    }

    protected abstract void setItemImage(ImageView itemImage, T item);

    @Override
    public void destroyItem(View container, int position, Object object) {
        //注意：此处position是ViewPager中所有要显示的页面的position，与Adapter mDrawableResIdList并不是一一对应的。
        //因为mDrawableResIdList有可能被修改删除某一个item，在调用notifyDataSetChanged()的时候，ViewPager中的页面
        //数量并没有改变，只有当ViewPager遍历完自己所有的页面，并将不存在的页面删除后，二者才能对应起来
        if (object != null) {
            ViewGroup viewPager = ((ViewGroup) container);
            int count = viewPager.getChildCount();
            for (int i = 0; i < count; i++) {
                View childView = viewPager.getChildAt(i);
                if (childView == object) {
                    viewPager.removeView(childView);
                    break;
                }
            }
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public void startUpdate(View container) {
    }

    @Override
    public void finishUpdate(View container) {
    }

}
