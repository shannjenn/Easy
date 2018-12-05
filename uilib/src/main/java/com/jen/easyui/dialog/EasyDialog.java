package com.jen.easyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

/**
 * 用Build创建
 * 只有一个按钮时，用左边按钮
 * 没有值时控件会隐藏，比如icon==null则隐藏图标
 * 作者：ShannJenn
 * 时间：2018/1/15.
 */
public class EasyDialog extends Dialog implements View.OnClickListener {
    private Context context;

//    private float mWidth;//宽度（db）
//    private float mHeight;//高度（db）

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
    protected int flagCode;

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
        if (txtLeft != null) {
            btn_left.setText(txtLeft);
        } else {
            btn_left.setVisibility(View.GONE);
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
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        super.show();
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (easyDialogListener == null) {
            return;
        }
        int id = v.getId();
        if (id == R.id.btn_left) {
            easyDialogListener.leftButton(flagCode);
        } else if (id == R.id.btn_middle) {
            easyDialogListener.middleButton(flagCode);
        } else if (id == R.id.btn_right) {
            easyDialogListener.rightButton(flagCode);
        }
    }

    public EasyDialog setEasyDialogListener(EasyDialogListener easyDialogListener) {
        this.easyDialogListener = easyDialogListener;
        return this;
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

    public int getFlagCode() {
        return flagCode;
    }

    public void setFlagCode(int flagCode) {
        this.flagCode = flagCode;
    }

    /*public float getWidth() {
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
    }*/


    /**
     * 用与创建EasyDialog
     * 作者：ShannJenn
     * 时间：2018/1/15.
     */
    public static class Build {

        private Context context;

        private float width;//宽度(db)
        private float height;//高度(db)

        private Drawable icon;
        private String txtTitle;
        private String txtContent;
        private String txtLeft;
        private String txtMiddle;
        private String txtRight;

        private int flagCode;

        private EasyDialogListener easyDialogListener;

        public Build(Context context) {
            this.context = context;
        }


        public EasyDialog create() {
            EasyDialog dialog = new EasyDialog(context, R.style._easy_dialog);
            dialog.setIcon(icon);
            dialog.setTxtTile(txtTitle);
            dialog.setTxtContent(txtContent);
            dialog.setTxtLeft(txtLeft);
            dialog.setTxtMiddle(txtMiddle);
            dialog.setTxtRight(txtRight);
            dialog.setEasyDialogListener(easyDialogListener);
            dialog.setFlagCode(flagCode);

//            dialog.setWidth(width);
//            dialog.setHeight(height);

            dialog.initViews();
            return dialog;
        }

        public Build setIcon(Drawable icon) {
            this.icon = icon;
            return this;
        }

        public Build setTitle(String txt) {
            txtTitle = txt;
            return this;
        }

        public Build setContent(String txt) {
            txtContent = txt;
            return this;
        }

        public Build setLeftButton(String txt) {
            txtLeft = txt;
            return this;
        }

        public Build setMiddleButton(String txt) {
            txtMiddle = txt;
            return this;
        }

        public Build setRightButton(String txt) {
            txtRight = txt;
            return this;
        }

        public Build setEasyDialogListener(EasyDialogListener easyDialogListener) {
            this.easyDialogListener = easyDialogListener;
            return this;
        }

        public Build setWidth(float width) {
            this.width = width;
            return this;
        }

        public Build setHeight(float height) {
            this.height = height;
            return this;
        }

        public Build setFlagCode(int flagCode) {
            this.flagCode = flagCode;
            return this;
        }
    }
}