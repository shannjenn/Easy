package com.jen.easyui.tabbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
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
abstract class EasyTabBarTopManager extends HorizontalScrollView {
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
    /*游标线性类型*/
    private final int INDICATOR_FIT_TXT = 0;
    /*游标块状类型*/
    private final int INDICATOR_FIT_WIDTH = 1;

    private int mHeight;
    private int mWidth;

    private int mIndicatorColor;
    /*游标：不设置时，默认为半圆角*/
    private float mIndicatorCornerRadius;
    private int mIndicatorType;
    private int mIndicatorFit;//游标宽度适配tab文字（INDICATOR_FIT_TXT）或者tab宽度（INDICATOR_FIT_WIDTH）
    private int mIndicatorPaddingLeft;//与indicatorFit属性一起使用的
    private int mIndicatorPaddingRight;//与indicatorFit属性一起使用的
    private float mIndicatorHeight;//高度只适合用与线条游标，方块游标高度是根据tab大小
    private float mIndicatorWidth;//宽度如果设置值，设置了indicatorFit属性的不适用

    private int mUnderlineColor;
    private float mUnderlineHeight;

    private float mTabTextsize;
    private int mTabWidth;//不设置时自动适应
    private int mTabHeith;//不设置是自动适应
    private int mTabPaddingLeft;
    private int mTabPaddingRight;
    private int mTabPaddingTop;
    private int mTabPaddingBottom;
    private int mTabSelectTextColor;
    private int mTabUnSelectTextColor;
    private boolean mTabTextBold;
    private boolean mTabTextItalic;
    private boolean mTabWeight;//tab加起来宽度不够mTabsContainer宽度时，平均分布

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


    public EasyTabBarTopManager(Context context) {
        this(context, null, 0);
    }

