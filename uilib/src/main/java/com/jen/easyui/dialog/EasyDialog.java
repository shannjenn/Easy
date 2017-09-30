package com.jen.easyui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jen.easyui.R;

/**
 * 作者：ShannJenn
 * 时间：2017/8/12.
 */
public class EasyDialog extends Dialog {

    public EasyDialog(Context context) {
        super(context, R.style._easy_dialog);
    }

    public static class Builder implements View.OnClickListener {
        private Context context;
        private EasyDialog dialog;

        private String txtContent;
        private String txtYes;
        private String txtNo;

        private DialogOnclick dialogOnclick;

        private int flag = -1;
        private int pos = -1;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContent(String txt) {
            txtContent = txt;
            return this;
        }

        public Builder setPositiveButton(String txt) {
            txtYes = txt;
            return this;
        }

        public Builder setNegativeButton(String txt) {
            txtNo = txt;
            return this;
        }

        public Builder setFlag(int flag) {
            this.flag = flag;
            return this;
        }

        public Builder setPos(int pos) {
            this.pos = pos;
            return this;
        }

        public interface DialogOnclick {
            void positiveButton(int flag, int pos);

            void negativeButton(int flag);
        }

        public void setDialogOnclick(DialogOnclick dialogOnclick) {
            this.dialogOnclick = dialogOnclick;
        }

        public EasyDialog create() {
            View layout = LayoutInflater.from(context).inflate(R.layout._easy_dialog, null);
            initViews(layout);
//			dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            if (dialog == null)
                dialog = new EasyDialog(context);
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
            /*switch (view.getId()) {
                case R.id.tv_yes:
                    dialogOnclick.positiveButton(flag, pos);
                    break;
                case R.id.tv_no:
                    dialogOnclick.negativeButton(flag);
                    break;
                default:
                    break;
            }*/
            dialog.cancel();
        }
    }
}
