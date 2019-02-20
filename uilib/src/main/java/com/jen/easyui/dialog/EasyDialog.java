package com.jen.easyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
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
    private GravityTitle titleGravity = GravityTitle.Left;
    private CharSequence txtContent;
    private GravityContent contentGravity = GravityContent.Center;

    private String txtLeft;
    private String txtRight;
    private String txtMiddle;

    private Drawable icon;
    private boolean showCloseImg;
    private String txtTile;

    private EasyDialogListener easyDialogListener;
    protected int flagCode;

    public static Build build(Context context) {
        return new Build(context);
    }

    EasyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    void initViews() {
        View layout = LayoutInflater.from(context).inflate(R.layout._easy_dialog, null);

        ImageView iv_icon = layout.findViewById(R.id.iv_icon);
        ImageView iv_close = layout.findViewById(R.id.iv_close);
        TextView tv_title = layout.findViewById(R.id.tv_title);

        TextView tv_content = layout.findViewById(R.id.tv_content);

        Button btn_left = layout.findViewById(R.id.btn_left);
        Button btn_middle = layout.findViewById(R.id.btn_middle);
        View v_middle_button_line = layout.findViewById(R.id.v_middle_button_line);
        View v_right_button_line = layout.findViewById(R.id.v_right_button_line);
        Button btn_right = layout.findViewById(R.id.btn_right);

        btn_left.setOnClickListener(this);
        btn_middle.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        iv_close.setOnClickListener(this);

        if (icon != null) {
            iv_icon.setImageDrawable(icon);
        } else {
            iv_icon.setVisibility(View.GONE);
        }

        iv_close.setVisibility(showCloseImg ? View.VISIBLE : View.GONE);

        if (txtTile != null) {
            tv_title.setText(txtTile);
            switch (titleGravity) {
                case Left: {
                    tv_title.setGravity(Gravity.CENTER_VERTICAL);
                    break;
                }
                case Center: {
                    tv_title.setGravity(Gravity.CENTER);
                    break;
                }
                default:
                    break;
            }
        } else {
            tv_title.setVisibility(View.GONE);
        }

        if (txtContent != null) {
            tv_content.setText(txtContent);
            switch (contentGravity) {
                case Left: {
                    tv_content.setGravity(Gravity.CENTER_VERTICAL);
                    break;
                }
                case Center: {
                    tv_content.setGravity(Gravity.CENTER);
                    break;
                }
                default:
                    break;
            }
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
    public void dismiss() {
        super.dismiss();
        if (easyDialogListener != null) {
            easyDialogListener.dismiss(flagCode);
        }
    }

    @Override
    public void hide() {
        super.hide();
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

    EasyDialog setEasyDialogListener(EasyDialogListener easyDialogListener) {
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

    public void setShowCloseImg(boolean showCloseImg) {
        this.showCloseImg = showCloseImg;
    }

    public String getTxtTile() {
        return txtTile;
    }

    public void setTxtTile(String txtTile) {
        this.txtTile = txtTile;
    }

    public void setTitleGravity(GravityTitle titleGravity) {
        this.titleGravity = titleGravity;
    }

    public CharSequence getTxtContent() {
        return txtContent;
    }

    public void setContentGravity(GravityContent contentGravity) {
        this.contentGravity = contentGravity;
    }

    public void setTxtContent(CharSequence txtContent) {
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

}