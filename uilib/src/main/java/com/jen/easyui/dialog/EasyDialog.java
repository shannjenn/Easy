package com.jen.easyui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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
public class EasyDialog extends EasyDialogFactory implements View.OnClickListener {
    private Build build;

    private TextView tv_title;
    private TextView tv_content;

    private LinearLayout ll_buttons;
    private EasyShapeTextView btn_left;
    private EasyShapeTextView btn_right;

    public static Build build(Context context) {
        return new Build(context);
    }

    EasyDialog(Context context, Build build) {
        super(context);
        this.build = build;
        initViews();
    }

    private void initViews() {
        View layout = LayoutInflater.from(build.context).inflate(R.layout._easy_dialog, null);

        ImageView iv_icon_left = layout.findViewById(R.id.iv_icon_left);
        ImageView iv_icon_right = layout.findViewById(R.id.iv_icon_right);
        tv_title = layout.findViewById(R.id.tv_title);
        tv_content = layout.findViewById(R.id.tv_content);

        ll_buttons = layout.findViewById(R.id.ll_buttons);
        btn_left = layout.findViewById(R.id.btn_left);
        btn_right = layout.findViewById(R.id.btn_right);

        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        iv_icon_right.setOnClickListener(this);

        if (build.iconLeft != null) {
            iv_icon_left.setImageDrawable(build.iconLeft);
            iv_icon_left.setVisibility(View.VISIBLE);
        } else {
            iv_icon_left.setVisibility(View.GONE);
        }
        if (build.iconRight != null) {
            iv_icon_right.setImageDrawable(build.iconRight);
            iv_icon_right.setVisibility(View.VISIBLE);
        } else {
            iv_icon_right.setVisibility(build.iconLeft == null ? View.GONE : View.VISIBLE);
        }
        setStyleTitle();
        setStyleContent();
        setStyleButtons();
        setContentView(layout);
    }

    /**
     * 设置标题样式
     */
    private void setStyleTitle() {
        if (build.txtTitle == null) {
            tv_title.setVisibility(View.GONE);
            return;
        }
        tv_title.setText(build.txtTitle);
        if (build.styleTitle == null) {
            build.styleTitle = new StyleTitle();
        }
        tv_title.setGravity(build.styleTitle.getGravity());
        tv_title.setPadding(build.styleTitle.getPaddingLeft(), build.styleTitle.getPaddingTop(),
                build.styleTitle.getPaddingRight(), build.styleTitle.getPaddingBottom());
    }

    /**
     * 设置内容样式
     */
    private void setStyleContent() {
        if (build.txtContent == null) {
            tv_content.setVisibility(View.GONE);
            return;
        }
        tv_content.setText(build.txtContent);
        if (build.styleContent == null) {
            build.styleContent = new StyleContent();
        }
        tv_content.setGravity(build.styleContent.getGravity());
        tv_content.setPadding(build.styleContent.getPaddingLeft(), build.styleContent.getPaddingTop(),
                build.styleContent.getPaddingRight(), build.styleContent.getPaddingBottom());
    }

    /**
     * 设置按钮样式
     */
    private void setStyleButtons() {
        if (build.styleButtons == null) {
            build.styleButtons = new StyleButtons();
        }
        switch (build.styleButtons.show) {
            case LEFT_RIGHT:
                btn_left.setVisibility(View.VISIBLE);
                btn_right.setVisibility(View.VISIBLE);
                btn_left.setText(build.txtLeft);
                btn_right.setText(build.txtRight);
                break;
            case RIGHT:
                btn_left.setVisibility(View.GONE);
                btn_right.setVisibility(View.VISIBLE);
                btn_right.setText(build.txtRight);
                break;
        }
        LinearLayout.LayoutParams buttonsP = (LinearLayout.LayoutParams) ll_buttons.getLayoutParams();
        buttonsP.topMargin = build.styleButtons.getButtonsMarginTop();
        buttonsP.bottomMargin = build.styleButtons.getButtonsMarginBottom();
        LinearLayout.LayoutParams buttonsL = (LinearLayout.LayoutParams) btn_left.getLayoutParams();
        buttonsL.leftMargin = build.styleButtons.getLeftButtonMarginLeft();
        buttonsL.rightMargin = build.styleButtons.getLeftButtonMarginRight();
        LinearLayout.LayoutParams buttonsR = (LinearLayout.LayoutParams) btn_right.getLayoutParams();
        buttonsR.leftMargin = build.styleButtons.getRightButtonMarginLeft();
        buttonsR.rightMargin = build.styleButtons.getRightButtonMarginRight();

        if (build.styleButtons.getLeftButtonCorners() > 0) {
            btn_left.getShape().setCorners(build.styleButtons.getLeftButtonCorners());
        } else {
            btn_left.getShape().setCornerLeftTop(build.styleButtons.getLeftButtonCornerLeftTop());
            btn_left.getShape().setCornerLeftBottom(build.styleButtons.getLeftButtonCornerLeftBottom());
            btn_left.getShape().setCornerRightTop(build.styleButtons.getLeftButtonCornerRightTop());
            btn_left.getShape().setCornerRightBottom(build.styleButtons.getLeftButtonCornerRightBottom());
        }
        if (build.styleButtons.getRightButtonCorners() > 0) {
            btn_right.getShape().setCorners(build.styleButtons.getRightButtonCorners());
        } else {
            btn_right.getShape().setCornerLeftTop(build.styleButtons.getRightButtonCornerLeftTop());
            btn_right.getShape().setCornerLeftBottom(build.styleButtons.getRightButtonCornerLeftBottom());
            btn_right.getShape().setCornerRightTop(build.styleButtons.getRightButtonCornerRightTop());
            btn_right.getShape().setCornerRightBottom(build.styleButtons.getRightButtonCornerRightBottom());
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void onTouchOutside() {

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void hide() {
        super.hide();
        if (build.listener != null) {
            if (build.listener instanceof DialogListenerB) {
                DialogListenerB dialogListener = (DialogListenerB) build.listener;
                dialogListener.dismiss(build.flagCode);
            }
        }
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (build.listener == null) {
            return;
        }
        int id = v.getId();
        if (id == R.id.btn_left) {
            if (build.listener instanceof DialogListenerA) {
                DialogListenerA dialogListener = (DialogListenerA) build.listener;
                dialogListener.leftButton(build.flagCode);
            }
        } else if (id == R.id.btn_right) {
            build.listener.rightButton(build.flagCode);
        }
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

    public TextView getTitleView() {
        return tv_title;
    }
}