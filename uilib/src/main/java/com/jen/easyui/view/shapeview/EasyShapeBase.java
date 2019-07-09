package com.jen.easyui.view.shapeview;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
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
    private int mHeight;
//    private GradientDrawable mDrawableNormal;
//    private GradientDrawable mDrawablePressed;

    int mSolidColorNormal;
    int mStrokeWidthNormal;
    float mStrokeDashGapWidthNormal;
    float mStrokeDashGapNormal;
    int mStrokeColorNormal;
    float mCornersNormal;
    float mCornerLeftTopNormal;
    float mCornerLeftBottomNormal;
    float mCornerRightTopNormal;
    float mCornerRightBottomNormal;

    int mSolidColorPressed;
    int mStrokeColorPressed;

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

    /*点击类型：normal：点击变色，check：check，-1：不允许点击*/
    ClickType mClickType = ClickType.NON;
//    private boolean isCheck;

    public enum ClickType {
        BUTTON,//按钮效果点击变色,默认效果
        SELECTED,//选中效果
        NON//没有点击效果
    }

    /*------------------------------------------------公共属性end*/
//    private int mPaddingLeft;
//    private int mPaddingRight;
//    private int mPaddingTop;
//    private int mPaddingBottom;

    /*------------------------------------------------只属于TextView属性start*/
    int mTextColor;
    int mTextColorPressed;
    /*------------------------------------------------只属于TextView属性end*/

    private Paint mLinePaint;

    EasyShapeBase(View view) {
        this.mView = view;
        isTextView = view instanceof TextView;
    }

    public void init() {
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);//设置填充样式
        mLinePaint.setStrokeWidth(mLineWidth);//设置画笔宽度
        initDrawable();
    }

    private void initDrawable() {
        GradientDrawable mDrawableNormal = new GradientDrawable();
        mDrawableNormal.setColor(mSolidColorNormal);
        mDrawableNormal.setStroke(mStrokeWidthNormal, mStrokeColorNormal, mStrokeDashGapWidthNormal, mStrokeDashGapNormal);
        if (mCornersNormal > 0) {
            mDrawableNormal.setCornerRadius(mCornersNormal);
        } else {
            mDrawableNormal.setCornerRadii(new float[]{mCornerLeftTopNormal, mCornerLeftTopNormal, mCornerRightTopNormal, mCornerRightTopNormal,
                    mCornerRightBottomNormal, mCornerRightBottomNormal, mCornerLeftBottomNormal, mCornerLeftBottomNormal});
        }
        mDrawableNormal.setShape(GradientDrawable.RECTANGLE);
        mDrawableNormal.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        GradientDrawable mDrawablePressed = new GradientDrawable();
        mDrawablePressed.setColor(mSolidColorPressed);
        mDrawablePressed.setStroke(mStrokeWidthNormal, mStrokeColorPressed, mStrokeDashGapWidthNormal, mStrokeDashGapNormal);
        if (mCornersNormal > 0) {
            mDrawablePressed.setCornerRadius(mCornersNormal);
        } else {
            mDrawablePressed.setCornerRadii(new float[]{mCornerLeftTopNormal, mCornerLeftTopNormal, mCornerRightTopNormal, mCornerRightTopNormal,
                    mCornerRightBottomNormal, mCornerRightBottomNormal, mCornerLeftBottomNormal, mCornerLeftBottomNormal});
        }
        mDrawablePressed.setShape(GradientDrawable.RECTANGLE);
        mDrawablePressed.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        StateListDrawable drawable = new StateListDrawable();
        switch (mClickType) {
            case BUTTON: {
                int pressed = android.R.attr.state_pressed; //取负值就表示pressed为false的意思
                drawable.addState(new int[]{pressed}, mDrawablePressed);
                drawable.addState(new int[]{-pressed}, mDrawableNormal);//默认状态下的图片
                break;
            }
            case SELECTED:
                int selected = android.R.attr.state_selected;
                drawable.addState(new int[]{selected}, mDrawablePressed);
                drawable.addState(new int[]{-selected}, mDrawableNormal);
                break;
            case NON:
                int normal = android.R.attr.state_pressed;
                drawable.addState(new int[]{normal}, mDrawableNormal);
                drawable.addState(new int[]{-normal}, mDrawableNormal);
                break;
        }
        mView.setBackground(drawable);
    }

//    public void update() {
//        mLinePaint.setStyle(Paint.Style.FILL);//设置填充样式
//        mLinePaint.setStrokeWidth(mLineWidth);//设置画笔宽度
//    }

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
     * 刷新check
     *
     * @param eventAction .
     */
    void updateCheckState(int eventAction) {
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_CANCEL: {//在ScrollView包含ViewPager的情况下,滑动时还原check状态
                mView.setSelected(true);
//                isCheck = !isCheck;
                if (mView.isSelected()) {
                    if (isTextView) {
                        ((TextView) mView).setTextColor(mTextColorPressed);
                    }
                } else {
                    mView.setSelected(false);
                    if (isTextView) {
                        ((TextView) mView).setTextColor(mTextColor);
                    }
                }
                break;
            }
        }
    }

    /**
     * 刷新button
     *
     * @param eventAction .
     */
    void updateButtonState(int eventAction) {
        switch (eventAction) {
            case MotionEvent.ACTION_DOWN: {
                if (isTextView) {
                    ((TextView) mView).setTextColor(mTextColorPressed);
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                if (isTextView) {
                    ((TextView) mView).setTextColor(mTextColor);
                }
                break;
            }
        }
    }

    public EasyShapeBase setSolidColor(int color) {
        mSolidColorNormal = color;
        return this;
    }

    public EasyShapeBase setStrokeColor(int color) {
        mStrokeColorNormal = color;
        return this;
    }

    public EasyShapeBase setCorners(int db) {
        mCornersNormal = dp2px(db);
        return this;
    }

    public EasyShapeBase setCornerLeftTop(int db) {
        mCornerLeftTopNormal = dp2px(db);
        return this;
    }

    public EasyShapeBase setCornerLeftBottom(int db) {
        mCornerLeftBottomNormal = dp2px(db);
        return this;
    }

    public EasyShapeBase setCornerRightTop(int db) {
        mCornerRightTopNormal = dp2px(db);
        return this;
    }

    public EasyShapeBase setCornerRightBottom(int db) {
        mCornerRightBottomNormal = dp2px(db);
        return this;
    }

    public EasyShapeBase setClickType(ClickType clickType) {
        mClickType = clickType;
        return this;
    }

    public ClickType getClickType() {
        return mClickType;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }

    public int gettextColorPressed() {
        return mTextColorPressed;
    }

    public void settextColorPressed(int textColorPressed) {
        this.mTextColorPressed = textColorPressed;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    static int dp2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}