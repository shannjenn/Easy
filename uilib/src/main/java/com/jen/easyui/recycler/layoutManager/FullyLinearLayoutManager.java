package com.jen.easyui.recycler.layoutManager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easy.log.EasyLog;

/**
 * 自动适应RecycleView高度(如遇到与ScrollView共用时会用到 调整适应高度)
 */
public class FullyLinearLayoutManager extends LinearLayoutManager {
    private int totalHeight;

    public FullyLinearLayoutManager(Context context) {
        super(context);
    }

    public FullyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {

        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);

//        Log.i(TAG, "onMeasure called. \nwidthMode " + widthMode
//                + " \nheightMode " + heightSpec
//                + " \nwidthSize " + widthSize
//                + " \nheightSize " + heightSize
//                + " \ngetItemCount() " + getItemCount());

        int width = 0;
        int height = 0;
        int[] measuredDimension = new int[2];
        for (int i = 0; i < getItemCount(); i++) {
            measureScrapChild(recycler, i,
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    measuredDimension);
            if (getOrientation() == HORIZONTAL) {
                width = width + measuredDimension[0];
                if (i == 0) {
                    height = measuredDimension[1];
                }
            } else {
                height = height + measuredDimension[1];
                if (i == 0) {
                    width = measuredDimension[0];
                }
            }
            EasyLog.d("item height i: " + i + "=" + measuredDimension[1]);
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
//        EasyLog.d("height=" + height);
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