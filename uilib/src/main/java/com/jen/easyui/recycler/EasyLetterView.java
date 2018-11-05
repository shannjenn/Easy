package com.jen.easyui.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;

/**
 * 说明：
 * 1.自定义字符串请在布局实现lettersByComma属性（值用逗号隔开）
 * 2.顶部和底部有遮挡，设置paddingTop和paddingBottom
 * 3.点击时该View宽度全屏，注意设置布局位置
 * 作者：ShannJenn
 * 时间：2018/03/14.
 */

public class EasyLetterView extends View {

    private Context context;
    private Paint backgroundPaint;
    private Paint letterPaint;
    private Paint textDialogPaint;
    private Paint textDialogBackgroundPaint;
    private int choosePosition = -1;//当前手指滑动到的位置
    private TouchListener touchListener;
    private final String[] LETTERS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};//默认字母
    private String[] letters;//自定义Letter逗号隔开
    //    private int spacingTop;//垂直间距（顶部）
//    private int spacingBottom;//垂直间距（底部）
    private int letterPerHeight;//字母平均高度

    private int height;
    private int width;
    private int paddingTop;
    private int paddingBottom;

    private int textDefaultColor;
    private int textTouchColor;
    private float textSize;

    private int backgroundTouchColor;
    private int backgroundColor;

    private String lettersByComma;

    private int dialogTextBackground;
    private int dialogTextBackgroundWith;
    private int dialogTextBackgroundHeight;
    private float dialogTextSize;
    private int dialogTextColor;
    private int dialogTextMarginRight;

    /*public EasyLetterView(Context context) {
        this(context, null);
    }*/

    public EasyLetterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initAttrs(context, attrs);
    }

    public EasyLetterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyLetterView);

        height = ta.getLayoutDimension(R.styleable.EasyLetterView_android_layout_height, 0);
        width = ta.getLayoutDimension(R.styleable.EasyLetterView_android_layout_width, 0);
        paddingTop = ta.getLayoutDimension(R.styleable.EasyLetterView_android_paddingTop, 0);
        paddingBottom = ta.getLayoutDimension(R.styleable.EasyLetterView_android_paddingBottom, 0);

        textTouchColor = ta.getColor(R.styleable.EasyLetterView_textTouchColor, Color.RED);
        textDefaultColor = ta.getColor(R.styleable.EasyLetterView_textDefaultColor, Color.BLACK);
        textSize = ta.getDimension(R.styleable.EasyLetterView_textSize, EasyDensityUtil.sp2px(14.0f));

        backgroundTouchColor = ta.getColor(R.styleable.EasyLetterView_backgroundTouchColor, Color.TRANSPARENT);
        backgroundColor = ta.getLayoutDimension(R.styleable.EasyLetterView_android_background, Color.TRANSPARENT);
//        backgroundDefaultColor = ta.getColor(R.styleable.EasyLetterView_backgroundDefaultColor, Color.TRANSPARENT);

        lettersByComma = ta.getString(R.styleable.EasyLetterView_lettersByComma);//自定义Letter逗号隔开

        dialogTextBackground = ta.getResourceId(R.styleable.EasyLetterView_dialogTextBackground, -1);
        dialogTextBackgroundWith = ta.getDimensionPixelSize(R.styleable.EasyLetterView_dialogTextBackgroundWith, 0);
        dialogTextBackgroundHeight = ta.getDimensionPixelSize(R.styleable.EasyLetterView_dialogTextBackgroundHeight, 0);
        dialogTextSize = ta.getDimension(R.styleable.EasyLetterView_dialogTextSize, EasyDensityUtil.sp2px(14.0f));
        dialogTextColor = ta.getColor(R.styleable.EasyLetterView_dialogTextColor, Color.RED);
        dialogTextMarginRight = ta.getDimensionPixelSize(R.styleable.EasyLetterView_dialogTextMarginRight, 0);

        ta.recycle();
        init();
    }

    private void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
//        backgroundPaint.setTextSize(textSize);

        letterPaint = new Paint();
        letterPaint.setAntiAlias(true);
        letterPaint.setTextSize(textSize);

        textDialogPaint = new Paint();
        textDialogPaint.setAntiAlias(true);
        textDialogPaint.setTextSize(dialogTextSize);

        textDialogBackgroundPaint = new Paint();
        textDialogBackgroundPaint.setAntiAlias(true);
