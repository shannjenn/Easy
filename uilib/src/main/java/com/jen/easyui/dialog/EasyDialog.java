package com.jen.easyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jen.easyui.R;
import com.jen.easyui.dialog.imp.EasyDialogListener;
import com.jen.easyui.util.EasyDensityUtil;

/**
 * 用Build创建
 * 只有一个按钮时，用左边按钮
 * 没有值时控件会隐藏，比如icon==null则隐藏图标
 * 作者：ShannJenn
 * 时间：2018/1/15.
 */
public class EasyDialog extends Dialog implements View.OnClickListener {
    private Context context;

    private float mWidth = 380;//宽度（db）
    private float mHeight = 200;//高度（db）

    private Drawable icon;
    private String txtTile;
    private String txtContent;
    private String txtLeft;
    private String txtRight;
    private String txtMiddle;

    protected ImageView iv_icon;
    protected TextView tv_title;
    protected TextView tv_content;
    protected Button btn_left;
    protected Button btn_middle;
    protected View v_middle_button_line;
    protected Button btn_right;
    protected View v_right_button_line;

    private EasyDialogListener easyDialogListener;

    EasyDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    EasyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    EasyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    void initViews() {
        View layout = LayoutInflater.from(context).inflate(R.layout._easy_dialog, null);

        iv_icon = layout.findViewById(R.id.iv_icon);
        tv_title = layout.findViewById(R.id.tv_title);

        tv_content = layout.findViewById(R.id.tv_content);

        btn_left = layout.findViewById(R.id.btn_left);
        btn_middle = layout.findViewById(R.id.btn_middle);
        v_middle_button_line = layout.findViewById(R.id.v_middle_button_line);
        v_right_button_line = layout.findViewById(R.id.v_right_button_line);
        btn_right = layout.findViewById(R.id.btn_right);

        btn_left.setOnClickListener(this);
        btn_middle.setOnClickListener(this);
        btn_right.setOnClickListener(this);

        if (icon != null) {
            iv_icon.setImageDrawable(icon);
        } else {
            iv_icon.setVisibility(View.GONE);
        }
        if (txtTile != null) {
            tv_title.setText(txtTile);
        } else {
            tv_title.setVisibility(View.GONE);
        }

        if (txtContent != null) {//始终显示
            tv_content.setText(txtContent);
        }
        if (txtLeft != null) {//始终显示
            btn_left.setText(txtLeft);
        }

        if (txtMiddle != null) {
            btn_middle.setText(txtMiddle);
        } else {
            btn_middle.setVisibility(View.GONE);
            v_middle_button_line.setVisibility(View.GONE);
        }
        if (txtRight != null) {
            btn_right.setText(txtRight);
        } else {
            btn_right.setVisibility(View.GONE);
            v_right_button_line.setVisibility(View.GONE);
        }

        setContentView(layout);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
//        window.setGravity(Gravity.BOTTOM); //可设置dialog的位置
//        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) EasyDensityUtil.dp2px(mWidth);
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = (int) EasyDensityUtil.dp2px(mHeight);
        window.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        if (easyDialogListener == null) {
            return;
        }
        int id = v.getId();
        if (id == R.id.btn_left) {
            easyDialogListener.leftButton(v);
        } else if (id == R.id.btn_middle) {
            easyDialogListener.middleButton(v);
        } else if (id == R.id.btn_right) {
            easyDialogListener.rightButton(v);
        }
    }

    public void setEasyDialogListener(EasyDialogListener easyDialogListener) {
        this.easyDialogListener = easyDialogListener;
    }


    //getter/setter========================================================
    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getTxtTile() {
        return txtTile;
    }

    public void setTxtTile(String txtTile) {
        this.txtTile = txtTile;
    }

    public String getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(String txtContent) {
        this.txtContent = txtContent;
    }

    public String getTxtLeft() {
        return txtLeft;
    }

    public void setTxtLeft(String txtLeft) {
        this.txtLeft = txtLeft;
    }

    public String getTxtRight() {
        return txtRight;
    }

    public void setTxtRight(String txtRight) {
        this.txtRight = txtRight;
    }

    public String getTxtMiddle() {
        return txtMiddle;
    }

    public void setTxtMiddle(String txtMiddle) {
        this.txtMiddle = txtMiddle;
    }

    public float getWidth() {
        return mWidth;
    }

    public void setWidth(float width) {
        if (width <= 0)
            return;
        this.mWidth = width;
    }

    public float getHeight() {
        return mHeight;
    }

    public void setHeight(float height) {
        if (height <= 0)
            return;
        this.mHeight = height;
    }


    /**
     * 用与创建EasyDialog
     * 作者：ShannJenn
     * 时间：2018/1/15.
     */
    public static class Build extends EasyDialogBuilder {

        public Build(Context context) {
            super(context);
        }

        @Override
        public Build setIcon(Drawable icon) {
            super.setIcon(icon);
            return this;
        }

        @Override
        public Build setTitle(String txt) {
            super.setTitle(txt);
            return this;
        }

        @Override
        public Build setContent(String txt) {
            super.setContent(txt);
            return this;
        }

        @Override
        public Build setLeftButton(String txt) {
            super.setLeftButton(txt);
            return this;
        }

        @Override
        public Build setMiddleButton(String txt) {
            super.setMiddleButton(txt);
            return this;
        }

        @Override
        public Build setRightButton(String txt) {
            super.setRightButton(txt);
            return this;
        }

        @Override
        public Build setEasyDialogListener(EasyDialogListener easyDialogListener) {
            super.setEasyDialogListener(easyDialogListener);
            return this;
        }

        @Override
        public EasyDialog create() {
            return super.create();
        }
    }
}