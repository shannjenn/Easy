package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;

/**
 * RadioButton/CheckBox功能
 * 作者：ShannJenn
 * 时间：2018/06/07.
 */

public class EasyRadioButton extends RelativeLayout {
    private Context mContext;
    private int radioShowType;
    private Drawable radioSrc;
    private String radioText;
    private float radioTextSize;
    private int radioTextPadding;
    private int radioTextColor;
    private boolean radioIsCheck;
    private boolean radioCheckBox;//作为CheckBox
    private int radioCheckPadding;
    private boolean radioShowBottomLine;
    private int radioBottomLineColor;

    private RadioButton radioTextView;
    private RadioButton radioImgView;
    private View bottomLine;

    private GroupListener groupListener;

    /*public EasyEditTextManager(Context context) {
        super(context);
        mShape = new EasyShapeBase(this);
        initAttrs(context, null);
    }*/

    public EasyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initAttrs(context, attrs);
        initView();
    }

    public EasyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(context, attrs);
        initView();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyRadioButton);
        radioText = ta.getString(R.styleable.EasyRadioButton_radioText);
        radioTextColor = ta.getColor(R.styleable.EasyRadioButton_radioTextColor, 0xff333333);
        radioTextSize = ta.getDimension(R.styleable.EasyRadioButton_radioTextSize, EasyDensityUtil.sp2px(context, 14));
        radioTextPadding = ta.getDimensionPixelOffset(R.styleable.EasyRadioButton_radioTextPadding, 0);

        radioSrc = ta.getDrawable(R.styleable.EasyRadioButton_radioSrc);
        radioShowType = ta.getInt(R.styleable.EasyRadioButton_radioShowType, 0);
        radioIsCheck = ta.getBoolean(R.styleable.EasyRadioButton_radioIsCheck, false);
        radioCheckBox = ta.getBoolean(R.styleable.EasyRadioButton_radioCheckBox, false);
        radioCheckPadding = ta.getDimensionPixelOffset(R.styleable.EasyRadioButton_radioCheckPadding, 0);
        radioShowBottomLine = ta.getBoolean(R.styleable.EasyRadioButton_radioShowBottomLine, false);
        radioBottomLineColor = ta.getColor(R.styleable.EasyRadioButton_radioBottomLineColor, 0xff333333);

        ta.recycle();
    }

    private void initView() {
        radioTextView = new RadioButton(mContext);
        radioTextView.setBackground(null);
        radioTextView.setButtonDrawable(null);
        radioTextView.setText(radioText);
        radioTextView.setTextColor(radioTextColor);
        radioTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, radioTextSize);
        radioTextView.setClickable(false);

        radioImgView = new RadioButton(mContext);
        radioImgView.setBackground(null);
        radioImgView.setButtonDrawable(null);
        radioImgView.setCompoundDrawablesWithIntrinsicBounds(null, null, radioSrc, null);
        radioImgView.setChecked(radioIsCheck);
        radioImgView.setClickable(false);

        bottomLine = new View(mContext);
        bottomLine.setBackgroundColor(mContext.getResources().getColor(radioBottomLineColor));

        LayoutParams radioTextParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutParams radioImgParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutParams bottomLineParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, EasyDensityUtil.dip2px(mContext, 0.5f));
        radioTextParam.addRule(RelativeLayout.CENTER_VERTICAL);
        radioImgParam.addRule(RelativeLayout.CENTER_VERTICAL);
        bottomLineParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        radioTextView.setLayoutParams(radioTextParam);
        radioImgView.setLayoutParams(radioImgParam);
        bottomLine.setLayoutParams(bottomLineParam);
        bottomLine.setVisibility(radioShowBottomLine ? VISIBLE : GONE);
        if (radioShowType == 0) {
            radioTextParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            radioImgParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            radioTextParam.leftMargin = radioTextPadding;
            radioImgParam.rightMargin = radioCheckPadding;
        } else {
            radioTextParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            radioImgParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            radioTextParam.rightMargin = radioTextPadding;
            radioImgParam.leftMargin = radioCheckPadding;
        }

        addView(radioTextView);
        addView(radioImgView);
        addView(bottomLine);
        setClickable(true);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                if (radioCheckBox) {
                    radioImgView.setChecked(!radioImgView.isChecked());
                } else {
                    radioImgView.setChecked(true);
                }
                if (groupListener != null) {
                    groupListener.check(this);
                }
                break;
            }
            default: {

                break;
            }
        }
        return super.onTouchEvent(event);
    }

    public void setCheck(boolean check) {
        radioImgView.setChecked(check);
    }

    public boolean isCheck() {
        return radioImgView.isChecked();
    }

    public void setText(String text) {
        radioTextView.setText(text);
    }

    interface GroupListener {
        void check(EasyRadioButton button);
    }


    GroupListener getGroupListener() {
        return groupListener;
    }

    void setGroupListener(GroupListener groupListener) {
        this.groupListener = groupListener;
    }

}