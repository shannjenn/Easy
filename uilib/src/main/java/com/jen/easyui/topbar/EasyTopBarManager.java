package com.jen.easyui.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;


/**
 * 顶上标题栏控件
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
abstract class EasyTopBarManager extends RelativeLayout {
    private static final String TAG = EasyTopBarManager.class.getSimpleName();

    private Context mContext;

    //左侧文字布局
    private TextView tvLeft;
    //左侧文字
    private String mLeftText;
    //左侧文字大小
    private int mLeftTextSize;
    // 左侧文字颜色
    private int mLeftTextColor = -1; //颜色没有负值，初始化设置为-1，使用默认颜色
    //左侧文字附带图片
//    private Drawable mLeftTextWithLeftImg;

    //左侧图片布局
    private ImageView ivLeft;
    //左侧图标资源
    private Drawable mLeftImage;
    //左侧图片背景
    private Drawable mLeftImageBackground;

    //中间标题布局
    private TextView tvTitle;
    //中间标题
    private String mTitle;
    //中间标题大小
    private int mTitleTextSize;
    //中间标题颜色
    private int mTitleColor; //颜色没有负值，初始化设置为-1，使用默认颜色

    //右侧文字布局
    private TextView tvRight;
    //右侧文字
    private String mRightText;
    //右侧标题大小
    private int mRightTextSize;
    //右侧文字颜色
    private int mRightTextColor; //颜色没有负值，初始化设置为-1，使用默认颜色

    //右侧图片布局
    private ImageView ivRight;
    //右侧图标资源
    private Drawable mRightImage;

    //底部线条
    private View viewLine;
    //显示显示底部的分割线。默认为显示
    private boolean isShowBottomLine = true;

    public EasyTopBarManager(Context context) {
        this(context, null);
        this.mContext = context;
        initAttrs(context, null);
        initView();
    }

    public EasyTopBarManager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        initAttrs(context, attrs);
        initView();
    }

    public EasyTopBarManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(context, attrs);
        initView();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyTopBar);

        mLeftText = ta.getString(R.styleable.EasyTopBar_leftText);
        mLeftTextSize = ta.getDimensionPixelOffset(R.styleable.EasyTopBar_leftTextSize, EasyDensityUtil.sp2px(mContext, 16.0f));
        mLeftTextColor = ta.getColor(R.styleable.EasyTopBar_leftTextColor, 0xFF000000);
//        mLeftTextWithLeftImg = ta.getDrawable(R.styleable.EasyTopBar_leftTextWithImg);

//        isShowLeftImage = ta.getBoolean(R.styleable.EasyTopBar_showLeftImage, false);
        mLeftImage = ta.getDrawable(R.styleable.EasyTopBar_leftImage);
        mLeftImageBackground = ta.getDrawable(R.styleable.EasyTopBar_leftImageBackground);

        mRightText = ta.getString(R.styleable.EasyTopBar_rightText);
        mRightTextSize = ta.getDimensionPixelOffset(R.styleable.EasyTopBar_rightTextSize, EasyDensityUtil.sp2px(mContext, 16.0f));
        mRightTextColor = ta.getColor(R.styleable.EasyTopBar_rightTextColor, 0xFF000000);
//        isShowFaceLeftImage = ta.getBoolean(R.styleable.EasyTopBar_showLeftFaceImage, false);
        mRightImage = ta.getDrawable(R.styleable.EasyTopBar_rightImage);

        mTitle = ta.getString(R.styleable.EasyTopBar_titleText);
        mTitleTextSize = ta.getDimensionPixelOffset(R.styleable.EasyTopBar_titleTextSize, EasyDensityUtil.sp2px(mContext, 16.0f));
        mTitleColor = ta.getColor(R.styleable.EasyTopBar_titleTextColor, 0xFF000000);

        isShowBottomLine = ta.getBoolean(R.styleable.EasyTopBar_isShowBottomLine, true);

        ta.recycle();
    }

    private void initView() {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout mMainContainer = (RelativeLayout) mInflater.inflate(R.layout._easy_topbar, null);
        addView(mMainContainer);

        ivLeft = (ImageView) mMainContainer.findViewById(R.id.iv_left);
        tvLeft = (TextView) mMainContainer.findViewById(R.id.btn_left);

        tvTitle = (TextView) mMainContainer.findViewById(R.id.tv_title);

        ivRight = (ImageView) mMainContainer.findViewById(R.id.iv_right);
        tvRight = (TextView) mMainContainer.findViewById(R.id.btn_right);

        viewLine = mMainContainer.findViewById(R.id.line_view_topbar);


        if (TextUtils.isEmpty(mLeftText)) {
            tvLeft.setVisibility(GONE);
        } else {
            tvLeft.setVisibility(VISIBLE);
            tvLeft.setText(mLeftText);
            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
            tvLeft.setTextColor(mLeftTextColor);
            /*if (mLeftTextWithLeftImg != null) {
                mLeftTextWithLeftImg.setBounds(0, 0, mLeftTextWithLeftImg.getMinimumWidth(), mLeftTextWithLeftImg.getMinimumHeight());
                tvLeft.setCompoundDrawables(mLeftTextWithLeftImg, null, null, null);
                tvLeft.setCompoundDrawablePadding(6);
                tvLeft.setVisibility(View.VISIBLE);
                tvLeft.setClickable(true);
            }*/
        }


        if (mLeftImage == null) {
            ivLeft.setVisibility(GONE);
        } else {
            ivLeft.setVisibility(VISIBLE);
            ivLeft.setImageDrawable(mLeftImage);
        }

        tvTitle.setText(mTitle);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        tvTitle.setTextColor(mTitleColor);

        if (TextUtils.isEmpty(mRightText)) {
            tvRight.setVisibility(GONE);
        } else {
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(mRightText);
            tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
            tvRight.setTextColor(mRightTextColor);
        }

        viewLine.setVisibility(isShowBottomLine ? VISIBLE : GONE);
    }

    public void setTitle(String text) {
        tvTitle.setText(text);
    }

    public void setLeftText(String text) {
        tvLeft.setText(text);
    }

    public void setRightText(String text) {
        tvRight.setText(text);
    }

}
