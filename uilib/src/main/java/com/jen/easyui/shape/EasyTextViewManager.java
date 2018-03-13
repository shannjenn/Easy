package com.jen.easyui.shape;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jen.easyui.R;

/**
 * TextView(如果要点击效果，设置android:clickable="true")
 * 作者：ShannJenn
 * 时间：2017/11/16.
 */

abstract class EasyTextViewManager extends android.support.v7.widget.AppCompatTextView {
    private ShapeSuper mShape;

    /*public EasyTextViewManager(Context context) {
        super(context);
        mShape = new ShapeSuper(this);
        initAttrs(context, null);
    }*/

    public EasyTextViewManager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShape = new ShapeSuper(this);
        initAttrs(context, attrs);
    }

    public EasyTextViewManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShape = new ShapeSuper(this);
        initAttrs(context, attrs);
    }

    protected void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyTextView);
        mShape.mStrokeWidth = (int) ta.getDimension(R.styleable.EasyTextView_stroke_width, 0);
        mShape.mStrokeColor = ta.getColor(R.styleable.EasyTextView_stroke_color, 0);
        mShape.mStrokeClickColor = ta.getColor(R.styleable.EasyTextView_stroke_click_color, 0);

        mShape.mCorners = (int) ta.getDimension(R.styleable.EasyTextView_corners, 0);
        mShape.mCornersHalfRound = ta.getBoolean(R.styleable.EasyTextView_corners_half_round, false);
        mShape.mCornersShowLeft = ta.getBoolean(R.styleable.EasyTextView_cornersShowLeft, true);
        mShape.mCornersShowRight = ta.getBoolean(R.styleable.EasyTextView_cornersShowRight, true);

        mShape.mSolidColor = ta.getColor(R.styleable.EasyTextView_solid_color, 0);
        mShape.mSolidClickColor = ta.getColor(R.styleable.EasyTextView_solid_click_color, 0);

        mShape.mTextColor = ta.getColor(R.styleable.EasyTextView_android_textColor, 0xFF000000);
        mShape.mTextClickColor = ta.getColor(R.styleable.EasyTextView_text_click_color, 0xFF000000);

        mShape.mChangeClickColor = ta.getBoolean(R.styleable.EasyTextView_change_click_color, false);

        /*int padding = (int) ta.getDimension(R.styleable.EasyTextView_android_padding, 0);
        if (padding == 0) {
            mPaddingLeft = (int) ta.getDimension(R.styleable.EasyTextView_android_paddingLeft, 0);
            mPaddingRight = (int) ta.getDimension(R.styleable.EasyTextView_android_paddingRight, 0);
            mPaddingTop = (int) ta.getDimension(R.styleable.EasyTextView_android_paddingTop, 0);
            mPaddingBottom = (int) ta.getDimension(R.styleable.EasyTextView_android_paddingBottom, 0);
        } else {
            mPaddingLeft = padding;
            mPaddingRight = padding;
            mPaddingTop = padding;
            mPaddingBottom = padding;
        }*/

        ta.recycle();
        mShape.init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mShape.setHalfRound(heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mShape.onFocusEvent(event);
        return super.onTouchEvent(event);
    }
}