package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;


/**
 * 上下文字控件
 * 作者：ShannJenn
 * 时间：2019/02/19.
 */

public class UpDownChooseTextView extends View {
//    private final float DEFAULT_TEXT_DOWN_SIZE = 19;//默认字体大小sp

    //    private final int PADDING_LEFT = 15;//db
//    private final int UP_DOWN_TEXT_SPACE = 15;//db 上下间距

    private int measuredHeight;
    private int measuredWidth;
    private int textSizeUp;
    private int textSizeDown;
    private int textColorUp;
    private int textColorDown;
    private int textColorDownChoose;
    private String textUp;
    private String textDown;
    private String textDownHint;
    private boolean choose;
    private boolean upDownAutoScaleUpText;
    private boolean upDownAutoScaleDownText;
    @TextGravity
    private int upDownTextGravity = TextGravity.center;

    private Paint paintUp; // paint that draw center text
    private int baseLineHeightUp;
    private Paint paintDown; // paint that draw top and bottom text
    private int baseLineHeightDown;
    private int textWithUp;
    private int textHeightUp;
    private int textWithDown;
    private int textHeightDown;
    private int chooseTextUpDownSpace;//上下间距

    private int layoutHeight;
    private int layoutWidth;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;

    @IntDef({TextGravity.left, TextGravity.right, TextGravity.center})
    private @interface TextGravity {
        int left = 0;
        int right = 1;
        int center = 2;
    }

