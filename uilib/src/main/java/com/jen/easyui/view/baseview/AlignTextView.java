package com.jen.easyui.view.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.jen.easyui.R;

public class AlignTextView extends android.support.v7.widget.AppCompatTextView {
    private String text;
    private int alignOneTextWithCount;
    private boolean alignEndTextIgnore;

    public AlignTextView(Context context) {
        super(context);
    }

    public AlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initView();
    }

    public AlignTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initView();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AlignTextView);
        text = ta.getString(R.styleable.AlignTextView_android_text);
        alignOneTextWithCount = ta.getInt(R.styleable.AlignTextView_alignOneTextWithCount, 0);
        alignEndTextIgnore = ta.getBoolean(R.styleable.AlignTextView_alignEndTextIgnore, false);
        ta.recycle();
    }

    private void initView() {
        if (text == null || text.length() == 0 || (alignEndTextIgnore && text.length() <= 1) || alignOneTextWithCount <= 0) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        StringBuilder emptyText = new StringBuilder();
        for (int i = 0; i < alignOneTextWithCount; i++) {
            emptyText.append("\u2000");
        }
        int size = text.length() - (alignEndTextIgnore ? 2 : 1);
        for (int i = 0; i < size; i++) {
            builder.append(text, i, i + 1);
            builder.append(emptyText);
        }
        builder.append(text, size, text.length());
        setText(builder.toString());
    }

}
