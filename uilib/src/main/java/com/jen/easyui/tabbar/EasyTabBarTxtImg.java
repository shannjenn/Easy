package com.jen.easyui.tabbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;

/**
 * 导航布局,说明：图片文字导航栏，不带滑动游标效果
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */

public class EasyTabBarTxtImg extends RelativeLayout {
    private Context context;
    /*分割线默认颜色*/
    private final int LINE_DEFAULT_COLOR = 0xffE0E0E0;
    /*文字默认颜色*/
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    /*默认文字大小12sp*/
    private static final int DEFAULT_TEXT_SIZE = 16;
    /*item最大个数*/
    private final int MAX_SIZE = 5;

    //分割线属性
    private int topLineColor;
    private int topLineSize;

    private int bottomLineColor;
    private int bottomLineSize;

    /*高度 LayoutParams.MATCH_PARENT=-1 LayoutParams.FILL_PARENT=-1 LayoutParams.WRAP_PARENT=-2*/
    private int height;
    /*宽度 */
    private int width;

    /*item个数 */
    private int itemSize;

    /*文字*/
    private final String[] txts = new String[MAX_SIZE];
    private TextView[] textViews;
    private int textSelectColor;
    private int textUnSelectColor;
    private int textSize;

    /*默认图片*/
    private final Drawable[] imgs_0 = new Drawable[MAX_SIZE];
    /*选中图片*/
    private final Drawable[] imgs_1 = new Drawable[MAX_SIZE];
    private ImageView[] imageViews;

    private ViewPager mViewPager;
    private int mCurrentItem;

    public EasyTabBarTxtImg(Context context) {
        super(context);
        this.context = context;
    }

