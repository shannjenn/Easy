package com.jen.easyui.recycler.layoutManager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyLog;

/**
 * 自动适应RecycleView高度(如遇到与ScrollView共用时会用到)
 */
public class FullyGridLayoutManager extends GridLayoutManager {
    private int totalHeight;

    public FullyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public FullyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);

        int width = 0;
        int height = 0;
        int count = getItemCount();
        int span = getSpanCount();
        int[] measuredDimension = new int[2];
        for (int i = 0; i < count; i++) {
            measureScrapChild(recycler, i,
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    measuredDimension);

            if (getOrientation() == HORIZONTAL) {
                if (i % span == 0) {
                    width = width + measuredDimension[0];
                }
                if (i == 0) {
                    height = measuredDimension[1];
                }
            } else {
                if (i % span == 0) {
                    height = height + measuredDimension[1];
                }
                if (i == 0) {
                    width = measuredDimension[0];
                }
            }
        }

        switch (widthMode) {
            case View.MeasureSpec.EXACTLY:
                width = widthSize;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
        }

        switch (heightMode) {
            case View.MeasureSpec.EXACTLY:
                height = heightSize;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
        }

        setMeasuredDimension(width, height);
        totalHeight = height;
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec, int heightSpec, int[] measuredDimension) {
        final View child;
        try {
            child = recycler.getViewForPosition(position);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        if (child == null) {
            return;
        }
        RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) child.getLayoutParams();
        int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, getPaddingLeft() + getPaddingRight(), p.width);
        int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, getPaddingTop() + getPaddingBottom(), p.height);
        child.measure(childWidthSpec, childHeightSpec);
        measuredDimension[0] = child.getMeasuredWidth() + p.leftMargin + p.rightMargin;
        measuredDimension[1] = child.getMeasuredHeight() + p.bottomMargin + p.topMargin;
        recycler.recycleView(child);
    }

    public int getTotalHeight() {
        return totalHeight;
    }
}