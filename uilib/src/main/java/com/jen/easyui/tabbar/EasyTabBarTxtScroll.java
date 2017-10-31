package com.jen.easyui.tabbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jen.easy.log.EasyUILog;
import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航布局,说明：文字导航栏，滑动游标效果、标题可以滑动
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * 作者：ShannJenn
 * 时间：2017/10/31.
 */
public class EasyTabBarTxtScroll extends HorizontalScrollView {
    private Context mContext;
    /*默认颜色*/
    private int COLOR_DEFAULT = 0xff000000;
    /*字体选中默认颜色*/
    private int TEXT_COLOR_SELECT_DEFAULT = 0xff0000ff;
    /*字体没选中默认颜色*/
    private int TEXT_COLOR_UNSELECT_DEFAULT = 0xff000000;
    /*指示器默认颜色*/
    private int INDICATOR_COLOR_DEFAULT = 0xffff0000;
    /*默认字体大小sp*/
    private int TEXT_SIZE_DEFAULT = 16;

    private int mHeight;
    private int mWidth;

    private int mIndicatorColor;
    private float mIndicatorHeight;
    private float mIndicatorWidth;
    private float mIndicatorCornerRadius;
    private float mIndicatorMarginTop;
    private float mIndicatorMarginBottom;
    private int mIndicatorGravity;

    private int mUnderlineColor;
    private float mUnderlineHeight;
    private int mUnderlineGravity;

    private float mTextsize;
    private int mTextSelectColor;
    private int mTextUnselectColor;
    private boolean mTextStyleBold;
    private boolean mTextStyleItalic;

    private ViewPager mViewPager;
    /*当前选中的tab*/
    private int mCurrentTab;
    /*tab数量*/
    private int mTabCount;
    /*最后一次滚动的X轴位置*/
    private int mLastScrollX;
    private final List<String> mTitles = new ArrayList<>();
    /*偏移量*/
    private float mCurrentPositionOffset;
    private LinearLayout mTabsContainer;

    /**
     * 用于绘制显示器
     */
    private Rect mIndicatorRect = new Rect();
    /**
     * 用于实现滚动居中
     */
    private Rect mTabRect = new Rect();
    private GradientDrawable mIndicatorDrawable = new GradientDrawable();

    private Paint mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mTrianglePath = new Path();


    public EasyTabBarTxtScroll(Context context) {
        this(context, null, 0);
    }

    public EasyTabBarTxtScroll(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        initAttrs(attrs);
    }

