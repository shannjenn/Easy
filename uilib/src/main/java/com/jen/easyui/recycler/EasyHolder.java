package com.jen.easyui.recycler;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * baseAdapter
 * 作者：ShannJenn
 * 时间：2018/05/28.
 */

public abstract class EasyHolder extends RecyclerView.ViewHolder {
    private final String TAG = EasyHolder.class.getSimpleName();
    protected EasyRecyclerBaseAdapter mAdapter;
    private View mView;

    public EasyHolder(EasyRecyclerBaseAdapter adapter, View itemView) {
        super(itemView);
        this.mAdapter = adapter;
        this.mView = itemView;
    }

    /**
     * 绑定item数据
     *
     * @return
     */
    protected abstract void onBindData(View view, int viewType, int position);


    /**
     * 增加点击事件
     *
     * @param parent   父级ID
     * @param id       ID
     * @param position 位置
     */
    public void addOnClickEvent(View parent, int id, final int position) {
        if (mAdapter == null) {
            Log.e(TAG, "mAdapter 为空，点击事件不能生效" + EasyHolder.class.getSimpleName());
            return;
        }
        if (mAdapter.easyItemClickListener == null) {
            return;
        }
        if (parent == null) {
            return;
        }
        View view = parent.findViewById(id);
        if (view == null) {
            Log.w(TAG, "点击设置事件失败，请检查view是否不为空");
            return;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.easyItemClickListener.onViewClick(v, position);
            }
        });
    }

    public void addOnClickEvent(int id, final int position) {
        addOnClickEvent(mView, id, position);
    }

    /**
     * 增加长按事件
     *
     * @param parent   父级ID
     * @param id       ID
     * @param position 位置
     */
    public void addOnLongClickEvent(View parent, int id, final int position) {
        if (mAdapter == null) {
            Log.e(TAG, "mAdapter 为空，点击事件不能生效" + EasyHolder.class.getSimpleName());
            return;
        }
        if (mAdapter.easyItemClickListener == null) {
            return;
        }
        if (mView == null) {
            return;
        }
        View view = mView.findViewById(id);
        if (view == null) {
            Log.w(TAG, "点击设置事件失败，请检查view是否不为空");
            return;
        }
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mAdapter.easyItemClickListener.onViewLongClick(v, position);
            }
        });
    }

    public void addOnLongClickEvent(int id, final int position) {
        addOnLongClickEvent(mView, id, position);
    }


    /**
     * 设置隐藏
     *
     * @param parent  父级ID
     * @param id      ID
     * @param visible 显示或者隐藏
     */
    public void setVisible(View parent, int id, int visible) {
        if (parent == null) {
            return;
        }
        View view = parent.findViewById(id);
        if (view != null) {
            view.setVisibility(visible);
        }
    }

    public void setVisible(int id, int visible) {
        setVisible(mView, id, visible);
    }

    /**
     * 设置文本
     *
     * @param parent 父级ID
     * @param id     ID
     * @param text   字符串
     */
    public void setTextView(View parent, int id, String text) {
        if (parent == null) {
            return;
        }
        View view = parent.findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    public void setTextView(int id, String text) {
        setTextView(mView, id, text);
    }

    public void setTextView(int id, int strId) {
        setTextView(mView, id, strId);
    }

    public void setTextView(View parent, int id, int strId) {
        setTextView(parent, id, mAdapter.mContext.getString(strId));
    }

    /**
     * 设置颜色
     *
     * @param parent 父级ID
     * @param id     ID
     * @param color  颜色
     */
    public void setTextColor(View parent, int id, int color) {
        if (parent == null) {
            return;
        }
        View view = parent.findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(color);
        }
    }

    public void setTextColor(int id, int color) {
        setTextColor(mView, id, color);
    }

    /**
     * 设置图片
     *
     * @param parent   父级ID
     * @param id       ID
     * @param sourceid 图片ID
     */
    public void setImageView(View parent, int id, int sourceid) {
        if (parent == null) {
            return;
        }
        View view = parent.findViewById(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(sourceid);
        }
    }

    public void setImageView(int id, int sourceid) {
        setImageView(mView, id, sourceid);
    }

    /**
     * 设置图片
     *
     * @param parent   父级ID
     * @param id       ID
     * @param drawable 图片
     */
    public void setImageView(View parent, int id, Drawable drawable) {
        if (parent == null) {
            return;
        }
        View view = parent.findViewById(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageDrawable(drawable);
        }
    }

    public void setImageView(int id, Drawable drawable) {
        setImageView(mView, id, drawable);
    }
}
