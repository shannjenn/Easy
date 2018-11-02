package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.jen.easyui.R;

/**
 * RelativeLayout(如果要点击效果，设置android:clickable="true")
 * 作者：ShannJenn
 * 时间：2017/11/16.
 */

public class EasyShapeRelativeLayout extends RelativeLayout {
    private EasyShapeBase mShape;

    /*public EasyEditTextManager(Context context) {
        super(context);
        mShape = new EasyShapeBase(this);
        initAttrs(context, null);
    }*/

    public EasyShapeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShape = new EasyShapeBase(this);
        initAttrs(context, attrs);
    }

    public EasyShapeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShape = new EasyShapeBase(this);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyShapeRelativeLayout);
        mShape.mStrokeWidth = (int) ta.getDimension(R.styleable.EasyShapeRelativeLayout_strokeWidth, 0);
        mShape.mStrokeColor = ta.getColor(R.styleable.EasyShapeRelativeLayout_strokeColor, 0);
        mShape.mStrokeClickColor = ta.getColor(R.styleable.EasyShapeRelativeLayout_strokeClickColor, 0);

        mShape.mCorners = (int) ta.getDimension(R.styleable.EasyShapeRelativeLayout_corners, 0);
        mShape.mCornerLeftTop = (int) ta.getDimension(R.styleable.EasyShapeRelativeLayout_cornerLeftTop, 0);
        mShape.mCornerLeftBottom = (int) ta.getDimension(R.styleable.EasyShapeRelativeLayout_cornerLeftBottom, 0);
        mShape.mCornerRightTop = (int) ta.getDimension(R.styleable.EasyShapeRelativeLayout_cornerRightTop, 0);
        mShape.mCornerRightBottom = (int) ta.getDimension(R.styleable.EasyShapeRelativeLayout_cornerRightBottom, 0);

        mShape.mSolidColor = ta.getColor(R.styleable.EasyShapeRelativeLayout_solidColor, 0);
        mShape.mSolidClickColor = ta.getColor(R.styleable.EasyShapeRelativeLayout_solidClickColor, 0);

        mShape.mLineWidth = (int) ta.getDimension(R.styleable.EasyShapeRelativeLayout_lineWidth, 0);
        mShape.mLineLeftColor = ta.getColor(R.styleable.EasyShapeRelativeLayout_lineLeftColor, 0);
        mShape.mLineRightColor = ta.getColor(R.styleable.EasyShapeRelativeLayout_lineRightColor, 0);
        mShape.mLineTopColor = ta.getColor(R.styleable.EasyShapeRelativeLayout_lineTopColor, 0);
        mShape.mLineBottomColor = ta.getColor(R.styleable.EasyShapeRelativeLayout_lineBottomColor, 0);

        int clickType = ta.getInt(R.styleable.EasyShapeRelativeLayout_clickType, 0);
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
        /*int padding = (int) ta.getDimension(R.styleable.EasyRelativeLayout_android_padding, 0);
        if (padding == 0) {
            mPaddingLeft = (int) ta.getDimension(R.styleable.EasyRelativeLayout_android_paddingLeft, 0);
            mPaddingRight = (int) ta.getDimension(R.styleable.EasyRelativeLayout_android_paddingRight, 0);
            mPaddingTop = (int) ta.getDimension(R.styleable.EasyRelativeLayout_android_paddingTop, 0);
            mPaddingBottom = (int) ta.getDimension(R.styleable.EasyRelativeLayout_android_paddingBottom, 0);
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