    public EasyTabBarTxtScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        setFillViewport(true);//填满横向
        setWillNotDraw(false);//重写onDraw方法,需要调用这个方法来清除flag
        setClipChildren(false);//是否限制子View在其范围内
        setClipToPadding(false);//控件的绘制区域是否在padding里面

        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.EasyTabBarTxtScroll);

        mHeight = ta.getLayoutDimension(R.styleable.EasyTabBarTxtImg_android_layout_height, 0);
        mWidth = ta.getLayoutDimension(R.styleable.EasyTabBarTxtImg_android_layout_width, 0);

        mIndicatorColor = ta.getColor(R.styleable.EasyTabBarTxtScroll_indicator_color, INDICATOR_COLOR_DEFAULT);
        mIndicatorHeight = ta.getDimension(R.styleable.EasyTabBarTxtScroll_indicator_height, 30);
        mIndicatorWidth = ta.getDimension(R.styleable.EasyTabBarTxtScroll_indicator_width, 100);
        mIndicatorCornerRadius = ta.getDimension(R.styleable.EasyTabBarTxtScroll_indicator_corner_radius, 0);
        mIndicatorMarginTop = ta.getDimension(R.styleable.EasyTabBarTxtScroll_indicator_margin_top, 0);
        mIndicatorMarginBottom = ta.getDimension(R.styleable.EasyTabBarTxtScroll_indicator_margin_bottom, 0);
        mIndicatorGravity = ta.getInt(R.styleable.EasyTabBarTxtScroll_indicator_gravity, Gravity.BOTTOM);

        mUnderlineColor = ta.getColor(R.styleable.EasyTabBarTxtScroll_underline_color, COLOR_DEFAULT);
        mUnderlineHeight = ta.getDimension(R.styleable.EasyTabBarTxtScroll_underline_height, 0);
        mUnderlineGravity = ta.getInt(R.styleable.EasyTabBarTxtScroll_underline_gravity, Gravity.BOTTOM);

        mTextsize = ta.getDimension(R.styleable.EasyTabBarTxtScroll_textsize, EasyDensityUtil.sp2px(mContext, TEXT_SIZE_DEFAULT));
        mTextSelectColor = ta.getColor(R.styleable.EasyTabBarTxtScroll_textSelColor, TEXT_COLOR_SELECT_DEFAULT);
        mTextUnselectColor = ta.getColor(R.styleable.EasyTabBarTxtScroll_textUnselColor, TEXT_COLOR_UNSELECT_DEFAULT);
        mTextStyleBold = ta.getBoolean(R.styleable.EasyTabBarTxtScroll_textStyleBold, false);
        mTextStyleItalic = ta.getBoolean(R.styleable.EasyTabBarTxtScroll_textStyleItalic, false);

        ta.recycle();
    }

    public void setViewPager(ViewPager viewPager) {
        setViewPager(viewPager, null);
    }

    public void setViewPager(ViewPager viewPager, List<String> titles) {
        if (viewPager == null || viewPager.getAdapter() == null) {
            EasyUILog.e("EasyTabBarTxtScroll setViewPager viewPager is null or viewPager.getAdapter() is null");
            return;
        }
        mTabCount = viewPager.getAdapter().getCount();
        if (mTabCount <= 0) {
            EasyUILog.e("EasyTabBarTxtScroll initViews ChildCount is 0");
            return;
        }
        if (titles != null && mTabCount != titles.size()) {//保证titles数量与mTabCount一致
            EasyUILog.e("viewPager.getChildCount() != titles.size()");
            return;
        }
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(pageChangeListener);
        mViewPager.addOnAdapterChangeListener(onAdapterChangeListener);
        if (mViewPager.getAdapter() != null) {
            mViewPager.getAdapter().registerDataSetObserver(dataSetObserver);
        }
        if (titles == null) {
            titles = new ArrayList<>();
            for (int i = 0; i < mTabCount; i++) {
                String title = "";
                CharSequence charSequence = mViewPager.getAdapter().getPageTitle(i);
                if (charSequence != null) {
                    title = charSequence.toString();
                }
                titles.add(title);
            }
        }
        mTitles.clear();
        mTitles.addAll(titles);
        initViews();
    }

    private void initViews() {
        mTabsContainer = new LinearLayout(mContext);
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mTabsContainer, layoutParams);
        for (int i = 0; i < mTabCount; i++) {
            TextView textView = new TextView(mContext);
            textView.setText(mTitles.get(i));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextsize);
            textView.setTextColor(mTextUnselectColor);
            textView.getPaint().setFakeBoldText(mTextStyleBold);//加粗
            textView.getPaint().setTextSkewX(mTextStyleItalic ? -0.25f : 0);//斜体因子
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            mTabsContainer.addView(textView, params);
        }
        invalidate();
    }


    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int lastPos = mCurrentTab;
            mCurrentPositionOffset = positionOffset;
            mCurrentTab = position;
            scrollToCurrentTab(lastPos,position);
