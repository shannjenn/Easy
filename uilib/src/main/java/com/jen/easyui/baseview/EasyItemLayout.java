package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
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

    /*是否为输入模式：TRUE显示EditText，隐藏TextView*/
    boolean itemIsEdit;

    TextView tv_item_layout_title;
    TextView tv_item_layout_content;
    EditText et_item_layout_content;
    TextView tv_item_layout_count;
    ImageView iv_item_layout_arrow;
    View v_item_layout_bottomLine;

    private int editTextMarginTop;
    private int editTextMarginBottom;

    String titleText;
    int titleTextSize;
    int titleWidth;
    int titleTextColor;
    int titleTextMarginLeft;

    String contentText;
    String contentHint;
    int contentTextSize;
    int contentTextColor;
    int contentTextLines;//默认1

    int editTextSize;
    int editTextColor;
    String editTextHint;
    int editLines = 1;

    String countText;
    int countTextSize;
    int countTextColor;
    int countMarginRight;

    boolean arrowVisible;

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
        int defaultTextSize = (int) EasyDensityUtil.dp2px(DEFAULT_TEXT_SIZE);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.EasyItemLayout, defStyleAttr, 0);

        itemIsEdit = a.getBoolean(R.styleable.EasyItemLayout_itemIsEdit, false);

        titleText = a.getString(R.styleable.EasyItemLayout_itemTitleText);
        titleTextSize = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemTitleTextSize, defaultTextSize);
        titleWidth = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemTitleWidth, -1);
        titleTextColor = a.getColor(R.styleable.EasyItemLayout_itemTitleTextColor, DEFAULT_TEXT_COLOR_TITLE);
        titleTextMarginLeft = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemTitleMarginLeft, 0);

        contentText = a.getString(R.styleable.EasyItemLayout_itemTxtText);
        contentHint = a.getString(R.styleable.EasyItemLayout_itemTxtHint);
        contentTextSize = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemTxtTextSize, defaultTextSize);
        contentTextColor = a.getColor(R.styleable.EasyItemLayout_itemTxtTextColor, DEFAULT_TEXT_COLOR_CONTENT);
        contentTextLines = a.getInt(R.styleable.EasyItemLayout_itemTxtLines, 1);

        editTextSize = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemEditTextSize, defaultTextSize);
        editTextColor = a.getColor(R.styleable.EasyItemLayout_itemEditTextColor, DEFAULT_TEXT_COLOR_CONTENT);
        editTextHint = a.getString(R.styleable.EasyItemLayout_itemEditTextHint);
        editLines = a.getInt(R.styleable.EasyItemLayout_itemEditLines, 1);
        editTextMarginTop = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemEditTextMarginTop, 0);
        editTextMarginBottom = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemEditTextMarginBottom, 0);

        countText = a.getString(R.styleable.EasyItemLayout_itemCountText);
        countTextSize = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemCountTextSize, defaultTextSize);
        countTextColor = a.getColor(R.styleable.EasyItemLayout_itemCountTextColor, DEFAULT_TEXT_COLOR_COUNT);
        countMarginRight = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemCountMarginRight, 0);

        arrowVisible = a.getBoolean(R.styleable.EasyItemLayout_itemArrowVisible, false);

        bottomLineVisible = a.getBoolean(R.styleable.EasyItemLayout_itemBottomLineVisible, false);
        bottomLineMarginLeft = a.getDimensionPixelOffset(R.styleable.EasyItemLayout_itemBottomLineMarginLeft, 0);

