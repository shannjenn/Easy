package com.jen.easyui.view.shapeview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class RippleAnimator {
    private View mView;
    private int mX, mY;
    private ObjectAnimator mAnimator;
    private int DEFAULT_RADIUS = 50;
    private int mCurRadius = 0;
    private RadialGradient mRadialGradient;
    private Paint mPaint;
    private int mStartColor = 0x00FFFFFF;
    private int mEndColor = 0x66EEEEEE;

    public RippleAnimator(View view) {
        mView = view;
        init();
    }

    private void init() {
        mView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
    }

    private void setRadius(final int radius) {
        mCurRadius = radius;
        if (mCurRadius > 0) {
            mRadialGradient = new RadialGradient(mX, mY, mCurRadius, mStartColor, mEndColor, Shader.TileMode.CLAMP);
            mPaint.setShader(mRadialGradient);
        }
        mView.postInvalidate();
    }

    public void onTouchEvent(MotionEvent event) {
        if (!mView.isClickable()) {
            return;
        }
        if (mX != event.getX() || mY != event.getY()) {
            mX = (int) event.getX();
            mY = (int) event.getY();
//            setRadius(DEFAULT_RADIUS);//按下的颜色
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            if (mAnimator != null && mAnimator.isRunning()) {
                mAnimator.cancel();
            }

            if (mAnimator == null) {
                mAnimator = ObjectAnimator.ofInt(this, "radius", DEFAULT_RADIUS, mView.getWidth());
            }

            mAnimator.setInterpolator(new AccelerateInterpolator());
            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    setRadius(0);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            mAnimator.start();
        }
    }

    public void onDraw(Canvas canvas) {
        if (!mView.isClickable()) {
            return;
        }
        canvas.drawCircle(mX, mY, mCurRadius, mPaint);
    }

    public void setmStartColor(int mStartColor) {
        this.mStartColor = mStartColor;
    }

    public void setmEndColor(int mEndColor) {
        this.mEndColor = mEndColor;
    }
}