    public UpDownChooseTextView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        initAttrs(attributeset);
        initView();
    }

    public UpDownChooseTextView(Context context, AttributeSet attributeset, int defStyleAttr) {
        super(context, attributeset, defStyleAttr);
        initAttrs(attributeset);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        //默认字体大小sp
        float DEFAULT_TEXT_UP_SIZE = 14;//sp
        int DEFAULT_TEXT_DOWN_SIZE = 16;//sp
        //默认字体颜色
        int DEFAULT_TEXT_UP_COLOR = 0XFF333333;
        int DEFAULT_TEXT_DOWN_COLOR = 0XFF999999;
        int DEFAULT_TEXT_DOWN_CHOOSE_COLOR = 0xFF335EC2;

        int defaultTextUpSize = (int) EasyDensityUtil.sp2px(DEFAULT_TEXT_UP_SIZE);
        int defaultTextDownSize = (int) EasyDensityUtil.sp2px(DEFAULT_TEXT_DOWN_SIZE);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.UpDownChooseTextView);
        textUp = a.getString(R.styleable.UpDownChooseTextView_upDownTextUp);
        textDown = a.getString(R.styleable.UpDownChooseTextView_upDownTextDown);
        textDownHint = a.getString(R.styleable.UpDownChooseTextView_upDownTextDownHint);
        textSizeUp = a.getDimensionPixelOffset(R.styleable.UpDownChooseTextView_upDownTextSizeUp, defaultTextUpSize);
        textSizeDown = a.getDimensionPixelOffset(R.styleable.UpDownChooseTextView_upDownTextSizeDown, defaultTextDownSize);
        textColorUp = a.getColor(R.styleable.UpDownChooseTextView_upDownTextColorUp, DEFAULT_TEXT_UP_COLOR);
        textColorDown = a.getColor(R.styleable.UpDownChooseTextView_upDownTextColorDown, DEFAULT_TEXT_DOWN_COLOR);
        textColorDownChoose = a.getColor(R.styleable.UpDownChooseTextView_upDownTextColorDownChoose, DEFAULT_TEXT_DOWN_CHOOSE_COLOR);
        chooseTextUpDownSpace = a.getDimensionPixelOffset(R.styleable.UpDownChooseTextView_upDownTextUpDownSpace, 0);
        choose = a.getBoolean(R.styleable.UpDownChooseTextView_upDownChoose, false);
        upDownAutoScaleUpText = a.getBoolean(R.styleable.UpDownChooseTextView_upDownAutoScaleUpText, true);
        upDownAutoScaleDownText = a.getBoolean(R.styleable.UpDownChooseTextView_upDownAutoScaleDownText, true);
        upDownTextGravity = a.getInt(R.styleable.UpDownChooseTextView_upDownTextGravity, TextGravity.center);

        layoutHeight = a.getLayoutDimension(R.styleable.UpDownChooseTextView_android_layout_height, -1);
        layoutWidth = a.getLayoutDimension(R.styleable.UpDownChooseTextView_android_layout_width, -1);
        paddingLeft = a.getLayoutDimension(R.styleable.UpDownChooseTextView_android_paddingLeft, 0);
        paddingRight = a.getLayoutDimension(R.styleable.UpDownChooseTextView_android_paddingRight, 0);
        paddingTop = a.getLayoutDimension(R.styleable.UpDownChooseTextView_android_paddingTop, 0);
        paddingBottom = a.getLayoutDimension(R.styleable.UpDownChooseTextView_android_paddingBottom, 0);
        a.recycle();

        if (textUp == null)
            textUp = "";
        if (textDown == null)
            textDown = "";
        if (textDownHint == null)
            textDownHint = "";

        paintUp = new Paint();
        paintDown = new Paint();
    }

    private void initView() {
        paintUp.setColor(textColorUp);
        paintUp.setAntiAlias(true);
        paintUp.setTypeface(Typeface.MONOSPACE);
        paintUp.setTextSize(textSizeUp);

        paintDown.setColor(textColorDown);
        paintDown.setAntiAlias(true);
        paintDown.setTypeface(Typeface.MONOSPACE);
        paintDown.setTextSize(textSizeDown);

        Paint.FontMetricsInt upFont = paintUp.getFontMetricsInt();
        Rect rectUp = new Rect();
        paintUp.getTextBounds(textUp, 0, textUp.length(), rectUp);
        textWithUp = rectUp.left + rectUp.right;
        textHeightUp = upFont.bottom - upFont.top;
        baseLineHeightUp = Math.abs(upFont.ascent);

        Paint.FontMetricsInt downFont = paintDown.getFontMetricsInt();
        Rect rectDown = new Rect();
        paintDown.getTextBounds(textDown, 0, textDown.length(), rectDown);
        textWithDown = rectDown.left + rectDown.right;
        textHeightDown = downFont.bottom - downFont.top;
        baseLineHeightDown = Math.abs(downFont.ascent);
    }

    private void update() {
        initView();
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int specMode = MeasureSpec.getMode(widthMeasureSpec);//得到模式
//        int specSize = MeasureSpec.getSize(widthMeasureSpec);//得到大小
        if (layoutHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            int HeightSpec = textHeightUp + textHeightDown + chooseTextUpDownSpace + paddingTop + paddingBottom;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(HeightSpec, MeasureSpec.EXACTLY);
        }
        if (layoutWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
            int widthSpec = textWithUp > textWithDown ? textWithUp : textWithDown + paddingLeft + paddingRight + 20;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSpec, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();
        initData();
    }

    private void initData() {
        if (upDownAutoScaleUpText) {
            autoScaleTextSize(paintUp, textUp, textSizeUp);
        }
        if (upDownAutoScaleDownText) {
            autoScaleTextSize(paintDown, textDown, textSizeDown);
        }
    }

    private void autoScaleTextSize(Paint paint, String text, int textSize) {
        boolean isFit = true;//字体是否合适
        if (measuredWidth > 0) {
            Rect rect = new Rect();
            int onePx = (int) EasyDensityUtil.sp2px(1);
            for (int i = 1; i < 100; i++) {//缩小字体，不能缩小太多，可以用省略号..代替
                paint.getTextBounds(text, 0, text.length(), rect);
                int withDown = rect.left + rect.right;
                if (withDown >= measuredWidth - paddingLeft - paddingRight) {
                    int size = textSize - i;
                    paint.setTextSize(size);
                    if (size <= onePx) {
                        isFit = false;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (!isFit) {//减少字样
            text = text + "..";
            for (int i = 0; i < text.length() - 1; i++) {
                Rect rectDown = new Rect();
                paint.getTextBounds(text, 0, text.length(), rectDown);
                int withDown = rectDown.left + rectDown.right;
                if (withDown >= measuredWidth - paddingLeft - paddingRight) {
                    text = text.replace(".", "");
                    text = text.substring(0, text.length() - 1) + "..";
                } else {
                    break;
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paintDown.setColor(choose ? textColorDownChoose : textColorDown);
        canvas.save();
        canvas.clipRect(0, 0, measuredWidth, measuredHeight);
        String drawDowText;
        if (textDown.length() > 0) {
            drawDowText = textDown;
        } else {
            drawDowText = textDownHint;
        }

        float xUp = 0;
        float xDown = 0;
        switch (upDownTextGravity) {
            case TextGravity.center:
                xUp = (measuredWidth >> 1) - (textWithUp >> 1);
                xDown = (measuredWidth >> 1) - (textWithDown >> 1);
                break;
            case TextGravity.left:
                xUp = paddingLeft;
                xDown = paddingLeft;
                break;
            case TextGravity.right:
                xUp = measuredWidth - textWithUp - paddingLeft;
                xDown = measuredWidth - textWithDown - paddingLeft;
                break;
        }
        float yUp = ((measuredHeight - paddingTop - paddingBottom) >> 1) - (chooseTextUpDownSpace >> 1) - (textHeightUp - baseLineHeightUp) + paddingTop;
        float yDown = ((measuredHeight - paddingTop - paddingBottom) >> 1) + (chooseTextUpDownSpace >> 1) + baseLineHeightDown + paddingTop;

        canvas.drawText(textUp, xUp, yUp, paintUp);
        canvas.restore();
        canvas.save();
        canvas.clipRect(0, 0, measuredWidth, measuredHeight);
        canvas.drawText(drawDowText, xDown, yDown, paintDown);
        super.onDraw(canvas);
    }

    private void setChoose(boolean choose) {
        this.choose = choose;
    }

    public void setTextUp(String textUp) {
        if (textUp == null)
            return;
        this.textUp = textUp;
        update();
    }

    public void setTextDown(String textDown) {
        if (textDown == null)
            return;
        this.textDown = textDown;
        update();
    }

    public String getTextUp() {
        return textUp;
    }

    public String getTextDown() {
        return textDown;
    }

    public void setTextColorUp(int textColorUp) {
        this.textColorUp = textColorUp;
        update();
    }

    public void setTextColorDown(int textColorDown) {
        this.textColorDown = textColorDown;
        update();
    }
}
