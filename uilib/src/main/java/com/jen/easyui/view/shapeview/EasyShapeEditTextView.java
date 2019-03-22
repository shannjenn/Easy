package com.jen.easyui.view.shapeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jen.easyui.R;


/**
 * EditText
 * 作者：ShannJenn
 * 时间：2017/11/16.
 */

public class EasyShapeEditTextView extends android.support.v7.widget.AppCompatEditText {
    private EasyShapeBase mShape;

    /*public EasyTextViewManager(Context context) {
        super(context);
        mShape = new EasyShapeBase(this);
        initAttrs(context, null);
    }*/

    public EasyShapeEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShape = new EasyShapeBase(this);
        initAttrs(context, attrs);
    }

    public EasyShapeEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShape = new EasyShapeBase(this);
        initAttrs(context, attrs);
    }

    protected void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyShapeEditTextView);
        mShape.mStrokeWidth = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_strokeWidth, 0);
        mShape.mStrokeDashGapWidth = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_strokeDashGapWidth, 0);
        mShape.mStrokeDashGap = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_strokeDashGap, 0);
        mShape.mStrokeWidth = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_strokeWidth, 0);
        mShape.mStrokeColor = ta.getColor(R.styleable.EasyShapeEditTextView_strokeColor, 0);
        mShape.mStrokeClickColor = ta.getColor(R.styleable.EasyShapeEditTextView_strokeClickColor, 0);

        mShape.mCorners = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_corners, 0);
        mShape.mCornerLeftTop = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_cornerLeftTop, 0);
        mShape.mCornerLeftBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_cornerLeftBottom, 0);
        mShape.mCornerRightTop = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_cornerRightTop, 0);
        mShape.mCornerRightBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_cornerRightBottom, 0);

        mShape.mSolidColor = ta.getColor(R.styleable.EasyShapeEditTextView_solidColor, 0);
        mShape.mSolidClickColor = ta.getColor(R.styleable.EasyShapeEditTextView_solidClickColor, 0);

        mShape.mTextColor = ta.getColor(R.styleable.EasyShapeEditTextView_android_textColor, 0xFF000000);
        mShape.mTextClickColor = ta.getColor(R.styleable.EasyShapeEditTextView_textClickColor, -1);

        mShape.mLineWidth = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineWidth, 0);
        mShape.mLineLeftColor = ta.getColor(R.styleable.EasyShapeEditTextView_lineLeftColor, 0);
        mShape.mLineRightColor = ta.getColor(R.styleable.EasyShapeEditTextView_lineRightColor, 0);
        mShape.mLineTopColor = ta.getColor(R.styleable.EasyShapeEditTextView_lineTopColor, 0);
        mShape.mLineBottomColor = ta.getColor(R.styleable.EasyShapeEditTextView_lineBottomColor, 0);

        mShape.mLineLeftMarginLeft = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineLeftMarginLeft, 0);
        mShape.mLineLeftMarginTop = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineLeftMarginTop, 0);
        mShape.mLineLeftMarginBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineLeftMarginBottom, 0);

        mShape.mLineRightMarginRight = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineRightMarginRight, 0);
        mShape.mLineRightMarginTop = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineRightMarginTop, 0);
        mShape.mLineRightMarginBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineRightMarginBottom, 0);

        mShape.mLineTopMarginTop = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineTopMarginTop, 0);
        mShape.mLineTopMarginLeft = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineTopMarginLeft, 0);
        mShape.mLineTopMarginRight = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineTopMarginRight, 0);

        mShape.mLineBottomMarginBottom = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineBottomMarginBottom, 0);
        mShape.mLineBottomMarginLeft = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineBottomMarginLeft, 0);
        mShape.mLineBottomMarginRight = ta.getDimensionPixelSize(R.styleable.EasyShapeEditTextView_lineBottomMarginRight, 0);

        if (mShape.mTextClickColor == -1) {
            mShape.mTextClickColor = mShape.mTextColor;
        }

        int clickType = ta.getInt(R.styleable.EasyShapeEditTextView_clickType, -1);
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

    public void update() {
        mShape.init();
        invalidate();
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mShape.onFocusEvent(event);
        return super.onTouchEvent(event);
    }

    public EasyShapeBase getShape(){
        return mShape;
    }

}