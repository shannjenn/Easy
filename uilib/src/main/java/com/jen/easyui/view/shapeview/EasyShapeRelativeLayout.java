package com.jen.easyui.view.shapeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
    private RippleAnimator mRippleAnimator;

    /*public EasyEditTextManager(Context context) {
        super(context);
        mShape = new EasyShapeBase(this);
        initAttrs(context, null);
    }*/

    public EasyShapeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShape = new EasyShapeBase(this);
        mRippleAnimator = new RippleAnimator(this);
        initAttrs(context, attrs);
    }

    public EasyShapeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShape = new EasyShapeBase(this);
        mRippleAnimator = new RippleAnimator(this);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyShapeRelativeLayout);
        mShape.mStrokeWidthNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_strokeWidth, 0);
        mShape.mStrokeDashGapWidthNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_strokeDashGapWidth, 0);
        mShape.mStrokeDashGapNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_strokeDashGap, 0);
        mShape.mStrokeColorNormal = ta.getColor(R.styleable.EasyShapeRelativeLayout_strokeColor, 0);
        mShape.mStrokeColorPressed = ta.getColor(R.styleable.EasyShapeRelativeLayout_strokeColorPressed, 0);

        mShape.mCornersNormal = ta.getDimensionPixelOffset(R.styleable.EasyShapeRelativeLayout_corners, 0);
        mShape.mCornerLeftTopNormal = ta.getDimensionPixelOffset(R.styleable.EasyShapeRelativeLayout_cornerLeftTop, 0);
        mShape.mCornerLeftBottomNormal = ta.getDimensionPixelOffset(R.styleable.EasyShapeRelativeLayout_cornerLeftBottom, 0);
        mShape.mCornerRightTopNormal = ta.getDimensionPixelOffset(R.styleable.EasyShapeRelativeLayout_cornerRightTop, 0);
        mShape.mCornerRightBottomNormal = ta.getDimensionPixelOffset(R.styleable.EasyShapeRelativeLayout_cornerRightBottom, 0);

        mShape.mSolidColorNormal = ta.getColor(R.styleable.EasyShapeRelativeLayout_solidColor, 0);
        mShape.mSolidColorPressed = ta.getColor(R.styleable.EasyShapeRelativeLayout_solidColorPressed, 0);

        mShape.mLineWidth = ta.getDimensionPixelOffset(R.styleable.EasyShapeRelativeLayout_lineWidth, 0);
        mShape.mLineLeftColor = ta.getColor(R.styleable.EasyShapeRelativeLayout_lineLeftColor, 0);
        mShape.mLineRightColor = ta.getColor(R.styleable.EasyShapeRelativeLayout_lineRightColor, 0);
        mShape.mLineTopColor = ta.getColor(R.styleable.EasyShapeRelativeLayout_lineTopColor, 0);
        mShape.mLineBottomColor = ta.getColor(R.styleable.EasyShapeRelativeLayout_lineBottomColor, 0);

        mShape.mLineLeftMarginLeft = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_lineLeftMarginLeft, 0);
        mShape.mLineLeftMarginTop = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_lineLeftMarginTop, 0);
        mShape.mLineLeftMarginBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_lineLeftMarginBottom, 0);

        mShape.mLineRightMarginRight = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_lineRightMarginRight, 0);
        mShape.mLineRightMarginTop = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_lineRightMarginTop, 0);
        mShape.mLineRightMarginBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_lineRightMarginBottom, 0);

        mShape.mLineTopMarginTop = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_lineTopMarginTop, 0);
        mShape.mLineTopMarginLeft = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_lineTopMarginLeft, 0);
        mShape.mLineTopMarginRight = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_lineTopMarginRight, 0);

        mShape.mLineBottomMarginBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_lineBottomMarginBottom, 0);
        mShape.mLineBottomMarginLeft = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_lineBottomMarginLeft, 0);
        mShape.mLineBottomMarginRight = ta.getDimensionPixelSize(R.styleable.EasyShapeRelativeLayout_lineBottomMarginRight, 0);

        int clickType = ta.getInt(R.styleable.EasyShapeRelativeLayout_clickType, -1);
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
                default:
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