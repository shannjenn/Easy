package com.jen.easyui.recycler;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jen.easyui.recycler.listener.EasyAdapterListenerA;
import com.jen.easyui.recycler.listener.EasyAdapterListenerB;

/**
 * baseAdapter
 * 作者：ShannJenn
 * 时间：2018/05/28.
 */

public abstract class EasyHolder extends RecyclerView.ViewHolder {
    private final String TAG = EasyHolder.class.getSimpleName();
    protected EasyRecyclerAdapter mAdapter;

    public EasyHolder(EasyRecyclerAdapter adapter, View itemView) {
        super(itemView);
        this.mAdapter = adapter;
    }

    /**
     * 绑定item数据
     */
    protected abstract void onBindData(View view, int viewType, int position);


    /**
     * 增加点击事件
     *
     * @param parent   父级ID
     * @param id       ID
     * @param position 位置
     */
    public EasyHolder addOnClickEvent(View parent, int id, final int position) {
        if (mAdapter == null) {
            Log.e(TAG, "mAdapter 为空，点击事件不能生效" + EasyHolder.class.getSimpleName());
            return this;
        }
        if (mAdapter.listener == null) {
            return this;
        }
        if (parent == null) {
            return this;
        }
        View view = parent.findViewById(id);
        addOnClickEvent(view, position);
        return this;
    }

    /**
     * 增加点击事件
     *
     * @param view     view
     * @param position 位置
     */
    public EasyHolder addOnClickEvent(View view, final int position) {
        if (view == null) {
            Log.w(TAG, "点击设置事件失败，请检查view是否不为空");
            return this;
        }
        if (mAdapter.listener instanceof EasyAdapterListenerA) {
            final EasyAdapterListenerA listenerA = (EasyAdapterListenerA) mAdapter.listener;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerA.onViewClick(v, position);
                }
            });
        }
        return this;
    }

    public EasyHolder addOnClickEvent(int id, final int position) {
        addOnClickEvent(itemView, id, position);
        return this;
    }

    /**
     * 增加长按事件
     *
     * @param parent   父级ID
     * @param id       ID
     * @param position 位置
     */
    public EasyHolder addOnLongClickEvent(View parent, int id, final int position) {
        if (mAdapter == null) {
            Log.e(TAG, "mAdapter 为空，点击事件不能生效" + EasyHolder.class.getSimpleName());
            return this;
        }
        if (mAdapter.listener == null) {
            return this;
        }
        View view = itemView.findViewById(id);
        if (view == null) {
            Log.w(TAG, "点击设置事件失败，请检查view是否不为空");
            return this;
        }
        if (mAdapter.listener instanceof EasyAdapterListenerA) {
            final EasyAdapterListenerB listenerB = (EasyAdapterListenerB) mAdapter.listener;
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return listenerB.onViewLongClick(v, position);
                }
            });
        }
        return this;
    }

    public EasyHolder addOnLongClickEvent(int id, final int position) {
        addOnLongClickEvent(itemView, id, position);
        return this;
    }


    /**
     * 设置隐藏
     *
     * @param parent  父级ID
     * @param id      ID
     * @param visible 显示或者隐藏
     */
    public EasyHolder setVisible(View parent, int id, int visible) {
        if (parent == null) {
            return this;
        }
        View view = parent.findViewById(id);
        if (view != null) {
            view.setVisibility(visible);
        }
        return this;
    }

    public EasyHolder setVisible(int id, int visible) {
        setVisible(itemView, id, visible);
        return this;
    }

    /**
     * 设置文本
     *
     * @param textView view
     * @param text     字符串
     */
    public EasyHolder setTextView(TextView textView, String text) {
        if (textView != null) {
            textView.setText(text);
        }
        return this;
    }

    public EasyHolder setTextView(View parent, int id, String text) {
        if (parent == null) {
            return this;
        }
        View view = parent.findViewById(id);
        if (view instanceof TextView) {
            setTextView((TextView) view, text);
        }
        return this;
    }

    public EasyHolder setTextView(int id, String text) {
        setTextView(itemView, id, text);
        return this;
    }

    public EasyHolder setTextView(int id, int strId) {
        setTextView(itemView, id, strId);
        return this;
    }

    public EasyHolder setTextView(View parent, int id, int strId) {
        setTextView(parent, id, mAdapter.mContext.getString(strId));
        return this;
    }

    /**
     * 设置颜色
     *
     * @param parent 父级ID
     * @param id     ID
     * @param color  颜色
     */
    public EasyHolder setTextColor(View parent, int id, int color) {
        if (parent == null) {
            return this;
        }
        View view = parent.findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(color);
        }
        return this;
    }

    public EasyHolder setTextColor(int id, int color) {
        setTextColor(itemView, id, color);
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param parent 父级ID
     * @param id     ID
     * @param color  颜色
     */
    public EasyHolder setBackgroundColor(View parent, int id, int color) {
        if (parent == null) {
            return this;
        }
        View view = parent.findViewById(id);
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return this;
    }

    public EasyHolder setBackgroundColor(int id, int color) {
        setBackgroundColor(itemView, id, color);
        return this;
    }

    /**
     * 设置图片
     *
     * @param parent   父级ID
     * @param id       ID
     * @param sourceid 图片ID
     */
    public EasyHolder setImageView(View parent, int id, int sourceid) {
        if (parent == null) {
            return this;
        }
        View view = parent.findViewById(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(sourceid);
        }
        return this;
    }

    public EasyHolder setImageView(int id, int sourceid) {
        setImageView(itemView, id, sourceid);
        return this;
    }

    /**
     * 设置图片
     *
     * @param parent   父级ID
     * @param id       ID
     * @param drawable 图片
     */
    public EasyHolder setImageView(View parent, int id, Drawable drawable) {
        if (parent == null) {
            return this;
        }
        View view = parent.findViewById(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(drawable);
        }
        return this;
    }

    public EasyHolder setImageView(int id, Drawable drawable) {
        setImageView(itemView, id, drawable);
        return this;
    }
}
