package com.jen.easytest.activity.recyclerView.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.jen.easy.log.EasyLog;
import com.jen.easytest.R;

/**
 * 股票条件选择器
 * 作者：ShannJenn
 * 时间：2018/11/01.
 */

public class SortStockTextView extends android.support.v7.widget.AppCompatTextView {
    private static String TAG = SortStockTextView.class.getSimpleName();
    private Context mContext;
    private Sort mSort = Sort.DEFAULT;
    private StockInfo.CompType mCompType;

    public enum Sort {
        DEFAULT(-1),//不排序
        ASC(0),//升序0
        DESC(1);//降序1

        private int value;

        Sort(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    public SortStockTextView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SortStockTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public SortStockTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.drawable.sort_default_icon), null);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mCompType == null) {
            EasyLog.e("mCompType is null  " + TAG);
            return super.dispatchTouchEvent(event);
        }
        switch (event.getAction()) {
            case KeyEvent.ACTION_UP: {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.sort_default_icon);
                switch (mSort) {
                    case DEFAULT: {
                        mSort = Sort.DESC;
                        drawable = mContext.getResources().getDrawable(R.drawable.sort_down_icon);
                        break;
                    }
                    case DESC: {
                        mSort = Sort.ASC;
                        drawable = mContext.getResources().getDrawable(R.drawable.sort_up_icon);
                        break;
                    }
                    case ASC: {
                        mSort = Sort.DEFAULT;
                        drawable = mContext.getResources().getDrawable(R.drawable.sort_default_icon);
                        break;
                    }
                }
                setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                break;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public StockInfo.CompType getCompType() {
        return mCompType;
    }

    public Sort getSort() {
        return mSort;
    }

    public void setCompType(StockInfo.CompType compType) {
        mCompType = compType;
    }

    public void resetSort() {
        mSort = Sort.DEFAULT;
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.sort_default_icon);
        setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }
}
