package com.jen.easyui.view.baseview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 必须包含两个Child: leftView 、rightView
 */
public class SlideDelete extends ViewGroup {
    private View leftView;
    private View rightView;
    private ViewDragHelper helper;
    private boolean enableSlide = true;

    public SlideDelete(Context context) {
        this(context, null);
    }

    public SlideDelete(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideDelete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        helper = ViewDragHelper.create(this, callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        //手势滑动时
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        //拖动控件水平移动
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //对左右越界问题的处理
            if (child == leftView) {
                //处理两边的越界问题
                if (left >= 0) {
                    left = 0;
                } else if (left <= -rightView.getMeasuredWidth()) {
                    left = -rightView.getMeasuredWidth();
                }
            } else if (child == rightView) {
                //只处理右边的越界问题,因为左侧越界的时看不到该View
                if (left <= leftView.getMeasuredWidth() - rightView.getMeasuredWidth()) {
                    left = leftView.getMeasuredWidth() - rightView.getMeasuredWidth();
                } else if (left >= leftView.getMeasuredWidth()) {
                    left = leftView.getMeasuredWidth();
                }
            }
            return left;
        }


        //监听控件移动状态
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            //如果左边控件拖动,我们要让右边控件也重新布局
            if (changedView == leftView) {
                rightView.layout(rightView.getLeft() + dx, 0, rightView.getRight() + dx, rightView.getBottom() + dy);
            } else if (changedView == rightView) {
                leftView.layout(leftView.getLeft() + dx, 0, leftView.getRight() + dx, leftView.getBottom() + dy);
            }
        }

        //解决滑动一半松手时,View的复位

        /**
         *
         * @param releasedChild 松开的View
         * @param xvel .
         * @param yvel .
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //松开后,什么时候打开rightView,什么时候关闭leftView
            //临界值,rightView.getLeft() 和 屏幕的宽度-rightView.getWidth()/2
            if (releasedChild == leftView) {
                if (rightView.getLeft() < getMeasuredWidth() - rightView.getMeasuredWidth() / 2) {
                    //使用ViewDragHelper来滑动
                    helper.smoothSlideViewTo(rightView, getMeasuredWidth() - rightView.getMeasuredWidth(), 0);
                    invalidate();
                } else {
                    helper.smoothSlideViewTo(rightView, getMeasuredWidth(), 0);
                    invalidate();
                }
            }

        }
    };

    //需要重写computeScroll

    @Override
    public void computeScroll() {
        //判断是否要继承滑动
        if (helper.continueSettling(true)) {
            //invalidate();
            //兼容使用
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //对当前组合View的测量,不使用的话,也可以自己设置
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //第一步获取里面子View
        leftView = getChildAt(0);
        rightView = getChildAt(1);
        //第二步给子View提供相应的布局
        int leftL = 0;
        int leftT = 0;
        int leftR = leftView.getMeasuredWidth();
        int leftB = leftView.getMeasuredHeight();
        leftView.layout(leftL, leftT, leftR, leftB);

        //给rightView提供相应的布局
        int rightL = leftView.getMeasuredWidth();
        int rightT = 0;
        int rightR = leftView.getMeasuredWidth() + rightView.getMeasuredWidth();
        int rightB = rightView.getMeasuredHeight();
        rightView.layout(rightL, rightT, rightR, rightB);
    }

    //View的事件传递

    private float DownX;
    private float DownY;
    private float moveX;
    private float moveY;
    private long currentMS;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!enableSlide){
            return super.onTouchEvent(event);
        }
        //1,要消费该事件,所以直接返回true
        //2,使用ViewDragHelper来实现滑动效果
        helper.processTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                DownX = event.getX();//float DownX
                DownY = event.getY();//float DownY
                moveX = 0;
                moveY = 0;
                currentMS = System.currentTimeMillis();//long currentMS     获取系统时间
                return true;
            case MotionEvent.ACTION_MOVE:
                moveX += Math.abs(event.getX() - DownX);//X轴距离
                moveY += Math.abs(event.getY() - DownY);//y轴距离
                DownX = event.getX();
                DownY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                long moveTime = System.currentTimeMillis() - currentMS;//移动时间
                //判断是否继续传递信号
                if (moveTime > 200 && (moveX > 20 || moveY > 20)) {
                    return true; //不再执行后面的事件，在这句前可写要执行的触摸相关代码。点击事件是发生在触摸弹起后
                } else {
                    performClick();
                    return super.onTouchEvent(event);//可以触发点击事件
                }
        }
        return true;//继续执行后面的代码
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void setEnableSlide(boolean enableSlide) {
        this.enableSlide = enableSlide;
    }
}