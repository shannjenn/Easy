package com.jen.easyui.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

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
    private int tagStrokeColor;
    private float tagStrokeWidth;//dp
    private int tagTextColor;
    private float tagTextSize;//sp

    private boolean same;//标签是否可以相同

    private long lastClickTime = -1;
    private static final long CLICK_DELAY = 500;//长按事件时间
    //    private String selectText;
    private EasyTagListener easyTagListener;
    private int flag;

    private final int H_ClEAR = 10;
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case H_ClEAR: {
                    if (inputText.length() > 0) {
                        Editable editableText = getEditableText();
                        editableText.delete(inputStartIndex, inputEndIndex);
                        resetInput();
                    }
                    break;
                }
            }
        }
    };

    public interface EasyTagListener {
        void addTextChanged(int flag, String inputText);

        void removeTags(int flag, List<EasyTag> tags);

        void onLongClick(int flag, EasyTagEditText easyTagEditText, EasyTag easyTag, float x, float y);
    }

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
        float backGroundHeight = ta.getDimension(R.styleable.EasyTagEditText_tagBackgroundHeight, EasyDensityUtil.sp2px(18));
        tagBackgroundHeight = (int) EasyDensityUtil.px2dp(backGroundHeight);

        tagStrokeColor = ta.getColor(R.styleable.EasyTagEditText_tagStrokeColor, 0xff666666);
        tagStrokeWidth = ta.getDimension(R.styleable.EasyTagEditText_tagStrokeHeight, 0.5f);

        tagTextColor = ta.getColor(R.styleable.EasyTagEditText_tagTextColor, 0xff333333);
        float textSize = ta.getDimension(R.styleable.EasyTagEditText_tagTextSize, EasyDensityUtil.sp2px(14));
        tagTextSize = EasyDensityUtil.px2sp(textSize);

        ta.recycle();
    }

    private void init() {
        addTextChangedListener(this);
        setMovementMethod(new EasyTagLinkMovementMethod());
        setLongClickable(false);
        setText(" ");//解决光标不显示问题
//        setTop();
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
//        EasyLog.d("onSelectionChanged ---------- ");
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        EasyLog.d("onTouchEvent ---------- ");
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                if (getText().length() == 0) {
                    setText(" ");//解决光标不显示问题
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    class EasyTagLinkMovementMethod extends LinkMovementMethod {
        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
//            EasyLog.d("EasyTagLinkMovementMethod onTouchEvent------");
            int action = event.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                EasyTag.EasyTagClick[] links = buffer.getSpans(off, off, EasyTag.EasyTagClick.class);

                if (links.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        links[0].onClick(widget);
                        lastClickTime = -1;
                    } else if (action == MotionEvent.ACTION_DOWN) {
//                        Selection.setSelection(buffer, buffer.getSpanStart(links[0]), buffer.getSpanEnd(links[0]));
                        lastClickTime = System.currentTimeMillis();
                    } else if (action == MotionEvent.ACTION_MOVE) {
                        if (System.currentTimeMillis() - lastClickTime >= CLICK_DELAY && lastClickTime != -1) {//长按事件触发
                            lastClickTime = -1;
//                            EasyLog.d("EasyTagLinkMovementMethod 长按事件触发 easyTagClick.easyTag=" + links[0].easyTag, " x=" + x + " y=" + y);
                            easyTagListener.onLongClick(flag, EasyTagEditText.this, links[0].easyTag, x, y);
                        }
                    }
                    return true;
                } else {
//                    Selection.removeSelection(buffer);
                }
            }
//            return super.onTouchEvent(widget, buffer, event);
            return true;
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
        if (inputText == null || inputTextAfter.equals(s.toString())) {
            inputTextAfter = s.toString();
            return;
        } else if (isInsert) {
            isInsert = false;
            resetInput();
            inputTextAfter = s.toString();
            return;
        } else if (s.length() == 0) {
            mEasyTag.clear();
            resetInput();
            inputTextAfter = s.toString();
            if (easyTagListener != null) {
                easyTagListener.addTextChanged(flag, inputText.toString());
            }
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
                if (inputStartIndex == inputEndIndex) {
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
            List<EasyTag> removes = new ArrayList<>();
            for (int i = 0; i < mEasyTag.size(); i++) {
                EasyTag easyTag = mEasyTag.get(i);
                if (remain.contains(easyTag)) {
                    remain.replace(easyTag, "");
                    removes.add(easyTag);
                    if (remain.length() == 0) {
                        break;
                    }
                    i--;
                }
            }
            mEasyTag.removeAll(removes);
            if (easyTagListener != null) {
                easyTagListener.removeTags(flag, removes);
            }
        } else if (count > 0) {//增加
            int startIndex = start - inputStartIndex;//计算inputText开始删除的位置
            inputText.insert(startIndex, afterText);
        } else {//数据不变

        }
        inputEndIndex = inputStartIndex + inputText.length();
        inputTextAfter = s.toString();
        if (easyTagListener != null) {
            easyTagListener.addTextChanged(flag, inputText.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 重置inputText
     */
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
        if (easyTag == null || easyTag.length() == 0) {
            return;
        }
        Editable editableText = getEditableText();
        editableText.replace(inputStartIndex, inputStartIndex + inputText.length(), "");
        if (!same && mEasyTag.contains(easyTag)) {
            resetInput();
            return;
        }
        int index = getSelectionStart();
        isInsert = true;
        int length = getText().length();
        if (length == 0 || index == length) {
            editableText.append(easyTag);
            mEasyTag.add(easyTag);
        } else {
            int position = 0;
            String tags = editableText.subSequence(0, index).toString();
            StringBuilder builder = new StringBuilder("");
            do {
                if (tags.equals(builder.toString())) {
                    break;
                }
                builder.append(mEasyTag.get(position));
                position++;
            } while (position < mEasyTag.size());
            editableText.insert(index, easyTag);
            mEasyTag.add(position, easyTag);
        }
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
                .setStrokeColor(tagStrokeColor)
                .setStrokeWidth(tagStrokeWidth)
                .setTextColor(tagTextColor)
                .setTextSize(tagTextSize)
                .init();
        EasyTag easyTag = new EasyTag(easyTagDrawable);
        insertTag(easyTag);
    }

    /**
     * 插入标签
     */
    public void insertTag() {
        insertTag(inputText.toString());
    }

    public void setEasyTagListener(EasyTagListener easyTagListener) {
        this.easyTagListener = easyTagListener;
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

    public void setTagStrokeColor(int tagStrokeColor) {
        this.tagStrokeColor = tagStrokeColor;
    }

    public void setTagStrokeWidth(float tagStrokeWidth) {
        this.tagStrokeWidth = tagStrokeWidth;
    }

    public void setSame(boolean same) {
        this.same = same;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
