package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.jen.easyui.util.EasyDensityUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 导航布局,说明：文字导航栏，滑动游标效果、标题可以滑动
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * 作者：ShannJenn
 * 时间：2017/10/31.
 */
public class EasyTabBar extends HorizontalScrollView {
    private final String TAG = EasyTabBar.class.getSimpleName();
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
    private int mIndicatorSpeed;

    private int mUnderlineColor;
    private float mUnderlineHeight;

    private float mTabTextSize;
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

    private LinearLayout mTabsContainer;
    private final List<String> mTitles = new ArrayList<>();
    /*当前选中的tab*/
    private int mFromPosition;
    /*当前选中的tab*/
    private int mCurrentPosition;
    /*滚动次数*/
    private int mTimes;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mRect = new RectF();

    //    private final int H_UPDATE = 100;
    private final int H_LOOP_DRAW = 101;
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
//                case H_UPDATE: {
//                    View view = (View) msg.obj;
//                    mFromPosition = mCurrentPosition;
//                    mCurrentPosition = (int) view.getTag();
//                    updateTabText(mCurrentPosition);
//
//                    mHandler.sendEmptyMessage(101);
//                    mTimes = 0;
//                    break;
//                }
                case H_LOOP_DRAW: {
                    if (mTimes < mIndicatorSpeed) {
                        mTimes++;
                        mHandler.sendEmptyMessage(H_LOOP_DRAW);
                    } else {
                        mTimes = 0;
                    }
                    scrollTabToCenter();
                    invalidate();
                    break;
                }
            }
        }
    };


    public EasyTabBar(Context context) {
        this(context, null, 0);
    }

    public EasyTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        initAttrs(attrs);
    }

    public EasyTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        setFillViewport(true);//填满横向
        setWillNotDraw(false);//重写onDraw方法,需要调用这个方法来清除flag
        setClipChildren(false);//是否限制子View在其范围内
        setClipToPadding(false);//控件的绘制区域是否在padding里面

        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.EasyTabBar);

        mHeight = ta.getLayoutDimension(R.styleable.EasyTabBar_android_layout_height, 0);
        mWidth = ta.getLayoutDimension(R.styleable.EasyTabBar_android_layout_width, 0);

        mIndicatorSpeed = ta.getInt(R.styleable.EasyTabBar_tabBarIndicatorSpeed, 20);
        /*指示器默认颜色*/
        int INDICATOR_COLOR_DEFAULT = 0xff00abff;
        mIndicatorColor = ta.getColor(R.styleable.EasyTabBar_tabBarIndicatorColor, INDICATOR_COLOR_DEFAULT);
        mIndicatorHeight = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarIndicatorHeight, 0);
        mIndicatorWidth = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarIndicatorWidth, 0);
        mIndicatorCornerRadius = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarIndicatorCornerRadius, -1);
        /*游标线性类型*/
        int INDICATOR_TYPE_LINE = 0;
        mIndicatorType = ta.getInt(R.styleable.EasyTabBar_tabBarIndicatorType, INDICATOR_TYPE_LINE);
        /*游标块状类型*/
        int INDICATOR_FIT_WIDTH = 1;
        mIndicatorFit = ta.getInt(R.styleable.EasyTabBar_tabBarIndicatorFit, INDICATOR_FIT_WIDTH);
        mIndicatorPaddingLeft = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarIndicatorPaddingLeft, 0);
        mIndicatorPaddingRight = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarIndicatorPaddingRight, 0);

        /*默认颜色*/
        int COLOR_DEFAULT = 0xff000000;
        mUnderlineColor = ta.getColor(R.styleable.EasyTabBar_tabBarUnderlineColor, COLOR_DEFAULT);
        mUnderlineHeight = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarUnderlineHeight, 0);

        /*默认字体大小sp*/
        int TEXT_SIZE_DEFAULT = 16;
        mTabTextSize = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarTextSize, (int) EasyDensityUtil.sp2px(TEXT_SIZE_DEFAULT));
        mTabWidth = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarTabWidth, -2);//-2为WRAP_CONTENT属性
        mTabHeight = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarTabHeight, -2);
        mTabPaddingLeft = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarTabPaddingLeft, 0);
        mTabPaddingRight = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarTabPaddingRight, 0);
        mTabPaddingTop = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarTabPaddingTop, 0);
        mTabPaddingBottom = ta.getDimensionPixelOffset(R.styleable.EasyTabBar_tabBarTabPaddingBottom, 0);
        /*字体选中默认颜色*/
        int TEXT_COLOR_SELECT_DEFAULT = 0xffffffff;
        mTabSelectTextColor = ta.getColor(R.styleable.EasyTabBar_tabBarTextSelectColor, TEXT_COLOR_SELECT_DEFAULT);
        /*字体没选中默认颜色*/
        int TEXT_COLOR_UNSELECT_DEFAULT = 0xff6b6b6b;
        mTabUnSelectTextColor = ta.getColor(R.styleable.EasyTabBar_tabBarTextUnSelectColor, TEXT_COLOR_UNSELECT_DEFAULT);
        mTabTextBold = ta.getBoolean(R.styleable.EasyTabBar_tabBarTextBold, false);
        mTabTextItalic = ta.getBoolean(R.styleable.EasyTabBar_tabBarTextItalic, false);
        mTabWeight = ta.getBoolean(R.styleable.EasyTabBar_tabBarTabAverage, false);

        ta.recycle();

        String[] titles = {"标题1", "标题2", "标题标题3", "标题4", "标题5", "标题6", "标题7", "标题8", "标题9", "标题10"};
        mTitles.clear();
        mTitles.addAll(Arrays.asList(titles));
        removeAllViews();
        mTabsContainer = new LinearLayout(mContext);
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mTabsContainer, layoutParams);

        initTabViews();
    }

    private void initTabViews() {
        mTabsContainer.removeAllViews();
        for (int i = 0; i < mTitles.size(); i++) {
            TextView textView = new TextView(mContext);
            textView.setText(mTitles.get(i));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
            textView.setTextColor(i == mCurrentPosition ? mTabSelectTextColor : mTabUnSelectTextColor);
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
        }
    }

    /**
     * 滚动到中间
     */
    private void scrollTabToCenter() {
        View currentView = mTabsContainer.getChildAt(mCurrentPosition);
        int centerX = (int) (currentView.getX() + currentView.getWidth() / 2 - mWidth / 2);
        int x;
        if (mTimes == 0) {
            x = centerX;
        } else {
            if (centerX > getScrollX()) {
                x = getScrollX() + mTimes * (centerX - getScrollX()) / mIndicatorSpeed;
            } else {
                x = getScrollX() - mTimes * (getScrollX() - centerX) / mIndicatorSpeed;
            }
        }
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
//        if (isInEditMode()) {
//            Log.d(TAG, "onDraw isInEditMode");
//            return;
//        }
//        Log.d(TAG, "onDraw -------------");
        drawUnderline(canvas);
        drawIndicator(canvas);
    }

    /**
     * 画圆角矩形游标
     *
     * @param canvas .
     */
    private void drawIndicator(Canvas canvas) {
        if (mTitles.size() <= 0) {
            Log.w(TAG, "drawIndicator mTitles.size()=" + mTitles.size());
            return;
        }
        TextView fromView = (TextView) mTabsContainer.getChildAt(mFromPosition);
        TextView currentView = (TextView) mTabsContainer.getChildAt(mCurrentPosition);

        float fromLeft;
        float fromRight;
        float currentLeft;
        float currentRight;
        if (mIndicatorWidth > 0) {
            fromLeft = fromView.getX() + (fromView.getWidth() >> 1) - mIndicatorWidth / 2;
            fromRight = fromView.getX() + (fromView.getWidth() >> 1) + mIndicatorWidth / 2;
            currentLeft = currentView.getX() + (currentView.getWidth() >> 1) - mIndicatorWidth / 2;
            currentRight = currentView.getX() + (currentView.getWidth() >> 1) + mIndicatorWidth / 2;
        } else if (mIndicatorFit == 0) {
            TextPaint paint = currentView.getPaint();
            float txtWidth = paint.measureText(currentView.getText().toString());

            fromLeft = fromView.getX() + (fromView.getWidth() >> 1) - txtWidth / 2 - mIndicatorPaddingLeft;
            fromRight = fromView.getX() + (fromView.getWidth() >> 1) + txtWidth / 2 + mIndicatorPaddingRight;
            currentLeft = currentView.getX() + (currentView.getWidth() >> 1) - txtWidth / 2 - mIndicatorPaddingLeft;
            currentRight = currentView.getX() + (currentView.getWidth() >> 1) + txtWidth / 2 + mIndicatorPaddingRight;
        } else {
            fromLeft = fromView.getX() - mIndicatorPaddingLeft;
            fromRight = fromView.getX() + fromView.getWidth() + mIndicatorPaddingRight;
            currentLeft = currentView.getX() - mIndicatorPaddingLeft;
            currentRight = currentView.getX() + currentView.getWidth() + mIndicatorPaddingRight;
        }

        float left;
        float right;
        float top;
        float bottom;
        if (mTimes == 0) {
            left = currentLeft;
            right = currentRight;
        } else {
            left = fromLeft + mTimes * (currentLeft - fromLeft) / mIndicatorSpeed;
            right = fromRight + mTimes * (currentRight - fromRight) / mIndicatorSpeed;
        }
        top = currentView.getY();
        bottom = currentView.getHeight() + top;

        Log.d(TAG, "left=" + left);
        Log.d(TAG, "right=" + right);

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

    /**
     * 更新选中tab
     *
     * @param currentPosition .
     */
    private void updateDrawPosition(int currentPosition) {
        mFromPosition = mCurrentPosition;
        mCurrentPosition = currentPosition;
        mTimes = 0;
        mHandler.sendEmptyMessage(H_LOOP_DRAW);
        updateTabText(mCurrentPosition);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int currentPosition = (int) view.getTag();
            updateDrawPosition(currentPosition);
        }
    };

    /**
     * 设置初始选择的tab
     *
     * @param currentPosition .
     */
    public void setInitPosition(int currentPosition) {
        mCurrentPosition = currentPosition;
        updateTabText(mCurrentPosition);
        scrollTabToCenter();
        invalidate();
    }

    public void changePosition(int currentPosition) {
        updateDrawPosition(currentPosition);
    }

    public int getSelectPosition() {
        return mCurrentPosition;
    }

    public void setTitle(String[] titles) {
        mTitles.clear();
        mTitles.addAll(Arrays.asList(titles));
        initTabViews();
    }

    public void setTitle(List<String> titles) {
        mTitles.clear();
        mTitles.addAll(titles);
        initTabViews();
    }
}