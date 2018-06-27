package com.jen.easyui.baseview;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

/**
 * Created by Administrator on 2018/6/20.
 */

public class EasyTag extends SpannableString {

    public EasyTag(EasyTagDrawable easyTagDrawable) {
        super(easyTagDrawable.getText() + "\t");

        easyTagDrawable.setBounds();
        ImageSpan imageSpan = new ImageSpan(easyTagDrawable, ImageSpan.ALIGN_BASELINE);
        super.setSpan(imageSpan, 0, easyTagDrawable.getText().length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

}
