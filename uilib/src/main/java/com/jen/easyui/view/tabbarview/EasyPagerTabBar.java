package com.jen.easyui.view.tabbarview;

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

import com.jen.easyui.R;
import com.jen.easyui.util.DensityUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 导航布局,说明：文字导航栏，滑动游标效果、标题可以滑动
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * 作者：ShannJenn
 * 时间：2017/10/31.
 */
public class EasyPagerTabBar extends HorizontalScrollView {
    private final String TAG = EasyPagerTabBar.class.getSimpleName();
    private Context mContext;

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

    private float mTabTextSize;
    private float mTabTextSelectSize;
    private int mTabWidth;//不设置时自动适应
    private int mTabHeight;//不设置是自动适应
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
    private LinearLayout mTabsContainer;
    private final List<String> mTitles = new ArrayList<>();
    /*当前选中的tab*/
    private int mFromPosition;
    /*偏移量百分比*/
    private float mPositionOffset;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mRect = new RectF();


    public EasyPagerTabBar(Context context) {
        this(context, null, 0);
    }

    public EasyPagerTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        initAttrs(attrs);
    }

    public EasyPagerTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        setFillViewport(true);//填满横向
        setWillNotDraw(false);//重写onDraw方法,需要调用这个方法来清除flag
        setClipChildren(false);//是否限制子View在其范围内
        setClipToPadding(false);//控件的绘制区域是否在padding里面

        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.EasyPagerTabBar);

        mHeight = ta.getLayoutDimension(R.styleable.EasyPagerTabBar_android_layout_height, 0);
        mWidth = ta.getLayoutDimension(R.styleable.EasyPagerTabBar_android_layout_width, 0);

        /*指示器默认颜色*/
        int INDICATOR_COLOR_DEFAULT = 0xff0000ff;
        mIndicatorColor = ta.getColor(R.styleable.EasyPagerTabBar_tabBarIndicatorColor, INDICATOR_COLOR_DEFAULT);
        mIndicatorHeight = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarIndicatorHeight, 0);
        mIndicatorWidth = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarIndicatorWidth, 0);
        mIndicatorCornerRadius = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarIndicatorCornerRadius, -1);
        /*游标线性类型*/
        int INDICATOR_TYPE_LINE = 0;
        mIndicatorType = ta.getInt(R.styleable.EasyPagerTabBar_tabBarIndicatorType, INDICATOR_TYPE_LINE);
        /*游标块状类型*/
        int INDICATOR_FIT_WIDTH = 1;
        mIndicatorFit = ta.getInt(R.styleable.EasyPagerTabBar_tabBarIndicatorFit, INDICATOR_FIT_WIDTH);
        mIndicatorPaddingLeft = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarIndicatorPaddingLeft, 0);
        mIndicatorPaddingRight = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarIndicatorPaddingRight, 0);

        /*底部线条默认颜色*/
        int COLOR_DEFAULT = 0xff000000;
        mUnderlineColor = ta.getColor(R.styleable.EasyPagerTabBar_tabBarUnderlineColor, COLOR_DEFAULT);
        mUnderlineHeight = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarUnderlineHeight, 0);

        /*默认字体大小sp*/
        int TEXT_SIZE_DEFAULT = 16;
        String tabBarTextList = ta.getString(R.styleable.EasyPagerTabBar_tabBarTextList);
        mTabTextSize = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarTextSize, DensityUtil.sp2pxInt(TEXT_SIZE_DEFAULT));
        mTabTextSelectSize = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarTextSelectSize, DensityUtil.sp2pxInt(TEXT_SIZE_DEFAULT));
        mTabWidth = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarTabWidth, -2);//-2为WRAP_CONTENT属性
        mTabHeight = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarTabHeight, -2);
        mTabPaddingLeft = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarTabPaddingLeft, 0);
        mTabPaddingRight = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarTabPaddingRight, 0);
        mTabPaddingTop = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarTabPaddingTop, 0);
        mTabPaddingBottom = ta.getDimensionPixelOffset(R.styleable.EasyPagerTabBar_tabBarTabPaddingBottom, 0);
        /*字体选中默认颜色*/
        int TEXT_COLOR_SELECT_DEFAULT = 0xff000000;
        mTabSelectTextColor = ta.getColor(R.styleable.EasyPagerTabBar_tabBarTextSelectColor, TEXT_COLOR_SELECT_DEFAULT);
        /*字体没选中默认颜色*/
        int TEXT_COLOR_UNSELECT_DEFAULT = 0xff666666;
        mTabUnSelectTextColor = ta.getColor(R.styleable.EasyPagerTabBar_tabBarTextUnSelectColor, TEXT_COLOR_UNSELECT_DEFAULT);
        mTabTextBold = ta.getBoolean(R.styleable.EasyPagerTabBar_tabBarTextBold, false);
        mTabTextItalic = ta.getBoolean(R.styleable.EasyPagerTabBar_tabBarTextItalic, false);
        mTabWeight = ta.getBoolean(R.styleable.EasyPagerTabBar_tabBarTabAverage, false);

        ta.recycle();

        String[] titles;
        if (tabBarTextList != null) {
            titles = tabBarTextList.split(",");
        } else {
            titles = new String[]{"标题1", "标题2", "标题3", "标题4", "标题5"};
        }
        mTitles.clear();
        mTitles.addAll(Arrays.asList(titles));
        removeAllViews();
        mTabsContainer = new LinearLayout(mContext);
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mTabsContainer, layoutParams);
        initTabViews();
    }

    public void updateTitle(String[] titles) {
        mTitles.clear();
        mTitles.addAll(Arrays.asList(titles));
        initTabViews();
    }

    public void updateTitle(List<String> titles) {
        mTitles.clear();
        mTitles.addAll(titles);
        initTabViews();
    }

    public void setViewPager(ViewPager viewPager) {
        setViewPager(viewPager, false);
    }

    public void setViewPager(ViewPager viewPager, boolean useAdapterTitle) {
        if (viewPager == null) {
            Log.w(TAG, "EasyTabBarTxtScroll setViewPager viewPager is null");
            return;
        }
        PagerAdapter mAdapter = viewPager.getAdapter();
        if (mAdapter == null) {
            Log.d("jen", "notifyDataSetChanged viewPager.getAdapter() is null");
            return;
        }
        if (useAdapterTitle) {
            List<String> titles = new ArrayList<>();
            int size = mAdapter.getCount();
            for (int i = 0; i < size; i++) {
                String title = "";
                CharSequence charSequence = mAdapter.getPageTitle(i);
                if (charSequence != null) {
                    title = charSequence.toString();
                }
                titles.add(title);
            }
            mTitles.clear();
            mTitles.addAll(titles);
        }
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(pageChangeListener);
        initTabViews();
    }

    public void setViewPager(ViewPager viewPager, List<String> titles) {
        if (viewPager == null) {
            Log.w(TAG, "EasyTabBarTxtScroll setViewPager viewPager is null");
            return;
        }
        if (titles != null && titles.size() > 0) {
            mTitles.clear();
            mTitles.addAll(titles);
        } else {
            Log.w(TAG, "EasyTabBarTxtScroll setViewPager titles is null");
            return;
        }
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(pageChangeListener);
        initTabViews();
    }

    private void initTabViews() {
        int currentPosition = 0;
        if (mViewPager != null) {
            currentPosition = mViewPager.getCurrentItem();
        }
        mTabsContainer.removeAllViews();
        for (int i = 0; i < mTitles.size(); i++) {
            TextView textView = new TextView(mContext);
            textView.setText(mTitles.get(i));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, i == currentPosition ? mTabTextSelectSize : mTabTextSize);
            textView.setTextColor(i == currentPosition ? mTabSelectTextColor : mTabUnSelectTextColor);
            textView.setPadding(mTabPaddingLeft, mTabPaddingTop, mTabPaddingRight, mTabPaddingBottom);
            textView.setGravity(Gravity.CENTER);
            textView.getPaint().setFakeBoldText(mTabTextBold);//加粗
            textView.getPaint().setTextSkewX(mTabTextItalic ? -0.25f : 0);//斜体因子
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mTabWidth, mTabHeight);
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

    private void updateTabText(int position) {
        for (int i = 0; i < mTitles.size(); i++) {
            TextView textView = (TextView) mTabsContainer.getChildAt(i);
            textView.setTextColor(i == position ? mTabSelectTextColor : mTabUnSelectTextColor);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, i == position ? mTabTextSelectSize : mTabTextSize);
        }
    }

    /**
     * 滚动到中间
     */
    private void scrollTabToCenter() {
        if (mWidth <= 0) {//控件未初始化完成
            return;
        }
        TextView fromView = (TextView) mTabsContainer.getChildAt(mFromPosition);
        View currentView = mTabsContainer.getChildAt(mViewPager.getCurrentItem());

        float left;
        if (mPositionOffset == 0) {
            left = fromView.getX();
        } else {
            float leftOffset = (currentView.getX() - fromView.getX()) * mPositionOffset;
            left = fromView.getX() + leftOffset;
        }
        int x = (int) (left + currentView.getWidth() / 2 - mWidth / 2);
        scrollTo(x, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mWidth <= 0) {
            mWidth = getMeasuredWidth();
        }
        if (mHeight <= 0) {
            mHeight = getMeasuredHeight();
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
        drawUnderline(canvas);
        drawIndicatorRoundRect(canvas);
    }

    /**
     * 画圆角矩形游标
     *
     * @param canvas .
     */
    private void drawIndicatorRoundRect(Canvas canvas) {
        if (mTitles.size() <= 0) {
            Log.w(TAG, "drawIndicatorRoundRect mTitles.size()=" + mTitles.size());
            return;
        }
        TextView textView = (TextView) mTabsContainer.getChildAt(mFromPosition);
        float left;
        float right;
        float top;
        float bottom;
        /*游标线性类型*/
        int INDICATOR_FIT_TXT = 0;
        if (mPositionOffset == 0) {
            if (mIndicatorWidth > 0) {
                left = textView.getX() + (textView.getWidth() >> 1) - mIndicatorWidth / 2;
                right = textView.getX() + (textView.getWidth() >> 1) + mIndicatorWidth / 2;
            } else if (mIndicatorFit == INDICATOR_FIT_TXT) {
                TextPaint paint = textView.getPaint();
                float txtWidth = paint.measureText(textView.getText().toString());

                left = textView.getX() + (textView.getWidth() >> 1) - txtWidth / 2 - mIndicatorPaddingLeft;
                right = textView.getX() + (textView.getWidth() >> 1) + txtWidth / 2 + mIndicatorPaddingRight;
            } else {
                left = textView.getX();
                right = textView.getX() + textView.getWidth();
            }
            top = textView.getY();
            bottom = textView.getHeight() + top;
        } else {
            float leftOffset = mTabsContainer.getChildAt(mFromPosition + 1).getX()
                    - mTabsContainer.getChildAt(mFromPosition).getX();
            float rightOffset = leftOffset + (mTabsContainer.getChildAt(mFromPosition + 1).getWidth()
                    - mTabsContainer.getChildAt(mFromPosition).getWidth());

            if (mIndicatorWidth > 0) {
                TextView nextTextView = (TextView) mTabsContainer.getChildAt(mFromPosition + 1);
                TextView nowTextView = (TextView) mTabsContainer.getChildAt(mFromPosition);
                float Offset = nextTextView.getX() + (nextTextView.getWidth() >> 1)
                        - nowTextView.getX() - (nowTextView.getWidth() >> 1);

                left = textView.getX() + (textView.getWidth() >> 1) - mIndicatorWidth / 2
                        + Offset * mPositionOffset;
                right = textView.getX() + (textView.getWidth() >> 1) + mIndicatorWidth / 2
                        + Offset * mPositionOffset;
            } else if (mIndicatorFit == INDICATOR_FIT_TXT) {
                TextPaint paint = textView.getPaint();
                float txtWidth = paint.measureText(textView.getText().toString());

                left = textView.getX() + (textView.getWidth() >> 1) - txtWidth / 2
                        + leftOffset * mPositionOffset - mIndicatorPaddingLeft;
                right = textView.getX() + (textView.getWidth() >> 1) + txtWidth / 2
                        + rightOffset * mPositionOffset + mIndicatorPaddingRight;
            } else {
                left = textView.getX() + leftOffset * mPositionOffset;
                right = textView.getX() + textView.getWidth() + rightOffset * mPositionOffset;
            }
            top = textView.getY();
            bottom = textView.getHeight() + top;
        }

        mPaint.setColor(mIndicatorColor);
        mRect.left = left;
        mRect.right = right;
        /*游标块状类型*/
        int INDICATOR_TYPE_BLOCK = 1;
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

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            mViewPager.setCurrentItem(position);
            updateTabText(position);
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
            mFromPosition = position;
            mPositionOffset = positionOffset;
            scrollTabToCenter();
            invalidate();
        }

        @Override
        public void onPageSelected(int position) {
            updateTabText(position);
        }

        /**
         * @param state 1(正在滑动)，2（滑动完毕，还在滑），0（停止）
         */
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    public List<String> getTitle() {
        return mTitles;
    }
}