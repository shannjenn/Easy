package com.jen.easyui.baseview;

import android.graphics.Canvas;
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

class EasyShapeBase {
    private View mView;
    private boolean isTextView;//是否为textView

    /*------------------------------------------------公共属性start*/
    private GradientDrawable mDrawable;
    private int mHeight;

    int mStrokeWidth;
    int mStrokeColor;
    int mStrokeClickColor;

    int mCorners;
    int mCornerLeftTop;
    int mCornerLeftBottom;
    int mCornerRightTop;
    int mCornerRightBottom;
//    boolean mCornersHalfRound;//是否半圆
//    boolean mCornersShowLeft;
//    boolean mCornersShowRight;

    int mLineLeftColor;
    int mLineRightColor;
    int mLineTopColor;
    int mLineBottomColor;
    int mLineWidth;

    int mSolidColor;
    int mSolidClickColor;

    /*点击类型：normal：点击变色，check：check，-1：不允许点击*/
    ClickType mClickType = ClickType.BUTTON;
    private boolean isCheck;

    enum ClickType {
        BUTTON,//按钮效果点击变色,默认效果
        CHECK,//check效果
        ENABLE//不允许点击效果
    }
//    boolean isCheck;
    /*------------------------------------------------公共属性end*/

//    private int mPaddingLeft;
//    private int mPaddingRight;
//    private int mPaddingTop;
//    private int mPaddingBottom;

    /*------------------------------------------------只属于TextView属性start*/
    int mTextColor;
    int mTextClickColor;
    /*------------------------------------------------只属于TextView属性end*/

    private Paint mLinePaint;

    EasyShapeBase(View view) {
        this.mView = view;
        isTextView = view instanceof TextView;
    }

    public void init() {
        mDrawable = new GradientDrawable();
        mDrawable.setStroke(mStrokeWidth, mStrokeColor);
        mDrawable.setColor(mSolidColor);
        mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        if (mCorners > 0) {
            mDrawable.setCornerRadius(mCorners);
        } else {
            mDrawable.setCornerRadii(new float[]{mCornerLeftTop, mCornerLeftTop, mCornerRightTop, mCornerRightTop,
                    mCornerRightBottom, mCornerRightBottom, mCornerLeftBottom, mCornerLeftBottom});
        }
        mView.setBackground(mDrawable);

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);//设置填充样式
        mLinePaint.setStrokeWidth(mLineWidth);//设置画笔宽度
    }

    void onDraw(Canvas canvas) {
        int width = mView.getMeasuredWidth();
        int height = mView.getMeasuredHeight();
        if (mLineLeftColor != 0) {
            mLinePaint.setColor(mLineLeftColor);  //设置画笔颜色
            canvas.drawLine(0, 0, 0, height, mLinePaint);
        }
        if (mLineRightColor != 0) {
            mLinePaint.setColor(mLineRightColor);  //设置画笔颜色
            canvas.drawLine(width, 0, width, height, mLinePaint);
        }
        if (mLineTopColor != 0) {
            mLinePaint.setColor(mLineTopColor);  //设置画笔颜色
            canvas.drawLine(0, 0, width, 0, mLinePaint);
        }
        if (mLineBottomColor != 0) {
            mLinePaint.setColor(mLineBottomColor);  //设置画笔颜色
            canvas.drawLine(0, height, width, height, mLinePaint);
        }
    }

    /**
     * 设置半圆角
     *
     * @param heightMeasureSpec measureSpec
     */
    void setHalfRound(int heightMeasureSpec) {
        if (mHeight == 0) {
//            mHeight = getMeasuredHeight();
            if (isTextView) {
                mHeight = measureTextViewHeight(heightMeasureSpec);
            } else {
                mHeight = measureLayoutHeight(heightMeasureSpec);
            }
        }
        if (mCorners > 0) {
            mDrawable.setCornerRadius(mCorners);
        } else {
            mDrawable.setCornerRadii(new float[]{mCornerLeftTop, mCornerLeftTop, mCornerRightTop, mCornerRightTop,
                    mCornerRightBottom, mCornerRightBottom, mCornerLeftBottom, mCornerLeftBottom});
        }
    }

    /**
     * 获取TextView高度
     *
     * @param measureSpec measureSpec
     * @return int
     */
    private int measureTextViewHeight(int measureSpec) {
        int result;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        Paint paint = ((TextView) mView).getPaint();
        int ascent = (int) paint.ascent();
        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (-ascent + paint.descent()) + mView.getPaddingTop() + mView.getPaddingBottom();
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 获取Layout高度
     *
     * @param measureSpec measureSpec
     * @return int
     */
    private int measureLayoutHeight(int measureSpec) {
        int result;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mView.getPaddingTop() + mView.getPaddingBottom();
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 触摸点击事件
     *
     * @param event e
     */
    boolean onFocusEvent(MotionEvent event) {
        if (mClickType == ClickType.ENABLE)
            return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                switch (mClickType) {
                    case BUTTON: {
                        mDrawable.setStroke(mStrokeWidth, mStrokeClickColor);
                        mDrawable.setColor(mSolidClickColor);
                        if (isTextView) {
                            ((TextView) mView).setTextColor(mTextClickColor);
                        }
                        break;
                    }
                    case CHECK: {
                        isCheck = !isCheck;
                        if (isCheck) {
                            mDrawable.setStroke(mStrokeWidth, mStrokeClickColor);
                            mDrawable.setColor(mSolidClickColor);
                            if (isTextView) {
                                ((TextView) mView).setTextColor(mTextClickColor);
                            }
                        } else {
                            mDrawable.setStroke(mStrokeWidth, mStrokeColor);
                            mDrawable.setColor(mSolidColor);
                            if (isTextView) {
                                ((TextView) mView).setTextColor(mTextColor);
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (mClickType == ClickType.BUTTON) {
                    mDrawable.setStroke(mStrokeWidth, mStrokeColor);
                    mDrawable.setColor(mSolidColor);
                    if (isTextView) {
                        ((TextView) mView).setTextColor(mTextColor);
                    }
                }
                break;
            }
        }
        return true;
    }

}