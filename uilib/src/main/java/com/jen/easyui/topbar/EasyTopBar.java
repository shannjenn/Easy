package com.jen.easyui.topbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 顶上标题栏控件
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public class EasyTopBar extends EasyTopBarManager {

    public EasyTopBar(Context context) {
        super(context);
    }

    public EasyTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTitleText(String title) {
        super.setTitleText(title);
    }

    @Override
    public void setLeftText(String text) {
        super.setLeftText(text);
    }

    @Override
    public void setLeftTextColor(int color) {
        super.setLeftTextColor(color);
    }

    @Override
    public void setLeftTextWithLeftImage(Drawable drawable) {
        super.setLeftTextWithLeftImage(drawable);
    }

    @Override
    public void setRightText(String text) {
        super.setRightText(text);
    }

    @Override
    public void setRightTextViewEnable(boolean enable) {
        super.setRightTextViewEnable(enable);
    }

    @Override
    public void setRightTextColor(int color) {
        super.setRightTextColor(color);
    }

    @Override
    public TextView getRightTextView() {
        return super.getRightTextView();
    }

    @Override
    public void setRightTextGone() {
        super.setRightTextGone();
    }

    @Override
    public void setRightTextVisible() {
        super.setRightTextVisible();
    }

    @Override
    public void setLeftImage(int resId) {
        super.setLeftImage(resId);
    }

    @Override
    public void setLeftImage(Drawable drawable) {
        super.setLeftImage(drawable);
    }

    @Override
    public void setLeftImage(Bitmap bitmap) {
        super.setLeftImage(bitmap);
    }

    @Override
    public void setLeftImageSize(int widthPx, int heightPx) {
        super.setLeftImageSize(widthPx, heightPx);
    }

    @Override
    public void setLeftImagePadding(int left, int top, int right, int bottom) {
        super.setLeftImagePadding(left, top, right, bottom);
    }

    @Override
    public ImageView getLeftIv() {
        return super.getLeftIv();
    }

    @Override
    public void setLeftImageBackground(Drawable drawable) {
        super.setLeftImageBackground(drawable);
    }

    @Override
    public void setRightImage(int resId) {
        super.setRightImage(resId);
    }

    @Override
    public void setRightImage(Drawable drawable) {
        super.setRightImage(drawable);
    }

    @Override
    public void setRightImage(Bitmap bmp) {
        super.setRightImage(bmp);
    }

    @Override
    public void setRightImageVisiable(boolean isShow) {
        super.setRightImageVisiable(isShow);
    }

    @Override
    public void setLeftImageClickListener(OnClickListener listener) {
        super.setLeftImageClickListener(listener);
    }

    @Override
    public void setLeftFaceImageClickListener(OnClickListener listener) {
        super.setLeftFaceImageClickListener(listener);
    }

    @Override
    public void setLeftTextClickListener(OnClickListener listener) {
        super.setLeftTextClickListener(listener);
    }

    @Override
    public void setRightTextClickListener(OnClickListener listener) {
        super.setRightTextClickListener(listener);
    }

    @Override
    public void setRightImageClickListener(OnClickListener listener) {
        super.setRightImageClickListener(listener);
    }
}
