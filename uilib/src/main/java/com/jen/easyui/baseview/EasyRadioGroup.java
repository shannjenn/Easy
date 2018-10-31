package com.jen.easyui.baseview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ShannJenn
 * 时间：2018/06/07.
 */

public class EasyRadioGroup extends LinearLayout implements EasyRadioButton.GroupListener {
    private final List<EasyRadioButton> mChildren = new ArrayList<>();
    private OnChildCheckListener childCheckListener;

    public EasyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            View view = getChildAt(i);
            if (view instanceof EasyRadioButton) {
                EasyRadioButton easyRadioButton = (EasyRadioButton) view;
                easyRadioButton.setGroupListener(this);
                mChildren.add(easyRadioButton);
            }
        }
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        if(child instanceof EasyRadioButton){
            mChildren.add((EasyRadioButton) child);
        }
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        mChildren.clear();
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
    }

    @Override
    public void check(EasyRadioButton button) {
        for (int i = 0; i < mChildren.size(); i++) {
            EasyRadioButton easyRadioButton = mChildren.get(i);
            easyRadioButton.setCheck(false);
        }
        button.setCheck(true);
        if (childCheckListener != null) {
            childCheckListener.check(button);
        }
    }

    public void check(int childIndex) {
        if (mChildren.size() - 1 < childIndex) {
            return;
        }
        for (int i = 0; i < mChildren.size(); i++) {
            EasyRadioButton easyRadioButton = mChildren.get(i);
            easyRadioButton.setCheck(false);
        }
        EasyRadioButton easyRadioButton = mChildren.get(childIndex);
        easyRadioButton.setCheck(true);
        if (childCheckListener != null) {
            childCheckListener.check(easyRadioButton);
        }
    }

    public void clearCheck() {
        for (int i = 0; i < mChildren.size(); i++) {
            EasyRadioButton easyRadioButton = mChildren.get(i);
            if (easyRadioButton.isCheck()) {
                easyRadioButton.setCheck(false);
            }
        }
    }

    public List<EasyRadioButton> getChildren() {
        return mChildren;
    }

    /**
     * @return
     */
    public List<Integer> getCheckNum() {
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < mChildren.size(); i++) {
            EasyRadioButton easyRadioButton = mChildren.get(i);
            if (easyRadioButton.isCheck()) {
                nums.add(i);
            }
        }
        return nums;
    }

    public List<EasyRadioButton> getCheckButton() {
        List<EasyRadioButton> buttons = new ArrayList<>();
        for (int i = 0; i < mChildren.size(); i++) {
            EasyRadioButton easyRadioButton = mChildren.get(i);
            if (easyRadioButton.isCheck()) {
                buttons.add(easyRadioButton);
            }
        }
        return buttons;
    }

    public interface OnChildCheckListener {
        void check(EasyRadioButton button);
    }

    public OnChildCheckListener getChildCheckListener() {
        return childCheckListener;
    }

    public void setChildCheckListener(OnChildCheckListener childCheckListener) {
        this.childCheckListener = childCheckListener;
    }
}