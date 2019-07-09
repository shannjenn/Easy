package com.jen.easyui.view.shapeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jen.easyui.R;


/**
 * TextView(如果要点击效果，设置android:clickable="true")
 * 作者：ShannJenn
 * 时间：2017/11/16.
 */

public class EasyShapeTextView extends android.support.v7.widget.AppCompatTextView {
    private EasyShapeBase mShape;
    private RippleAnimator mRippleAnimator;

    /*public EasyTextViewManager(Context context) {
        super(context);
        mShape = new EasyShapeBase(this);
        initAttrs(context, null);
    }*/

    public EasyShapeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShape = new EasyShapeBase(this);
        mRippleAnimator = new RippleAnimator(this);
        initAttrs(context, attrs);
    }

    public EasyShapeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShape = new EasyShapeBase(this);
        mRippleAnimator = new RippleAnimator(this);
        initAttrs(context, attrs);
    }

    protected void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyShapeTextView);
        mShape.mStrokeWidthNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_strokeWidth, 0);
        mShape.mStrokeDashGapWidthNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_strokeDashGapWidth, 0);
        mShape.mStrokeDashGapNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_strokeDashGap, 0);
        mShape.mStrokeWidthNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_strokeWidth, 0);
        mShape.mStrokeColorNormal = ta.getColor(R.styleable.EasyShapeTextView_strokeColor, 0);
        mShape.mStrokeColorPressed = ta.getColor(R.styleable.EasyShapeTextView_strokeColorPressed, 0);

        mShape.mCornersNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_corners, 0);
        mShape.mCornerLeftTopNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_cornerLeftTop, 0);
        mShape.mCornerLeftBottomNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_cornerLeftBottom, 0);
        mShape.mCornerRightTopNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_cornerRightTop, 0);
        mShape.mCornerRightBottomNormal = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_cornerRightBottom, 0);

        mShape.mSolidColorNormal = ta.getColor(R.styleable.EasyShapeTextView_solidColor, 0);
        mShape.mSolidColorPressed = ta.getColor(R.styleable.EasyShapeTextView_solidColorPressed, 0);

        mShape.mTextColor = ta.getColor(R.styleable.EasyShapeTextView_android_textColor, 0xFF000000);
        mShape.mTextColorPressed = ta.getColor(R.styleable.EasyShapeTextView_textColorPressed, mShape.mTextColor);

        mShape.mLineWidth = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineWidth, 0);
        mShape.mLineLeftColor = ta.getColor(R.styleable.EasyShapeTextView_lineLeftColor, 0);
        mShape.mLineRightColor = ta.getColor(R.styleable.EasyShapeTextView_lineRightColor, 0);
        mShape.mLineTopColor = ta.getColor(R.styleable.EasyShapeTextView_lineTopColor, 0);
        mShape.mLineBottomColor = ta.getColor(R.styleable.EasyShapeTextView_lineBottomColor, 0);

        mShape.mLineLeftMarginLeft = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineLeftMarginLeft, 0);
        mShape.mLineLeftMarginTop = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineLeftMarginTop, 0);
        mShape.mLineLeftMarginBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineLeftMarginBottom, 0);

        mShape.mLineRightMarginRight = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineRightMarginRight, 0);
        mShape.mLineRightMarginTop = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineRightMarginTop, 0);
        mShape.mLineRightMarginBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineRightMarginBottom, 0);

        mShape.mLineTopMarginTop = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineTopMarginTop, 0);
        mShape.mLineTopMarginLeft = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineTopMarginLeft, 0);
        mShape.mLineTopMarginRight = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineTopMarginRight, 0);

        mShape.mLineBottomMarginBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineBottomMarginBottom, 0);
        mShape.mLineBottomMarginLeft = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineBottomMarginLeft, 0);
        mShape.mLineBottomMarginRight = ta.getDimensionPixelSize(R.styleable.EasyShapeTextView_lineBottomMarginRight, 0);

        int clickType = ta.getInt(R.styleable.EasyShapeTextView_clickType, -1);
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
        /*int padding = (int) ta.getDimensionPixelSize(R.styleable.EasyTextView_android_padding, 0);
        if (padding == 0) {
            mPaddingLeft = (int) ta.getDimensionPixelSize(R.styleable.EasyTextView_android_paddingLeft, 0);
            mPaddingRight = (int) ta.getDimensionPixelSize(R.styleable.EasyTextView_android_paddingRight, 0);
            mPaddingTop = (int) ta.getDimensionPixelSize(R.styleable.EasyTextView_android_paddingTop, 0);
            mPaddingBottom = (int) ta.getDimensionPixelSize(R.styleable.EasyTextView_android_paddingBottom, 0);
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
        if (mShape.mClickType == EasyShapeBase.ClickType.SELECTED) {
            if (selected) {
                setTextColor(mShape.mTextColorPressed);
            } else {
                setTextColor(mShape.mTextColor);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN: {
//                EasyLog.d("MotionEvent.ACTION_DOWN ---------------");
//                break;
//            }
//            case MotionEvent.ACTION_UP: {
//                EasyLog.d("MotionEvent.ACTION_UP ---------------");
//                break;
//            }
//            case MotionEvent.ACTION_CANCEL: {
//                EasyLog.d("MotionEvent.ACTION_CANCEL ---------------");
//                break;
//            }
//        }
        boolean result = super.onTouchEvent(event);
        if (isEnabled()) {
            switch (mShape.mClickType) {
                case BUTTON:
                    if (result) {
                        mShape.updateButtonState(event.getAction());
                    }
                    break;
                case SELECTED:
                    mShape.updateCheckState(event.getAction());
                    break;
                case NON:
                    break;
                default:
                    break;
            }
        }
        switch (mShape.mClickType) {
            case BUTTON:
            case SELECTED:
                mRippleAnimator.onTouchEvent(event);
                break;
            case NON:
                break;
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
