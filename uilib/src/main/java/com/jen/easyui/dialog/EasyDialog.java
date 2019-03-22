package com.jen.easyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jen.easyui.R;
import com.jen.easyui.view.shapeview.EasyShapeTextView;


/**
 * 用Build创建
 * 只有一个按钮时，用左边按钮
 * 没有值时控件会隐藏，比如icon==null则隐藏图标
 * 作者：ShannJenn
 * 时间：2018/1/15.
 */
public class EasyDialog extends Dialog implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Context context;
    private TextView tv_content;
    private CharSequence txtContent;

    private GravityTitle titleGravity = GravityTitle.Left;
    private GravityContent contentGravity = GravityContent.Center;
    private StyleButtons styleButtons = StyleButtons.Fill;

    private String txtLeft;
    private String txtRight;
    private String txtMiddle;

    private Drawable iconLeft;
    private Drawable iconRight;
    private String txtTile;
    private String txtCheckBox;

    private DialogListener listener;
    protected int flagCode;

    public static Build build(Context context) {
        return new Build(context);
    }

    EasyDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    void initViews() {
        View layout = LayoutInflater.from(context).inflate(R.layout._easy_dialog, null);

        ImageView iv_icon_left = layout.findViewById(R.id.iv_icon_left);
        ImageView iv_icon_right = layout.findViewById(R.id.iv_icon_right);
        TextView tv_title = layout.findViewById(R.id.tv_title);

        tv_content = layout.findViewById(R.id.tv_content);
        CheckBox cb_check = layout.findViewById(R.id.cb_check);

        LinearLayout ll_buttons = layout.findViewById(R.id.ll_buttons);
        EasyShapeTextView btn_left = layout.findViewById(R.id.btn_left);
        Button btn_middle = layout.findViewById(R.id.btn_middle);
        EasyShapeTextView btn_right = layout.findViewById(R.id.btn_right);

        btn_left.setOnClickListener(this);
        btn_middle.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        iv_icon_right.setOnClickListener(this);

        cb_check.setOnCheckedChangeListener(this);

        if (iconLeft != null) {
            iv_icon_left.setImageDrawable(iconLeft);
            iv_icon_left.setVisibility(View.VISIBLE);
        } else {
            iv_icon_left.setVisibility(View.GONE);
        }
        if (iconRight != null) {
            iv_icon_right.setImageDrawable(iconRight);
            iv_icon_right.setVisibility(View.VISIBLE);
        } else {
            iv_icon_right.setVisibility(View.GONE);
        }

        if (txtTile != null) {
            tv_title.setVisibility(View.VISIBLE);
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
            tv_content.setVisibility(View.VISIBLE);
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
        } else {
            tv_content.setVisibility(View.GONE);
        }

        if (txtCheckBox != null) {
            cb_check.setVisibility(View.VISIBLE);
            cb_check.setText(txtCheckBox);
        } else {
            cb_check.setVisibility(View.GONE);
        }

        switch (styleButtons) {
            case Fill:
                break;
            case Margin:
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll_buttons.getLayoutParams();
                params.leftMargin = Build.dp2px(StyleButtons.marginLeft());
                params.rightMargin = Build.dp2px(StyleButtons.marginRight());
                params.bottomMargin = Build.dp2px(StyleButtons.marginBottom());
                btn_right.getShape().setCorners(4);
                break;
        }

        if (txtLeft != null) {
            btn_left.setVisibility(View.VISIBLE);
            btn_left.setText(txtLeft);
            if (btn_right.getVisibility() == View.GONE) {
                btn_left.getShape().setCornerRightBottom(4);
            }
        } else {
            btn_left.setVisibility(View.GONE);
        }

        if (txtMiddle != null) {
            btn_middle.setVisibility(View.VISIBLE);
            btn_middle.setText(txtMiddle);
        } else {
            btn_middle.setVisibility(View.GONE);
        }
        if (txtRight != null) {
            btn_right.setVisibility(View.VISIBLE);
            btn_right.setText(txtRight);
            if (btn_left.getVisibility() == View.GONE) {
                btn_right.getShape().setCornerLeftBottom(4);
            }
        } else {
            btn_right.setVisibility(View.GONE);
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
        if (listener != null) {
            if (listener instanceof DialogListenerB) {
                DialogListenerB dialogListener = (DialogListenerB) listener;
                dialogListener.dismiss(flagCode);
            }
        }
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (listener == null) {
            return;
        }
        int id = v.getId();
        if (id == R.id.btn_left) {
            if (listener instanceof DialogListenerA) {
                DialogListenerA dialogListener = (DialogListenerA) listener;
                dialogListener.leftButton(flagCode);
            }
        } else if (id == R.id.btn_middle) {
            if (listener instanceof DialogListenerB) {
                DialogListenerB dialogListener = (DialogListenerB) listener;
                dialogListener.middleButton(flagCode);
            }
        } else if (id == R.id.btn_right) {
            listener.rightButton(flagCode);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (listener instanceof DialogListenerC) {
            DialogListenerC dialogListener = (DialogListenerC) listener;
            dialogListener.check(flagCode, buttonView, isChecked);
        }
    }

    void setListener(DialogListener listener) {
        this.listener = listener;
    }

    void setIconLeft(Drawable iconLeft) {
        this.iconLeft = iconLeft;
    }

    void setIconRight(Drawable iconRight) {
        this.iconRight = iconRight;
    }

    void setTxtTile(String txtTile) {
        this.txtTile = txtTile;
    }

    void setTitleGravity(GravityTitle titleGravity) {
        this.titleGravity = titleGravity;
    }

    void setContentGravity(GravityContent contentGravity) {
        this.contentGravity = contentGravity;
    }

    void setTxtCheckBox(String txtCheckBox) {
        this.txtCheckBox = txtCheckBox;
    }

    void setTxtContent(CharSequence txtContent) {
        this.txtContent = txtContent;
    }

    void setTxtLeft(String txtLeft) {
        this.txtLeft = txtLeft;
    }

    void setTxtRight(String txtRight) {
        this.txtRight = txtRight;
    }

    void setTxtMiddle(String txtMiddle) {
        this.txtMiddle = txtMiddle;
    }

    void setFlagCode(int flagCode) {
        this.flagCode = flagCode;
    }

    public void setStyleButtons(StyleButtons styleButtons) {
        this.styleButtons = styleButtons;
    }

    /**
     * 用于方便设置部分文字点击事件 ClickableSpan
     * dialog.getContentView().setMovementMethod(LinkMovementMethod.getInstance());
     *
     * @return .
     */
    public TextView getContentView() {
        return tv_content;
    }
}