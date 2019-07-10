package com.jen.easyui.view.loopview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 说明：滚轮视图
 * 作者：ShannJenn
 * 时间：2018/07/31.
 */
public class EasyLoopView extends View {
    //默认值
//    private final float TEXT_SIZE_DEFAULT = 16;//SP
//    private final int TEXT_COLOR_SELECT_DEFAULT = 0xff0085f2;//选中的颜色
//    private final int TEXT_COLOR_UN_SELECT_DEFAULT = 0xffaaaaaa;//未选中的颜色
//    private final int TEXT_VERTICAL_MARGIN_DEFAULT = 100;//字体垂直间隔
//    private final int LINE_COLOR_DEFAULT = 0x660085f2;//线条的颜色 透明
//    private final int VIEW_HORIZONTAL_MARGIN_DEFAULT = 20;//view左右间隔px
//    private final int UNIT_TEXT_COLOR_DEFAULT = 0xff0085f2;//单位颜色
//    private final int UNIT_HORIZONTAL_MARGIN_DEFAULT = 40;//单位间隔px

    private int textSize;
    private int textColorSelect;
    private int textColorUnSelect;
    public int textVerticalMargin;

    public int viewHorizontalMargin;

    private int lineColor;

    private String unitText;
    private int unitTextColor;
    public int unitHorizontalMargin;

    Context context;
    private ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;

    public EasyLoopViewListener loopListener;
    private GestureDetector gestureDetector;
    private int selectedItem;

    private Paint selectedPaint; // paint that draw center text
    private Paint unselectedPaint; // paint that draw top and bottom text
    private Paint unitPaint; // paint that draw unit text
    private Paint linePaint; // paint that draw line besides center text

    public final List<String> mData = new ArrayList<>();
    private int maxTextWidth;
    private int unitTextWidth;
    public int maxTextHeight;

    private int firstLineY;
    private int secondLineY;

    private int preCurrentIndex;
    private int initPosition;
    private int itemCount = 7;

    private int measuredHeight;
    private int measuredWidth;
    private int halfCircumference;
    private int radius;

    public int totalScrollY;
    private float rawY;

    private int layoutWidth;
    private int layoutHeight;
    private float layoutWeight;

    /*public LoopView(Context context) {
        super(context);
        initLoopView(context);
    }*/

    public interface EasyLoopViewListener {
        void onItemSelect(EasyLoopView loopView, int item);
    }


    static final int H_INVALIDATE = 1000;
    static final int H_SMOOTH_SCROLL = 2000;
    static final int H_ITEM_SELECTED = 3000;
    private Handler loopViewHandler = new Handler(Looper.myLooper()) {
        @Override
        public final void handleMessage(Message msg) {
            if (msg.what == H_INVALIDATE)
                invalidate();
            while (true) {
                if (msg.what == H_SMOOTH_SCROLL)
                    smoothScroll();
                else if (msg.what == H_ITEM_SELECTED)
                    itemSelected();
                super.handleMessage(msg);
                return;
            }
        }
    };

    class ScrollOffsetTimerTask extends TimerTask {
        int realTotalOffset;
        int realOffset;
        int offset;

        ScrollOffsetTimerTask(int offset) {
            super();
            this.offset = offset;
            realTotalOffset = Integer.MAX_VALUE;
            realOffset = 0;
        }

        @Override
        public final void run() {
            if (realTotalOffset == Integer.MAX_VALUE) {
                float itemHeight = textVerticalMargin * maxTextHeight;
                offset = (int) ((offset + itemHeight) % itemHeight);
                if ((float) offset > itemHeight / 2.0F) {
                    realTotalOffset = (int) (itemHeight - (float) offset);
                } else {
                    realTotalOffset = -offset;
                }
            }
            realOffset = (int) ((float) realTotalOffset * 0.4F);
            if (realOffset == 0) {
                if (realTotalOffset < 0) {
                    realOffset = -1;
                } else {
                    realOffset = 1;
                }
            }
            if (Math.abs(realTotalOffset) <= 0) {
                cancelFuture();
                loopViewHandler.sendMessageDelayed(loopViewHandler.obtainMessage(H_ITEM_SELECTED), 200L);
            } else {
                totalScrollY = totalScrollY + realOffset;
                loopViewHandler.sendMessage(loopViewHandler.obtainMessage(H_INVALIDATE));
                realTotalOffset = realTotalOffset - realOffset;
            }
        }
    }

