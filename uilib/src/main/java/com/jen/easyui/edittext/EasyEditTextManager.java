package com.jen.easyui.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;

import com.jen.easyui.R;

/**
 * EditText
 * 作者：ShannJenn
 * 时间：2017/11/16.
 */

abstract class EasyEditTextManager extends android.support.v7.widget.AppCompatEditText {
    private final String TAG = "EasyEditTextManager : ";
    GradientDrawable mDrawable;

    private int mStrokeWidth;
    private int mStrokeColor;
    private int mStrokeFocusColor;

    private int mCorners;
    /*是否半圆*/
    private boolean mCornersHalfRound;
    private boolean mCornersShowLeft;
    private boolean mCornersShowRight;

    private int mSolidColor;
    private int mSolidFocusColor;

    private int mTextColor;
    private int mTextFocusColor;

    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mPaddingBottom;

    /*是否触摸变色*/
    private boolean mChangeFocusColor;

    public EasyEditTextManager(Context context) {
        super(context);
        initAttrs(context, null);
        init();
    }

    public EasyEditTextManager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    public EasyEditTextManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyEditText);
        mStrokeWidth = (int) ta.getDimension(R.styleable.EasyEditText_stroke_width, 0);
        mStrokeColor = ta.getColor(R.styleable.EasyEditText_stroke_color, 0);
        mStrokeFocusColor = ta.getColor(R.styleable.EasyEditText_stroke_focus_color, 0);

        mCorners = (int) ta.getDimension(R.styleable.EasyEditText_corners, 0);
        mCornersHalfRound = ta.getBoolean(R.styleable.EasyEditText_corners_half_round, false);
        mCornersShowLeft = ta.getBoolean(R.styleable.EasyEditText_cornersShowLeft, true);
        mCornersShowRight= ta.getBoolean(R.styleable.EasyEditText_cornersShowRight, true);

        mSolidColor = ta.getColor(R.styleable.EasyEditText_solid_color, 0);
        mSolidFocusColor = ta.getColor(R.styleable.EasyEditText_solid_focus_color, 0);

        mTextColor = ta.getColor(R.styleable.EasyEditText_android_textColor, 0xFF000000);
        mTextFocusColor = ta.getColor(R.styleable.EasyEditText_text_focus_color, 0xFF000000);

        mChangeFocusColor = ta.getBoolean(R.styleable.EasyEditText_change_focus_color, false);

        int padding = (int) ta.getDimension(R.styleable.EasyEditText_android_padding, 0);
        if (padding == 0) {
            mPaddingLeft = (int) ta.getDimension(R.styleable.EasyEditText_android_paddingLeft, 0);
            mPaddingRight = (int) ta.getDimension(R.styleable.EasyEditText_android_paddingRight, 0);
            mPaddingTop = (int) ta.getDimension(R.styleable.EasyEditText_android_paddingTop, 0);
            mPaddingBottom = (int) ta.getDimension(R.styleable.EasyEditText_android_paddingBottom, 0);
        } else {
            mPaddingLeft = padding;
            mPaddingRight = padding;
            mPaddingTop = padding;
            mPaddingBottom = padding;
        }

        ta.recycle();
    }

    private void init() {
        super.setBackgroundDrawable(getResources().getDrawable(R.drawable._easy_edittext));
        mDrawable = (GradientDrawable) getBackground();
        mDrawable.setStroke(mStrokeWidth, mStrokeColor);
        mDrawable.setColor(mSolidColor);
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
        setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mCornersHalfRound) {
            if (mCornersShowLeft && mCornersShowRight) {
                mDrawable.setCornerRadius(getHeight() / 2);
            } else {
                int cornerLeft = mCornersShowLeft ? getHeight() / 2 : 0;
                int cornerRight = mCornersShowRight ? getHeight() / 2 : 0;
                mDrawable.setCornerRadii(new float[]{cornerLeft, cornerLeft, cornerRight, cornerRight,
                        cornerRight, cornerRight, cornerLeft, cornerLeft});
            }
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
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if(!mChangeFocusColor)
            return;
        if (focused) {
            mDrawable.setStroke(mStrokeWidth, mStrokeFocusColor);
            mDrawable.setColor(mSolidFocusColor);
            setTextColor(mTextFocusColor);
        } else {
            mDrawable.setStroke(mStrokeWidth, mStrokeColor);
            mDrawable.setColor(mSolidColor);
            setTextColor(mTextColor);
        }
    }
}