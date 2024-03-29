package com.jen.easyui.view.shapeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
    private RippleAnimator mRippleAnimator;

    /*public EasyEditTextManager(Context context) {
        super(context);
        mShape = new EasyShapeBase(this);
        initAttrs(context, null);
    }*/

    public EasyShapeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShape = new EasyShapeBase(this);
        mRippleAnimator = new RippleAnimator(this);
        initAttrs(context, attrs);
    }

    public EasyShapeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShape = new EasyShapeBase(this);
        mRippleAnimator = new RippleAnimator(this);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyShapeLinearLayout);
        mShape.mStrokeWidthNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_strokeWidth, 0);
        mShape.mStrokeDashGapWidthNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_strokeDashGapWidth, 0);
        mShape.mStrokeDashGapNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_strokeDashGap, 0);
        mShape.mStrokeColorNormal = ta.getColor(R.styleable.EasyShapeLinearLayout_strokeColor, 0);
        mShape.mStrokeColorPressed = ta.getColor(R.styleable.EasyShapeLinearLayout_strokeColorPressed, 0);

        mShape.mCornersNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_corners, 0);
        mShape.mCornerLeftTopNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_cornerLeftTop, 0);
        mShape.mCornerLeftBottomNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_cornerLeftBottom, 0);
        mShape.mCornerRightTopNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_cornerRightTop, 0);
        mShape.mCornerRightBottomNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_cornerRightBottom, 0);

        mShape.mSolidColorNormal = ta.getColor(R.styleable.EasyShapeLinearLayout_solidColor, 0);
        mShape.mSolidColorPressed = ta.getColor(R.styleable.EasyShapeLinearLayout_solidColorPressed, 0);

        mShape.mLineWidth = ta.getDimensionPixelOffset(R.styleable.EasyShapeLinearLayout_lineWidth, 0);
        mShape.mLineLeftColor = ta.getColor(R.styleable.EasyShapeLinearLayout_lineLeftColor, 0);
        mShape.mLineRightColor = ta.getColor(R.styleable.EasyShapeLinearLayout_lineRightColor, 0);
        mShape.mLineTopColor = ta.getColor(R.styleable.EasyShapeLinearLayout_lineTopColor, 0);
        mShape.mLineBottomColor = ta.getColor(R.styleable.EasyShapeLinearLayout_lineBottomColor, 0);

        mShape.mLineLeftMarginLeft = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_lineLeftMarginLeft, 0);
        mShape.mLineLeftMarginTop = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_lineLeftMarginTop, 0);
        mShape.mLineLeftMarginBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_lineLeftMarginBottom, 0);

        mShape.mLineRightMarginRight = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_lineRightMarginRight, 0);
        mShape.mLineRightMarginTop = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_lineRightMarginTop, 0);
        mShape.mLineRightMarginBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_lineRightMarginBottom, 0);

        mShape.mLineTopMarginTop = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_lineTopMarginTop, 0);
        mShape.mLineTopMarginLeft = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_lineTopMarginLeft, 0);
        mShape.mLineTopMarginRight = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_lineTopMarginRight, 0);

        mShape.mLineBottomMarginBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_lineBottomMarginBottom, 0);
        mShape.mLineBottomMarginLeft = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_lineBottomMarginLeft, 0);
        mShape.mLineBottomMarginRight = ta.getDimensionPixelSize(R.styleable.EasyShapeLinearLayout_lineBottomMarginRight, 0);

        int clickType = ta.getInt(R.styleable.EasyShapeLinearLayout_clickType, -1);
        switch (clickType) {
            case 0: {
                mShape.mClickType = EasyShapeBase.ClickType.BUTTON;
                setClickable(true);
                break;
            }
            case 1: {
                mShape.mClickType = EasyShapeBase.ClickType.SELECTED;
                setClickable(true);
                break;
            }
            case -1: {
                mShape.mClickType = EasyShapeBase.ClickType.NON;
                break;
            }
        }
        ta.recycle();
        mShape.init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mShape.onDraw(canvas);
        mRippleAnimator.onDraw(canvas);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        if (isEnabled()) {
            switch (mShape.mClickType) {
                case BUTTON:
                    mRippleAnimator.onTouchEvent(event);
                    break;
                case SELECTED:
                    mShape.updateCheckState(event.getAction());
                    mRippleAnimator.onTouchEvent(event);
                    break;
                case NON:
                    break;
            }
        }
        return result;
    }

    @Override
    public boolean performClick() {
//        EasyLog.d("performClick ---------------");
        return super.performClick();
    }

    public EasyShapeBase getShape() {
        return mShape;
    }

}