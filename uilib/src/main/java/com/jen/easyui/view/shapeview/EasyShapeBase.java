package com.jen.easyui.view.shapeview;

import android.content.res.Resources;
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

public class EasyShapeBase {
    private View mView;
    private boolean isTextView;//是否为textView

    /*------------------------------------------------公共属性start*/
    private GradientDrawable mDrawable;
    private int mHeight;

    int mStrokeWidth;
    float mStrokeDashGapWidth;
    float mStrokeDashGap;
    int mStrokeColor;
    int mStrokeClickColor;

    float mCorners;
    float mCornerLeftTop;
    float mCornerLeftBottom;
    float mCornerRightTop;
    float mCornerRightBottom;
//    boolean mCornersHalfRound;//是否半圆
//    boolean mCornersShowLeft;
//    boolean mCornersShowRight;

    int mLineLeftColor;
    int mLineLeftMarginLeft;
    int mLineLeftMarginTop;
    int mLineLeftMarginBottom;

    int mLineRightColor;
    int mLineRightMarginRight;
    int mLineRightMarginTop;
    int mLineRightMarginBottom;

    int mLineTopColor;
    int mLineTopMarginTop;
    int mLineTopMarginLeft;
    int mLineTopMarginRight;

    int mLineBottomColor;
    int mLineBottomMarginBottom;
    int mLineBottomMarginLeft;
    int mLineBottomMarginRight;

    float mLineWidth;

    int mSolidColor;
    int mSolidClickColor;

    /*点击类型：normal：点击变色，check：check，-1：不允许点击*/
    ClickType mClickType = ClickType.NON;
    private boolean isCheck;

    public enum ClickType {
        BUTTON,//按钮效果点击变色,默认效果
        CHECK,//check效果
        NON//没有点击效果
    }

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

    public void update() {
        if (mDrawable == null || mLinePaint == null) {
            mDrawable = new GradientDrawable();
            mLinePaint = new Paint();
        }
        mDrawable.setStroke(mStrokeWidth, mStrokeColor, mStrokeDashGapWidth, mStrokeDashGap);
        mDrawable.setColor(mSolidColor);
        mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        if (mCorners > 0) {
            mDrawable.setCornerRadius(mCorners);
        } else {
            mDrawable.setCornerRadii(new float[]{mCornerLeftTop, mCornerLeftTop, mCornerRightTop, mCornerRightTop,
                    mCornerRightBottom, mCornerRightBottom, mCornerLeftBottom, mCornerLeftBottom});
        }
        mView.setBackground(mDrawable);

        mLinePaint.setStyle(Paint.Style.FILL);//设置填充样式
        mLinePaint.setStrokeWidth(mLineWidth);//设置画笔宽度
    }