    class ScrollVelocityTimerTask extends TimerTask {
        float tempY;
        final float velocityY;

        ScrollVelocityTimerTask(float velocityY) {
            super();
            this.velocityY = velocityY;
            tempY = Integer.MAX_VALUE;
        }

        @Override
        public final void run() {
            if (tempY == Integer.MAX_VALUE) {
                if (Math.abs(velocityY) > 2000F) {
                    if (velocityY > 0.0F) {
                        tempY = 2000F;
                    } else {
                        tempY = -2000F;
                    }
                } else {
                    tempY = velocityY;
                }
            }
            if (Math.abs(tempY) >= 0.0F && Math.abs(tempY) <= 20F) {
                cancelFuture();
                loopViewHandler.sendMessage(loopViewHandler.obtainMessage(H_SMOOTH_SCROLL));
                return;
            }
            int i = (int) ((tempY * 10F) / 1000F);
            totalScrollY = totalScrollY - i;
            if (tempY < 0.0F) {
                tempY = tempY + 20F;
            } else {
                tempY = tempY - 20F;
            }
            loopViewHandler.sendMessage(loopViewHandler.obtainMessage(H_INVALIDATE));
        }
    }

    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public final boolean onDown(MotionEvent motionevent) {
            cancelFuture();
            return true;
        }

        @Override
        public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            smoothScroll(velocityY * 1.2f);
            return true;
        }
    };

    public EasyLoopView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        initLoopView(context, attributeset);
    }

    public EasyLoopView(Context context, AttributeSet attributeset, int defStyleAttr) {
        super(context, attributeset, defStyleAttr);
        initLoopView(context, attributeset);
    }

    private void initLoopView(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EasyLoopView);
        int textSizeXp = EasyDensityUtil.sp2pxInt(16);

        textSize = a.getDimensionPixelOffset(R.styleable.EasyLoopView_loopViewTextSize, textSizeXp);
        textColorSelect = a.getColor(R.styleable.EasyLoopView_loopViewTextSelectColor, 0xff0085f2);
        textColorUnSelect = a.getColor(R.styleable.EasyLoopView_loopViewTextUnSelectColor, 0xffaaaaaa);
        textVerticalMargin = a.getDimensionPixelOffset(R.styleable.EasyLoopView_loopViewTextVerticalMargin, 30);

        viewHorizontalMargin = a.getDimensionPixelOffset(R.styleable.EasyLoopView_loopViewHorizontalMargin, 20);

        lineColor = a.getColor(R.styleable.EasyLoopView_loopViewLineColor, 0x660085f2);

        unitText = a.getString(R.styleable.EasyLoopView_loopViewUnitText);
        unitTextColor = a.getColor(R.styleable.EasyLoopView_loopViewUnitTextColor, 0xff0085f2);
        unitHorizontalMargin = a.getColor(R.styleable.EasyLoopView_loopViewUnitHorizontalMargin, 15);

        layoutWidth = a.getLayoutDimension(R.styleable.EasyLoopView_android_layout_width, 0);
        layoutHeight = a.getLayoutDimension(R.styleable.EasyLoopView_android_layout_height, 0);
        layoutWeight = a.getFloat(R.styleable.EasyLoopView_android_layout_weight, 0f);

        a.recycle();

        selectedPaint = new Paint();
        selectedPaint.setColor(textColorSelect);
        selectedPaint.setAntiAlias(true);
        selectedPaint.setTextScaleX(1.05F);
        selectedPaint.setTypeface(Typeface.MONOSPACE);//字体样式
        selectedPaint.setTextSize(textSize);

        unselectedPaint = new Paint();
        unselectedPaint.setColor(textColorUnSelect);
        unselectedPaint.setAntiAlias(true);
        unselectedPaint.setTypeface(Typeface.MONOSPACE);
        unselectedPaint.setTextSize(textSize);

        unitPaint = new Paint();
        unitPaint.setColor(unitTextColor);
        unitPaint.setAntiAlias(true);
        unitPaint.setTypeface(Typeface.MONOSPACE);
        unitPaint.setTextSize(textSize);

        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setAntiAlias(true);
        linePaint.setTypeface(Typeface.MONOSPACE);
        linePaint.setTextSize(textSize);

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        gestureDetector = new GestureDetector(context, gestureListener);
        gestureDetector.setIsLongpressEnabled(false);
