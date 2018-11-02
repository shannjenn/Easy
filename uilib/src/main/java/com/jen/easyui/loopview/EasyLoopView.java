package com.jen.easyui.loopview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;

import java.util.ArrayList;
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
    private final float TEXT_SIZE_DEFAULT = 16;//SP
    private final int TEXT_COLOR_SELECT_DEFAULT = 0xff0085f2;//选中的颜色
    private final int TEXT_COLOR_UN_SELECT_DEFAULT = 0xffaaaaaa;//未选中的颜色
    private final int TEXT_VERTICAL_SPACING_DEFAULT = 100;//字体垂直间隔
    private final int LINE_COLOR_DEFAULT = 0x660085f2;//线条的颜色 透明
    private final int VIEW_HORIZONTAL_SPACING_DEFAULT = 20;//view左右间隔px
    private final int UNIT_TEXT_COLOR_DEFAULT = 0xff0085f2;//单位颜色
    private final int UNIT_HORIZONTAL_SPACING_DEFAULT = 40;//单位间隔px

    private float textSize = TEXT_SIZE_DEFAULT;
    private int textColorSelect = TEXT_COLOR_SELECT_DEFAULT;
    private int textColorUnSelect = TEXT_COLOR_UN_SELECT_DEFAULT;
    public int textVerticalSpacing = TEXT_VERTICAL_SPACING_DEFAULT;
    private int lineColor = LINE_COLOR_DEFAULT;
    public int viewHorizontalSpacing = VIEW_HORIZONTAL_SPACING_DEFAULT;
    private String unitText;
    private int unitTextColor = UNIT_TEXT_COLOR_DEFAULT;
    public int unitHorizontalSpacing = UNIT_HORIZONTAL_SPACING_DEFAULT;

    Context context;
    private ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;

    public EasyLoopViewListener loopListener;
    private GestureDetector gestureDetector;
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener;
    public EasyLoopViewHandler loopViewHandler;
    private int selectedItem;

    private Paint selectedPaint; // paint that draw center text
    private Paint unselectedPaint; // paint that draw top and bottom text
    private Paint unitPaint; // paint that draw unit text
    private Paint linePaint; // paint that draw line besides center text

    public final ArrayList arrayList = new ArrayList();
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

    /*public LoopView(Context context) {
        super(context);
        initLoopView(context);
    }*/

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
        layoutWidth = a.getLayoutDimension(R.styleable.EasyLoopView_android_layout_width, 0);

        float textSizeXp = EasyDensityUtil.sp2px(textSize);

        selectedPaint = new Paint();
        selectedPaint.setColor(textColorSelect);
        selectedPaint.setAntiAlias(true);
        selectedPaint.setTextScaleX(1.05F);
        selectedPaint.setTypeface(Typeface.MONOSPACE);//字体样式
        selectedPaint.setTextSize(textSizeXp);

        unselectedPaint = new Paint();
        unselectedPaint.setColor(textColorUnSelect);
        unselectedPaint.setAntiAlias(true);
        unselectedPaint.setTypeface(Typeface.MONOSPACE);
        unselectedPaint.setTextSize(textSizeXp);

        unitPaint = new Paint();
        unitPaint.setColor(unitTextColor);
        unitPaint.setAntiAlias(true);
        unitPaint.setTypeface(Typeface.MONOSPACE);
        unitPaint.setTextSize(textSizeXp);

        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setAntiAlias(true);
        linePaint.setTypeface(Typeface.MONOSPACE);
        linePaint.setTextSize(textSizeXp);

        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
        simpleOnGestureListener = new EasyLoopViewGestureListener(this);
        gestureDetector = new GestureDetector(context, simpleOnGestureListener);
        gestureDetector.setIsLongpressEnabled(false);
        loopViewHandler = new EasyLoopViewHandler(this);
    }

    private void initData() {
        Rect rect = new Rect();
        for (int i = 0, len = arrayList.size(); i < len; i++) {
            String s1 = (String) arrayList.get(i);
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

        halfCircumference = (maxTextHeight + textVerticalSpacing) * (itemCount - 1);
        measuredHeight = (int) ((halfCircumference * 2) / Math.PI);
        radius = (int) (halfCircumference / Math.PI);
        firstLineY = (int) ((measuredHeight - (maxTextHeight + textVerticalSpacing)) / 2.0F);
        secondLineY = (int) ((measuredHeight + (maxTextHeight + textVerticalSpacing)) / 2.0F);
//        preCurrentIndex = initPosition;
        totalScrollY = 0;
        rawY = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int specMode = MeasureSpec.getMode(widthMeasureSpec);//得到模式
//        int specSize = MeasureSpec.getSize(widthMeasureSpec);//得到大小
        if (layoutWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
            int widthSpec = maxTextWidth + unitTextWidth + unitHorizontalSpacing + viewHorizontalSpacing * 2;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSpec, MeasureSpec.EXACTLY);
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
        if (arrayList.size() == 0) {
            super.onDraw(canvas);
            return;
        }
        int size = arrayList.size();
        String[] as = new String[itemCount];
        int change = totalScrollY / ((maxTextHeight + textVerticalSpacing));
        preCurrentIndex = initPosition + change % size;
        if (preCurrentIndex < 0) {
            preCurrentIndex = size + preCurrentIndex;
        }
        if (preCurrentIndex > size - 1) {
            preCurrentIndex = preCurrentIndex - size;
        }

        int j2 = totalScrollY % (maxTextHeight + textVerticalSpacing);
        int k1 = 0;
        while (k1 < itemCount) {
            int l1 = preCurrentIndex - (itemCount / 2 - k1);
            if (l1 < 0)
                l1 = l1 + size;
            if (l1 > size - 1)
                l1 = l1 % size;
            if (size == 1)
                l1 = 0;
            as[k1] = (String) arrayList.get(l1);
            k1++;
        }
        canvas.drawLine(0.0F, firstLineY, measuredWidth, firstLineY, linePaint);
        canvas.drawLine(0.0F, secondLineY, measuredWidth, secondLineY, linePaint);

        int left;
        if (unitText != null) {
            left = (measuredWidth - maxTextWidth - unitTextWidth - unitHorizontalSpacing) / 2;

            canvas.save();
            canvas.clipRect(0, 0, measuredWidth, measuredHeight);
            canvas.drawText(unitText, left + maxTextWidth + unitHorizontalSpacing, measuredHeight / 2 + unitTextWidth / 2, unitPaint);
            canvas.restore();
        } else {
            left = (measuredWidth - maxTextWidth) / 2;
        }

        onDrawLoopView(canvas, as, arrayList, itemCount, j2, left);
        super.onDraw(canvas);
    }

    private void onDrawLoopView(Canvas canvas, String[] as, ArrayList arrayList, int itemCount, int j2, int left) {
        int j1 = 0;
        while (j1 < itemCount) {
            canvas.save();
            int itemHeight = maxTextHeight + textVerticalSpacing;
            double radian = ((itemHeight * j1 - j2) * Math.PI) / halfCircumference;
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
                    canvas.drawText(as[j1], left, maxTextHeight, unselectedPaint);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, firstLineY - translateY, measuredWidth, itemHeight);
                    canvas.drawText(as[j1], left, maxTextHeight, selectedPaint);
                    canvas.restore();
                } else if (translateY <= secondLineY && maxTextHeight + translateY >= secondLineY) {
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, secondLineY - translateY);
                    canvas.drawText(as[j1], left, maxTextHeight, selectedPaint);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, secondLineY - translateY, measuredWidth, itemHeight);
                    canvas.drawText(as[j1], left, maxTextHeight, unselectedPaint);
                    canvas.restore();
                } else if (translateY >= firstLineY && maxTextHeight + translateY <= secondLineY) {
                    canvas.clipRect(0, 0, measuredWidth, itemHeight);
                    canvas.drawText(as[j1], left, maxTextHeight, selectedPaint);
                    selectedItem = arrayList.indexOf(as[j1]);
                } else {
                    canvas.clipRect(0, 0, measuredWidth, itemHeight);
                    canvas.drawText(as[j1], left, maxTextHeight, unselectedPaint);
                }
                canvas.restore();
            }
            j1++;
        }
    }

    public void smoothScroll() {
        int offset = totalScrollY % ((maxTextHeight + textVerticalSpacing));
        cancelFuture();
        mFuture = mExecutor.scheduleWithFixedDelay(new EasyLoopViewScrollOffsetTimerTask(this, offset),
                0, 10, TimeUnit.MILLISECONDS);
    }

    public final void smoothScroll(float velocityY) {
        cancelFuture();
        int velocityFling = 20;
        mFuture = mExecutor.scheduleWithFixedDelay(new EasyLoopViewScrollVelocityTimerTask(this, velocityY),
                0, velocityFling, TimeUnit.MILLISECONDS);
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

    public final void setArrayList(ArrayList list) {
        if (list != null && list.size() > 0) {
            arrayList.clear();
            arrayList.addAll(list);
        }
        initData();
        invalidate();
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void itemSelected() {
        if (loopListener != null) {
            postDelayed(new EasyLoopViewRunnable(this), 200L);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionevent) {
        switch (motionevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                rawY = motionevent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float tempRawY = motionevent.getRawY();
                totalScrollY = (int) ((float) totalScrollY + rawY - tempRawY);
                rawY = tempRawY;
                break;
            case MotionEvent.ACTION_UP:
            default:
                if (!gestureDetector.onTouchEvent(motionevent)
                        && motionevent.getAction() == MotionEvent.ACTION_UP) {
                    smoothScroll();
                }
                return true;
        }
        invalidate();

        if (!gestureDetector.onTouchEvent(motionevent)
                && motionevent.getAction() == MotionEvent.ACTION_UP) {
            smoothScroll();
        }
        return true;
    }

    public void setListener(EasyLoopViewListener LoopListener) {
        loopListener = LoopListener;
    }

    public void setTextSize(float size) {
        textSize = size;
        float textSizeXp = EasyDensityUtil.sp2px(textSize);
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
