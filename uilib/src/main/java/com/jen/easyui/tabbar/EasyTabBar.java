package com.jen.easyui.tabbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.jen.easyui.R;

/**
 * 导航布局
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public class EasyTabBar extends RelativeLayout {
    private Context context;
    private final int HORIZONTAL = 0;
    private final int VERTICAL = 1;

    /*分割线默认颜色*/
    private final int LINE_DEFAULT_COLOR = 0xffE0E0E0;

    //分割线属性
    private int topLineColor;
    private int topLineSize;

    private int bottomLineColor;
    private int bottomLineSize;

    private int leftLineColor;
    private int leftLineSize;

    private int rightLineColor;
    private int rightLineSize;

    /*高度 LayoutParams.MATCH_PARENT=-1 LayoutParams.FILL_PARENT=-1 LayoutParams.WRAP_PARENT=-2*/
    int height;
    /*宽度 */
    int width;
    /*横向或者竖向 VERTICAL:1,HORIZONTAL:0 */
    int orientation;

    public EasyTabBar(Context context) {
        super(context);
        this.context = context;
    }

    public EasyTabBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs, 0);
    }

    public EasyTabBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs, defStyleAttr);
    }

    private void initAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.EasyTabLayout, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.EasyTabLayout_topLineColor) {
                topLineColor = a.getColor(attr, LINE_DEFAULT_COLOR);

            } else if (attr == R.styleable.EasyTabLayout_topLineSize) {
                topLineSize = a.getDimensionPixelOffset(attr, 0);

            } else if (attr == R.styleable.EasyTabLayout_bottomLineColor) {
                bottomLineColor = a.getColor(attr, LINE_DEFAULT_COLOR);

            } else if (attr == R.styleable.EasyTabLayout_bottomLineSize) {
                bottomLineSize = a.getDimensionPixelOffset(attr, 0);

            } else if (attr == R.styleable.EasyTabLayout_leftLineColor) {
                leftLineColor = a.getColor(attr, LINE_DEFAULT_COLOR);

            } else if (attr == R.styleable.EasyTabLayout_leftLineSize) {
                leftLineSize = a.getDimensionPixelOffset(attr, 0);

            } else if (attr == R.styleable.EasyTabLayout_rightLineColor) {
                rightLineColor = a.getColor(attr, LINE_DEFAULT_COLOR);

            } else if (attr == R.styleable.EasyTabLayout_rightLineSize) {
                rightLineSize = a.getDimensionPixelOffset(attr, 0);

            } else if (attr == R.styleable.EasyTabLayout_android_layout_height) {
                height = a.getLayoutDimension(attr, 0);

            } else if (attr == R.styleable.EasyTabLayout_android_layout_width) {
                width = a.getLayoutDimension(attr, 0);

            } else if (attr == R.styleable.EasyTabLayout_orientation) {
                orientation = a.getInt(attr, 0);

            }
        }
        a.recycle();
    }

    private void initView() {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout mainContainer = (RelativeLayout) mInflater.inflate(R.layout._easy_tabbar, null);


        if (orientation == HORIZONTAL) {
            if (leftLineSize > 0) {
                MarginLayoutParams params = new MarginLayoutParams(leftLineSize, LayoutParams.MATCH_PARENT);
                View leftLine = new View(getContext());
                leftLine.setBackgroundColor(leftLineColor);
                leftLine.setLayoutParams(params);
                addView(leftLine, 0);
            }
            if (rightLineSize > 0) {
                MarginLayoutParams params = new MarginLayoutParams(rightLineSize, LayoutParams.MATCH_PARENT);
                View rightLine = new View(getContext());
                rightLine.setBackgroundColor(rightLineColor);
                rightLine.setLayoutParams(params);
                addView(rightLine, getChildCount());
            }
            if (topLineSize > 0) {
                MarginLayoutParams params = new MarginLayoutParams(LayoutParams.MATCH_PARENT, topLineSize);
                View topLine = new View(getContext());
                topLine.setBackgroundColor(topLineColor);
                topLine.setLayoutParams(params);
                addView(topLine, 0);
            }
            if (bottomLineSize > 0) {
                MarginLayoutParams params = new MarginLayoutParams(LayoutParams.MATCH_PARENT, bottomLineSize);
                View bottomLine = new View(getContext());
                bottomLine.setBackgroundColor(bottomLineColor);
                bottomLine.setLayoutParams(params);
                addView(bottomLine, getChildCount());
            }
        } else {
            if (leftLineSize > 0) {
                MarginLayoutParams params = new MarginLayoutParams(LayoutParams.MATCH_PARENT, leftLineSize);
                View leftLine = new View(getContext());
                leftLine.setBackgroundColor(leftLineColor);
                leftLine.setLayoutParams(params);
                addView(leftLine, 0);
            }
            if (rightLineSize > 0) {
                MarginLayoutParams params = new MarginLayoutParams(LayoutParams.MATCH_PARENT, rightLineSize);
                View rightLine = new View(getContext());
                rightLine.setBackgroundColor(rightLineColor);
                rightLine.setLayoutParams(params);
                addView(rightLine, getChildCount());
            }
            if (topLineSize > 0) {
                MarginLayoutParams params = new MarginLayoutParams(topLineSize, LayoutParams.MATCH_PARENT);
                View topLine = new View(getContext());
                topLine.setBackgroundColor(topLineColor);
                topLine.setLayoutParams(params);
                addView(topLine, 0);
            }
            if (bottomLineSize > 0) {
                MarginLayoutParams params = new MarginLayoutParams(bottomLineSize, LayoutParams.MATCH_PARENT);
                View bottomLine = new View(getContext());
                bottomLine.setBackgroundColor(bottomLineColor);
                bottomLine.setLayoutParams(params);
                addView(bottomLine, getChildCount());
            }
        }
    }

}