//        loopViewHandler = new EasyLoopViewHandler(this);
    }

    private void initData() {
        Rect rect = new Rect();
        for (int i = 0, len = mData.size(); i < len; i++) {
            String s1 = mData.get(i);
            selectedPaint.getTextBounds(s1, 0, s1.length(), rect);
            int textWidth = rect.width();
            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth;
            }
            selectedPaint.getTextBounds("\u661F\u671F", 0, 2, rect);
            int textHeight = rect.height();
            if (textHeight > maxTextHeight) {
                maxTextHeight = textHeight;
            }
        }
        if (unitText != null) {
            unitPaint.getTextBounds(unitText, 0, unitText.length(), rect);
            unitTextWidth = rect.width();
        }

        halfCircumference = (maxTextHeight + textVerticalMargin) * (itemCount - 1);
        measuredHeight = (int) ((halfCircumference * 2) / Math.PI);
        radius = (int) (halfCircumference / Math.PI);
        firstLineY = (int) ((measuredHeight - (maxTextHeight + textVerticalMargin)) / 2.0F);
        secondLineY = (int) ((measuredHeight + (maxTextHeight + textVerticalMargin)) / 2.0F);
//        preCurrentIndex = initPosition;
        totalScrollY = 0;
        rawY = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int specMode = MeasureSpec.getMode(widthMeasureSpec);//得到模式
