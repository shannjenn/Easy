package com.jen.easyui.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jen.easyui.R;


/**
 * 顶上标题栏控件
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public class EasyTopBar extends RelativeLayout {
    private static final String TAG = EasyTopBar.class.getSimpleName();

    private Context mContext;
    //左侧图片布局
    private ImageView ivLeft;
    //左侧头像布局
    private ImageView ivFace;
    //右侧图片布局
    private ImageView ivRight;

    //中间标题布局
    private TextView tvTitle;
    //左侧文字布局
    private TextView tvLeft;
    //右侧文字布局
    private TextView tvRight;
    //底部线条
    private View viewLine;

    //中间标题
    private String mTitle;
    //左侧文字
    private String mLeftText;
    //右侧文字
    private String mRightText;

    //左侧文字颜色
    private int mLeftTextColor = -1; //颜色没有负值，初始化设置为-1，使用默认颜色
    //右侧文字颜色
    private int mRightTextColor = -1; //颜色没有负值，初始化设置为-1，使用默认颜色
    //左侧文字附带图片
    private Drawable mLeftTextWithLeftImg;
    //左侧图标资源
    private Drawable mLeftImage;
    //右侧图标资源
    private Drawable mRightImage;
    //左侧图片背景
    private Drawable mLeftImageBackground;
    //是否显示左侧图片
    private boolean isShowLeftImage;
    //是否显示左侧头像图片
    private boolean isShowFaceLeftImage;
    //显示显示底部的分割线。默认为显示
    private boolean isShowBottomLine = true;

    public EasyTopBar(Context context) {
        this(context, null);
    }

    public EasyTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(attrs, defStyleAttr);
        initView();
    }

    private void initAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.EasyTopBar, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.EasyTopBar_title) {
                mTitle = a.getString(attr);

            } else if (attr == R.styleable.EasyTopBar_leftText) {
                mLeftText = a.getString(attr);

            } else if (attr == R.styleable.EasyTopBar_leftTextWithLeftImg) {
                mLeftTextWithLeftImg = a.getDrawable(attr);

            } else if (attr == R.styleable.EasyTopBar_rightText) {
                mRightText = a.getString(attr);

            } else if (attr == R.styleable.EasyTopBar_showLeftImage) {
                isShowLeftImage = a.getBoolean(attr, false);

            } else if (attr == R.styleable.EasyTopBar_showLeftFaceImage) {
                isShowFaceLeftImage = a.getBoolean(attr, false);

            } else if (attr == R.styleable.EasyTopBar_leftImageBackground) {
                mLeftImageBackground = a.getDrawable(attr);

            } else if (attr == R.styleable.EasyTopBar_leftImage) {
                mLeftImage = a.getDrawable(attr);

            } else if (attr == R.styleable.EasyTopBar_rightImage) {
                mRightImage = a.getDrawable(attr);

            } else if (attr == R.styleable.EasyTopBar_leftTextColor) {
                mLeftTextColor = a.getColor(attr, ActivityCompat.getColor(mContext, R.color._easy_blue_text));

            } else if (attr == R.styleable.EasyTopBar_rightTextColor) {
                mRightTextColor = a.getColor(attr, ActivityCompat.getColor(mContext, R.color._easy_blue_text));

            } else if (attr == R.styleable.EasyTopBar_isShowBottomLine) {
                isShowBottomLine = a.getBoolean(attr, true);

            }
        }
        a.recycle();
    }

    private void initView() {
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout mMainContainer = (RelativeLayout) mInflater.inflate(R.layout._easy_view_topbar, null);

        ivLeft = (ImageView) mMainContainer.findViewById(R.id.iv_left);
        ivFace = (ImageView) mMainContainer.findViewById(R.id.iv_face);
        ivRight = (ImageView) mMainContainer.findViewById(R.id.iv_right);
        tvTitle = (TextView) mMainContainer.findViewById(R.id.tv_title);
        tvLeft = (TextView) mMainContainer.findViewById(R.id.tv_left);
        tvRight = (TextView) mMainContainer.findViewById(R.id.tv_right);
        viewLine = mMainContainer.findViewById(R.id.line_view_topbar);

        this.addView(mMainContainer);


        if (!TextUtils.isEmpty(mTitle)) {
            setTitleText(mTitle);
        }
        if (!TextUtils.isEmpty(mLeftText)) {
            setLeftText(mLeftText);
        }
        if (!TextUtils.isEmpty(mRightText)) {
            setRightText(mRightText);
        }

        if (mLeftTextColor != -1) {
            setLeftTextColor(mLeftTextColor);
        }

        if (mRightTextColor != -1) {
            setRightTextColor(mRightTextColor);
        }

        setLeftImage(mLeftImage);
//        暂时屏蔽
//        setLeftImageBackground(mLeftImageBackground);
        setRightImage(mRightImage);

        setLeftTextWithLeftImage(mLeftTextWithLeftImg);

        ivLeft.setVisibility((isShowLeftImage || mLeftImage != null) ? VISIBLE : GONE);
        ivFace.setVisibility((isShowFaceLeftImage) ? VISIBLE : GONE);

        viewLine.setVisibility(isShowBottomLine ? VISIBLE : GONE);
    }

    /**
     * 设置标题
     */
    public void setTitleText(String title) {
        mTitle = title;
        tvTitle.setText(mTitle);
    }

    /**
     * 设置左边文字
     */
    public void setLeftText(String text) {
        tvLeft.setText(text);
        tvLeft.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左侧文字颜色
     *
     * @param color
     */
    public void setLeftTextColor(int color) {
        tvLeft.setTextColor(color);
    }

    /**
     * 设置左边文字附带左边图片
     *
     * @param drawable 图片资源
     */
    public void setLeftTextWithLeftImage(Drawable drawable) {
        if (drawable != null) {
//            int marginPx = ViewsUtil.dip2px(mContext, 6f); //图片距离文字的边距。6dp暂时写死
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvLeft.setCompoundDrawables(drawable, null, null, null);
            tvLeft.setCompoundDrawablePadding(6);
            tvLeft.setVisibility(View.VISIBLE);
            tvLeft.setClickable(true);
        }
    }

    /**
     * 设置右边文字
     *
     * @param text
     */
    public void setRightText(String text) {
        tvRight.setText(text);
        tvRight.setVisibility(View.VISIBLE);
    }

    /***
     * 设置右边文字TextView是否可用
     * @param enable
     */
    public void setRightTextViewEnable(boolean enable) {
        tvRight.setEnabled(enable);
        tvRight.setVisibility(View.VISIBLE);
    }

    public void setRightTextColor(int color) {
        tvRight.setTextColor(color);
    }

    public TextView getRightTextView() {
        return tvRight;
    }

    /**
     * 设置右边文字隐藏
     */
    public void setRightTextGone() {
        tvRight.setVisibility(View.GONE);
    }

    /**
     * 设置右边文字显示
     */
    public void setRightTextVisible() {
        tvRight.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左边图片
     *
     * @param resId
     */
    public void setLeftImage(int resId) {
        ivLeft.setImageResource(resId);
        ivLeft.setVisibility(VISIBLE);
    }

    /**
     * 设置左边图片
     *
     * @param drawable
     */
    public void setLeftImage(Drawable drawable) {
        if (drawable != null) {
            ivLeft.setImageDrawable(drawable);
            ivLeft.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置左边图片
     *
     * @param bitmap
     */
    public void setLeftImage(Bitmap bitmap) {
        ivLeft.setImageBitmap(bitmap);
        ivLeft.setVisibility(View.VISIBLE);
    }

    /**
     * 设置左边图片宽高
     *
     * @param widthPx
     * @param heightPx
     */
    public void setLeftImageSize(int widthPx, int heightPx) {
        if (ivLeft != null) {
            Log.d(TAG, "setLeftImageSize() called with: widthPx = [" + widthPx + "], heightPx = [" + heightPx + "]");
            ViewGroup.LayoutParams layoutParams = ivLeft.getLayoutParams();
            layoutParams.height = widthPx;
            layoutParams.width = heightPx;
            ivLeft.setLayoutParams(layoutParams);
        }
    }

    public void setLeftImagePadding(int left, int top, int right, int bottom) {
        if (ivLeft != null) {
            Log.d(TAG, "setLeftImagePadding() called with: left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
            ivLeft.setPadding(left, top, right, bottom);
        }
    }

    public ImageView getLeftIv() {
        return ivLeft;
    }

    /**
     * 设置左边图片背景
     *
     * @param drawable
     */
    public void setLeftImageBackground(Drawable drawable) {
        ivLeft.setBackground(drawable);
    }

    /**
     * 设置右边图片
     *
     * @param resId
     */
    public void setRightImage(int resId) {
        ivRight.setImageResource(resId);
        ivRight.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右边图片
     *
     * @param drawable
     */
    public void setRightImage(Drawable drawable) {
        if (drawable != null) {
            ivRight.setImageDrawable(drawable);
            ivRight.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置右边图片
     *
     * @param bmp
     */
    public void setRightImage(Bitmap bmp) {
        ivRight.setImageBitmap(bmp);
        ivRight.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右边图片是否显示
     *
     * @param isShow
     */
    public void setRightImageVisiable(boolean isShow) {
        ivRight.setVisibility(isShow ? VISIBLE : GONE);
    }

    /**
     * 设置左边图片点击事件
     *
     * @param listener
     */
    public void setLeftImageClickListener(OnClickListener listener) {
        ivLeft.setOnClickListener(listener);
    }

    /**
     * 设置左边图片点击事件
     *
     * @param listener
     */
    public void setLeftFaceImageClickListener(OnClickListener listener) {
        ivFace.setOnClickListener(listener);
    }

    /**
     * 设置左边文字点击监听
     *
     * @param listener
     */
    public void setLeftTextClickListener(OnClickListener listener) {
        tvLeft.setOnClickListener(listener);
    }


    /**
     * 设置右边文字点击监听
     *
     * @param listener
     */
    public void setRightTextClickListener(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    /**
     * 设置右边图标点击监听
     *
     * @param listener
     */
    public void setRightImageClickListener(OnClickListener listener) {
        ivRight.setOnClickListener(listener);
    }
}
