package com.jen.easyui.baseview;

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

    /*public EasyTextViewManager(Context context) {
        super(context);
        mShape = new EasyShapeBase(this);
        initAttrs(context, null);
    }*/

    public EasyShapeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShape = new EasyShapeBase(this);
        initAttrs(context, attrs);
    }

    public EasyShapeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShape = new EasyShapeBase(this);
        initAttrs(context, attrs);
    }

    protected void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyShapeTextView);
        mShape.mStrokeWidth = (int) ta.getDimension(R.styleable.EasyShapeTextView_strokeWidth, 0);
        mShape.mStrokeColor = ta.getColor(R.styleable.EasyShapeTextView_strokeColor, 0);
        mShape.mStrokeClickColor = ta.getColor(R.styleable.EasyShapeTextView_strokeClickColor, 0);

        mShape.mCorners = (int) ta.getDimension(R.styleable.EasyShapeTextView_corners, 0);
        mShape.mCornerLeftTop = (int) ta.getDimension(R.styleable.EasyShapeTextView_cornerLeftTop, 0);
        mShape.mCornerLeftBottom = (int) ta.getDimension(R.styleable.EasyShapeTextView_cornerLeftBottom, 0);
        mShape.mCornerRightTop = (int) ta.getDimension(R.styleable.EasyShapeTextView_cornerRightTop, 0);
        mShape.mCornerRightBottom = (int) ta.getDimension(R.styleable.EasyShapeTextView_cornerRightBottom, 0);

        mShape.mSolidColor = ta.getColor(R.styleable.EasyShapeTextView_solidColor, 0);
        mShape.mSolidClickColor = ta.getColor(R.styleable.EasyShapeTextView_solidClickColor, 0);

        mShape.mTextColor = ta.getColor(R.styleable.EasyShapeTextView_android_textColor, 0xFF000000);
        mShape.mTextClickColor = ta.getColor(R.styleable.EasyShapeTextView_textClickColor, 0xFF000000);

        mShape.mLineWidth = (int) ta.getDimension(R.styleable.EasyShapeTextView_lineWidth, 0);
        mShape.mLineLeftColor = ta.getColor(R.styleable.EasyShapeTextView_lineLeftColor, 0);
        mShape.mLineRightColor = ta.getColor(R.styleable.EasyShapeTextView_lineRightColor, 0);
        mShape.mLineTopColor = ta.getColor(R.styleable.EasyShapeTextView_lineTopColor, 0);
        mShape.mLineBottomColor = ta.getColor(R.styleable.EasyShapeTextView_lineBottomColor, 0);

        int clickType = ta.getInt(R.styleable.EasyShapeTextView_clickType, 0);
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
        return mShape.onFocusEvent(event);
//        return super.onTouchEvent(event);
    }
}