//        int specSize = MeasureSpec.getSize(widthMeasureSpec);//得到大小
        if (layoutWidth == ViewGroup.LayoutParams.WRAP_CONTENT && layoutWeight == 0) {
            int widthSpec = maxTextWidth + unitTextWidth + unitHorizontalMargin + viewHorizontalMargin * 2;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSpec, MeasureSpec.EXACTLY);
        }
        if (layoutHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight + 10, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mData.size() == 0) {
            super.onDraw(canvas);
            return;
        }
        int size = mData.size();
        int change = totalScrollY / ((maxTextHeight + textVerticalMargin));
        preCurrentIndex = initPosition + change % size;
        if (preCurrentIndex < 0) {
            preCurrentIndex = size + preCurrentIndex;
        }
        if (preCurrentIndex > size - 1) {
            preCurrentIndex = preCurrentIndex - size;
        }

        int scrollY = totalScrollY % (maxTextHeight + textVerticalMargin);
        canvas.drawLine(0.0F, firstLineY, measuredWidth, firstLineY, linePaint);
        canvas.drawLine(0.0F, secondLineY, measuredWidth, secondLineY, linePaint);

        int left;
        if (unitText != null) {
            left = (measuredWidth - maxTextWidth - unitTextWidth - unitHorizontalMargin) / 2;
            canvas.save();
            canvas.clipRect(0, 0, measuredWidth, measuredHeight);
            canvas.drawText(unitText, left + maxTextWidth + unitHorizontalMargin, measuredHeight / 2 + unitTextWidth / 2, unitPaint);
            canvas.restore();
        } else {
            left = (measuredWidth - maxTextWidth) / 2;
        }
        List<String> texts = getTexts();
        onDrawLoopView(canvas, texts, scrollY, left);
        super.onDraw(canvas);
    }

    /**
     * 获取画文字
     *
     * @return .
     */
    private List<String> getTexts() {
        int size = mData.size();
        List<String> texts = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            int position = preCurrentIndex - (itemCount / 2 - i);
            if (position < 0)
                position = position + size;
            if (position > size - 1)
                position = position % size;
            if (size == 1)
                position = 0;
            texts.add(mData.get(position));
        }
        return texts;
    }

    private void onDrawLoopView(Canvas canvas, List<String> texts, int scrollY, int left) {
        int count = texts.size();
        for (int i = 0; i < count; i++) {
            canvas.save();
            int itemHeight = maxTextHeight + textVerticalMargin;
            double radian = ((itemHeight * i - scrollY) * Math.PI) / halfCircumference;
            float angle = (float) (90D - (radian / Math.PI) * 180D);
            if (angle >= 90F || angle <= -90F) {
                canvas.restore();
            } else {
                int translateY = (int) (radius - Math.cos(radian) * radius - (Math.sin(radian) * maxTextHeight) / 2D);
                canvas.translate(0.0F, translateY);
                canvas.scale(1.0F, (float) Math.sin(radian));
                if (translateY <= firstLineY && maxTextHeight + translateY >= firstLineY) {
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, firstLineY - translateY);
                    canvas.drawText(texts.get(i), left, maxTextHeight, unselectedPaint);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, firstLineY - translateY, measuredWidth, itemHeight);
                    canvas.drawText(texts.get(i), left, maxTextHeight, selectedPaint);
                    canvas.restore();
                } else if (translateY <= secondLineY && maxTextHeight + translateY >= secondLineY) {
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, secondLineY - translateY);
                    canvas.drawText(texts.get(i), left, maxTextHeight, selectedPaint);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, secondLineY - translateY, measuredWidth, itemHeight);
                    canvas.drawText(texts.get(i), left, maxTextHeight, unselectedPaint);
                    canvas.restore();
                } else if (translateY >= firstLineY && maxTextHeight + translateY <= secondLineY) {
                    canvas.clipRect(0, 0, measuredWidth, itemHeight);
                    canvas.drawText(texts.get(i), left, maxTextHeight, selectedPaint);
                    selectedItem = mData.indexOf(texts.get(i));
                } else {
                    canvas.clipRect(0, 0, measuredWidth, itemHeight);
                    canvas.drawText(texts.get(i), left, maxTextHeight, unselectedPaint);
                }
                canvas.restore();
            }
        }
    }

    public void smoothScroll() {
        cancelFuture();
        int offset = totalScrollY % ((maxTextHeight + textVerticalMargin));
        mFuture = mExecutor.scheduleWithFixedDelay(new ScrollOffsetTimerTask(offset), 0, 10, TimeUnit.MILLISECONDS);
    }

    public final void smoothScroll(float velocityY) {
        cancelFuture();
        mFuture = mExecutor.scheduleWithFixedDelay(new ScrollVelocityTimerTask(velocityY), 0, 20, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture() {
        if (mFuture != null && !mFuture.isCancelled()) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    public final void setInitPosition(int initPosition) {
        this.initPosition = initPosition;
    }

    public final void setData(List<String> list) {
        if (list != null && list.size() > 0) {
            mData.clear();
            mData.addAll(list);
        }
        initData();
        invalidate();
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void itemSelected() {
        if (loopListener != null) {
            int selectedItem = getSelectedItem();
            loopListener.onItemSelect(this, selectedItem);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionevent) {
        switch (motionevent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                rawY = motionevent.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float tempRawY = motionevent.getRawY();
                totalScrollY = (int) ((float) totalScrollY + rawY - tempRawY);
                rawY = tempRawY;
                break;
            }
            case MotionEvent.ACTION_UP: {
            }
            default: {
                if (!gestureDetector.onTouchEvent(motionevent) && motionevent.getAction() == MotionEvent.ACTION_UP) {
                    smoothScroll();
                }
                return true;
            }
        }
        invalidate();
        if (!gestureDetector.onTouchEvent(motionevent) && motionevent.getAction() == MotionEvent.ACTION_UP) {
            smoothScroll();
        }
        return true;
    }

    public void setListener(EasyLoopViewListener LoopListener) {
        loopListener = LoopListener;
    }

    public void setTextSize(Integer size) {
        textSize = size;
        float textSizeXp = EasyDensityUtil.sp2pxFloat(textSize);
        unselectedPaint.setTextSize(textSizeXp);
        selectedPaint.setTextSize(textSizeXp);
        unitPaint.setTextSize(textSizeXp);
        linePaint.setTextSize(textSizeXp);
    }

    public void setUnitText(String unitText) {
        this.unitText = unitText;
    }

    public void setUnitTextColor(int unitTextColor) {
        this.unitTextColor = unitTextColor;
        unitPaint.setColor(unitTextColor);
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        linePaint.setColor(lineColor);
    }

    public void setTextColorSelect(int textColorSelect) {
        this.textColorSelect = textColorSelect;
        selectedPaint.setColor(textColorSelect);
    }

    public void setTextColorUnSelect(int textColorUnSelect) {
        this.textColorUnSelect = textColorUnSelect;
        unselectedPaint.setColor(textColorUnSelect);
    }
}