    void onDraw(Canvas canvas) {
        int width = mView.getMeasuredWidth() - 1;
        int height = mView.getMeasuredHeight() - 1;

        if (mLineLeftColor != 0) {
            mLinePaint.setColor(mLineLeftColor);  //设置画笔颜色
            int x = mLineLeftMarginLeft > 0 ? mLineLeftMarginLeft : 0;
            int y1 = mLineLeftMarginTop > 0 ? mLineLeftMarginTop : 0;
            int y2 = height - mLineLeftMarginBottom;
            canvas.drawLine(x, y1, x, y2, mLinePaint);
        }
        if (mLineRightColor != 0) {
            mLinePaint.setColor(mLineRightColor);  //设置画笔颜色
            int x = mLineRightMarginRight > 0 ? width - mLineRightMarginRight : width;
            int y1 = mLineRightMarginTop > 0 ? mLineRightMarginTop : 0;
            int y2 = height - mLineRightMarginBottom;
            canvas.drawLine(x, y1, x, y2, mLinePaint);
        }
        if (mLineTopColor != 0) {
            mLinePaint.setColor(mLineTopColor);  //设置画笔颜色
            int x1 = mLineTopMarginLeft > 0 ? mLineTopMarginLeft : 0;
            int x2 = mLineTopMarginRight > 0 ? width - mLineTopMarginRight : width;
            int y = mLineTopMarginTop > 0 ? mLineTopMarginTop : 0;
            canvas.drawLine(x1, y, x2, y, mLinePaint);
        }
        if (mLineBottomColor != 0) {
            mLinePaint.setColor(mLineBottomColor);  //设置画笔颜色
            int x1 = mLineBottomMarginLeft > 0 ? mLineBottomMarginLeft : 0;
            int x2 = mLineBottomMarginRight > 0 ? width - mLineBottomMarginRight : width;
            int y = mLineBottomMarginBottom > 0 ? height - mLineBottomMarginBottom : height;
            canvas.drawLine(x1, y, x2, y, mLinePaint);
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
    void onFocusEvent(MotionEvent event) {
        switch (mClickType) {
            case BUTTON: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        updateButtonState(true);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        updateButtonState(false);
                        break;
                    }
                }
                break;
            }
            case CHECK: {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        isCheck = !isCheck;
                        updateCheckState();
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {//在ScrollView包含ViewPager的情况下,滑动时还原check状态
                        isCheck = !isCheck;
                        updateCheckState();
                        break;
                    }
                }
                break;
            }
            case NON: {
                break;
            }
        }
    }

    private void updateCheckState() {
        if (isCheck) {
            mDrawable.setStroke(mStrokeWidth, mStrokeClickColor, mStrokeDashGapWidth, mStrokeDashGap);
            mDrawable.setColor(mSolidClickColor);
            if (isTextView) {
                ((TextView) mView).setTextColor(mTextClickColor);
            }
        } else {
            mDrawable.setStroke(mStrokeWidth, mStrokeColor, mStrokeDashGapWidth, mStrokeDashGap);
            mDrawable.setColor(mSolidColor);
            if (isTextView) {
                ((TextView) mView).setTextColor(mTextColor);
            }
        }
    }

    private void updateButtonState(boolean isActionDown) {
        if (isActionDown) {
            mDrawable.setStroke(mStrokeWidth, mStrokeClickColor, mStrokeDashGapWidth, mStrokeDashGap);
            mDrawable.setColor(mSolidClickColor);
            if (isTextView) {
                ((TextView) mView).setTextColor(mTextClickColor);
            }
        } else {
            mDrawable.setStroke(mStrokeWidth, mStrokeColor, mStrokeDashGapWidth, mStrokeDashGap);
            mDrawable.setColor(mSolidColor);
            if (isTextView) {
                ((TextView) mView).setTextColor(mTextColor);
            }
        }
    }

    public EasyShapeBase setSolidColor(int color) {
        mSolidColor = color;
        return this;
    }

    public EasyShapeBase setStrokeColor(int color) {
        mStrokeColor = color;
        return this;
    }

    public EasyShapeBase setCorners(int db) {
        mCorners = dp2px(db);
        return this;
    }

    public EasyShapeBase setCornerLeftTop(int db) {
        mCornerLeftTop = dp2px(db);
        return this;
    }

    public EasyShapeBase setCornerLeftBottom(int db) {
        mCornerLeftBottom = dp2px(db);
        return this;
    }

    public EasyShapeBase setCornerRightTop(int db) {
        mCornerRightTop = dp2px(db);
        return this;
    }

    public EasyShapeBase setCornerRightBottom(int db) {
        mCornerRightBottom = dp2px(db);
        return this;
    }

    public EasyShapeBase setClickType(ClickType clickType) {
        mClickType = clickType;
        return this;
    }

    public ClickType getClickType() {
        return mClickType;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        if (mClickType != ClickType.CHECK) {
            return;
        }
        isCheck = check;
        if (isCheck) {
            mDrawable.setStroke(mStrokeWidth, mStrokeClickColor, mStrokeDashGapWidth, mStrokeDashGap);
            mDrawable.setColor(mSolidClickColor);
            if (isTextView) {
                ((TextView) mView).setTextColor(mTextClickColor);
            }
        } else {
            mDrawable.setStroke(mStrokeWidth, mStrokeColor, mStrokeDashGapWidth, mStrokeDashGap);
            mDrawable.setColor(mSolidColor);
            if (isTextView) {
                ((TextView) mView).setTextColor(mTextColor);
            }
        }
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }

    public int getTextClickColor() {
        return mTextClickColor;
    }

    public void setTextClickColor(int textClickColor) {
        this.mTextClickColor = textClickColor;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    static int dp2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}