//        paddingTop = a.getDimensionPixelOffset(R.styleable.itemItemLayout_android_paddingTop, 0);
//        paddingBottom = a.getDimensionPixelOffset(R.styleable.itemItemLayout_android_paddingBottom, 0);

        a.recycle();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout._easy_item_layout, this, true);
        tv_item_layout_title = findViewById(R.id.tv_item_layout_title);
        tv_item_layout_content = findViewById(R.id.tv_item_layout_content);
        et_item_layout_content = findViewById(R.id.et_item_layout_content);
        tv_item_layout_count = findViewById(R.id.tv_item_layout_count);
        iv_item_layout_arrow = findViewById(R.id.iv_item_layout_arrow);
        v_item_layout_bottomLine = findViewById(R.id.v_item_layout_bottomLine);

        tv_item_layout_content.setVisibility(itemIsEdit ? GONE : VISIBLE);
        et_item_layout_content.setVisibility(itemIsEdit ? VISIBLE : GONE);
        iv_item_layout_arrow.setVisibility(arrowVisible ? VISIBLE : GONE);

        v_item_layout_bottomLine.setVisibility(bottomLineVisible ? VISIBLE : GONE);
        if (bottomLineMarginLeft > 0) {
            RelativeLayout.LayoutParams line_params = (RelativeLayout.LayoutParams) v_item_layout_bottomLine.getLayoutParams();
            line_params.leftMargin = bottomLineMarginLeft;
        }

        tv_item_layout_title.setText(titleText);
        tv_item_layout_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        tv_item_layout_title.setTextColor(titleTextColor);
        RelativeLayout.LayoutParams title_params = (RelativeLayout.LayoutParams) tv_item_layout_title.getLayoutParams();
        if (titleWidth > 0) {
            title_params.width = titleWidth;
        }
        if (titleTextMarginLeft > 0) {
            title_params.leftMargin = titleTextMarginLeft;
        }

        tv_item_layout_content.setText(contentText);
        tv_item_layout_content.setHint(contentHint);
        tv_item_layout_content.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
        tv_item_layout_content.setTextColor(contentTextColor);
        tv_item_layout_content.setLines(contentTextLines);

        et_item_layout_content.setHint(editTextHint);
        et_item_layout_content.setTextSize(TypedValue.COMPLEX_UNIT_PX, editTextSize);
        et_item_layout_content.setTextColor(editTextColor);
//        et_item_layout_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(itemEditMaxLength)});
        LinearLayout.LayoutParams et_params = (LinearLayout.LayoutParams) et_item_layout_content.getLayoutParams();
        et_params.topMargin = editTextMarginTop;
        et_params.bottomMargin = editTextMarginBottom;
        if (editLines == 1) {
            et_item_layout_content.setSingleLine(true);
        } else {
//            et_item_layout_content.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            et_item_layout_content.setSingleLine(false);
            et_item_layout_content.setMaxLines(editLines);
//            et_item_layout_content.setHorizontallyScrolling(false);
        }

        tv_item_layout_count.setText(countText);
        tv_item_layout_count.setTextSize(TypedValue.COMPLEX_UNIT_PX, countTextSize);
        tv_item_layout_count.setTextColor(countTextColor);
        LinearLayout.LayoutParams tv_count_params = (LinearLayout.LayoutParams) tv_item_layout_count.getLayoutParams();
        tv_count_params.rightMargin = countMarginRight;
    }

    public void setTitle(String text) {
        tv_item_layout_title.setText(text);
    }

    public void setContentText(String text) {
        tv_item_layout_content.setText(text);
    }

    public void setContentEdit(String text) {
        et_item_layout_content.setText(text);
    }

    public void setCount(String text) {
        tv_item_layout_count.setText(text);
    }

    public void setBottomLineVisible(int visible) {
        bottomLineVisible = visible == View.VISIBLE;
        v_item_layout_bottomLine.setVisibility(visible);
    }

    public void setCountText(String text) {
        tv_item_layout_count.setText(text);
    }

    public void setCountVisible(int visible) {
        tv_item_layout_count.setVisibility(visible);
    }

    public TextView getTitleView() {
        return tv_item_layout_title;
    }

    public TextView getcontentViewTv() {
        return tv_item_layout_content;
    }

    public EditText getcontentViewEt() {
        return et_item_layout_content;
    }

    public TextView getcountView() {
        return tv_item_layout_count;
    }
}