//            invalidate();
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    ViewPager.OnAdapterChangeListener onAdapterChangeListener = new ViewPager.OnAdapterChangeListener() {
        @Override
        public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {

        }
    };

    DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    };

    /**
     * HorizontalScrollView滚到当前tab,并且居中显示
     */
    private void scrollToCurrentTab(int lastPos,int newPos) {
        TextView lastTv = (TextView) mTabsContainer.getChildAt(lastPos);
        lastTv.setTextColor(mTextUnselectColor);
        TextView newTv = (TextView) mTabsContainer.getChildAt(newPos);
        newTv.setTextColor(mTextSelectColor);

        int offset = (int) (mCurrentPositionOffset * mTabsContainer.getChildAt(newPos).getWidth());
        int newScrollX = mTabsContainer.getChildAt(newPos).getLeft() + offset;

        if (newPos > 0 || offset > 0) {
            newScrollX -= getWidth() / 2 - getPaddingLeft();
//            calcIndicatorRect();
            newScrollX += ((mTabRect.right - mTabRect.left) / 2);
        }

        if (newScrollX != mLastScrollX) {
            mLastScrollX = newScrollX;
            /** scrollTo（int x,int y）:x,y代表的不是坐标点,而是偏移量
             *  x:表示离起始位置的x水平方向的偏移量
             *  y:表示离起始位置的y垂直方向的偏移量
             */
            scrollTo(newScrollX, 0);
        }
    }

    private void updateTabSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            TextView tabView = (TextView) mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;
            tabView.setTextColor(isSelect ? mTextSelectColor : mTextUnselectColor);
        }
    }

    /*private void calcIndicatorRect() {
        View currentTabView = mTabsContainer.getChildAt(this.mCurrentTab);
        float left = currentTabView.getLeft();
        float right = currentTabView.getRight();

        //for mIndicatorWidthEqualTitle
        if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
            TextView tab_title = (TextView) currentTabView.findViewById(R.id.tv_tab_title);
            mTextPaint.setTextSize(mTextsize);
            float textWidth = mTextPaint.measureText(tab_title.getText().toString());
            margin = (right - left - textWidth) / 2;
        }

        if (this.mCurrentTab < mTabCount - 1) {
            View nextTabView = mTabsContainer.getChildAt(this.mCurrentTab + 1);
            float nextTabLeft = nextTabView.getLeft();
            float nextTabRight = nextTabView.getRight();

            left = left + mCurrentPositionOffset * (nextTabLeft - left);
            right = right + mCurrentPositionOffset * (nextTabRight - right);

            //for mIndicatorWidthEqualTitle
            if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
                TextView next_tab_title = (TextView) nextTabView.findViewById(R.id.tv_tab_title);
                mTextPaint.setTextSize(mTextsize);
                float nextTextWidth = mTextPaint.measureText(next_tab_title.getText().toString());
                float nextMargin = (nextTabRight - nextTabLeft - nextTextWidth) / 2;
                margin = margin + mCurrentPositionOffset * (nextMargin - margin);
            }
        }

        mIndicatorRect.left = (int) left;
        mIndicatorRect.right = (int) right;
        //for mIndicatorWidthEqualTitle
        if (mIndicatorStyle == STYLE_NORMAL && mIndicatorWidthEqualTitle) {
            mIndicatorRect.left = (int) (left + margin - 1);
            mIndicatorRect.right = (int) (right - margin - 1);
        }

        mTabRect.left = (int) left;
        mTabRect.right = (int) right;

        if (mIndicatorWidth < 0) {   //indicatorWidth小于0时,原jpardogo's PagerSlidingTabStrip

        } else {//indicatorWidth大于0时,圆角矩形以及三角形
            float indicatorLeft = currentTabView.getLeft() + (currentTabView.getWidth() - mIndicatorWidth) / 2;

            if (this.mCurrentTab < mTabCount - 1) {
                View nextTab = mTabsContainer.getChildAt(this.mCurrentTab + 1);
                indicatorLeft = indicatorLeft + mCurrentPositionOffset * (currentTabView.getWidth() / 2 + nextTab.getWidth() / 2);
            }

            mIndicatorRect.left = (int) indicatorLeft;
            mIndicatorRect.right = (int) (mIndicatorRect.left + mIndicatorWidth);
        }
    }*/


    /**
     * 自己理解
     * canvas：画布，左上角为0,0坐标
     * Paint:画笔
     * Rect ：矩形,左、上、右，下（坐标）
     * GradientDrawable：图形，类似于ShareDrawable
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || mTabCount <= 0) {
            return;
        }

        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        // draw divider
        /*if (mDividerWidth > 0) {
            mDividerPaint.setStrokeWidth(mDividerWidth);
            mDividerPaint.setColor(mDividerColor);
            for (int i = 0; i < mTabCount - 1; i++) {
                View tab = mTabsContainer.getChildAt(i);
                canvas.drawLine(paddingLeft + tab.getRight(), mDividerPadding, paddingLeft + tab.getRight(), height - mDividerPadding, mDividerPaint);
            }
        }*/

        // draw underline
        /*if (mUnderlineHeight > 0) {
            mRectPaint.setColor(mUnderlineColor);
            if (mUnderlineGravity == Gravity.BOTTOM) {
                canvas.drawRect(paddingLeft, height - mUnderlineHeight, mTabsContainer.getWidth() + paddingLeft, height, mRectPaint);
            } else {
                canvas.drawRect(paddingLeft, 0, mTabsContainer.getWidth() + paddingLeft, mUnderlineHeight, mRectPaint);
            }
        }*/

        if (mIndicatorHeight < 0) {
            mIndicatorHeight = height - mIndicatorMarginTop - mIndicatorMarginBottom;
        } else {

        }

        if (mIndicatorHeight > 0) {
            if (mIndicatorCornerRadius < 0 || mIndicatorCornerRadius > mIndicatorHeight / 2) {
                mIndicatorCornerRadius = mIndicatorHeight / 2;
            }

            mIndicatorDrawable.setColor(mIndicatorColor);
            mIndicatorDrawable.setBounds(paddingLeft + mIndicatorRect.left,
                    (int) mIndicatorMarginTop, (int) (paddingLeft + mIndicatorRect.right),
                    (int) (mIndicatorMarginTop + mIndicatorHeight));
            mIndicatorDrawable.setCornerRadius(mIndicatorCornerRadius);

            mIndicatorDrawable.setColor(0xff00ff00);
            mIndicatorDrawable.setBounds(0,0,300,50);
            mIndicatorDrawable.setCornerRadius(10);
            mIndicatorDrawable.draw(canvas);
        }


