package com.jen.easyui.linearlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.jen.easyui.R;

/**
 * LinearLayout(如果要点击效果，设置android:clickable="true")
 * 作者：ShannJenn
 * 时间：2017/11/16.
 */

abstract class EasyLinearLayoutManager extends LinearLayout {
    private final String TAG = "EasyLinearLayoutManager : ";
    private GradientDrawable mDrawable;
    private int mHeight;

    private int mStrokeWidth;
    private int mStrokeColor;
    private int mStrokeClickColor;

    private int mCorners;
    /*是否半圆*/
    private boolean mCornersHalfRound;
    private boolean mCornersShowLeft;
    private boolean mCornersShowRight;

    private int mSolidColor;
    private int mSolidClickColor;

//    private int mPaddingLeft;
//    private int mPaddingRight;
//    private int mPaddingTop;
//    private int mPaddingBottom;

    private boolean mChangeClickColor;

    public EasyLinearLayoutManager(Context context) {
        super(context);
        initAttrs(context, null);
        init();
    }

    public EasyLinearLayoutManager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    public EasyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyLinearLayout);
        mStrokeWidth = (int) ta.getDimension(R.styleable.EasyLinearLayout_stroke_width, 0);
        mStrokeColor = ta.getColor(R.styleable.EasyLinearLayout_stroke_color, 0);
        mStrokeClickColor = ta.getColor(R.styleable.EasyLinearLayout_stroke_click_color, 0);

        mCorners = (int) ta.getDimension(R.styleable.EasyLinearLayout_corners, 0);
        mCornersHalfRound = ta.getBoolean(R.styleable.EasyLinearLayout_corners_half_round, false);
        mCornersShowLeft = ta.getBoolean(R.styleable.EasyLinearLayout_cornersShowLeft, true);
        mCornersShowRight = ta.getBoolean(R.styleable.EasyLinearLayout_cornersShowRight, true);

        mSolidColor = ta.getColor(R.styleable.EasyLinearLayout_solid_color, 0);
        mSolidClickColor = ta.getColor(R.styleable.EasyLinearLayout_solid_click_color, 0);

        mChangeClickColor = ta.getBoolean(R.styleable.EasyLinearLayout_change_click_color, false);

        /*int padding = (int) ta.getDimension(R.styleable.EasyLinearLayout_android_padding, 0);
        if (padding == 0) {
            mPaddingLeft = (int) ta.getDimension(R.styleable.EasyLinearLayout_android_paddingLeft, 0);
            mPaddingRight = (int) ta.getDimension(R.styleable.EasyLinearLayout_android_paddingRight, 0);
            mPaddingTop = (int) ta.getDimension(R.styleable.EasyLinearLayout_android_paddingTop, 0);
            mPaddingBottom = (int) ta.getDimension(R.styleable.EasyLinearLayout_android_paddingBottom, 0);
        } else {
            mPaddingLeft = padding;
            mPaddingRight = padding;
            mPaddingTop = padding;
            mPaddingBottom = padding;
        }*/

        ta.recycle();
    }


    private void init() {
        mDrawable = new GradientDrawable();
        mDrawable.setStroke(mStrokeWidth, mStrokeColor);
        mDrawable.setColor(mSolidColor);
        mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        if (!mCornersHalfRound) {
            if (mCornersShowLeft && mCornersShowRight) {
                mDrawable.setCornerRadius(mCorners);
            } else {
                int cornerLeft = mCornersShowLeft ? mCorners : 0;
                int cornerRight = mCornersShowRight ? mCorners : 0;
                mDrawable.setCornerRadii(new float[]{cornerLeft, cornerLeft, cornerRight, cornerRight,
                        cornerRight, cornerRight, cornerLeft, cornerLeft});
            }
        }
        super.setBackgroundDrawable(mDrawable);
    }

    private void setHalfRound() {
        if (mCornersHalfRound) {
            if (mCornersShowLeft && mCornersShowRight) {
                mDrawable.setCornerRadius(mHeight / 2);
            } else {
                int cornerLeft = mCornersShowLeft ? mHeight / 2 : 0;
                int cornerRight = mCornersShowRight ? mHeight / 2 : 0;
                mDrawable.setCornerRadii(new float[]{cornerLeft, cornerLeft, cornerRight, cornerRight,
                        cornerRight, cornerRight, cornerLeft, cornerLeft});
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeight == 0) {
            mHeight = getHeight();
            setHalfRound();
        }
    }

    @Override
    public void setBackground(Drawable background) {
//        super.setBackground(background);

    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
//        super.setBackgroundColor(color);

    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
//        super.setBackgroundDrawable(background);

    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mChangeClickColor)
            return super.onTouchEvent(event);
        if (isClickable()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
//                EasyUILog.d(TAG + "drawableStateChanged-- ACTION_DOWN");
                    mDrawable.setStroke(mStrokeWidth, mStrokeClickColor);
                    mDrawable.setColor(mSolidClickColor);
                    break;
                }
                case MotionEvent.ACTION_UP: {
//                EasyUILog.d(TAG + "drawableStateChanged-- ACTION_UP");
                    mDrawable.setStroke(mStrokeWidth, mStrokeColor);
                    mDrawable.setColor(mSolidColor);
                    break;
                }
                default: {

                    break;
                }
            }
        }
        return super.onTouchEvent(event);
    }
}