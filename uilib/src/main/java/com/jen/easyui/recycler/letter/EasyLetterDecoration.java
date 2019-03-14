package com.jen.easyui.recycler.letter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jen.easyui.recycler.EasyRecyclerView;

import java.util.List;

/**
 * 说明：搭配EasyRecyclerView、EasyLetterItem使用
 * 作者：ShannJenn
 * 时间：2018/03/20.
 */

public class EasyLetterDecoration<T extends EasyLetterItem> extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private List<T> mData;
    private int letterHeight = 80;
    private int letterTextColor = Color.BLUE;
    private int letterTextSize = 50;
    private int letterBackgroundColor = Color.GRAY;

    public EasyLetterDecoration(List<T> data) {
        this.mData = data;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent instanceof EasyRecyclerView) {
            getItemOffsets(outRect, view, (EasyRecyclerView) parent, state);
        }
    }

    public void getItemOffsets(Rect outRect, View view, EasyRecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position < 0)
            return;

        int height;
        if (position == 0) {
            height = letterHeight;
        } else {
            String letter = mData.get(position).getLetter();
            String lastLetter = mData.get(position - 1).getLetter();
            if (!letter.equals(lastLetter) && letter.length() > 0) {
                height = letterHeight;
            } else {
                height = 0;
            }
        }
        outRect.set(0, height, 0, 0);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        if (parent instanceof EasyRecyclerView) {
            onDraw(canvas, (EasyRecyclerView) parent, state);
        }
    }

    public void onDraw(Canvas canvas, EasyRecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();

        String lastLetter = "";
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child == null)
                continue;
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position == 0) {
                drawTitle(canvas, left, right, child, params, position, 0);
            } else {
                String letter = mData.get(position).getLetter();
                if (!letter.equals(lastLetter)) {
                    lastLetter = letter;
                    drawTitle(canvas, left, right, child, params, position, 0);
                }
            }
        }
    }

    /**
     * 绘制Title区域背景和文字的方法
     * 最先调用，绘制最下层的title
     *
     * @param canvas
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     * @param headItems
     */
    private void drawTitle(Canvas canvas, int left, int right, View child, RecyclerView.LayoutParams params, int position, int headItems) {
        mPaint.setColor(letterBackgroundColor);
        canvas.drawRect(left, child.getTop() - params.topMargin - letterHeight, right, child.getTop() - params.topMargin, mPaint);

        mPaint.setColor(letterTextColor);
        mPaint.setTextSize(letterTextSize);
        String letter = mData.get(position - headItems).getLetter();
        Rect rect = new Rect();
        mPaint.getTextBounds(letter, 0, letter.length(), rect);
        canvas.drawText(letter, child.getPaddingLeft(), child.getTop() - params.topMargin - (letterHeight / 2 - rect.height() / 2), mPaint);
    }

    @Override
    public void onDrawOver(Canvas canvas, final RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        if (parent instanceof EasyRecyclerView) {
            onDrawOver(canvas, (EasyRecyclerView) parent, state);
        }
    }

    /**
     * 最后调用，绘制最上层的title
     *
     * @param canvas
     * @param parent
     * @param state
     */
    public void onDrawOver(Canvas canvas, final EasyRecyclerView parent, RecyclerView.State state) {
        int position = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) (layoutManager)).findFirstVisibleItemPosition();
        }
        if (position == -1)
            return;
        boolean flag = false;//Canvas是否位移过的标志
        View child = parent.findViewHolderForLayoutPosition(position).itemView;
        String letter = mData.get(position).getLetter();
        if (position + 1 < mData.size()) {
            String nextLetter = mData.get(position + 1).getLetter();
            if (!letter.equals(nextLetter)) {
                if (child.getHeight() + child.getTop() < letterHeight) {
                    canvas.save();
                    flag = true;
                    canvas.translate(0, child.getHeight() + child.getTop() - letterHeight);
                    //头部折叠起来的视效（下边的索引慢慢遮住上边的索引）
                    /*canvas.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(),
                            parent.getPaddingTop() + hild.getHeight() + child.getTop());*/
                }
            }
        }
        mPaint.setColor(letterBackgroundColor);
        canvas.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(),
                parent.getPaddingTop() + letterHeight, mPaint);
        mPaint.setColor(letterTextColor);
        mPaint.setTextSize(letterTextSize);
        Rect rect = new Rect();
        mPaint.getTextBounds(letter, 0, letter.length(), rect);
        canvas.drawText(letter, child.getPaddingLeft(), parent.getPaddingTop() + letterHeight - (letterHeight / 2 - rect.height() / 2), mPaint);
        if (flag)
            canvas.restore();//恢复画布到之前保存的状态
    }

    public Paint getmPaint() {
        return mPaint;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    public int getLetterHeight() {
        return letterHeight;
    }

    public void setLetterHeight(int letterHeight) {
        this.letterHeight = letterHeight;
    }

    public int getLetterTextColor() {
        return letterTextColor;
    }

    public void setLetterTextColor(int letterTextColor) {
        this.letterTextColor = letterTextColor;
    }

    public int getLetterTextSize() {
        return letterTextSize;
    }

    public void setLetterTextSize(int letterTextSize) {
        this.letterTextSize = letterTextSize;
    }

    public int getLetterBackgroundColor() {
        return letterBackgroundColor;
    }

    public void setLetterBackgroundColor(int letterBackgroundColor) {
        this.letterBackgroundColor = letterBackgroundColor;
    }
}