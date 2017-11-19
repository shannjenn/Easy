package com.jen.easyui.tabbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
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
abstract class EasyTabBarTopScrollManager extends HorizontalScrollView {
    private Context mContext;
    /*默认颜色*/
    private int COLOR_DEFAULT = 0xff000000;
    /*字体选中默认颜色*/
    private int TEXT_COLOR_SELECT_DEFAULT = 0xffffffff;
    /*字体没选中默认颜色*/
    private int TEXT_COLOR_UNSELECT_DEFAULT = 0xff6b6b6b;
    /*指示器默认颜色*/
    private int INDICATOR_COLOR_DEFAULT = 0xff00abff;
    /*默认字体大小sp*/
    private int TEXT_SIZE_DEFAULT = 16;
    /*游标线性类型*/
    private final int INDICATOR_TYPE_LINE = 0;
    /*游标块状类型*/
    private final int INDICATOR_TYPE_BLOCK = 1;

    private int mHeight;
    private int mWidth;

    private int mIndicatorColor;
    private float mIndicatorHeight;
    /*不设置时，默认为半圆角*/
    private float mIndicatorCornerRadius;
    //    private int mIndicatorGravity;
//    private float mIndicatorMarginBottom;
//    private float mIndicatorMarginTop;
    private int mIndicatorType;

    private int mUnderlineColor;
    private float mUnderlineHeight;

    private float mTabTextsize;
    private int mTabWidth;
    private int mTabHeith;
    private int mTabPaddingLeft;
    private int mTabPaddingRight;
    private int mTabPaddingTop;
    private int mTabPaddingBottom;
    private int mTabSelectTextColor;
    private int mTabUnSelectTextColor;
    private boolean mTabTextBold;
    private boolean mTabTextItalic;

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private LinearLayout mTabsContainer;
    private final List<String> mTitles = new ArrayList<>();
    /*是否带标题传入*/
    private boolean isWithTitles;
    /*当前选中的tab*/
    private int mScrollPostion;
    /*当前选中的tab*/
    private int mCurrentTab;
    /*tab数量*/
    private int mTabCount;
    /*偏移量百分比*/
    private float mPositionOffset;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mRect = new RectF();


    public EasyTabBarTopScrollManager(Context context) {
        this(context, null, 0);
    }

    public EasyTabBarTopScrollManager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        initAttrs(attrs);
    }

    public EasyTabBarTopScrollManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        setFillViewport(true);//填满横向
        setWillNotDraw(false);//重写onDraw方法,需要调用这个方法来清除flag
        setClipChildren(false);//是否限制子View在其范围内
        setClipToPadding(false);//控件的绘制区域是否在padding里面

        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.EasyTabBarTopScroll);

        mHeight = ta.getLayoutDimension(R.styleable.EasyTabBarTopScroll_android_layout_height, 0);
        mWidth = ta.getLayoutDimension(R.styleable.EasyTabBarTopScroll_android_layout_width, 0);

        mIndicatorColor = ta.getColor(R.styleable.EasyTabBarTopScroll_indicatorColor, INDICATOR_COLOR_DEFAULT);
        mIndicatorHeight = ta.getDimension(R.styleable.EasyTabBarTopScroll_indicatorSize, 0);
        mIndicatorCornerRadius = ta.getDimension(R.styleable.EasyTabBarTopScroll_indicatorCornerRadius, -1);
