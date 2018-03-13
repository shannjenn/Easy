package com.jen.easyui.shape;

import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 按钮
 * 作者：ShannJenn
 * 时间：2018/03/12.
 */

class ShapeSuper<T extends View> {
    private T t;
    private boolean isTextView;//是否为textView

    /*------------------------------------------------公共属性start*/
    protected GradientDrawable mDrawable;
    protected int mHeight;

    protected int mStrokeWidth;
    protected int mStrokeColor;
    protected int mStrokeClickColor;

    protected int mCorners;
    protected boolean mCornersHalfRound;//是否半圆
    protected boolean mCornersShowLeft;
    protected boolean mCornersShowRight;

    protected int mSolidColor;
    protected int mSolidClickColor;

    /*是否触摸变色*/
    protected boolean mChangeClickColor;
    /*------------------------------------------------公共属性end*/

//    private int mPaddingLeft;
//    private int mPaddingRight;
//    private int mPaddingTop;
//    private int mPaddingBottom;

    /*------------------------------------------------只属于TextView属性start*/
    protected int mTextColor;
    protected int mTextClickColor;
     /*------------------------------------------------只属于TextView属性end*/

    public ShapeSuper(T t) {
        this.t = t;
        isTextView = t instanceof TextView;
    }


    public void init() {
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
        t.setBackgroundDrawable(mDrawable);
    }

    /**
     * 设置半圆角
     *
     * @param heightMeasureSpec
     */
    public void setHalfRound(int heightMeasureSpec) {
        if (mHeight == 0) {
//            mHeight = getMeasuredHeight();
            if (isTextView) {
                mHeight = measureTextViewHeight(heightMeasureSpec);
            } else {

                mHeight = measureLayoutHeight(heightMeasureSpec);
            }
        }
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

    /**
     * 获取TextView高度
     *
     * @param measureSpec
     * @return
     */
    private int measureTextViewHeight(int measureSpec) {
        int result;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        Paint paint = ((TextView) t).getPaint();
        int ascent = (int) paint.ascent();
        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (-ascent + paint.descent()) + t.getPaddingTop() + t.getPaddingBottom();
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 获取Layout高度
     *
     * @param measureSpec
     * @return
     */
    private int measureLayoutHeight(int measureSpec) {
        int result;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = t.getPaddingTop() + t.getPaddingBottom();
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }


    /**
     * 触摸点击事件
     *
     * @param event
     */
    public void onFocusEvent(MotionEvent event) {
        if (!mChangeClickColor)
            return;
        if (t.isClickable()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
//                EasyUILog.d(TAG + "drawableStateChanged-- ACTION_DOWN");
                    mDrawable.setStroke(mStrokeWidth, mStrokeClickColor);
                    mDrawable.setColor(mSolidClickColor);
                    if (isTextView) {
                        ((TextView) t).setTextColor(mTextClickColor);
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
//                EasyUILog.d(TAG + "drawableStateChanged-- ACTION_UP");
                    mDrawable.setStroke(mStrokeWidth, mStrokeColor);
                    mDrawable.setColor(mSolidColor);
                    if (isTextView) {
                        ((TextView) t).setTextColor(mTextColor);
                    }
                    break;
                }
                default: {

                    break;
                }
            }
        }
    }

}