    public EasyTabBarTopManager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        initAttrs(attrs);
    }

    public EasyTabBarTopManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        setFillViewport(true);//填满横向
        setWillNotDraw(false);//重写onDraw方法,需要调用这个方法来清除flag
        setClipChildren(false);//是否限制子View在其范围内
        setClipToPadding(false);//控件的绘制区域是否在padding里面

        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.EasyTabBarTop);

        mHeight = ta.getLayoutDimension(R.styleable.EasyTabBarTop_android_layout_height, 0);
        mWidth = ta.getLayoutDimension(R.styleable.EasyTabBarTop_android_layout_width, 0);

        mIndicatorColor = ta.getColor(R.styleable.EasyTabBarTop_indicatorColor, INDICATOR_COLOR_DEFAULT);
        mIndicatorHeight = ta.getDimension(R.styleable.EasyTabBarTop_indicatorHeight, 0);
        mIndicatorWidth = ta.getDimension(R.styleable.EasyTabBarTop_indicatorWidth, 0);
        mIndicatorCornerRadius = ta.getDimension(R.styleable.EasyTabBarTop_indicatorCornerRadius, -1);
        mIndicatorType = ta.getInt(R.styleable.EasyTabBarTop_indicatorType, INDICATOR_TYPE_LINE);
        mIndicatorFit = ta.getInt(R.styleable.EasyTabBarTop_indicatorFit, INDICATOR_FIT_WIDTH);
        mIndicatorPaddingLeft = (int) ta.getDimension(R.styleable.EasyTabBarTop_indicatorPaddingLeft, 0);
        mIndicatorPaddingRight = (int) ta.getDimension(R.styleable.EasyTabBarTop_indicatorPaddingRight, 0);

        mUnderlineColor = ta.getColor(R.styleable.EasyTabBarTop_underlineColor, COLOR_DEFAULT);
        mUnderlineHeight = ta.getDimension(R.styleable.EasyTabBarTop_underlineHeight, 0);

        mTabTextsize = ta.getDimension(R.styleable.EasyTabBarTop_tabTextSize, EasyDensityUtil.sp2px(mContext, TEXT_SIZE_DEFAULT));
        mTabWidth = (int) ta.getDimension(R.styleable.EasyTabBarTop_tabWidth, -2f);//-2为WRAP_CONTENT属性
        mTabHeith = (int) ta.getDimension(R.styleable.EasyTabBarTop_tabHeight, -2f);
        mTabPaddingLeft = (int) ta.getDimension(R.styleable.EasyTabBarTop_tabPaddingLeft, 0);
        mTabPaddingRight = (int) ta.getDimension(R.styleable.EasyTabBarTop_tabPaddingRight, 0);
        mTabPaddingTop = (int) ta.getDimension(R.styleable.EasyTabBarTop_tabPaddingTop, 0);
        mTabPaddingBottom = (int) ta.getDimension(R.styleable.EasyTabBarTop_tabPaddingBottom, 0);
        mTabSelectTextColor = ta.getColor(R.styleable.EasyTabBarTop_tabSelectTextColor, TEXT_COLOR_SELECT_DEFAULT);
        mTabUnSelectTextColor = ta.getColor(R.styleable.EasyTabBarTop_tabUnSelectTextColor, TEXT_COLOR_UNSELECT_DEFAULT);
        mTabTextBold = ta.getBoolean(R.styleable.EasyTabBarTop_tabTextBold, false);
        mTabTextItalic = ta.getBoolean(R.styleable.EasyTabBarTop_tabTextItalic, false);
        mTabWeight = ta.getBoolean(R.styleable.EasyTabBarTop_tabWeight, false);

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
            if (mTabWeight) {
                params.weight = 1;
            }
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
    private void scrollTabToCenter() {
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
            if (mIndicatorWidth > 0) {
                left = textView.getX() + textView.getWidth() / 2 - mIndicatorWidth / 2;
                right = textView.getX() + textView.getWidth() / 2 + mIndicatorWidth / 2;
            } else if (mIndicatorFit == INDICATOR_FIT_TXT) {
                TextPaint paint = textView.getPaint();
                float txtWidth = paint.measureText(textView.getText().toString());

                left = textView.getX() + textView.getWidth() / 2 - txtWidth / 2 - mIndicatorPaddingLeft;
                right = textView.getX() + textView.getWidth() / 2 + txtWidth / 2 + mIndicatorPaddingRight;
            } else {
                left = textView.getX();
                right = textView.getX() + textView.getWidth();
            }
            top = textView.getY();
            bottom = textView.getHeight() + top;
        } else {
            float lfetOffset = mTabsContainer.getChildAt(mScrollPostion + 1).getX()
                    - mTabsContainer.getChildAt(mScrollPostion).getX();
            float rightOffset = lfetOffset + (mTabsContainer.getChildAt(mScrollPostion + 1).getWidth()
                    - mTabsContainer.getChildAt(mScrollPostion).getWidth());

            if (mIndicatorWidth > 0) {
                TextView nextTextView = (TextView) mTabsContainer.getChildAt(mScrollPostion + 1);
                TextView nowTextView = (TextView) mTabsContainer.getChildAt(mScrollPostion);
                float Offset = nextTextView.getX() + nextTextView.getWidth() / 2
                        - nowTextView.getX() - nowTextView.getWidth() / 2;

                left = textView.getX() + textView.getWidth() / 2 - mIndicatorWidth / 2
                        + Offset * mPositionOffset;
                right = textView.getX() + textView.getWidth() / 2 + mIndicatorWidth / 2
                        + Offset * mPositionOffset;
            } else if (mIndicatorFit == INDICATOR_FIT_TXT) {
                TextPaint paint = textView.getPaint();
                float txtWidth = paint.measureText(textView.getText().toString());

                left = textView.getX() + textView.getWidth() / 2 - txtWidth / 2
                        + lfetOffset * mPositionOffset - mIndicatorPaddingLeft;
                right = textView.getX() + textView.getWidth() / 2 + txtWidth / 2
                        + rightOffset * mPositionOffset + mIndicatorPaddingRight;
            } else {
                left = textView.getX() + lfetOffset * mPositionOffset;
                right = textView.getX() + textView.getWidth() + rightOffset * mPositionOffset;
            }
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
            scrollTabToCenter();
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
            scrollTabToCenter();
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