//        textDialogBackgroundPaint.setTextSize(textSize);

        if (lettersByComma != null && lettersByComma.trim().length() != 0) {
            letters = lettersByComma.split(",");//自定义Letter逗号隔开
        } else {
            letters = LETTERS;
        }

        //计算垂直间距（顶部和底部padding）
        /*if (dialogTextBackgroundHeight > 0) {
            spacingTop = dialogTextBackgroundHeight / 4 + paddingTop;
            spacingBottom = dialogTextBackgroundHeight / 4 + paddingBottom;
        } else {
            spacingTop = dialogTextSize / 2 + paddingTop;
            spacingBottom = dialogTextSize / 2 + paddingBottom;
        }
        spacingTop = 0;
        spacingBottom = 0;*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Log.d(TAG, "onDraw choosePosition = " + choosePosition);
        if (letterPerHeight == 0) {
            letterPerHeight = (getHeight() - paddingTop - paddingBottom) / letters.length;
        }
        drawBackground(canvas);
        drawLetters(canvas);
        drawChooseText(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        float left;
        if (choosePosition == -1) {
            backgroundPaint.setColor(backgroundColor);
            left = 0;
        } else {
            backgroundPaint.setColor(backgroundTouchColor);
            if (width < 0) {
                left = 0;
            } else {
                left = getWidth() - width;
            }
        }
        canvas.drawRect(left, 0, getWidth(), getHeight(), backgroundPaint);
    }

    /**
     * 绘制Letters
     *
     * @param canvas
     */
    private void drawLetters(Canvas canvas) {
        for (int i = 0; i < letters.length; i++) {
            if (i == choosePosition) {
                letterPaint.setColor(textTouchColor);
            } else {
                letterPaint.setColor(textDefaultColor);
            }
            Rect letterRect = new Rect();
            letterPaint.getTextBounds(letters[i], 0, letters[i].length(), letterRect);
            float letterWidth = letterPaint.measureText(letters[i]);
            float x;
            float y = i * letterPerHeight + letterPerHeight / 2 + letterRect.height() / 2 + paddingTop;
            if (width < 0) {
                int differ = (int) EasyDensityUtil.dp2px(20);//当布局宽度不固定时，暂时不做算法
                x = getWidth() - (differ + letterWidth) / 2;
            } else {
                x = getWidth() - (width + letterWidth) / 2;
            }
            canvas.drawText(letters[i], x, y, letterPaint);
        }
    }

    /**
     * 绘制提示字符
     *
     * @param canvas
     */
    private void drawChooseText(Canvas canvas) {
        if (choosePosition == -1) {
            return;
        }
        textDialogPaint.setColor(dialogTextColor);
        String target = letters[choosePosition];
        Rect letterRect = new Rect();
        letterPaint.getTextBounds(target, 0, target.length(), letterRect);

        int letterWidth = (int) textDialogPaint.measureText(target);
        int centerX = getWidth() - width - dialogTextBackgroundWith / 2 - dialogTextMarginRight;
        int centerY = choosePosition * letterPerHeight + letterPerHeight / 2 + letterRect.height() / 2 + paddingTop;

        Drawable drawable = getResources().getDrawable(dialogTextBackground);
        if (drawable != null) {
            drawable.setBounds(centerX - dialogTextBackgroundWith / 2, centerY - dialogTextBackgroundHeight / 2,
                    centerX + dialogTextBackgroundWith / 2, centerY + dialogTextBackgroundHeight / 2);
            drawable.draw(canvas);
        }
        Rect rect = new Rect();
        textDialogPaint.getTextBounds(target, 0, 1, rect);
        canvas.drawText(target, centerX - letterWidth / 2, centerY + rect.height() / 2, textDialogPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                setBackgroundColor(backgroundColor);
                choosePosition = -1;
                if (getLayoutParams().width != width) {
                    getLayoutParams().width = width;
                    requestLayout();
                }
                invalidate();
                break;
            default: {
                float y = event.getY();
                int currentPosition = (int) ((y - paddingTop) / letterPerHeight);
                if (currentPosition > -1 && currentPosition < letters.length) {
                    String letter = letters[currentPosition];
                    setBackgroundColor(backgroundTouchColor);
                    if (currentPosition > -1 && currentPosition < letters.length) {
                        if (touchListener != null) {
                            touchListener.onTouch(letter);
                        }
                        choosePosition = currentPosition;
                    }
                    if (getLayoutParams().width != -1) {
                        getLayoutParams().width = -1;//MATCH_PARENT
                        requestLayout();
                    }
                    invalidate();
                }
                break;
            }
        }
        return true;
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(-1);
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(null);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(null);
    }

    public interface TouchListener {
        void onTouch(String letter);
    }

    /**
     * 设置监听触摸事件
     *
     * @param touchListener
     */
    public void setTouchListener(TouchListener touchListener) {
        this.touchListener = touchListener;
    }

    public String[] getLetters() {
        return letters;
    }

    public void setLetters(String[] letters) {
        this.letters = letters;
        invalidate();
    }
}