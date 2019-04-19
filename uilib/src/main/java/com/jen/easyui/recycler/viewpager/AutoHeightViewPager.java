package com.jen.easyui.recycler.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自动适应高度
 */
public class AutoHeightViewPager extends ViewPager {
    private boolean autoAllItem;

    public AutoHeightViewPager(Context context) {
        super(context);
    }

    public AutoHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (autoAllItem) {
            int maxHeight = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                if (view != null) {
                    view.measure(widthMeasureSpec, heightMeasureSpec);
                }
                int height = measureHeight(heightMeasureSpec, view);
                if (maxHeight < height) {
                    maxHeight = height;
                }
            }
            setMeasuredDimension(getMeasuredWidth(), maxHeight);
        } else {
            View view = getChildAt(getCurrentItem());
            if (view != null) {
                view.measure(widthMeasureSpec, heightMeasureSpec);
            }
            setMeasuredDimension(getMeasuredWidth(), measureHeight(heightMeasureSpec, view));
        }
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @param view        the base view with already measured height
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec, View view) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            // set the height from the base view if available
            if (view != null) {
                result = view.getMeasuredHeight();
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 单独测量view获取尺寸
     *
     * @param view .
     */
    public void measureView(View view) {
        int intw = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int inth = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        // 重新测量view
        view.measure(intw, inth);

        // 以上3句可简写成下面一句
        //view.measure(0,0);

        // 获取测量后的view尺寸
        int intwidth = view.getMeasuredWidth();
        int intheight = view.getMeasuredHeight();
    }

    public void setAutoAllItem(boolean autoAllItem) {
        this.autoAllItem = autoAllItem;
    }
}