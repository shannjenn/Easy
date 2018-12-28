package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;

/**
 * 作者：ShannJenn
 * 时间：2018/12/06.
 * 说明：目前只支持0/90度文字(布局只设置2两颜色，多种颜色请调用appendText)
 * 暂时不支持换行显示
 */
public class EasyRotateView2 extends android.support.v7.widget.AppCompatTextView {
    private int rotation;

    public EasyRotateView2(Context context) {
        super(context);
    }

    public EasyRotateView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public EasyRotateView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        int txtSize = (int) EasyDensityUtil.dp2px(14);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EasyRotateView2);
//        rotation = a.getInt(R.styleable.EasyRotateView2_r);
//        textSizeDefault = a.getDimensionPixelSize(R.styleable.EasyRotateView_rotateTextSize, txtSize);
//        textColorDefault = a.getColor(R.styleable.EasyRotateView_rotateTextColor, 0xff000000);
//        degree = a.getInt(R.styleable.EasyRotateView_rotateDegree, 90);
//
//        spanTextColor = a.getColor(R.styleable.EasyRotateView_rotateSpanTextColor, 0xff000000);
//        spanIndexStart = a.getInt(R.styleable.EasyRotateView_rotateSpanIndexStart, -1);
//        spanIndexEnd = a.getInt(R.styleable.EasyRotateView_rotateSpanIndexEnd, -1);

        rotation = a.getLayoutDimension(R.styleable.EasyRotateView2_android_rotation, 0);

        a.recycle();
    }

    private void init(){

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (rotation % 90 == 0) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}