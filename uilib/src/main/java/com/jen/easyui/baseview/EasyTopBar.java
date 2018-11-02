package com.jen.easyui.baseview;

import android.app.Activity;
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


/**
 * 顶上标题栏控件
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public class EasyTopBar extends RelativeLayout implements View.OnClickListener {
    private Context mContext;
    private Activity mActivity;


    private TextView tvLeft;//左侧文字布局
    private String mLeftText;//左侧文字
    private float mLeftTextSize;//左侧文字大小
    private int mLeftTextColor; //左侧文字颜色,颜色没有负值，初始化设置为-1，使用默认颜色

//    private Drawable mLeftTextWithLeftImg;//左侧文字附带图片


    private ImageView ivLeft;//左侧图片布局
    private Drawable mLeftImage;//左侧图标资源
    private Drawable mLeftImageBackground;//左侧图片背景


    private TextView tvTitle;//中间标题布局
    private String mTitle;//中间标题
    private float mTitleTextSize;//中间标题大小
    private int mTitleColor; //颜色没有负值，初始化设置为-1，使用默认颜色


    private TextView tvRight;//右侧文字布局
    private String mRightText;
    private float mRightTextSize;
    private int mRightTextColor; //颜色没有负值，初始化设置为-1，使用默认颜色


    private ImageView ivRight;//右侧图片布局
    private Drawable mRightImage;

    private View viewLine;//底部线条
    private boolean isShowBottomLine = true;

    public EasyTopBar(Context context) {
        this(context, null);
        this.mContext = context;
        initAttrs(context, null);
        initView();
    }

    public EasyTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        initAttrs(context, attrs);
        initView();
    }

    public EasyTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(context, attrs);
        initView();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyTopBar);

        mLeftText = ta.getString(R.styleable.EasyTopBar_topBarLeftText);
        mLeftTextSize = ta.getDimension(R.styleable.EasyTopBar_topBarLeftTextSize, -1);
        mLeftTextColor = ta.getColor(R.styleable.EasyTopBar_topBarLeftTextColor, -1);
//        mLeftTextWithLeftImg = ta.getDrawable(R.styleable.EasyTopBar_leftTextWithImg);

//        isShowLeftImage = ta.getBoolean(R.styleable.EasyTopBar_showLeftImage, false);
        mLeftImage = ta.getDrawable(R.styleable.EasyTopBar_topBarLeftImage);
        mLeftImageBackground = ta.getDrawable(R.styleable.EasyTopBar_topBarLeftImageBackground);

        mRightText = ta.getString(R.styleable.EasyTopBar_topBarRightText);
        mRightTextSize = ta.getDimension(R.styleable.EasyTopBar_topBarRightTextSize, -1);
        mRightTextColor = ta.getColor(R.styleable.EasyTopBar_topBarRightTextColor, -1);
//        isShowFaceLeftImage = ta.getBoolean(R.styleable.EasyTopBar_showLeftFaceImage, false);
        mRightImage = ta.getDrawable(R.styleable.EasyTopBar_topBarRightImage);

        mTitle = ta.getString(R.styleable.EasyTopBar_topBarTitleText);
        mTitleTextSize = ta.getDimension(R.styleable.EasyTopBar_topBarTitleTextSize, -1);
        mTitleColor = ta.getColor(R.styleable.EasyTopBar_topBarTitleTextColor, -1);

        isShowBottomLine = ta.getBoolean(R.styleable.EasyTopBar_topBarShowBottomLine, true);

        ta.recycle();
    }

    private void initView() {
//        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        RelativeLayout mMainContainer = (RelativeLayout) mInflater.inflate(R.layout._easy_topbar, null);
        LayoutInflater.from(mContext).inflate(R.layout._easy_topbar, this);
//        addView(mMainContainer);

        tvLeft = (TextView) findViewById(R.id.btn_left);
        ivLeft = (ImageView) findViewById(R.id.iv_left);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRight = (TextView) findViewById(R.id.btn_right);
        ivRight = (ImageView) findViewById(R.id.iv_right);
        viewLine = findViewById(R.id.line_view_topbar);


        if (!TextUtils.isEmpty(mLeftText)) {
            tvLeft.setText(mLeftText);
            if (mLeftTextSize != -1) {
                tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
            }
            if (mLeftTextColor != -1) {
                tvLeft.setTextColor(mLeftTextColor);
            }
        }
        if (mLeftImage != null) {
            ivLeft.setImageDrawable(mLeftImage);
        }
        if (mTitle != null) {
            tvTitle.setText(mTitle);
            if (mTitleTextSize != -1) {
                tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
            }
            if (mTitleColor != -1) {
                tvTitle.setTextColor(mTitleColor);
            }
        }
        if (mRightText != null) {
            tvRight.setText(mRightText);
            if (mRightTextSize != -1) {
                tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
            }
            if (mRightTextColor != -1) {
                tvRight.setTextColor(mRightTextColor);
            }
        }
        viewLine.setVisibility(isShowBottomLine ? VISIBLE : GONE);
        ivLeft.setOnClickListener(this);
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

    public void bindOnBackClick(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_left) {
            if (mActivity != null)
                mActivity.onBackPressed();

        }
    }

}