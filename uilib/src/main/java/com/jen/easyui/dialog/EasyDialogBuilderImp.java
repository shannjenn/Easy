package com.jen.easyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jen.easyui.R;

/**
 * Created by Administrator on 2017/11/3.
 */

abstract class EasyDialogBuilderImp implements View.OnClickListener {
    private Context context;
    private Dialog dialog;

    private String txtContent;
    private String txtYes;
    private String txtNo;

    private DialogOnclick dialogOnclick;

    private int flagCode = -1;
    private String flag;

    public EasyDialogBuilderImp(Context context) {
        this.context = context;
    }

    public EasyDialogBuilderImp setContent(String txt) {
        txtContent = txt;
        return this;
    }

    public EasyDialogBuilderImp setPositiveButton(String txt) {
        txtYes = txt;
        return this;
    }

    public EasyDialogBuilderImp setNegativeButton(String txt) {
        txtNo = txt;
        return this;
    }

    public EasyDialogBuilderImp setFlagCode(int flagCode) {
        this.flagCode = flagCode;
        return this;
    }

    public EasyDialogBuilderImp setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public interface DialogOnclick {
        void positiveButton(String flag, int flagCode);

        void negativeButton(String flag, int flagCode);
    }

    public void setDialogOnclick(DialogOnclick dialogOnclick) {
        this.dialogOnclick = dialogOnclick;
    }

    public Dialog create() {
        View layout = LayoutInflater.from(context).inflate(R.layout._easy_dialog, null);
        initViews(layout);
//			dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        if (dialog == null)
            dialog = new Dialog(context);
        dialog.setContentView(layout);
        return dialog;
    }

    private void initViews(View layout) {
        TextView tvContent = (TextView) layout.findViewById(R.id.tv_content);
        TextView tvYes = (TextView) layout.findViewById(R.id.tv_yes);
        TextView tvNo = (TextView) layout.findViewById(R.id.tv_no);

        if (txtContent != null)
            tvContent.setText(txtContent);
        if (txtYes != null)
            tvYes.setText(txtYes);
        if (txtNo != null)
            tvNo.setText(txtNo);

        tvContent.setOnClickListener(this);
        tvYes.setOnClickListener(this);
        tvNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (dialogOnclick == null) {
            dialog.cancel();
            return;
        }
        int i = view.getId();
        if (i == R.id.tv_yes) {
            dialogOnclick.positiveButton(flag, flagCode);

        } else if (i == R.id.tv_no) {
            dialogOnclick.negativeButton(flag, flagCode);
        } else {
        }
        dialog.cancel();
    }
}
