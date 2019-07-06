package com.jen.easyui.view.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;


/**
 * item布局
 * 作者：ShannJenn
 * 时间：2018/06/07.
 */

public class EasyItemLayout extends RelativeLayout {
    private Context context;

    private final float DEFAULT_TEXT_SIZE = 14;//默认字体大小sp
    private final int DEFAULT_TEXT_COLOR_TITLE = 0XFF313B40;//默认字体颜色Title
    private final int DEFAULT_TEXT_COLOR_CONTENT = 0XFF313B40;//默认字体颜色content
    private final int DEFAULT_TEXT_COLOR_COUNT = 0XFF6b6b6b;//默认字体颜色count
    private final int TEXT_HINT_COLOR = 0XFFD9D9D9;

    /*是否为输入模式：TRUE显示EditText，隐藏TextView*/
    boolean centerIsEdit;

    TextView easy_item_left_tv;
    TextView easy_item_center_tv;
    EditText easy_item_center_et;
    TextView easy_item_right_tv;
    ImageView easy_item_right_icon;
    ImageView easy_item_center_icon_clear;
    View easy_item_v_bottomLine;

    private int centerEditTextMarginTop;
    private int centerEditTextMarginBottom;

    int leftWidth;
    String leftText;
    int leftTextSize;
    int leftTextColor;
    int titleTextMarginLeft;

    String centerTextText;
    String centerTextHint;
    int centerTextTextSize;
    int centerTextTextColor;
    int centerTextTextHintColor;
    int centerTextTextLines;//默认1
    int centerTextGray;//默认0
    final int GRAY_LEFT = 0, GRAY_CENTER = 1, GRAY_RIGHT = 2;

    int centerEditTextSize;
    int centerEditTextColor;
    int centerEditTextHintColor;
    String centerEditTextHint;
    int centerEditLines = 1;

    int rightWidth;
    String rightText;
    int rightTextSize;
    int rightTextColor;
    int rightMarginRight;
    int rightTextViewGray;

    boolean arrowVisible;
    boolean clearVisible;

    boolean bottomLineVisible;
    int bottomLineMarginLeft;

    public EasyItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs, 0);
        initView();
    }

    public EasyItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs, defStyleAttr);
        initView();
    }

    private void initAttrs(AttributeSet attrs, int defStyleAttr) {
        int defaultTextSize = EasyDensityUtil.dp2pxInt(DEFAULT_TEXT_SIZE);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.EasyItemLayout, defStyleAttr, 0);

        centerIsEdit = a.getBoolean(R.styleable.EasyItemLayout_centerIsEditText, false);
        leftText = a.getString(R.styleable.EasyItemLayout_leftText);

        leftTextSize = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_leftTextSize, defaultTextSize);
        leftWidth = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_leftWidth, -1);
        leftTextColor = a.getColor(R.styleable.EasyItemLayout_leftTextColor, DEFAULT_TEXT_COLOR_TITLE);
        titleTextMarginLeft = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_leftMarginLeft, 0);

        centerTextText = a.getString(R.styleable.EasyItemLayout_centerTextViewText);
        centerTextTextSize = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_centerTextViewTextSize, defaultTextSize);
        centerTextHint = a.getString(R.styleable.EasyItemLayout_centerTextViewTextHint);
        centerTextTextColor = a.getColor(R.styleable.EasyItemLayout_centerTextViewTextColor, DEFAULT_TEXT_COLOR_CONTENT);
        centerTextTextHintColor = a.getColor(R.styleable.EasyItemLayout_centerTextViewTextHintColor, TEXT_HINT_COLOR);
        centerTextTextLines = a.getInt(R.styleable.EasyItemLayout_centerTextViewLines, 1);
        centerTextGray = a.getInt(R.styleable.EasyItemLayout_centerTextViewGray, GRAY_LEFT);

        centerEditTextSize = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_centerEditTextTextSize, defaultTextSize);
        centerEditTextColor = a.getColor(R.styleable.EasyItemLayout_centerEditTextTextColor, DEFAULT_TEXT_COLOR_CONTENT);
        centerEditTextHintColor = a.getColor(R.styleable.EasyItemLayout_centerEditTextTextHintColor, TEXT_HINT_COLOR);
        centerEditTextHint = a.getString(R.styleable.EasyItemLayout_centerEditTextTextHint);
        centerEditLines = a.getInt(R.styleable.EasyItemLayout_centerEditTextLines, 1);
        centerEditTextMarginTop = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_centerEditTextMarginTop, 0);
        centerEditTextMarginBottom = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_centerEditTextMarginBottom, 0);

        rightWidth = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_rightWidth, -1);
        rightText = a.getString(R.styleable.EasyItemLayout_rightText);
        rightTextSize = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_rightTextSize, defaultTextSize);
        rightTextColor = a.getColor(R.styleable.EasyItemLayout_rightTextColor, DEFAULT_TEXT_COLOR_COUNT);
        rightMarginRight = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_rightMarginRight, 0);
        rightTextViewGray = a.getInt(R.styleable.EasyItemLayout_rightTextViewGray, GRAY_LEFT);

        arrowVisible = a.getBoolean(R.styleable.EasyItemLayout_rightIconVisible, false);
        clearVisible = a.getBoolean(R.styleable.EasyItemLayout_centerEditTextClearIconVisible, false);

        bottomLineVisible = a.getBoolean(R.styleable.EasyItemLayout_itemBottomLineVisible, false);
        bottomLineMarginLeft = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemBottomLineMarginLeft, 0);

