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
        mShape.mStrokeWidth = (int) ta.getDimension(R.styleable.EasyShapeLinearLayout_strokeWidth, 0);
        mShape.mStrokeColor = ta.getColor(R.styleable.EasyShapeLinearLayout_strokeColor, 0);
        mShape.mStrokeClickColor = ta.getColor(R.styleable.EasyShapeLinearLayout_strokeClickColor, 0);

        mShape.mCorners = (int) ta.getDimension(R.styleable.EasyShapeLinearLayout_corners, 0);
        mShape.mCornerLeftTop = (int) ta.getDimension(R.styleable.EasyShapeLinearLayout_cornerLeftTop, 0);
        mShape.mCornerLeftBottom = (int) ta.getDimension(R.styleable.EasyShapeLinearLayout_cornerLeftBottom, 0);
        mShape.mCornerRightTop = (int) ta.getDimension(R.styleable.EasyShapeLinearLayout_cornerRightTop, 0);
        mShape.mCornerRightBottom = (int) ta.getDimension(R.styleable.EasyShapeLinearLayout_cornerRightBottom, 0);

        mShape.mSolidColor = ta.getColor(R.styleable.EasyShapeLinearLayout_solidColor, 0);
        mShape.mSolidClickColor = ta.getColor(R.styleable.EasyShapeLinearLayout_solidClickColor, 0);

        mShape.mLineWidth = (int) ta.getDimension(R.styleable.EasyShapeLinearLayout_lineWidth, 0);
        mShape.mLineLeftColor = ta.getColor(R.styleable.EasyShapeLinearLayout_lineLeftColor, 0);
        mShape.mLineRightColor = ta.getColor(R.styleable.EasyShapeLinearLayout_lineRightColor, 0);
        mShape.mLineTopColor = ta.getColor(R.styleable.EasyShapeLinearLayout_lineTopColor, 0);
        mShape.mLineBottomColor = ta.getColor(R.styleable.EasyShapeLinearLayout_lineBottomColor, 0);

        int clickType = ta.getInt(R.styleable.EasyShapeLinearLayout_clickType, 0);
        switch (clickType) {
            case 0: {
                mShape.mClickType = EasyShapeBase.ClickType.BUTTON;
                break;
            }
            case 1: {
                mShape.mClickType = EasyShapeBase.ClickType.CHECK;
                break;
            }
            case -1: {
                mShape.mClickType = EasyShapeBase.ClickType.ENABLE;
                break;
            }
        }
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
        return mShape.onFocusEvent(event);
//        return super.onTouchEvent(event);
    }

}