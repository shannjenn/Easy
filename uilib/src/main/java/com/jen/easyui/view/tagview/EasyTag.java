package com.jen.easyui.view.tagview;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;

/**
 * 标签
 * 作者：ShannJenn
 * 时间：2018/06/20.
 */

public class EasyTag extends SpannableString {

    public EasyTag(EasyTagDrawable easyTagDrawable) {
        super("\t" + easyTagDrawable.getText() + "\t");

        easyTagDrawable.setBounds();
        int length = easyTagDrawable.getText().length() + 2;
        ImageSpan imageSpan = new ImageSpan(easyTagDrawable, ImageSpan.ALIGN_BASELINE);
        super.setSpan(imageSpan, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        super.setSpan(new EasyTagClick(this), 0, length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//关系到点击事件
    }


    class EasyTagClick extends ClickableSpan {
        public EasyTag easyTag;

        public EasyTagClick(EasyTag easyTag) {
            this.easyTag = easyTag;
        }

        @Override
        public void onClick(View widget) {

        }
    }
}
