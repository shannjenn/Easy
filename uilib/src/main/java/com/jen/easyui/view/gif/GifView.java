package com.jen.easyui.view.gif;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jen.easyui.R;

/**
 * 自定义控件，用于显示Gif动图
 * Created by yc on 2018/11/18.
 */

public class GifView extends View {

    private static final int DEFAULT_MOVIE_VIEW_DURATION = 1000; // 默认1秒

    private int mMovieResourceId;
    private Movie movie;

    private long mMovieStart;
    private int mCurrentAnimationTime;

    private float mLeft;
    private float mTop;

    private float mScale;

    private int mMeasuredMovieWidth;
    private int mMeasuredMovieHeight;

    private volatile boolean mPaused;
    private boolean mVisible = true;

    public GifView(Context context) {
        super(context);
    }

    public GifView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setViewAttributes(context, attrs);
    }

    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setViewAttributes(context, attrs);
    }

    @SuppressLint("NewApi")
    private void setViewAttributes(Context context, AttributeSet attrs) {

        // 从 HONEYCOMB(Api Level:11) 开始，必须关闭HW加速度才能在Canvas上绘制Movie
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        // 从描述文件中读出Gif的值，绘制出Movie实例
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GifView);

        mMovieResourceId = array.getResourceId(R.styleable.GifView_gifSrc, -1); // -1为默认值
        mPaused = array.getBoolean(R.styleable.GifView_gifPaused, false);

        array.recycle();

        if (mMovieResourceId != -1) {
            movie = Movie.decodeStream(getResources().openRawResource(mMovieResourceId));
        }
    }

    /**
     * 设置Gif资源
     */
    public void setGifResource(int movieResourceId) {
        this.mMovieResourceId = movieResourceId;
        movie = Movie.decodeStream(getResources().openRawResource(mMovieResourceId));
        requestLayout();
    }

    /**
     * 获取Gif资源
     */
    public int getGifResource() {
        return this.mMovieResourceId;
    }

    /**
     * 播放
     */
    public void play() {
        if (this.mPaused) {
            this.mPaused = false;

            /**
             * 计算新的movie开始时间，使它从刚刚停止的帧重新播放
             */
            mMovieStart = android.os.SystemClock.uptimeMillis() - mCurrentAnimationTime;

            invalidate();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        if (!this.mPaused) {
            this.mPaused = true;

            invalidate();
        }

    }

    /**
     * 判断Gif动图当前处于播放还是暂停状态
     */

    public boolean isPaused() {
        return this.mPaused;
    }

    public boolean isPlaying() {
        return !this.mPaused;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (movie != null) {
            int movieWidth = movie.width();
            int movieHeight = movie.height();

            /**
             * 计算水平方向上的扩展
             */
            float scaleH = 1f;
            int measureModeWidth = MeasureSpec.getMode(widthMeasureSpec);

            if (measureModeWidth != MeasureSpec.UNSPECIFIED) {
                int maximumWidth = MeasureSpec.getSize(widthMeasureSpec);
                if (movieWidth > maximumWidth) {
                    scaleH = (float) movieWidth / (float) maximumWidth;
                }
            }

            /**
             * 计算竖直方向上的扩展
             */
            float scaleW = 1f;
            int measureModeHeight = MeasureSpec.getMode(heightMeasureSpec);

            if (measureModeHeight != MeasureSpec.UNSPECIFIED) {
                int maximumHeight = MeasureSpec.getSize(heightMeasureSpec);
                if (movieHeight > maximumHeight) {
                    scaleW = (float) movieHeight / (float) maximumHeight;
                }
            }

            /**
             * 计算扩展规模
             */
            mScale = 1f / Math.max(scaleH, scaleW);

            mMeasuredMovieWidth = (int) (movieWidth * mScale);
            mMeasuredMovieHeight = (int) (movieHeight * mScale);

            setMeasuredDimension(mMeasuredMovieWidth, mMeasuredMovieHeight);

        } else {
            /**
             * Movie为空，设置最小可用大小
             */
            setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        /**
         * 计算距离，以便绘制动画帧
         */
        mLeft = (getWidth() - mMeasuredMovieWidth) / 2f;
        mTop = (getHeight() - mMeasuredMovieHeight) / 2f;

        mVisible = getVisibility() == View.VISIBLE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (movie != null) {
            if (!mPaused) {
                updateAnimationTime();
                drawMovieFrame(canvas);
                invalidateView();
            } else {
                drawMovieFrame(canvas);
            }
        }
    }


    @SuppressLint("NewApi")
    private void invalidateView() {
        if (mVisible) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                postInvalidateOnAnimation();
            } else {
                invalidate();
            }
        }
    }

    /**
     * 计算当前动画时间
     */
    private void updateAnimationTime() {
        long now = android.os.SystemClock.uptimeMillis();
        // 如果是第一帧，记录起始时间
        if (mMovieStart == 0) {
            mMovieStart = now;
        }
        // 取出动画的时长
        int dur = movie.duration();
        if (dur == 0) {
            dur = DEFAULT_MOVIE_VIEW_DURATION;
        }
        // 算出需要显示第几帧
        mCurrentAnimationTime = (int) ((now - mMovieStart) % dur);
    }

    /**
     * 绘制当前要显示的Gif帧
     */
    private void drawMovieFrame(Canvas canvas) {

        movie.setTime(mCurrentAnimationTime);

        canvas.save();
        canvas.scale(mScale, mScale);
        movie.draw(canvas, mLeft / mScale, mTop / mScale);
        canvas.restore();
    }

    @SuppressLint("NewApi")
    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        mVisible = screenState == SCREEN_STATE_ON;
        invalidateView();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        mVisible = visibility == View.VISIBLE;
        invalidateView();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mVisible = visibility == View.VISIBLE;
        invalidateView();
    }

}