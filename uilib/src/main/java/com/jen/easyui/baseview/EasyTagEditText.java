package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.jen.easyui.R;
import com.jen.easyui.util.EasyDensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签输入控件
 * 作者：ShannJenn
 * 时间：2018/06/20.
 */

public class EasyTagEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher {
    private final List<EasyTag> mEasyTag = new ArrayList<>();
    private boolean isInsert;

    private int inputStartIndex;
    private int inputEndIndex;
    private Editable inputText = new SpannableStringBuilder("");
    private String inputTextBefore = "";
    private String inputTextAfter = "";

    private int tagBackgroundColor;
    private int tagBackgroundHeight;//dp
    private int tagTextColor;
    private float tagTextSize;//sp

    private final int H_ClEAR = 10;
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case H_ClEAR: {
                    if (inputText.length() > 0) {
                        Editable editableText = getEditableText();
//                        EasyLog.d("editableText=" + editableText + "inputText=" + inputText + " inputStartIndex=" + inputStartIndex);
                        editableText.delete(inputStartIndex, inputEndIndex);
                        resetInput();
//                        inputText.clear();
                    }
                    break;
                }
            }
        }
    };

    /*public EasyTagEditText(Context context) {
        super(context);
        init();
    }*/

    public EasyTagEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    public EasyTagEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EasyTagEditText);
        tagBackgroundColor = ta.getColor(R.styleable.EasyTagEditText_tagBackgroundColor, 0xffcccccc);
        float backGroundSize = ta.getDimension(R.styleable.EasyTagEditText_tagBackgroundHeight, EasyDensityUtil.sp2px(18));
        tagBackgroundHeight = (int) EasyDensityUtil.px2dp(backGroundSize);
        tagTextColor = ta.getColor(R.styleable.EasyTagEditText_tagTextColor, 0xff333333);
        float textSize = ta.getDimension(R.styleable.EasyTagEditText_tagTextSize, EasyDensityUtil.sp2px(14));
        tagTextSize = EasyDensityUtil.px2sp(textSize);

        ta.recycle();
    }

    private void init() {
        addTextChangedListener(this);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (inputText == null) {
            return;
        }
        if (selStart == selEnd) {
            if (inputStartIndex > selStart || inputEndIndex < selStart) {
                handler.removeMessages(H_ClEAR);
                handler.sendEmptyMessageDelayed(H_ClEAR, 100);
            }
            if (inputText.length() == 0) {
                resetInput();
            }
        }
    }

    /**
     * 从start开始的count个字符将会被一个长度为after字符替换
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        inputTextBefore = s.toString();
        handler.removeMessages(H_ClEAR);
//        EasyLog.d("beforeTextChanged inputTextBefore=" + inputTextBefore);
    }

    /**
     * 从start开始count个字符替换长度为before的旧文本
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        EasyLog.d("onTextChanged start=" + start + " before=" + before + " count=" + count + " s=" + s);
        if (mEasyTag == null || inputTextAfter.equals(s.toString())) {
            inputTextAfter = s.toString();
            return;
        } else if (s.length() == 0) {
            mEasyTag.clear();
            resetInput();
            inputTextAfter = s.toString();
            return;
        } else if (isInsert) {
            isInsert = false;
            resetInput();
            inputTextAfter = s.toString();
            return;
        }

        boolean isEditInput = false;//是否更改到inputText
        for (int i = start; i < start + before; i++) {
            if (i >= inputStartIndex && i < inputEndIndex) {
                isEditInput = true;
                break;
            }
        }
        if (!isEditInput) {
            for (int i = start; i < start + count; i++) {
                if (i == inputStartIndex && inputStartIndex == inputEndIndex) {
                    isEditInput = true;
                } else if (i >= inputStartIndex && i <= inputEndIndex) {
                    isEditInput = true;
                }
            }
        }
        if (!isEditInput) {//没更改inputText则返回
            if (start < inputStartIndex) {
                inputStartIndex = inputStartIndex + count - before;
                inputEndIndex = inputEndIndex + count - before;
            }
            inputTextAfter = s.toString();
            return;
        }

        String afterText = s.subSequence(start, start + count).toString();

        if (before > 0) {//删除&&替换
            int startIndex = 0;//计算inputText开始删除的位置
            int endIndex;//计算inputText结束替换的位置
            int inputStartIndexTemp = inputStartIndex;

            if (start <= inputStartIndexTemp) {
                startIndex = 0;
                inputStartIndex = inputStartIndexTemp - (inputStartIndexTemp - start);
            } else {
                startIndex = start - inputStartIndexTemp;
            }
            if (start + before >= inputEndIndex) {
                endIndex = inputEndIndex - inputStartIndexTemp;
            } else {
                endIndex = start + before - inputStartIndexTemp;
            }

            String inputTextTemp = inputText.toString();
            if (count > 0) {
                inputText.replace(startIndex, endIndex, afterText);
            } else {
                inputText.delete(startIndex, endIndex);
            }

            String cut = inputTextTemp.subSequence(startIndex, endIndex).toString();
            String beforeText = inputTextBefore.substring(start, start + before);
            String remain = beforeText.replace(cut, "");
            for (int i = 0; i < mEasyTag.size(); i++) {
                EasyTag easyTag = mEasyTag.get(i);
                if (remain.contains(easyTag)) {
                    remain.replace(easyTag, "");
                    mEasyTag.remove(i);
                    if (remain.length() == 0) {
                        break;
                    }
                    i--;
                }
            }
        } else if (count > 0) {//增加
            int startIndex = start - inputStartIndex;//计算inputText开始删除的位置
            inputText.insert(startIndex, afterText);
        } else {//数据不变

        }
        inputEndIndex = inputStartIndex + inputText.length();
        inputTextAfter = s.toString();
//        EasyLog.d("onTextChanged inputText===" + inputText + " inputStartIndex=" + inputStartIndex + " inputEndIndex=" + inputEndIndex);
//        EasyLog.d("inputTextAfter inputTextAfter=" + inputTextAfter);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void resetInput() {
        inputText.clear();
        inputStartIndex = getSelectionStart();
        inputEndIndex = getSelectionStart();
    }

    /**
     * 插入标签
     *
     * @param easyTag
     */
    public void insertTag(EasyTag easyTag) {
        int index = getSelectionStart();
        Editable editableText = getEditableText();
        editableText.replace(inputStartIndex, inputStartIndex + inputText.length(), "");
        isInsert = true;
        if (index < 0 || index > getText().length()) {
            editableText.append(easyTag);
        } else {
            editableText.insert(index, easyTag);
        }
        mEasyTag.add(easyTag);
        resetInput();
    }

    /**
     * 插入标签
     *
     * @param tagText
     */
    public void insertTag(String tagText) {
        EasyTagDrawable easyTagDrawable = EasyTagDrawable.build()
                .setText(tagText)
                .setBackgroundColor(tagBackgroundColor)
                .setBackgroundHeight(tagBackgroundHeight)
                .setTextColor(tagTextColor)
                .setTextSize(tagTextSize)
                .init();
        EasyTag easyTag = new EasyTag(easyTagDrawable);
        insertTag(easyTag);
    }

    /**
     * 获取已经输入的标签
     *
     * @return
     */
    public List<EasyTag> getEasyTag() {
        return mEasyTag;
    }

    public String getInputText() {
        return inputText.toString();
    }

    public void setTagBackgroundColor(int tagBackgroundColor) {
        this.tagBackgroundColor = tagBackgroundColor;
    }

    public void setTagTextColor(int tagTextColor) {
        this.tagTextColor = tagTextColor;
    }

    public void setTagTextSize(float tagTextSize) {
        this.tagTextSize = tagTextSize;
    }

    public void setTagBackgroundHeight(int tagBackgroundHeight) {
        this.tagBackgroundHeight = tagBackgroundHeight;
    }
}