/*
        //draw indicator line

//        calcIndicatorRect();
        if (mIndicatorStyle == STYLE_TRIANGLE) {
            if (mIndicatorHeight > 0) {
                mTrianglePaint.setColor(mIndicatorColor);
                mTrianglePath.reset();
                mTrianglePath.moveTo(paddingLeft + mIndicatorRect.left, height);
                mTrianglePath.lineTo(paddingLeft + mIndicatorRect.left / 2 + mIndicatorRect.right / 2, height - mIndicatorHeight);
                mTrianglePath.lineTo(paddingLeft + mIndicatorRect.right, height);
                mTrianglePath.close();
                canvas.drawPath(mTrianglePath, mTrianglePaint);
            }
        } else if (mIndicatorStyle == STYLE_BLOCK) {
            if (mIndicatorHeight < 0) {
                mIndicatorHeight = height - mIndicatorMarginTop - mIndicatorMarginBottom;
            } else {

            }

            if (mIndicatorHeight > 0) {
                if (mIndicatorCornerRadius < 0 || mIndicatorCornerRadius > mIndicatorHeight / 2) {
                    mIndicatorCornerRadius = mIndicatorHeight / 2;
                }

                mIndicatorDrawable.setColor(mIndicatorColor);
                mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                        (int) mIndicatorMarginTop, (int) (paddingLeft + mIndicatorRect.right - mIndicatorMarginRight),
                        (int) (mIndicatorMarginTop + mIndicatorHeight));
                mIndicatorDrawable.setCornerRadius(mIndicatorCornerRadius);
                mIndicatorDrawable.draw(canvas);
            }
        } else {
               *//* mRectPaint.setColor(mIndicatorColor);
                calcIndicatorRect();
                canvas.drawRect(getPaddingLeft() + mIndicatorRect.left, getHeight() - mIndicatorHeight,
                        mIndicatorRect.right + getPaddingLeft(), getHeight(), mRectPaint);*//*

            if (mIndicatorHeight > 0) {
                mIndicatorDrawable.setColor(mIndicatorColor);

                if (mIndicatorGravity == Gravity.BOTTOM) {
                    mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                            height - (int) mIndicatorHeight - (int) mIndicatorMarginBottom,
                            paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight,
                            height - (int) mIndicatorMarginBottom);
                } else {
                    mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                            (int) mIndicatorMarginTop,
                            paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight,
                            (int) mIndicatorHeight + (int) mIndicatorMarginTop);
                }
                mIndicatorDrawable.setCornerRadius(mIndicatorCornerRadius);
                mIndicatorDrawable.draw(canvas);
            }
        }*/
    }

    private void drawRoundRect(Canvas canvas){
//        canvas.
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("mCurrentTab", mCurrentTab);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentTab = bundle.getInt("mCurrentTab");
            state = bundle.getParcelable("instanceState");
            if (mCurrentTab != 0 && mTabsContainer.getChildCount() > 0) {
                updateTabSelection(mCurrentTab);
//                scrollToCurrentTab();
            }
        }
        super.onRestoreInstanceState(state);
    }

}