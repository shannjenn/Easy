package com.jen.easyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.jen.easyui.R;


/**
 * 加载中
 * <p>
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public class EasyLoading extends Dialog {

    private Context context;
    private ImageView imagLoading;
    private Animation operatingAnim;

    public EasyLoading(Context context) {
        super(context, R.style.dialog_loading);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        imagLoading = findViewById(R.id.loading);
        operatingAnim = AnimationUtils.loadAnimation(context, R.anim.cirle);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

        imagLoading.startAnimation(operatingAnim);
    }

    @Override
    public void show() {
        if (imagLoading != null) {
            imagLoading.startAnimation(operatingAnim);
        }
        super.show();
    }

    @Override
    public void dismiss() {
        if (imagLoading != null) {
            imagLoading.clearAnimation();
        }
        super.dismiss();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            dismiss();
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