//        paddingTop = a.getDimensionPixelOffset(R.styleable.itemItemLayout_android_paddingTop, 0);
//        paddingBottom = a.getDimensionPixelOffset(R.styleable.itemItemLayout_android_paddingBottom, 0);

        a.recycle();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout._easy_item_layout, this, true);
        easy_item_left_tv = findViewById(R.id.easy_item_left_tv);
        easy_item_center_tv = findViewById(R.id.easy_item_center_tv);
        easy_item_center_et = findViewById(R.id.easy_item_center_et);
        easy_item_right_tv = findViewById(R.id.easy_item_right_tv);
        easy_item_right_icon = findViewById(R.id.easy_item_right_icon);
        easy_item_center_icon_clear = findViewById(R.id.easy_item_center_icon_clear);
        easy_item_v_bottomLine = findViewById(R.id.easy_item_v_bottomLine);

        int txtGray = Gravity.START;
        switch (centerTextGray) {
            case GRAY_LEFT: {
                txtGray = Gravity.START;
                break;
            }
            case GRAY_CENTER: {
                txtGray = Gravity.CENTER;
                break;
            }
            case GRAY_RIGHT: {
                txtGray = Gravity.END;
                break;
            }
        }

        easy_item_center_tv.setVisibility(centerIsEdit ? GONE : VISIBLE);
        easy_item_center_et.setVisibility(centerIsEdit ? VISIBLE : GONE);
        easy_item_right_icon.setVisibility(arrowVisible ? VISIBLE : GONE);

        easy_item_v_bottomLine.setVisibility(bottomLineVisible ? VISIBLE : GONE);
        if (bottomLineMarginLeft > 0) {
            LayoutParams line_params = (LayoutParams) easy_item_v_bottomLine.getLayoutParams();
            line_params.leftMargin = bottomLineMarginLeft;
        }

        easy_item_left_tv.setText(leftText);
        easy_item_left_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        easy_item_left_tv.setTextColor(leftTextColor);
        LayoutParams title_params = (LayoutParams) easy_item_left_tv.getLayoutParams();
        if (leftWidth > 0) {
            title_params.width = leftWidth;
        }
        if (titleTextMarginLeft > 0) {
            title_params.leftMargin = titleTextMarginLeft;
        }

        easy_item_center_tv.setText(centerTextText);
        easy_item_center_tv.setHint(centerTextHint);
        easy_item_center_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, centerTextTextSize);
        easy_item_center_tv.setTextColor(centerTextTextColor);
        easy_item_center_tv.setHintTextColor(centerTextTextHintColor);
        easy_item_center_tv.setLines(centerTextTextLines);
        easy_item_center_tv.setGravity(txtGray);

        easy_item_center_et.setHint(centerEditTextHint);
        easy_item_center_et.setTextSize(TypedValue.COMPLEX_UNIT_PX, centerEditTextSize);
        easy_item_center_et.setTextColor(centerEditTextColor);
        easy_item_center_et.setHintTextColor(centerEditTextHintColor);
//        easy_item_center_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(itemEditMaxLength)});
        LinearLayout.LayoutParams et_params = (LinearLayout.LayoutParams) easy_item_center_et.getLayoutParams();
        et_params.topMargin = centerEditTextMarginTop;
        et_params.bottomMargin = centerEditTextMarginBottom;
        if (centerEditLines == 1) {
            easy_item_center_et.setSingleLine(true);
        } else {
//            easy_item_center_et.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            easy_item_center_et.setSingleLine(false);
            easy_item_center_et.setMaxLines(centerEditLines);
//            easy_item_center_et.setHorizontallyScrolling(false);
        }
        easy_item_center_icon_clear.setOnClickListener(clickListener);
        easy_item_center_et.addTextChangedListener(textWatcher);
        easy_item_center_et.setGravity(txtGray);

        LinearLayout.LayoutParams right_params = (LinearLayout.LayoutParams) easy_item_right_tv.getLayoutParams();
        if (rightWidth > 0) {
            right_params.width = rightWidth;
        }
        easy_item_right_tv.setText(rightText);
        easy_item_right_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
        easy_item_right_tv.setTextColor(rightTextColor);
        int rightGray = Gravity.START;
        switch (rightTextViewGray) {
            case GRAY_LEFT: {
                rightGray = Gravity.START;
                break;
            }
            case GRAY_CENTER: {
                rightGray = Gravity.CENTER;
                break;
            }
            case GRAY_RIGHT: {
                rightGray = Gravity.END;
                break;
            }
        }
        easy_item_right_tv.setGravity(rightGray);

        LinearLayout.LayoutParams tv_count_params = (LinearLayout.LayoutParams) easy_item_right_tv.getLayoutParams();
        tv_count_params.rightMargin = rightMarginRight;
    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.easy_item_center_icon_clear) {
                easy_item_center_et.setText("");
            }
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            easy_item_center_icon_clear.setVisibility(clearVisible && s.length() > 0 ? VISIBLE : GONE);
        }
    };

    public void setTitle(String text) {
        easy_item_left_tv.setText(text);
    }

    public void setCenterTextText(String text) {
        easy_item_center_tv.setText(text);
    }

    public void setContentEdit(String text) {
        easy_item_center_et.setText(text);
    }

    public void setCount(String text) {
        easy_item_right_tv.setText(text);
    }

    public void setBottomLineVisible(int visible) {
        bottomLineVisible = visible == View.VISIBLE;
        easy_item_v_bottomLine.setVisibility(visible);
    }

    public String getLeftText(String text) {
        return easy_item_left_tv.getText().toString();
    }

    public void setLeftText(String text) {
        easy_item_left_tv.setText(text);
    }

    public void setRightText(String text) {
        easy_item_right_tv.setText(text);
    }

    public String getRightText() {
        return easy_item_right_tv.getText().toString();
    }

    public void setCenterText(String text) {
        easy_item_center_tv.setText(text);
    }

    public String getCenterText() {
        return easy_item_center_tv.getText().toString();
    }


}
