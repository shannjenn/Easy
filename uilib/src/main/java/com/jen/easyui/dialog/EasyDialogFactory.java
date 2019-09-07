package com.jen.easyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;

import com.jen.easyui.R;

public abstract class EasyDialogFactory extends Dialog {
    boolean touchOutsideHideInputMethod = true;//点击空白隐藏输入法

    public EasyDialogFactory(@NonNull Context context) {
        super(context, R.style._easy_dialog);
    }

    /**
     * 最外层布局属于Window，默认宽高:WindowManager.LayoutParams.WRAP_CONTENT
     * 相应属性也可以在布局中设置：如居中显示:android:layout_gravity="center"
     */
    @Override
    public void show() {
        if (isShowing()) {
            return;
        }
//        Window window = getWindow();
//        if (window != null) {
//            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//            window.setGravity(Gravity.CENTER);
//        }
        super.show();
    }

    @Override
    public void cancel() {
        if (isShowing()) {
            super.cancel();
        }
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (isOutOfBounds(getContext(), event)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    if (touchOutsideHideInputMethod) {
                        View view = getCurrentFocus();
                        if (view != null) {
                            hideSoftInput(view.getWindowToken());
                        }
                    }
                    onTouchOutsideListener();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    break;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 点击
     */
    protected abstract void onTouchOutsideListener();

    /**
     * 点击空白
     *
     * @param context .
     * @param event   .
     * @return .
     */
    private boolean isOutOfBounds(Context context, MotionEvent event) {
        int x = (int) event.getX();//相对弹窗左上角的x坐标
        int y = (int) event.getY();//相对弹窗左上角的y坐标
        int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();//最小识别距离
        boolean isOut = false;
        if (getWindow() != null) {
            View decorView = getWindow().getDecorView();//弹窗的根View
            isOut = (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop)) || (y > (decorView.getHeight() + slop));
        }
        return isOut;
    }

    /**
     * 隐藏软键盘
     *
     * @param view .
     */
    public void hideSorfInput(View view) {
        hideSoftInput(view.getWindowToken());
    }

    /**
     * 隐藏软键盘
     *
     * @param token .
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void setTouchOutsideHideInputMethod(boolean touchOutsideHideInputMethod) {
        this.touchOutsideHideInputMethod = touchOutsideHideInputMethod;
    }
}
