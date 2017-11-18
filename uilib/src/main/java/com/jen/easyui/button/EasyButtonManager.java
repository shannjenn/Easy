package com.jen.easyui.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jen.easy.log.EasyUILog;
import com.jen.easyui.R;

/**
 * 按钮
 * 作者：ShannJenn
 * 时间：2017/11/16.
 */

abstract class EasyButtonManager extends android.support.v7.widget.AppCompatButton {
    private final String TAG = "EasyButtonManager : ";
    GradientDrawable mDrawable;

    private int mStrokeWidth;
    private int mStrokeColor;
    private int mStrokeClickColor;

    private int mCorners;
    /*是否半圆*/
    private boolean mCornersHalfCircle;

    private int mSolidColor;
    private int mSolidClickColor;

    private int mTextColor;
    private int mTextClickColor;

    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mPaddingBottom;

    public EasyButtonManager(Context context) {
        super(context);
        initAttrs(context, null);
        init();
    }

    public EasyButtonManager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    public EasyButtonManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyButton);
        mStrokeWidth = (int) ta.getDimension(R.styleable.EasyButton_stroke_width, 0);
        mStrokeColor = ta.getColor(R.styleable.EasyButton_stroke_color, 0);
        mStrokeClickColor = ta.getColor(R.styleable.EasyButton_stroke_click_color, 0);

        mCorners = (int) ta.getDimension(R.styleable.EasyButton_corners, 0);
        mCornersHalfCircle = ta.getBoolean(R.styleable.EasyButton_corners_half_circle, false);

        mSolidColor = ta.getColor(R.styleable.EasyButton_solid_color, 0);
        mSolidClickColor = ta.getColor(R.styleable.EasyButton_solid_click_color, 0);

        mTextColor = ta.getColor(R.styleable.EasyButton_android_textColor, 0xFF000000);
        mTextClickColor = ta.getColor(R.styleable.EasyButton_text_click_color, 0xFF000000);

        int padding = (int) ta.getDimension(R.styleable.EasyButton_android_padding, 0);
        if (padding == 0) {
            mPaddingLeft = (int) ta.getDimension(R.styleable.EasyButton_android_paddingLeft, 0);
            mPaddingRight = (int) ta.getDimension(R.styleable.EasyButton_android_paddingRight, 0);
            mPaddingTop = (int) ta.getDimension(R.styleable.EasyButton_android_paddingTop, 0);
            mPaddingBottom = (int) ta.getDimension(R.styleable.EasyButton_android_paddingBottom, 0);
        } else {
            mPaddingLeft = padding;
            mPaddingRight = padding;
            mPaddingTop = padding;
            mPaddingBottom = padding;
        }

        ta.recycle();
    }


    private void init() {
        super.setBackgroundDrawable(getResources().getDrawable(R.drawable._easy_button));
        mDrawable = (GradientDrawable) getBackground();
        mDrawable.setStroke(mStrokeWidth, mStrokeColor);
        mDrawable.setColor(mSolidColor);
        if(!mCornersHalfCircle){
            mDrawable.setCornerRadius(mCorners);
        }
        setPadding(mPaddingLeft,mPaddingTop,mPaddingRight,mPaddingBottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mCornersHalfCircle) {
            mDrawable.setCornerRadius(getHeight() / 2);
        }
    }

    @Override
    public void setBackground(Drawable background) {
//        super.setBackground(background);
        EasyUILog.w(TAG + "setBackground 已经被拦截，该功能失效，请在xml布局用自定义设置");
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
//        super.setBackgroundColor(color);
        EasyUILog.w(TAG + "setBackground 已经被拦截，该功能失效，请在xml布局用自定义设置");
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
//        super.setBackgroundDrawable(background);
        EasyUILog.w(TAG + "setBackground 已经被拦截，该功能失效，请在xml布局用自定义设置");
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isClickable()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
//                EasyUILog.d(TAG + "drawableStateChanged-- ACTION_DOWN");
                    mDrawable.setStroke(mStrokeWidth, mStrokeClickColor);
                    mDrawable.setColor(mSolidClickColor);
                    setTextColor(mTextClickColor);
                    break;
                }
                case MotionEvent.ACTION_UP: {
//                EasyUILog.d(TAG + "drawableStateChanged-- ACTION_UP");
                    mDrawable.setStroke(mStrokeWidth, mStrokeColor);
                    mDrawable.setColor(mSolidColor);
                    setTextColor(mTextColor);
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
