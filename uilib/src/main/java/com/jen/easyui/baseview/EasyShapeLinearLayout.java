package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.jen.easyui.R;

/**
 * LinearLayout(如果要点击效果，设置android:clickable="true")
 * 作者：ShannJenn
 * 时间：2017/11/16.
 */

public class EasyShapeLinearLayout extends LinearLayout {
    private EasyShapeBase mShape;

    /*public EasyEditTextManager(Context context) {
        super(context);
        mShape = new EasyShapeBase(this);
        initAttrs(context, null);
    }*/

    public EasyShapeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShape = new EasyShapeBase(this);
        initAttrs(context, attrs);
    }

    public EasyShapeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShape = new EasyShapeBase(this);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyShapeLinearLayout);
        mShape.mStrokeWidth = (int) ta.getDimension(R.styleable.EasyShapeLinearLayout_stroke_width, 0);
        mShape.mStrokeColor = ta.getColor(R.styleable.EasyShapeLinearLayout_stroke_color, 0);
        mShape.mStrokeClickColor = ta.getColor(R.styleable.EasyShapeLinearLayout_stroke_click_color, 0);

        mShape.mCorners = (int) ta.getDimension(R.styleable.EasyShapeLinearLayout_corners, 0);
        mShape.mCornersHalfRound = ta.getBoolean(R.styleable.EasyShapeLinearLayout_corners_half_round, false);
        mShape.mCornersShowLeft = ta.getBoolean(R.styleable.EasyShapeLinearLayout_cornersShowLeft, true);
        mShape.mCornersShowRight = ta.getBoolean(R.styleable.EasyShapeLinearLayout_cornersShowRight, true);

        mShape.mSolidColor = ta.getColor(R.styleable.EasyShapeLinearLayout_solid_color, 0);
        mShape.mSolidClickColor = ta.getColor(R.styleable.EasyShapeLinearLayout_solid_click_color, 0);

        mShape.mClickType = ta.getInt(R.styleable.EasyShapeLinearLayout_click_type, 0);

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

    public boolean isSelect() {
        return mShape.isSelect();
    }

    public void setSelect(boolean select) {
        mShape.setSelect(select);
    }
}