//        mIndicatorMarginTop = ta.getDimension(R.styleable.EasyTabBarTopScroll_indicator_margin_top, 0);
//        mIndicatorMarginBottom = ta.getDimension(R.styleable.EasyTabBarTopScroll_indicator_margin_bottom, 0);
//        mIndicatorGravity = ta.getInt(R.styleable.EasyTabBarTopScroll_indicator_gravity, Gravity.BOTTOM);
        mIndicatorType = ta.getInt(R.styleable.EasyTabBarTopScroll_indicatorType, INDICATOR_TYPE_LINE);

        mUnderlineColor = ta.getColor(R.styleable.EasyTabBarTopScroll_underlineColor, COLOR_DEFAULT);
        mUnderlineHeight = ta.getDimension(R.styleable.EasyTabBarTopScroll_underlineSize, 0);

        mTabTextsize = ta.getDimension(R.styleable.EasyTabBarTopScroll_tabTextSize, EasyDensityUtil.sp2px(mContext, TEXT_SIZE_DEFAULT));
        mTabWidth = (int) ta.getDimension(R.styleable.EasyTabBarTopScroll_tabWidth, -2f);//-2为WRAP_CONTENT属性
        mTabHeith = (int) ta.getDimension(R.styleable.EasyTabBarTopScroll_tabHeight, -2f);
        mTabPaddingLeft = (int) ta.getDimension(R.styleable.EasyTabBarTopScroll_tabPaddingLeft, 0);
        mTabPaddingRight = (int) ta.getDimension(R.styleable.EasyTabBarTopScroll_tabPaddingRight, 0);
        mTabPaddingTop = (int) ta.getDimension(R.styleable.EasyTabBarTopScroll_tabPaddingTop, 0);
        mTabPaddingBottom = (int) ta.getDimension(R.styleable.EasyTabBarTopScroll_tabPaddingBottom, 0);
        mTabSelectTextColor = ta.getColor(R.styleable.EasyTabBarTopScroll_tabSelectTextColor, TEXT_COLOR_SELECT_DEFAULT);
        mTabUnSelectTextColor = ta.getColor(R.styleable.EasyTabBarTopScroll_tabUnSelectTextColor, TEXT_COLOR_UNSELECT_DEFAULT);
        mTabTextBold = ta.getBoolean(R.styleable.EasyTabBarTopScroll_tabTextBold, false);
        mTabTextItalic = ta.getBoolean(R.styleable.EasyTabBarTopScroll_tabTextItalic, false);

        ta.recycle();

        removeAllViews();
        mTabsContainer = new LinearLayout(mContext);
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mTabsContainer, layoutParams);
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager == null) {
            EasyUILog.e("EasyTabBarTxtScroll setViewPager viewPager is null");
            return;
        }
        isWithTitles = false;
        mViewPager = viewPager;
        notifyDataSetChanged();
    }

    public void setViewPager(ViewPager viewPager, List<String> titles) {
        if (viewPager == null) {
            EasyUILog.e("EasyTabBarTxtScroll setViewPager viewPager is null");
            return;
        }
        isWithTitles = true;
        mViewPager = viewPager;
        if (titles != null) {
            mTitles.addAll(titles);
        } else {
            EasyUILog.e("EasyTabBarTxtScroll setViewPager titles is null");
        }
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        mAdapter = mViewPager.getAdapter();
        if (mAdapter == null) {
            Log.d("jen", "notifyDataSetChanged viewPager.getAdapter() is null");
            mTabCount = 0;
        } else {
            mTabCount = mAdapter.getCount();
        }

        if (!isWithTitles) {
            mTitles.clear();
        }
        int size = mTabCount - mTitles.size();
        for (int i = 0; i < size; i++) {//保证titles数量大于等于mTabCount
            String title = "";
            if (mAdapter != null) {
                CharSequence charSequence = mAdapter.getPageTitle(i);
                if (charSequence != null) {
                    title = charSequence.toString();
                }
            }
            mTitles.add(title);
        }
        mViewPager.addOnPageChangeListener(pageChangeListener);
        mCurrentTab = mViewPager.getCurrentItem();
        initTabViews();
    }

    private void initTabViews() {
        mTabsContainer.removeAllViews();
        for (int i = 0; i < mTabCount; i++) {
            TextView textView = new TextView(mContext);
            textView.setText(mTitles.get(i));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextsize);
            textView.setTextColor(i == mCurrentTab ? mTabSelectTextColor : mTabUnSelectTextColor);
            textView.setPadding(mTabPaddingLeft, mTabPaddingTop, mTabPaddingRight, mTabPaddingBottom);
            textView.setGravity(Gravity.CENTER);
            textView.getPaint().setFakeBoldText(mTabTextBold);//加粗
            textView.getPaint().setTextSkewX(mTabTextItalic ? -0.25f : 0);//斜体因子
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mTabWidth, mTabHeith);
            params.weight = 1;
            params.gravity = Gravity.CENTER;
            mTabsContainer.addView(textView, params);

            textView.setTag(i);
            textView.setOnClickListener(onClickListener);
        }
        invalidate();
    }

    private void updtaeTabText(int position) {
        for (int i = 0; i < mTabCount; i++) {
            TextView textView = (TextView) mTabsContainer.getChildAt(i);
            textView.setTextColor(i == position ? mTabSelectTextColor : mTabUnSelectTextColor);
        }
    }

    /**
     * 滚动到中间
     */
    private void scrollTabToCenter(boolean isOnClick) {
        if (mWidth <= 0) {//控件未初始化完成
            return;
        }

        int pos;
        if (mPositionOffset == 0) {//已经静止不做处理
            pos = mScrollPostion;
        } else if (mCurrentTab == mScrollPostion) {//（左滑）向右pos++
            pos = mScrollPostion + 1;
        } else {
            pos = mScrollPostion;
        }
        View newTv = mTabsContainer.getChildAt(pos);
        TextView scrollTv = (TextView) mTabsContainer.getChildAt(mScrollPostion);

        float left;
        if (mPositionOffset == 0) {
            left = scrollTv.getX();
        } else {
            float lfetOffset = mTabsContainer.getChildAt(mScrollPostion + 1).getX()
                    - mTabsContainer.getChildAt(mScrollPostion).getX();
            left = scrollTv.getX() + lfetOffset * mPositionOffset;
        }

        int x = (int) (left + newTv.getWidth() / 2 - mWidth / 2);
        scrollTo(x, 0);
    }

    /**
     * 如果超出屏幕滚动一个tab
     */
    private void scrollTabIfEdge(boolean isOnClick) {
        if (mWidth <= 0) {//控件未初始化完成
            return;
        }
        if (!isOnClick && mPositionOffset == 0) {//已经静止不做处理
            return;
        }

        if (isOnClick) {
            int pos = mCurrentTab;

            int scrollX = getScrollX();
            View newTv = mTabsContainer.getChildAt(pos);
            float leftX = newTv.getX();
            float width = newTv.getWidth();

            if (leftX + width >= scrollX + mWidth) {
                int x = (int) (leftX + width - mWidth);
                scrollTo(x, 0);
            } else if (leftX <= scrollX) {
                int x = (int) (leftX + width / 2);
                scrollTo(x, 0);
            }
            return;
        }

        boolean isToRight = false;
        int scrollX = getScrollX();

        if (mCurrentTab == mScrollPostion) {
            isToRight = true;
        } else if (mScrollPostion + 1 < mTabCount) {
            TextView scrollTv = (TextView) mTabsContainer.getChildAt(mScrollPostion + 1);
            if (scrollTv.getX() + scrollTv.getWidth() >= scrollX + mWidth) {
                isToRight = true;
            }
        }


        if (isToRight) {//（左滑）向右pos++
            EasyUILog.d("----------------右");
            int pos = mScrollPostion + 1;

            /*int scrollX = getScrollX();
            if (newTv.getX() > scrollX && newTv.getX() - scrollX < mWidth + newTv.getWidth()) {
                return;
            }*/

//            View posPlusView = mTabsContainer.getChildAt(pos + 1);
            View newTv = mTabsContainer.getChildAt(pos);
//            newTv.getX() > scrollX ||
            if (newTv.getX() + newTv.getWidth() <= scrollX + mWidth) {
                return;
            }

            TextView scrollTv = (TextView) mTabsContainer.getChildAt(mScrollPostion);
            float lfetOffset = mTabsContainer.getChildAt(mScrollPostion + 1).getX()
                    - mTabsContainer.getChildAt(mScrollPostion).getX();
            float left = scrollTv.getX() + lfetOffset * mPositionOffset;

            int x = (int) (left) + newTv.getWidth() /*+ posPlusView.getWidth()*/ - mWidth;
            scrollTo(x, 0);
        } else {//（右滑）向左pos--
            EasyUILog.d("左----------------");
            int pos = mScrollPostion;

//            View posMinusView = mTabsContainer.getChildAt(pos - 1);
            View newTv = mTabsContainer.getChildAt(pos);
//            newTv.getX() > scrollX ||
            if (newTv.getX() >= scrollX) {
                return;
            }

            TextView scrollTv = (TextView) mTabsContainer.getChildAt(mScrollPostion);
            float lfetOffset = mTabsContainer.getChildAt(mScrollPostion + 1).getX()
                    - mTabsContainer.getChildAt(mScrollPostion).getX();
            float left = scrollTv.getX() + lfetOffset * mPositionOffset;

            int x = (int) (left)/* - posMinusView.getWidth() - newTv.getScrollX()*/;
            scrollTo(x, 0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mWidth <= 0) {
            mWidth = getWidth();
        }
        if (mHeight <= 0) {
            mHeight = getHeight();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

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
        //如果在自定义控件的构造函数或者其他绘制相关地方使用系统依赖的代码，会导致可视化编辑器无法报错并提
        if (isInEditMode()) {
            EasyUILog.w("onDraw isInEditMode");
            return;
        }
        drawUnderline(canvas);
        drawIndicatorRoundRect(canvas);
    }

    /**
     * 画圆角矩形游标
     *
     * @param canvas
     */
    private void drawIndicatorRoundRect(Canvas canvas) {
        if (mTabCount <= 0) {
            EasyUILog.e("drawIndicatorRoundRect mTabCount=" + mTabCount);
            return;
        }
        TextView textView = (TextView) mTabsContainer.getChildAt(mScrollPostion);
        float left;
        float right;
        float top;
        float bottom;
        if (mPositionOffset == 0) {
            left = textView.getX();
            right = textView.getX() + textView.getWidth();
            top = textView.getY();
            bottom = textView.getHeight() + top;
        } else {
            float lfetOffset = mTabsContainer.getChildAt(mScrollPostion + 1).getX()
                    - mTabsContainer.getChildAt(mScrollPostion).getX();
            float rightOffset = lfetOffset + (mTabsContainer.getChildAt(mScrollPostion + 1).getWidth()
                    - mTabsContainer.getChildAt(mScrollPostion).getWidth());

            left = textView.getX() + lfetOffset * mPositionOffset;
            right = textView.getX() + textView.getWidth() + rightOffset * mPositionOffset;
            top = textView.getY();
            bottom = textView.getHeight() + top;
        }

        mPaint.setColor(mIndicatorColor);
        mRect.left = left;
        mRect.right = right;
        mRect.top = mIndicatorType == INDICATOR_TYPE_BLOCK ? top : mHeight - mIndicatorHeight;
        mRect.bottom = mIndicatorType == INDICATOR_TYPE_BLOCK ? bottom : mHeight;
        if (mIndicatorCornerRadius == -1) {
            mIndicatorCornerRadius = (mRect.bottom - mRect.top) / 2;
        }
        canvas.drawRoundRect(mRect, mIndicatorCornerRadius, mIndicatorCornerRadius, mPaint);
    }

    private void drawUnderline(Canvas canvas) {
        if (mUnderlineHeight <= 0) {
            return;
        }
        mPaint.setColor(mUnderlineColor);
        mRect.left = 0;
        mRect.right = mWidth + getScrollX();
        mRect.top = mHeight - mUnderlineHeight;
        mRect.bottom = mHeight;

        canvas.drawRoundRect(mRect, 5, 5, mPaint);
    }

    /*@Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putInt("mCurrentTab", mCurrentTab);
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentTab = bundle.getInt("mCurrentTab");
            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }*/

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mCurrentTab = (int) v.getTag();
            mViewPager.setCurrentItem(mCurrentTab);
            updtaeTabText(mCurrentTab);
            scrollTabToCenter(true);
        }
    };


    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        /**
         * @param position 位置
         * @param positionOffset 偏移百分比
         * @param positionOffsetPixels 偏移像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            EasyUILog.d("onPageScrolled position=" + position
//                    + " positionOffset=" + positionOffset + " positionOffsetPixels=" + positionOffsetPixels);
            mScrollPostion = position;
            mPositionOffset = positionOffset;
            scrollTabToCenter(false);
            invalidate();
        }

        @Override
        public void onPageSelected(int position) {
//            EasyUILog.d("onPageSelected position=" + position);
            updtaeTabText(position);
//            mCurrentTab = position;
        }

        /**
         * @param state 1(正在滑动)，2（滑动完毕，还在滑），0（停止）
         */
        @Override
        public void onPageScrollStateChanged(int state) {
//            EasyUILog.d("onPageScrollStateChanged state=" + state);
            if (state == 0) {
                mCurrentTab = mScrollPostion;
            }
        }
    };
}