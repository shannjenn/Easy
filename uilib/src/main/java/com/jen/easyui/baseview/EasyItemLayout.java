package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
    private final int DEFAULT_TEXT_COLOR_TITLE = 0XFF6b6b6b;//默认字体颜色Title
    private final int DEFAULT_TEXT_COLOR_CONTENT = 0XFF313B40;//默认字体颜色content
    private final int DEFAULT_TEXT_COLOR_COUNT = 0XFF6b6b6b;//默认字体颜色count
    private final int DEFAULT_BACKGOUND_COLOR = 0XFFFFFFFF;//背景颜色

    /*是否为输入模式：TRUE显示EditText，隐藏TextView*/
    boolean itemEdit;

    TextView tv_title;
    TextView tv_content;
    EditText et_content;
    TextView tv_count;
    View v_bottomLine;

    private int itemPaddingLeft;
    private int itemPaddingRight;
    private int itemPaddingTop;
    private int itemPaddingBottom;

    String titleText;
    int titleTextSize;
    int titleTextColor;

    String contentText;
    int contentTextSize;
    int contentTextColor;
    boolean contentTextSingleLine;

    int editTextSize;
    int editTextColor;
    int editLines = 1;
    int itemEditMaxLength;

    String countText;
    int countTextSize;
    int countTextColor;
    boolean countVisible;

    boolean bottomLineVisible;

//    int paddingTop;
//    int paddingBottom;

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
        int defaultTextSize = (int) EasyDensityUtil.dp2px(DEFAULT_TEXT_SIZE);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.EasyItemLayout, defStyleAttr, 0);

        itemEdit = a.getBoolean(R.styleable.EasyItemLayout_itemEdit, false);

        itemPaddingLeft = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemPaddingLeft, (int) EasyDensityUtil.dp2px(10));
        itemPaddingRight = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemPaddingRight, (int) EasyDensityUtil.dp2px(10));
        itemPaddingTop = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemPaddingTop, (int) EasyDensityUtil.dp2px(10));
        itemPaddingBottom = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemPaddingBottom, (int) EasyDensityUtil.dp2px(10));

        titleText = a.getString(R.styleable.EasyItemLayout_itemTitleText);
        titleTextSize = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemTitleTextSize, defaultTextSize);
        titleTextColor = a.getColor(R.styleable.EasyItemLayout_itemTitleTextColor, DEFAULT_TEXT_COLOR_TITLE);

        contentText = a.getString(R.styleable.EasyItemLayout_itemTxtText);
        contentTextSize = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemTxtTextSize, defaultTextSize);
        contentTextColor = a.getColor(R.styleable.EasyItemLayout_itemTxtTextColor, DEFAULT_TEXT_COLOR_CONTENT);
        contentTextSingleLine = a.getBoolean(R.styleable.EasyItemLayout_itemTxtSingleLine, false);

        editTextSize = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemEditTextSize, defaultTextSize);
        editTextColor = a.getColor(R.styleable.EasyItemLayout_itemEditTextColor, DEFAULT_TEXT_COLOR_CONTENT);
        editLines = a.getInt(R.styleable.EasyItemLayout_itemEditLines, 1);
        itemEditMaxLength = a.getInt(R.styleable.EasyItemLayout_itemEditMaxLength, 0);

        countText = a.getString(R.styleable.EasyItemLayout_itemCountText);
        countTextSize = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemCountTextSize, defaultTextSize);
        countTextColor = a.getColor(R.styleable.EasyItemLayout_itemCountTextColor, DEFAULT_TEXT_COLOR_COUNT);
        countVisible = a.getBoolean(R.styleable.EasyItemLayout_itemCountVisible, true);

        bottomLineVisible = a.getBoolean(R.styleable.EasyItemLayout_itemBottomLineVisible, true);

//        paddingTop = a.getDimensionPixelOffset(R.styleable.itemItemLayout_android_paddingTop, 0);
//        paddingBottom = a.getDimensionPixelOffset(R.styleable.itemItemLayout_android_paddingBottom, 0);

        a.recycle();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout._easy_item_layout, this, true);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        et_content = findViewById(R.id.et_content);
        tv_count = findViewById(R.id.tv_count);
        v_bottomLine = findViewById(R.id.v_bottomLine);

        tv_content.setVisibility(itemEdit ? GONE : VISIBLE);
        et_content.setVisibility(itemEdit ? VISIBLE : GONE);
        tv_count.setVisibility(countVisible ? VISIBLE : GONE);
        v_bottomLine.setVisibility(bottomLineVisible ? VISIBLE : GONE);

        tv_title.setText(titleText);
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        tv_title.setTextColor(titleTextColor);

        tv_content.setText(contentText);
        tv_content.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
        tv_content.setTextColor(contentTextColor);
        tv_content.setSingleLine(contentTextSingleLine);
        tv_content.setMinLines(1);
        tv_content.setMaxLines(4);

        et_content.setTextSize(TypedValue.COMPLEX_UNIT_PX, editTextSize);
        et_content.setTextColor(editTextColor);
        if (editLines == 1) {
            et_content.setSingleLine(true);
        } else {
            int etPaddingH = (int) EasyDensityUtil.dp2px(10);
            et_content.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            et_content.setGravity(Gravity.TOP);
            et_content.setSingleLine(false);
            et_content.setHorizontallyScrolling(false);
            et_content.setMaxLines(editLines);
            et_content.setPadding(0, etPaddingH, 0, etPaddingH);
        }
        if (itemEditMaxLength > 0) {
            et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(itemEditMaxLength)});
        }

        tv_count.setText(countText);
        tv_count.setTextSize(TypedValue.COMPLEX_UNIT_PX, countTextSize);
        tv_count.setTextColor(countTextColor);

        if (itemEdit) {
            LayoutParams et_params = (LayoutParams) et_content.getLayoutParams();
            et_params.topMargin = itemPaddingTop;
            et_params.bottomMargin = itemPaddingBottom;
        } else {
            LayoutParams tv_params = (LayoutParams) tv_content.getLayoutParams();
            tv_params.topMargin = itemPaddingTop;
            tv_params.bottomMargin = itemPaddingBottom;
        }

        setPadding(itemPaddingLeft, 0, itemPaddingRight, 0);
        setBackgroundColor(DEFAULT_BACKGOUND_COLOR);
    }

    public void setTitle(String text) {
        tv_title.setText(text);
    }

    public void setContentText(String text) {
        tv_content.setText(text);
    }

    public void setContentEdit(String text) {
        et_content.setText(text);
    }

    public void setCount(String text) {
        tv_count.setText(text);
    }

    public void setBottomLineVisible(int visible) {
        bottomLineVisible = visible == View.VISIBLE;
        v_bottomLine.setVisibility(visible);
    }

    public void setCountText(String text) {
        tv_count.setText(text);
    }

    public void setCountVisible(int visible) {
        countVisible = visible == View.VISIBLE;
        tv_count.setVisibility(visible);
    }


    public TextView getTitleView() {
        return tv_title;
    }

    public TextView getcontentViewTv() {
        return tv_content;
    }

    public EditText getcontentViewEt() {
        return et_content;
    }

    public TextView getcountView() {
        return tv_count;
    }
}
