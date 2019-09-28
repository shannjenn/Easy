package com.jen.easyui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jen.easy.bind.EasyBind;


/**
 * 用Build创建
 * 只有一个按钮时，用左边按钮
 * 没有值时控件会隐藏，比如icon==null则隐藏图标
 * 作者：ShannJenn
 * 时间：2018/1/15.
 */
public abstract class EasyDialogCustom extends EasyDialogFactory {

    public EasyDialogCustom(Context context) {
        super(context);
        init();
    }

    public EasyDialogCustom(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        View layout = LayoutInflater.from(context).inflate(bindLayout(), null);
        EasyBind easyBind = new EasyBind();
        easyBind.inject(this, layout);
        initView(layout);
        setContentView(layout);
        if (alwaysHideKeyboard()) {
            Window window = getWindow();
            if (window != null)
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }
    }

    @Override
    protected void onTouchOutsideListener() {
        if (touchOutsideHideInputMethod) {

        }
    }

    protected abstract int bindLayout();

    protected abstract void initView(View layout);

    /**
     * 禁止键盘弹出
     *
     * @return .
     */
    protected boolean alwaysHideKeyboard() {
        return false;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void hide() {
        super.hide();
    }

}