    public EasyTabBarTxtImg(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs, 0);
        initView();
    }

    public EasyTabBarTxtImg(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    private void initAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.EasyTabBarTxtImg, defStyleAttr, 0);
        topLineColor = a.getColor(R.styleable.EasyTabBarTxtImg_topLineColor, LINE_DEFAULT_COLOR);
        topLineSize = a.getDimensionPixelOffset(R.styleable.EasyTabBarTxtImg_topLineSize, 0);
        bottomLineColor = a.getColor(R.styleable.EasyTabBarTxtImg_bottomLineColor, LINE_DEFAULT_COLOR);
        bottomLineSize = a.getDimensionPixelOffset(R.styleable.EasyTabBarTxtImg_bottomLineSize, 0);
        height = a.getLayoutDimension(R.styleable.EasyTabBarTxtImg_android_layout_height, 0);
        width = a.getLayoutDimension(R.styleable.EasyTabBarTxtImg_android_layout_width, 0);
        itemSize = a.getInt(R.styleable.EasyTabBarTxtImg_itemSize, 0);
        textSelectColor = a.getColor(R.styleable.EasyTabBarTxtImg_textSelectColor, DEFAULT_TEXT_COLOR);
        textUnSelectColor = a.getColor(R.styleable.EasyTabBarTxtImg_textUnSelectColor, DEFAULT_TEXT_COLOR);
        textSize = a.getDimensionPixelOffset(R.styleable.EasyTabBarTxtImg_textSize, EasyDensityUtil.dip2px(context, DEFAULT_TEXT_SIZE));

        String txt0 = a.getString(R.styleable.EasyTabBarTxtImg_text0);
        String txt1 = a.getString(R.styleable.EasyTabBarTxtImg_text1);
        String txt2 = a.getString(R.styleable.EasyTabBarTxtImg_text2);
        String txt3 = a.getString(R.styleable.EasyTabBarTxtImg_text3);
        String txt4 = a.getString(R.styleable.EasyTabBarTxtImg_text4);
        txts[0] = txt0;
        txts[1] = txt1;
        txts[2] = txt2;
        txts[3] = txt3;
        txts[4] = txt4;

        Drawable img0_0 = a.getDrawable(R.styleable.EasyTabBarTxtImg_img0_0);
        Drawable img0_1 = a.getDrawable(R.styleable.EasyTabBarTxtImg_img0_1);
        Drawable img1_0 = a.getDrawable(R.styleable.EasyTabBarTxtImg_img1_0);
        Drawable img1_1 = a.getDrawable(R.styleable.EasyTabBarTxtImg_img1_1);
        Drawable img2_0 = a.getDrawable(R.styleable.EasyTabBarTxtImg_img2_0);
        Drawable img2_1 = a.getDrawable(R.styleable.EasyTabBarTxtImg_img2_1);
        Drawable img3_0 = a.getDrawable(R.styleable.EasyTabBarTxtImg_img3_0);
        Drawable img3_1 = a.getDrawable(R.styleable.EasyTabBarTxtImg_img3_1);
        Drawable img4_0 = a.getDrawable(R.styleable.EasyTabBarTxtImg_img4_0);
        Drawable img4_1 = a.getDrawable(R.styleable.EasyTabBarTxtImg_img4_1);
        imgs_0[0] = img0_0;
        imgs_0[1] = img1_0;
        imgs_0[2] = img2_0;
        imgs_0[3] = img3_0;
        imgs_0[4] = img4_0;

        imgs_1[0] = img0_1;
        imgs_1[1] = img1_1;
        imgs_1[2] = img2_1;
        imgs_1[3] = img3_1;
        imgs_1[4] = img4_1;

        a.recycle();
    }

    private void initView() {
        textViews = new TextView[itemSize];
        imageViews = new ImageView[itemSize];
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(0, topLineSize, 0, bottomLineSize);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(params);
        addView(linearLayout);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < itemSize; i++) {
            LinearLayout mainContainer = (LinearLayout) mInflater.inflate(R.layout._easy_tabbar_imgtxt, null);
            LinearLayout.LayoutParams linerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            mainContainer.setLayoutParams(linerParams);
            linearLayout.addView(mainContainer);
            mainContainer.setOnClickListener(onClickListener);
            mainContainer.setTag(i);

            TextView textView = (TextView) mainContainer.findViewById(R.id.item_text);
            textView.setText(txts[i]);
            textView.setTextColor(textUnSelectColor);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            ImageView imageView = (ImageView) mainContainer.findViewById(R.id.item_img);
            imageView.setImageDrawable(imgs_0[i]);
            textViews[i] = textView;
            imageViews[i] = imageView;
        }

        if (topLineSize > 0) {
            LayoutParams lineParams = new LayoutParams(LayoutParams.MATCH_PARENT, topLineSize);
            lineParams.addRule(ALIGN_PARENT_TOP);
            View topLine = new View(getContext());
            topLine.setBackgroundColor(topLineColor);
            topLine.setLayoutParams(lineParams);
            addView(topLine, 0);
        }
        if (bottomLineSize > 0) {
            LayoutParams lineParams = new LayoutParams(LayoutParams.MATCH_PARENT, bottomLineSize);
            lineParams.addRule(ALIGN_PARENT_BOTTOM);
            View bottomLine = new View(getContext());
            bottomLine.setBackgroundColor(bottomLineColor);
            bottomLine.setLayoutParams(lineParams);
            addView(bottomLine, getChildCount());
        }
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        if (mViewPager != null) {
            int currentItem = mViewPager.getCurrentItem();
            setSelectState(currentItem);
            mViewPager.addOnPageChangeListener(pageChangeListener);
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setSelectState(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private View.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mViewPager != null) {
                int pos = (int) v.getTag();
                mViewPager.setCurrentItem(pos);
            }
        }
    };

    private void setSelectState(int pos) {
        if (pos >= 0 && pos < itemSize) {
            textViews[mCurrentItem].setTextColor(textUnSelectColor);
            imageViews[mCurrentItem].setImageDrawable(imgs_0[mCurrentItem]);
            textViews[pos].setTextColor(textSelectColor);
            imageViews[pos].setImageDrawable(imgs_1[pos]);
            mCurrentItem = pos;
        }
    }

}
