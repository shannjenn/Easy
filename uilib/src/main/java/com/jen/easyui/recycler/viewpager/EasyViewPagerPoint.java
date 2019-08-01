package com.jen.easyui.recycler.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jen.easyui.R;
import com.jen.easyui.util.DensityUtil;

/**
 * 作者：ShannJenn
 * 时间：2017/9/11.
 */

public class EasyViewPagerPoint extends RelativeLayout {
    private final String TAG = EasyViewPagerPoint.class.getSimpleName() + " ";
    /*小圆点离底部距离db*/
    private final float DB_BOTTOM_MARGIN = 20.0f;
    /*小圆点大小db*/
    private final float DB_NUM_SIZE = 5.0f;
    /*小圆点直接的距离*/
    private final int DB_NUM_DISTANCE = 5;
    /*小圆点数量*/
    private int numCount = 0;

    private ViewPager viewPager;
    /*小圆点布局容器*/
    private LinearLayout numLayout;
    /*当前选中的小圆点*/
    private View mPreSelectedPoit;

    private EasyImagePagerChageListener pagerChageListener;

    public EasyViewPagerPoint(Context context) {
        super(context);
        initLayout();
    }

    public EasyViewPagerPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    private void initLayout() {
        LayoutParams viewPagerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        viewPager = new ViewPager(getContext());
        viewPager.setLayoutParams(viewPagerParams);
        addView(viewPager);

        LayoutParams linearLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        linearLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        linearLayoutParams.bottomMargin = DensityUtil.dp2pxInt(DB_BOTTOM_MARGIN);
        numLayout = new LinearLayout(getContext());
        numLayout.setLayoutParams(linearLayoutParams);
        addView(numLayout);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            if (position >= numLayout.getChildCount()) {
                Log.w(TAG, "position >=numLayout.getChildCount()");
                return;
            }
            if (mPreSelectedPoit != null) {
                mPreSelectedPoit.setBackgroundResource(R.drawable._easy_viewpager_num_defaut);
            }
            View currentView = numLayout.getChildAt(position);
            currentView.setBackgroundResource(R.drawable._easy_viewpager_num_select);
            mPreSelectedPoit = currentView;
            if (pagerChageListener != null) {
                pagerChageListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (pagerChageListener != null) {
                pagerChageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (pagerChageListener != null) {
                pagerChageListener.onPageScrollStateChanged(state);
            }
        }
    };

    public void setAdapter(PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    /**
     * 设置小圆点数量
     *
     * @param numCount
     */
    public void setNumCount(int numCount) {
        this.numCount = numCount;
        int size = DensityUtil.dp2pxInt(DB_NUM_SIZE);
        LinearLayout.LayoutParams poitParams = new LinearLayout.LayoutParams(size, size);
        poitParams.rightMargin = DB_NUM_DISTANCE;
//        poitParams.setMargins(0, 0, DB_NUM_DISTANCE, 0);

        for (int i = 0; i < numCount; i++) {
            View poit = new View(getContext());
            poit.setLayoutParams(poitParams);
            if (i == 0) {
                poit.setBackgroundResource(R.drawable._easy_viewpager_num_select);
            } else {
                poit.setBackgroundResource(R.drawable._easy_viewpager_num_defaut);
            }
            numLayout.addView(poit);
        }
    }

    public void addOnPageChangeListener(EasyImagePagerChageListener pagerChageListener) {
        this.pagerChageListener = pagerChageListener;
    }

    public interface EasyImagePagerChageListener {
        void onPageSelected(int position);

        void onPageScrolled(int arg0, float arg1, int arg2);

        void onPageScrollStateChanged(int arg0);
    }

}
