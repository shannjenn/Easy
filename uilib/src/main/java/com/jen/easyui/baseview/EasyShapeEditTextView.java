package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
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

    /*public EasyEditTextManager(Context context) {
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
        mShape.mStrokeWidth = (int) ta.getDimension(R.styleable.EasyShapeEditTextView_strokeWidth, 0);
        mShape.mStrokeColor = ta.getColor(R.styleable.EasyShapeEditTextView_strokeColor, 0);
        mShape.mStrokeClickColor = ta.getColor(R.styleable.EasyShapeEditTextView_strokeClickColor, 0);

        mShape.mCorners = (int) ta.getDimension(R.styleable.EasyShapeEditTextView_corners, 0);
        mShape.mCornerLeftTop = (int) ta.getDimension(R.styleable.EasyShapeEditTextView_cornerLeftTop, 0);
        mShape.mCornerLeftBottom = (int) ta.getDimension(R.styleable.EasyShapeEditTextView_cornerLeftBottom, 0);
        mShape.mCornerRightTop = (int) ta.getDimension(R.styleable.EasyShapeEditTextView_cornerRightTop, 0);
        mShape.mCornerRightBottom = (int) ta.getDimension(R.styleable.EasyShapeEditTextView_cornerRightBottom, 0);

        mShape.mSolidColor = ta.getColor(R.styleable.EasyShapeEditTextView_solidColor, 0);
        mShape.mSolidClickColor = ta.getColor(R.styleable.EasyShapeEditTextView_solidClickColor, 0);

        mShape.mLineWidth = (int) ta.getDimension(R.styleable.EasyShapeEditTextView_lineWidth, 0);
        mShape.mLineLeftColor = ta.getColor(R.styleable.EasyShapeEditTextView_lineLeftColor, 0);
        mShape.mLineRightColor = ta.getColor(R.styleable.EasyShapeEditTextView_lineRightColor, 0);
        mShape.mLineTopColor = ta.getColor(R.styleable.EasyShapeEditTextView_lineTopColor, 0);
        mShape.mLineBottomColor = ta.getColor(R.styleable.EasyShapeEditTextView_lineBottomColor, 0);

        int clickType = ta.getInt(R.styleable.EasyShapeEditTextView_clickType, 0);
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
        /*int padding = (int) ta.getDimension(R.styleable.EasyEditText_android_padding, 0);
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